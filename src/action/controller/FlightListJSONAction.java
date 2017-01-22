package action.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import action.passenger.FlightListJSONAction.FlightView;
import domain.dao.HibernateGeneric;
import domain.model.Flight;

/**
 * The Class FlightListJSONAction.
 *
 * @param <sincronized> the generic type
 */
public class FlightListJSONAction<sincronized> extends ActionSupport {
	
	/** The Constant DEPARTURE_AIRPORT. */
	private static final int DEPARTURE_AIRPORT = 1;

	/** The Constant ARRIVAL_AIRPORT. */
	private static final int ARRIVAL_AIRPORT = 2;

	/** The Constant EXPECTED_ARRIVAL_DATA. */
	private static final int EXPECTED_ARRIVAL_DATA = 3;

	/** The Constant EXPECTED_DEPARTURE_DATA. */
	private static final int EXPECTED_DEPARTURE_DATA = 4;

	/** The Constant PLANE_STRING. */
	private static final String PLANE_STRING = "plane";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The draw. */
	private Integer draw = 0;
	
	/** The records total. */
	private Integer recordsTotal = 1;
	
	/** The records filtered. */
	private Integer recordsFiltered = 0;
	
	/** The data. */
	private List<AirplaneView> data = new ArrayList<AirplaneView>();
	
	/** The error. */
	String error = null;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public synchronized String execute() throws Exception {

		Map<String, String[]> map = ActionContext.getContext().getParameters().toMap();

		String search = map.get("search[value]")[0];
		int orderCol = Integer.parseInt(map.get("order[0][column]")[0]);
		String orderDir = map.get("order[0][dir]")[0];

		int start = Integer.parseInt(map.get("start")[0]);
		int length = Integer.parseInt(map.get("length")[0]);

		List<Object> allFlights = HibernateGeneric.loadAllObjects(new Flight());
		allFlights.removeIf((Object f) -> !((Flight)f).getRoute().getArrivalTerminal().getAirport().isLocale() && !((Flight)f).getRoute().getDepartureTerminal().getAirport().isLocale());

		recordsTotal = allFlights.size();
		
		
		allFlights = filter(allFlights, search);

		recordsFiltered = allFlights.size();
		
		data = orderAndTrim(allFlights, orderCol, orderDir, start, length);

		return SUCCESS;
	}

	/**
	 * Filter.
	 *
	 * @param data the data
	 * @param search the search
	 * @return the list
	 */
	private List<Object> filter(List<Object> allFlights, String search) {
		if (search != null && !search.equals(""))
			allFlights.removeIf((Object f) -> !((Flight) f).getPlane().getSerial()
					.toLowerCase().contains(search)
					&& !((Flight) f).getRoute().getDepartureTerminal().getAirport().getName().toLowerCase()
					.contains(search)
					&& !((Flight) f).getRoute().getArrivalTerminal().getAirport().getName().toLowerCase()
							.contains(search)
					&& !((Flight) f).getExpectedArrivalDate().toString().toLowerCase().contains(search)
					&& !((Flight) f).getExpectedDepartureDate().toString().toLowerCase().contains(search));

		return allFlights;
	}

