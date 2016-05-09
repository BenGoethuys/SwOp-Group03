package bugtrap03.bugdomain;

import purecollections.PList;

/**
 * An enumeration of health indicators. <br>
 * Order them from most critical to healthy.
 * 
 * @author Mathias
 *
 */
public enum HealthIndicator {
    CRITICAL, SERIOUS, STABLE, SATISFACTORY, HEALTHY;

    HealthIndicator() {
    }

    /**
     * 
     * @param projects
     * @return
     */
    public String getAllHealthIndicators(PList<Project> projects) {
	String str = "";
	HealthAlgorithm1 ha1 = new HealthAlgorithm1();
	HealthAlgorithm2 ha2 = new HealthAlgorithm2();
	HealthAlgorithm3 ha3 = new HealthAlgorithm3();

	for (Project proj : projects) {
	    str += (proj.getName() + ":\n");
	    str += ("\tAlgorithm 1: " + proj.getIndicator(ha1) + "\n");
	    str += ("\tAlgorithm 2: " + proj.getIndicator(ha2) + "\n");
	    str += ("\tAlgorithm 3: " + proj.getIndicator(ha3) + "\n");
	}

	return str;
    }

}
