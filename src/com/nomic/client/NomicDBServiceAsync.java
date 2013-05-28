package com.nomic.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nomic.shared.SimulationData;

public interface NomicDBServiceAsync {
	void getSimulationData(AsyncCallback<Collection<SimulationData>> callback);
}
