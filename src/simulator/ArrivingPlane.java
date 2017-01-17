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
 * Class that contains a therad that represents an arriving plane.
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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			Thread.currentThread().interrupt();
			e1.printStackTrace();
		}
		System.out.println("Sartu de");
		startPlane();
		Notification.sendNotification(MD5.encrypt(ADMIN),
				"Plane " + plane.getSerial() + " ASK PERMISSION TO ARRIVE");

		if (!controller.askPermission(this)) {
			Thread waitingThread = new Thread(new MovePlaneInCircles(plane));
			moveToAirport();
			try {
				semControllerPermision.acquire();
				waitingThread.interrupt();
				
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Interrupted plane");
				e.printStackTrace();
			}
		}
		
		goToDestine();
		landPlane();
		// set plane status OnAirport eta NeedRevision
	}

	private void moveToAirport() {
		if(lane.getStartNode().getName().equals("A")){
			plane.getPlaneMovement().setPositionX(51.478920);
			plane.getPlaneMovement().setPositionX(-0.417926);
		}else{
			plane.getPlaneMovement().setPositionX(51.465125);
			plane.getPlaneMovement().setPositionX(-0.497062);
		}
		plane.getPlaneMovement().setSpeed(LAND_SPEED);
		HibernateGeneric.updateObject(plane);
		
		try {
			Thread.sleep((long) (CONSTANT_TIME / LAND_SPEED));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Land plane.
	 */
	private void landPlane() {
		Notification.sendNotification(MD5.encrypt(ADMIN), "Plane " + plane.getSerial() + " LANDED");
		activePlanes.decrementAndGet();
		System.out.println(plane.getSerial() + " LANDED");

	}

	/**
	 * Init the position of the plane
	 */
	private void startPlane() {
		double posx = flight.getRoute().getDepartureTerminal().getAirport().getPositionNode().getPositionX();
		double posy = flight.getRoute().getDepartureTerminal().getAirport().getPositionNode().getPositionY();
		plane.getPlaneMovement().setPositionX(posx);
		plane.getPlaneMovement().setPositionX(posy);
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
