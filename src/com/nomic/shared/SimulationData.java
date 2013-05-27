package com.nomic.shared;

import java.util.ArrayList;
import java.util.Collection;


public class SimulationData {
	
	Integer ID;
	
	String Name;
	
	Integer NumAgents;
	
	Integer NumTimeSteps;
	
	Integer NumTurns;
	
	Integer NumRounds;
	
	boolean Won;
	
	String WinnerName;
	
	Collection<AgentData> Agents;
	
	Collection<ProposalData> Proposals;

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
		System.out.println("Parsing parameters for sim " + ID);
		
		String[] params = parameters.split(",");
		
		for (String param : params) {
			//System.out.println("Parameter split: " + param);
			
			String[] terms = param.split("\"");
		
			if (param.contains("\"Won\"=>\"")) {
				Won = Boolean.parseBoolean(terms[3]);
				System.out.println("Got won: " + Won);
			}
			else if (param.contains("NumTurns")) {
				NumTurns = Integer.parseInt(terms[3]);
				System.out.println("Got NumTurns: " + NumTurns);
			}
			else if (param.contains("NumRounds")) {
				NumRounds = Integer.parseInt(terms[3]);
				System.out.println("Got NumRounds: " + NumRounds);
			}
			else if (param.contains("NumAgents")) {
				NumAgents = Integer.parseInt(terms[3]);
				System.out.println("Got NumAgents: " + NumAgents);
			}
			else if (param.contains("Winner")) {
				WinnerName = terms[3];
				System.out.println("Got winner name: " + WinnerName);
			}
		}
	}
	
	public void add(AgentData agentData) {
		Agents.add(agentData);
	}
	
	public void add(ProposalData proposalData) {
		Proposals.add(proposalData);
	}
	
	public AgentData getAgentByName(String agentName) {
		for (AgentData agentData : Agents) {
			if (agentData.getName() == agentName)
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

	public String getWinnerName() {
		return WinnerName;
	}
}
