package simulator;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.model.Lane;
import domain.model.Plane;

public abstract class PlaneThread implements Runnable {
	private static final boolean FULL = false;
	private static final boolean FREE = true;
	protected Plane plane;
	protected AirportController controller;
	protected Semaphore semControllerPermision;
	protected Lane momentLane;
	protected Lane lane;
	protected AtomicInteger activePlanes;

	@Override
	abstract public void run();

	protected void goToDestine() {
		while (isPlaneInPosition()) {
			ArrayList<Lane> listaPistas = getBestRoute();
			momentLane = listaPistas.get(0);
			try {
				momentLane.getSemaphore().acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			momentLane.setStatus(FULL);
			rotatePlane();
			movePlaneToEndOfLane();
			momentLane.getSemaphore().release();
			momentLane.setStatus(FREE);
		}

	}

	private void movePlaneToEndOfLane() {
		// TODO Auto-generated method stub

	}

	private void rotatePlane() {
		// TODO Auto-generated method stub

	}

	private boolean isPlaneInPosition() {
		// TODO Auto-generated method stub
		return false;
	}

	private ArrayList<Lane> getBestRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	public void givePermission() {
		semControllerPermision.release();
	}

	public void setLane(Lane lane) {
		this.lane = lane;
	}
}
