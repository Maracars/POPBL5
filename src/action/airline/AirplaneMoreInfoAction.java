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

/**
 * The Class AirplaneMoreInfoAction.
 */
public class AirplaneMoreInfoAction extends ActionSupport {

	/** The Constant TEN. */
	private static final int TEN = 10;

	/** The Constant AIRLINE_PLANE_NOT_FOUND. */
	private static final String AIRLINE_PLANE_NOT_FOUND = "airline.planeNotFound";

	/** The Constant AIRLINE_SERIAL_BLANK. */
	private static final String AIRLINE_SERIAL_BLANK = "airline.serialBlank";

	/** The Constant JSON. */
	private static final String JSON = "json";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The serial. */
	String serial;
	
	/** The plane. */
	Plane plane;
	
	/** The data. */
	List<FlightView> data;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		String ret = SUCCESS;

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		int airlineId = user.getId();

		if (serial.contains("JSON")) {

			String serialNumber[] = serial.split("[/]");

			plane = DAOPlane.loadAirplaneWithSerial(airlineId, serialNumber[2]);

			long flightHours = DAOFlight.loadPlaneFlightHours(plane.getId());

			data = generateData(plane.getSerial(), flightHours);

			ret = JSON;

		} else if (serial == null || serial.equals("")) {
			addActionError(AIRLINE_SERIAL_BLANK);
		} else {

			plane = DAOPlane.loadAirplaneWithSerial(airlineId, serial);

			if (plane != null) {
				ret = SUCCESS;
			} else {
				addActionError(AIRLINE_PLANE_NOT_FOUND);
			}
		}
		return ret;
	}

	/**
	 * Generate data.
	 *
	 * @param planeName the plane name
	 * @param flightHours the flight hours
	 * @return the list
	 */
	public List<FlightView> generateData(String planeName, long flightHours) {
		List<FlightView> flightViewList = new ArrayList<FlightView>();
		Random random = new Random();
		long maxFlightHours = random.nextInt(TEN);
		flightViewList.add(new FlightView(planeName, String.valueOf(flightHours)));
		flightViewList.add(new FlightView("Max Hours", String.valueOf(maxFlightHours)));
		return flightViewList;

	}

	/**
	 * Gets the serial.
	 *
	 * @return the serial
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * Sets the serial.
	 *
	 * @param serial the new serial
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * Gets the plane.
	 *
	 * @return the plane
	 */
	public Plane getPlane() {
		return plane;
	}

	/**
	 * Sets the plane.
	 *
	 * @param plane the new plane
	 */
	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<FlightView> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<FlightView> data) {
		this.data = data;
	}

	/**
	 * The Class FlightView.
	 */
	public class FlightView {
		
		/** The name. */
		String name;
		
		/** The quantity. */
		String quantity;

		/**
		 * Instantiates a new flight view.
		 *
		 * @param name the name
		 * @param quantity the quantity
		 */
		public FlightView(String name, String quantity) {
			this.name = name;
			this.quantity = quantity;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets the name.
		 *
		 * @param name the new name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets the quantity.
		 *
		 * @return the quantity
		 */
		public String getQuantity() {
			return quantity;
		}

		/**
		 * Sets the quantity.
		 *
		 * @param quantity the new quantity
		 */
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}

	}

}
