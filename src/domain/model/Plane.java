package domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import domain.model.users.Airline;

// TODO: Auto-generated Javadoc
/**
 * The Class Plane.
 */
@Entity
public class Plane {

	/** The id. */
	@Id
	@GeneratedValue
	Integer id;

	/** The serial. */
	String serial;

	/** The model. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	PlaneModel model;

	/** The fabrication date. */
	// TODO POSITION ZELA?
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	Date fabricationDate;

	/** The airline. */
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)

	// TODO taga definitu
	Airline airline;

	/** The status. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)

	PlaneStatus status;

	/**
	 * Gets the plane status.
	 *
	 * @return the plane status
	 */
	public PlaneStatus getPlaneStatus() {
		return status;
	}

	/**
	 * Sets the plane status.
	 *
	 * @param planeStatus the new plane status
	 */
	public void setPlaneStatus(PlaneStatus planeStatus) {
		this.status = planeStatus;
	}

	/**
	 * Gets the airline.
	 *
	 * @return the airline
	 */
	public Airline getAirline() {
		return airline;
	}

	/**
	 * Sets the airline.
	 *
	 * @param airline the new airline
	 */
	public void setAirline(Airline airline) {
		this.airline = airline;
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
	 * Gets the serial.
	 *
	 * @return the serial
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * Sets the serial.
	 *
	 * @param serial the new serial
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public PlaneModel getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(PlaneModel model) {
		this.model = model;
	}

	/**
	 * Gets the fabrication date.
	 *
	 * @return the fabrication date
	 */
	public Date getFabricationDate() {
		return fabricationDate;
	}

	/**
	 * Sets the fabrication date.
	 *
	 * @param fabricationDate the new fabrication date
	 */
	public void setFabricationDate(Date fabricationDate) {
		this.fabricationDate = fabricationDate;
	}

}
