package com.ocsubtitles.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.ocsubtitles.dao.DAOUtilitaire.*;

import com.ocsubtitles.beans.SubtitleFileBean;
import com.ocsubtitles.beans.SubtitleTranslateBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.exceptions.DAOException;

public class SubtitleDaoImpl implements SubtitleDao {
	private DAOFactory          daoFactory;
	private Connection 			connection;
	private boolean tableExist = false;


	private static final String NUMBER_COLUMN ="number";
	private static final String START_COLUMN ="start";
	private static final String END_COLUMN ="end";
	private static final String TEXT_COLUMN = "text";
	private static final String TRAD_COLUMN ="Fr";
	private static final String FILE_NAME_COLUMN="fileName";
	private static final String TABLE_NAME="Subtitles";
	private static final String DB_NAME="javaee";
	
	
	private static final String SQL_INSERT = "INSERT INTO "+TABLE_NAME+" ("+NUMBER_COLUMN+", "+START_COLUMN +", "+END_COLUMN+", "
			+TEXT_COLUMN+","+FILE_NAME_COLUMN+") VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_CHECK_ENTRY = "SELECT "+NUMBER_COLUMN+", "+START_COLUMN+", "+END_COLUMN+", "+
			TEXT_COLUMN+", "+FILE_NAME_COLUMN+" FROM " +TABLE_NAME+
			" WHERE "+NUMBER_COLUMN+" = ? AND "+FILE_NAME_COLUMN+" = ?";
	private static final String SQL_GET_MOVIE = "SELECT "+NUMBER_COLUMN+", "+START_COLUMN+", "+END_COLUMN+", "
			+TEXT_COLUMN+", "+FILE_NAME_COLUMN+","+TRAD_COLUMN+" FROM "+TABLE_NAME+
			" WHERE "+ FILE_NAME_COLUMN +" = ?";
	private static final String SQL_CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+" ("
			+ NUMBER_COLUMN +" INT NOT NULL,"
			+ START_COLUMN + " TIME(2) NOT NULL,"
			+ END_COLUMN + " TIME(2) NOT NULL,"
			+ TEXT_COLUMN + " VARCHAR(200),"
			+ TRAD_COLUMN + " VARCHAR(200),"
			+ FILE_NAME_COLUMN + " VARCHAR(100) NOT NULL,"
			+ "PRIMARY KEY ("+NUMBER_COLUMN+","+FILE_NAME_COLUMN+"))";
	
	
	
	PreparedStatement preparedStatement = null;


