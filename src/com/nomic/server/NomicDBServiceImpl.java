package com.nomic.server;

import java.sql.SQLException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.nomic.client.NomicDBService;
import com.nomic.database.NomicDatabase;
import com.nomic.shared.SimulationData;

public class NomicDBServiceImpl extends RemoteServiceServlet implements NomicDBService {

	public SimulationData[] getSimulationData() {
		NomicDatabase db = new NomicDatabase();
		
		if (!db.IsInitialized()) {
			try {
				db.init();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return db.getSimulations();
	}

}
