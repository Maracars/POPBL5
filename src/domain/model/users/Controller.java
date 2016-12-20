package domain.model.users;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import domain.model.Address;

@Entity
public class Controller extends User {

	String name;
	String secondName;

	@ManyToOne(optional = false)
	Address address;

	@Temporal(TemporalType.DATE)
	Date birthDate;

	public Controller(User user) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setPassword(user.getPassword());
		this.setUsername(user.getUsername());
	}

	public Controller() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
