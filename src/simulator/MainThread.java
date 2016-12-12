package simulator;

import domain.dao.Initializer;
import domain.model.Airport;

public class MainThread {

	public static void main(String[] args) {

		Airport airport = Initializer.initCompleteAirport();

		FlightCreator fc = new FlightCreator(airport);
		AirportController ac = new AirportController(airport);
		AutomaticMaintenance am = new AutomaticMaintenance(airport);

		fc.run();
		ac.run();
		am.run();

	}

}
