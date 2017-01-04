package simulator;

import domain.dao.DAOPlane;
import domain.dao.HibernateGeneric;
import domain.model.Plane;
import domain.model.users.Admin;
import helpers.MD5;
import notification.Notification;

// TODO: Auto-generated Javadoc
/**
 * The Class AutomaticMaintenance.
 */
public class AutomaticMaintenance implements Runnable {

	/** The Constant OK. */
	private static final String OK = "OK";
	
	/** The Constant BROKEN. */
	private static final String BROKEN = "BROKEN";
	
	/** The Constant NO_PLANE_TO_REVISE. */
	private static final String NO_PLANE_TO_REVISE = "There is no plane to revise";
	
	/** The Constant ADMIN. */
	private static final String ADMIN = new Admin().getClass().getSimpleName();
	
	/** The Constant MILIS_TO_SECOND. */
	private static final int MILIS_TO_SECOND = 1000;
	
	/** The Constant SECOND_TO_MIN. */
	private static final int SECOND_TO_MIN = 60;
	
	/** The Constant SLEEP_MIN_TIME. */
	private static final int SLEEP_MIN_TIME = 5;
	
	/** The Constant SLEEP_TIME_5_MIN_IN_MILIS. */
	private static final int SLEEP_TIME_5_MIN_IN_MILIS = SLEEP_MIN_TIME * SECOND_TO_MIN * MILIS_TO_SECOND;
	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
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


	/**
	 * Revise plane.
	 *
	 * @param planeToRevise the plane to revise
	 * @param status the status
	 */
	private void revisePlane(Plane planeToRevise,  boolean status) {
		
		String statusString = status ? OK : BROKEN;
		planeToRevise.getPlaneStatus().setTechnicalStatus(statusString);
		HibernateGeneric.updateObject(planeToRevise);		
	}

}
