package Database;

import java.sql.*;
import java.util.Collection;
import java.util.Properties;

import org.postgresql.Driver;

public class NomicDatabase {
	
	Collection<SimulationData> Simulations;
	
	public NomicDatabase() {
		super();
	}
	
	public void init() {
		
		String url = "jdbc:postgresql://localhost/presage";
		Properties props = new Properties();
		props.setProperty("user", "presage_user");
		props.setProperty("password", "n0micgam3s");
		props.setProperty("ssl", "true");
		
		Driver driver = new Driver();
		
		try {
			System.out.println("Connecting to database...");
			System.out.println();
			
			DriverManager.registerDriver(driver);
			Connection db = DriverManager.getConnection(url, props);
			
			System.out.println("Retrieving Nomic data...");
			
			PopulateNomicData(db);
			
			System.out.println("Database loaded.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("We got this far.");
	}
	
	private void PopulateNomicData(Connection db) {
		
	}

}
