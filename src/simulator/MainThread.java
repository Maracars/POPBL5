package simulator;

import domain.dao.HibernateGeneric;
import domain.model.Airport;
import domain.model.City;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Route;
import domain.model.State;
import domain.model.Terminal;

public class MainThread {

	public static void main(String[] args) {

		Airport airport = initializeExampleOnDB();
		AirportController ac = new AirportController(airport);
		FlightCreator fc = new FlightCreator(airport, ac);

		AutomaticMaintenance am = new AutomaticMaintenance(airport);

		Thread airController = new Thread(ac);
		airController.start();
		System.out.println("ok");
		Thread flightCreator = new Thread(fc);
		flightCreator.start();
		Thread automaticMaintainance = new Thread(am);
		automaticMaintainance.start();

	}

	private static Airport initializeExampleOnDB() {

		State state = new State();
		state.setName("EH");
		HibernateGeneric.saveOrUpdateObject(state);

		City city = new City();
		city.setName("Dima");
		city.setState(state);
		HibernateGeneric.saveOrUpdateObject(city);

		Airport airport = new Airport();
		airport.setMaxFlights(6);
		airport.setName("Naranair");
		airport.setCity(city);
		HibernateGeneric.saveOrUpdateObject(airport);

		Node node = new Node();
		node.setName("n");
		node.setPositionX(2);
		node.setPositionY(3);
		HibernateGeneric.saveOrUpdateObject(node);

		Terminal terminal = new Terminal();
		terminal.setAirport(airport);
		terminal.setName("T1");
		HibernateGeneric.saveOrUpdateObject(terminal);

		Gate gate = new Gate();
		gate.setNumber(2);
		gate.setTerminal(terminal);
		gate.setPositionNode(node);
		HibernateGeneric.saveOrUpdateObject(gate);

		Route route = new Route();
		route.setArrivalGate(gate);
		route.setDepartureGate(gate);
		HibernateGeneric.saveOrUpdateObject(route);

		return airport;

	}

}
