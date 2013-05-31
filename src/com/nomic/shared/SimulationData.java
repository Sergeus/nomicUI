package com.nomic.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class SimulationData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer ID;
	
	String Name;
	
	Integer NumAgents;
	
	Integer NumTimeSteps;
	
	Integer NumTurns;
	
	Integer NumRounds;
	
	boolean Won;
	
	String WinnerName;
	
	Integer WinTime;
	
	ArrayList<AgentData> Agents;
	
	Collection<ProposalData> Proposals;
	
	public SimulationData() {
		
	}

	public SimulationData(Integer iD, String name,
			Integer numTimeSteps) {
		super();
		ID = iD;
		Name = name;
		NumTimeSteps = numTimeSteps;
		
		Agents = new ArrayList<AgentData>();
		Proposals = new ArrayList<ProposalData>();
	}
	
	public void ParseParameters(String parameters) {
		//System.out.println("Parsing parameters for sim " + ID);
		
		String[] params = parameters.split(",");
		
		for (String param : params) {
			//System.out.println("Parameter split: " + param);
			
			String[] terms = param.split("\"");
		
			if (param.contains("\"Won\"=>\"")) {
				Won = Boolean.parseBoolean(terms[3]);
				//System.out.println("Got won: " + Won);
			}
			else if (param.contains("NumTurns")) {
				NumTurns = Integer.parseInt(terms[3]);
				//System.out.println("Got NumTurns: " + NumTurns);
			}
			else if (param.contains("NumRounds")) {
				NumRounds = Integer.parseInt(terms[3]);
				//System.out.println("Got NumRounds: " + NumRounds);
			}
			else if (param.contains("NumAgents")) {
				NumAgents = Integer.parseInt(terms[3]);
				//System.out.println("Got NumAgents: " + NumAgents);
			}
			else if (param.contains("Winner")) {
				WinnerName = terms[3];
				//System.out.println("Got winner name: " + WinnerName);
			}
			else if (param.contains("WinTime")) {
				WinTime = Integer.parseInt(terms[3]);
				//System.out.println("Got win time: " + WinTime);
			}
		}
	}
	
	public void add(AgentData agentData) {
		Agents.add(agentData);
	}
	
	public void add(ProposalData proposalData) {
		Proposals.add(proposalData);
	}
	
	public ArrayList<AgentData> getAgentData() {
		return Agents;
	}
	
	public AgentData getAgentByName(String agentName) {
		for (AgentData agentData : Agents) {
			if (agentData.getName().equals(agentName))
				return agentData;
		}
		
		return null;
	}

	public Integer getID() {
		return ID;
	}

	public String getName() {
		return Name;
	}

	public Integer getNumAgents() {
		return NumAgents;
	}

	public Integer getNumTimeSteps() {
		return NumTimeSteps;
	}

	public Integer getNumTurns() {
		return NumTurns;
	}

	public Integer getNumRounds() {
		return NumRounds;
	}

	public boolean isWon() {
		return Won;
	}
	
	public boolean isOver(Integer time) {
		for (int i=time; i < getNumTimeSteps(); i++) {
			if (getProposals(i).size() > 0)
				return false;
			if (getVotes(i).size() > 0)
				return false;
		}
		
		return true;
	}

	public String getWinnerName() {
		return WinnerName;
	}
	
	/**
	 * Returns true if the parameter time step was used for voting
	 * @return
	 */
	public boolean isVoteTimeStep(Integer time) {
		for (AgentData agent : Agents) {
			if (agent.getVote(time) != null)
				return true;
		}
		
		return false;
	}
	
	public boolean isProposalTimeStep(Integer time) {
		for (ProposalData proposal : Proposals) {
			if (proposal.getTime().equals(time))
				return true;
		}
		
		return false;
	}
	
	public boolean isWinTimeStep(Integer time) {
		if (isWon() && time.equals(WinTime)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isBeforeWin(Integer time) {
		if (!isWon()) {
			return true;
		}
		else {
			return time < WinTime;
		}
	}
	
	public ArrayList<VoteData> getVotes(Integer time) {
		ArrayList<VoteData> votes = new ArrayList<VoteData>();
		
		for (AgentData agent : Agents) {
			VoteData vote = agent.getVote(time);
			if (vote != null)
				votes.add(vote);
		}
		
		return votes;
	}
	
	public ArrayList<ProposalData> getProposals(Integer time) {
		ArrayList<ProposalData> props = new ArrayList<ProposalData>();
		
		for (ProposalData prop : Proposals) {
			if (prop.getTime().equals(time))
				props.add(prop);
		}
		
		return props;
	}
}
