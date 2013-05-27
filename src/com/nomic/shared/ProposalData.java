package com.nomic.shared;



public abstract class ProposalData {
	
	static String ModificationString = "\"Type\"=>\"MODIFICATION\"";
	static String AdditionString = "\"Type\"=>\"ADDITION\"";
	static String RemovalString = "\"Type\"=>\"REMOVAL\"";
	
	public static RuleChangeType getProposalType(String states) {
		if (states.contains(ModificationString)) {
			return RuleChangeType.MODIFICATION;
		}
		else if (states.contains(RemovalString)) {
			return RuleChangeType.REMOVAL;
		}
		else {
			return RuleChangeType.ADDITION;
		}
	}
	
	Integer SimID;
	
	Integer Time;
	
	Integer Turn;
	
	RuleChangeType Type;
	
	String ProposerName;
	
	boolean Success;
	
	public ProposalData() {
		
	}
	
	public ProposalData(Integer simID, Integer time, RuleChangeType type) {
		this.SimID = simID;
		this.Time = time;
		this.Type = type;
	}
	
	public abstract void ParseStates(String states);

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

	public String getProposerName() {
		return ProposerName;
	}

	public boolean isSuccess() {
		return Success;
	}
}
