import java.util.Scanner;

// dataCollection class by Garrett Chapman
// created 05/11/2020
// last updated 05/25/2020
// collects data from the user for edhData

class dataCollection {
	private Scanner user;
	
	// variables
	private String[][] playerData; // array to hold info about the player
	private int numPlayers; //number of players
	private String[] colors = {"Colorless", "White", "Blue", "Black", "Red", "Green", 
			"Azorius", "Dimir", "Rakdos", "Gruul", "Selesnya", "Orzhov", "Izzet", "Golgari", "Boros", "Simic",
			"Esper", "Grixis", "Jund", "Naya", "Bant", "Abzan", "Jeskai", "Sultai", "Mardu", "Temur",
			"Yore-Tiller", "Glint-Eye", "Dune-Brood", "Ink-Treader", "Witch-Maw", "WUBRG"};
	private String[] yn = {"y", "n"};
	private String[] yen = {"y", "e", "n"};
	private String[] winCons = {"aggro", "aether", "labman", "combo", "scoops", "other"};
	
	// constructor
	public dataCollection(Scanner in) {
		System.out.println(edhData.divider);
		System.out.print("How many players? ");
		
		// while loop to make sure user inputs an integer
		loop: while(true) {
			try { // checks if the user input an integer
				numPlayers = Integer.parseInt(in.nextLine());
				break loop;
			} catch(NumberFormatException e) {
				System.out.print("Please input a number: ");
			} //end of check
		} // end of while loop
		System.out.println();
		
		playerData = new String[numPlayers][41];
		user = in;
	} // end of constructor
	
