package simulator;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOLane;
import domain.model.Plane;

public class DeparturingPlane extends PlaneThread {

	public DeparturingPlane(Plane plane, AirportController controller, AtomicInteger activePlanesNum) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
		this.activePlanes = activePlanesNum;
	}

	@Override
	public void run() {
		System.out.println("DEPARTURE plane ask PERMISSION");
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
		//goToDestine();
		System.out.println("Departure plane ready TO GO");
		
		System.out.println("Departure plane went OUT");
		goOutFromMap();
		//set plane status arriving??

	}

	private void goOutFromMap() {
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

}
