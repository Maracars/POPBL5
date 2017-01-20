package action.passenger;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Airport;

/**
 * The Class BookFlightStepAction.
 */
public class BookFlightStepAction extends ActionSupport{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The list origin airport. */
	List<Object> listOriginAirport;
	
	/** The list destination airport. */
	List<Object> listDestinationAirport;


	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		listOriginAirport = HibernateGeneric.loadAllObjects(new Airport());
		listDestinationAirport = HibernateGeneric.loadAllObjects(new Airport());

		return SUCCESS;
	}


	/**
	 * Gets the list origin airport.
	 *
	 * @return the list origin airport
	 */
	public List<Object> getListOriginAirport() {
		return listOriginAirport;
	}


	/**
	 * Sets the list origin airport.
	 *
	 * @param listOriginAirport the new list origin airport
	 */
	public void setListOriginAirport(List<Object> listOriginAirport) {
		this.listOriginAirport = listOriginAirport;
	}


	/**
	 * Gets the list destination airport.
	 *
	 * @return the list destination airport
	 */
	public List<Object> getListDestinationAirport() {
		return listDestinationAirport;
	}


	/**
	 * Sets the list destination airport.
	 *
	 * @param listDestinationAirport the new list destination airport
	 */
	public void setListDestinationAirport(List<Object> listDestinationAirport) {
		this.listDestinationAirport = listDestinationAirport;
	}





}
