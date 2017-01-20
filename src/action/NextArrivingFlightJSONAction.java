package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.dao.DAOPlane;
import domain.model.Flight;
import domain.model.users.User;

public class NextArrivingFlightJSONAction<sincronized> extends ActionSupport {

	private static final int DESTINATION = 1;

	private static final int FLIGHT_INFO = 2;

	private static final int FLIGHT_STATUS = 3;

	private static final long serialVersionUID = 1L;

	private Integer draw = 0;
	private Integer recordsTotal = 1;
	private Integer recordsFiltered = 0;
	private List<AirplaneView> data = new ArrayList<AirplaneView>();
	String error = null;

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

			data = filter(data, search);

			recordsFiltered = DAOFlight.loadNextDepartureFlights().size();
		}
		return SUCCESS;
	}

	private List<AirplaneView> filter(List<AirplaneView> data, String search) {
		String searchToLower = search.toLowerCase();
		for (Iterator<AirplaneView> pIt = data.iterator(); pIt.hasNext();) {
			AirplaneView pv = pIt.next();
			if (pv.getOrigin().toLowerCase().contains(searchToLower))
				continue;
			if (pv.getFlightInfo().toLowerCase().contains(searchToLower))
				continue;
			if (pv.getPositionStatus().toLowerCase().contains(searchToLower))
				continue;
			pIt.remove();
		}
		return data;
	}

	public ArrayList<AirplaneView> generateData(String search, String orderCol, String orderDir, int start,
			int length) {
		List<Flight> flightList = null;
		ArrayList<AirplaneView> planeViews = new ArrayList<AirplaneView>();
		
		String colName = getOrderColumnName(Integer.parseInt(orderCol));


		flightList = DAOFlight.loadNextDepartureFlightsForTable(colName, orderDir, start, length);

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
			colName = "plane.model.planeMaker.name";
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

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<AirplaneView> getData() {
		return data;
	}

	public void setData(List<AirplaneView> data) {
		this.data = data;
	}

	public class AirplaneView {
		Date date;
		String flightInfo;
		String origin;
		String positionStatus;

		public AirplaneView(Date date, String flightInfo, String origin, String positionStatus) {
			this.flightInfo = flightInfo;
			this.origin = origin;
			this.positionStatus = positionStatus;
			this.date = date;
		}

		public String getPositionStatus() {
			return positionStatus;
		}

		public void setPositionStatus(String positionStatus) {
			this.positionStatus = positionStatus;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		public String getFlightInfo() {
			return flightInfo;
		}

		public void setFlightInfo(String flightInfo) {
			this.flightInfo = flightInfo;
		}

	}

}
