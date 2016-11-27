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