	/**
	 * Generate data.
	 *
	 * @param search the search
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @return the array list
	 */
	private List<FlightListJSONAction<sincronized>.AirplaneView> orderAndTrim(List<Object> allFlights, int orderCol,
			String orderDir, int start, int length) {

		switch (orderCol) {
		case 0: // DEPARTURE AIRPORT
			if (orderDir.equals("asc"))
				allFlights.sort((Object f1, Object f2) -> ((Flight) f1).getRoute().getDepartureTerminal().getAirport()
						.getName()
						.compareToIgnoreCase(((Flight) f2).getRoute().getDepartureTerminal().getAirport().getName()));
			else
				allFlights.sort((Object f1,
						Object f2) -> -((Flight) f1).getRoute().getDepartureTerminal().getAirport().getName()
						.compareToIgnoreCase(
								((Flight) f2).getRoute().getDepartureTerminal().getAirport().getName()));
			break;
		case 1: // ARRIVAL AIRPORT
			if (orderDir.equals("asc"))
				allFlights.sort((Object f1, Object f2) -> ((Flight) f1).getRoute().getArrivalTerminal().getAirport()
						.getName()
						.compareToIgnoreCase(((Flight) f2).getRoute().getArrivalTerminal().getAirport().getName()));
			else
				allFlights.sort((Object f1,
						Object f2) -> -((Flight) f1).getRoute().getArrivalTerminal().getAirport().getName()
						.compareToIgnoreCase(
								((Flight) f2).getRoute().getArrivalTerminal().getAirport().getName()));
			break;
		case 2: // DEPARTUREDATE
			if (orderDir.equals("asc"))
				allFlights.sort((Object f1, Object f2) -> ((Flight) f1).getExpectedDepartureDate()
						.compareTo(((Flight) f2).getExpectedDepartureDate()));
			else
				allFlights.sort((Object f1, Object f2) -> -((Flight) f1).getExpectedDepartureDate()
						.compareTo(((Flight) f2).getExpectedDepartureDate()));
			break;
		case 3: // ARRIVALDATE
			if (orderDir.equals("asc"))
				allFlights.sort((Object f1, Object f2) -> ((Flight) f1).getExpectedArrivalDate()
						.compareTo(((Flight) f2).getExpectedArrivalDate()));
			else
				allFlights.sort((Object f1, Object f2) -> -((Flight) f1).getExpectedArrivalDate()
						.compareTo(((Flight) f2).getExpectedArrivalDate()));
			break;
		case 4: // Serial
			if (orderDir.equals("asc"))
				allFlights.sort((Object f1, Object f2) -> ((Flight) f1).getPlane().getSerial()
						.compareTo(((Flight) f2).getPlane().getSerial()));
			else
				allFlights.sort((Object f1, Object f2) -> -((Flight) f1).getPlane().getSerial()
						.compareTo(((Flight) f2).getPlane().getSerial()));
			break;
		default:
			allFlights.sort((Object f1, Object f2) -> ((Flight) f1).getExpectedDepartureDate()
					.compareTo(((Flight) f2).getExpectedDepartureDate()));
			break;
		}
		allFlights = allFlights.subList(start, (start + length) > allFlights.size() ? allFlights.size() - start : (start + length));

		ArrayList<AirplaneView> avViewsList = new ArrayList<AirplaneView>();

		for (Object o : allFlights) {
			Flight flight = (Flight) o;
			String airplane = flight.getPlane().getSerial();
			String origin = flight.getRoute().getDepartureTerminal().getAirport().getName();
			String destination = flight.getRoute().getArrivalTerminal().getAirport().getName();
			String departureDate = flight.getExpectedDepartureDate().toString();
			String arrivalDate = flight.getExpectedArrivalDate().toString();

			avViewsList.add(new AirplaneView(airplane, origin, destination, arrivalDate, departureDate));

		}

		return avViewsList;
	}



	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public Integer getDraw() {
		return draw;
	}

	/**
	 * Sets the draw.
	 *
	 * @param draw the new draw
	 */
	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	/**
	 * Gets the records total.
	 *
	 * @return the records total
	 */
	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * Sets the records total.
	 *
	 * @param recordsTotal the new records total
	 */
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 * Gets the records filtered.
	 *
	 * @return the records filtered
	 */
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * Sets the records filtered.
	 *
	 * @param recordsFiltered the new records filtered
	 */
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<AirplaneView> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<AirplaneView> data) {
		this.data = data;
	}

	/**
	 * The Class AirplaneView.
	 */
	public class AirplaneView {
		
		/** The airplane. */
		String airplane;
		
		/** The origin. */
		String origin;
		
		/** The destination. */
		String destination;
		
		/** The expected arrival date. */
		String expectedArrivalDate;
		
		/** The expected departure date. */
		String expectedDepartureDate;

		/**
		 * Instantiates a new airplane view.
		 *
		 * @param airplane the airplane
		 * @param origin the origin
		 * @param destination the destination
		 * @param expectedArrivalDate the expected arrival date
		 * @param expectedDepartureDate the expected departure date
		 */
		public AirplaneView(String airplane, String origin, String destination, String expectedArrivalDate,
				String expectedDepartureDate) {
			this.airplane = airplane;
			this.origin = origin;
			this.destination = destination;
			this.expectedArrivalDate = expectedArrivalDate;
			this.expectedDepartureDate = expectedDepartureDate;
		}

		/**
		 * Gets the airplane.
		 *
		 * @return the airplane
		 */
		public String getAirplane() {
			return airplane;
		}

		/**
		 * Sets the airplane.
		 *
		 * @param airplane the new airplane
		 */
		public void setAirplane(String airplane) {
			this.airplane = airplane;
		}

		/**
		 * Gets the origin.
		 *
		 * @return the origin
		 */
		public String getOrigin() {
			return origin;
		}

		/**
		 * Sets the origin.
		 *
		 * @param origin the new origin
		 */
		public void setOrigin(String origin) {
			this.origin = origin;
		}

		/**
		 * Gets the destination.
		 *
		 * @return the destination
		 */
		public String getDestination() {
			return destination;
		}

		/**
		 * Sets the destination.
		 *
		 * @param destination the new destination
		 */
		public void setDestination(String destination) {
			this.destination = destination;
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

	}

}
