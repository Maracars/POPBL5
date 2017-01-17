package action.airline;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import action.airline.PieChartAction.FlightView;
import domain.dao.DAOFlight;

public class PieChartAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	List<FlightView> data;
	String[] texts = { "Arrival On Time", "Departure On Time", "Arrival Delay", "Departure Delay" };
	int[] values = new int[4];

	@Override
	public String execute() throws Exception {

		values[0] = DAOFlight.loadDayFlightsArriveOnTime();
		values[1] = DAOFlight.loadDayFlightsDepartureOnTime();
		values[2] = DAOFlight.loadDayFlightsArriveOnNotTime();
		values[3] = DAOFlight.loadDayFlightsDepartureOnNotTime();

		data = generateData();
		return SUCCESS;
	}

	public List<FlightView> generateData() {
		List<FlightView> flightViewList = new ArrayList<FlightView>();
		for (int i = 0; i < texts.length; i++) {
			flightViewList.add(new FlightView(texts[i], String.valueOf(values[i])));

		}

		return flightViewList;

	}

	public class FlightView {
		String name;
		String quantity;

		public FlightView(String name, String quantity) {
			this.name = name;
			this.quantity = quantity;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getQuantity() {
			return quantity;
		}

		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}

	}

	public List<FlightView> getData() {
		return data;
	}

	public void setData(List<FlightView> data) {
		this.data = data;
	}

	public FlightView newFlightView(String string, String string2) {
		return new FlightView(string, string2);
	}

}
