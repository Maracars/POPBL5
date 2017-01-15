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

public class AirplaneMoreInfoAction extends ActionSupport{

	private static final String AIRLINE_PLANE_NOT_FOUND = "airline.planeNotFound";

	private static final String AIRLINE_SERIAL_BLANK = "airline.serialBlank";

	private static final String JSON = "json";

	private static final long serialVersionUID = 1L;

	String serial;
	Plane plane;
	List<FlightView> data;

	@Override
	public String execute() throws Exception {
		String ret = SUCCESS;

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		int airlineId = user.getId();

		if(serial.contains("JSON")){

			String serialNumber[] = serial.split("[/]");

			plane = DAOPlane.loadAirplaneWithSerial(airlineId, serialNumber[2]);

			long flightHours = DAOFlight.loadPlaneFlightHours(plane.getId());
			
			data = generateData(plane.getSerial(), flightHours);
			
			ret = JSON;

		} else if(serial == null || serial.equals("")){
			addActionError(AIRLINE_SERIAL_BLANK);
		}else{


			plane = DAOPlane.loadAirplaneWithSerial(airlineId, serial);

			if(plane != null){
				ret = SUCCESS;
			}else{
				addActionError(AIRLINE_PLANE_NOT_FOUND);
			}
		}
		return ret;
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

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public List<FlightView> getData() {
		return data;
	}

	public void setData(List<FlightView> data) {
		this.data = data;
	}

}
