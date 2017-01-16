import java.sql.*;
import java.util.UUID;

/**
 * Created by taylor on 1/15/17.
 * Example of storing Player Data in a MySQL Database.
 */
public class PlayerSQLData {
	
	private static Connection connection;
	private final static String host = "HOST",
			database = "DATABASE", username = "USERNAME", password = "PASSWORD";
	
	private String uuid;
	
	public PlayerSQLData(UUID uuid) {
		this.uuid = uuid.toString();
		checkForPlayer();
	}
	
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
	
	public static synchronized void closeConnection() {
		if(connection != null)
			try {
				connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
	}
	
}