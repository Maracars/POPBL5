package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.model.Flight;

/**
 * The Class NextDeparturingFlightJSONAction.
 *
 * @param <sincronized> the generic type
 */
public class NextDeparturingFlightJSONAction<sincronized> extends ActionSupport {

	/** The Constant DESTINATION. */
	private static final int DESTINATION = 1;

	/** The Constant FLIGHT_INFO. */
	private static final int FLIGHT_INFO = 2;

	/** The Constant FLIGHT_STATUS. */
	private static final int FLIGHT_STATUS = 3;

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
		String orderCol = map.get("order[0][column]")[0];
		String orderDir = map.get("order[0][dir]")[0];

		int start = Integer.parseInt(map.get("start")[0]);
		int length = Integer.parseInt(map.get("length")[0]);

		data = generateData(search, orderCol, orderDir, start, length);

		if (DAOFlight.loadNextDepartureFlights() != null) {
			recordsTotal = DAOFlight.loadNextDepartureFlights().size();

			//data = filter(data, search);

			recordsFiltered = DAOFlight.loadNextDepartureFlights().size();
		}
		return SUCCESS;
	}

	/**
	 * Filter.
	 *
	 * @param data the data
	 * @param search the search
	 * @return the list
	 */
	private List<AirplaneView> filter(List<AirplaneView> data, String search) {
		String searchToLower = search.toLowerCase();
		//data = DAOFlight.filterDeparturingFlights(searchToLower);
		return data;
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
	public ArrayList<AirplaneView> generateData(String search, String orderCol, String orderDir, int start,
			int length) {
		List<Flight> flightList = null;
		ArrayList<AirplaneView> planeViews = new ArrayList<AirplaneView>();
		
		String colName = getOrderColumnName(Integer.parseInt(orderCol));
		
		flightList = DAOFlight.filterDeparturingFlights(colName, orderDir, start, length, search);

		if (flightList != null) {

			for (int i = 0; i < flightList.size(); i++) {
				if (flightList.get(i) instanceof Flight) {
					String flightInfo = flightList.get(i).getPlane().getModel().getPlaneMaker().getName() + ", "
							+ flightList.get(i).getPlane().getSerial();

					// Hemen egongo da desberdintasunetako bat bi taulen artian
					String destination = flightList.get(i).getRoute().getArrivalTerminal().getAirport().getName();
					String positionStatus = flightList.get(i).getPlane().getPlaneStatus().getPositionStatus();

					// Hemen egongo da desberdintasunetako bat bi taulen artian
					Date date = flightList.get(i).getExpectedDepartureDate();

					planeViews.add(new AirplaneView(date, flightInfo, destination, positionStatus));
				}

			}
		}
		return planeViews;
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
			colName = "expectedDepartureDate";
			break;
		case DESTINATION:
			colName = "route.arrivalTerminal.airport.name";
			break;
		case FLIGHT_INFO:
			colName = "plane.serial";
			break;
		case FLIGHT_STATUS:
			colName = "status.positionStatus";
			break;
		default:
			colName = "expectedDepartureDate";
			break;
		}
		return colName;
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
		
		/** The date. */
		Date date;
		
		/** The flight info. */
		String flightInfo;
		
		/** The origin. */
		String origin;
		
		/** The position status. */
		String positionStatus;

		/**
		 * Instantiates a new airplane view.
		 *
		 * @param date the date
		 * @param flightInfo the flight info
		 * @param origin the origin
		 * @param positionStatus the position status
		 */
		public AirplaneView(Date date, String flightInfo, String origin, String positionStatus) {
			this.flightInfo = flightInfo;
			this.origin = origin;
			this.positionStatus = positionStatus;
			this.date = date;
		}

		/**
		 * Gets the position status.
		 *
		 * @return the position status
		 */
		public String getPositionStatus() {
			return positionStatus;
		}

		/**
		 * Sets the position status.
		 *
		 * @param positionStatus the new position status
		 */
		public void setPositionStatus(String positionStatus) {
			this.positionStatus = positionStatus;
		}

		/**
		 * Gets the date.
		 *
		 * @return the date
		 */
		public Date getDate() {
			return date;
		}

		/**
		 * Sets the date.
		 *
		 * @param date the new date
		 */
		public void setDate(Date date) {
			this.date = date;
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
		 * Gets the flight info.
		 *
		 * @return the flight info
		 */
		public String getFlightInfo() {
			return flightInfo;
		}

		/**
		 * Sets the flight info.
		 *
		 * @param flightInfo the new flight info
		 */
		public void setFlightInfo(String flightInfo) {
			this.flightInfo = flightInfo;
		}

	}

}
