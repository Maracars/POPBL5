package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Passenger extends User {

	@Id
	@GeneratedValue
	Integer id;
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
