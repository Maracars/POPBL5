package domain.model;

import javax.persistence.Entity;

/**
 * @author joanes
 *
 */
@Entity
public class Airline extends User {

	String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// TODO hemen finkatzeko dare kanpuak

}
