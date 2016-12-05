package simulator;

import java.util.concurrent.Semaphore;

import domain.model.Plane;

public class ArrivingPlane extends PlaneThread {

	public ArrivingPlane(Plane plane, AirportController controller) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
	}

	@Override
	public void run() {
		moveToAirport();
		if (!controller.askPermission(this)) {
			Thread waitingThread = new Thread(new MovePlaneInCircles(plane));
			// run?
			try {
				semControllerPermision.acquire();
				waitingThread.interrupt();
				landPlane();
				goToDestine();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void landPlane() {
		// TODO Auto-generated method stub

	}

	private void moveToAirport() {
		// TODO Auto-generated method stub

	}

}
