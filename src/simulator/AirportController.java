package simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import domain.dao.DAOLane;
import domain.model.Airport;
import domain.model.Lane;

public class AirportController implements Runnable {
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
		while (true) {
			try {
				mutex.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (activePlaneList.size() > 0 && (freeLaneList = DAOLane.getFreeLanes(airport.getId())) != null) {
				PlaneThread plane = activePlaneList.get(0);
				Lane lane = freeLaneList.get(0);
				lane.setStatus(false);
				plane.setLane(lane);
				DAOLane.updateLane(lane);
				activePlaneList.remove(plane);
				plane.givePermission();
				System.out.println("Controller gives one PERMISSION to plane " + plane.getPlane().getSerial());
			}
			mutex.release();
			try {
				Thread.sleep(SLEEP_TIME_5_SEGS_IN_MILIS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		if (activePlaneList.size() == 0 && (freeLaneList = DAOLane.getFreeLanes(airport.getId())) != null) {

			Lane lane = freeLaneList.get(0);
			lane.setStatus(false);
			plane.setLane(lane);
			DAOLane.updateLane(lane);
			ret = true;
			System.out.println("Controller GIVES SPECIFIC PERMISSION to plane " + plane.getPlane().getSerial());
		} else {
			activePlaneList.add(plane);
			ret = false;
			System.out.println("Controller DENIES SPECIFIC PERMISSION to plane " + plane.getPlane().getSerial());
		}
		mutex.release();
		return ret;
	}

}
