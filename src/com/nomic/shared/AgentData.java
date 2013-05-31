package com.nomic.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class AgentData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer SimID;
	
	String Type;
	
	String Name;
	
	Integer NumSubSims;
	
	Integer AverageSubSimLength;
	
	Collection<VoteData> VoteHistory;
	
	public AgentData() {
		
	}

	public AgentData(Integer simID, String name) {
		super();
		SimID = simID;
		Name = name;
		VoteHistory = new ArrayList<VoteData>();
	}
	
	public void ParseStates(String States) {
		String[] subStates = States.split(",");
		
		for (String subState : subStates) {
			//System.out.println("State split: " + subState);
			
			String[] terms = subState.split("\"");
			
			if (subState.contains("\"type\"=>\"")) {
				Type = terms[3];
				//System.out.println("Got agent type: " + Type);
			}
			else if (subState.contains("NumSubSims")) {
				NumSubSims = Integer.parseInt(terms[3]);
				//System.out.println("Got NumSubSims: " + NumSubSims);
			}
			else if (subState.contains("AverageSubSimLength")) {
				AverageSubSimLength = Integer.parseInt(terms[3]);
			}
		}
	}
	
	public void add(VoteData voteData) {
		VoteHistory.add(voteData);
	}

	public Integer getSimID() {
		return SimID;
	}

	public String getType() {
		return Type;
	}

	public String getName() {
		return Name;
	}

	public Integer getNumSubSims() {
		return NumSubSims;
	}

	public Integer getAverageSubSimLength() {
		return AverageSubSimLength;
	}
	
	/**
	 * Gets vote data from the parameter sim time.
	 * Returns null if no vote data found.
	 * @param simTime
	 * @return
	 */
	public VoteData getVote(Integer simTime) {
		for (VoteData vote : VoteHistory) {
			if (vote.getTimeCast().equals(simTime))
				return vote;
		}
		
		return null;
	}
	
	public int getNumVotes(VoteType type) {
		int total = 0;
		for (VoteData vote : VoteHistory) {
			if (vote.getType() == type)
				total++;
		}
		
		return total;
	}
}
