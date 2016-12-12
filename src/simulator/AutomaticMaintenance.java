package simulator;

import domain.dao.DAOPlane;
import domain.model.Plane;

public class AutomaticMaintenance implements Runnable {

	@Override
	public void run() {
		while (true) {
			Plane planeToRevise = DAOPlane.selectPlaneNeedToRevise();
			if (planeToRevise != null) {
				DAOPlane.revisePlane(planeToRevise);
				System.out.println("Plane revised");
			}
			// sleep??
		}

	}

}
