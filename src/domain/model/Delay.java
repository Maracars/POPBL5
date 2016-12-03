package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Delay {

	@Id
	@GeneratedValue
	Integer id;

	Integer timeInMinutes;

	String description;

	@ManyToOne(optional = false)
	Flight affectedFlight;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTimeInMinutes() {
		return timeInMinutes;
	}

	public void setTimeInMinutes(Integer timeInMinutes) {
		this.timeInMinutes = timeInMinutes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Flight getAffectedFlight() {
		return affectedFlight;
	}

	public void setAffectedFlight(Flight affectedFlight) {
		this.affectedFlight = affectedFlight;
	}

}
