package com.nomic.shared;


public class ModificationProposalData extends ProposalData {
	String OldRuleName;
	
	String OldRule;
	
	String NewRuleName;
	
	String NewRule;
	
	public ModificationProposalData(Integer simID, Integer time) {
		super(simID, time, RuleChangeType.MODIFICATION);
	}

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
			else if (substate.contains("\"NewRuleName\"=>\"")) {
				this.NewRuleName = terms[3];
			}
			else if (substate.contains("\"OldRuleName\"=>\"")) {
				this.OldRuleName = terms[3];
			}
		}
	}

}