	/* function to collect data from the game
	 * fills the array with info about the player from the game
	 * each "row" in the array is a player
	 */
	public void collectData() {
		boolean winnerFound = false; // stop asking for a winner if there already is one
		
		// loop to go through array
		for (int i = 0; i < numPlayers; i++) {
			System.out.println("Player " + (i+1) + ":");
			
			// begin questions
			System.out.print("Name: ");
			playerData[i][0] = fixString(user.nextLine());
			
			System.out.print("Commander: ");
			playerData[i][1] = fixString(user.nextLine());
			
			System.out.print("Theme: ");
			playerData[i][2] = fixString(user.nextLine());
			
			System.out.print("Color identity: ");
			validateText(i, 3, user.nextLine(), colors);
			switch(playerData[i][3].toString().trim()) {
			case "White":
				playerData[i][4] = "1";
				break;
			case "Blue":
				playerData[i][4] = "1";
				break;
			case "Black":
				playerData[i][4] = "1";
				break;
			case "Red":
				playerData[i][4] = "1";
				break;
			case "Green":
				playerData[i][4] = "1";
				break;
			case "Azorius":
				playerData[i][4] = "2";
				break;
			case "Dimir":
				playerData[i][4] = "2";
				break;
			case "Rakdos":
				playerData[i][4] = "2";
				break;
			case "Gruul":
				playerData[i][4] = "2";
				break;
			case "Selesnya":
				playerData[i][4] = "2";
				break;
			case "Orzhov":
				playerData[i][4] = "2";
				break;
			case "Izzet":
				playerData[i][4] = "2";
				break;
			case "Golgari":
				playerData[i][4] = "2";
				break;
			case "Boros":
				playerData[i][4] = "2";
				break;
			case "Simic":
				playerData[i][4] = "2";
				break;
			case "Esper":
				playerData[i][4] = "3";
				break;
			case "Grixis":
				playerData[i][4] = "3";
				break;
			case "Jund":
				playerData[i][4] = "3";
				break;
			case "Naya":
				playerData[i][4] = "3";
				break;
			case "Bant":
				playerData[i][4] = "3";
				break;
			case "Abzan":
				playerData[i][4] = "3";
				break;
			case "Jeskai":
				playerData[i][4] = "3";
				break;
			case "Sultai":
				playerData[i][4] = "3";
				break;
			case "Mardu":
				playerData[i][4] = "3";
				break;
			case "Temur":
				playerData[i][4] = "3";
				break;
			case "Yore-Tiller":
				playerData[i][4] = "4";
				break;
			case "Glint-Eye":
				playerData[i][4] = "4";
				break;
			case "Dune-Brood":
				playerData[i][4] = "4";
				break;
			case "Ink-Treader":
				playerData[i][4] = "4";
				break;
			case "Witch-Maw":
				playerData[i][4] = "4";
				break;
			case "WUBRG":
				playerData[i][4] = "5";
				break;
			default:
				playerData[i][4] = "0";
				break;
			}
			
			System.out.print("Did they have fun? y/e/n: ");
			validateText(i, 5, user.nextLine(), yen);
			
			System.out.println(edhData.miniDivider);
			
			// opening hand info
			System.out.print("Colors in opening hand: ");
			validateNum(i, 6, user.nextLine());
			
			System.out.print("Colors of mana in opening hand: ");
			validateNum(i, 7, user.nextLine());
			
			System.out.print("Total CMC of opening hand: ");
			validateNum(i, 8, user.nextLine());
			
			System.out.println(edhData.miniDivider);
			
			// card types
			System.out.print("Number of artifacts kept: ");
			validateNum(i, 9, user.nextLine());
			
			System.out.print("Number of creatures kept: ");
			validateNum(i, 10, user.nextLine());
			
			System.out.print("Number of lands kept: ");
			validateNum(i, 11, user.nextLine());
			
			System.out.print("Number of enchantments kept: ");
			validateNum(i, 12, user.nextLine());
			
			System.out.print("Number of instants kept: ");
			validateNum(i, 13, user.nextLine());
			
			System.out.print("Number of sorceries kept: ");
			validateNum(i, 14, user.nextLine());
			
			System.out.print("Number of planeswalkers kept: ");
			validateNum(i, 15, user.nextLine());
			
			System.out.println(edhData.miniDivider);
			
			// card themes
			System.out.print("Number of mana cards kept: ");
			validateNum(i, 16, user.nextLine());
			
			System.out.print("Number of draw cards kept: ");
			validateNum(i, 17, user.nextLine());
			
			System.out.print("Number of interaction cards kept: ");
			validateNum(i, 18, user.nextLine());
			
			System.out.print("Number of threat cards kept: ");
			validateNum(i, 19, user.nextLine());
			
			System.out.print("Number of combo cards kept: ");
			validateNum(i, 20, user.nextLine());
			
			System.out.print("Number of other cards kept: ");
			validateNum(i, 21, user.nextLine());
			
			System.out.println(edhData.miniDivider);
			
			// mulligan info
			System.out.print("Number of mulligans: ");
			validateNum(i, 22, user.nextLine());
			int mulligans = Integer.parseInt(playerData[i][22]);
			
			// avoids asking player questions if there aren't mulligans
			if (mulligans > 0) {
				System.out.print("Number of cards pitched: ");
				validateNum(i, 23, user.nextLine());
				int pitched = Integer.parseInt(playerData[i][23]);
				
				if (pitched > 0) {
					System.out.print("Total CMC of cards pitched: ");
					validateNum(i, 24, user.nextLine());
					
					System.out.println(edhData.miniDivider);
					
					// card types
					System.out.print("Number of artifacts pitched: ");
					validateNum(i, 25, user.nextLine());
					
					System.out.print("Number of creatures pitched: ");
					validateNum(i, 26, user.nextLine());
					
					System.out.print("Number of lands pitched: ");
					validateNum(i, 27, user.nextLine());
					
					System.out.print("Number of enchantments pitched: ");
					validateNum(i, 28, user.nextLine());
					
					System.out.print("Number of instants pitched: ");
					validateNum(i, 29, user.nextLine());
					
					System.out.print("Number of sorceries pitched: ");
					validateNum(i, 30, user.nextLine());
					
					System.out.print("Number of planeswalkers pitched: ");
					validateNum(i, 31, user.nextLine());
					
					System.out.println(edhData.miniDivider);
					
					// card themes
					System.out.print("Number of mana cards pitched: ");
					validateNum(i, 32, user.nextLine());
					
					System.out.print("Number of draw cards pitched: ");
					validateNum(i, 33, user.nextLine());
					
					System.out.print("Number of interaction cards pitched: ");
					validateNum(i, 34, user.nextLine());
					
					System.out.print("Number of threat cards pitched: ");
					validateNum(i, 35, user.nextLine());
					
					System.out.print("Number of combo cards pitched: ");
					validateNum(i, 36, user.nextLine());
					
					System.out.print("Number of other cards pitched: ");
					validateNum(i, 37, user.nextLine());
				} else {
					// for loop to assign 0 to all cards pitched
					for (int j = 24; j < 38; j++) {
						playerData[i][j] = "0";
					} // end of for loop
				}
			} else {
				// for loop to assign 0 to all cards pitched
				for (int j = 23; j < 38; j++) {
					playerData[i][j] = "0";
				} // end of for loop
			} // end of checks
			
			System.out.println(edhData.miniDivider);
		
			System.out.print("Did they scoop? y/n: ");
			validateText(i, 38, user.nextLine(), yn);
			
			// avoids asking about winning
			if (playerData[i][38].equals("y") || winnerFound) {
				playerData[i][39] = "n";
				playerData[i][40] = "-";
			} else {
				System.out.print("Did they win? y/n: ");
				validateText(i, 39, user.nextLine(), yn);
				
				// checks if a winner has been found
				if (playerData[i][39].equals("y")) {
					winnerFound = true;
					
					System.out.print("How did they win? aggro/aether/labman/combo/scoops/other: ");
					validateText(i, 40, user.nextLine(), winCons);
				} else {
					playerData[i][40] = "-";
				} //end of checks
			} //end of checks
			
			System.out.println(edhData.divider);
		} // end of for loop
	} // end of function collectData
	
