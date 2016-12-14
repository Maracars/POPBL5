package simulator;

import java.util.concurrent.Semaphore;

import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Address;
import domain.model.Airport;
import domain.model.Gate;
import domain.model.Lane;
import domain.model.Node;
import domain.model.Route;
import domain.model.Terminal;

public class MainThread {
	private static final int GATE_NUMBER = 2;
	private static final String TERMINAL_NAME = "T1";
	private static final String AIRPORT_NAME = "Naranair";
	private static final int MAX_ACTIVE_PLANES = 6;
	private static final String LANE_NAME = "Pincipal Lane";


	public static void main(String[] args) {

		Airport airport = initializeExampleOnDB();
		AirportController ac = new AirportController(airport);
		FlightCreator fc = new FlightCreator(airport, ac);

		AutomaticMaintenance am = new AutomaticMaintenance(airport);

		Thread airController = new Thread(ac);
		airController.start();
		Thread flightCreator = new Thread(fc);
		flightCreator.start();
		//Thread automaticMaintainance = new Thread(am);
		//automaticMaintainance.start();

	}

	private static Airport initializeExampleOnDB() {

		Address address = Initializer.initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport airport = new Airport();
		airport.setMaxFlights(MAX_ACTIVE_PLANES);
		airport.setName(AIRPORT_NAME);
		airport.setAddress(address);
		HibernateGeneric.saveOrUpdateObject(airport);

		Node node = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(node);

		Terminal terminal = new Terminal();
		terminal.setAirport(airport);
		terminal.setName(TERMINAL_NAME);
		HibernateGeneric.saveOrUpdateObject(terminal);

		Gate gate = new Gate();
		gate.setNumber(GATE_NUMBER);
		gate.setTerminal(terminal);
		gate.setPositionNode(node);
		HibernateGeneric.saveOrUpdateObject(gate);

		Route route = new Route();
		route.setArrivalGate(gate);
		route.setDepartureGate(gate);
		HibernateGeneric.saveOrUpdateObject(route);
		
		Lane lane = new Lane();
		lane.setAirport(airport);
		lane.setPrincipal(true);
		lane.setName(LANE_NAME);
		lane.setSemaphore(new Semaphore(1, true));
		lane.setStartNode(node);
		lane.setEndNode(node);
		lane.setStatus(true);
		HibernateGeneric.saveOrUpdateObject(lane);
		
		HibernateGeneric.saveOrUpdateObject(Initializer.initCompletePlaneModel());

		return airport;

	}

}
