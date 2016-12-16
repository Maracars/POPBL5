package simulator;

import domain.dao.DAOPlane;
import domain.model.Airport;
import domain.model.Plane;
import domain.model.users.Admin;
import helpers.MD5;
import notification.Notification;

public class AutomaticMaintenance implements Runnable {

	private static final String ADMIN = new Admin().getClass().getSimpleName();
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
				Notification.sendNotification(MD5.encrypt(ADMIN), "Plane " + planeToRevise.getSerial() + " REVISED");

			} else {
				Notification.sendNotification(MD5.encrypt(ADMIN), "There is no plane to revise REVISED");
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
