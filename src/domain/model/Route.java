package domain.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Route {

	@Id
	@GeneratedValue
	Integer id;

	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	Gate arrivalGate;

	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	Gate departureGate;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "AirlineRoutes",
    joinColumns = @JoinColumn(name = "RouteId"),
    inverseJoinColumns = @JoinColumn(name = "AirlineId"))
	
	Collection<Airline> airlineList = new ArrayList<Airline>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	Gate getArrivalGate() {
		return arrivalGate;
	}

	public void setArrivalGate(Gate arrivalGate) {
		this.arrivalGate = arrivalGate;
	}

	public Gate getDepartureGate() {
		return departureGate;
	}

	public void setDepartureGate(Gate departureGate) {
		this.departureGate = departureGate;
	}

}
