package domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import domain.model.users.Passenger;

@Entity
public class Flight {

	@Id
	@GeneratedValue
	Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	Date realDepartureDate;

	@Temporal(TemporalType.TIMESTAMP)
	Date realArrivalDate;

	@Temporal(TemporalType.TIMESTAMP)
	Date expectedDepartureDate;

	@Temporal(TemporalType.TIMESTAMP)
	Date expectedArrivalDate;

	@ManyToMany
	Collection<Passenger> passengerList = new ArrayList<>();

	@ManyToOne(optional = false)
	Plane plane;

	// TODO tagak jarri
	@ManyToOne(optional = true)
	Route route;

	Float price;

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getRealDepartureDate() {
		return realDepartureDate;
	}

	public void setRealDepartureDate(Date realDepartureDate) {
		this.realDepartureDate = realDepartureDate;
	}

	public Date getRealArrivalDate() {
		return realArrivalDate;
	}

	public void setRealArrivalDate(Date realArrivalDate) {
		this.realArrivalDate = realArrivalDate;
	}

	public Date getExpectedDepartureDate() {
		return expectedDepartureDate;
	}

	public void setExpectedDepartureDate(Date expectedDepartureDate) {
		this.expectedDepartureDate = expectedDepartureDate;
	}

	public Date getExpectedArrivalDate() {
		return expectedArrivalDate;
	}

	public void setExpectedArrivalDate(Date expectedArrivalDate) {
		this.expectedArrivalDate = expectedArrivalDate;
	}

	public Collection<Passenger> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(Collection<Passenger> passengerList) {
		this.passengerList = passengerList;
	}

}
