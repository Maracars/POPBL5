package action.airline;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOFlight;

public class PieChartAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	List<FlightView> data;
	 
		@Override
		public String execute() throws Exception {
			int numArriveOnTime = DAOFlight.loadDayFlightsArriveOnTime();
			int numDepartureOnTime = DAOFlight.loadDayFlightsDepartureOnTime();
			int numArriveNotTime = DAOFlight.loadDayFlightsArriveOnNotTime();
			int numDepartureNotTime = DAOFlight.loadDayFlightsDepartureOnNotTime();
			
			data = generateData(numArriveOnTime, numDepartureOnTime, numArriveNotTime, numDepartureNotTime);
			return SUCCESS;
		}
		
		public List<FlightView> generateData(int numArriveOnTime, int numDepartureOnTime, int numArriveNotTime, int numDepartureNotTime) {
			List<FlightView> flightViewList = new ArrayList<FlightView>();
			flightViewList.add(new FlightView("Arrival On Time", String.valueOf(numArriveOnTime)));
			flightViewList.add(new FlightView("Departure On Time", String.valueOf(numDepartureOnTime)));
			flightViewList.add(new FlightView("Arrival Delay", String.valueOf(numArriveNotTime)));
			flightViewList.add(new FlightView("Departure Delay", String.valueOf(numDepartureNotTime)));
			
			return flightViewList;

		}


		public class FlightView{
			String name;
			String quantity;
			

			public FlightView(String name, String quantity){
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
	 

}
