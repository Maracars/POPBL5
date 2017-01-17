package helpers;

import java.util.ArrayList;
import java.util.List;

import domain.model.Lane;
import domain.model.Path;

/**
 * The Class LaneFilter.
 */
public class LaneFilter {

	/**
	 * Gets the free lanes.
	 *
	 * @param pathList
	 *            the path list
	 * @param airportId
	 *            the airport id
	 * @return the free lanes
	 */
	public static List<Lane> getFreeLanes(List<Path> pathList, int airportId) {
		List<Lane> freeLaneList = new ArrayList<Lane>();
		for (Path path : pathList) {
			for (Lane lane : path.getLaneList()) {
				if (lane.isFree() && lane.getType().equals("PRINCIPAL") 
						&& lane.getAirport().getId() == airportId) {
					freeLaneList.add(lane);
				}
			}
		}
		return freeLaneList;

	}

	/**
	 * Gets the free paths.
	 *
	 * @param pathList
	 *            the path list
	 * @return the free paths
	 */
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
