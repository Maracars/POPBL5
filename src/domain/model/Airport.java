package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Airport {

	@Id
	@GeneratedValue
	Integer id;

	String name;

	Integer maxFlights;

	@ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
