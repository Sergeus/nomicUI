package com.nomic.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nomic.shared.SimulationData;

@RemoteServiceRelativePath("nomicDB")
public interface NomicDBService extends RemoteService {
	Collection<SimulationData> getSimulationData();
}
