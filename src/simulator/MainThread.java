package simulator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import domain.dao.DAOAirport;
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
	private static final int THREAD_NUM = 3;
	private static final int GATE_NUMBER = 2;
	private static final String TERMINAL_NAME = "T1";
	private static final String AIRPORT_NAME = "Naranair";
	private static final int MAX_ACTIVE_PLANES = 6;
	private static final String LANE_NAME = "Pincipal Lane";

	private static ExecutorService threadPool;

	public static void createMainThread(String[] args) {

		threadPool = Executors.newFixedThreadPool(THREAD_NUM);

		
		Airport airport = initialize();
		AirportController ac = new AirportController(airport);
		FlightCreator fc = new FlightCreator(airport, ac);
		AutomaticMaintenance am = new AutomaticMaintenance(airport);

		threadPool.submit(ac);
		threadPool.submit(fc);
		threadPool.submit(am);

	}

	public static void finishThreads() {
		threadPool.shutdownNow();
	}

	
	private static Airport initialize() {
		Airport myAirport = null;
		myAirport = DAOAirport.getLocaleAirport();
		
		if(myAirport == null) {
			myAirport = initializeExampleOnDB();
		}
		
		return myAirport;
	}
	
	private static Airport initializeExampleOnDB() {

		
		Address address = Initializer.initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport myAirport = new Airport();
		myAirport.setMaxFlights(MAX_ACTIVE_PLANES);
		myAirport.setName(AIRPORT_NAME);
		myAirport.setAddress(address);
		myAirport.setLocale(true);
		HibernateGeneric.saveOrUpdateObject(myAirport);

		Airport airport = new Airport();
		airport.setMaxFlights(MAX_ACTIVE_PLANES);
		airport.setName(AIRPORT_NAME);
		airport.setAddress(address);
		HibernateGeneric.saveOrUpdateObject(airport);

		Node node = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(node);
		Node myNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(myNode);

		Terminal myTerminal = new Terminal();
		myTerminal.setAirport(myAirport);
		myTerminal.setName(TERMINAL_NAME);
		HibernateGeneric.saveOrUpdateObject(myTerminal);

		Terminal terminal = new Terminal();
		terminal.setAirport(airport);
		terminal.setName(TERMINAL_NAME);
		HibernateGeneric.saveOrUpdateObject(terminal);

		Gate gate = new Gate();
		gate.setNumber(GATE_NUMBER);
		gate.setTerminal(terminal);
		gate.setPositionNode(node);
		HibernateGeneric.saveOrUpdateObject(gate);
		Gate myGate = new Gate();
		myGate.setNumber(GATE_NUMBER);
		myGate.setTerminal(myTerminal);
		myGate.setPositionNode(myNode);
		HibernateGeneric.saveOrUpdateObject(myGate);

		Route arrivalRoute = new Route();
		arrivalRoute.setArrivalGate(myGate);
		arrivalRoute.setDepartureGate(gate);
		HibernateGeneric.saveOrUpdateObject(arrivalRoute);

		Route departureRoute = new Route();
		departureRoute.setArrivalGate(gate);
		departureRoute.setDepartureGate(myGate);
		HibernateGeneric.saveOrUpdateObject(departureRoute);

		Lane lane = new Lane();
		lane.setAirport(myAirport);
		lane.setPrincipal(true);
		lane.setName(LANE_NAME);
		lane.setSemaphore(new Semaphore(1, true));
		lane.setStartNode(node);
		lane.setEndNode(node);
		lane.setStatus(true);
		HibernateGeneric.saveOrUpdateObject(lane);

		HibernateGeneric.saveOrUpdateObject(Initializer.initCompletePlaneModel());

		return myAirport;

	}

}
