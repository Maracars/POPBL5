package simulator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOLane;
import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.Lane;
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
	protected static final boolean ARRIVING = true;

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

	protected Flight flight;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	abstract public void run();

	/**
	 * Go to destine.
	 */
	protected void goToDestine() {

		momentLane = lane;
		plane.getPlaneMovement().setPositionX(momentLane.getStartNode().getPositionX());
		plane.getPlaneMovement().setPositionY(momentLane.getStartNode().getPositionY());
		moveInLane(momentLane);

		LinkedList<Path> listaPistas = AirportController.getBestRoute(mode, momentLane, flight);

		for (int countList = 0; countList < listaPistas.size(); countList++) {
			for (int countLaneList = 0; countLaneList < listaPistas.get(countList).getLaneList()
					.size(); countLaneList++) {
				// Hemen suposatzen dot planea edukiko dauela
				momentLane = listaPistas.get(countList).getLaneList().get(countLaneList);
				moveInLane(momentLane);
			}
		}

	}

	public boolean isMode() {
		return mode;
	}

	public Flight getFlight() {
		return flight;
	}

	private void moveInLane(Lane laneWhereMove) {
		try {
			laneWhereMove.getSemaphore().acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("Interrupted plane");
			e.printStackTrace();
		}
		changeLaneStatus(laneWhereMove, FULL);
		rotatePlane();
		movePlaneToEndOfLane();
		laneWhereMove.getSemaphore().release();
		changeLaneStatus(laneWhereMove, FREE);
	}

	protected void changeLaneStatus(Lane laneToChange, boolean status) {
		laneToChange.setStatus(status);
		try {
			controller.mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Thread.currentThread().interrupt();
			System.out.println("Interrupted plane");
			e.printStackTrace();
		}
		HibernateGeneric.updateObject(laneToChange);
		controller.getMutex().release();
	}

	/**
	 * Move plane to end of lane.
	 */
	private void movePlaneToEndOfLane() {
		plane.getPlaneMovement().setPositionX(momentLane.getEndNode().getPositionX());
		plane.getPlaneMovement().setPositionY(momentLane.getEndNode().getPositionY());
		HibernateGeneric.updateObject(plane);
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
}
