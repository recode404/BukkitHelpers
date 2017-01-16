import java.sql.*;
import java.util.UUID;

/**
 * Example of storing Player Data in a MySQL Database.
 */
public class PlayerSQLData {
	
	private static Connection connection;
	
	//REPLACE VALUES WITH YOUR SQL SERVER'S INFORMATION
	private final static String host = "HOST",
			database = "DATABASE", username = "USERNAME", password = "PASSWORD";
	
	private String uuid;
	
	public PlayerSQLData(UUID uuid) {
		this.uuid = uuid.toString();
		checkForPlayer();
	}
	
	//checks if player exists, if not intializes player
	private synchronized void checkForPlayer() {
		try {
			PreparedStatement statement = connection.prepareStatement
					("SELECT * FROM player_data WHERE uuid=? ;");
			statement.setString(1, this.uuid);
			ResultSet result = statement.executeQuery();
			
			if(!result.next())
				initializePlayer();
			
			statement.close();
			result.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//adds player to sql database
	private synchronized void initializePlayer() {
		try {
			PreparedStatement statement = connection.prepareStatement
					("INSERT INTO player_data (uuid, rank) VALUES (?, 0) ;");
			statement.setString(1, uuid);
			
			statement.execute();
			statement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//retrives the player's rank as an integer from the database
	public synchronized int getRank() {
		try {
			PreparedStatement statement = connection.prepareStatement
					("SELECT rank FROM player_data WHERE uuid=? ;");
			statement.setString(1, uuid);
			ResultSet result = statement.executeQuery();
			result.next();
			
			int rank = result.getInt("rank");
			statement.close();
			result.close();
			return rank;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//sets the players rank in the database
	public synchronized void setRank(int rank) {
		try {
			PreparedStatement statement = connection.prepareStatement
					("UPDATE player_data SET rank=? WHERE uuid=? ;");
			statement.setInt(1, rank);
			statement.setString(2, uuid);
			statement.executeUpdate();
			
			statement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//establishes connection with the SQL server. this should be executed in your onEnable only
	public static synchronized void openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+"3306"+"/"+database, username, password);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//closes the connection to the SQL server. this should be executed in your onDisable only
	public static synchronized void closeConnection() {
		if(connection != null)
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
	}
	
}
