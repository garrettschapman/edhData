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
	private ResultSet resultSet = null;
	private Statement statement;
	private String sqlStatement = "";
	
	// information from the database
	private Object[][] playerData = new Object[0][0];
	private Object[][] deckData = new Object[0][0];
	private Object[][] gameData = new Object[0][0];
	
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
			readPlayerData(resultSet.getInt(1));
			
			// deck data
			sqlStatement = "SELECT COUNT(deckID) FROM dbo.edhDecks";
			resultSet = statement.executeQuery(sqlStatement);
			resultSet.next();
			deckData = new Object[resultSet.getInt(1)][52];
			readDeckData(resultSet.getInt(1));
			
			// game data
			sqlStatement = "SELECT COUNT(playerID) FROM dbo.gameData";
			resultSet = statement.executeQuery(sqlStatement);
			resultSet.next();
			gameData = new Object[resultSet.getInt(1)][14];
			readGameData(resultSet.getInt(1));
		} catch (SQLException e) {
			System.out.println("ERROR: there was an error with the database.");
			e.printStackTrace();
			System.out.println("Closing program now.");
			System.exit(0);
		}
	} // end of constructor
	
	// functions to read from the database
	
	/*
	 * function to set up player data
	 * reads information from the database
	 */
	private void readPlayerData(int rows) throws SQLException {
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
				+ "enchantmentsKept, "
				+ "instantsKept, "
				+ "sorceriesKept, "
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
	} // end of function readPlayerData
	
	/*
	 * function to set up deck data
	 * reads data from the database
	 */
	private void readDeckData(int rows) throws SQLException {
		sqlStatement = "SELECT TOP " + rows + " "
				+ "deckID, "
				+ "commander, "
				+ "theme, "
				+ "colors, "
				+ "numColors, "
				+ "totalGames, "
				+ "funGames, "
				+ "ehGames, "
				+ "unfunGames, "
				+ "totalOpponents, "
				+ "opponentsFun, "
				+ "opponentsEh, "
				+ "opponentsUnfun, "
				+ "colorsInHand, "
				+ "manaInHand, "
				+ "keptCMC, "
				+ "artifactsKept, "
				+ "creaturesKept, "
				+ "landsKept, "
				+ "enchantmentsKept, "
				+ "instantsKept, "
				+ "sorceriesKept, "
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
				+ "otherWins "
				+ " FROM dbo.edhDecks";
		resultSet = statement.executeQuery(sqlStatement);
		resultSet.next();
		
		// for loop to fill the table
		for (int i = 0; i < deckData.length; i++) {
			// internal for loop to fill the current row of the table
			for (int j = 0; j < deckData[i].length; j++) {
				deckData[i][j] = resultSet.getObject(j+1);
			} // end of internal for loop
			resultSet.next();
		} // end of for loop
	} // end of function readDeckData
	
	/*
	 * function to set up game data
	 * reads data from the database
	 */
	private void readGameData(int rows) throws SQLException {
		sqlStatement = "SELECT TOP " + rows + " "
				+ "playerID, "
				+ "deckID, "
				+ "totalGames, "
				+ "mulligans, "
				+ "funGames, "
				+ "ehGames, "
				+ "unfunGames, "
				+ "wins, "
				+ "aggroWins, "
				+ "comboWins, "
				+ "scoopWins, "
				+ "aetherfluxWins, "
				+ "labmanWins, "
				+ "otherWins"
				+ " FROM dbo.gameData";
		resultSet = statement.executeQuery(sqlStatement);
		resultSet.next();
		
		// for loop to fill the table
		for (int i = 0; i < gameData.length; i++) {
			// internal for loop to fill the current row of the table
			for (int j = 0; j < gameData[i].length; j++) {
				gameData[i][j] = resultSet.getObject(j+1);
			} // end of internal for loop
			resultSet.next();
		} // end of for loop
	} // end of function readGameData
	
	/*
	 * function to write data to the database
	 */
	public void write() {
		//TODO this
	} // end of function write
	
	// getters
	
	/*
	 * function to get information from the players
	 */
	public Object[][] getPlayerData() {
		return playerData;
	} // end of function getPlayerData
	
	/*
	 * function to get information from the decks
	 */
	public Object[][] getDeckData(){
		return deckData;
	} // end of function getDeckData
	
	/*
	 * function to get information about games (player/deck combinations)
	 */
	public Object[][] getGameData(){
		return gameData;
	} // end of function getGameData
	
	// setters
	
	/*
	 * function to set player information
	 */
	public void setPlayerData(Object[][] newPlayers) {
		playerData = newPlayers;
	} // end of function setPlayerData
	
	/*
	 * function to set deck information
	 */
	public void setDeckData(Object[][] newDecks) {
		deckData = newDecks;
	} // end of function setDeckData
	
	/*
	 * function to set game information
	 */
	public void setGameData(Object[][] newGames) {
		gameData = newGames;
	} // end of function setGameData
} // end of class sqlEdit