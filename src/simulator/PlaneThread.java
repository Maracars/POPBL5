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

/**
 * The Class PlaneThread.
 */
public abstract class PlaneThread implements Runnable {

	/** The Constant FULL. */
	protected static final boolean FULL = false;

	/** The Constant FREE. */
	protected static final boolean FREE = true;

	/** The Constant ARRIVING. */
	protected static final boolean ARRIVING = true;

	/** The Constant DEPARTURING. */
	protected static final boolean DEPARTURING = false;

	public static final Double FLIGHT_SPEED = 500.0;

	public static final Double CONSTANT_TIME = 1000000.0;

	public static final Double LAND_SPEED = 300.0;

	public static final Double LANE_SPEED = 100.0;
	
	protected static final double INIT_54_Y = -0.557062;
	protected static final double INIT_54_X = 51.464703;
	protected static final double INIT_B_Y = -0.357926;
	protected static final double INIT_B_X = 51.477588;

	/** The plane. */
	protected Plane plane;

	/** The controller. */
	protected AirportController controller;

	/** The sem controller permision. */
	protected Semaphore semControllerPermision;

	/** The moment lane. */
	protected Lane momentLane = null;

	/** The lane. */
	protected Lane landLane;

	/** The active planes. */
	protected AtomicInteger activePlanes;

	/** The mode. */
	protected boolean mode;

	/** The flight. */
	protected Flight flight;

	LinkedList<Path> routeOfPaths;

	@Override
	abstract public void run();

	/**
	 * Go to destination.
	 */
	protected void goToDestine() {
		Lane lastLane = momentLane;
		for (int countList = 0; countList < routeOfPaths.size(); countList++) {
			routeOfPaths.get(countList).changePathStatus(FULL);
			lastLane = moveInPath(routeOfPaths.get(countList), lastLane);
			routeOfPaths.get(countList).changePathStatus(FREE);

		}
	}

	private Lane moveInPath(Path path, Lane lastLane) {
		if (lastLane != null) {
			Node pathStartNode = path.getLaneList().get(0).getStartNode();
			Node pathEndNode = path.getLaneList().get(path.getLaneList().size() - 1).getEndNode();
			if (lastLane.getEndNode().getName().equals(pathStartNode.getName())
					|| lastLane.getStartNode().getName().equals(pathStartNode.getName())) {
				for (int countLaneList = 0; countLaneList < path.getLaneList().size(); countLaneList++) {
					momentLane = path.getLaneList().get(countLaneList);
					moveInLane(momentLane, lastLane);
					lastLane = momentLane;
				}
			} else if (lastLane.getEndNode().getName().equals(pathEndNode.getName())
					|| lastLane.getStartNode().getName().equals(pathEndNode.getName())) {
				for (int countLaneList = (path.getLaneList().size() - 1); countLaneList >= 0; countLaneList--) {
					momentLane = path.getLaneList().get(countLaneList);
					moveInLane(momentLane, lastLane);
					lastLane = momentLane;
				}
			}
		} else {
			for (int countLaneList = 0; countLaneList < path.getLaneList().size(); countLaneList++) {
				momentLane = path.getLaneList().get(countLaneList);
				moveInLane(momentLane, lastLane);
				lastLane = momentLane;
			}
		}
		return lastLane;
	}

	/**
	 * Move in lane.
	 *
	 * @param laneWhereMove
	 *            the lane where move
	 * @param lastLane
	 */
	protected void moveInLane(Lane laneWhereMove, Lane lastLane) {
		try {
			laneWhereMove.getSemaphore().acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		rotatePlane();
		movePlaneToEndOfLane(laneWhereMove, lastLane);
		laneWhereMove.getSemaphore().release();
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

		laneToChange.setStatus(status);
		HibernateGeneric.updateObject(laneToChange);
	}

	/**
	 * Move plane to end of lane.
	 */
	private void movePlaneToEndOfLane(Lane firstLane, Lane lastLane) {

		if (lastLane != null) {
			changePlanePositionNormal(firstLane, lastLane);
		} else {
			changePlanePositionWithoutLastLane(firstLane);
		}

		plane.getPlaneMovement().setSpeed(LANE_SPEED);

		HibernateGeneric.updateObject(plane);

		try {
			Thread.sleep((long) (CONSTANT_TIME / LANE_SPEED));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void changePlanePositionWithoutLastLane(Lane firstLane) {
			if (firstLane.getStartNode().getName().equals(flight.getStartGate().getPositionNode().getName())) {
				plane.getPlaneMovement().setPositionX(firstLane.getEndNode().getPositionX());
				plane.getPlaneMovement().setPositionY(firstLane.getEndNode().getPositionY());
			} else {
				plane.getPlaneMovement().setPositionX(firstLane.getStartNode().getPositionX());
				plane.getPlaneMovement().setPositionY(firstLane.getStartNode().getPositionY());
			}
	}

	private void changePlanePositionNormal(Lane firstLane, Lane lastLane) {
		Node lastLaneEndNode;
		Node lastLaneStartNode;
		Node momentLaneEndNode;
		Node momentLaneStartNode;

		lastLaneEndNode = lastLane.getEndNode();
		lastLaneStartNode = lastLane.getStartNode();
		momentLaneEndNode = firstLane.getEndNode();
		momentLaneStartNode = firstLane.getStartNode();

		if (lastLaneEndNode.getName().equals(momentLaneStartNode.getName())
				|| lastLaneStartNode.getName().equals(momentLaneStartNode.getName())) {
			plane.getPlaneMovement().setPositionX(firstLane.getEndNode().getPositionX());
			plane.getPlaneMovement().setPositionY(firstLane.getEndNode().getPositionY());
		} else if (lastLaneEndNode.getName().equals(momentLaneEndNode.getName())
				|| lastLaneStartNode.getName().equals(momentLaneEndNode.getName())) {
			plane.getPlaneMovement().setPositionX(firstLane.getStartNode().getPositionX());
			plane.getPlaneMovement().setPositionY(firstLane.getStartNode().getPositionY());
		}
	}

	/**
	 * Rotate plane.
	 */
	private void rotatePlane() {

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
		this.landLane = lane;
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
}
