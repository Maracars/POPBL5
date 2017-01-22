package action.passenger;

import java.util.ArrayList;
import java.util.Collection;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.users.Passenger;

/**
 * The Class BookFlightSubmit.
 */
public class BookFlightSubmit extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The flight id. */
	String flightId;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		Passenger passenger = (Passenger) ActionContext.getContext().getSession().get("user");
		Flight flight = DAOFlight.loadFlightById(Integer.parseInt(flightId));
		if (flight.getPassengerList().size() > 0) {
			flight.getPassengerList().add(passenger);
		} else {
			Collection<Passenger> passengerList = new ArrayList<Passenger>();
			passengerList.add(passenger);
			flight.setPassengerList(passengerList);
		}
		HibernateGeneric.updateObject(flight);
		addActionMessage(getText("passenger.flightBooked"));
		return SUCCESS;
	}

	/**
	 * Gets the flight id.
	 *
	 * @return the flight id
	 */
	public String getFlightId() {
		return flightId;
	}

	/**
	 * Sets the flight id.
	 *
	 * @param flightId the new flight id
	 */
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

}
