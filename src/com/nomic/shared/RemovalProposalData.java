package com.nomic.shared;

public class RemovalProposalData extends ProposalData {
	String OldRuleName;

	String OldRule;
	
	public RemovalProposalData(Integer simID, Integer time) {
		super(simID, time, RuleChangeType.REMOVAL);
	}

	public String getOldRuleName() {
		return OldRuleName;
	}

	public String getOldRule() {
		return OldRule;
	}

	@Override
	public void ParseStates(String states) {
		String[] substates = states.split(",");
		
		for (String substate : substates) {
			String[] terms = substate.split("\"");
			
			if (substate.contains("\"Turn\"=>\"")) {
				Turn = Integer.parseInt(terms[3]);
			}
			else if (substate.contains("\"Success\"=>\"")) {
				Success = Boolean.parseBoolean(terms[3]);
			}
			else if (substate.contains("\"Proposer\"=>\"")) {
				this.ProposerName = terms[3];
			}
			else if (substate.contains("\"OldRuleName\"=>\"")) {
				this.OldRuleName = terms[3];
			}
		}
	}
}
