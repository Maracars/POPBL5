package simulator;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.users.Admin;
import helpers.MD5;
import notification.Notification;

/**
 * The Class ArrivingPlane. Class that contains a thread that represents an
 * arriving plane.
 */
public class ArrivingPlane extends PlaneThread {

	private static final int SLEEP_ARRIVING_PLANE = 1000;
	private static final double INIT_54_Y = -0.497062;
	private static final double INIT_54_X = 51.465125;
	private static final double INIT_B_Y = -0.417926;
	private static final double INIT_B_X = 51.478920;
	private static final String STRING_PLANE = "Plane ";
	/** The Constant ADMIN. */
	private static final String ADMIN = new Admin().getClass().getSimpleName();

	/**
	 * Instantiates a new arriving plane.
	 *
	 * @param plane
	 *            the plane
	 * @param controller
	 *            the controller
	 * @param activePlanesNum
	 *            the active planes num
	 * @param flight
	 *            the flight
	 */
	public ArrivingPlane(Plane plane, AirportController controller, AtomicInteger activePlanesNum, Flight flight) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
		this.activePlanes = activePlanesNum;
		this.mode = ARRIVING;
		this.flight = flight;
	}

	@Override
	public void run() {
		Notification.sendNotification(MD5.encrypt(ADMIN), STRING_PLANE + plane.getSerial() + " ARRIVING");
		try {
			Thread.sleep(SLEEP_ARRIVING_PLANE);
		} catch (InterruptedException e1) {
			Thread.currentThread().interrupt();
			e1.printStackTrace();
		}
		startPlane();
		Notification.sendNotification(MD5.encrypt(ADMIN),
				STRING_PLANE + plane.getSerial() + " ASK PERMISSION TO ARRIVE");

		if (!controller.askPermission(this)) {
			Thread waitingThread = new Thread(new MovePlaneInCircles(plane));
			try {
				semControllerPermision.acquire();
				waitingThread.interrupt();

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
		moveToAirport();
		goToDestine();
		landPlane();
		// set plane status OnAirport eta NeedRevision
	}

	private void moveToAirport() {
		if (lane.getStartNode().getName().equals("B")) {
			plane.getPlaneMovement().setPositionX(INIT_B_X);
			plane.getPlaneMovement().setPositionY(INIT_B_Y);
		} else {
			plane.getPlaneMovement().setPositionX(INIT_54_X);
			plane.getPlaneMovement().setPositionY(INIT_54_Y);
		}
		plane.getPlaneMovement().setSpeed(LAND_SPEED);
		HibernateGeneric.updateObject(plane);

		try {
			Thread.sleep((long) (CONSTANT_TIME / LAND_SPEED));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Land plane.
	 */
	private void landPlane() {
		Notification.sendNotification(MD5.encrypt(ADMIN), STRING_PLANE + plane.getSerial() + " LANDED");
		activePlanes.decrementAndGet();

	}

	/**
	 * Init the position of the plane
	 */
	private void startPlane() {
		double posx = flight.getRoute().getDepartureTerminal().getAirport().getPositionNode().getPositionX();
		double posy = flight.getRoute().getDepartureTerminal().getAirport().getPositionNode().getPositionY();
		plane.getPlaneMovement().setPositionX(posx);
		plane.getPlaneMovement().setPositionY(posy);
		plane.getPlaneMovement().setSpeed(FLIGHT_SPEED);
		HibernateGeneric.updateObject(plane);

		try {
			Thread.sleep((long) (CONSTANT_TIME / FLIGHT_SPEED));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
