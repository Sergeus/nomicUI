package com.nomic.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nomic.shared.SimulationData;

public interface NomicDBServiceAsync {
	void getSimulationData(AsyncCallback<SimulationData[]> callback);
}
