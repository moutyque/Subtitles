package ocsubtitles.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

public class TestDAOUtil {
	private final DAOFactory factory = DAOFactory.getInstance();
	private Connection connection =null;
	private Statement stat = null;
	private ResultSet resultSet = null;
	private String SQL_QUERY = "SELECT * FROM sakila.actor limit 100;";
	@Before
	public void setUp() throws Exception {
		connection = factory.getConnection();
		stat = connection.createStatement();
		resultSet = stat.executeQuery(SQL_QUERY);
	}

	@Test
	public void testSlienteClosingResultSet() throws SQLException {
		DAOUtilitaire.slienteClosing(resultSet);
		assertTrue(resultSet.isClosed());
	}

	@Test
	public void testSlienteClosingStatement() throws SQLException {
		DAOUtilitaire.slienteClosing(stat);
		assertTrue(stat.isClosed());
	}

	@Test
	public void testSlienteClosingConnection() throws SQLException {
		DAOUtilitaire.slienteClosing(connection);
		assertTrue(connection.isClosed());
	}

	@Test
	public void testSlienteClosingStatementConnection() throws SQLException {
		DAOUtilitaire.slienteClosing(stat,connection);
		assertTrue(connection.isClosed());
		assertTrue(stat.isClosed());
	}

	@Test
	public void testSlienteClosingResultSetStatementConnection() throws SQLException {
		DAOUtilitaire.slienteClosing(resultSet,stat,connection);
		assertTrue(resultSet.isClosed());
		assertTrue(stat.isClosed());
		assertTrue(connection.isClosed());
	
	}

	@Test
	public void testSlienteClosingResultSetStatement() throws SQLException {
		DAOUtilitaire.slienteClosing(resultSet,stat);
		assertTrue(resultSet.isClosed());
		assertTrue(stat.isClosed());
	}

	@Test
	public void testInitialisationRequetePreparee() throws SQLException {
		
		PreparedStatement preStat = null;
		preStat = factory.initialisationRequetePreparee(connection, SQL_QUERY, false);
		assertTrue(null!=preStat);
	}

	@Test
	public void testPrintResutlSet() {
		DAOUtilitaire.printResutlSet(resultSet);
	}

	@Test
	public void testGetResultSetSize() {
		assertEquals(DAOUtilitaire.getResultSetSize(resultSet),100);
	}

}
