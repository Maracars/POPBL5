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
		goOutFromMap();
		System.out.println("Departure plane went OUT");
		//set plane status arriving??

	}

	private void goOutFromMap() {
		// TODO Auto-generated method stub

	}

}
