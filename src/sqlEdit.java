import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// sqlEdit class created by Garrett Chapman
// created 05/11/2020
// last updated 05/13/2020
// handles connection to SQL server
// reads and writes data to SQL database

class sqlEdit {
	// information for the database
	private String connectionUrl =
			"jdbc:sqlserver://DESKTOP-US0M9AH:1433;"
			+ "database=edhStats;"
			+ "user=MyLogin;"
			+ "password=userpassword;"
			+ "encrypt=false;"
			+ "trustServerCertificate=false;"
			+ "loginTimeout=30;";
	ResultSet resultSet = null;
	Statement statement;
	String sqlStatement = "";
	
	// information from the database
	Object[][] playerData = new Object[0][0];
	
	// constructor
	public sqlEdit() {
		System.out.println("Updating the database...");
		
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement connect = connection.createStatement();) {
			statement = connect;
			
			// player data
			sqlStatement = "SELECT COUNT(playerID) FROM dbo.edhPlayers";
			resultSet = statement.executeQuery(sqlStatement);
			resultSet.next();
			playerData = new Object[resultSet.getInt(1)][45];
			generatePlayerData(resultSet.getInt(1));
		} catch (SQLException e) {
			System.out.println("ERROR: there was an error with the database.");
			e.printStackTrace();
			System.out.println("Closing program now.");
			System.exit(0);
		}
	} // end of constructor
	
	/*
	 * function to set up player data
	 * gets information from the database
	 */
	private void generatePlayerData(int rows) throws SQLException {
		sqlStatement = "SELECT TOP " + rows + " "
				+ "playerID, "
				+ "name, "
				+ "totalGames, "
				+ "funGames, "
				+ "ehGames, "
				+ "unfunGames, "
				+ "totalNum, "
				+ "scoops, "
				+ "keptCMC, "
				+ "artifactsKept, "
				+ "creaturesKept, "
				+ "landsKept, "
				+ "instantsKept, "
				+ "sorceriesKept, "
				+ "enchantmentsKept, "
				+ "planeswalkersKept, "
				+ "manaKept, "
				+ "drawKept, "
				+ "interactionKept, "
				+ "threatKept, "
				+ "comboKept, "
				+ "otherKept, "
				+ "mulligans, "
				+ "cardsPitched, "
				+ "pitchedCMC, "
				+ "artifactsPitched, "
				+ "creaturesPitched, "
				+ "landsPitched, "
				+ "enchantmentsPitched, "
				+ "instantsPitched, "
				+ "sorceriesPitched, "
				+ "planeswalkersPitched, "
				+ "manaPitched, "
				+ "drawPitched, "
				+ "interactionPitched, "
				+ "threatPitched, "
				+ "comboPitched, "
				+ "otherPitched, "
				+ "wins, "
				+ "aggroWins, "
				+ "aetherfluxWins, "
				+ "labmanWins, "
				+ "comboWins, "
				+ "scoopWins, "
				+ "otherWins"
				+ " FROM dbo.edhPlayers";
		resultSet = statement.executeQuery(sqlStatement);
		resultSet.next();
		
		// for loop to fill the table
		for (int i = 0; i < playerData.length; i++) {
			// internal for loop to fill the current row of the table
			for (int j = 0; j < playerData[i].length; j++) {
				playerData[i][j] = resultSet.getObject(j+1);
			} // end of internal for loop
			resultSet.next();
		} // end of for loop
	} // end of function generatePlayerData
	
	/*
	 * function to get information from the players
	 */
	public Object[][] getPlayerData() {
		return playerData;
	} // end of function getPlayerData

} // end of class sqlEdit