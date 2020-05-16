import java.util.NoSuchElementException;
import java.util.Scanner;

// deckData class created by Garrett Chapman
// created 05/15/2020
// last updated 05/15/2020
// deals with data about the decks taken from the database

class deckData {
	// constructs
	private sqlEdit database;
	private Scanner user;
	
	// list of decks and their information
	private Object[][] rawData;
	private Object[][] deckList;
	private int sortColumn = 0;
	private String columnName = "ID";
	
	// menu stuff
	private String[] commands = {
			"back",
			"categories",
			"exit",
			"help",
			"hide",
			"high",
			"list",
			"minimum",
			"low",
			"show",
			"sort"
	};
	private String[] categories = {
			"cmc",
			"fun",
			"games",
			"mulligans",
			"opponents",
			"wins"
	};
	private boolean showAllDecks = false;
	private int minimumGames = 5;
	
	// constructor
	public deckData(sqlEdit server, Scanner in) {
		database = server;
		user = in;
	} // end of constructor
	
	/*
	 * function to run the deck menu
	 */
	public void start() {
		makeDeckList();
		
		// while loop to allow functionality
		loop: while (true) {
			deckMenu();
			deckCommands();
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
					deckHelp();
					break;
				case "hide":
					showAllDecks = false;
				case "high":
					getTopDecks();
					break;
				case "list":
					getDeckList();
					break;
				case "low":
					getBottomDecks();
					break;
				case "minimum":
					setMinimumGames();
					break;
				case "show":
					showAllDecks = true;
					break;
				case "sort":
					sortDecks();
					break;
				default:
					System.out.println(edhData.divider);
					System.out.println("Unknown command.");
				}
			} catch(NoSuchElementException e) {
				System.out.println("ERROR: bad scan");
				System.exit(0);
			}
		} // end of while loop
	} // end of function start
	
	/*
	 * function to make the deck list
	 */
	private void makeDeckList() {
		rawData = database.getDeckData();
		deckList = new Object[rawData.length][52];
		
		// for loop to turn raw data into data that can be displayed
		for (int i = 0; i < deckList.length; i++) {
			deckList[i][0] = rawData[i][0];	// deck ID
			
			deckList[i][1] = rawData[i][1];	//	deck name
			deckList[i][2] = rawData[i][2];	// deck theme
			deckList[i][3] = rawData[i][3];	// color identity
			deckList[i][4] = rawData[i][5]; // total games played
			
			deckList[i][5] = getRatio(i, 6, 5)*100;	// percent of fun games
			deckList[i][6] = getRatio(i, 7, 5)*100;	// perfent of eh games
			deckList[i][7] = getRatio(i, 8, 5)*100;	// percent of unfun games
			
			deckList[i][8] = getRatio(i, 10, 9)*100;	// percent of opponents who had fun games against the deck
			deckList[i][9] = getRatio(i, 11, 9)*100;	// percent of opponents who had eh games against the deck
			deckList[i][10] = getRatio(i, 12, 9)*100;	// percent of opponents who had unfun games against the deck
			
			// number cards kept in hand
			int keptCards = 0;
			for (int k = 23; k < 28; k++) {
				keptCards += Integer.parseInt(rawData[i][k].toString());
			} // end of for loop to determine the total number of cards kept
			
			// stuf about cards kept in hand
			deckList[i][11] = (((double)Integer.parseInt(rawData[i][13].toString()) / (double)Integer.parseInt(rawData[i][5].toString())) / (double)Integer.parseInt(rawData[i][4].toString()))*100;	// average percent of colors in opening hand
			deckList[i][11] = (((double)Integer.parseInt(rawData[i][14].toString()) / (double)Integer.parseInt(rawData[i][5].toString())) / (double)Integer.parseInt(rawData[i][4].toString()))*100;	// average percent of colors of mana available in opening hand
			
			deckList[i][13] = (double)keptCards / (double)Integer.parseInt(rawData[i][5].toString());				// average starting hand size
			deckList[i][14] = getRatioComplex(i, 15, (keptCards - Integer.parseInt(rawData[i][18].toString())));	// average CMC of cards kept
			
			deckList[i][15] = getRatioComplex(i, 16, keptCards)*100;	// percent of artifacts kept
			deckList[i][16] = getRatioComplex(i, 17, keptCards)*100;	// percent of creatures kept
			deckList[i][17] = getRatioComplex(i, 18, keptCards)*100;	// percent of lands kept
			deckList[i][18] = getRatioComplex(i, 19, keptCards)*100;	// percent of enchantments kept
			deckList[i][19] = getRatioComplex(i, 20, keptCards)*100;	// percent of instants kept
			deckList[i][20] = getRatioComplex(i, 21, keptCards)*100;	// percent of sorceries kept
			deckList[i][21] = getRatioComplex(i, 22, keptCards)*100;	// percent of planeswalkers kept
			
			deckList[i][22] = getRatioComplex(i, 23, keptCards)*100;	// percent of mana cards kept
			deckList[i][23] = getRatioComplex(i, 24, keptCards)*100;	// percent of draw cards kept
			deckList[i][24] = getRatioComplex(i, 25, keptCards)*100;	// percent of interaction cards kept
			deckList[i][25] = getRatioComplex(i, 26, keptCards)*100;	// percent of threat cards kept
			deckList[i][26] = getRatioComplex(i, 27, keptCards)*100;	// percent of combo cards kept
			deckList[i][27] = getRatioComplex(i, 28, keptCards)*100;	// percent of other cards kept
			
			// stuff about mulligans
			deckList[i][28] = getRatio(i, 29, 5);	// average mulligans per game
			deckList[i][29] = getRatio(i, 30, 5);	// average number of cards pitched per game
			deckList[i][30] = getRatioComplex(1, 31, (Integer.parseInt(rawData[i][30].toString()) - Integer.parseInt(rawData[i][34].toString())));	// average CMC of cards pitched
			
			deckList[i][31] = getRatio(i, 32, 30)*100;	// percent of artifacts pitched
			deckList[i][32] = getRatio(i, 33, 30)*100;	// percent of creatures pitched
			deckList[i][33] = getRatio(i, 34, 30)*100;	// percent of lands pitched
			deckList[i][34] = getRatio(i, 35, 30)*100;	// percent of enchantments pitched
			deckList[i][35] = getRatio(i, 36, 30)*100;	// percent of instants pitched
			deckList[i][36] = getRatio(i, 37, 30)*100;	// percent of sorceries pitched
			deckList[i][37] = getRatio(i, 38, 30)*100;	// percent of planeswalkers pitched
			
			deckList[i][38] = getRatio(i, 39, 30)*100;	// percent of mana cards pitched
			deckList[i][39] = getRatio(i, 40, 30)*100;	// percent of draw cards pitched
			deckList[i][40] = getRatio(i, 41, 30)*100;	// percent of interaction cards pitched
			deckList[i][41] = getRatio(i, 42, 30)*100;	// percent of threat cards pitched
			deckList[i][42] = getRatio(i, 43, 30)*100;	// percent of combo cards pitched
			deckList[i][43] = getRatio(i, 44, 30)*100;	// percent of other cards pitched
			
			// stuff about wins
			deckList[i][44] = getRatio(i, 45, 5)*100;	// win rate
			deckList[i][45] = getRatio(i, 46, 45)*100;	// percent of aggro wins
			deckList[i][46] = getRatio(i, 47, 45)*100;	// percent of aetherflux reservoir wins
			deckList[i][47] = getRatio(i, 48, 45)*100;	// percent of laboratory maniac wins
			deckList[i][48] = getRatio(i, 49, 45)*100;	// percent of other combo wins
			deckList[i][49] = getRatio(i, 50, 45)*100;	// percent of wins via opponents scooping
			deckList[i][50] = getRatio(i, 51, 45)*100;	// percent of other wins
			
			deckList[i][51] = rawData[i][52];	// relevancy
		} // end of for loop
	} // end of function makeDeckList
	
	/*
	 * function to create deck menu
	 */
	private void deckMenu() {
		System.out.println(edhData.divider);
		
		System.out.println("edhData deck information menu");
		
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
	 * displays the deck help menu
	 * called by help command
	 */
	private void deckHelp() {
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
		System.out.println("hide");
		System.out.println("     Tells the database to hide decks that are no longer in the meta or do not have the minimum number of games.");
		System.out.println("high");
		System.out.println("     Asks for a number and lists that many top decks in the current category.");
		System.out.println("list");
		System.out.println("     Lists all decks in the database and their themes.");
		System.out.println("low");
		System.out.println("     Asks for a number and lists that many bottom decks in the current category.");
		System.out.println("minimum");
		System.out.println("     Asks for a number and sets the minimum number of games for a deck to be shown.");
		System.out.println("show");
		System.out.println("     Tells the program to show all decks, even decks that are no longer in the meta or decks that do not have the minimum number of games.");
		System.out.println("sort");
		System.out.println("     Asks for a category and sorts all decks in the database by that category.");
		System.out.println();
		System.out.println(edhData.divider);
		System.out.print("Press any button to continue.");
		user.nextLine();
		
		System.out.println(edhData.divider);
	} // end of function playerHelp
	
	/*
	 * function to get the top decks
	 * asks the user for the number of decks to show
	 * called by high command
	 */
	private void getTopDecks() {
		System.out.println(edhData.divider);
		
		if(sortColumn == 0) { // does not list if they are not sorted
			System.out.println("Please sort the decks first using the sort command.");
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
		
		if(number >= deckList.length) {
			number = deckList.length;
		}
		
		System.out.println(edhData.divider);
		System.out.println();
		System.out.format("%-60s", "Decks");
		System.out.print("|" + columnName);
		System.out.println();
		System.out.println("============================================================|==============================================");
		
		// for loop to print each deck's information
		for(int i = 1; i <= number; i++) {
			if((Integer.parseInt(deckList[deckList.length - i][51].toString()) == 1 && Integer.parseInt(deckList[deckList.length - i][4].toString()) >= minimumGames) || showAllDecks) {	// only show if relevant or more than one game unless specifically told otherwise
				System.out.format("%-60s", deckList[deckList.length-i][1].toString());
				System.out.print("|" + deckList[deckList.length-i][sortColumn].toString());
				System.out.println();
				System.out.format("%-60s", deckList[deckList.length-i][2].toString());
				System.out.print("|");
				System.out.println();
				System.out.println("------------------------------------------------------------|----------------------------------------------");
			} else {
				if(number < deckList.length) {
					number++;
				}
			}
		} // end of for loop
		
		System.out.println();
		System.out.println(edhData.divider);
		System.out.print("Press any button to continue.");
		user.nextLine();
		
		System.out.println(edhData.divider);
	} // end of function getTopPlayers
	
	/*
	 * function to list all known decks
	 * called by list command
	 */
	private void getDeckList() {
		System.out.println(edhData.divider);
		arraySort.sort(deckList, 0);
		
		System.out.println("Decks in the database:");
		// for loop to print each deck
		for(int i = 0; i < deckList.length; i++) {
			if(((Integer.parseInt(deckList[i][51].toString()) == 1 && Integer.parseInt(deckList[i][4].toString()) >= minimumGames) || showAllDecks)) {	// only show if relevant or more than one game unless specifically told otherwise
				System.out.println("     " + deckList[i][1].toString().trim() + " - " + deckList[i][2]);
			}
		} // end of for loop
		
		arraySort.sort(deckList, sortColumn);
		System.out.println(edhData.divider);
	} // end of function getDeckList
	
	/*
	 * function to get the bottom decks
	 * asks the user for the number of decks to show
	 * called by low command
	 */
	private void getBottomDecks() {
		System.out.println(edhData.divider);
		
		if(sortColumn == 0) {
			System.out.println("Please sort the decks first using the sort command.");
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
		
		if(number > deckList.length) {
			number = deckList.length;
		}
		
		System.out.println(edhData.divider);
		System.out.println();
		System.out.format("%-60s", "Deck");
		System.out.print("|" + columnName);
		System.out.println();
		System.out.println("============================================================|==============================================");
		
		// for loop to print each deck's information
		for(int i = 0; i < number; i++) {
			if(((Integer.parseInt(deckList[i][51].toString()) == 1 && Integer.parseInt(deckList[i][4].toString()) >= minimumGames) || showAllDecks)) {	// only show if relevant or more than one game unless specifically told otherwise
				System.out.format("%-60s", deckList[i][1].toString());
				System.out.print("|" + deckList[i][sortColumn].toString());
				System.out.println();
				System.out.format("%-60s", deckList[i][2].toString());
				System.out.print("|");
				System.out.println();
				System.out.println("------------------------------------------------------------|----------------------------------------------");
			} else {
				if(number < deckList.length) {
					number++;
				}
			}
		} // end of for loop
		
		System.out.println();
		System.out.println(edhData.divider);
		System.out.print("Press any button to continue.");
		user.nextLine();
		
		System.out.println(edhData.divider);
	} // end of function getBottomDecks
	
	/*
	 * function to set the minimum game number
	 * called by minimum command
	 */
	private void setMinimumGames() {
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
		
		minimumGames = number;
	} // end of function setMinimumGames
	
	/*
	 * function to sort decks by a category
	 * asks the user which category to sort by
	 * called by sort command
	 */
	private void sortDecks() {
		categoryList();
		System.out.println();
		System.out.print("Please enter a category: ");
		switch(user.nextLine()) {
		case "cmc":
			System.out.println("Sorting decks by average opening hand CMC...");
			sortColumn = 14;
			columnName = "Average Opening Hand CMC";
			System.out.println("Finished sorting.");
			break;
		case "fun":
			System.out.println("Sorting decks by percentage of fun games...");
			sortColumn = 5;
			columnName = "Fun Games (%)";
			System.out.println("Finished sorting.");
			break;
		case "games":
			System.out.println("Sorting decks by total games played...");
			sortColumn = 4;
			columnName = "Total Games";
			System.out.println("Finished sorting.");
			break;
		case "mulligans":
			System.out.println("Sorting decks by average mulligans per game...");
			sortColumn = 28;
			columnName = "Average Mulligans per Game";
			System.out.println("Finished sorting.");
			break;
		case "opponents":
			System.out.println("Sorting decks by percentage of opponents who had fun against this deck...");
			sortColumn = 8;
			columnName = "Opponents Who Had Fun Against This Deck (%)";
			System.out.println("Finished sorting.");
			break;
		case "wins":
			System.out.println("Sorting decks by win rate...");
			sortColumn = 44;
			columnName = "Win Rate (%)";
			System.out.println("Finished sorting.");
			break;
		default:
			System.out.println();
			System.out.println("Unknown category.");
		}
		
		arraySort.sort(deckList, sortColumn);
		System.out.println(edhData.divider);
	} // end of function sortDeck
	
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
	private void deckCommands() {
		System.out.println("Known commands:");
		// for loop to add in commands
		for (int i = 0; i < commands.length; i++) {
			System.out.print("     " + commands[i]);
		} // end of for loop
		System.out.println();
		System.out.println();
	} // end of function deckCommands
} // end of class deckData