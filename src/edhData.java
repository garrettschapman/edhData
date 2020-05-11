import java.util.Scanner;

// edhData class by Garrett Chapman
// last updated 05/11/2020

class edhData {
	// constructs
	private static Scanner in = new Scanner(System.in);
	private static dataCollection collect;
	
	// variables
	private String[][] playerData;
	
	/*
	 * main method
	 * creates a data collection object to collect data
	 */
	static void main(String[] args) {
		System.out.print("How many players? ");
		int players = in.nextInt();
		collect = new dataCollection(players);
		collect.collectData();
	} // end of main method
}