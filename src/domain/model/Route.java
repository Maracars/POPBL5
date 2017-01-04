package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

// TODO: Auto-generated Javadoc
/**
 * The Class Route.
 */
@Entity
public class Route {

	/** The id. */
	@Id
	@GeneratedValue
	Integer id;

	/** The arrival gate. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	Gate arrivalGate;

	/** The departure gate. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)

	Gate departureGate;

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
	 * Gets the arrival gate.
	 *
	 * @return the arrival gate
	 */
	public Gate getArrivalGate() {
		return arrivalGate;
	}

	/**
	 * Sets the arrival gate.
	 *
	 * @param arrivalGate the new arrival gate
	 */
	public void setArrivalGate(Gate arrivalGate) {
		this.arrivalGate = arrivalGate;
	}

	/**
	 * Gets the departure gate.
	 *
	 * @return the departure gate
	 */
	public Gate getDepartureGate() {
		return departureGate;
	}

	/**
	 * Sets the departure gate.
	 *
	 * @param departureGate the new departure gate
	 */
	public void setDepartureGate(Gate departureGate) {
		this.departureGate = departureGate;
	}

}
