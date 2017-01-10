package simulator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import domain.dao.DAOGate;
import domain.dao.DAOLane;
import domain.dao.DAOPath;
import domain.dao.HibernateGeneric;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Lane;
import domain.model.Node;
import domain.model.Path;
import domain.model.users.Admin;
import helpers.Dijkstra;
import helpers.MD5;
import notification.Notification;

// TODO: Auto-generated Javadoc
/**
 * The Class AirportController.
 */
public class AirportController implements Runnable {

	/** The Constant ADMIN. */
	private static final String ADMIN = new Admin().getClass().getSimpleName();

	/** The Constant SLEEP_TIME_5_SEGS_IN_MILIS. */
	private static final int SLEEP_TIME_5_SEGS_IN_MILIS = 5000;

	/** The active plane list. */
	private ArrayList<PlaneThread> activePlaneList = new ArrayList<PlaneThread>();

	/** The free lane list. */
	// private List<Lane> freeLaneList;

	/** The airport. */
	private Airport airport;

	/** The mutex. */
	public Semaphore mutex;

	/**
	 * Gets the mutex.
	 *
	 * @return the mutex
	 */
	public Semaphore getMutex() {
		return mutex;
	}

	/**
	 * Instantiates a new airport controller.
	 *
	 * @param airport
	 *            the airport
	 */
	public AirportController(Airport airport) {

		this.airport = airport;
		mutex = new Semaphore(1, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				mutex.acquire();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}

			if (activePlaneList.size() > 0) {
				PlaneThread plane = activePlaneList.get(0);
				if (allocateLaneIfFree(plane)) {
					activePlaneList.remove(plane);
					Notification.sendNotification(MD5.encrypt(ADMIN),
							"Controller gives one PERMISSION to plane " + plane.getPlane().getSerial());
					plane.givePermission();
				}

			}
			mutex.release();
			try {
				Thread.sleep(SLEEP_TIME_5_SEGS_IN_MILIS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Ask permission.
	 *
	 * @param plane
	 *            the plane
	 * @return true, if successful
	 */
	public boolean askPermission(PlaneThread plane) {
		boolean ret = false;
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

		if (activePlaneList.size() == 0) {
			ret = allocateLaneIfFree(plane);
		}

		if (ret) {
			Notification.sendNotification(MD5.encrypt(ADMIN),
					"Controller GIVES SPECIFIC PERMISSION to plane " + plane.getPlane().getSerial());
		} else {
			activePlaneList.add(plane);
			ret = false;

			Notification.sendNotification(MD5.encrypt(ADMIN),
					"Controller DENIES SPECIFIC PERMISSION to plane " + plane.getPlane().getSerial());
		}
		mutex.release();
		return ret;
	}

	/**
	 * Allocate lane if free.
	 *
	 * @param plane
	 *            the plane
	 * @return true, if successful
	 */
	private boolean allocateLaneIfFree(PlaneThread plane) {
		boolean ret = false;

		List<Lane> freeLaneList = DAOLane.getFreeLanes(airport.getId());
		if (freeLaneList != null && assignFreeGate(plane)) {
			Lane lane = freeLaneList.get(0);
			lane.setStatus(false);
			plane.setLane(lane);
			HibernateGeneric.updateObject(lane);
			ret = true;
		}
		return ret;
	}

	/**
	 * Assign free gate.
	 *
	 * @param plane the plane
	 * @return true, if successful
	 */
	private boolean assignFreeGate(PlaneThread plane) {
		Gate freeGate = null;
		boolean ret = true;
		if (plane.isMode() == plane.ARRIVING) {
			List<Gate> freeGateList = DAOGate
					.loadFreeGatesFromTerminal(plane.getFlight().getRoute().getArrivalTerminal().getId());
			freeGate = freeGateList.get(0);
			if (freeGate != null) {
				plane.getFlight().setEndGate(freeGate);
				freeGate.setFree(false);
				HibernateGeneric.updateObject(freeGate);
				HibernateGeneric.updateObject(plane.getFlight());
			} else {
				ret = false;
			}
		}
		return ret;
	}

	/**
	 * Gets the best route.
	 *
	 * @param mode
	 *            the mode
	 * @param landLane
	 *            the land lane
	 * @param flight
	 *            the flight
	 * @return the best route
	 */
	/*
	 * mode thread berak jakingo zauen zein dan eta lane asignaute deko ya
	 * (kontrollerrak permisoa emoterakoan
	 */
	public static LinkedList<Path> getBestRoute(boolean mode, Lane landLane, Flight flight) {
		List<Path> paths = DAOPath.loadAllFreePaths();
		Node source;
		Node destination;

		if (mode == Dijkstra.ARRIVAL_MODE) {

			source = landLane.getEndNode();
			destination = flight.getEndGate().getPositionNode();

		} else {

			source = flight.getStartGate().getPositionNode();
			destination = landLane.getStartNode();

		}

		Dijkstra dijkstra = new Dijkstra(paths);
		dijkstra.execute(source, mode);

		LinkedList<Path> pathList = dijkstra.getPath(destination);

		return pathList;
	}

	/**
	 * Interrupt.
	 */
	public void interrupt() {
		Thread.currentThread().interrupt();	
	}

}
