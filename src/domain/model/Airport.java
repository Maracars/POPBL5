package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Airport {

	@Id
	@GeneratedValue
	Integer id;

	String name;

	Integer maxFlights;

	@ManyToOne // (cascade = CascadeType.PERSIST)
	@NotNull
	City city;

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

	public Integer getMaxFlights() {
		return maxFlights;
	}

	public void setMaxFlights(Integer maxFlights) {
		this.maxFlights = maxFlights;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
