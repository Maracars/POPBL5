package simulator;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOLane;
import domain.model.Plane;
import domain.model.users.Admin;
import helpers.MD5;
import notification.PGSocketIONotify;

public class DeparturingPlane extends PlaneThread {

	private static final String ADMIN = new Admin().getClass().getSimpleName();

	public DeparturingPlane(Plane plane, AirportController controller, AtomicInteger activePlanesNum) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
		this.activePlanes = activePlanesNum;
	}

	@Override
	public void run() {
		PGSocketIONotify.sendNotification(MD5.encrypt(ADMIN),
				"Plane " + plane.getSerial() + " ASKED PERMISSION TO DEPARTURE");
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
		// goToDestine();
		System.out.println("Plane " + plane.getSerial() + " TOOK OFF");
		goOutFromMap();
		// set plane status arriving??

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
		controller.getMutex().release();
		activePlanes.decrementAndGet();

	}

}
