package domain.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Passenger extends User {

	// TODO kanpuak finkatzeko
	@ManyToMany(mappedBy = "passengerList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

	Collection<Flight> flightList = new ArrayList<>();

	public Passenger() {
		super();
	}

	public Passenger(User user) {
		this.birthDate = user.getBirthDate();
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

}
