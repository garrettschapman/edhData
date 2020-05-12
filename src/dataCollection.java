// dataCollection class by Garrett Chapman
// last updated 05/11/2020
// collects data from the user for edhData

class dataCollection {
	// variables
	private String[][] playerData; // array to hold info about the player
	private int numPlayers; //number of players
	private String[] yn = {"y", "n"};
	private String[] yen = {"y", "e", "n"};
	private String[] winCons = {"aggro", "aether", "labman", "combo", "scoops", "other"};
	
	// constructor
	public dataCollection(int players) {
		playerData = new String[players][41];
		numPlayers = players;
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
			playerData[i][0] = edhData.in.nextLine();
			
			System.out.print("Commander: ");
			playerData[i][1] = edhData.in.nextLine();
			
			System.out.print("Theme: ");
			playerData[i][2] = edhData.in.nextLine();
			
			System.out.print("Color identity: ");
			playerData[i][3] = edhData.in.nextLine();
			
			System.out.print("Number of colors: ");
			validateNum(i, 4, edhData.in.nextLine());
			
			System.out.print("Did they have fun? y/e/n: ");
			validateText(i, 5, edhData.in.nextLine(), yen);
			
			// opening hand info
			System.out.print("Colors in opening hand: ");
			validateNum(i, 6, edhData.in.nextLine());
			
			System.out.print("Colors of mana in opening hand: ");
			validateNum(i, 7, edhData.in.nextLine());
			
			System.out.print("Total CMC of opening hand: ");
			validateNum(i, 8, edhData.in.nextLine());
			
			// card types
			System.out.print("Number of artifacts kept: ");
			validateNum(i, 9, edhData.in.nextLine());
			
			System.out.print("Number of creatures kept: ");
			validateNum(i, 10, edhData.in.nextLine());
			
			System.out.print("Number of lands kept: ");
			validateNum(i, 11, edhData.in.nextLine());
			
			System.out.print("Number of enchantments kept: ");
			validateNum(i, 12, edhData.in.nextLine());
			
			System.out.print("Number of instants kept: ");
			validateNum(i, 13, edhData.in.nextLine());
			
			System.out.print("Number of sorceries kept: ");
			validateNum(i, 14, edhData.in.nextLine());
			
			System.out.print("Number of planeswalkers kept: ");
			validateNum(i, 15, edhData.in.nextLine());
			
			// card themes
			System.out.print("Number of mana cards kept: ");
			validateNum(i, 16, edhData.in.nextLine());
			
			System.out.print("Number of draw cards kept: ");
			validateNum(i, 17, edhData.in.nextLine());
			
			System.out.print("Number of interaction cards kept: ");
			validateNum(i, 18, edhData.in.nextLine());
			
			System.out.print("Number of threat cards kept: ");
			validateNum(i, 19, edhData.in.nextLine());
			
			System.out.print("Number of combo cards kept: ");
			validateNum(i, 20, edhData.in.nextLine());
			
			System.out.print("Number of other cards kept: ");
			validateNum(i, 21, edhData.in.nextLine());
			
			// mulligan info
			System.out.print("Number of mulligans: ");
			validateNum(i, 22, edhData.in.nextLine());
			int mulligans = Integer.parseInt(playerData[i][22]);
			
			// avoids asking player questions if there aren't mulligans
			if (mulligans > 0) {
				System.out.print("Number of cards pitched: ");
				validateNum(i, 23, edhData.in.nextLine());
				int pitched = Integer.parseInt(playerData[i][23]);
				
				if (pitched > 0) {
					System.out.print("Total CMC of cards pitched: ");
					validateNum(i, 24, edhData.in.nextLine());
					
					// card types
					System.out.print("Number of artifacts pitched: ");
					validateNum(i, 25, edhData.in.nextLine());
					
					System.out.print("Number of creatures pitched: ");
					validateNum(i, 26, edhData.in.nextLine());
					
					System.out.print("Number of lands pitched: ");
					validateNum(i, 27, edhData.in.nextLine());
					
					System.out.print("Number of enchantments pitched: ");
					validateNum(i, 28, edhData.in.nextLine());
					
					System.out.print("Number of instants pitched: ");
					validateNum(i, 29, edhData.in.nextLine());
					
					System.out.print("Number of sorceries pitched: ");
					validateNum(i, 30, edhData.in.nextLine());
					
					System.out.print("Number of planeswalkers pitched: ");
					validateNum(i, 31, edhData.in.nextLine());
					
					// card themes
					System.out.print("Number of mana cards pitched: ");
					validateNum(i, 32, edhData.in.nextLine());
					
					System.out.print("Number of draw cards pitched: ");
					validateNum(i, 33, edhData.in.nextLine());
					
					System.out.print("Number of interaction cards pitched: ");
					validateNum(i, 34, edhData.in.nextLine());
					
					System.out.print("Number of threat cards pitched: ");
					validateNum(i, 35, edhData.in.nextLine());
					
					System.out.print("Number of combo cards pitched: ");
					validateNum(i, 36, edhData.in.nextLine());
					
					System.out.print("Number of other cards pitched: ");
					validateNum(i, 37, edhData.in.nextLine());
				} else {
					// for loop to assign 0 to all cards pitched
					for (int j = 0; j < 14; j++) {
						playerData[i][j + 24] = "0";
					} // end of for loop
				}
			} else {
				// for loop to assign 0 to all cards pitched
				for (int j = 0; j < 15; j++) {
					playerData[i][j + 23] = "0";
				} // end of for loop
			} // end of checks
		
			System.out.print("Did they scoop? y/n: ");
			validateText(i, 38, edhData.in.nextLine(), yn);
			
			// avoids asking about winning
			if (playerData[i][38].equals("y") || winnerFound) {
				playerData[i][39] = "n";
				playerData[i][40] = "-";
			} else {
				System.out.print("Did they win? y/n: ");
				validateText(i, 39, edhData.in.nextLine(), yn);
				
				// checks if a winner has been found
				if (playerData[i][39].equals("y")) {
					winnerFound = true;
					
					System.out.print("How did they win? aggro/aether/labman/combo/scoops/other: ");
					validateText(i, 40, edhData.in.nextLine(), winCons);
				} else {
					playerData[i][40] = "-";
				} //end of checks
			} //end of checks
			
			System.out.println(edhData.divider);
		} // end of for loop
		
		System.out.println("Game data collection finished.");
		System.out.println(edhData.divider);
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
			
			input = edhData.in.nextLine();
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
				input = edhData.in.nextLine();
			} //end of check
		} // end of while loop
	} // end of method validateNum
	
	/*
	 * getter for playerData
	 */
	public String[][] getPlayerData() {
		return playerData;
	}
} // end of class dataCollection