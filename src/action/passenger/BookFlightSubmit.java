package action.passenger;

import java.util.ArrayList;
import java.util.Collection;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.users.Passenger;
import domain.model.users.User;

public class BookFlightSubmit extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	String flightId;

	@Override
	public String execute() throws Exception {
		Passenger passenger = (Passenger) ActionContext.getContext().getSession().get("user");
		Flight flight = DAOFlight.loadFlightById(Integer.parseInt(flightId));
		if(flight.getPassengerList().size() > 0){
			flight.getPassengerList().add(passenger);
		}else{
			Collection<Passenger> passengerList = new ArrayList<Passenger>();
			passengerList.add(passenger);
			flight.setPassengerList(passengerList);
		}
		HibernateGeneric.updateObject(flight);
		addActionMessage("Flight booked");
		return SUCCESS;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	
	
	
	
	

}
