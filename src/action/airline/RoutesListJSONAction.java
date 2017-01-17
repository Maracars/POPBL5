package action.airline;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class RoutesListJSONAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	List<RouteView> data;

	@Override
	public String execute() throws Exception {
		data = new ArrayList<RouteView>();
		String [] source = {"51.4775", "-0.461389"};
		String [] destination = {"43.1205796", "-3.129408"};
		data.add(new RouteView("12","Heathrow", "Bilbao", source, destination));
		source = new String[]{"40.46537039999999","-3.5951517999999396"};
		destination = new String[]{"52.5588327","13.28843740000002"};
		data.add(new RouteView("23","Madrid", "Berlin", source, destination));
		
		return SUCCESS;
	}
	
	public class RouteView{
		String routeId;
		String sourceAirport;
		String destinationAirport;
		String[] source;
		String[] destination;
		
		public RouteView(String routeId, String sourceAirport, String destinationAirport, String[] source, String[] destination){
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
