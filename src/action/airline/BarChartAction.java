package action.airline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.dao.DAOPlane;
import domain.model.Plane;
import domain.model.users.User;

public class BarChartAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String serial;

	
	List<FlightView> data;
	Plane plane;

	@Override
	public String execute() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		int airlineId = user.getId();
		plane = DAOPlane.loadAirplaneWithSerial(airlineId, serial);
		long flightHours = DAOFlight.loadPlaneFlightHours(plane.getId());
		
		data = generateData(plane.getSerial(), flightHours);
		return SUCCESS;
	}

	public List<FlightView> generateData(String planeName, long flightHours) {
		List<FlightView> flightViewList = new ArrayList<FlightView>();
		Random random = new Random();
		long maxFlightHours = random.nextInt(10);
		flightViewList.add(new FlightView(planeName, String.valueOf(flightHours)));
		flightViewList.add(new FlightView("Max Hours", String.valueOf(maxFlightHours)));
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

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	


}
