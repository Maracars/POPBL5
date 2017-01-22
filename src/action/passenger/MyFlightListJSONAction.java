package action.passenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.users.User;

/**
 * The Class MyFlightListJSONAction.
 *
 * @param <sincronized>
 *            the generic type
 */
public class MyFlightListJSONAction<sincronized> extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The draw. */
	private int recordsTotal = 1, recordsFiltered = 0, draw = 0;

	/** The data. */
	List<FlightView> data;

	/** The error. */
	String error = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public synchronized String execute() throws Exception {
		Map<String, String[]> map = ActionContext.getContext().getParameters().toMap();

		int orderCol = Integer.parseInt(map.get("order[0][column]")[0]);
		String search = map.get("search[value]")[0].toLowerCase();
		String orderDir = map.get("order[0][dir]")[0];
		String originSearch = map.get("columns[0][search][value]")[0].toLowerCase();
		String destinationSearch = map.get("columns[1][search][value]")[0].toLowerCase();

		int start = Integer.parseInt(map.get("start")[0]);
		int length = Integer.parseInt(map.get("length")[0]);
		User user = (User) ActionContext.getContext().getSession().get("user");

		List<Object> allFlights = HibernateGeneric.loadAllObjects(new Flight());

		allFlights.removeIf((Object f) -> !((Flight) f).getPassengerList().contains(user));

		recordsTotal = allFlights.size();

		allFlights = filter(allFlights, destinationSearch, originSearch, search);

		recordsFiltered = allFlights.size();

		data = orderAndTrim(allFlights, orderCol, orderDir, start, length);

		return SUCCESS;
	}

	private List<MyFlightListJSONAction<sincronized>.FlightView> orderAndTrim(List<Object> allFlights, int orderCol,
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
		case 3: // PRICE
			if (orderDir.equals("asc"))
				allFlights.sort(
						(Object f1, Object f2) -> Double.compare(((Flight) f1).getPrice(), ((Flight) f2).getPrice()));
			else
				allFlights.sort(
						(Object f1, Object f2) -> -Double.compare(((Flight) f1).getPrice(), ((Flight) f2).getPrice()));
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

		ArrayList<FlightView> fvViewsList = new ArrayList<FlightView>();

		for (Object o : allFlights) {
			Flight flight = (Flight) o;
			String source = flight.getRoute().getDepartureTerminal().getAirport().getName();
			String destination = flight.getRoute().getArrivalTerminal().getAirport().getName();
			String departureDate = flight.getExpectedDepartureDate().toString();
			String price = String.valueOf(flight.getPrice());
			String planeInfo = flight.getPlane().getSerial();
			String flightId = Integer
					.toString(flight.getExpectedDepartureDate().after(new Date()) ? flight.getId() : 0);

			fvViewsList.add(new FlightView(source, destination, departureDate, price, planeInfo, flightId));

		}
		
		return fvViewsList;

	}

	private List<Object> filter(List<Object> allFlights, String destinationSearch, String originSearch, String search) {
		if (originSearch != null && !originSearch.equals(""))
			allFlights.removeIf((Object f) -> !((Flight) f).getRoute().getDepartureTerminal().getAirport().getName()
					.toLowerCase().contains(originSearch));
		if (destinationSearch != null && !destinationSearch.equals(""))
			allFlights.removeIf((Object f) -> !((Flight) f).getRoute().getArrivalTerminal().getAirport().getName()
					.toLowerCase().contains(destinationSearch));

		if (search != null && !search.equals(""))
			allFlights.removeIf((Object f) -> !((Flight) f).getRoute().getArrivalTerminal().getAirport().getName()
					.toLowerCase().contains(search)
					&& !((Flight) f).getRoute().getDepartureTerminal().getAirport().getName().toLowerCase()
							.contains(search)
					&& !((Flight) f).getPlane().getSerial().toLowerCase().contains(search));

		return allFlights;
	}

	/**
	 * The Class FlightView.
	 */
	public class FlightView {

		/** The source. */
		String source;

		/** The destination. */
		String destination;

		/** The departure date. */
		String departureDate;

		/** The price. */
		String price;

		/** The plane info. */
		String planeInfo;

		/** The flight id. */
		String flightId;

		/**
		 * Instantiates a new flight view.
		 *
		 * @param source
		 *            the source
		 * @param destination
		 *            the destination
		 * @param departureDate
		 *            the departure date
		 * @param price
		 *            the price
		 * @param planeInfo
		 *            the plane info
		 * @param flightId
		 *            the flight id
		 */
		public FlightView(String source, String destination, String departureDate, String price, String planeInfo,
				String flightId) {
			this.source = source;
			this.destination = destination;
			this.departureDate = departureDate;
			this.price = price;
			this.planeInfo = planeInfo;
			this.flightId = flightId;
		}

		/**
		 * Gets the source.
		 *
		 * @return the source
		 */
		public String getSource() {
			return source;
		}

		/**
		 * Sets the source.
		 *
		 * @param source
		 *            the new source
		 */
		public void setSource(String source) {
			this.source = source;
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
		 * @param destination
		 *            the new destination
		 */
		public void setDestination(String destination) {
			this.destination = destination;
		}

		/**
		 * Gets the departure date.
		 *
		 * @return the departure date
		 */
		public String getDepartureDate() {
			return departureDate;
		}

		/**
		 * Sets the departure date.
		 *
		 * @param departureDate
		 *            the new departure date
		 */
		public void setDepartureDate(String departureDate) {
			this.departureDate = departureDate;
		}

		/**
		 * Gets the price.
		 *
		 * @return the price
		 */
		public String getPrice() {
			return price;
		}

		/**
		 * Sets the price.
		 *
		 * @param price
		 *            the new price
		 */
		public void setPrice(String price) {
			this.price = price;
		}

		/**
		 * Gets the plane info.
		 *
		 * @return the plane info
		 */
		public String getPlaneInfo() {
			return planeInfo;
		}

		/**
		 * Sets the plane info.
		 *
		 * @param planeInfo
		 *            the new plane info
		 */
		public void setPlaneInfo(String planeInfo) {
			this.planeInfo = planeInfo;
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
		 * @param flightId
		 *            the new flight id
		 */
		public void setFlightId(String flightId) {
			this.flightId = flightId;
		}

	}

	/**
	 * Gets the records total.
	 *
	 * @return the records total
	 */
	public int getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * Sets the records total.
	 *
	 * @param recordsTotal
	 *            the new records total
	 */
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 * Gets the records filtered.
	 *
	 * @return the records filtered
	 */
	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * Sets the records filtered.
	 *
	 * @param recordsFiltered
	 *            the new records filtered
	 */
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
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
	 * @param data
	 *            the new data
	 */
	public void setData(List<FlightView> data) {
		this.data = data;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error
	 *            the new error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public int getDraw() {
		return draw;
	}

	/**
	 * Sets the draw.
	 *
	 * @param draw
	 *            the new draw
	 */
	public void setDraw(int draw) {
		this.draw = draw;
	}

}
