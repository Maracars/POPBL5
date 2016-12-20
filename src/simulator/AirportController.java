package simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import domain.dao.DAOLane;
import domain.model.Airport;
import domain.model.Lane;
import domain.model.users.Admin;
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
			freeLaneList = DAOLane.getFreeLanes(airport.getId());
			if (activePlaneList.size() > 0 && freeLaneList != null) {
				PlaneThread plane = activePlaneList.get(0);
				Lane lane = freeLaneList.get(0);
				lane.setStatus(false);
				plane.setLane(lane);
				DAOLane.updateLane(lane);
				activePlaneList.remove(plane);
				Notification.sendNotification(MD5.encrypt(ADMIN),
						"Controller gives one PERMISSION to plane " 
								+ plane.getPlane().getSerial());
				plane.givePermission();

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
		boolean ret;
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		freeLaneList = DAOLane.getFreeLanes(airport.getId());
		if (activePlaneList.size() == 0 && freeLaneList != null) {

			Lane lane = freeLaneList.get(0);
			lane.setStatus(false);
			plane.setLane(lane);
			DAOLane.updateLane(lane);
			ret = true;

			Notification.sendNotification(MD5.encrypt(ADMIN),
					"Controller GIVES SPECIFIC PERMISSION to plane " 
							+ plane.getPlane().getSerial());

		} else {
			activePlaneList.add(plane);
			ret = false;

			Notification.sendNotification(MD5.encrypt(ADMIN),
					"Controller DENIES SPECIFIC PERMISSION to plane " 
							+ plane.getPlane().getSerial());
		}
		mutex.release();
		return ret;
	}

}
