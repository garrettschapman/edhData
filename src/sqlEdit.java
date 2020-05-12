import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// sqlEdit class created by Garrett Chapman
// last updated 05/12/2020
// handles connection to SQL server

class sqlEdit {
	private String connectionUrl =
			"jdbc:sqlserver://DESKTOP-US0M9AH:1433;"
			+ "database=edhStats;"
			+ "user=MyLogin;"
			+ "password=userpassword;"
			+ "encrypt=false;"
			+ "trustServerCertificate=false;"
			+ "loginTimeout=30;";
	ResultSet resultSet = null;
	
	// constructor
	public sqlEdit() {
		try (Connection connection = DriverManager.getConnection(connectionUrl);
				Statement statement = connection.createStatement();) {
		} catch (Exception e) {
			System.out.println("ERROR: could not connect to database.");
			System.exit(0);
		}
	} // end of constructor
} // end of class sqlEdit