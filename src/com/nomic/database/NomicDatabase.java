package com.nomic.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.postgresql.Driver;

import com.nomic.shared.AdditionProposalData;
import com.nomic.shared.AgentComparator;
import com.nomic.shared.AgentData;
import com.nomic.shared.ModificationProposalData;
import com.nomic.shared.NoProposalData;
import com.nomic.shared.ProposalData;
import com.nomic.shared.RemovalProposalData;
import com.nomic.shared.SimulationData;
import com.nomic.shared.VoteData;
import com.nomic.shared.VoteType;


public class NomicDatabase {
	
	static Collection<SimulationData> Simulations = null;
	
	boolean Empty = false;
	
	String SimDataQuery = "SELECT * FROM simulations;";
	String AgentDataQuery = "SELECT * FROM agents;";
	String AgentTransientDataQuery = "SELECT * FROM agenttransient;";
	String ProposalDataQuery = "SELECT * FROM environmenttransient;";
	
	String simFilePath = "/home/stuart/Documents/";
	
	public NomicDatabase() {
		super();
	}

	/**
	 * Loads simulation data from the Presage2 SQL database.
	 * @throws SQLException
	 */
	public void init() throws SQLException {
		
		String url = "jdbc:postgresql://localhost/presage";
		Properties props = new Properties();
		props.setProperty("user", "presage_user");
		props.setProperty("password", "n0micgam3s");
		props.setProperty("ssl", "true");
		
		Driver driver = new Driver();
		
		System.out.println("Connecting to database...");
		System.out.println();
		
		DriverManager.registerDriver(driver);
		Connection db = DriverManager.getConnection(url, props);
		
		System.out.println("Retrieving Nomic data...");
		System.out.println();
		
		populateNomicData(db);
		
		System.out.println("Database loaded.");
		
		db.close();
	}
	
	private void populateNomicData(Connection db) throws SQLException {
		Simulations = new ArrayList<SimulationData>();
		
		Statement stmt = db.createStatement();
		
		// Grab simulation data from the 'simulations' table
		if (stmt.execute(SimDataQuery)) {
			ResultSet sResults = stmt.getResultSet();
			
			//System.out.println("Row limit of " + stmt.getMaxRows());
			
			while (sResults.next()) {
				
				SimulationData simData = loadSimulationData(sResults);
				
				Simulations.add(simData);
				
			}
			
			if (stmt.execute(AgentDataQuery)) {
				ResultSet aResults = stmt.getResultSet();
				
				while (aResults.next()) {
					AgentData agentData = loadAgentData(aResults);
					
					//System.out.println("SimID: " + agentData.getSimID());
					
					getSimByID(agentData.getSimID())
							.add(agentData);
				}
				
				if (stmt.execute(AgentTransientDataQuery)) {
					ResultSet atResults = stmt.getResultSet();
					
					while (atResults.next()) {
						VoteData voteData = loadVoteData(atResults);
						
						//System.out.println("Sim id " + voteData.getSimID() + " caster " + voteData.getCasterName());
						
						getAgentBySimIDAndAgentName(voteData.getSimID(), 
								voteData.getCasterName())
								.add(voteData);
					}
				}
				
				if (stmt.execute(ProposalDataQuery)) {
					ResultSet pResults = stmt.getResultSet();
					
					while (pResults.next()) {
						ProposalData propData = loadProposalData(pResults);
						
						getSimByID(propData.getSimID()).add(propData);
					}
				}
			}
		}
		else {
			Empty = true;
		}
	}
	
	private SimulationData loadSimulationData(ResultSet kResults) throws SQLException {
		
		Integer ID = kResults.getInt("id");
		String Name = kResults.getString("name");
		Integer NumTimeSteps = kResults.getInt("finishTime");
		
		String Parameters = kResults.getString("parameters");
		
		SimulationData simData = new SimulationData(ID, Name, NumTimeSteps);
		simData.ParseParameters(Parameters);
		
		return simData;
	}
	
