package com.nomic.shared;

import java.io.Serializable;


public class AdditionProposalData extends ProposalData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String NewRuleName;

	String NewRule;
	
	public AdditionProposalData() {
		
	}
	
	public AdditionProposalData(Integer simID, Integer time) {
		super(simID, time, RuleChangeType.ADDITION);
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
		}
	}
}
