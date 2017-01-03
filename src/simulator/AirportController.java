package simulator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import domain.dao.DAOLane;
import domain.dao.HibernateGeneric;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Lane;
import domain.model.Node;
import domain.model.Path;
import domain.model.users.Admin;
import helpers.Dijkstra;
import helpers.MD5;
import notification.Notification;

public class AirportController implements Runnable {
	private static final String ADMIN = new Admin().getClass().getSimpleName();
	private static final int SLEEP_TIME_5_SEGS_IN_MILIS = 5000;
	private ArrayList<PlaneThread> activePlaneList = new ArrayList<PlaneThread>();
	private List<Lane> freeLaneList;
	private Airport airport;
	public Semaphore mutex;

	public Semaphore getMutex() {
		return mutex;
	}

	public AirportController(Airport airport) {

		this.airport = airport;
		mutex = new Semaphore(1, true);
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

	private boolean allocateLaneIfFree(PlaneThread plane) {
		boolean ret = false;
		freeLaneList = DAOLane.getFreeLanes(airport.getId());
		if (freeLaneList != null) {
			Lane lane = freeLaneList.get(0);
			lane.setStatus(false);
			plane.setLane(lane);
			DAOLane.updateLane(lane);
			ret = true;
		}
		return ret;
	}

	/*
	 * mode thread berak jakingo zauen zein dan eta lane asignaute deko ya
	 * (kontrollerrak permisoa emoterakoan
	 */
	public static LinkedList<Path> getBestRoute(boolean mode, Lane landLane, Flight flight) {
		/* mutexa badaezpada */
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		List<Object> objects = HibernateGeneric.loadAllObjects(new Path());
		mutex.release();
		List<Path> paths = new ArrayList<>();
		Node source;
		Node destination;
		for (Object path : objects) {
			paths.add((Path) path);
		}
		if (mode == Dijkstra.ARRIVAL_MODE) {

			source = landLane.getEndNode();
			destination = flight.getRoute().getArrivalGate().getPositionNode();

		} else {

			source = flight.getRoute().getArrivalGate().getPositionNode();
			destination = landLane.getStartNode();

		}

		Dijkstra dijkstra = new Dijkstra(paths);
		dijkstra.execute(source, mode);

		LinkedList<Path> pathList = dijkstra.getPath(destination);

		return pathList;
	}

}
