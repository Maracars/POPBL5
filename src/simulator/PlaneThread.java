package simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.HibernateGeneric;
import domain.model.Lane;
import domain.model.Path;
import domain.model.Plane;
import helpers.Dijkstra;

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
			//Hemen suposatzen dot planea edukiko dauela
			ArrayList<Lane> listaPistas = getBestRoute();
			momentLane = listaPistas.get(0);
			try {
				momentLane.getSemaphore().acquire();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Interrupted plane");
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



	public void givePermission() {
		semControllerPermision.release();
	}

	public void setLane(Lane lane) {
		this.lane = lane;
	}

	public Plane getPlane() {
		return plane;
	}
}
