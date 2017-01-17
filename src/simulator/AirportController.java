package simulator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import domain.dao.DAOGate;
import domain.dao.HibernateGeneric;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Lane;
import domain.model.Node;
import domain.model.Path;
import domain.model.users.Admin;
import helpers.Dijkstra;
import helpers.LaneFilter;
import helpers.MD5;
import notification.Notification;

/**
 * The Class AirportController.
 * <p>
 * Represents the airport controller on the simulation.
 * </p>
 */
public class AirportController implements Runnable {

	/** The Constant ADMIN. */
	private static final String ADMIN = new Admin().getClass().getSimpleName();

	/** The Constant SLEEP_TIME_5_SEGS_IN_MILIS. */
	private static final int SLEEP_TIME_5_SEGS_IN_MILIS = 5000;

	/** The active plane list. */
	private ArrayList<PlaneThread> activePlaneList = new ArrayList<PlaneThread>();

	private static List<Path> pathList;

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
	 * @param laneList
	 * @param pathList
	 */
	public AirportController(Airport airport, List<Path> pathList) {

		this.airport = airport;
		mutex = new Semaphore(1, true);
		AirportController.pathList = pathList;
	}


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
							"Controller gives one PERMISSION to plane "
									+ plane.getPlane().getSerial());
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
	 * Ask permission to the controller and will return the decission
	 *
	 * @param plane
	 *            the plane
	 * @return true, if has permission
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
	 * Planifies the landing asignment of a plane
	 *
	 * @param plane
	 *            the plane
	 * @return true, if successful
	 */
	private synchronized boolean allocateLaneIfFree(PlaneThread plane) {
		boolean ret = false;

		List<Lane> freeLaneList = LaneFilter.getFreeLanes(pathList, airport.getId());
		if (freeLaneList != null && freeLaneList.size() != 0 && assignFreeGate(plane)) {
			Lane lane = freeLaneList.get(0);
			LinkedList<Path> bestRoute = getBestRoute(plane.getMode(), lane, plane.getFlight());
			bestRoute.get(0).changePathStatus(false);
			plane.setRouteOfPaths(bestRoute);
			lane.setStatus(false);
			plane.setLane(lane);
			HibernateGeneric.updateObject(lane);
			ret = true;
		}
		return ret;
	}

	/**
	 * Assign free gate to a plane
	 *
	 * @param plane
	 *            the plane
	 * @return true, if successful
	 */
	private boolean assignFreeGate(PlaneThread plane) {
		Gate freeGate = null;
		boolean ret = true;
		if (plane.getMode() == plane.ARRIVING) {
			List<Gate> freeGateList = DAOGate
					.loadFreeGatesFromTerminal(plane.getFlight().getRoute().getArrivalTerminal().getId());
			if (freeGateList != null) {
				freeGate = freeGateList.get(0);
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
	 * Gets the best route from a point to another on the airpors internal road
	 * system
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
	public LinkedList<Path> getBestRoute(boolean mode, Lane landLane, Flight flight) {
		List<Path> paths = LaneFilter.getFreePaths(pathList);
		Node source;
		Node destination;

		if (mode == Dijkstra.ARRIVAL_MODE) {
			System.out.println("Dijkstra " + landLane.getEndNode() + "  " + flight.getEndGate().getPositionNode());
			source = landLane.getStartNode();
			destination = flight.getEndGate().getPositionNode();

		} else {
			System.out.println(
					"Dijkstra FROM" + flight.getStartGate().getPositionNode() + " TO " + landLane.getStartNode());
			source = flight.getStartGate().getPositionNode();
			destination = landLane.getEndNode();

		}

		Dijkstra dijkstra = new Dijkstra(paths);
		System.out.println("start dijkstra");
		dijkstra.execute(source);
		System.out.println("finish dijkstra");
		LinkedList<Path> pathList = dijkstra.getPath(destination);
		System.out.println("dijkstra returns " + pathList);

		return pathList;
	}

	/**
	 * Interrupts the controller thread
	 */
	public void interrupt() {
		Thread.currentThread().interrupt();
	}

}
