package simulator;

import domain.dao.DAOPlane;
import domain.dao.HibernateGeneric;
import domain.model.Plane;
import domain.model.users.Admin;
import helpers.MD5;
import notification.Notification;

public class AutomaticMaintenance implements Runnable {

	private static final String OK = "OK";
	private static final String BROKEN = "BROKEN";
	private static final String NO_PLANE_TO_REVISE = "There is no plane to revise";
	private static final String ADMIN = new Admin().getClass().getSimpleName();
	private static final int MILIS_TO_SECOND = 1000;
	private static final int SECOND_TO_MIN = 60;
	private static final int SLEEP_MIN_TIME = 5;
	private static final int SLEEP_TIME_5_MIN_IN_MILIS = SLEEP_MIN_TIME * SECOND_TO_MIN * MILIS_TO_SECOND;
	

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			Plane planeToRevise = DAOPlane.selectPlaneNeedToRevise();
			if (planeToRevise != null) {
				revisePlane(planeToRevise, true);
				Notification.sendNotification(MD5.encrypt(ADMIN),
						"Plane " + planeToRevise.getSerial() + " REVISED");

			} else {
				Notification.sendNotification(MD5.encrypt(ADMIN),
						NO_PLANE_TO_REVISE);
			}

			try {

				Thread.sleep(SLEEP_TIME_5_MIN_IN_MILIS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}

	}


	private void revisePlane(Plane planeToRevise,  boolean status) {
		
		String statusString = status ? OK : BROKEN;
		planeToRevise.getPlaneStatus().setTechnicalStatus(statusString);
		HibernateGeneric.updateObject(planeToRevise);		
	}

}
