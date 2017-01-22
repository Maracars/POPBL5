package domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import domain.model.users.Passenger;

/**
 * The Class Flight.
 */
@Entity
public class Flight {

	/** The id. */
	@Id
	@GeneratedValue
	Integer id;

	/** The real departure date. */
	@Temporal(TemporalType.TIMESTAMP)
	Date realDepartureDate;

	/** The real arrival date. */
	@Temporal(TemporalType.TIMESTAMP)
	Date realArrivalDate;

	/** The expected departure date. */
	@Temporal(TemporalType.TIMESTAMP)
	Date expectedDepartureDate;

	/** The expected arrival date. */
	@Temporal(TemporalType.TIMESTAMP)
	Date expectedArrivalDate;

    /** The passenger list. */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "userFlights", joinColumns = {
			@JoinColumn(name = "FlightId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "PassengerId", nullable = false, updatable = false) })

	Collection<Passenger> passengerList = new ArrayList<>();

	/** The plane. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	Plane plane;

	/** The route. */
	@ManyToOne(optional = true)
	Route route;

	/** The price. */
	Float price;
	
	/** The start gate. */
	@ManyToOne(optional = true)
	Gate startGate;
	
	/** The end gate. */
	@ManyToOne(optional = true)
	Gate endGate;
	
	

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Float getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(Float price) {
		this.price = price;
	}

	/**
	 * Gets the plane.
	 *
	 * @return the plane
	 */
	public Plane getPlane() {
		return plane;
	}

	/**
	 * Sets the plane.
	 *
	 * @param plane the new plane
	 */
	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	/**
	 * Gets the route.
	 *
	 * @return the route
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * Sets the route.
	 *
	 * @param route the new route
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the real departure date.
	 *
	 * @return the real departure date
	 */
	public Date getRealDepartureDate() {
		return realDepartureDate;
	}

	/**
	 * Sets the real departure date.
	 *
	 * @param realDepartureDate the new real departure date
	 */
	public void setRealDepartureDate(Date realDepartureDate) {
		this.realDepartureDate = realDepartureDate;
	}

	/**
	 * Gets the real arrival date.
	 *
	 * @return the real arrival date
	 */
	public Date getRealArrivalDate() {
		return realArrivalDate;
	}

	/**
	 * Sets the real arrival date.
	 *
	 * @param realArrivalDate the new real arrival date
	 */
	public void setRealArrivalDate(Date realArrivalDate) {
		this.realArrivalDate = realArrivalDate;
	}

	/**
	 * Gets the expected departure date.
	 *
	 * @return the expected departure date
	 */
	public Date getExpectedDepartureDate() {
		return expectedDepartureDate;
	}

	/**
	 * Sets the expected departure date.
	 *
	 * @param expectedDepartureDate the new expected departure date
	 */
	public void setExpectedDepartureDate(Date expectedDepartureDate) {
		this.expectedDepartureDate = expectedDepartureDate;
	}

	/**
	 * Gets the expected arrival date.
	 *
	 * @return the expected arrival date
	 */
	public Date getExpectedArrivalDate() {
		return expectedArrivalDate;
	}

	/**
	 * Sets the expected arrival date.
	 *
	 * @param expectedArrivalDate the new expected arrival date
	 */
	public void setExpectedArrivalDate(Date expectedArrivalDate) {
		this.expectedArrivalDate = expectedArrivalDate;
	}

	/**
	 * Gets the passenger list.
	 *
	 * @return the passenger list
	 */
	public Collection<Passenger> getPassengerList() {
		return passengerList;
	}

	/**
	 * Sets the passenger list.
	 *
	 * @param passengerList the new passenger list
	 */
	public void setPassengerList(Collection<Passenger> passengerList) {
		this.passengerList = passengerList;
	}
	
	/**
	 * Gets the start gate.
	 *
	 * @return the start gate
	 */
	public Gate getStartGate() {
		return startGate;
	}

	/**
	 * Sets the start gate.
	 *
	 * @param startGate the new start gate
	 */
	public void setStartGate(Gate startGate) {
		this.startGate = startGate;
	}

	/**
	 * Gets the end gate.
	 *
	 * @return the end gate
	 */
	public Gate getEndGate() {
		return endGate;
	}

	/**
	 * Sets the end gate.
	 *
	 * @param endGate the new end gate
	 */
	public void setEndGate(Gate endGate) {
		this.endGate = endGate;
	}

}
