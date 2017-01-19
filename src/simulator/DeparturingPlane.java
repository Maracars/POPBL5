package simulator;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOGate;
import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Plane;
import domain.model.users.Admin;
import helpers.MD5;
import notification.Notification;

/**
 * The Class DeparturingPlane.
 */
public class DeparturingPlane extends PlaneThread {

	/** The Constant ADMIN. */
	private static final String ADMIN = new Admin().getClass().getSimpleName();

	/**
	 * Instantiates a new departuring plane.
	 *
	 * @param plane
	 *            the plane
	 * @param controller
	 *            the controller
	 * @param activePlanesNum
	 *            the active planes number
	 * @param flight
	 *            the flight
	 */
	public DeparturingPlane(Plane plane, AirportController controller, AtomicInteger activePlanesNum, Flight flight) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
		this.activePlanes = activePlanesNum;
		this.mode = DEPARTURING;
		this.flight = flight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulator.PlaneThread#run()
	 */
	@Override
	public void run() {
		Gate gate = DAOGate.getNodeFromPosXPosY(plane.getPlaneMovement().getPositionX(), plane.getPlaneMovement().getPositionY());
		flight.setStartGate(gate);
		HibernateGeneric.saveObject(flight);
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
			}
		}
		goToDestine();
		goOutFromMap();

	}

	/**
	 * Go out from map.
	 */
	private void goOutFromMap() {
		if (momentLane.getStartNode().getName().equals("B")) {
			plane.getPlaneMovement().setPositionX(INIT_B_X);
			plane.getPlaneMovement().setPositionY(INIT_B_Y);
		} else {
			plane.getPlaneMovement().setPositionX(INIT_54_X);
			plane.getPlaneMovement().setPositionY(INIT_54_Y);
		}
		
		plane.getPlaneMovement().setSpeed(FLIGHT_SPEED);
		HibernateGeneric.updateObject(plane);
		
		try {
			Thread.sleep((long) (CONSTANT_TIME / FLIGHT_SPEED));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		double posx = flight.getRoute().getArrivalTerminal().getAirport().getPositionNode().getPositionX();
		double posy = flight.getRoute().getArrivalTerminal().getAirport().getPositionNode().getPositionY();
		plane.getPlaneMovement().setPositionX(posx);
		plane.getPlaneMovement().setPositionY(posy);
		HibernateGeneric.updateObject(plane);

		try {
			Thread.sleep((long) (CONSTANT_TIME / FLIGHT_SPEED));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		plane.getPlaneStatus().setPositionStatus("DEPARTURED");
			
		activePlanes.decrementAndGet();
		HibernateGeneric.updateObject(plane.getPlaneStatus());

	}

}
