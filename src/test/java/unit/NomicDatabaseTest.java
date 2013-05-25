package unit;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import Database.NomicDatabase;

public class NomicDatabaseTest {

	@Test
	public void testInit() {
		NomicDatabase db = new NomicDatabase();
		
		try {
			db.init();
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Init failed with SQLException.");
		}
	}
}
