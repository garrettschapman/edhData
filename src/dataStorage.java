// dataStorage class created by Garrett Chapman
// created 05/12/2020
// last updated 05/18/2020
// sends data collected by the user to the database

class dataStorage {
	// database stuff
	private sqlEdit database;
	private Object[][] currentPlayers;
	private Object[][] currentDecks;
	private Object[][] currentGames;
	
	// stuff to add to database
	private Object[] winner;
	
	private Object[][] newPlayers;
	private Object[][] newDecks;
	private Object[][] newGames;
	
	// constructor
	public dataStorage(sqlEdit server) {
		database = server;
		
		currentPlayers = database.getPlayerData();
		currentDecks = database.getDeckData();
		currentGames = database.getGameData();
	} //end of constructor
	
	/*
	 * takes data collected by dataCollection and stores it in the database
	 */
	public void storeData(String[][] data) {
		winner = new Object[46];
		winner[0] = 0;
		winner[1] = "Game Winner";
		for(int i = 2; i < winner.length; i++) {
			winner[i] = 0;
		}
		
		makeNewPlayers(data);
		makeNewDecks(data);
		makeNewGames(data);
		
		updatePlayer(0, winner);
		
		// for loop to add everything to the current arrays
		for(int i = 0; i < data.length; i++) {
			int playerID = findPlayerID(newPlayers[i]);
			newPlayers[i][0] = playerID;
			updatePlayer(playerID, newPlayers[i]);
			
			int deckID = findDeckID(newDecks[i]);
			newDecks[i][0] = deckID;
			updateDeck(deckID, newDecks[i]);
			
			newGames[i][0] = playerID;
			newGames[i][1] = deckID;
			int gameData = findGameData(newGames[i]);
			updateGame(gameData, newGames[i]);
		} // end of for loop
		
		database.setPlayerData(currentPlayers);
		database.setDeckData(currentDecks);
		database.setGameData(currentGames);
	} // end of function storeData
	
	// make arrays
	
	/*
	 * makes new player array from new data
	 */
	private void makeNewPlayers(String[][] data) {
		newPlayers = new Object[data.length][46];
		
		// for loop to fill new players
		for(int i = 0; i < newPlayers.length; i++) {
			newPlayers[i][1] = data[i][0];	// player name
			newPlayers[i][2] = 1;			// game (add a game)
			
			switch(data[i][5].toString()) {			// fun/eh/unfun
			case "y":
				newPlayers[i][3] = 1;
				newPlayers[i][4] = 0;
				newPlayers[i][5] = 0;
				break;
			case "e":
				newPlayers[i][3] = 0;
				newPlayers[i][4] = 1;
				newPlayers[i][5] = 0;
				break;
			case "n":
				newPlayers[i][3] = 0;
				newPlayers[i][4] = 0;
				newPlayers[i][5] = 1;
				break;
			}
			
			newPlayers[i][6] = (i+1);		// player number
			
			switch(data[i][38]) {			// scoop?
			case "y":
				newPlayers[i][7] = 1;
				break;
			case "n":
				newPlayers[i][7] = 0;
				break;
			}
			
			/*
			 * internal for loop to get kept cards and pitched cards
			 * 8 		= Kept CMC
			 * 9-15		= Kept card types
			 * 16-21	= Kept card themes
			 * 22		= Mulligans
			 * 23		= Number of cards pitched
			 * 24		= Pitched CMC
			 * 25-31	= Pitched card types
			 * 32-37	= Pitched card themes
			 */
			for (int j = 8; j < 38; j++) {
				newPlayers[i][j] = data[i][j];
			} // end of internal for loop
			
			switch(data[i][40].toString()) {			// how did they win?
			case "aggro":
				newPlayers[i][39] = 1;
				newPlayers[i][40] = 0;
				newPlayers[i][41] = 0;
				newPlayers[i][42] = 0;
				newPlayers[i][43] = 0;
				newPlayers[i][44] = 0;
				break;
			case "aether":
				newPlayers[i][39] = 0;
				newPlayers[i][40] = 1;
				newPlayers[i][41] = 0;
				newPlayers[i][42] = 0;
				newPlayers[i][43] = 0;
				newPlayers[i][44] = 0;
				break;
			case "labman":
				newPlayers[i][39] = 0;
				newPlayers[i][40] = 0;
				newPlayers[i][41] = 1;
				newPlayers[i][42] = 0;
				newPlayers[i][43] = 0;
				newPlayers[i][44] = 0;
				break;
			case "combo":
				newPlayers[i][39] = 0;
				newPlayers[i][40] = 0;
				newPlayers[i][41] = 0;
				newPlayers[i][42] = 1;
				newPlayers[i][43] = 0;
				newPlayers[i][44] = 0;
				break;
			case "scoops":
				newPlayers[i][39] = 0;
				newPlayers[i][40] = 0;
				newPlayers[i][41] = 0;
				newPlayers[i][42] = 0;
				newPlayers[i][43] = 1;
				newPlayers[i][44] = 0;
				break;
			case "other":
				newPlayers[i][39] = 0;
				newPlayers[i][40] = 0;
				newPlayers[i][41] = 0;
				newPlayers[i][42] = 0;
				newPlayers[i][43] = 0;
				newPlayers[i][44] = 1;
				break;
			default:
				newPlayers[i][39] = 0;
				newPlayers[i][40] = 0;
				newPlayers[i][41] = 0;
				newPlayers[i][42] = 0;
				newPlayers[i][43] = 0;
				newPlayers[i][44] = 0;
			}
			
			newPlayers[i][45] = data.length-1;	// number of opponents
			
			switch(data[i][39].toString()) {			// did they win? (comes after so we can set the winner)
			case "y":
				newPlayers[i][38] = 1;
				
				// internal for loop to create the winning player
				for(int w = 2; w < winner.length; w++) {
					winner[w] = newPlayers[i][w];
				} // end of internal for loop
				
				break;
			case "n":
				newPlayers[i][38] = 0;
				break;
			}
		} // end of external for loop
	} // end of function makeNewPlayers
	
