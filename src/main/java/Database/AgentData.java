package Database;

import java.util.ArrayList;
import java.util.Collection;

public class AgentData {
	Integer SimID;
	
	String Type;
	
	String Name;
	
	Integer NumSubSims;
	
	Integer AverageSubSimLength;
	
	Collection<VoteData> VoteHistory;

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
				System.out.println("Got agent type: " + Type);
			}
			else if (subState.contains("NumSubSims")) {
				NumSubSims = Integer.parseInt(terms[3]);
				System.out.println("Got NumSubSims: " + NumSubSims);
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
}
