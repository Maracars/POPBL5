package action.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Flight;

public class FlightListJSONAction<sincronized> extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private Integer draw = 0, recordsTotal = 1, recordsFiltered = 0, length = 0, start = 0;
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

		data = generateData();
		recordsTotal = data.size();

		data = filter(data, search);
		data = sort(data, orderCol, orderDir);
		recordsFiltered = data.size();
		if (data.size() != 0)
			data = data.subList(start, (start + length > data.size()) ? data.size() : start + length);
		data = new ArrayList<FlightView>(data);
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
			if (fv.getTerminal().toLowerCase().contains(search))
				continue;
			if (fv.getGate().toLowerCase().contains(search))
				continue;
			fIt.remove();
		}
		return data;
	}
	
	private List<FlightView> sort(List<FlightView> data, int orderCol, String orderDir) {
		switch (orderCol) {
		/*case 0:
			if (orderDir.equals("asc"))
				data.sort((FlightView uv1, FlightView uv2) -> uv1.getAirplane().compareToIgnoreCase(uv2.getAirplane()));
			if (orderDir.equals("desc"))
				data.sort((FlightView uv1, FlightView uv2) -> -uv1.getAirplane().compareToIgnoreCase(uv2.getAirplane()));
			break;*/
		case 1:
			if (orderDir.equals("asc"))
				data.sort((FlightView uv1, FlightView uv2) -> uv1.getOrigin().compareToIgnoreCase(uv2.getOrigin()));
			if (orderDir.equals("desc"))
				data.sort((FlightView uv1, FlightView uv2) -> -uv1.getOrigin().compareToIgnoreCase(uv2.getOrigin()));
			break;
		case 2:
			if (orderDir.equals("asc"))
				data.sort((FlightView uv1, FlightView uv2) -> uv1.getDestination().compareToIgnoreCase(uv2.getDestination()));
			if (orderDir.equals("desc"))
				data.sort((FlightView uv1, FlightView uv2) -> -uv1.getDestination().compareTo(uv2.getDestination()));
			break;
		case 3:
			if (orderDir.equals("asc"))
				data.sort((FlightView uv1, FlightView uv2) -> uv1.getTerminal().compareToIgnoreCase(uv2.getTerminal()));
			if (orderDir.equals("desc"))
				data.sort((FlightView uv1, FlightView uv2) -> -uv1.getTerminal().compareToIgnoreCase(uv2.getTerminal()));
			break;
		case 4:
			if (orderDir.equals("asc"))
				data.sort((FlightView uv1, FlightView uv2) -> uv1.getGate().compareToIgnoreCase(uv2.getGate()));
			if (orderDir.equals("desc"))
				data.sort((FlightView uv1, FlightView uv2) -> -uv1.getGate().compareToIgnoreCase(uv2.getGate()));
			break;
		default:
			data.sort((FlightView uv1, FlightView uv2) -> uv1.getAirplane().compareToIgnoreCase(uv2.getAirplane()));
			break;
		}
		return data;
	}
	
	public List<FlightView> generateData() {
		List<Object> flights = HibernateGeneric.loadAllObjects(new Flight());
		ArrayList<FlightView> flightViews = new ArrayList<>();
		for (Object f : flights) {
			Flight flight = (Flight) f;
			
			String airplane = flight.getPlane().getSerial();
			String origin = "ajajajja";
			String destination = "jajajajaja";
			String terminal = flight.getRoute().getArrivalTerminal().getName();
			String gate = "jajajajajaj";
			
			flightViews.add(new FlightView(airplane, origin, destination, terminal, gate));
			
		}

		return flightViews;
	}
	
	public class FlightView {
		String airplane;
		String origin;
		String destination;
		String terminal;
		String gate;

		public FlightView(String airplane, String origin, String destination, String terminal, String gate) {
			this.airplane = airplane;
			this.origin = origin;
			this.destination = destination;
			this.terminal = terminal;
			this.gate = gate;
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

		public String getTerminal() {
			return terminal;
		}

		public void setTerminal(String terminal) {
			this.terminal = terminal;
		}

		public String getGate() {
			return gate;
		}

		public void setGate(String gate) {
			this.gate = gate;
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

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public List<FlightView> getData() {
		return data;
	}

	public void setData(List<FlightView> data) {
		this.data = data;
	}
	
	
	

}
