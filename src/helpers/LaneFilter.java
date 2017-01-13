package helpers;

import java.util.ArrayList;
import java.util.List;

import domain.model.Lane;
import domain.model.Path;

public class LaneFilter {

	public static List<Lane> getFreeLanes(List<Path> pathList, int airportId) {
		List<Lane> freeLaneList = new ArrayList<Lane>();
		for (Path path : pathList) {
			for (Lane lane : path.getLaneList()) {
				if (lane.isFree() && lane.getType().equals("PRINCIPAL")) {
					freeLaneList.add(lane);
				}
			}
		}
		return freeLaneList;

	}

	public static List<Path> getFreePaths(List<Path> pathList) {
		List<Path> freePathList = new ArrayList<Path>();
		for (Path path : pathList) {
			if (path.isFree()) {
				freePathList.add(path);
			}
		}
		return freePathList;
	}

}
