package simulator;

import domain.dao.DAOPlane;
import domain.model.Airport;
import domain.model.Plane;

public class AutomaticMaintenance implements Runnable {

	private Airport airport;

	public AutomaticMaintenance(Airport airport) {

		this.airport = airport;
	}

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
