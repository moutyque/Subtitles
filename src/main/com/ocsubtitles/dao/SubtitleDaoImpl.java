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
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.exceptions.DAOException;

public class SubtitleDaoImpl implements SubtitleDao {
    private DAOFactory          daoFactory;
    private boolean tableExist = false;
    private Connection connexion=null;
	public SubtitleDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	private static final String SQL_INSERT = "INSERT INTO Subtitles (number, start, end, text,movieName) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_CHECK_ENTRY = "SELECT number, start, end, text, movieName FROM Subtitles WHERE number = ? AND movieName = ?";
	private static final String SQL_GET_MOVIE = "SELECT number, start, end, text, movieName FROM subtitles WHERE movieName = ?";
	PreparedStatement preparedStatement = null;
	@Override
	public void create(SubtitleTripletBean triplet) throws DAOException {

	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        checkTable();
	        if(!checkQuery(triplet)) {
	        	valeursAutoGenerees = createItem(triplet);
	        }
	        
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	slienteClosing( valeursAutoGenerees, preparedStatement, connexion );
	    }

	}

	private ResultSet createItem(SubtitleTripletBean triplet) throws SQLException {
		ResultSet valeursAutoGenerees;
		preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, triplet.getNumber(), triplet.getStart().toString(), triplet.getEnd().toString(), triplet.getText(),triplet.getMovieName() );
		int statut = preparedStatement.executeUpdate();
		/* Analyse du statut retourné par la requête d'insertion */
		if ( statut == 0 ) {
		    throw new DAOException( "Failed to create the user, no rows added in the table." );
		    
		}
		/* Récupération de l'id auto-généré par la requête d'insertion */
		valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		if ( valeursAutoGenerees.next() ) {
		    /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
			triplet.setId( valeursAutoGenerees.getLong( 1 ) );
		} else {
		    throw new DAOException( "Failed to create subtitle in base, no auto-generated ID returned." );
		}
		return valeursAutoGenerees;
	}

	public boolean checkQuery(SubtitleTripletBean triplet) throws SQLException {
		Statement stmt;
		triplet = findEntry(triplet.getNumber(),triplet.getMovieName());
		
		return triplet==null ? false : true ;
		
	}

	private void checkTable() throws SQLException {
		if(!tableExist) {
        	
        		
		DatabaseMetaData dbm = connexion.getMetaData();
		// check if "employee" table is there
		ResultSet tables = dbm.getTables(null, null, "Subtitles", null);
		if (tables.next()) {
			tableExist = true;
		}
		else {
			tableExist = false;
			tableExist = createTable();
		}
		if(!tableExist) throw new DAOException("Can not find or create Subtitles table");
        }
	
		
	}
	
	private boolean createTable() {
		final String SQL_CREATE_TABLE ="CREATE TABLE javaee.Subtitles ("
                + "id INT NOT NULL AUTO_INCREMENT,"
                + "number INT NOT NULL,"
                + "start TIME NOT NULL,"
                + "end  TIME NULL,"
                + "text VARCHAR(200),"
                + "txtFr VARCHAR(200),"
                + "movieName VARCHAR(100) NOT NULL,"
                + "PRIMARY KEY (id))";
		Statement stmt;
		boolean returnValue =false;
		try {
			stmt = connexion.createStatement();
			 stmt.executeUpdate(SQL_CREATE_TABLE);
			 returnValue = true;
				
		} catch (SQLException e) {
			System.err.println("Failed to create table");
			System.err.println(e.toString());
			returnValue= false;
			
		}
	     return returnValue;
	}


	/* Implémentation de la méthode définie dans l'interface UtilisateurDao */
	@Override
	public SubtitleTripletBean findEntry(long number, String fileName) throws DAOException {
	    ResultSet resultSet = null;
	    SubtitleTripletBean triplet = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_CHECK_ENTRY, false, number, fileName );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
	        	triplet = map( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	slienteClosing( resultSet, preparedStatement, connexion );
	    }

	    return triplet;
	}
	
	public List<SubtitleTripletBean> findMovie(String fileName) throws DAOException {
	    ResultSet resultSet = null;
	    SubtitleTripletBean triplet = null;
	    List<SubtitleTripletBean> tripletList = new ArrayList<SubtitleTripletBean>();
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_GET_MOVIE, false, fileName );
	        System.out.println(preparedStatement.toString());
	        resultSet = preparedStatement.executeQuery();

	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while(resultSet.next()) {
	        	System.out.println(resultSet.getString(1));
	        	triplet = map( resultSet );
	        	tripletList.add(triplet);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	slienteClosing( resultSet, preparedStatement, connexion );
	    }
	    return tripletList;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static SubtitleTripletBean map( ResultSet resultSet ) throws SQLException {
		SubtitleTripletBean triplet = new SubtitleTripletBean(resultSet.getLong( "number" ));
		triplet.setText(resultSet.getString( "text" ));
		LocalTime date = LocalTime.parse(resultSet.getString( "start" ));
		triplet.setStart(date);
		 date = LocalTime.parse(resultSet.getString( "end" ));
		triplet.setEnd(date);
		triplet.setMovieName(resultSet.getString( "movieName" ));
	    return triplet;
	}

}