	/*
	 * function to validate user text input
	 * makes sure that user input is the type that can be used
	 */
	private void validateText(int i, int j, String input, String[] options) {
		
		// while loop to make sure the user inputs correct data
		while(true) {
			// for loop to check all options
			for (int k = 0; k < options.length; k++) {
				// checks if the input is valid
				if (input.equals(options[k])) {
					playerData[i][j] = input;
					return;
				} // end of check
			} // end of for loop
			
			System.out.print("Please input a valid entry. ");
			
			// for loop to list all options
			for (int k = 0; k < options.length; k++) {
				System.out.print(options[k]);
				
				// prints a slash if more options, or a colon if no more options
				if (k+1 == options.length) {
					System.out.print(": ");
				} else {
					System.out.print("/");
				} // end of check
			} // end of for loop
			
			input = user.nextLine();
		} // end of while loop
		
	} // end of function validate
	
	/*
	 * function to validate user integer input
	 * makes sure the user gives a number
	 */
	private void validateNum(int i, int j, String input) {
		
		// while loop to make sure user inputs an integer
		while(true) {
			try { // checks if the user input an integer
				Integer.parseInt(input);
				playerData[i][j] = input;
				return;
			} catch(NumberFormatException e) {
				System.out.print("Please input a number: ");
				input = user.nextLine();
			} //end of check
		} // end of while loop
	} // end of method validateNum
	
	/*
	 * function to remove apostrophes
	 * SQL does not like having apostrophes written to it
	 */
	private String fixString(String input) {
		input = input.replace("'", "");
		return input;
	} // end of function fixString
	
	/*
	 * getter for playerData
	 */
	public String[][] getPlayerData() {
		return playerData;
	} // end of getter
} // end of class dataCollection