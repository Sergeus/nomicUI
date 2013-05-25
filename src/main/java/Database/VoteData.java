package Database;

import enums.VoteType;

public class VoteData {
	Integer SimID;
	
	String CasterName;
	
	VoteType Type;
	
	Integer TimeCast;

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
}
