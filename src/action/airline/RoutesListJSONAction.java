package action.airline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAORoute;
import domain.model.Route;
import domain.model.users.User;

public class RoutesListJSONAction<sincronized> extends ActionSupport {

	private static final long serialVersionUID = 1L;

	List<RouteView> data;

	@Override
	public synchronized String execute() throws Exception {

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		int airlineId = user.getId();

		List<Route> routeList = DAORoute.getRouteListFromAirline(airlineId);

		data = generateData(routeList);

		return SUCCESS;
	}

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

	public class RouteView {
		String routeId;
		String sourceAirport;
		String destinationAirport;
		String[] source;
		String[] destination;

		public RouteView(String routeId, String sourceAirport, String destinationAirport, String[] source,
				String[] destination) {
			this.routeId = routeId;
			this.sourceAirport = sourceAirport;
			this.destinationAirport = destinationAirport;
			this.source = source;
			this.destination = destination;
		}

		public String getRouteId() {
			return routeId;
		}

		public void setRouteId(String routeId) {
			this.routeId = routeId;
		}

		public String getSourceAirport() {
			return sourceAirport;
		}

		public void setSourceAirport(String sourceAirport) {
			this.sourceAirport = sourceAirport;
		}

		public String[] getSource() {
			return source;
		}

		public void setSource(String[] source) {
			this.source = source;
		}

		public String[] getDestination() {
			return destination;
		}

		public void setDestination(String[] destination) {
			this.destination = destination;
		}

		public String getDestinationAirport() {
			return destinationAirport;
		}

		public void setDestinationAirport(String destinationAirport) {
			this.destinationAirport = destinationAirport;
		}

	}

	public List<RouteView> getData() {
		return data;
	}

	public void setData(List<RouteView> data) {
		this.data = data;
	}

}
