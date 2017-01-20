package action.airline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;
import domain.model.users.User;

/**
 * The Class RouteStatsAction.
 */
public class RouteStatsAction extends ActionSupport {

	/** The Constant _7. */
	private static final int _7 = 7;

	/** The Constant _6. */
	private static final int _6 = 6;

	/** The Constant _5. */
	private static final int _5 = 5;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data. */
	List<FlightView> data;
	
	/** The values. */
	int[] values = { _5, _6, _7 };
	
	/** The texts. */
	String[] texts = { "Arrival On Time", "Departure On Time", "Arrival Delay" };

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
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

	/**
	 * New flight view.
	 *
	 * @param name the name
	 * @param quantity the quantity
	 * @return the flight view
	 */
	public FlightView newFlightView(String name, String quantity) {
		return new FlightView(name, quantity);
	}

	/**
	 * Generate data.
	 *
	 * @return the list
	 */
	public List<FlightView> generateData() {
		List<FlightView> flightViewList = new ArrayList<FlightView>();
		for (int i = 0; i < values.length; i++) {
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
