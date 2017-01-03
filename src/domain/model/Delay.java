package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

// TODO: Auto-generated Javadoc
/**
 * The Class Delay.
 */
@Entity
public class Delay {

	/** The id. */
	@Id
	@GeneratedValue
	Integer id;

	/** The time in minutes. */
	Integer timeInMinutes;

	/** The description. */
	String description;

	/** The affected flight. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)

	Flight affectedFlight;

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
	 * Gets the time in minutes.
	 *
	 * @return the time in minutes
	 */
	public Integer getTimeInMinutes() {
		return timeInMinutes;
	}

	/**
	 * Sets the time in minutes.
	 *
	 * @param timeInMinutes the new time in minutes
	 */
	public void setTimeInMinutes(Integer timeInMinutes) {
		this.timeInMinutes = timeInMinutes;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the affected flight.
	 *
	 * @return the affected flight
	 */
	public Flight getAffectedFlight() {
		return affectedFlight;
	}

	/**
	 * Sets the affected flight.
	 *
	 * @param affectedFlight the new affected flight
	 */
	public void setAffectedFlight(Flight affectedFlight) {
		this.affectedFlight = affectedFlight;
	}

}
