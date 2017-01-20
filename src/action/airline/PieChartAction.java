package action.airline;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;

/**
 * The Class PieChartAction.
 */
public class PieChartAction extends ActionSupport {

	/** The Constant FORTH_VALUE. */
	private static final int FORTH_VALUE = 3;

	/** The Constant THIRD_VALUE. */
	private static final int THIRD_VALUE = 2;

	/** The Constant SECOND_VALUE. */
	private static final int SECOND_VALUE = 1;

	/** The Constant VALUE_NUMBER. */
	private static final int VALUE_NUMBER = 4;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data. */
	List<FlightView> data;
	
	/** The texts. */
	String[] texts = { "Arrival On Time", "Departure On Time", "Arrival Delay", "Departure Delay" };
	
	/** The values. */
	int[] values = new int[VALUE_NUMBER];

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {

		values[0] = DAOFlight.loadDayFlightsArriveOnTime();
		values[SECOND_VALUE] = DAOFlight.loadDayFlightsDepartureOnTime();
		values[THIRD_VALUE] = DAOFlight.loadDayFlightsArriveOnNotTime();
		values[FORTH_VALUE] = DAOFlight.loadDayFlightsDepartureOnNotTime();

		data = generateData();
		return SUCCESS;
	}

	/**
	 * Generate data.
	 *
	 * @return the list
	 */
	public List<FlightView> generateData() {
		List<FlightView> flightViewList = new ArrayList<FlightView>();
		for (int i = 0; i < texts.length; i++) {
			flightViewList.add(new FlightView(texts[i], String.valueOf(values[i])));

		}

		return flightViewList;

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
	 * New flight view.
	 *
	 * @param string the string
	 * @param string2 the string 2
	 * @return the flight view
	 */
	public FlightView newFlightView(String string, String string2) {
		return new FlightView(string, string2);
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
