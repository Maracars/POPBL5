package simulator;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOLane;
import domain.model.Plane;
import domain.model.users.Admin;
import helpers.MD5;
import notification.Notification;

// TODO: Auto-generated Javadoc
/**
 * The Class DeparturingPlane.
 */
public class DeparturingPlane extends PlaneThread {

	/** The Constant ADMIN. */
	private static final String ADMIN = new Admin().getClass().getSimpleName();

	/**
	 * Instantiates a new departuring plane.
	 *
	 * @param plane the plane
	 * @param controller the controller
	 * @param activePlanesNum the active planes number
	 */
	public DeparturingPlane(Plane plane, AirportController controller, AtomicInteger activePlanesNum) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
		this.activePlanes = activePlanesNum;
		this.mode = DEPARTURING;
	}

	/* (non-Javadoc)
	 * @see simulator.PlaneThread#run()
	 */
	@Override
	public void run() {
		Notification.sendNotification(MD5.encrypt(ADMIN),
				"Plane " + plane.getSerial() + " ASKED PERMISSION TO DEPARTURE");
		if (!controller.askPermission(this)) {
			Thread waitingThread = new Thread(new MovePlaneInCircles(plane));
			// run?
			try {
				semControllerPermision.acquire();
				waitingThread.interrupt();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Interrupted plane");
				e.printStackTrace();
			}
		}
		// goToDestine();
		goOutFromMap();
		// set plane status arriving??

	}

	/**
	 * Go out from map.
	 */
	private void goOutFromMap() {
		lane.setStatus(true);
		try {
			controller.mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Thread.currentThread().interrupt();
			System.out.println("Interrupted plane");
			e.printStackTrace();
		}
		DAOLane.updateLane(lane);
		controller.getMutex().release();
		activePlanes.decrementAndGet();

	}

}
