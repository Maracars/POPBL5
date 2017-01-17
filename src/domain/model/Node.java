package domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Node.
 */
@Entity
@Table(name = "AirportNode")
public class Node {

	/** The id. */
	@Id
	@GeneratedValue
	private Integer id;

	/** The position X. */
	@Column(nullable = false)
	private double positionX; // Longitude

	/** The position Y. */
	@Column(nullable = false)
	private double positionY; // Latitude

	/** The name. */
	private String name;

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
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Sets the position X.
	 *
	 * @param positionX2
	 *            the new position X
	 */
	public void setPositionX(double positionX2) {
		this.positionX = positionX2;
	}

	/**
	 * Sets the position Y.
	 *
	 * @param positionY
	 *            the new position Y
	 */
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	/**
	 * Gets the position X.
	 *
	 * @return the position X
	 */
	public double getPositionX() {
		return positionX;
	}

	/**
	 * Gets the position Y.
	 *
	 * @return the position Y
	 */
	public double getPositionY() {
		return positionY;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + "positionX: " + positionX + "positionY: " + positionY;
	}

}
