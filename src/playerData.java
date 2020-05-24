import java.util.NoSuchElementException;
import java.util.Scanner;

// playerData class created by Garrett Chapman
// created 05/13/2020
// last updated 05/24/2020
// deals with data about players taken from the database

class playerData {
	// constructs
	private sqlEdit database;
	private Scanner user;
	
	// list of players and their information
	private Object[][] rawData;
	private Object[][] playerList;
	private Object[] winner = new Object[47];
	private int sortColumn = 0;
	private String columnName = "ID";
	
	// menu stuff
	private String[] commands = {
		"back",
		"categories",
		"exit",
		"help",
		"high",
		"list",
		"low",
		"sort",
		"view"
	};
	private String[] categories = {
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
		
		// while loop to allow functionality
		loop: while(true) {
			playerMenu();
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
				case "high":
					getTopPlayers();
					break;
				case "list":
					getPlayerList();
					break;
				case "low":
					getBottomPlayers();
					break;
				case "sort":
					sortPlayers();
					break;
				case "view":
					viewPlayer();
					break;
				default:
					System.out.println(edhData.divider);
					System.out.println("Unknown command.");
				}
			} catch(NoSuchElementException e) {
				System.out.println("ERROR: bad scan");
				System.exit(0);
			}
			
			// clear console
		} // end of while loop
	} // end of function start
	
	/*
	 * function to create the player list
	 */
	private void makePlayerList() {
		rawData = database.getPlayerData();
		playerList = new Object[(rawData.length-1)][47];
		
		// for loop to turn raw data into data that can be displayed
		for (int i = 0; i < rawData.length; i++) {
			if(i == 0) {
				// get average winner data
				winner[0] = rawData[i][0]; // player ID
				
				winner[1] = rawData[i][1];			// player name
				winner[2] = getRatio(i, 6, 2);		// average player number
				winner[3] = rawData[i][2];			// total games played
				winner[4] = getRatio(i, 3, 2)*100;	// percent of fun games
				winner[5] = getRatio(i, 4, 2)*100;	// percent of eh games
				winner[6] = getRatio(i, 5, 2)*100;	// percent of unfun games
				
				// number cards kept in hand
				int keptCards = 0;
				for (int k = 16; k < 22; k++) {
					keptCards += Integer.parseInt(rawData[i][k].toString());
				} // end of for loop to determine the total number of cards kept
				
				// stuff about cards kept in hand
				winner[7] = (double)keptCards / (double)Integer.parseInt(rawData[i][2].toString()); 				// average starting hand size
				winner[8] = getRatioComplex(i, 8, (keptCards - Integer.parseInt(rawData[i][11].toString())));	// average CMC of cards kept in hand
				winner[9] = getRatioComplex(i, 9, keptCards)*100;	// percent of artifacts kept
				winner[10] = getRatioComplex(i, 10, keptCards)*100;	// percent of creatures kept
				winner[11] = getRatioComplex(i, 11, keptCards)*100;	// percent of lands kept
				winner[12] = getRatioComplex(i, 12, keptCards)*100;	// percent of enchantments kept
				winner[13] = getRatioComplex(i, 13, keptCards)*100;	// percent of instants kept
				winner[14] = getRatioComplex(i, 14, keptCards)*100;	// percent of sorceries kept
				winner[15] = getRatioComplex(i, 15, keptCards)*100;	// percent of planeswalkers kept
				winner[16] = getRatioComplex(i, 16, keptCards)*100;	// percent of mana cards kept
				winner[17] = getRatioComplex(i, 17, keptCards)*100;	// percent of draw cards kept
				winner[18] = getRatioComplex(i, 18, keptCards)*100;	// percent of interaction cards kept
				winner[19] = getRatioComplex(i, 19, keptCards)*100;	// percent of threat cards kept
				winner[20] = getRatioComplex(i, 20, keptCards)*100;	// percent of combo cards kept
				winner[21] = getRatioComplex(i, 21, keptCards)*100;	// percent of other cards kept
				
				// stuff about mulligans
				winner[22] = getRatio(i, 22, 2);		// average mulligans
				winner[23] = getRatio(i, 23, 2);		// average number of cards pitched
				winner[24] = getRatioComplex(i, 24, (Integer.parseInt(rawData[i][23].toString()) - Integer.parseInt(rawData[i][27].toString()))); // average CMC of cards pitched
				winner[25] = getRatio(i, 25, 23)*100;	// percent of artifacts pitched
				winner[26] = getRatio(i, 26, 23)*100;	// percent of creatures pitched
				winner[27] = getRatio(i, 27, 23)*100;	// percent of lands pitched
				winner[28] = getRatio(i, 28, 23)*100;	// percent of enchantments pitched
				winner[29] = getRatio(i, 29, 23)*100;	// percent of instants pitched
				winner[30] = getRatio(i, 30, 23)*100;	// percent of sorceries pitched
				winner[31] = getRatio(i, 31, 23)*100;	// percent of planeswalkers pitched
				winner[32] = getRatio(i, 32, 23)*100;	// percent of mana cards pitched
				winner[33] = getRatio(i, 33, 23)*100;	// percent of draw cards pitched
				winner[34] = getRatio(i, 34, 23)*100;	// percent of interaction cards pitched
				winner[35] = getRatio(i, 35, 23)*100;	// percent of threat cards pitched
				winner[36] = getRatio(i, 36, 23)*100;	// percent of combo cards pitched
				winner[37] = getRatio(i, 37, 23)*100;	// percent of other cards pitched
				
				// stuff about end of game
				winner[38] = getRatio(i, 7, 2)*100;		// scoop rate
				winner[39] = getRatio(i, 38, 2)*100;	// win rate
				winner[40] = getRatio(i, 39, 38)*100;	// percent of aggro wins
				winner[41] = getRatio(i, 40, 38)*100;	// percent of aetherflux reservoir wins
				winner[42] = getRatio(i, 41, 38)*100;	// percent of laboratory maniac wins
				winner[43] = getRatio(i, 42, 38)*100;	// percent of other combo wins
				winner[44] = getRatio(i, 43, 38)*100;	// percent of wins via opponents scooping
				winner[45] = getRatio(i, 44, 38)*100;	// percent of other wins
				
				winner[46] = getRatio(i, 45, 2);	// average number of opponents per game (this doesn't matter for the winner, but it's being set to avoid NullPointerExceptions
			} else {
				// actual players
				playerList[i-1][0] = rawData[i][0]; // player ID
				
				playerList[i-1][1] = rawData[i][1];		// player name
				playerList[i-1][2] = getRatio(i, 6, 2);	// average player number
				playerList[i-1][3] = rawData[i][2];		// total games played
				
				playerList[i-1][4] = getRatio(i, 3, 2)*100;	// percent of fun games
				playerList[i-1][5] = getRatio(i, 4, 2)*100;	// percent of eh games
				playerList[i-1][6] = getRatio(i, 5, 2)*100;	// percent of unfun games
				
				// number cards kept in hand
				int keptCards = 0;
				for (int k = 16; k < 22; k++) {
					keptCards += Integer.parseInt(rawData[i][k].toString());
				} // end of for loop to determine the total number of cards kept
				
				// stuff about cards kept in hand
				playerList[i-1][7] = (double)keptCards / (double)Integer.parseInt(rawData[i][2].toString()); 			// average starting hand size
				playerList[i-1][8] = getRatioComplex(i, 8, (keptCards - Integer.parseInt(rawData[i][11].toString())));	// average CMC of cards kept in hand
				
				playerList[i-1][9] = getRatioComplex(i, 9, keptCards)*100;		// percent of artifacts kept
				playerList[i-1][10] = getRatioComplex(i, 10, keptCards)*100;	// percent of creatures kept
				playerList[i-1][11] = getRatioComplex(i, 11, keptCards)*100;	// percent of lands kept
				playerList[i-1][12] = getRatioComplex(i, 12, keptCards)*100;	// percent of enchantments kept
				playerList[i-1][13] = getRatioComplex(i, 13, keptCards)*100;	// percent of instants kept
				playerList[i-1][14] = getRatioComplex(i, 14, keptCards)*100;	// percent of sorceries kept
				playerList[i-1][15] = getRatioComplex(i, 15, keptCards)*100;	// percent of planeswalkers kept
				
				playerList[i-1][16] = getRatioComplex(i, 16, keptCards)*100;	// percent of mana cards kept
				playerList[i-1][17] = getRatioComplex(i, 17, keptCards)*100;	// percent of draw cards kept
				playerList[i-1][18] = getRatioComplex(i, 18, keptCards)*100;	// percent of interaction cards kept
				playerList[i-1][19] = getRatioComplex(i, 19, keptCards)*100;	// percent of threat cards kept
				playerList[i-1][20] = getRatioComplex(i, 20, keptCards)*100;	// percent of combo cards kept
				playerList[i-1][21] = getRatioComplex(i, 21, keptCards)*100;	// percent of other cards kept
				
				// stuff about mulligans
				playerList[i-1][22] = getRatio(i, 22, 2);	// average mulligans per game
				playerList[i-1][23] = getRatio(i, 23, 2);	// average number of cards pitched per game
				playerList[i-1][24] = getRatioComplex(i, 24, (Integer.parseInt(rawData[i][23].toString()) - Integer.parseInt(rawData[i][27].toString()))); // average CMC of cards pitched
				
				playerList[i-1][25] = getRatio(i, 25, 23)*100;	// percent of artifacts pitched
				playerList[i-1][26] = getRatio(i, 26, 23)*100;	// percent of creatures pitched
				playerList[i-1][27] = getRatio(i, 27, 23)*100;	// percent of lands pitched
				playerList[i-1][28] = getRatio(i, 28, 23)*100;	// percent of enchantments pitched
				playerList[i-1][29] = getRatio(i, 29, 23)*100;	// percent of instants pitched
				playerList[i-1][30] = getRatio(i, 30, 23)*100;	// percent of sorceries pitched
				playerList[i-1][31] = getRatio(i, 31, 23)*100;	// percent of planeswalkers pitched
				
				playerList[i-1][32] = getRatio(i, 32, 23)*100;	// percent of mana cards pitched
				playerList[i-1][33] = getRatio(i, 33, 23)*100;	// percent of draw cards pitched
				playerList[i-1][34] = getRatio(i, 34, 23)*100;	// percent of interaction cards pitched
				playerList[i-1][35] = getRatio(i, 35, 23)*100;	// percent of threat cards pitched
				playerList[i-1][36] = getRatio(i, 36, 23)*100;	// percent of combo cards pitched
				playerList[i-1][37] = getRatio(i, 37, 23)*100;	// percent of other cards pitched
				
				// stuff about end of game
				playerList[i-1][38] = getRatio(i, 7, 2)*100;	// scoop rate
				playerList[i-1][39] = getRatio(i, 38, 2)*100;	// win rate
				playerList[i-1][40] = getRatio(i, 39, 38)*100;	// percent of aggro wins
				playerList[i-1][41] = getRatio(i, 40, 38)*100;	// percent of aetherflux reservoir wins
				playerList[i-1][42] = getRatio(i, 41, 38)*100;	// percent of laboratory maniac wins
				playerList[i-1][43] = getRatio(i, 42, 38)*100;	// percent of other combo wins
				playerList[i-1][44] = getRatio(i, 43, 38)*100;	// percent of wins via opponents scooping
				playerList[i-1][45] = getRatio(i, 44, 38)*100;	// percent of other wins
				
				playerList[i-1][46] = (1/(getRatio(i, 45, 2)+1))*100;	// target winrate
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
		System.out.println("Total Games Played: " + edhData.totalGames);
		System.out.println(edhData.divider);
	} // end of function playerMenu
	
	/*
	 * function to list sorting categories
	 * called by categories command
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
	 * displays the player help menu
	 * called by help command
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
		System.out.println("high");
		System.out.println("     Asks for a number and lists that many top players in the current category.");
		System.out.println("list");
		System.out.println("     Lists all players in the database.");
		System.out.println("low");
		System.out.println("     Asks for a number and lists that many bottom players in the current category.");
		System.out.println("sort");
		System.out.println("     Asks for a category and sorts all players in the database by that category.");
		System.out.println("view");
		System.out.println("     Asks for a player's name and displays more specific information about that player.");
		System.out.println();
		System.out.println(edhData.divider);
		System.out.print("Press any button to continue.");
		user.nextLine();
		
		System.out.println(edhData.divider);
	} // end of function playerHelp
	
	/*
	 * function to get the top players
	 * asks the user for the number of players to show
	 * called by high command
	 */
	private void getTopPlayers() {
		System.out.println(edhData.divider);
		
		if(sortColumn == 0) { // does not list if they are not sorted
			System.out.println("Please sort the players first using the sort command.");
			System.out.println(edhData.divider);
			return;
		}
		
		System.out.print("Please enter a number: ");
		String input = user.nextLine();
		int number;
		
		// loop to validate input
		loop: while(true) {
			try {
				number = Integer.parseInt(input);
				break loop;
			} catch (NumberFormatException e) {
				System.out.print("");
				input = user.nextLine();
			}
		} // end of while loop
		
		if(number > playerList.length) {
			number = playerList.length;
		}
		
		if (sortColumn == 2) {
			printFirstPlayers(number);
		} else {
			printLastPlayers(number);
		}
	} // end of function getTopPlayers
	
	/*
	 * function to list all known players
	 * called by list command
	 */
	private void getPlayerList() {
		System.out.println(edhData.divider);
		arraySort.sort(playerList, 0);
		
		System.out.println("Players in the database:");
		// for loop to print each player
		for(int i = 0; i < playerList.length; i++) {
			System.out.println("     " + (i+1) + ". " + playerList[i][1]);
		} // end of for loop
		System.out.println();
		
		arraySort.sort(playerList, sortColumn);
		System.out.println(edhData.divider);
		System.out.print("Press any button to continue.");
		user.nextLine();
		
		System.out.println(edhData.divider);
	} // end of function getPlayerList
	
	/*
	 * function to get the bottom players
	 * asks the user for the number of players to show
	 * called by low command
	 */
	private void getBottomPlayers() {
		System.out.println(edhData.divider);
		
		if(sortColumn == 0) {
			System.out.println("Please sort the players first using the sort command.");
			System.out.println(edhData.divider);
			return;
		}
		
		System.out.print("Please enter a number: ");
		String input = user.nextLine();
		int number;
		
		// loop to validate input
		loop: while(true) {
			try {
				number = Integer.parseInt(input);
				break loop;
			} catch (NumberFormatException e) {
				System.out.print("");
				input = user.nextLine();
			}
		} // end of while loop
		
		if(number > playerList.length) {
			number = playerList.length;
		}
		
		if (sortColumn == 2) {
			printLastPlayers(number);
		} else {
			printFirstPlayers(number);
		}
	} // end of function getBottomPlayers
	
	/*
	 * function to sort players by a category
	 * asks the user which category to sort by
	 * called by sort command
	 */
	private void sortPlayers() {
		categoryList();
		System.out.println();
		System.out.print("Please enter a category: ");
		switch(user.nextLine()) {
		case "cmc":
			System.out.println("Sorting players by average opening hand CMC...");
			sortColumn = 8;
			columnName = "Average Opening Hand CMC";
			System.out.println("Finished sorting.");
			break;
		case "fun":
			System.out.println("Sorting players by percentage of fun games...");
			sortColumn = 4;
			columnName = "Fun Games (%)";
			System.out.println("Finished sorting.");
			break;
		case "games":
			System.out.println("Sorting players by total games played...");
			sortColumn = 3;
			columnName = "Total Games";
			System.out.println("Finished sorting.");
			break;
		case "mulligans":
			System.out.println("Sorting players by average mulligans per game...");
			sortColumn = 22;
			columnName = "Average Mulligans per Game";
			System.out.println("Finished sorting.");
			break;
		case "number":
			System.out.println("Sorting players by average starting turn...");
			sortColumn = 2;
			columnName = "Average Starting Turn";
			System.out.println("Finished sorting.");
			break;
		case "scoops":
			System.out.println("Sorting players by scoop rate...");
			sortColumn = 38;
			columnName = "Scoop Rate (%)";
			System.out.println("Finished sorting.");
			break;
		case "wins":
			System.out.println("Sorting players by win rate...");
			sortColumn = 39;
			columnName = "Win Rate (%)";
			System.out.println("Finished sorting.");
			break;
		default:
			System.out.println();
			System.out.println("Unknown category.");
		}
		
		arraySort.sort(playerList, sortColumn);
		System.out.println(edhData.divider);
	} // end of function sortPlayers
	
	/*
	 * function to view a player's information
	 * asks the user for the player's name
	 * called by view command
	 */
	private void viewPlayer() {
		boolean found = false;
		
		// get the desired commander
		System.out.print("Please enter the player's name: ");
		String player = fixString(user.nextLine());
		
		if(player.equals("Winner")) {
			found = true;
			
			playerScreen(winner);
		}
		
		// for loop to find the right deck
		for (int i = 0; i < playerList.length; i++) {
			String checkPlayer = playerList[i][1].toString().replaceAll("\\P{Print}", "").trim();
			
			// the deck must have the same commander and theme
			if(((player).equals(checkPlayer))) {
				found = true;
				System.out.println(edhData.divider);
				playerScreen(playerList[i]);
			}
		} // end of for loop
		
		if (!found) {
			System.out.println("There is no player in the database with the desired name.");
		}
	} // end of function viewPlayer
	
	// functions called by other functions
	
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
	 * function to print the first X players where X is the number selected by the user
	 */
	private void printFirstPlayers(int number) {
		System.out.println(edhData.divider);
		System.out.println();
		System.out.format("%-19s", "Player");
		System.out.print("|" + columnName);
		System.out.println();
		System.out.println("===================|==========================");
		
		// for loop to print each player's information
		for(int i = 0; i < number; i++) {
			String line = (i+1) + ". " + playerList[i][1].toString();
			System.out.format("%-19s", line);
			System.out.print("|" + playerList[i][sortColumn].toString());
			System.out.println();
			System.out.println("-------------------|--------------------------");
		} // end of for loop
		
		System.out.println();
		System.out.println(edhData.divider);
		System.out.print("Press any button to continue.");
		user.nextLine();
		
		System.out.println(edhData.divider);
	} // end of function printFirstPlayers
	
	/*
	 * function to print the last X players where X is the number selected by the user
	 */
	private void printLastPlayers(int number) {
		System.out.println(edhData.divider);
		System.out.println();
		System.out.format("%-19s", "Player");
		System.out.print("|" + columnName);
		System.out.println();
		System.out.println("===================|==========================");
		
		// for loop to print each player's information
		for(int i = 1; i <= number; i++) {
			String line = i + ". " + playerList[playerList.length-i][1].toString();
			System.out.format("%-19s", line);
			System.out.print("|" + playerList[playerList.length-i][sortColumn].toString());
			System.out.println();
			System.out.println("-------------------|--------------------------");
		} // end of for loop
		
		System.out.println();
		System.out.println(edhData.divider);
		System.out.print("Press any button to continue.");
		user.nextLine();
		
		System.out.println(edhData.divider);
	} // end of function printLastPlayers
	
	/*
	 * function to remove apostrophes
	 * SQL does not like having apostrophes written to it
	 */
	private String fixString(String input) {
		input = input.replace("'", "");
		return input;
	} // end of function fixString
	
	/*
	 * function to create the player screen
	 * prints specific information about a player
	 */
	private void playerScreen(Object[] row) {
		System.out.println(row[1].toString().trim());
		System.out.println();
		
		System.out.println(edhData.miniDivider); // games and fun
		System.out.println("Total Games: " + row[3].toString());
		System.out.println();
		
		System.out.println();
		System.out.println("       Fun (%)     |       Eh (%)      |      Unfun (%)    ");
		System.out.println("===================|===================|===================");
		System.out.format("%-19s", row[4].toString());
		System.out.print("|");
		System.out.format("%-19s", row[5].toString());
		System.out.print("|");
		System.out.format("%-19s", row[6].toString());
		System.out.println();
		System.out.println("-------------------|-------------------|-------------------");
		System.out.println();
		
		System.out.println(edhData.miniDivider); // winrate
		System.out.println("Target win percentage: " + row[46].toString());
		System.out.println("Actual win percentage: " + row[39].toString());
		System.out.println();
		
		System.out.println("Types of wins:");
		System.out.println();
		System.out.println("     Aggro (%)     |   Aetherflux (%)  |    Lab Man (%)    |     Combo (%)     |     Scoops (%)    |      Other(%)     ");
		System.out.println("===================|===================|===================|===================|===================|===================");
		System.out.format("%-19s", row[40].toString());
		System.out.print("|");
		System.out.format("%-19s", row[41].toString());
		System.out.print("|");
		System.out.format("%-19s", row[42].toString());
		System.out.print("|");
		System.out.format("%-19s", row[43].toString());
		System.out.print("|");
		System.out.format("%-19s", row[44].toString());
		System.out.print("|");
		System.out.format("%-19s", row[45].toString());
		System.out.println();
		System.out.println("-------------------|-------------------|-------------------|-------------------|-------------------|-------------------");
		System.out.println();
		
		System.out.println(edhData.miniDivider); // kept cards
		System.out.println("Cards kept in hand:");
		System.out.println();
		System.out.println("   Artifact (%)    |    Creature (%)   |      Land (%)     |  Enchantment (%)  |    Instant (%)    |    Sorcery (%)    |  Planeswalker (%) ");
		System.out.println("===================|===================|===================|===================|===================|===================|===================");
		System.out.format("%-19s", row[9].toString());
		System.out.print("|");
		System.out.format("%-19s", row[10].toString());
		System.out.print("|");
		System.out.format("%-19s", row[11].toString());
		System.out.print("|");
		System.out.format("%-19s", row[12].toString());
		System.out.print("|");
		System.out.format("%-19s", row[13].toString());
		System.out.print("|");
		System.out.format("%-19s", row[14].toString());
		System.out.print("|");
		System.out.format("%-19s", row[15].toString());
		System.out.println();
		System.out.println("-------------------|-------------------|-------------------|-------------------|-------------------|-------------------|-------------------");
		System.out.println();
		
		System.out.println("      Mana (%)     |      Draw (%)     |  Interaction (%)  |     Threat (%)    |     Combo (%)     |     Other (%)     ");
		System.out.println("===================|===================|===================|===================|===================|===================");
		System.out.format("%-19s", row[16].toString());
		System.out.print("|");
		System.out.format("%-19s", row[17].toString());
		System.out.print("|");
		System.out.format("%-19s", row[18].toString());
		System.out.print("|");
		System.out.format("%-19s", row[19].toString());
		System.out.print("|");
		System.out.format("%-19s", row[20].toString());
		System.out.print("|");
		System.out.format("%-19s", row[21].toString());
		System.out.println();
		System.out.println("-------------------|-------------------|-------------------|-------------------|-------------------|-------------------");
		System.out.println();
		
		System.out.println("Average CMC of cards kept: " + row[8].toString());
		
		System.out.println(edhData.miniDivider); // pitched cards
		System.out.println("Average mulligans per game: " + row[22].toString());
		System.out.println();
		System.out.println("Cards pitched:");
		System.out.println();
		System.out.println("   Artifact (%)    |    Creature (%)   |      Land (%)     |  Enchantment (%)  |    Instant (%)    |    Sorcery (%)    |  Planeswalker (%) ");
		System.out.println("===================|===================|===================|===================|===================|===================|===================");
		System.out.format("%-19s", row[25].toString());
		System.out.print("|");
		System.out.format("%-19s", row[26].toString());
		System.out.print("|");
		System.out.format("%-19s", row[27].toString());
		System.out.print("|");
		System.out.format("%-19s", row[28].toString());
		System.out.print("|");
		System.out.format("%-19s", row[29].toString());
		System.out.print("|");
		System.out.format("%-19s", row[30].toString());
		System.out.print("|");
		System.out.format("%-19s", row[31].toString());
		System.out.println();
		System.out.println("-------------------|-------------------|-------------------|-------------------|-------------------|-------------------|-------------------");
		System.out.println();
		
		System.out.println("      Mana (%)     |      Draw (%)     |  Interaction (%)  |     Threat (%)    |     Combo (%)     |     Other (%)     ");
		System.out.println("===================|===================|===================|===================|===================|===================");
		System.out.format("%-19s", row[32].toString());
		System.out.print("|");
		System.out.format("%-19s", row[33].toString());
		System.out.print("|");
		System.out.format("%-19s", row[34].toString());
		System.out.print("|");
		System.out.format("%-19s", row[35].toString());
		System.out.print("|");
		System.out.format("%-19s", row[36].toString());
		System.out.print("|");
		System.out.format("%-19s", row[37].toString());
		System.out.println();
		System.out.println("-------------------|-------------------|-------------------|-------------------|-------------------|-------------------");
		System.out.println();
		
		System.out.println("Average CMC of cards pitched: " + row[24].toString());
		
		System.out.println(edhData.divider);
		System.out.print("Press any button to continue.");
		user.nextLine();
	} // end of function playerScreen
} // end of class playerData