	public SubtitleDaoImpl(DAOFactory daoFactory) throws DAOException{
		this.daoFactory = daoFactory;
		try {
			this.connection = daoFactory.getConnection();
		} catch (SQLException e) {
			throw new DAOException("Failed to create SubtitleDaoImpl", e);
		}
	}
	@Override
	public void create(SubtitleTripletBean triplet) throws DAOException {
		try {
			//Check if the table exist before save
			boolean tableExist = checkTable();
			boolean isEntry = findEntry(triplet.getNumber(),triplet.getOriginalFileName())==null;
			if(tableExist && isEntry) {
				createItem(triplet);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} 
	}

	private void createItem(SubtitleTripletBean triplet) throws SQLException {
		preparedStatement = initialisationRequetePreparee( connection, SQL_INSERT, false, triplet.getNumber(), triplet.getStart().toString(), triplet.getEnd().toString(), triplet.getText(),triplet.getOriginalFileName() );
		int statut = preparedStatement.executeUpdate();
		/* Analyse du statut retourn� par la requ�te d'insertion */
		if ( statut == 0 ) {
			throw new DAOException( "Failed to create the user, no rows added in the table." );

		}
	}

	private boolean checkTable() throws DAOException {
		ResultSet tables = null;
		if(!tableExist) {
			try {

				DatabaseMetaData dbm = connection.getMetaData();
				// check if "Subtitles" table is there
				tables = dbm.getTables(null, null, TABLE_NAME, null);
				if (tables.next()) {
					tableExist = true;
				}
				else {
					tableExist = false;
					tableExist = createSubtitleTable();
				}				


			} catch (SQLException e) {
				throw new DAOException(e);
			}
			finally {
				DAOUtilitaire.slienteClosing(tables);
			}
		}
		return tableExist;
	}

	private boolean createSubtitleTable() throws DAOException {
		
		return createTable(SQL_CREATE_TABLE);
		
	}
	public boolean createTable(final String SQL_CREATE_TABLE) {
		
		Statement stmt = null;
		boolean returnValue =true;
		try {
			connection.setCatalog(DB_NAME);
			stmt = connection.createStatement();
			stmt.executeUpdate(SQL_CREATE_TABLE);				
		} catch (SQLException e) {
			returnValue = false;
			throw new DAOException("Can not create subtitle table : "+e.toString());
		}
		finally {
			DAOUtilitaire.slienteClosing(stmt);

		}
		return returnValue;
	}

	
	/* Impl�mentation de la m�thode d�finie dans l'interface UtilisateurDao */
	@Override
	public SubtitleTripletBean findEntry(long number, String fileName) throws DAOException {
		ResultSet resultSet = null;
		SubtitleTripletBean triplet = null;
		try {
			/* R�cup�ration d'une connexion depuis la Factory */
			preparedStatement = initialisationRequetePreparee( connection, SQL_CHECK_ENTRY, false, number, fileName );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
			if ( resultSet.next() ) {
				triplet = mapTriplet( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			slienteClosing( resultSet);
			slienteClosing(preparedStatement);
		}

		return triplet;
	}

	public List<SubtitleTranslateBean> findMovie(String fileName) throws DAOException {
		ResultSet resultSet = null;
		SubtitleTranslateBean sub = null;
		List<SubtitleTranslateBean> subs = new ArrayList<SubtitleTranslateBean>();

		try {
			/* R�cup�ration d'une connexion depuis la Factory */
			preparedStatement = initialisationRequetePreparee( connection, SQL_GET_MOVIE, false, fileName );
			//System.out.println(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();


			/* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
			while(resultSet.next()) {
				//System.out.println(resultSet.getString(1));
				sub = mapSub( resultSet );
				subs.add(sub);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			slienteClosing( resultSet, preparedStatement);
		}
		return subs;
	}
	
	

	/*
	 * Simple m�thode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static SubtitleTripletBean mapTriplet( ResultSet resultSet ) throws SQLException {
		SubtitleTripletBean triplet = new SubtitleTripletBean(resultSet.getLong( NUMBER_COLUMN ));
		triplet.setText(resultSet.getString( TEXT_COLUMN ));
		LocalTime date = LocalTime.parse(resultSet.getString( START_COLUMN ));
		triplet.setStart(date);
		date = LocalTime.parse(resultSet.getString( END_COLUMN ));
		triplet.setEnd(date);
		triplet.setOriginalFileName(resultSet.getString( FILE_NAME_COLUMN ));
		return triplet;
	}

	/*
	 * Simple m�thode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static SubtitleTranslateBean mapSub( ResultSet resultSet ) throws SQLException {
		SubtitleTripletBean triplet = new SubtitleTripletBean(resultSet.getLong( NUMBER_COLUMN ));
		triplet.setText(resultSet.getString( TEXT_COLUMN ));
		LocalTime date = LocalTime.parse(resultSet.getString( START_COLUMN ));
		triplet.setStart(date);
		date = LocalTime.parse(resultSet.getString( END_COLUMN ));
		triplet.setEnd(date);
		triplet.setOriginalFileName(resultSet.getString( FILE_NAME_COLUMN ));
		
		SubtitleTranslateBean sub = new SubtitleTranslateBean(triplet, resultSet.getString( TRAD_COLUMN ));
		return sub;
	}
	public void save(SubtitleFileBean subtitleFile) {
		try {
			connection = daoFactory.getConnection();
			for(SubtitleTripletBean triplet : subtitleFile.getSubtitles()) {
				this.create(triplet);
			}
		}
		catch (SQLException e) {
			System.err.println(e.toString());
		}


	}





}
