package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Route {

	@Id
	@GeneratedValue
	Integer id;

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	Gate arrivalGate;

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)

	Gate departureGate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Gate getArrivalGate() {
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
