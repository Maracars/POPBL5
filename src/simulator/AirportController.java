package simulator;

import java.util.ArrayList;
import java.util.List;

import domain.dao.DAOLane;
import domain.model.Airport;
import domain.model.Lane;

public class AirportController implements Runnable {
	private ArrayList<PlaneThread> activePlaneList = new ArrayList<PlaneThread>();
	private List<Lane> freeLaneList;
	private Airport airport;

	public AirportController(Airport airport) {

		this.airport = airport;
	}

	@Override
	public void run() {
		while (true) {
			if (activePlaneList.size() > 0 && (freeLaneList = DAOLane.getFreeLanes(airport.getId())) != null) {
				PlaneThread plane = activePlaneList.get(0);
				plane.setLane(freeLaneList.get(0));
				activePlaneList.remove(plane);
				plane.givePermission();
				System.out.println("Controller gives one PERMISSION");
			}
		}

	}

	public boolean askPermission(PlaneThread plane) {
		boolean ret;
		if (activePlaneList.size() == 0 && (freeLaneList = DAOLane.getFreeLanes(airport.getId())) != null) {
			plane.setLane(freeLaneList.get(0));
			ret = true;
			System.out.println("Controller gives specific PERMISSION");
		} else {
			activePlaneList.add(plane);
			ret = false;
			System.out.println("Controller DENIES specific PERMISSION");
		}
		return ret;
	}

}
