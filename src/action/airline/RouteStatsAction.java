package action.airline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.model.users.User;

public class RouteStatsAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	List<FlightView> data;
	int[] values = { 5, 6, 7 };
	String[] texts = { "Arrival On Time", "Departure On Time", "Arrival Delay" };

	@Override
	public String execute() throws Exception {

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		String airlineUser = user.getUsername();

		data = DAOFlight.loadFlightsOfAirlineByRoute(airlineUser);
		if (data == null || data.size() == 0) {
			data = generateData();

		}

		return Action.SUCCESS;
	}

	public FlightView newFlightView(String name, String quantity) {
		return new FlightView(name, quantity);
	}

	public List<FlightView> generateData() {
		List<FlightView> flightViewList = new ArrayList<FlightView>();
		for (int i = 0; i < values.length; i++) {
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
