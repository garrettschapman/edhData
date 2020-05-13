// dataStorage class created by Garrett Chapman
// created 05/12/2020
// last updated 05/13/2020
// sends data collected by the user to the database

class dataStorage {
	private sqlEdit database;
	
	// constructor
	public dataStorage(sqlEdit server) {
		database = server;
	} //end of constructor
	
	/*
	 * takes data collected by dataCollection and stores it in the database
	 */
	public void storeData(String[][] data) {
		
	} // end of function storeData
} // end of class dataStorage