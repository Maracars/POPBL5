package simulator;

import domain.dao.HibernateGeneric;
import domain.model.Plane;

public class AutomaticMaintenance implements Runnable {

	@Override
	public void run() {
		while (true) {
			Plane planeToRevise = HibernateGeneric.selectPlaneNeedToRevise();
			if (planeToRevise != null) {
				HibernateGeneric.revisePlane(planeToRevise);
				System.out.println("Plane revised");
			}
			// sleep??
		}

	}

}