	/*
	 * make new deck array from new data
	 */
	private void makeNewDecks(String[][] data) {
		newDecks = new Object[data.length][53];
		
		// for loop to fill new decks
		for(int i = 0; i < newDecks.length; i++) {
			newDecks[i][1] = data[i][1];	// commander
			newDecks[i][2] = data[i][2];	// theme
			newDecks[i][3] = data[i][3];	// color name
			newDecks[i][4] = data[i][4];	// number of colors
			newDecks[i][5] = 1;				// add a game
			
			switch(data[i][5].toString()) {			// fun/eh/unfun
			case "y":
				newDecks[i][6] = 1;
				newDecks[i][7] = 0;
				newDecks[i][8] = 0;
				break;
			case "e":
				newDecks[i][6] = 0;
				newDecks[i][7] = 1;
				newDecks[i][8] = 0;
				break;
			case "n":
				newDecks[i][6] = 0;
				newDecks[i][7] = 0;
				newDecks[i][8] = 1;
				break;
			}
			
			newDecks[i][9] = data.length-1;	// number of opponents
			
			// variables for keeping track of opponents' fun
			int fun = 0;
			int eh = 0;
			int unfun = 0;
			
			// internal for loop to determine opponents' fun
			for(int j = 0; j < data.length; j++) {
				if(i != j) { // does not treat this deck as one of its opponents
					switch(data[j][5].toString()) {
					case "y":
						fun++;
						break;
					case "e":
						eh++;
						break;
					case "n":
						unfun++;
						break;
					}
				}
			} // end of internal for loop
			
			newDecks[i][10] = fun;
			newDecks[i][11] = eh;
			newDecks[i][12] = unfun;
			
			/*
			 * internal for loop to get kept cards and pitched cards
			 * 13		= Colors in hand
			 * 14		= Colors of mana in hand
			 * 15 		= Kept CMC
			 * 15-21	= Kept card types
			 * 22-27	= Kept card themes
			 * 28		= Mulligans
			 * 29		= Number of cards pitched
			 * 30		= Pitched CMC
			 * 31-37	= Pitched card types
			 * 38-44	= Pitched card themes
			 */
			for(int j = 13; j < 45; j++) {
				newDecks[i][j] = data[i][j-7];
			} // end of internal for loop
			
			switch(data[i][39].toString()) {			// did they win?
			case "y":
				newDecks[i][45] = 1;
				break;
			case "n":
				newDecks[i][45] = 0;
				break;
			}
			
			switch(data[i][40].toString()) {			// how did they win?
			case "aggro":
				newDecks[i][46] = 1;
				newDecks[i][47] = 0;
				newDecks[i][48] = 0;
				newDecks[i][49] = 0;
				newDecks[i][50] = 0;
				newDecks[i][51] = 0;
				break;
			case "aether":
				newDecks[i][46] = 0;
				newDecks[i][47] = 1;
				newDecks[i][48] = 0;
				newDecks[i][49] = 0;
				newDecks[i][50] = 0;
				newDecks[i][51] = 0;
				break;
			case "labman":
				newDecks[i][46] = 0;
				newDecks[i][47] = 0;
				newDecks[i][48] = 1;
				newDecks[i][49] = 0;
				newDecks[i][50] = 0;
				newDecks[i][51] = 0;
				break;
			case "combo":
				newDecks[i][46] = 0;
				newDecks[i][47] = 0;
				newDecks[i][48] = 0;
				newDecks[i][49] = 1;
				newDecks[i][50] = 0;
				newDecks[i][51] = 0;
				break;
			case "scoops":
				newDecks[i][46] = 0;
				newDecks[i][47] = 0;
				newDecks[i][48] = 0;
				newDecks[i][49] = 0;
				newDecks[i][50] = 1;
				newDecks[i][51] = 0;
				break;
			case "other":
				newDecks[i][46] = 0;
				newDecks[i][47] = 0;
				newDecks[i][48] = 0;
				newDecks[i][49] = 0;
				newDecks[i][50] = 0;
				newDecks[i][51] = 1;
				break;
			default:
				newDecks[i][46] = 0;
				newDecks[i][47] = 0;
				newDecks[i][48] = 0;
				newDecks[i][49] = 0;
				newDecks[i][50] = 0;
				newDecks[i][51] = 0;
			}
			
			//TODO switch this once all current data is in and decks have been set
			newDecks[i][52] = 0;	// relevancy (0 = not relevant, 1 = relevant)
		} // end of for loop
	} // end of function makeNewDecks
	
