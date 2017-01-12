package domain.model.users;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import domain.model.Address;

// TODO: Auto-generated Javadoc
/**
 * The Class Airline.
 *
 * @author joanes
 */
@Entity
public class Airline extends User {

	/** The name. */
	String name;

	/** The address. */
	@ManyToOne(optional = false) 
	Address address;

	/**
	 * Instantiates a new airline.
	 */
	public Airline() {
		super();
	}

	/**
	 * Instantiates a new airline.
	 *
	 * @param user the user
	 */
	public Airline(User user) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setPassword(user.getPassword());
		this.setUsername(user.getUsername());
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
	
	

}
