package action.airline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAORoute;
import domain.model.Route;
import domain.model.users.User;

/**
 * The Class RoutesListJSONAction.
 *
 * @param <sincronized> the generic type
 */
public class RoutesListJSONAction<sincronized> extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data. */
	List<RouteView> data;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public synchronized String execute() throws Exception {

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		int airlineId = user.getId();

		List<Route> routeList = DAORoute.getRouteListFromAirline(airlineId);

		data = generateData(routeList);

		return SUCCESS;
	}

	/**
	 * Generate data.
	 *
	 * @param routeList the route list
	 * @return the list
	 */
	public List<RouteView> generateData(List<Route> routeList) {
		List<RouteView> data = new ArrayList<RouteView>();
		for (Route route : routeList) {
			String routeId = String.valueOf(route.getId());
			String sourceAirport = route.getDepartureTerminal().getAirport().getName();
			String destinationAirport = route.getArrivalTerminal().getAirport().getName();
			String[] source = {
					String.valueOf(route.getDepartureTerminal().getAirport().getPositionNode().getPositionX()),
					String.valueOf(route.getDepartureTerminal().getAirport().getPositionNode().getPositionY()) };
			String[] destination = {
					String.valueOf(route.getArrivalTerminal().getAirport().getPositionNode().getPositionX()),
					String.valueOf(route.getArrivalTerminal().getAirport().getPositionNode().getPositionY()) };

			data.add(new RouteView(routeId, sourceAirport, destinationAirport, source, destination));
		}

		return data;
	}

	/**
	 * The Class RouteView.
	 */
	public class RouteView {
		
		/** The route id. */
		String routeId;
		
		/** The source airport. */
		String sourceAirport;
		
		/** The destination airport. */
		String destinationAirport;
		
		/** The source. */
		String[] source;
		
		/** The destination. */
		String[] destination;

		/**
		 * Instantiates a new route view.
		 *
		 * @param routeId the route id
		 * @param sourceAirport the source airport
		 * @param destinationAirport the destination airport
		 * @param source the source
		 * @param destination the destination
		 */
		public RouteView(String routeId, String sourceAirport, String destinationAirport, String[] source,
				String[] destination) {
			this.routeId = routeId;
			this.sourceAirport = sourceAirport;
			this.destinationAirport = destinationAirport;
			this.source = source;
			this.destination = destination;
		}

		/**
		 * Gets the route id.
		 *
		 * @return the route id
		 */
		public String getRouteId() {
			return routeId;
		}

		/**
		 * Sets the route id.
		 *
		 * @param routeId the new route id
		 */
		public void setRouteId(String routeId) {
			this.routeId = routeId;
		}

		/**
		 * Gets the source airport.
		 *
		 * @return the source airport
		 */
		public String getSourceAirport() {
			return sourceAirport;
		}

		/**
		 * Sets the source airport.
		 *
		 * @param sourceAirport the new source airport
		 */
		public void setSourceAirport(String sourceAirport) {
			this.sourceAirport = sourceAirport;
		}

		/**
		 * Gets the source.
		 *
		 * @return the source
		 */
		public String[] getSource() {
			return source;
		}

		/**
		 * Sets the source.
		 *
		 * @param source the new source
		 */
		public void setSource(String[] source) {
			this.source = source;
		}

		/**
		 * Gets the destination.
		 *
		 * @return the destination
		 */
		public String[] getDestination() {
			return destination;
		}

		/**
		 * Sets the destination.
		 *
		 * @param destination the new destination
		 */
		public void setDestination(String[] destination) {
			this.destination = destination;
		}

		/**
		 * Gets the destination airport.
		 *
		 * @return the destination airport
		 */
		public String getDestinationAirport() {
			return destinationAirport;
		}

		/**
		 * Sets the destination airport.
		 *
		 * @param destinationAirport the new destination airport
		 */
		public void setDestinationAirport(String destinationAirport) {
			this.destinationAirport = destinationAirport;
		}

	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<RouteView> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<RouteView> data) {
		this.data = data;
	}

}
