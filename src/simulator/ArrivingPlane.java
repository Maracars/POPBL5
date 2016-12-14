package simulator;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOLane;
import domain.model.Plane;

public class ArrivingPlane extends PlaneThread {

	public ArrivingPlane(Plane plane, AirportController controller, AtomicInteger activePlanesNum) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
		this.activePlanes = activePlanesNum;
	}

	@Override
	public void run() {
		//set plane status waiting to arrive (hau hemen edo thread zortuterakoan)
		moveToAirport();
		System.out.println("Arriving plane ASK PERMISSION");
		if (!controller.askPermission(this)) {
			Thread waitingThread = new Thread(new MovePlaneInCircles(plane));
			// run?
			try {
				semControllerPermision.acquire();
				waitingThread.interrupt();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Arriving plane LANDED");
		//goToDestine();
		System.out.println("Arriving plane went to PARKING");
		landPlane();
		//set plane status OnAirport eta NeedRevision
	}

	private void landPlane() {
		lane.setStatus(true);
		try {
			controller.mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAOLane.updateLane(lane);
		controller.mutex.release();
		activePlanes.decrementAndGet();

	}

	private void moveToAirport() {
		// TODO Auto-generated method stub

	}

}
