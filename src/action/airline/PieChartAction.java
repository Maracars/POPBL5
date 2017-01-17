package action.airline;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;

public class PieChartAction extends ActionSupport {

	private static final int FORTH_VALUE = 3;

	private static final int THIRD_VALUE = 2;

	private static final int SECOND_VALUE = 1;

	private static final int VALUE_NUMBER = 4;

	private static final long serialVersionUID = 1L;

	List<FlightView> data;
	String[] texts = { "Arrival On Time", "Departure On Time", "Arrival Delay", "Departure Delay" };
	int[] values = new int[VALUE_NUMBER];

	@Override
	public String execute() throws Exception {

		values[0] = DAOFlight.loadDayFlightsArriveOnTime();
		values[SECOND_VALUE] = DAOFlight.loadDayFlightsDepartureOnTime();
		values[THIRD_VALUE] = DAOFlight.loadDayFlightsArriveOnNotTime();
		values[FORTH_VALUE] = DAOFlight.loadDayFlightsDepartureOnNotTime();

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

	public List<FlightView> getData() {
		return data;
	}

	public void setData(List<FlightView> data) {
		this.data = data;
	}

	public FlightView newFlightView(String string, String string2) {
		return new FlightView(string, string2);
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

}
