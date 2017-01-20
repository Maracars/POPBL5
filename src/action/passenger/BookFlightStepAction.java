package action.passenger;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Airport;

public class BookFlightStepAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	List<Object> listOriginAirport;
	List<Object> listDestinationAirport;


	@Override
	public String execute() throws Exception {
		listOriginAirport = HibernateGeneric.loadAllObjects(new Airport());
		listDestinationAirport = HibernateGeneric.loadAllObjects(new Airport());

		return SUCCESS;
	}


	public List<Object> getListOriginAirport() {
		return listOriginAirport;
	}


	public void setListOriginAirport(List<Object> listOriginAirport) {
		this.listOriginAirport = listOriginAirport;
	}


	public List<Object> getListDestinationAirport() {
		return listDestinationAirport;
	}


	public void setListDestinationAirport(List<Object> listDestinationAirport) {
		this.listDestinationAirport = listDestinationAirport;
	}





}