	private AgentData loadAgentData(ResultSet kResults) throws SQLException {
		Integer simId = kResults.getInt("simId");
		String name = kResults.getString("name");
		
		String states = kResults.getString("state");
		
		AgentData agentData = new AgentData(simId, name);
		agentData.ParseStates(states);
		
		return agentData;
	}
	
	private VoteData loadVoteData(ResultSet kResults) throws SQLException {
		Integer SimID = kResults.getInt("simId");
		Integer timeCast = kResults.getInt("time");
		String states = kResults.getString("state");
		
		VoteData voteData = new VoteData(SimID, timeCast);
		voteData.parseStates(states);
		
		return voteData;
	}
	
	private ProposalData loadProposalData(ResultSet kResults) throws SQLException {
		Integer SimID = kResults.getInt("simId");
		Integer time = kResults.getInt("time");
		String state = kResults.getString("state");
		
		ProposalData propData;
		
		switch (ProposalData.getProposalType(state)) {
		case ADDITION :
			propData = new AdditionProposalData(SimID, time);
			break;
		case REMOVAL :
			propData = new RemovalProposalData(SimID, time);
			break;
		case MODIFICATION :
			propData = new ModificationProposalData(SimID, time);
			break;
		case NONE :
			propData = new NoProposalData(SimID, time);
			break;
		default :
			propData = new AdditionProposalData(SimID, time);
		}
		
		propData.ParseStates(state);
		
		return propData;
	}
	
	/**
	 * Creates a CSV file for results presentation of the sim with the parameter ID
	 * @param simID
	 */
	public void makeCSV(Integer simID) {
		File simFile = new File(simFilePath + "Simulation" + simID + ".csv");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(simFile));
			
			SimulationData simData = getSimByID(simID);
			
			writer.write("Simulation" + simID + "\n");
			
			writer.write("Agent Name,Type,SubSims,Subsim Length,Yes Votes,No Votes\n");
			
			Collections.sort(simData.getAgentData(), new AgentComparator());
			
			for (AgentData agent : simData.getAgentData()) {
				writer.write(agent.getName() + "," + agent.getType() + "," + agent.getNumSubSims() + ","
						+ agent.getAverageSubSimLength() + "," + agent.getNumVotes(VoteType.YES)
						+ "," + agent.getNumVotes(VoteType.NO) + "\n");
			}
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the simulation data that corresponds to the parameter ID.
	 * Returns null if no data is found for the given ID.
	 * @param ID
	 * @return Simulation data
	 */
	public SimulationData getSimByID(Integer ID) {
		for (SimulationData simData : Simulations) {
			if (simData.getID().equals(ID))
				return simData;
		}
		
		//System.out.println("No sim for ID " + ID);
		
		return null;
	}
	
	/**
	 * Gets the agent data corresponding to a given agent from a given simulation.
	 * Returns null if no matching agent is found.
	 * @param SimID
	 * @param AgentName
	 * @return
	 */
	public AgentData getAgentBySimIDAndAgentName(Integer SimID, String AgentName) {
		for (SimulationData simData : Simulations) {
			if (simData.getID().equals(SimID))
				return simData.getAgentByName(AgentName);
		}
		
		return null;
	}
	
	/**
	 * Invalidates the DB cache so the next page request will cause data to be 
	 * retrieved from the DB.
	 */
	public void InvalidateCache() {
		Simulations = null;
	}
	
	private void printSimIDs() {
		for (SimulationData simData : Simulations) {
			System.out.println("Sim ID: " + simData.getID());
		}
	}

	/**
	 * Checks if the DB has been cached locally.
	 * @return
	 */
	public boolean IsInitialized() {
		return Simulations != null;
	}
	
	public boolean IsEmpty() {
		return Empty;
	}
	
	public Collection<SimulationData> getSimulations() {
		return Simulations;
	}
}
