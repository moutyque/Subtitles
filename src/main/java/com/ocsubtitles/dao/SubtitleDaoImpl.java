package ocsubtitles.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ocsubtitles.dao.DAOUtilitaire.*;

import ocsubtitles.beans.SubtitleFileBean;
import ocsubtitles.beans.SubtitleTranslateBean;
import ocsubtitles.beans.SubtitleTripletBean;
import ocsubtitles.dao.exceptions.DAOException;

public class SubtitleDaoImpl implements SubtitleDao {
	private Connection 			connection;
	private boolean tableExist = false;


	private static final String NUMBER_COLUMN ="number";
	private static final String START_COLUMN ="start";
	private static final String END_COLUMN ="end";
	private static final String TEXT_COLUMN = "text";
	private static final String TRANSLATION_COLUMN ="Fr";
	private static final String FILE_NAME_COLUMN="fileName";
	private static final String TABLE_NAME="Subtitles";
	private static final String DB_NAME="javaee";
	
	
	private static final String SQL_INSERT = "INSERT INTO "+TABLE_NAME+" ("+NUMBER_COLUMN+", "+START_COLUMN +", "+END_COLUMN+", "
			+TEXT_COLUMN+","+FILE_NAME_COLUMN+") VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_GET_ENTRY = "SELECT "+NUMBER_COLUMN+", "+START_COLUMN+", "+END_COLUMN+", "+
			TEXT_COLUMN+", "+FILE_NAME_COLUMN+", "+TRANSLATION_COLUMN +" FROM " +TABLE_NAME+
			" WHERE "+NUMBER_COLUMN+" = ? AND "+FILE_NAME_COLUMN+" = ?";
	private static final String SQL_GET_MOVIE = "SELECT "+NUMBER_COLUMN+", "+START_COLUMN+", "+END_COLUMN+", "
			+TEXT_COLUMN+", "+FILE_NAME_COLUMN+","+TRANSLATION_COLUMN+" FROM "+TABLE_NAME+
			" WHERE "+ FILE_NAME_COLUMN +" = ?";
	private static final String SQL_UPDATE_ENTRY = "UPDATE LOW_PRIORITY "+TABLE_NAME+" SET " + TRANSLATION_COLUMN +" = ? WHERE "+NUMBER_COLUMN+"= ? AND "+ FILE_NAME_COLUMN+" =?";
	private static final String SQL_GET_ALL_MOVIES_NAME ="";
	private static final String SQL_CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+" ("
			+ NUMBER_COLUMN +" INT NOT NULL,"
			+ START_COLUMN + " TIME(3) NOT NULL,"
			+ END_COLUMN + " TIME(3) NOT NULL,"
			+ TEXT_COLUMN + " VARCHAR(200),"
			+ TRANSLATION_COLUMN + " VARCHAR(200),"
			+ FILE_NAME_COLUMN + " VARCHAR(100) NOT NULL,"
			+ "PRIMARY KEY ("+NUMBER_COLUMN+","+FILE_NAME_COLUMN+"))";
	private static final String  SQL_GET_ALL_FILENAME = "SELECT distinct "+ FILE_NAME_COLUMN +" FROM "+ TABLE_NAME +" ORDER BY " + FILE_NAME_COLUMN;
	
	
	
	PreparedStatement preparedStatement = null;


	public SubtitleDaoImpl(DAOFactory daoFactory) throws DAOException{
		try {
			this.connection = daoFactory.getConnection();
			tableExist = checkTable();
		} catch (SQLException e) {
			throw new DAOException("Failed to create SubtitleDaoImpl", e);
		}
	}
	@Override
	public void create(SubtitleTranslateBean sub, String fileName) throws DAOException {
		try {
			//Check if the table exist before save
			
			boolean noEntry = findEntry(sub.getNumber(),fileName)==null;
			if(tableExist && noEntry) {
				createItem(sub,fileName);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} 
	}

	private void createItem(SubtitleTranslateBean sub,String fileName) throws SQLException {
		preparedStatement = initialisationRequetePreparee( connection, SQL_INSERT, false, sub.getNumber(), sub.getStart().toString(), sub.getEnd().toString(), sub.getText(),fileName );
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
	public SubtitleTranslateBean findEntry(long number, String fileName) throws DAOException {
		ResultSet resultSet = null;
		SubtitleTranslateBean sub = null;
		try {
			/* R�cup�ration d'une connexion depuis la Factory */
			preparedStatement = initialisationRequetePreparee( connection, SQL_GET_ENTRY, false, number, fileName );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
			if ( resultSet.next() ) {
				sub = mapSub(resultSet);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			slienteClosing( resultSet);
			slienteClosing(preparedStatement);
		}

		return sub;
	}
	public SubtitleFileBean findMovie(String fileName) throws DAOException {
		ResultSet resultSet = null;
		SubtitleTranslateBean sub = null;
		List<SubtitleTranslateBean> subs = new ArrayList<>();
		SubtitleFileBean subFile = new SubtitleFileBean();
		
		try {
			/* R�cup�ration d'une connexion depuis la Factory */
			preparedStatement = initialisationRequetePreparee( connection, SQL_GET_MOVIE, false, fileName );
			resultSet = preparedStatement.executeQuery();


			/* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
			while(resultSet.next()) {
				sub = mapSub( resultSet );
				subs.add(sub);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			slienteClosing( resultSet, preparedStatement);
		}
		subFile.setSubtitles(subs);
		subFile.setName(fileName);
		return subFile;
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
		
		return new SubtitleTranslateBean(triplet, resultSet.getString( TRANSLATION_COLUMN ));
	}
	
	private static String mapFileName( ResultSet resultSet ) throws SQLException {
			return resultSet.getString(FILE_NAME_COLUMN);
	}
	public void save(SubtitleFileBean subtitleFile) {
			for(SubtitleTranslateBean sub : subtitleFile.getSubtitles()) {
				this.create(sub,subtitleFile.getName());
			}
	}
	@Override
	public void update(SubtitleFileBean subtitleFile) {
		for(SubtitleTranslateBean sub : subtitleFile.getSubtitles()) {
			try {
				preparedStatement = initialisationRequetePreparee( connection, SQL_UPDATE_ENTRY, false,sub.getTranslation(), sub.getNumber(), subtitleFile.getName() );
				preparedStatement.execute();
			} catch (SQLException e) {
				throw new DAOException("Could not update the entry : " +sub.toString());
			}

			
		}
		
	}
	@Override
	public List<String> getAllMoviesTitle() {
		List<String> fileNames = new ArrayList<>();
		try {
			preparedStatement = initialisationRequetePreparee( connection,SQL_GET_ALL_FILENAME , false);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				fileNames.add(mapFileName( resultSet ));
			}
		} catch (SQLException e) {
			throw new DAOException("Could not find movies");
		}
		return fileNames;
	}





}
