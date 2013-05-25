package Web;

import Database.NomicDatabase;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WEnvironment;

public class NomicApplication extends WApplication {

	public NomicApplication(WEnvironment env) {
		super(env);
		
		NomicDatabase db = new NomicDatabase();
		
		db.init();
	}

}
