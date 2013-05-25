package Database;

import java.util.Collection;

public class SimulationData {
	
	Integer ID;
	
	String Name;
	
	Integer NumAgents;
	
	Integer NumTimeSteps;
	
	Integer NumTurns;
	
	Integer NumRounds;
	
	boolean Won;
	
	String WinnerName;
	
	Collection<AgentData> Agents;
	
	Collection<ProposalData> Proposals;

	public Integer getID() {
		return ID;
	}

	public String getName() {
		return Name;
	}

	public Integer getNumAgents() {
		return NumAgents;
	}

	public Integer getNumTimeSteps() {
		return NumTimeSteps;
	}

	public Integer getNumTurns() {
		return NumTurns;
	}

	public Integer getNumRounds() {
		return NumRounds;
	}

	public boolean isWon() {
		return Won;
	}

	public String getWinnerName() {
		return WinnerName;
	}
}
