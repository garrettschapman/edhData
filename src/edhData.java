import java.util.Scanner;

// edhData class by Garrett Chapman
// last updated 05/11/2020

public class edhData {
	// constructs
	private static Scanner in = new Scanner(System.in);
	
	// menu text
	public static String divider = "===========================================================================================================================================================";
	private static String[] commands = {
			"help",
			"play"
	};
	
	/*
	 * main menu
	 * creates and runs menu, allowing user functionality
	 */
	public static void main(String[] args) {
		mainMenu();
		
		// while loop to allow functionality
		while(true) {
			
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
		
		commands();
	} //end of function mainMenu
	
	/*
	 * function to print commands
	 * separated from main menu function because it will be printed again for incorrect input and after a command
	 */
	private static void commands() {
		System.out.println(divider);
		
		System.out.println("Known commands:");
		// for loop to add in commands
		for (int i = 0; i < commands.length; i++) {
			System.out.print("     " + commands[i]);
		} // end of for loop
		System.out.println();
		
		System.out.println(divider);
		
		System.out.println("enter a command: ");
	} // end of function commands
	
	/*
	 *  function to collect and store game data
	 */
	private static void gameData() {
		System.out.print("How many players? ");
		int players = in.nextInt();
		System.out.println();
		
		// collects data from the game
		dataCollection collect = new dataCollection(players);
		collect.collectData();
		
		// stores data from the game
		dataStorage store = new dataStorage();
		store.storeData(collect.getPlayerData());
		
		commands();
	} //end of function gameData
} //end of class edhData