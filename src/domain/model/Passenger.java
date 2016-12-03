package domain.model;

import javax.persistence.Entity;

@Entity
public class Passenger extends User {

	
	// TODO kanpuak finkatzeko
	
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
