package simulator;

import java.util.ArrayList;
import java.util.List;

import domain.dao.DAOLane;
import domain.model.Lane;

public class AirportController implements Runnable {
	private ArrayList<PlaneThread> activePlaneList;
	private List<Lane> freeLaneList;

	@Override
	public void run() {
		while (true) {
			if (activePlaneList.size() > 0 && (freeLaneList = DAOLane.getFreeLanes(1)) != null) { //Aldatzeko aiport Id
				PlaneThread plane = activePlaneList.get(0);
				plane.setLane(freeLaneList.get(0));
				activePlaneList.remove(plane);
				plane.givePermission();
			}
		}

	}

	public boolean askPermission(PlaneThread plane) {
		boolean ret;
		if (activePlaneList.size() == 0 && (freeLaneList = DAOLane.getFreeLanes(1)) != null) { //Aldatzeko airport Id
			plane.setLane(freeLaneList.get(0));
			ret = true;
		} else {
			activePlaneList.add(plane);
			ret = false;
		}
		return ret;
	}

}
