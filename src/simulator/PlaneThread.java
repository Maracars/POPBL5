package simulator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

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

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	abstract public void run();

	/**
	 * Go to destine.
	 */
	protected void goToDestine() {
		while (isPlaneInPosition()) {
			// Hemen suposatzen dot planea edukiko dauela
			LinkedList<Path> listaPistas = 
					AirportController.getBestRoute(mode, lane, new Flight());
			momentLane = listaPistas.get(0).getLaneList().get(0);
			try {
				momentLane.getSemaphore().acquire();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Interrupted plane");
				e.printStackTrace();
			}
			momentLane.setStatus(FULL);
			rotatePlane();
			movePlaneToEndOfLane();
			momentLane.getSemaphore().release();
			momentLane.setStatus(FREE);
		}

	}

	/**
	 * Gets the best route.
	 *
	 * @return the best route
	 */
	private ArrayList<Lane> getBestRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Move plane to end of lane.
	 */
	private void movePlaneToEndOfLane() {
		// TODO Auto-generated method stub

	}

	/**
	 * Rotate plane.
	 */
	private void rotatePlane() {
		// TODO Auto-generated method stub

	}

	/**
	 * Checks if is plane in position.
	 *
	 * @return true, if is plane in position
	 */
	private boolean isPlaneInPosition() {
		// TODO Auto-generated method stub
		return false;
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
	 * @param lane the new lane
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
