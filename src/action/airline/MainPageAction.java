package action.airline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.model.Flight;
import domain.model.users.Airline;
import domain.model.users.User;

/**
 * The Class MainPageAction.
 */
public class MainPageAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data. */
	List<FlightView> data;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		List<Flight> flightList = DAOFlight.loadOneWeekFlights();
		if (flightList != null) {
			data = generateData(flightList);

		}
		return SUCCESS;
	}

	/**
	 * Generate data.
	 *
	 * @param flightList the flight list
	 * @return the list
	 */
	public List<FlightView> generateData(List<Flight> flightList) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		List<FlightView> data = new ArrayList<FlightView>();
		for (Flight f : flightList) {
			String planeName = f.getPlane().getSerial();
			String flightId = String.valueOf(f.getId());
			String routeName = f.getRoute().getDepartureTerminal().getName() + "-"
					+ f.getRoute().getArrivalTerminal().getName();
			String expectedDepartureDate = sdf.format(f.getExpectedDepartureDate());
			String expectedArrivalDate = sdf.format(f.getExpectedArrivalDate());

			data.add(new FlightView(planeName, flightId, routeName, expectedDepartureDate, expectedArrivalDate));
		}
		return data;
	}

	/**
	 * New flight view.
	 *
	 * @param planeName the plane name
	 * @param flightId the flight id
	 * @param routeName the route name
	 * @param expectedDepartureDate the expected departure date
	 * @param expectedArrivalDate the expected arrival date
	 * @return the flight view
	 */
	// Function for test
	public FlightView newFlightView(String planeName, String flightId, String routeName, String expectedDepartureDate,
			String expectedArrivalDate) {
		return new FlightView(planeName, flightId, routeName, expectedDepartureDate, expectedArrivalDate);
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<FlightView> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<FlightView> data) {
		this.data = data;
	}

	/**
	 * The Class FlightView.
	 */
	public class FlightView {
		
		/** The plane name. */
		String planeName;
		
		/** The flight id. */
		String flightId;
		
		/** The route name. */
		String routeName;
		
		/** The expected departure date. */
		String expectedDepartureDate;
		
		/** The expected arrival date. */
		String expectedArrivalDate;

		/**
		 * Instantiates a new flight view.
		 *
		 * @param planeName the plane name
		 * @param flightId the flight id
		 * @param routeName the route name
		 * @param expectedDepartureDate the expected departure date
		 * @param expectedArrivalDate the expected arrival date
		 */
		public FlightView(String planeName, String flightId, String routeName, String expectedDepartureDate,
				String expectedArrivalDate) {
			this.planeName = planeName;
			this.flightId = flightId;
			this.routeName = routeName;
			this.expectedDepartureDate = expectedDepartureDate;
			this.expectedArrivalDate = expectedArrivalDate;
		}

		/**
		 * Gets the plane name.
		 *
		 * @return the plane name
		 */
		public String getPlaneName() {
			return planeName;
		}

		/**
		 * Sets the plane name.
		 *
		 * @param planeName the new plane name
		 */
		public void setPlaneName(String planeName) {
			this.planeName = planeName;
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

		/**
		 * Gets the route name.
		 *
		 * @return the route name
		 */
		public String getRouteName() {
			return routeName;
		}

		/**
		 * Sets the route name.
		 *
		 * @param routeName the new route name
		 */
		public void setRouteName(String routeName) {
			this.routeName = routeName;
		}

		/**
		 * Gets the expected departure date.
		 *
		 * @return the expected departure date
		 */
		public String getExpectedDepartureDate() {
			return expectedDepartureDate;
		}

		/**
		 * Sets the expected departure date.
		 *
		 * @param expectedDepartureDate the new expected departure date
		 */
		public void setExpectedDepartureDate(String expectedDepartureDate) {
			this.expectedDepartureDate = expectedDepartureDate;
		}

		/**
		 * Gets the expected arrival date.
		 *
		 * @return the expected arrival date
		 */
		public String getExpectedArrivalDate() {
			return expectedArrivalDate;
		}

		/**
		 * Sets the expected arrival date.
		 *
		 * @param expectedArrivalDate the new expected arrival date
		 */
		public void setExpectedArrivalDate(String expectedArrivalDate) {
			this.expectedArrivalDate = expectedArrivalDate;
		}
	}
	
	

}
