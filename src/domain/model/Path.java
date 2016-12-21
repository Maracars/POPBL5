package domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import helpers.DistanceCalculator;

@Entity
public class Path {

	@Id
	@GeneratedValue
	private Integer id;

	@OneToMany
	private List<Lane> laneList = new ArrayList<Lane>();

	@Column(nullable = false)
	private double distance;

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Lane> getLaneList() {
		return laneList;
	}

	public void setLaneList(ArrayList<Lane> laneList) {
		this.laneList = laneList;
		for (Lane lane : laneList) {
			this.distance += DistanceCalculator.calculateDistance(lane.getStartNode(), lane.getEndNode());
		}
	}

	@Override
	public String toString() {
		return laneList.toString() + distance;
	}

}
