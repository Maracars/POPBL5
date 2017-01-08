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
	Terminal arrivalTerminal;

	public void setArrivalTerminal(Terminal arrivalTerminal) {
		this.arrivalTerminal = arrivalTerminal;
	}

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
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public Terminal getArrivalTerminal() {
		return arrivalTerminal;
	}

	public Terminal getDepartureTerminal() {
		return departureTerminal;
	}

}
