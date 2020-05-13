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
		
		
	} // end of function makeNewPlayers
	
	// find differences
	
	/*
	 * 
	 */
	private int findPlayerUpdate(Object[][] players) {
		return -1;
	} // end of function findPlayerUpdate
} // end of class dataStorage