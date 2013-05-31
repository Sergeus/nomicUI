package com.nomic.shared;

import java.util.Comparator;

public class AgentComparator implements Comparator<AgentData> {

	@Override
	public int compare(AgentData o1, AgentData o2) {
		if (o1.getSequentialID() < o2.getSequentialID())
			return -1;
		
		if (o1.getSequentialID() > o2.getSequentialID())
			return 1;
		
		return 0;
	}

}
