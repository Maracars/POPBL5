package action.passenger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.users.Passenger;

public class UnbookFlightSubmit extends ActionSupport {
	private static final long serialVersionUID = 1L;

	String flightId;

	@Override
	public String execute() throws Exception {
		Passenger passenger = (Passenger) ActionContext.getContext().getSession().get("user");
		Flight flight = DAOFlight.loadFlightById(Integer.parseInt(flightId));
		flight.getPassengerList().removeIf((Passenger p) -> p.getId() == passenger.getId());
		HibernateGeneric.updateObject(flight);
		addActionMessage(getText("passenger.flightUnbooked"));
		return SUCCESS;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

}
