import java.util.NoSuchElementException;
import java.util.Scanner;

// playerData class created by Garrett Chapman
// created 05/13/2020
// last updated 05/13/2020
// deals with data about players taken from the database

class playerData {
	// constructs
	sqlEdit database;
	Scanner user;
	
	// list of players and their information
	Object[][] rawData;
	Object[][] playerList;
	Object[] winner = new Object[46];
	private int sortColumn = 0;
	
	// menu stuff
	String[] commands = {
		"back",
		"categories",
		"exit",
		"help",
		"list",
		"sort"
	};
	String[] categories = {
		"cmc",
		"fun",
		"games",
		"mulligans",
		"number",
		"scoops",
		"wins"
	};
	
	// constructor
	public playerData(sqlEdit server, Scanner in) {
		database = server;
		user = in;
	} // end of constructor
	
	/*
	 * function to run the player menu
	 */
	public void start() {
		makePlayerList();
		playerMenu();
		
		// while loop to allow functionality
		loop: while(true) {
			playerCommands();
			try {
				System.out.print("Please enter a command: ");
				switch(user.nextLine()) {
				case "back":
					break loop;
				case "categories":
					categoryList();
					break;
				case "exit":
					System.out.println(edhData.divider);
					System.out.println("Program closing.  Thank you.");
					System.out.println(edhData.divider);
					System.exit(0);
				case "help":
					playerHelp();
					break;
				case "list":
					getPlayerList();
					break;
				case "sort":
					sortPlayers();
					break;
				default:
					System.out.println(edhData.divider);
					System.out.println("ERROR: unknown command.");
				}
			} catch(NoSuchElementException e) {
				System.out.println("ERROR: bad scan");
			}
		} // end of while loop
	} // end of function start
	
