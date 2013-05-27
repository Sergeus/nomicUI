package com.nomic.database;

public class ModificationProposalData extends ProposalData {
	String OldRuleName;
	
	String OldRule;
	
	String NewRuleName;
	
	String NewRule;

	public String getOldRuleName() {
		return OldRuleName;
	}

	public String getOldRule() {
		return OldRule;
	}

	public String getNewRuleName() {
		return NewRuleName;
	}

	public String getNewRule() {
		return NewRule;
	}

}
