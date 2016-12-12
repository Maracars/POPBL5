package simulator;

import domain.dao.DAOPlane;
import domain.dao.HibernateGeneric;
import domain.model.Plane;

public class AutomaticMaintenance implements Runnable {

	@Override
	public void run() {
		while (true) {
			Plane planeToRevise = DAOPlane.selectPlaneNeedToRevise();
			HibernateGeneric.revisePlane(planeToRevise);
			// sleep??
		}

	}

}
