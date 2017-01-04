package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

// TODO: Auto-generated Javadoc
/**
 * The Class Airport.
 */
@Entity
public class Airport {
	
	/** The id. */
	@Id
	@GeneratedValue
	Integer id;

	/** The name. */
	String name;

	/** The max flights. */
	Integer maxFlights;
	
	/** The locale. */
	boolean locale;
	
	/** The position node. */
	@OneToOne
	Node positionNode;

	/**
	 * Sets the locale.
	 *
	 * @param locale the new locale
	 */
	public void setLocale(boolean locale) {
		this.locale = locale;
	}

	/** The address. */
	@ManyToOne(optional = false)
	Address address;

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
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the max flights.
	 *
	 * @return the max flights
	 */
	public Integer getMaxFlights() {
		return maxFlights;
	}

	/**
	 * Sets the max flights.
	 *
	 * @param maxFlights the new max flights
	 */
	public void setMaxFlights(Integer maxFlights) {
		this.maxFlights = maxFlights;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Gets the position node.
	 *
	 * @return the position node
	 */
	public Node getPositionNode() {
		return positionNode;
	}

	/**
	 * Sets the position node.
	 *
	 * @param positionNode the new position node
	 */
	public void setPositionNode(Node positionNode) {
		this.positionNode = positionNode;
	}

}
