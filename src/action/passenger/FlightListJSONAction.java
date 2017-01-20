package action.passenger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.model.Flight;
import domain.model.users.Passenger;

/**
 * The Class FlightListJSONAction.
 *
 * @param <sincronized> the generic type
 */
public class FlightListJSONAction<sincronized> extends ActionSupport {

	/** The Constant STRING_ROUTE_DEPARTURE_TERMINAL_AIRPORT_NAME. */
	private static final String STRING_ROUTE_DEPARTURE_TERMINAL_AIRPORT_NAME = "route.departureTerminal.airport.name";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The draw. */
	private int recordsTotal = 1, recordsFiltered = 0, draw = 0;
	
	/** The data. */
	List<FlightView> data;
	
	/** The error. */
	String error = null;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public synchronized String execute() throws Exception {
		Map<String, String[]> map = ActionContext.getContext().getParameters().toMap();

		int orderCol = Integer.parseInt(map.get("order[0][column]")[0]);
		String orderDir = map.get("order[0][dir]")[0];
		String originSearch = map.get("columns[0][search][value]")[0];
		String destinationSearch = map.get("columns[1][search][value]")[0];

		int start = Integer.parseInt(map.get("start")[0]);
		int length = Integer.parseInt(map.get("length")[0]);

		data = generateData(orderCol, orderDir, start, length);

		recordsTotal = DAOFlight.loadNextDepartureFlights().size();

		data = filter(data, originSearch, destinationSearch);

		recordsFiltered = DAOFlight.loadNextDepartureFlights().size();

		return SUCCESS;
	}

	/**
	 * Filter.
	 *
	 * @param data the data
	 * @param originSearch the origin search
	 * @param destinationSearch the destination search
	 * @return the list
	 */
	private List<FlightView> filter(List<FlightView> data, String originSearch, String destinationSearch) {
		originSearch = originSearch.toLowerCase();
		destinationSearch = destinationSearch.toLowerCase();
		for (Iterator<FlightView> fvIt = data.iterator(); fvIt.hasNext();) {
			FlightView fv = fvIt.next();
			if (fv.getSource().toLowerCase().contains(originSearch)
					&& fv.getDestination().toLowerCase().contains(destinationSearch))
				continue;
			fvIt.remove();
		}
		return data;
	}

	/**
	 * Generate data.
	 *
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @return the array list
	 */
	public ArrayList<FlightView> generateData(int orderCol, String orderDir, int start, int length) {
		List<Flight> flightList = null;
		ArrayList<FlightView> fvViewsList = new ArrayList<FlightView>();
		String colName = getOrderColumnName(orderCol);

		flightList = DAOFlight.loadNextDepartureFlightsForTable(colName, orderDir, start, length);

		if (flightList != null) {
			for (Flight flight : flightList) {
				String source = flight.getRoute().getDepartureTerminal().getAirport().getName();
				String destination = flight.getRoute().getArrivalTerminal().getAirport().getName();
				String departureDate = flight.getExpectedDepartureDate().toString();
				String price = String.valueOf(flight.getPrice());
				String planeInfo = flight.getPlane().getSerial();
				String flightId = getFlightId(flight);

				fvViewsList.add(new FlightView(source, destination, departureDate, price, planeInfo, flightId));

			}
		}
		return fvViewsList;
	}

	/**
	 * Gets the flight id.
	 *
	 * @param flight the flight
	 * @return the flight id
	 */
	public String getFlightId(Flight flight) {
		String id = String.valueOf(flight.getId());
		Passenger passenger = (Passenger) ActionContext.getContext().getSession().get("user");
		for (Passenger p : flight.getPassengerList()) {
			if (p.getId() == passenger.getId()) {
				id = "0";
			}
		}
		return id;
	}

	/**
	 * Gets the order column name.
	 *
	 * @param orderCol the order col
	 * @return the order column name
	 */
	public String getOrderColumnName(int orderCol) {
		String colName = null;
		switch (orderCol) {
		case 0:
			colName = STRING_ROUTE_DEPARTURE_TERMINAL_AIRPORT_NAME;
			break;
		case 1:
			colName = "route.arrivalTerminal.airport.name";
			break;
		case 2:
			colName = "expectedDepartureDate";
			break;
		case 3:
			colName = "price";
			break;
		case 4:
			colName = "plane.serial";
			break;
		default:
			colName = STRING_ROUTE_DEPARTURE_TERMINAL_AIRPORT_NAME;
			break;
		}
		return colName;
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
	 * @param recordsTotal the new records total
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
	 * @param recordsFiltered the new records filtered
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
	 * @param data the new data
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
	 * @param error the new error
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
	 * @param draw the new draw
	 */
	public void setDraw(int draw) {
		this.draw = draw;
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
		 * @param source the source
		 * @param destination the destination
		 * @param departureDate the departure date
		 * @param price the price
		 * @param planeInfo the plane info
		 * @param flightId the flight id
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
		 * @param source the new source
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
		 * @param destination the new destination
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
		 * @param departureDate the new departure date
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
		 * @param price the new price
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
		 * @param planeInfo the new plane info
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
		 * @param flightId the new flight id
		 */
		public void setFlightId(String flightId) {
			this.flightId = flightId;
		}

	}

}
