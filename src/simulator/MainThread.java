package simulator;

import domain.model.Airport;

public class MainThread {

	public static void main(String[] args) {

		Airport airport = new Airport();

		FlightCreator fc = new FlightCreator(airport);
		AirportController ac = new AirportController(airport);
		AutomaticMaintenance am = new AutomaticMaintenance(airport);

		fc.run();
		ac.run();
		am.run();

	}

}
