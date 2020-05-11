import java.util.Scanner;

// dataCollection class by Garrett Chapman
// last updated 05/11/2020
// collects data from the user for edhData

class dataCollection {
	// variables
	private String[][] playerData; // array to hold info about the player
	private int numPlayers; //number of players
	
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
		Scanner in = new Scanner(System.in); // scanner to get info about game
		boolean winnerFound = false; // stop asking for a winner if there already is one
		
		// loop to go through array
		for (int i = 0; i < numPlayers; i++) {
			System.out.println("Player " + (i+1) + ":");
			
			// begin questions
			System.out.print("Name: ");
			playerData[i][0] = in.nextLine();
			System.out.println();
			
			System.out.print("Commander: ");
			playerData[i][1] = in.nextLine();
			System.out.println();
			
			System.out.print("Theme: ");
			playerData[i][2] = in.nextLine();
			System.out.println();
			
			System.out.print("Color identity: ");
			playerData[i][3] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of colors: ");
			playerData[i][4] = in.nextLine();
			System.out.println();
			
			System.out.print("Did they have fun? y/e/n: ");
			playerData[i][5] = in.nextLine();
			System.out.println();
			
			// opening hand info
			System.out.print("Colors in opening hand: ");
			playerData[i][6] = in.nextLine();
			System.out.println();
			
			System.out.print("Colors of mana in opening hand: ");
			playerData[i][7] = in.nextLine();
			System.out.println();
			
			System.out.print("Total CMC of opening hand: ");
			playerData[i][8] = in.nextLine();
			System.out.println();
			
			// card types
			System.out.print("Number of artifacts kept: ");
			playerData[i][9] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of creatures kept: ");
			playerData[i][10] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of lands kept: ");
			playerData[i][11] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of enchantments kept: ");
			playerData[i][12] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of instants kept: ");
			playerData[i][13] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of sorceries kept: ");
			playerData[i][14] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of planeswalkers kept: ");
			playerData[i][15] = in.nextLine();
			System.out.println();
			
			// card themes
			System.out.print("Number of mana cards kept: ");
			playerData[i][16] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of draw cards kept: ");
			playerData[i][17] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of interaction cards kept: ");
			playerData[i][18] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of threat cards kept: ");
			playerData[i][19] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of combo cards kept: ");
			playerData[i][20] = in.nextLine();
			System.out.println();
			
			System.out.print("Number of other cards kept: ");
			playerData[i][21] = in.nextLine();
			System.out.println();
			
			// mulligan info
			System.out.print("Number of mulligans: ");
			playerData[i][22] = in.nextLine();
			int mulligans = Integer.parseInt(playerData[i][22]);
			System.out.println();
			
			// avoids asking player questions if there aren't mulligans
			if (mulligans > 0) {
				System.out.print("Number of cards pitched: ");
				playerData[i][23] = in.nextLine();
				int pitched = Integer.parseInt(playerData[i][23]);
				System.out.println();
				
				if (pitched > 0) {
					System.out.print("Total CMC of cards pitched: ");
					playerData[i][24] = in.nextLine();
					System.out.println();
					
					// card types
					System.out.print("Number of artifacts pitched: ");
					playerData[i][25] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of creatures pitched: ");
					playerData[i][26] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of lands pitched: ");
					playerData[i][27] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of enchantments pitched: ");
					playerData[i][28] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of instants pitched: ");
					playerData[i][29] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of sorceries pitched: ");
					playerData[i][30] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of planeswalkers pitched: ");
					playerData[i][31] = in.nextLine();
					System.out.println();
					
					// card themes
					System.out.print("Number of mana cards pitched: ");
					playerData[i][32] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of draw cards pitched: ");
					playerData[i][33] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of interaction cards pitched: ");
					playerData[i][34] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of threat cards pitched: ");
					playerData[i][35] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of combo cards pitched: ");
					playerData[i][36] = in.nextLine();
					System.out.println();
					
					System.out.print("Number of other cards pitched: ");
					playerData[i][37] = in.nextLine();
					System.out.println();
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
			playerData[i][38] = in.nextLine();
			System.out.println();
			
			// avoids asking about winning
			if (playerData[i][38].equals("y") || winnerFound) {
				playerData[i][39] = "n";
				playerData[i][40] = "-";
			} else {
				System.out.print("Did they win? y/n: ");
				playerData[i][39] = in.nextLine();
				System.out.println();
				
				// checks if a winner has been found
				if (playerData[i][39].equals("y")) {
					winnerFound = true;
					
					System.out.print("How did they win? aggro/aether/labman/combo/scoops/other: ");
					playerData[i][40] = in.nextLine();
					System.out.println();
				} else {
					playerData[i][40] = "-";
				} //end of checks
			} //end of checks
			
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("=============================================================================================");
			System.out.println();
			System.out.println();
			System.out.println();
		} // end of for loop
		
		in.close();
	} // end of function collectData
	
	/*
	 * getter for playerData
	 */
	public String[][] getPlayerData() {
		return playerData;
	}
} // end of class