	/*
	 * make new game data array from new data
	 */
	private void makeNewGames(String[][] data) {
		newGames = new Object[data.length][14];
		
		// for loop to fill out new combinations
		for (int i = 0; i < newGames.length; i++) {
			newGames[i][2] = 1;				// add a game
			newGames[i][3] = data[i][22];	// mulligans
			
			switch(data[i][5].toString()) {			// fun/eh/unfun
			case "y":
				newGames[i][4] = 1;
				newGames[i][5] = 0;
				newGames[i][6] = 0;
				break;
			case "e":
				newGames[i][4] = 0;
				newGames[i][5] = 1;
				newGames[i][6] = 0;
				break;
			case "n":
				newGames[i][4] = 0;
				newGames[i][5] = 0;
				newGames[i][6] = 1;
				break;
			}
			
			switch(data[i][39].toString()) {			// did they win?
			case "y":
				newGames[i][7] = 1;
				break;
			case "n":
				newGames[i][7] = 0;
				break;
			}
			
			switch(data[i][40].toString()) {			// how did they win?
			case "aggro":
				newGames[i][8] = 1;
				newGames[i][9] = 0;
				newGames[i][10] = 0;
				newGames[i][11] = 0;
				newGames[i][12] = 0;
				newGames[i][13] = 0;
				break;
			case "aether":
				newGames[i][8] = 0;
				newGames[i][9] = 1;
				newGames[i][10] = 0;
				newGames[i][11] = 0;
				newGames[i][12] = 0;
				newGames[i][13] = 0;
				break;
			case "labman":
				newGames[i][8] = 0;
				newGames[i][9] = 0;
				newGames[i][10] = 1;
				newGames[i][11] = 0;
				newGames[i][12] = 0;
				newGames[i][13] = 0;
				break;
			case "combo":
				newGames[i][8] = 0;
				newGames[i][9] = 0;
				newGames[i][10] = 0;
				newGames[i][11] = 1;
				newGames[i][12] = 0;
				newGames[i][13] = 0;
				break;
			case "scoops":
				newGames[i][8] = 0;
				newGames[i][9] = 0;
				newGames[i][10] = 0;
				newGames[i][11] = 0;
				newGames[i][12] = 1;
				newGames[i][13] = 0;
				break;
			case "other":
				newGames[i][8] = 0;
				newGames[i][9] = 0;
				newGames[i][10] = 0;
				newGames[i][11] = 0;
				newGames[i][12] = 0;
				newGames[i][13] = 1;
				break;
			default:
				newGames[i][8] = 0;
				newGames[i][9] = 0;
				newGames[i][10] = 0;
				newGames[i][11] = 0;
				newGames[i][12] = 0;
				newGames[i][13] = 0;
			}
		} // end of for loop
	} // end of function makeNewGames
	
