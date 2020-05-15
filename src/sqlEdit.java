import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	private int numPlayers = 0;
	private int numDecks = 0;
	private int numGames = 0;
	
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
			numPlayers = resultSet.getInt(1);
			playerData = new Object[numPlayers][45];
			readPlayerData(numPlayers);
			
			// deck data
			sqlStatement = "SELECT COUNT(deckID) FROM dbo.edhDecks";
			resultSet = statement.executeQuery(sqlStatement);
			resultSet.next();
			numDecks = resultSet.getInt(1);
			deckData = new Object[numDecks][52];
			readDeckData(numDecks);
			
			// game data
			sqlStatement = "SELECT COUNT(playerID) FROM dbo.gameData";
			resultSet = statement.executeQuery(sqlStatement);
			resultSet.next();
			numGames = resultSet.getInt(1);
			gameData = new Object[numGames][14];
			readGameData(numGames);
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
				+ " FROM dbo.edhPlayers ORDER BY playerID, totalGames";
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
				+ " FROM dbo.edhDecks ORDER BY deckID, totalGames";
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
				+ " FROM dbo.gameData ORDER BY playerID, deckID, totalGames";
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
	
	// getters
	
	public Object[][] getPlayerData() {
		return playerData;
	} // end of function getPlayerData
	
	public Object[][] getDeckData(){
		return deckData;
	} // end of function getDeckData
	
	public Object[][] getGameData(){
		return gameData;
	} // end of function getGameData
	
	// setters
	
	public void setPlayerData(Object[][] newPlayerData) {
		playerData = newPlayerData;
		delete(numPlayers, "dbo.edhPlayers");
		
		String insertStatement = "";
		// for loop to add each row to the database
		for (int i = 0; i < playerData.length; i++) {
			insertStatement = "INSERT INTO dbo.edhPlayers ("
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
					+ ") VALUES ("
					+ playerData[i][0] + ", "
					+ "'" + playerData[i][1] + "', "
					+ playerData[i][2] + ", "
					+ playerData[i][3] + ", "
					+ playerData[i][4] + ", "
					+ playerData[i][5] + ", "
					+ playerData[i][6] + ", "
					+ playerData[i][7] + ", "
					+ playerData[i][8] + ", "
					+ playerData[i][9] + ", "
					+ playerData[i][10] + ", "
					+ playerData[i][11] + ", "
					+ playerData[i][12] + ", "
					+ playerData[i][13] + ", "
					+ playerData[i][14] + ", "
					+ playerData[i][15] + ", "
					+ playerData[i][16] + ", "
					+ playerData[i][17] + ", "
					+ playerData[i][18] + ", "
					+ playerData[i][19] + ", "
					+ playerData[i][20] + ", "
					+ playerData[i][21] + ", "
					+ playerData[i][22] + ", "
					+ playerData[i][23] + ", "
					+ playerData[i][24] + ", "
					+ playerData[i][25] + ", "
					+ playerData[i][26] + ", "
					+ playerData[i][27] + ", "
					+ playerData[i][28] + ", "
					+ playerData[i][29] + ", "
					+ playerData[i][30] + ", "
					+ playerData[i][31] + ", "
					+ playerData[i][32] + ", "
					+ playerData[i][33] + ", "
					+ playerData[i][34] + ", "
					+ playerData[i][35] + ", "
					+ playerData[i][36] + ", "
					+ playerData[i][37] + ", "
					+ playerData[i][38] + ", "
					+ playerData[i][39] + ", "
					+ playerData[i][40] + ", "
					+ playerData[i][41] + ", "
					+ playerData[i][42] + ", "
					+ playerData[i][43] + ", "
					+ playerData[i][44] +");";
					
			newRow(insertStatement);
		} // end of for loop
	} // end of function setPlayerData
	
	public void setDeckData(Object[][] newDeckData) {
		deckData = newDeckData;
		delete(numDecks, "dbo.edhDecks");
		
		String insertStatement = "";
		// for loop to add each row to the database
		for (int i = 0; i < deckData.length; i++) {
			insertStatement = "INSERT INTO dbo.edhDecks ("
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
					+ "otherWins"
					+ ") VALUES ("
					+ deckData[i][0] + ", "
					+ "'" + deckData[i][1] + "', "
					+ "'" + deckData[i][2] + "', "
					+ "'" + deckData[i][3] + "', "
					+ deckData[i][4] + ", "
					+ deckData[i][5] + ", "
					+ deckData[i][6] + ", "
					+ deckData[i][7] + ", "
					+ deckData[i][8] + ", "
					+ deckData[i][9] + ", "
					+ deckData[i][10] + ", "
					+ deckData[i][11] + ", "
					+ deckData[i][12] + ", "
					+ deckData[i][13] + ", "
					+ deckData[i][14] + ", "
					+ deckData[i][15] + ", "
					+ deckData[i][16] + ", "
					+ deckData[i][17] + ", "
					+ deckData[i][18] + ", "
					+ deckData[i][19] + ", "
					+ deckData[i][20] + ", "
					+ deckData[i][21] + ", "
					+ deckData[i][22] + ", "
					+ deckData[i][23] + ", "
					+ deckData[i][24] + ", "
					+ deckData[i][25] + ", "
					+ deckData[i][26] + ", "
					+ deckData[i][27] + ", "
					+ deckData[i][28] + ", "
					+ deckData[i][29] + ", "
					+ deckData[i][30] + ", "
					+ deckData[i][31] + ", "
					+ deckData[i][32] + ", "
					+ deckData[i][33] + ", "
					+ deckData[i][34] + ", "
					+ deckData[i][35] + ", "
					+ deckData[i][36] + ", "
					+ deckData[i][37] + ", "
					+ deckData[i][38] + ", "
					+ deckData[i][39] + ", "
					+ deckData[i][40] + ", "
					+ deckData[i][41] + ", "
					+ deckData[i][42] + ", "
					+ deckData[i][43] + ", "
					+ deckData[i][44] + ", "
					+ deckData[i][45] + ", "
					+ deckData[i][46] + ", "
					+ deckData[i][47] + ", "
					+ deckData[i][48] + ", "
					+ deckData[i][49] + ", "
					+ deckData[i][50] + ", "
					+ deckData[i][51] +");";
					
			newRow(insertStatement);
		} // end of for loop
	} // end of function setDeckData
	
	public void setGameData(Object[][] newGameData) {
		gameData = newGameData;
		delete(numGames, "dbo.gameData");
		
		String insertStatement = "";
		// for loop to add each row to the database
		for (int i = 0; i < gameData.length; i++) {
			insertStatement = "INSERT INTO dbo.gameData ("
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
					+ ") VALUES ("
					+ gameData[i][0] + ", "
					+ gameData[i][1] + ", "
					+ gameData[i][2] + ", "
					+ gameData[i][3] + ", "
					+ gameData[i][4] + ", "
					+ gameData[i][5] + ", "
					+ gameData[i][6] + ", "
					+ gameData[i][7] + ", "
					+ gameData[i][8] + ", "
					+ gameData[i][9] + ", "
					+ gameData[i][10] + ", "
					+ gameData[i][11] + ", "
					+ gameData[i][12] + ", "
					+ gameData[i][13] + ");";
					
			newRow(insertStatement);
		} // end of for loop
	} // end of function setGameData
	
	/*
	 * function to write a new row to the database
	 */
	private void newRow(String sql) {
		sqlStatement = sql;
		resultSet = null;
		
		try(Connection connection = DriverManager.getConnection(connectionUrl);
				PreparedStatement prepsInsertProduct = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);) {
			
			prepsInsertProduct.execute();
			resultSet = prepsInsertProduct.getGeneratedKeys();
		} catch (Exception e) {
			System.out.println("ERROR: could not write to database");
			System.exit(0);
		}
	} // end of function newRow
	
	/*
	 * function to delete old data
	 */
	private void delete(int rows, String table) {
		sqlStatement = "DELETE TOP (" + rows + ") FROM " + table;
		resultSet = null;
		
		try(Connection connection = DriverManager.getConnection(connectionUrl);
				Statement deleteStatement = connection.createStatement();) {
			
			deleteStatement.executeUpdate(sqlStatement);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: could not write to database");
			System.exit(0);
		}
	} // end of function delete
} // end of class sqlEdit