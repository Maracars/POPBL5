package domain.model.users;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import domain.model.Address;
import domain.model.Route;

/**
 * @author joanes
 *
 */
@Entity
public class Airline extends User {

	String name;

	@ManyToMany(cascade = CascadeType.ALL)
	Collection<Route> routesList = new ArrayList<>();
	
	@ManyToOne//(cascade = CascadeType.PERSIST)
	@NotNull
	Address address;

	public Airline(){
		super();
	}
	
	public Airline(User user) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setPassword(user.getPassword());
		this.setUsername(user.getUsername());
	}

	public Collection<Route> getRoutesList() {
		return routesList;
	}

	public void setRoutesList(Collection<Route> routesList) {
		this.routesList = routesList;
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