	// find differences
	
	/*
	 * finds which player needs to be updated
	 * returns the playerID
	 */
	private int findPlayerID(Object[] player) {
		String newPlayer = player[1].toString().replaceAll("\\P{Print}", "").trim();
		
		// for loop to find which player needs to be updated
		for(int i = 0; i < currentPlayers.length; i++) {
			String check = currentPlayers[i][1].toString().replaceAll("\\P{Print}", "").trim();
			
			// the player must have the same name
			if((newPlayer.equals(check))) {
				return i;
			}
		} // end of for loop
		
		// player will be added to the bottom of the table
		return currentPlayers.length;
	} // end of function findPlayerID
	
	/*
	 * finds which deck needs to be updated
	 * returns the deckID
	 */
	private int findDeckID(Object[] deck) {
		String newDeckName = deck[1].toString().replaceAll("\\P{Print}", "").trim();
		String newDeckTheme = deck[2].toString().replaceAll("\\P{Print}", "").trim();
		
		// for loop to find which deck needs to be updated
		for(int i = 0; i < currentDecks.length; i++) {
			String checkName = currentDecks[i][1].toString().replaceAll("\\P{Print}", "").trim();
			String checkTheme = currentDecks[i][2].toString().replaceAll("\\P{Print}", "").trim();
			
			// the deck must have the same commander and theme
			if(((newDeckName).equals(checkName)) && ((newDeckTheme).equals(checkTheme))) {
				return i+1;
			}
		} // end of for loop
		
		// deck will be added to the bottom of the table
		return currentDecks.length+1;
	} // end of function findDeckID
	
	/*
	 * finds which player/deck combo needs to be updated
	 * returns its location in the array
	 */
	private int findGameData(Object[] gameData) {
		// for loop to find which combination needs to be updated
		for(int i = 0; i < currentGames.length; i++) {
			// the combination must have the same playerID and deckID
			if((Integer.parseInt(gameData[0].toString()) == Integer.parseInt(currentGames[i][0].toString())) && (Integer.parseInt(gameData[1].toString()) == Integer.parseInt(currentGames[i][1].toString()))) {
				return i;
			}
		} // end of for loop
		
		// combination will be added to the bottom of the table
		return currentGames.length;
	} // end of function findGameData
	
	// update existing info
	
	/*
	 * update player list
	 * adds a new player if the playerID is not known
	 */
	private void updatePlayer(int id, Object[] player) {
		if (id >= currentPlayers.length) {
			currentPlayers = addNew(id, currentPlayers, player);
		} else {
			// for loop to update the player's row
			for(int i = 2; i < 46; i++) {
				currentPlayers[id][i] = Integer.parseInt((currentPlayers[id][i].toString())) + Integer.parseInt(player[i].toString());
			} // end of for loop
		}
	} // end of function updatePlayer
	
	/*
	 * update deck list
	 * adds a new deck if the deckID is not known
	 */
	private void updateDeck(int id, Object[] deck) {
		if (id-1 >= currentDecks.length) {
			currentDecks = addNew(id, currentDecks, deck);
		} else {
			// for loop to update the deck's row
			for(int i = 5; i < 52; i++) {
				currentDecks[id-1][i] = Integer.parseInt((currentDecks[id-1][i].toString())) + Integer.parseInt(deck[i].toString());
			} // end of for loop
		}
	} // end of function updateDeck
	
	/*
	 * update game list
	 * adds a new game if the player/deck combination is not known
	 */
	private void updateGame(int id, Object[] game) {
		if (id >= currentGames.length) {
			currentGames = addNew(id, currentGames, game);
		} else {
			// for loop to update the deck's row
			for(int i = 2; i < 14; i++) {
				currentGames[id][i] = Integer.parseInt((currentGames[id][i].toString())) + Integer.parseInt(game[i].toString());
			} // end of for loop
		}
	} // end of function updateGame

	/*
	 * adds a new object to a table
	 */
	private Object[][] addNew(int id, Object[][] data, Object[] newObject) {
		Object[][] newData = new Object[data.length+1][data[0].length+1];
		
		// for loop to add each row to the table
		for(int i = 0; i < data.length; i++) {
			newData[i] = data[i];
		} // end of for loop
		
		newData[data.length] = newObject;
		
		return newData;
	} // end of function addNew
} // end of class dataStorage