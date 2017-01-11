package simulator;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.users.Admin;
import helpers.MD5;
import notification.Notification;

// TODO: Auto-generated Javadoc
/**
 * The Class ArrivingPlane.
 */
public class ArrivingPlane extends PlaneThread {

	/** The Constant INITIAL_POSY. */
	private static final double INITIAL_POSY = 21.1;
	
	/** The Constant INITIAL_POSX. */
	private static final double INITIAL_POSX = 11.1;
	/** The Constant ADMIN. */
	private static final String ADMIN = new Admin().getClass().getSimpleName();

	/**
	 * Instantiates a new arriving plane.
	 *
	 * @param plane the plane
	 * @param controller the controller
	 * @param activePlanesNum the active planes num
	 * @param flight the flight
	 */
	public ArrivingPlane(Plane plane, AirportController controller, AtomicInteger activePlanesNum, Flight flight) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
		this.activePlanes = activePlanesNum;
		this.mode = ARRIVING;
		this.flight = flight;
	}

	/* (non-Javadoc)
	 * @see simulator.PlaneThread#run()
	 */
	@Override
	public void run() {
		Notification.sendNotification(MD5.encrypt(ADMIN), "Plane " + plane.getSerial() + " ARRIVING");
		moveToAirport();
		Notification.sendNotification(MD5.encrypt(ADMIN),
				"Plane " + plane.getSerial() + " ASK PERMISSION TO ARRIVE");

		if (!controller.askPermission(this)) {
			Thread waitingThread = new Thread(new MovePlaneInCircles(plane));
			//run??
			try {
				semControllerPermision.acquire();
				waitingThread.interrupt();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Interrupted plane");
				e.printStackTrace();
			}
		}

		Notification.sendNotification(MD5.encrypt(ADMIN), "Plane " + plane.getSerial() + " LANDED");

		goToDestine();
		landPlane();
		// set plane status OnAirport eta NeedRevision
	}

	/**
	 * Land plane.
	 */
	private void landPlane() {
		changeLaneStatus(lane, true);
		activePlanes.decrementAndGet();

	}

	/**
	 * Move to airport.
	 */
	private void moveToAirport() {
		try {
			controller.getMutex().acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		plane.getPlaneMovement().setPositionX(INITIAL_POSX);
		plane.getPlaneMovement().setPositionY(INITIAL_POSY);
		HibernateGeneric.updateObject(plane);
		controller.getMutex().release();

	}

}
