package Database;

import java.util.Collection;

public class AgentData {
	Integer SimID;
	
	String Type;
	
	String Name;
	
	Integer NumSubSims;
	
	Integer AverageSubSimLength;
	
	Collection<VoteData> VoteHistory;

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
