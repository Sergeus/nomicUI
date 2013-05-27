package com.nomic.database;

import com.nomic.enums.RuleChangeType;

public class ProposalData {
	Integer SimID;
	
	Integer Time;
	
	Integer Turn;
	
	RuleChangeType Type;

	public Integer getSimID() {
		return SimID;
	}

	public Integer getTime() {
		return Time;
	}

	public Integer getTurn() {
		return Turn;
	}

	public RuleChangeType getType() {
		return Type;
	}
}
