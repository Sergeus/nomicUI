package com.nomic.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nomic.shared.SimulationData;

@RemoteServiceRelativePath("nomicDB")
public interface NomicDBService extends RemoteService {
	SimulationData[] getSimulationData();
}
