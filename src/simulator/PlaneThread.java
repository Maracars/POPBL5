package simulator;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.Lane;
import domain.model.Node;
import domain.model.Path;
import domain.model.Plane;

// TODO: Auto-generated Javadoc
/**
 * The Class PlaneThread.
 */
public abstract class PlaneThread implements Runnable {

	/** The Constant FULL. */
	private static final boolean FULL = false;

	/** The Constant FREE. */
	private static final boolean FREE = true;

	/** The Constant ARRIVING. */
	protected final boolean ARRIVING = true;

	/** The Constant DEPARTURING. */
	protected static final boolean DEPARTURING = false;

	/** The plane. */
	protected Plane plane;

	/** The controller. */
	protected AirportController controller;

	/** The sem controller permision. */
	protected Semaphore semControllerPermision;

	/** The moment lane. */
	protected Lane momentLane;

	/** The lane. */
	protected Lane lane;

	/** The active planes. */
	protected AtomicInteger activePlanes;

	/** The mode. */
	protected boolean mode;

	/** The flight. */
	protected Flight flight;

	LinkedList<Path> routeOfPaths;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	abstract public void run();

	/**
	 * Go to destination.
	 */
	protected void goToDestine() {
		Lane lastLane;
		System.out.println("Go to destine");
		momentLane = routeOfPaths.get(0).getLaneList().get(0);
		plane.getPlaneMovement().setPositionX(momentLane.getStartNode().getPositionX());
		plane.getPlaneMovement().setPositionY(momentLane.getStartNode().getPositionY());
		HibernateGeneric.updateObject(plane);
		// moveInLane(momentLane);
		System.out.println("Landed");
		System.out.println("Lista de pistas: " + routeOfPaths);

		for (int countList = 0; countList < routeOfPaths.size(); countList++) {
			routeOfPaths.get(countList).changePathStatus(FULL);
			for (int countLaneList = 0; countLaneList < routeOfPaths.get(countList).getLaneList()
					.size(); countLaneList++) {
				lastLane = momentLane;
				momentLane = routeOfPaths.get(countList).getLaneList().get(countLaneList);
				moveInLane(momentLane, lastLane);
			}
			routeOfPaths.get(countList).changePathStatus(FREE);

		}
System.out.println("kk");
	}

	/**
	 * Checks if is mode.
	 *
	 * @return true, if is mode
	 */
	public boolean getMode() {
		return mode;
	}

	/**
	 * Gets the flight.
	 *
	 * @return the flight
	 */
	public Flight getFlight() {
		return flight;
	}

	/**
	 * Move in lane.
	 *
	 * @param laneWhereMove
	 *            the lane where move
	 * @param lastLane
	 */
	private void moveInLane(Lane laneWhereMove, Lane lastLane) {
		try {
			laneWhereMove.getSemaphore().acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("Interrupted plane");
			e.printStackTrace();
		}
		System.out.println(plane.getSerial() + " Enter to lane " + lane.getName());
		System.out.println("2");
		rotatePlane();
		movePlaneToEndOfLane(laneWhereMove, lastLane);
		System.out.println("3");
		laneWhereMove.getSemaphore().release();
		System.out.println(plane.getSerial() + " go out from lane " + lane.getName());
		System.out.println("4");
	}

	/**
	 * Change lane status.
	 *
	 * @param laneToChange
	 *            the lane to change
	 * @param status
	 *            the status
	 */
	protected void changeLaneStatus(Lane laneToChange, boolean status) {

		System.out.println(
				plane.getSerial() + "moving from " + laneToChange.getStartNode() + " to " + laneToChange.getEndNode());
		laneToChange.setStatus(status);
		HibernateGeneric.updateObject(laneToChange);
	}

	/**
	 * Move plane to end of lane.
	 */
	private void movePlaneToEndOfLane(Lane firstLane, Lane lastLane) {
		Node lastLaneEndNode = lastLane.getEndNode();
		Node lastLaneStartNode = lastLane.getStartNode();
		Node momentLaneEndNode = firstLane.getEndNode();
		Node momentLaneStartNode = firstLane.getStartNode();

		if (lastLaneEndNode.getName().equals(momentLaneStartNode.getName())
				|| lastLaneStartNode.getName().equals(momentLaneStartNode.getName())) {
			plane.getPlaneMovement().setPositionX(firstLane.getEndNode().getPositionX());
			plane.getPlaneMovement().setPositionY(firstLane.getEndNode().getPositionY());
		} else if (lastLaneEndNode.getName().equals(momentLaneEndNode.getName())
				|| lastLaneStartNode.getName().equals(momentLaneStartNode.getName())) {
			plane.getPlaneMovement().setPositionX(firstLane.getStartNode().getPositionX());
			plane.getPlaneMovement().setPositionY(firstLane.getStartNode().getPositionY());
		}

		HibernateGeneric.updateObject(plane);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Rotate plane.
	 */
	private void rotatePlane() {
		// TODO Auto-generated method stub

	}

	/**
	 * Give permission.
	 */
	public void givePermission() {
		semControllerPermision.release();
	}

	/**
	 * Sets the lane.
	 *
	 * @param lane
	 *            the new lane
	 */
	public void setLane(Lane lane) {
		this.lane = lane;
	}

	/**
	 * Gets the plane.
	 *
	 * @return the plane
	 */
	public Plane getPlane() {
		return plane;
	}

	public void setRouteOfPaths(LinkedList<Path> routeOfPaths) {
		this.routeOfPaths = routeOfPaths;
	}
}
