package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	Terminal arrivalTerminal;

	/**
	 * Sets the arrival terminal.
	 *
	 * @param arrivalTerminal
	 *            the new arrival terminal
	 */
	public void setArrivalTerminal(Terminal arrivalTerminal) {
		this.arrivalTerminal = arrivalTerminal;
	}

	/**
	 * Sets the departure terminal.
	 *
	 * @param departureTerminal
	 *            the new departure terminal
	 */
	public void setDepartureTerminal(Terminal departureTerminal) {
		this.departureTerminal = departureTerminal;
	}

	/** The departure gate. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	Terminal departureTerminal;

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
	 * Gets the arrival terminal.
	 *
	 * @return the arrival terminal
	 */
	public Terminal getArrivalTerminal() {
		return arrivalTerminal;
	}

	/**
	 * Gets the departure terminal.
	 *
	 * @return the departure terminal
	 */
	public Terminal getDepartureTerminal() {
		return departureTerminal;
	}

}
