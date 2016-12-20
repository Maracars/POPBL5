package domain.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Path {
	
	@Id @GeneratedValue
	private Integer id;
	
	@OneToMany
	private ArrayList<Lane> laneList = new ArrayList<>();
	
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

	public ArrayList<Lane> getLaneList() {
		return laneList;
	}

	public void setLaneList(ArrayList<Lane> laneList) {
		this.laneList = laneList;
	}

}
