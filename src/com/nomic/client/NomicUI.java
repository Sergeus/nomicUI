package com.nomic.client;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.nomic.shared.SimulationData;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NomicUI implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	private StackLayoutPanel sidePanel;
	
	private VerticalPanel mainPanel;
	
	private Label ActiveSimLabel;

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final NomicDBServiceAsync nomicDBService = GWT
			.create(NomicDBService.class);
	
	public void displaySimData(Collection<SimulationData> simulations) {
		ArrayList<SimulationData> simArray = (ArrayList<SimulationData>) simulations;
		ActiveSimLabel.setText(simArray.get(0).getName());
		
		for (SimulationData simData : simulations) {
			final Button simButton = new Button("Simulation " + simData.getID());
			final HTML simContent = new HTML("This sim has " + simData.getNumAgents() + " agents.");
			simButton.setWidth("100%");
			sidePanel.add(simContent, simButton, 4);
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Document.get().setTitle("NomicUI");
		
		final DockLayoutPanel topPanel = new DockLayoutPanel(Unit.EM);
		RootLayoutPanel.get().add(topPanel);
		
		topPanel.setWidth("100%");
		
		final Label mainTitle = new Label("Nomic");
		mainTitle.setStyleName("Heading1");
		topPanel.addNorth(mainTitle, 10);
		
		sidePanel = new StackLayoutPanel(Unit.EM);
		topPanel.addWest(sidePanel, 15);
		
		final HorizontalPanel footerPanel = new HorizontalPanel();
		footerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		footerPanel.setWidth("100%");
		topPanel.addSouth(footerPanel, 4);
		
		final Label nameLabel = new Label("Stuart Holland");
		nameLabel.setStyleName("Footer1");
		footerPanel.add(nameLabel);
		
		mainPanel = new VerticalPanel();
		topPanel.add(mainPanel);
		mainPanel.setWidth("100%");
		
		ActiveSimLabel = new Label();
		ActiveSimLabel.setStyleName("Heading2");
		mainPanel.add(ActiveSimLabel);
		
		AsyncCallback<Collection<SimulationData>> callback = new AsyncCallback<Collection<SimulationData>>() {
			
			@Override
			public void onSuccess(Collection<SimulationData> result) {
				displaySimData(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
		
		nomicDBService.getSimulationData(callback);
	}
}
