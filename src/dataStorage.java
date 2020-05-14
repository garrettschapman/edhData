// dataStorage class created by Garrett Chapman
// created 05/12/2020
// last updated 05/13/2020
// sends data collected by the user to the database

class dataStorage {
	// constructs
	private sqlEdit database;
	
	Object[][] newPlayers;
	Object[][] newDecks;
	Object[][] newGames;
	
	// constructor
	public dataStorage(sqlEdit server) {
		database = server;
	} //end of constructor
	
	/*
	 * takes data collected by dataCollection and stores it in the database
	 */
	public void storeData(String[][] data) {
		Object[][] currentPlayers = database.getPlayerData();
		Object[][] currentDecks = database.getDeckData();
		Object[][] currentGames = database.getGameData();
		
		makeNewPlayers(data);
	} // end of function storeData
	
	// make arrays
	
	/*
	 * Makes new player array from new data
	 */
	private void makeNewPlayers(String[][] data) {
		newPlayers = new Object[data.length][45];
		
		// for loop to fill new players
		for(int i = 0; i < newPlayers.length; i++) {
			newPlayers[i][0] = 0;			// player ID (blank)
			newPlayers[i][1] = data[i][0];	// player name
			newPlayers[i][2] = 1;			// game (add a game)
			
			switch(data[i][3]) {			// fun/eh/unfun
			case "y":
				newPlayers[i][3] = 1;
				newPlayers[i][4] = 0;
				newPlayers[i][5] = 0;
			case "e":
				newPlayers[i][3] = 0;
				newPlayers[i][4] = 1;
				newPlayers[i][5] = 0;
			case "n":
				newPlayers[i][3] = 0;
				newPlayers[i][4] = 0;
				newPlayers[i][5] = 1;
			}
			
			newPlayers[i][6] = i;
			
		} // end of for loop
	} // end of function makeNewPlayers
	
	// find differences
	
	/*
	 * 
	 */
	private int findPlayerUpdate(Object[][] players) {
		return -1;
	} // end of function findPlayerUpdate
} // end of class dataStorage