	/*
	 * function to create the player list
	 */
	private void makePlayerList() {
		rawData = database.getPlayerData();
		playerList = new Object[(rawData.length-1)][46];
		
		// for loop to turn raw data into data that can be displayed
		for (int i = 0; i < rawData.length; i++) {
			if(i == 0) {
				// get average winner data
				winner[0] = rawData[i][0]; // player ID
				
				winner[1] = rawData[i][1];		// player name
				winner[2] = getRatio(i, 6, 2);	// average player number
				winner[3] = rawData[i][2];		// total games played
				winner[4] = getRatio(i, 3, 2);	// percent of fun games
				winner[5] = getRatio(i, 4, 2);	// percent of eh games
				winner[6] = getRatio(i, 5, 2);	// percent of unfun games
				
				// number cards kept in hand
				int keptCards = 0;
				for (int k = 16; k < 22; k++) {
					keptCards += Integer.parseInt(rawData[i][k].toString());
				} // end of for loop to determine the total number of cards kept
				
				// stuff about cards kept in hand
				winner[7] = (double)keptCards / (double)Integer.parseInt(rawData[i][2].toString()); 				// average starting hand size
				winner[8] = getRatioComplex(i, 8, (keptCards - Integer.parseInt(rawData[i][11].toString())));	// average CMC of cards kept in hand
				winner[9] = getRatioComplex(i, 9, keptCards);	// percent of artifacts kept
				winner[10] = getRatioComplex(i, 10, keptCards);	// percent of creatures kept
				winner[11] = getRatioComplex(i, 11, keptCards);	// percent of lands kept
				winner[12] = getRatioComplex(i, 12, keptCards);	// percent of enchantments kept
				winner[13] = getRatioComplex(i, 13, keptCards);	// percent of instants kept
				winner[14] = getRatioComplex(i, 14, keptCards);	// percent of sorceries kept
				winner[15] = getRatioComplex(i, 15, keptCards);	// percent of planeswalkers kept
				winner[16] = getRatioComplex(i, 16, keptCards);	// percent of mana cards kept
				winner[17] = getRatioComplex(i, 17, keptCards);	// percent of draw cards kept
				winner[18] = getRatioComplex(i, 18, keptCards);	// percent of interaction cards kept
				winner[19] = getRatioComplex(i, 19, keptCards);	// percent of threat cards kept
				winner[20] = getRatioComplex(i, 20, keptCards);	// percent of combo cards kept
				winner[21] = getRatioComplex(i, 21, keptCards);	// percent of other cards kept
				
				// stuff about mulligans
				winner[22] = getRatio(i, 22, 2);	// average mulligans
				winner[23] = getRatio(i, 23, 2);	// average number of cards pitched
				winner[24] = getRatioComplex(i, 24, (Integer.parseInt(rawData[i][23].toString()) - Integer.parseInt(rawData[i][27].toString()))); // average CMC of cards pitched
				winner[25] = getRatio(i, 25, 23);	// percent of artifacts pitched
				winner[26] = getRatio(i, 26, 23);	// percent of creatures pitched
				winner[27] = getRatio(i, 27, 23);	// percent of lands pitched
				winner[28] = getRatio(i, 28, 23);	// percent of enchantments pitched
				winner[29] = getRatio(i, 29, 23);	// percent of instants pitched
				winner[30] = getRatio(i, 30, 23);	// percent of sorceries pitched
				winner[31] = getRatio(i, 31, 23);	// percent of planeswalkers pitched
				winner[32] = getRatio(i, 32, 23);	// percent of mana cards pitched
				winner[33] = getRatio(i, 33, 23);	// percent of draw cards pitched
				winner[34] = getRatio(i, 34, 23);	// percent of interaction cards pitched
				winner[35] = getRatio(i, 35, 23);	// percent of threat cards pitched
				winner[36] = getRatio(i, 36, 23);	// percent of combo cards pitched
				winner[37] = getRatio(i, 37, 23);	// percent of other cards pitched
				
				// stuff about end of game
				winner[38] = getRatio(i, 7, 2);		// scoop rate
				winner[39] = getRatio(i, 38, 2);	// win rate
				winner[40] = getRatio(i, 39, 38);	// percent of aggro wins
				winner[41] = getRatio(i, 40, 38);	// percent of aetherflux reservoir wins
				winner[42] = getRatio(i, 41, 38);	// percent of laboratory maniac wins
				winner[43] = getRatio(i, 42, 38);	// percent of other combo wins
				winner[44] = getRatio(i, 43, 38);	// percent of wins via opponents scooping
				winner[45] = getRatio(i, 44, 38);	// percent of other wins
			} else {
				// actual players
				playerList[i-1][0] = rawData[i][0]; // player ID
				
				playerList[i-1][1] = rawData[i][1];		// player name
				playerList[i-1][2] = getRatio(i, 6, 2);	// average player number
				playerList[i-1][3] = rawData[i][2];		// total games played
				playerList[i-1][4] = getRatio(i, 3, 2);	// percent of fun games
				playerList[i-1][5] = getRatio(i, 4, 2);	// percent of eh games
				playerList[i-1][6] = getRatio(i, 5, 2);	// percent of unfun games
				
				// number cards kept in hand
				int keptCards = 0;
				for (int k = 16; k < 22; k++) {
					keptCards += Integer.parseInt(rawData[i][k].toString());
				} // end of for loop to determine the total number of cards kept
				
				// stuff about cards kept in hand
				playerList[i-1][7] = (double)keptCards / (double)Integer.parseInt(rawData[i][2].toString()); 				// average starting hand size
				playerList[i-1][8] = getRatioComplex(i, 8, (keptCards - Integer.parseInt(rawData[i][11].toString())));	// average CMC of cards kept in hand
				playerList[i-1][9] = getRatioComplex(i, 9, keptCards);		// percent of artifacts kept
				playerList[i-1][10] = getRatioComplex(i, 10, keptCards);	// percent of creatures kept
				playerList[i-1][11] = getRatioComplex(i, 11, keptCards);	// percent of lands kept
				playerList[i-1][12] = getRatioComplex(i, 12, keptCards);	// percent of enchantments kept
				playerList[i-1][13] = getRatioComplex(i, 13, keptCards);	// percent of instants kept
				playerList[i-1][14] = getRatioComplex(i, 14, keptCards);	// percent of sorceries kept
				playerList[i-1][15] = getRatioComplex(i, 15, keptCards);	// percent of planeswalkers kept
				playerList[i-1][16] = getRatioComplex(i, 16, keptCards);	// percent of mana cards kept
				playerList[i-1][17] = getRatioComplex(i, 17, keptCards);	// percent of draw cards kept
				playerList[i-1][18] = getRatioComplex(i, 18, keptCards);	// percent of interaction cards kept
				playerList[i-1][19] = getRatioComplex(i, 19, keptCards);	// percent of threat cards kept
				playerList[i-1][20] = getRatioComplex(i, 20, keptCards);	// percent of combo cards kept
				playerList[i-1][21] = getRatioComplex(i, 21, keptCards);	// percent of other cards kept
				
				// stuff about mulligans
				playerList[i-1][22] = getRatio(i, 22, 2);	// average mulligans
				playerList[i-1][23] = getRatio(i, 23, 2);	// average number of cards pitched
				playerList[i-1][24] = getRatioComplex(i, 24, (Integer.parseInt(rawData[i][23].toString()) - Integer.parseInt(rawData[i][27].toString()))); // average CMC of cards pitched
				playerList[i-1][25] = getRatio(i, 25, 23);	// percent of artifacts pitched
				playerList[i-1][26] = getRatio(i, 26, 23);	// percent of creatures pitched
				playerList[i-1][27] = getRatio(i, 27, 23);	// percent of lands pitched
				playerList[i-1][28] = getRatio(i, 28, 23);	// percent of enchantments pitched
				playerList[i-1][29] = getRatio(i, 29, 23);	// percent of instants pitched
				playerList[i-1][30] = getRatio(i, 30, 23);	// percent of sorceries pitched
				playerList[i-1][31] = getRatio(i, 31, 23);	// percent of planeswalkers pitched
				playerList[i-1][32] = getRatio(i, 32, 23);	// percent of mana cards pitched
				playerList[i-1][33] = getRatio(i, 33, 23);	// percent of draw cards pitched
				playerList[i-1][34] = getRatio(i, 34, 23);	// percent of interaction cards pitched
				playerList[i-1][35] = getRatio(i, 35, 23);	// percent of threat cards pitched
				playerList[i-1][36] = getRatio(i, 36, 23);	// percent of combo cards pitched
				playerList[i-1][37] = getRatio(i, 37, 23);	// percent of other cards pitched
				
				// stuff about end of game
				playerList[i-1][38] = getRatio(i, 7, 2);	// scoop rate
				playerList[i-1][39] = getRatio(i, 38, 2);	// win rate
				playerList[i-1][40] = getRatio(i, 39, 38);	// percent of aggro wins
				playerList[i-1][41] = getRatio(i, 40, 38);	// percent of aetherflux reservoir wins
				playerList[i-1][42] = getRatio(i, 41, 38);	// percent of laboratory maniac wins
				playerList[i-1][43] = getRatio(i, 42, 38);	// percent of other combo wins
				playerList[i-1][44] = getRatio(i, 43, 38);	// percent of wins via opponents scooping
				playerList[i-1][45] = getRatio(i, 44, 38);	// percent of other wins
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
	 * function to print player commands
	 * separated from main menu function because it will be printed again for incorrect input and after a command
	 */
	private void playerCommands() {
		System.out.println("Known commands:");
		// for loop to add in commands
		for (int i = 0; i < commands.length; i++) {
			System.out.print("     " + commands[i]);
		} // end of for loop
		System.out.println();
		System.out.println();
	} // end of function playerCommands
	
	/*
	 * displays the player help menu
	 */
	private void playerHelp() {
		System.out.println(edhData.divider);
		System.out.println("Known commands:");
		System.out.println();
		System.out.println("back");
		System.out.println("     Returns to the main menu.");
		System.out.println("categories");
		System.out.println("     Displays sorting categories.");
		System.out.println("exit");
		System.out.println("     Quits the program.");
		System.out.println("help");
		System.out.println("     Displays the help menu.");
		System.out.println("list");
		System.out.println("     Lists all players in the database.");
		System.out.println("sort");
		System.out.println("     Asks for a category and sorts all players in the database by that category.");
		System.out.println(edhData.divider);
	} // end of function playerHelp
	
	/*
	 * function to list all known players
	 */
	private void getPlayerList() {
		System.out.println(edhData.divider);
		arraySort.sort(playerList, 0);
		
		System.out.println("Players in the database:");
		// for loop to print each player
		for(int i = 0; i < playerList.length; i++) {
			System.out.print("     " + playerList[i][1]);
		} // end of for loop
		System.out.println();
		
		arraySort.sort(playerList, sortColumn);
		System.out.println(edhData.divider);
	} // end of function getPlayerList
	
	/*
	 * function to list sorting categories
	 */
	private void categoryList() {
		System.out.println(edhData.divider);
		System.out.println("Categories:");
		// for loop to add in commands
		for (int i = 0; i < categories.length; i++) {
			System.out.print("     " + categories[i]);
		} // end of for loop
		System.out.println();
		System.out.println(edhData.divider);
	} // end of function categoryList
	
	/*
	 * function to sort players by a category
	 */
	private void sortPlayers() {
		categoryList();
		System.out.println();
		System.out.print("Please enter a category: ");
		switch(user.nextLine()) {
		case "cmc":
			System.out.println("Sorting players by average opening hand CMC...");
			sortColumn = 8;
			System.out.println("Finished sorting.");
			break;
		case "fun":
			System.out.println("Sorting players by percentage of fun games...");
			sortColumn = 4;
			System.out.println("Finished sorting.");
			break;
		case "games":
			System.out.println("Sorting players by total games played...");
			sortColumn = 3;
			System.out.println("Finished sorting.");
			break;
		case "mulligans":
			System.out.println("Sorting players by average mulligans per game...");
			sortColumn = 22;
			System.out.println("Finished sorting.");
			break;
		case "number":
			System.out.println("Sorting players by average starting turn...");
			sortColumn = 2;
			System.out.println("Finished sorting.");
			break;
		case "scoops":
			System.out.println("Sorting players by scoop rate...");
			sortColumn = 38;
			System.out.println("Finished sorting.");
			break;
		case "wins":
			System.out.println("Sorting players by win rate...");
			sortColumn = 39;
			System.out.println("Finished sorting.");
			break;
		default:
			System.out.println();
			System.out.println("ERROR: unknown category.");
		}
		
		arraySort.sort(playerList, sortColumn);
		System.out.println(edhData.divider);
	} // end of function sortPlayers
	
	/*
	 * 
	 */
	private void getTopPlayers() {
		
	} // end of function getTopPlayers
	
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