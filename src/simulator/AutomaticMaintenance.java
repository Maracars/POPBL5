package simulator;

import domain.dao.DAOPlane;
import domain.model.Airport;
import domain.model.Plane;
import helpers.MD5;
import notification.PGSocketIONotify;

public class AutomaticMaintenance implements Runnable {

	private static final int SLEEP_TIME_5_MIN_IN_MILIS = 5 * 60 * 1000;
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
				System.out.println("JODER BA HONA ASKHJDFADFBKRHNFKJWEHBJ");
				PGSocketIONotify.sendNotification(MD5.encrypt("controller"),
						"Plane " + planeToRevise.getSerial() + " REVISED");

			}

			try {
				Thread.sleep(SLEEP_TIME_5_MIN_IN_MILIS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
