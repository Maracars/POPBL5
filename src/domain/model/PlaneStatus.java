package domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Class PlaneStatus.
 */
@Entity
public class PlaneStatus {
	
	/** The id. */
	@Id
	@GeneratedValue
	Integer id;
	
	/** The technical status. */
	@Column(nullable = false)
	String technicalStatus;
	
	/** The position status. */
	@Column(nullable = false)
	String positionStatus;

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
	 * Gets the technical status.
	 *
	 * @return the technical status
	 */
	public String getTechnicalStatus() {
		return technicalStatus;
	}

	/**
	 * Sets the technical status.
	 *
	 * @param technicalStatus the new technical status
	 */
	public void setTechnicalStatus(String technicalStatus) {
		this.technicalStatus = technicalStatus;
	}

	/**
	 * Gets the position status.
	 *
	 * @return the position status
	 */
	public String getPositionStatus() {
		return positionStatus;
	}

	/**
	 * Sets the position status.
	 *
	 * @param positionStatus the new position status
	 */
	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

}
