package simulator;

import java.util.concurrent.Semaphore;

import domain.model.Plane;

public class DeparturingPlane extends PlaneThread {

	public DeparturingPlane(Plane plane, AirportController controller) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
	}

	@Override
	public void run() {
		if (controller.askPermission(this) != true) {
			Thread waitingThread = new Thread(new MovePlaneInCircles(plane));
			// run?
			try {
				semControllerPermision.acquire();
				waitingThread.interrupt();
				goToDestine();
				goOutFromMap();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void goOutFromMap() {
		// TODO Auto-generated method stub

	}

}
