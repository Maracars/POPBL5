package action.airline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.model.Flight;

public class MainPageAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	 List<FlightView> data;

	@Override
	public String execute() throws Exception {
		List<Flight> flightList = DAOFlight.loadOneWeekFlights();
		
		data = generateData(flightList);
		return SUCCESS;
	}


	public List<FlightView> generateData(List<Flight> flightList) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		List<FlightView> data = new ArrayList<FlightView>();
		for(Flight f : flightList){
			String planeName = f.getPlane().getSerial();
			String flightId = String.valueOf(f.getId());
			String routeName = f.getRoute().getDepartureTerminal().getName() + "-" + f.getRoute().getArrivalTerminal().getName();
			String expectedDepartureDate = sdf.format(f.getExpectedDepartureDate());
			String expectedArrivalDate = sdf.format(f.getExpectedArrivalDate());
			
			data.add(new FlightView(planeName, flightId, routeName, expectedDepartureDate, expectedArrivalDate));
		}
		return data;
	}


	public class FlightView{
		String planeName;
		String flightId;
		String routeName;
		String expectedDepartureDate;
		String expectedArrivalDate;

		public FlightView(String planeName, String flightId, String routeName, String expectedDepartureDate, String expectedArrivalDate){
			this.planeName = planeName;
			this.flightId = flightId;
			this.routeName = routeName;
			this.expectedDepartureDate = expectedDepartureDate;
			this.expectedArrivalDate = expectedArrivalDate;
		}

		public String getPlaneName() {
			return planeName;
		}

		public void setPlaneName(String planeName) {
			this.planeName = planeName;
		}

		public String getFlightId() {
			return flightId;
		}

		public void setFlightId(String flightId) {
			this.flightId = flightId;
		}

		public String getRouteName() {
			return routeName;
		}

		public void setRouteName(String routeName) {
			this.routeName = routeName;
		}

		public String getExpectedDepartureDate() {
			return expectedDepartureDate;
		}

		public void setExpectedDepartureDate(String expectedDepartureDate) {
			this.expectedDepartureDate = expectedDepartureDate;
		}

		public String getExpectedArrivalDate() {
			return expectedArrivalDate;
		}

		public void setExpectedArrivalDate(String expectedArrivalDate) {
			this.expectedArrivalDate = expectedArrivalDate;
		}
	}


	public List<FlightView> getData() {
		return data;
	}


	public void setData(List<FlightView> data) {
		this.data = data;
	}


}
