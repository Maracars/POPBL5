package domain.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Path {

	@Id
	@GeneratedValue
	private Integer id;

	@OneToMany
	private ArrayList<Lane> laneList = new ArrayList<>();

	private double distance = 0;

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
		for (Lane lane : laneList) {
			this.distance += calculateDistance(lane.getStartNode(), lane.getEndNode());
		}
	}

	// Honek ez dakit hemen egon biharko liakien, igual helper estatiko baten

	private double calculateDistance(Node src, Node dst) {

		return pitagor(src.getPositionX() - dst.getPositionX(), src.getPositionY() - dst.getPositionY());

	}

	private double pitagor(double x, double y) {
		return Math.sqrt((x * x) + (y * y));
	}

	@Override
	public String toString() {
		return laneList.toString() + distance;
	}

}
