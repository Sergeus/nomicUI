package com.nomic.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.nomic.shared.SimulationData;

public class SimButtonClickHandler implements ClickHandler {
	
	private NomicUI mainPage;
	
	private SimulationData simData;
	
	public SimButtonClickHandler(NomicUI page, SimulationData simData) {
		this.mainPage = page;
		this.simData = simData;
	}

	@Override
	public void onClick(ClickEvent event) {
		mainPage.SetActiveSimulation(simData);
	}

}
