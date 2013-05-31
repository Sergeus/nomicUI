package com.nomic.client;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.nomic.shared.AdditionProposalData;
import com.nomic.shared.AgentData;
import com.nomic.shared.ModificationProposalData;
import com.nomic.shared.ProposalData;
import com.nomic.shared.RemovalProposalData;
import com.nomic.shared.RuleChangeType;
import com.nomic.shared.SimulationData;
import com.nomic.shared.VoteData;
import com.nomic.shared.VoteType;

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
	
	private VerticalPanel SimContentPanel = new VerticalPanel();
	
	private Label ActiveSimLabel = new Label();
	
	private Label NumberOfAgents = new Label();
	
	private Label SimTimeLength = new Label();
	
	private Label SimTurnLength = new Label();
	
	private Label SimRoundLength = new Label();
	
	private Label SimWon = new Label();
	
	private Integer ActiveSimVisibleTimeSteps = 0;
	
	private Button simProgressButton;
	
	private SimulationData ActiveSimData;

	/**
	 * Create a remote service proxy to talk to the server-side NomicDB service.
	 */
	private final NomicDBServiceAsync nomicDBService = GWT
			.create(NomicDBService.class);
	
	public void displaySimData(Collection<SimulationData> simulations) {
		ArrayList<SimulationData> simArray = (ArrayList<SimulationData>) simulations;
		SetActiveSimulation(simArray.get(0));
		
		for (SimulationData simData : simulations) {
			final Button simButton = new Button("Simulation " + simData.getID());
			
			final ScrollPanel simContent = constructSimSummaryPanel(simData);
			simButton.setWidth("100%");
			simButton.addClickHandler(new SimButtonClickHandler(this, simData));
			sidePanel.add(simContent, simButton, 3);
		}
		
		// Height must be explicit since StackLayoutPanels don't properly report their size to
		// parent panels, so the scroll panel won't work properly without this.
		// Height is a function of the number of subpanels (each heading is 3em tall).
		// The extra 15 is for the content panel of the currently selected item,
		// only one of which will ever be visible at a time.
		sidePanel.setHeight((3*sidePanel.getWidgetCount() + 15) + "em");
	}
	
	private ScrollPanel constructSimSummaryPanel(SimulationData simData) {
		final ScrollPanel topScroll = new ScrollPanel();
		final VerticalPanel contentPanel = new VerticalPanel();
		topScroll.add(contentPanel);
		
		final Label agentNumberLabel = new Label();
		agentNumberLabel.setText("Agents: " + simData.getNumAgents());
		agentNumberLabel.setStyleName("SimSummary");
		contentPanel.add(agentNumberLabel);
		
		for (AgentData agentData : simData.getAgentData()) {
			final Label agentSummary = new Label();
			agentSummary.setText(agentData.getName() + ": " + agentData.getType());
			agentSummary.setStyleName("SimSummary");
			agentSummary.addStyleDependentName("AgentLabel");
			contentPanel.add(agentSummary);
			
			final Label agentSubSims = new Label();
			agentSubSims.setText("Subsims: " + agentData.getNumSubSims());
			agentSubSims.setStyleName("SimSummary");
			contentPanel.add(agentSubSims);
			
			final Label agentSubSimLength = new Label();
			agentSubSimLength.setText("Subsim length: " + agentData.getAverageSubSimLength());
			agentSubSimLength.setStyleName("SimSummary");
			contentPanel.add(agentSubSimLength);
			
			final Label agentYesVotes = new Label();
			agentYesVotes.setText("Yes votes: " + agentData.getNumVotes(VoteType.YES));
			agentYesVotes.setStyleName("SimSummary");
			contentPanel.add(agentYesVotes);
			
			final Label agentNoVotes = new Label();
			agentNoVotes.setText("No votes: " + agentData.getNumVotes(VoteType.NO));
			agentNoVotes.setStyleName("SimSummary");
			contentPanel.add(agentNoVotes);
		}
		
		// Slightly smaller than the size allowed for the content panel, so the scroll bar
		// buttons fit too
		topScroll.setHeight("14em");
		
		return topScroll;
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
		//topPanel.addNorth(mainTitle, 10);
		
		sidePanel = new StackLayoutPanel(Unit.EM);
		
		final ScrollPanel sideScroll = new ScrollPanel();
		sideScroll.add(sidePanel);
		
		topPanel.addWest(sideScroll, 15);
		
		final HorizontalPanel footerPanel = new HorizontalPanel();
		footerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		footerPanel.setWidth("100%");
		topPanel.addSouth(footerPanel, 4);
		
		final Label nameLabel = new Label("Stuart Holland");
		nameLabel.setStyleName("Footer1");
		footerPanel.add(nameLabel);
		
		final ScrollPanel mainScroll = new ScrollPanel();
		topPanel.add(mainScroll);
		
		mainPanel = new VerticalPanel();
		mainScroll.add(mainPanel);
		mainPanel.setWidth("100%");
		
		ActiveSimLabel.setStyleName("hero-unit");
		mainPanel.add(ActiveSimLabel);
		
		final HorizontalPanel simDataPanel = new HorizontalPanel();
		mainPanel.add(simDataPanel);
		
		simDataPanel.setWidth("100%");
		simDataPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		simDataPanel.add(NumberOfAgents);
		simDataPanel.add(SimTimeLength);
		simDataPanel.add(SimTurnLength);
		simDataPanel.add(SimRoundLength);
		simDataPanel.add(SimWon);
		
		NumberOfAgents.setStyleName("SimHeadings");
		SimTimeLength.setStyleName("SimHeadings");
		SimTurnLength.setStyleName("SimHeadings");
		SimRoundLength.setStyleName("SimHeadings");
		SimWon.setStyleName("SimHeadings");
		
		final Label divider = new Label(" ");
		divider.setStyleName("Divider");
		divider.setWidth("100%");
		mainPanel.add(divider);
		
		HTML hrTag = new HTML("<hr/>");
		
		mainPanel.add(hrTag);
		
		mainPanel.add(SimContentPanel);
		SimContentPanel.setWidth("100%");
		SimContentPanel.setHeight("100%");
		SimContentPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		
		simProgressButton = new Button("Next Time Step");
		footerPanel.add(simProgressButton);
		
		simProgressButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				DisplayNextSimTimeStep();
			}
		});
		
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
	
	public void SetActiveSimulation(SimulationData simData) {
		ActiveSimData = simData;
		
		ActiveSimLabel.setText(simData.getName());
		NumberOfAgents.setText("Agents: " + simData.getNumAgents());
		SimTimeLength.setText("Time steps: " + simData.getNumTimeSteps());
		SimTurnLength.setText("Turns: " + simData.getNumTurns());
		SimRoundLength.setText("Rounds: " + simData.getNumRounds());
		
		if (!simData.isWon()) {
			SimWon.setText("No winner.");
		}
		else {
			SimWon.setText(simData.getWinnerName() + " has won!");
		}
		
		ActiveSimVisibleTimeSteps = 0;
		for (int i = SimContentPanel.getWidgetCount() - 1; i >= 0; i--) {
			SimContentPanel.remove(i);
		}
		simProgressButton.setEnabled(true);
	}
	
	public void DisplayNextSimTimeStep() {
		AddSimtimeStep(ActiveSimData, ActiveSimVisibleTimeSteps);
		ActiveSimVisibleTimeSteps++;
		
		if (ActiveSimVisibleTimeSteps == ActiveSimData.getNumTimeSteps()) {
			simProgressButton.setEnabled(false);
		}
	}
	
	public void AddSimtimeStep(SimulationData simData, Integer timeStep) {
		final HorizontalPanel simTimeDataPanel = new HorizontalPanel();
		
		final Label timeLabel = new Label("Time " + timeStep);
		simTimeDataPanel.add(timeLabel);
		timeLabel.setStyleName("SimData");
		
		if (simData.isWinTimeStep(timeStep)) {
			final Label winLabel = new Label();
			winLabel.setText(simData.getWinnerName() + " Wins!");
			winLabel.setStyleName("WinLabel");
			simTimeDataPanel.add(winLabel);
			timeLabel.setStyleName("WinLabel");
			simTimeDataPanel.setWidth("100%");
			
			simProgressButton.setEnabled(false);
		}
		// Voting arrangement
		else if (simData.isVoteTimeStep(timeStep)) {
			final Label voteLabel = new Label("Voting");
			simTimeDataPanel.add(voteLabel);
			voteLabel.setStyleName("SimData");
			
			for (VoteData vote : simData.getVotes(timeStep)) {
				final Label casterName = new Label(vote.getCasterName() + ": ");
				simTimeDataPanel.add(casterName);
				casterName.setStyleName("SimData");
				final Label voteValue = new Label(vote.getType().toString());
				simTimeDataPanel.add(voteValue);
				casterName.setStyleName("SimData");
				
				voteValue.setStyleName("Vote");
				
				if (vote.getType() == VoteType.YES) {
					voteValue.addStyleDependentName("Yes");
				}
				else {
					voteValue.addStyleDependentName("No");
				}
			}
		}
		else if (simData.isProposalTimeStep(timeStep)) {
			final Label proposeLabel = new Label("Rule Proposal");
			simTimeDataPanel.add(proposeLabel);
			proposeLabel.setStyleName("SimData");
			
			for (ProposalData proposal : simData.getProposals(timeStep)) {
				final Label casterName = new Label();
				casterName.setText("Proposer: " + proposal.getProposerName());
				casterName.setStyleName("SimData");
				simTimeDataPanel.add(casterName);
				
				final Label ruleTypeLabel = new Label();
				ruleTypeLabel.setText("Type: ");
				ruleTypeLabel.setStyleName("SimData");
				simTimeDataPanel.add(ruleTypeLabel);
				
				final Label ruleType = new Label();
				RuleChangeType changeType = proposal.getType();
				ruleType.setText(changeType.toString());
				ruleType.setStyleName("Rule");
				
				simTimeDataPanel.add(ruleType);
				
				switch (changeType) {
				case ADDITION :
					AdditionProposalData addProp = (AdditionProposalData)proposal;
					ruleType.addStyleDependentName("Addition");
					
					final Label AddNewRuleName = new Label();
					AddNewRuleName.setText("New Rule Name: " + addProp.getNewRuleName());
					AddNewRuleName.setStyleName("SimData");
					simTimeDataPanel.add(AddNewRuleName);
					break;
				case MODIFICATION :
					ModificationProposalData modProp = (ModificationProposalData)proposal;
					ruleType.addStyleDependentName("Modification");
					
					final Label ModOldRuleName = new Label();
					ModOldRuleName.setText("Old Rule Name: " + modProp.getOldRuleName());
					ModOldRuleName.setStyleName("SimData");
					simTimeDataPanel.add(ModOldRuleName);
					
					final Label ModNewRuleName = new Label();
					ModNewRuleName.setText("New Rule Name: " + modProp.getNewRuleName());
					ModNewRuleName.setStyleName("SimData");
					simTimeDataPanel.add(ModNewRuleName);
					break;
				case REMOVAL :
					RemovalProposalData remProp = (RemovalProposalData)proposal;
					ruleType.addStyleDependentName("Removal");
					
					final Label RemOldRuleName = new Label();
					RemOldRuleName.setText("Old Rule Name: " + remProp.getOldRuleName());
					RemOldRuleName.setStyleName("SimData");
					simTimeDataPanel.add(RemOldRuleName);
					break;
				case NONE :
					ruleType.addStyleDependentName("None");
				default :
					ruleType.setStyleName("SimData");
					break;
				}
				
				final Label successLabel = new Label();
				successLabel.setStyleName("Vote");
				
				if (proposal.isSuccess()) {
					successLabel.setText("Success");
					successLabel.addStyleDependentName("Yes");
				}
				else if (changeType != RuleChangeType.NONE) {
					successLabel.setText("Failure");
					successLabel.addStyleDependentName("No");
				}
				simTimeDataPanel.add(successLabel);
			}
		}
		else if (timeStep == 0) {
			final Label init = new Label();
			init.setText("Initialization");
			init.setStyleName("SimData");
			simTimeDataPanel.add(init);
		}
		else {
			final Label idle = new Label();
			idle.setText("No proposals");
			idle.setStyleName("SimData");
			simTimeDataPanel.add(idle);
		}
		
		SimContentPanel.add(simTimeDataPanel);
		
		//simTimeDataPanel.setWidth("100%");
		
		if (timeStep % 2 == 0) {
			simTimeDataPanel.addStyleDependentName("Secondary");
		}
	}
}
