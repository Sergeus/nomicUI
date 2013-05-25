package Web;

import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WEnvironment;
import eu.webtoolkit.jwt.WtServlet;

public class NomicServlet extends WtServlet {
	private static final long serialVersionUID = 1L;
	
	public NomicServlet() {
		super();
	}

	@Override
	public WApplication createApplication(WEnvironment env) {
		return new NomicApplication(env);
	}

}
