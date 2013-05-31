package com.nomic.shared;

import java.util.Comparator;

public class SimulationComparator implements Comparator<SimulationData> {

	@Override
	public int compare(SimulationData arg0, SimulationData arg1) {
		if (arg0.getID() < arg1.getID())
			return -1;
		
		if (arg0.getID() > arg1.getID())
			return 1;
		
		return 0;
	}

}
