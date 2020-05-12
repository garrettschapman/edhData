import java.util.NoSuchElementException;
import java.util.Scanner;

// edhData class by Garrett Chapman
// last updated 05/12/2020
// controls main menu and user functionality

public class edhData {
	// constructs
	private static Scanner in = new Scanner(System.in);
	private static sqlEdit database;
	
	// menu text
	public static String divider = "===========================================================================================================================================================";
	private static String[] commands = {
			"add",
			"exit",
			"help"
	};
	
	/*
	 * main menu
	 * creates and runs menu, allowing user functionality
	 */
	public static void main(String[] args) {
		database = new sqlEdit();
		mainMenu();
		
		// while loop to allow functionality
		while(true) {
			commands();
			try {
				System.out.print("Please enter a command: ");
				switch(in.nextLine()) {
				case "add":
					System.out.println(divider);
					add();
					break;
				case "exit":
					System.out.println(divider);
					System.out.println("Program closing.  Thank you.");
					System.out.println(divider);
					System.exit(0);
					break;
				case "help":
					System.out.println(divider);
					help();
					break;
				default:
					System.out.println(divider);
					System.out.println("ERROR: unknown command.");
				}
			} catch(NoSuchElementException e) {
				System.out.println("ERROR: bad scan");
			}
		} // end of while loop
	} // end of main method
	
	/*
	 * function to create main menu
	 */
	private static void mainMenu() {
		System.out.println(divider);
		
		System.out.println("edhData Collector created by Garrett Chapman");
		System.out.println("This software is designed to keep track of EDH (Commander) games of Magic: The Gathering, which is created by Wizards of the Coast and not owned by me.");
		System.out.println("See the github repository here: https://github.com/garrettschapman/edhData");
		
		System.out.println(divider);
	} //end of function mainMenu
	
	/*
	 * function to print commands
	 * separated from main menu function because it will be printed again for incorrect input and after a command
	 */
	private static void commands() {
		System.out.println("Known commands:");
		// for loop to add in commands
		for (int i = 0; i < commands.length; i++) {
			System.out.print("     " + commands[i]);
		} // end of for loop
		System.out.println();
		System.out.println();
	} // end of function commands
	
	/*
	 *  function to collect and store game data
	 *  called by add command
	 */
	private static void add() {
		System.out.print("How many players? ");
		int players = Integer.parseInt(in.nextLine());
		System.out.println();
		
		// collects data from the game
		dataCollection collect = new dataCollection(players, in);
		collect.collectData();
		
		// stores data from the game
		dataStorage store = new dataStorage();
		store.storeData(collect.getPlayerData());
	} //end of function add
	
	/*
	 * function to display help menu
	 */
	private static void help() {
		System.out.println("Known commands:");
		System.out.println();
		System.out.println("add");
		System.out.println("     Adds game data to the database.  Takes the number of players and information about each player in the game.");
		System.out.println();
		System.out.println("exit");
		System.out.println("     Quits the program.");
		System.out.println();
		System.out.println("help");
		System.out.println("     Displays the help menu.");
		System.out.println();
		System.out.println(divider);
		System.out.print("Press any button to continue.");
		in.nextLine();
		System.out.println(divider);
	} // end of function help
} //end of class edhData