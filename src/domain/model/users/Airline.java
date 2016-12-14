package domain.model.users;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import domain.model.Address;

/**
 * @author joanes
 *
 */
@Entity
public class Airline extends User {

	String name;

	@ManyToOne(optional = false) 
	Address address;

	public Airline() {
		super();
	}

	public Airline(User user) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setPassword(user.getPassword());
		this.setUsername(user.getUsername());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
