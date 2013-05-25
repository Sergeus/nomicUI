package Web;

import java.sql.SQLException;

import Database.NomicDatabase;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WEnvironment;

public class NomicApplication extends WApplication {

	public NomicApplication(WEnvironment env) {
		super(env);
		
		NomicDatabase db = new NomicDatabase();
		
		if (!db.IsInitialized()) {
			try {
				db.init();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
