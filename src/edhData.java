import java.util.Scanner;

// edhData class by Garrett Chapman
// last updated 05/11/2020

public class edhData {
	// constructs
	private static dataCollection collect;
	
	// variables
	private static String[][] playerData;
	
	/*
	 * main method
	 * creates a data collection object to collect data
	 */
	public static void main(String[] args) {
		// gets the number of players
		Scanner in = new Scanner(System.in);
		System.out.print("How many players? ");
		int players = in.nextInt();
		
		// collects data from the game
		collect = new dataCollection(players);
		collect.collectData();
		playerData = collect.getPlayerData();
		in.close();
	} // end of main method
}