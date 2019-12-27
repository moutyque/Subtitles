package com.ocsubtitles.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public final class DAOUtilitaire {

    /*
     * Constructeur caché par défaut (car c'est une classe finale utilitaire,
     * contenant uniquement des méthode appelées de manière statique)
     */
    private DAOUtilitaire() {
    }

    /* Fermeture silencieuse du resultset */
    public static void slienteClosing( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "Failed to close the ResultSet : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse du statement */
    public static void slienteClosing( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "Failed to close the Statement : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse de la connexion */
    public static void slienteClosing( Connection connexion ) {
        if ( connexion != null ) {
            try {
                connexion.close();
            } catch ( SQLException e ) {
                System.out.println( "Failed to close the connexion : " + e.getMessage() );
            }
        }
    }

    /* Fermetures silencieuses du statement et de la connexion */
    public static void slienteClosing( Statement statement, Connection connexion ) {
        slienteClosing( statement );
        slienteClosing( connexion );
    }

    /* Fermetures silencieuses du resultset, du statement et de la connexion */
    public static void slienteClosing( ResultSet resultSet, Statement statement, Connection connexion ) {
        slienteClosing( resultSet );
        slienteClosing( statement );
        slienteClosing( connexion );
    }
    /* Fermetures silencieuses du resultset, du statement */
    public static void slienteClosing( ResultSet resultSet, Statement statement) {
        slienteClosing( resultSet );
        slienteClosing( statement );
    }
    /*
     * Initialise la requête préparée basée sur la connexion passée en argument,
     * avec la requête SQL et les objets donnés.
     */
    public static PreparedStatement initialisationRequetePreparee( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }
public void printResutlSet(ResultSet result) throws SQLException {
	ResultSetMetaData rsmd = result.getMetaData();
	int columnsNumber = rsmd.getColumnCount();
	while (result.next()) {
	    for (int i = 1; i <= columnsNumber; i++) {
	        if (i > 1) System.out.print(",  ");
	        String columnValue = result.getString(i);
	        System.out.print(columnValue + " " + rsmd.getColumnName(i));
	    }
	    System.out.println("");
	}
}
}
