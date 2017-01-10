package action.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.dao.HibernateGeneric;
import domain.model.Flight;

public class FlightListJSONAction<sincronized> extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private Integer draw = 0, recordsTotal = 1, recordsFiltered = 0;
	private List<FlightView> data = new ArrayList<FlightView>();
	String error = null;

	@Override
	public synchronized String execute() throws Exception {

		Map<String, String[]> map = ActionContext.getContext().getParameters().toMap();
		for (Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key:"+ key +" value: " + Arrays.toString((String[])value));
		}

		String search = map.get("search[value]")[0];
		int orderCol = Integer.parseInt(map.get("order[0][column]")[0]);
		String orderDir = map.get("order[0][dir]")[0];

		int start = Integer.parseInt(map.get("start")[0]);
		int length = Integer.parseInt(map.get("length")[0]);

		data = generateData(search, orderCol, orderDir, start, length);

		recordsTotal = HibernateGeneric.loadAllObjects(new Flight()).size();

		filter(data, search);

		recordsFiltered = HibernateGeneric.loadAllObjects(new Flight()).size();

		return SUCCESS;
	}

	private List<FlightView> filter(List<FlightView> data, String search) {
		search = search.toLowerCase();
		for (Iterator<FlightView> fIt = data.iterator(); fIt.hasNext();) {
			FlightView fv = fIt.next();
			if (fv.getAirplane().toLowerCase().contains(search))
				continue;
			if (fv.getOrigin().toLowerCase().contains(search))
				continue;
			if (fv.getDestination().toLowerCase().contains(search))
				continue;
			if (fv.getExpectedArrivalDate().toLowerCase().contains(search))
				continue;
			if (fv.getExpectedDepartureDate().toLowerCase().contains(search))
				continue;
			fIt.remove();
		}
		return data;
	}


	public ArrayList<FlightView> generateData(String search, int orderCol, String orderDir, int start, int length) {
		List<Flight> flightList = null;
		ArrayList<FlightView> flightViews = new ArrayList<FlightView>();
		String colName = getOrderColumnName(orderCol);

		flightList = DAOFlight.loadFlightsForTable(colName, orderDir, start, length);

		for(Flight f : flightList){
			String airplane = f.getPlane().getSerial();
			String origin = f.getRoute().getDepartureTerminal().getAirport().getName();
			String destination = f.getRoute().getArrivalTerminal().getAirport().getName();
			String terminal = f.getExpectedArrivalDate().toString();
			String gate = f.getExpectedDepartureDate().toString();

			flightViews.add(new FlightView(airplane, origin, destination, terminal, gate));
		}

		return flightViews;
	}


	public String getOrderColumnName(int orderCol){
		String colName = null;
		switch(orderCol){
		case 0:
			colName = "plane";
			break;
		case 1:
			colName = "route.departureTerminal.airport";
			break;
		case 2:
			colName = "route.arrivalTerminal.airport";
			break;
		case 3:
			colName = "expectedArrivalDate";
			break;
		case 4:
			colName = "expectedDepartureDate";
			break;
		default:
			colName = "plane";
			break;
		}
		return colName;
	}

	public class FlightView {
		String airplane;
		String origin;
		String destination;
		String expectedArrivalDate;
		String expectedDepartureDate;

		public FlightView(String airplane, String origin, String destination, String expectedArrivalDate, String expectedDepartureDate) {
			this.airplane = airplane;
			this.origin = origin;
			this.destination = destination;
			this.expectedArrivalDate = expectedArrivalDate;
			this.expectedDepartureDate = expectedDepartureDate;
		}

		public String getAirplane() {
			return airplane;
		}

		public void setAirplane(String airplane) {
			this.airplane = airplane;
		}

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public String getExpectedArrivalDate() {
			return expectedArrivalDate;
		}

		public void setExpectedArrivalDate(String expectedArrivalDate) {
			this.expectedArrivalDate = expectedArrivalDate;
		}

		public String getExpectedDepartureDate() {
			return expectedDepartureDate;
		}

		public void setExpectedDepartureDate(String expectedDepartureDate) {
			this.expectedDepartureDate = expectedDepartureDate;
		}
		
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

	public List<FlightView> getData() {
		return data;
	}

	public void setData(List<FlightView> data) {
		this.data = data;
	}

}
