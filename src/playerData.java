import java.util.Scanner;

// playerData class created by Garrett Chapman
// last updated 05/12/2020
// deals with data about players taken from the database

class playerData {
	// constructs
	sqlEdit database;
	Scanner user;
	
	// list of players and their information
	Object[][] rawData;
	Object[][] playerList;
	Object[] winner = new Object[45];
	
	// menu stuff
	String[] commands = {
		""	
	};
	
	// constructor
	public playerData(sqlEdit server, Scanner in) {
		database = server;
		user = in;
		makePlayerList();
		playerMenu();
		
	} // end of constructor
	
	/*
	 * function to create the player list
	 */
	private void makePlayerList() {
		rawData = database.getPlayerData();
		playerList = new Object[rawData.length][45];
		
		// for loop to turn raw data into data that can be displayed
		for (int i = 0; i < playerList.length; i++) {
			if(i == 0) {
				// get average winner data
				
				winner[0] = rawData[i][1];		// player name
				winner[1] = getRatio(i, 6, 2);	// average player number
				winner[2] = rawData[i][2];		// total games played
				winner[3] = getRatio(i, 3, 2);	// percent of fun games
				winner[4] = getRatio(i, 4, 2);	// percent of eh games
				winner[5] = getRatio(i, 5, 2);	// percent of unfun games
				
				// number cards kept in hand
				int keptCards = 0;
				for (int k = 16; k < 22; k++) {
					keptCards += Integer.parseInt(rawData[i][k].toString());
				} // end of for loop to determine the total number of cards kept
				
				// stuff about cards kept in hand
				winner[6] = (double)keptCards / (double)Integer.parseInt(rawData[i][2].toString()); 				// average starting hand size
				winner[7] = getRatioComplex(i, 8, (keptCards - Integer.parseInt(rawData[i][11].toString())));	// average CMC of cards kept in hand
				winner[8] = getRatioComplex(i, 9, keptCards);	// percent of artifacts kept
				winner[8] = getRatioComplex(i, 10, keptCards);	// percent of creatures kept
				winner[10] = getRatioComplex(i, 11, keptCards);	// percent of lands kept
				winner[11] = getRatioComplex(i, 12, keptCards);	// percent of enchantments kept
				winner[12] = getRatioComplex(i, 13, keptCards);	// percent of instants kept
				winner[13] = getRatioComplex(i, 14, keptCards);	// percent of sorceries kept
				winner[14] = getRatioComplex(i, 15, keptCards);	// percent of planeswalkers kept
				winner[15] = getRatioComplex(i, 16, keptCards);	// percent of mana cards kept
				winner[16] = getRatioComplex(i, 17, keptCards);	// percent of draw cards kept
				winner[17] = getRatioComplex(i, 18, keptCards);	// percent of interaction cards kept
				winner[18] = getRatioComplex(i, 19, keptCards);	// percent of threat cards kept
				winner[19] = getRatioComplex(i, 20, keptCards);	// percent of combo cards kept
				winner[20] = getRatioComplex(i, 21, keptCards);	// percent of other cards kept
				
				// stuff about mulligans
				winner[21] = getRatio(i, 22, 2);		// average mulligans
				winner[22] = getRatio(i, 23, 2);		// average number of cards pitched
				winner[23] = getRatioComplex(i, 24, (Integer.parseInt(rawData[i][23].toString()) - Integer.parseInt(rawData[i][27].toString()))); // average CMC of cards pitched
				winner[24] = getRatio(i, 25, 23);	// percent of artifacts pitched
				winner[25] = getRatio(i, 26, 23);	// percent of creatures pitched
				winner[26] = getRatio(i, 27, 23);	// percent of lands pitched
				winner[27] = getRatio(i, 28, 23);	// percent of enchantments pitched
				winner[28] = getRatio(i, 29, 23);	// percent of instants pitched
				winner[29] = getRatio(i, 30, 23);	// percent of sorceries pitched
				winner[30] = getRatio(i, 31, 23);	// percent of planeswalkers pitched
				winner[31] = getRatio(i, 32, 23);	// percent of mana cards pitched
				winner[32] = getRatio(i, 33, 23);	// percent of draw cards pitched
				winner[33] = getRatio(i, 34, 23);	// percent of interaction cards pitched
				winner[34] = getRatio(i, 35, 23);	// percent of threat cards pitched
				winner[35] = getRatio(i, 36, 23);	// percent of combo cards pitched
				winner[36] = getRatio(i, 37, 23);	// percent of other cards pitched
				
				// stuff about end of game
				winner[37] = getRatio(i, 7, 2);		// scoop rate
				winner[38] = getRatio(i, 38, 2);		// win rate
				winner[39] = getRatio(i, 39, 38);	// percent of aggro wins
				winner[40] = getRatio(i, 40, 38);	// percent of aetherflux reservoir wins
				winner[41] = getRatio(i, 41, 38);	// percent of laboratory maniac wins
				winner[42] = getRatio(i, 42, 38);	// percent of other combo wins
				winner[43] = getRatio(i, 43, 38);	// percent of wins via opponents scooping
				winner[44] = getRatio(i, 44, 38);	// percent of other wins
			} else {
				// actual players
				playerList[i][0] = rawData[i][1];		// player name
				playerList[i][1] = getRatio(i, 6, 2);	// average player number
				playerList[i][2] = rawData[i][2];		// total games played
				playerList[i][3] = getRatio(i, 3, 2);	// percent of fun games
				playerList[i][4] = getRatio(i, 4, 2);	// percent of eh games
				playerList[i][5] = getRatio(i, 5, 2);	// percent of unfun games
				
				// number cards kept in hand
				int keptCards = 0;
				for (int k = 16; k < 22; k++) {
					keptCards += Integer.parseInt(rawData[i][k].toString());
				} // end of for loop to determine the total number of cards kept
				
				// stuff about cards kept in hand
				playerList[i][6] = (double)keptCards / (double)Integer.parseInt(rawData[i][2].toString()); 				// average starting hand size
				playerList[i][7] = getRatioComplex(i, 8, (keptCards - Integer.parseInt(rawData[i][11].toString())));	// average CMC of cards kept in hand
				playerList[i][8] = getRatioComplex(i, 9, keptCards);	// percent of artifacts kept
				playerList[i][8] = getRatioComplex(i, 10, keptCards);	// percent of creatures kept
				playerList[i][10] = getRatioComplex(i, 11, keptCards);	// percent of lands kept
				playerList[i][11] = getRatioComplex(i, 12, keptCards);	// percent of enchantments kept
				playerList[i][12] = getRatioComplex(i, 13, keptCards);	// percent of instants kept
				playerList[i][13] = getRatioComplex(i, 14, keptCards);	// percent of sorceries kept
				playerList[i][14] = getRatioComplex(i, 15, keptCards);	// percent of planeswalkers kept
				playerList[i][15] = getRatioComplex(i, 16, keptCards);	// percent of mana cards kept
				playerList[i][16] = getRatioComplex(i, 17, keptCards);	// percent of draw cards kept
				playerList[i][17] = getRatioComplex(i, 18, keptCards);	// percent of interaction cards kept
				playerList[i][18] = getRatioComplex(i, 19, keptCards);	// percent of threat cards kept
				playerList[i][19] = getRatioComplex(i, 20, keptCards);	// percent of combo cards kept
				playerList[i][20] = getRatioComplex(i, 21, keptCards);	// percent of other cards kept
				
				// stuff about mulligans
				playerList[i][21] = getRatio(i, 22, 2);		// average mulligans
				playerList[i][22] = getRatio(i, 23, 2);		// average number of cards pitched
				playerList[i][23] = getRatioComplex(i, 24, (Integer.parseInt(rawData[i][23].toString()) - Integer.parseInt(rawData[i][27].toString()))); // average CMC of cards pitched
				playerList[i][24] = getRatio(i, 25, 23);	// percent of artifacts pitched
				playerList[i][25] = getRatio(i, 26, 23);	// percent of creatures pitched
				playerList[i][26] = getRatio(i, 27, 23);	// percent of lands pitched
				playerList[i][27] = getRatio(i, 28, 23);	// percent of enchantments pitched
				playerList[i][28] = getRatio(i, 29, 23);	// percent of instants pitched
				playerList[i][29] = getRatio(i, 30, 23);	// percent of sorceries pitched
				playerList[i][30] = getRatio(i, 31, 23);	// percent of planeswalkers pitched
				playerList[i][31] = getRatio(i, 32, 23);	// percent of mana cards pitched
				playerList[i][32] = getRatio(i, 33, 23);	// percent of draw cards pitched
				playerList[i][33] = getRatio(i, 34, 23);	// percent of interaction cards pitched
				playerList[i][34] = getRatio(i, 35, 23);	// percent of threat cards pitched
				playerList[i][35] = getRatio(i, 36, 23);	// percent of combo cards pitched
				playerList[i][36] = getRatio(i, 37, 23);	// percent of other cards pitched
				
				// stuff about end of game
				playerList[i][37] = getRatio(i, 7, 2);		// scoop rate
				playerList[i][38] = getRatio(i, 38, 2);		// win rate
				playerList[i][39] = getRatio(i, 39, 38);	// percent of aggro wins
				playerList[i][40] = getRatio(i, 40, 38);	// percent of aetherflux reservoir wins
				playerList[i][41] = getRatio(i, 41, 38);	// percent of laboratory maniac wins
				playerList[i][42] = getRatio(i, 42, 38);	// percent of other combo wins
				playerList[i][43] = getRatio(i, 43, 38);	// percent of wins via opponents scooping
				playerList[i][44] = getRatio(i, 44, 38);	// percent of other wins
			}
		} // end of for loop
	} // end of function makePlayerList
	
	/*
	 * function to create player menu
	 */
	private void playerMenu() {
		System.out.println(edhData.divider);
		
		System.out.println("edhData player information menu");
		
		System.out.println(edhData.divider);
	} // end of function playerMenu
	
	/*
	 * function to get a ratio for the data table
	 * only takes data points from the raw data
	 */
	private double getRatio(int i, int j, int k) {
		return (double)Integer.parseInt(rawData[i][j].toString()) / (double)Integer.parseInt(rawData[i][k].toString());
	} // end of function getRatio
	
	/*
	 * function to get a ratio for the data table
	 * uses values other than raw data
	 */
	private double getRatioComplex(int i, int j, int divisor) {
		return (double)Integer.parseInt(rawData[i][j].toString()) / (double)divisor;
	} // end of function getRatioComplex
} // end of class playerData