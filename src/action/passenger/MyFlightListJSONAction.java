package action.passenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.model.Flight;
import domain.model.users.User;

public class MyFlightListJSONAction<sincronized> extends ActionSupport {	
	private static final long serialVersionUID = 1L;

	private int recordsTotal = 1, recordsFiltered = 0, draw = 0;
	List<FlightView> data;
	String error = null;
	
	@Override
	public synchronized String execute() throws Exception {
		Map<String, String[]> map = ActionContext.getContext().getParameters().toMap();
		
		int orderCol = Integer.parseInt(map.get("order[0][column]")[0]);
		String search = map.get("search[value]")[0];
		String orderDir = map.get("order[0][dir]")[0];
		String originSearch = map.get("columns[0][search][value]")[0];
		String destinationSearch = map.get("columns[1][search][value]")[0];
		
		int start = Integer.parseInt(map.get("start")[0]);
		int length = Integer.parseInt(map.get("length")[0]);
		User user = (User) ActionContext.getContext().getSession().get("user");
		data = generateData(orderCol, orderDir, destinationSearch, originSearch, search);
		
		try{
			recordsTotal = DAOFlight.loadFlightsForTablePasenger("route.departureTerminal.airport.name", "asc", "", "", "",  user.getId()).size();
			
		}catch(NullPointerException e){
			recordsTotal = 0;
		}
		
		recordsFiltered =  data.size();
		
		data = filter(data, start, length);

		return SUCCESS;
	}

	private List<FlightView> filter(List<FlightView> data, int start, int length) {
		data = data.subList(start, (start + length) > data.size() ? data.size()-start : (start + length));
		return data;
	}

	public ArrayList<FlightView> generateData(int orderCol, String orderDir, String dest, String origin, String search) {
		List<Flight> flightList = null;
		ArrayList<FlightView> fvViewsList = new ArrayList<FlightView>();
		String colName = getOrderColumnName(orderCol);
		User user = (User) ActionContext.getContext().getSession().get("user");
		flightList = DAOFlight.loadFlightsForTablePasenger(colName, orderDir, search, dest, origin, user.getId());

		if(flightList != null){
			for(Flight flight : flightList){
				String source = flight.getRoute().getDepartureTerminal().getAirport().getName();
				String destination = flight.getRoute().getArrivalTerminal().getAirport().getName();
				String departureDate = flight.getExpectedDepartureDate().toString();
				String price = String.valueOf(flight.getPrice());
				String planeInfo = flight.getPlane().getSerial();
				String flightId = Integer.toString(flight.getExpectedDepartureDate().after(new Date()) ? flight.getId() : 0);

				fvViewsList.add(new FlightView(source, destination, departureDate, price, planeInfo, flightId));

			}
		}
		return fvViewsList;
	}
	
	public String getOrderColumnName(int orderCol){
		String colName = null;
		switch(orderCol){
		case 0:
			colName = "route.departureTerminal.airport.name";
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
			colName = "route.departureTerminal.airport.name";
			break;
		}
		return colName;
	}


	public class FlightView{
		String source;
		String destination;
		String departureDate;
		String price;
		String planeInfo;
		String flightId;

		public FlightView(String source, String destination, String departureDate, String price, String planeInfo, String flightId){
			this.source = source;
			this.destination = destination;
			this.departureDate = departureDate;
			this.price = price;
			this.planeInfo = planeInfo;
			this.flightId = flightId;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public String getDepartureDate() {
			return departureDate;
		}

		public void setDepartureDate(String departureDate) {
			this.departureDate = departureDate;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getPlaneInfo() {
			return planeInfo;
		}

		public void setPlaneInfo(String planeInfo) {
			this.planeInfo = planeInfo;
		}

		public String getFlightId() {
			return flightId;
		}

		public void setFlightId(String flightId) {
			this.flightId = flightId;
		}
		
		
	}


	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<FlightView> getData() {
		return data;
	}

	public void setData(List<FlightView> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}
	

}
