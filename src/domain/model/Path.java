package domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import helpers.DistanceCalculator;

// TODO: Auto-generated Javadoc
/**
 * The Class Path.
 */
@Entity
public class Path {

	/** The id. */
	@Id
	@GeneratedValue
	private Integer id;

	/** The lane list. */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "pathId")
	private List<Lane> laneList = new ArrayList<Lane>();

	/** The distance. */
	@Column(nullable = false)
	private double distance;

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Sets the distance.
	 *
	 * @param distance the new distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the lane list.
	 *
	 * @return the lane list
	 */
	public List<Lane> getLaneList() {
		return laneList;
	}

	/**
	 * Sets the lane list.
	 *
	 * @param laneList the new lane list
	 */
	public void setLaneList(ArrayList<Lane> laneList) {
		this.laneList = laneList;
		for (Lane lane : laneList) {
			this.distance += DistanceCalculator.calculateDistance(lane.getStartNode(), lane.getEndNode());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return laneList.toString() + distance;
	}

	public boolean isFree() {
		for (Lane lane : laneList) {
			if(!lane.isFree()){
				return false;
			}
		}
		return true;
	}

}
