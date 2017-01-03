package domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

// TODO: Auto-generated Javadoc
/**
 * The Class PlaneMovement.
 */
@Entity
public class PlaneMovement {

	/** The id. */
	@Id
	@GeneratedValue
	Integer id;
	
	/** The direction X. */
	@Column(nullable = false)
	Double directionX;
	
	/** The direction Y. */
	@Column(nullable = false)
	Double directionY;
	
	/** The position X. */
	@Column(nullable = false)
	Double positionX;
	
	/** The position Y. */
	@Column(nullable = false)
	Double positionY;
	
	/** The speed. */
	@Column(nullable = false)
	Double speed;

	/** The plane. */
	@ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

	Plane plane;

	/**
	 * Gets the plane.
	 *
	 * @return the plane
	 */
	public Plane getPlane() {
		return plane;
	}

	/**
	 * Sets the plane.
	 *
	 * @param plane the new plane
	 */
	public void setPlane(Plane plane) {
		this.plane = plane;
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
	 * Gets the direction X.
	 *
	 * @return the direction X
	 */
	public Double getDirectionX() {
		return directionX;
	}

	/**
	 * Sets the direction X.
	 *
	 * @param directionX the new direction X
	 */
	public void setDirectionX(Double directionX) {
		this.directionX = directionX;
	}

	/**
	 * Gets the direction Y.
	 *
	 * @return the direction Y
	 */
	public Double getDirectionY() {
		return directionY;
	}

	/**
	 * Sets the direction Y.
	 *
	 * @param directionY the new direction Y
	 */
	public void setDirectionY(Double directionY) {
		this.directionY = directionY;
	}

	/**
	 * Gets the position X.
	 *
	 * @return the position X
	 */
	public Double getPositionX() {
		return positionX;
	}

	/**
	 * Sets the position X.
	 *
	 * @param positionX the new position X
	 */
	public void setPositionX(Double positionX) {
		this.positionX = positionX;
	}

	/**
	 * Gets the position Y.
	 *
	 * @return the position Y
	 */
	public Double getPositionY() {
		return positionY;
	}

	/**
	 * Sets the position Y.
	 *
	 * @param positionY the new position Y
	 */
	public void setPositionY(Double positionY) {
		this.positionY = positionY;
	}

	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public Double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed.
	 *
	 * @param speed the new speed
	 */
	public void setSpeed(Double speed) {
		this.speed = speed;
	}

}
