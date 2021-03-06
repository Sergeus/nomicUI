package com.nomic.shared;

import java.io.Serializable;

public class NoProposalData extends ProposalData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoProposalData() {
		
	}
	
	public NoProposalData(Integer simID, Integer time) {
		super(simID, time, RuleChangeType.NONE);
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
		}
	}
}
