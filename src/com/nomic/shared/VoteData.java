package com.nomic.shared;

import java.io.Serializable;



public class VoteData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer SimID;
	
	String CasterName;
	
	VoteType Type;
	
	Integer TimeCast;
	
	Integer TurnCast;
	
	public VoteData() {
		
	}

	public VoteData(Integer simID, Integer timeCast) {
		super();
		SimID = simID;
		TimeCast = timeCast;
	}
	
	public void parseStates(String states) {
		String[] subStates = states.split(",");
		
		for (String substate : subStates) {
			String[] terms = substate.split("\"");
			
			if (substate.contains("\"Vote\"=>\"")) {
				Type = VoteType.valueOf(terms[3]);
			}
			else if (substate.contains("CasterName")) {
				CasterName = terms[3];
			}
			else if (substate.contains("TurnNumber")) {
				TurnCast = Integer.parseInt(terms[3]);
			}
		}
	}

	public Integer getSimID() {
		return SimID;
	}

	public String getCasterName() {
		return CasterName;
	}

	public VoteType getType() {
		return Type;
	}

	public Integer getTimeCast() {
		return TimeCast;
	}
	
	public Integer getTurnCast() {
		return TurnCast;
	}
}
