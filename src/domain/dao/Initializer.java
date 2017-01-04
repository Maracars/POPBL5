package domain.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

import domain.model.Address;
import domain.model.Airport;
import domain.model.Delay;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Lane;
import domain.model.Node;
import domain.model.Path;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.PlaneMovement;
import domain.model.PlaneStatus;
import domain.model.Route;
import domain.model.Terminal;
import domain.model.users.Airline;
import domain.model.users.Passenger;

// TODO: Auto-generated Javadoc
/**
 * The Class Initializer.
 */
public class Initializer {

	/** The Constant OCCUPIED. */
	private static final boolean OCCUPIED = false;
	
	/** The Constant FREE. */
	private static final boolean FREE = true;
	
	/** The Constant PRINCIPAL. */
	private static final String PRINCIPAL = "PRINCIPAL";
	
	/** The Constant EMAIL. */
	private static final String EMAIL = "jajaja@mondragon.edu";
	
	/** The Constant STREET_AND_NUMBER. */
	private static final String STREET_AND_NUMBER = "Sofia erreginaren kalea jajajaja";
	
	/** The Constant REGION. */
	private static final String REGION = "DEBAGOIENA";
	
	/** The Constant POSTCODE. */
	private static final String POSTCODE = "20570";
	
	/** The Constant USERNAME. */
	private static final String USERNAME = "naranair";
	
	/** The Constant PASSWORD. */
	private static final String PASSWORD = "Nestor123";
	
	/** The Constant NARANAIR. */
	private static final String NARANAIR = "Naranair";
	
	/** The Constant MAX_FLIGHTS. */
	private static final int MAX_FLIGHTS = 6;
	
	/** The Constant HEATHROW. */
	private static final String HEATHROW = "Heathrow";
	
	/** The Constant BERGARA. */
	private static final String BERGARA = "Bergara";
	
	/** The Constant PASSWORD_2. */
	private static final String PASSWORD_2 = "Joxantonio";
	
	/** The Constant USERNAME_2. */
	private static final String USERNAME_2 = "Nestor";
	
	/** The Constant GATE_NUM. */
	private static final int GATE_NUM = 3;
	
	/** The Constant LANE_NAME. */
	private static final String LANE_NAME = "Principal lane";

	/** The Constant NODE_NAME. */
	private static final String NODE_NAME = "Node A";
	
	/** The Constant POSITION_X. */
	private static final double POSITION_X = 33.2;
	
	/** The Constant POSITION_Y. */
	private static final double POSITION_Y = 13.2;

	/** The Constant SERIAL. */
	private static final String SERIAL = "SSSS";

	/** The Constant BOEING. */
	private static final String BOEING = "Boeing";

	/** The Constant SPEED. */
	private static final double SPEED = 0.0;
	
	/** The Constant POSITION. */
	private static final double POSITION = 3.2;
	
	/** The Constant DIRECTION. */
	private static final double DIRECTION = 3.2;

	/** The Constant POSITION_STATUS. */
	private static final String POSITION_STATUS = "ARRIVING";
	
	/** The Constant TECHNICAL_STATUS. */
	private static final String TECHNICAL_STATUS = "NEEDS REVISION";

	/** The Constant EUSKAL_HERRIA. */
	private static final String EUSKAL_HERRIA = "Euskal Herria";

	/** The Constant TERMINAL_NAME. */
	private static final String TERMINAL_NAME = "3";
	
	/** The Constant GATE_NUMBER. */
	private static final int GATE_NUMBER = 2;
	
	/** The Constant AIRPORT_NAME. */
	private static final String AIRPORT_NAME = "Naranair";
	
	/** The Constant MAX_ACTIVE_PLANES. */
	private static final int MAX_ACTIVE_PLANES = 6;

	/**
	 * Inits the path with free lanes.
	 *
	 * @return the path
	 */
	public static Path initPathWithFreeLanes() {
		Lane lane = initFreeLane();
		HibernateGeneric.saveObject(lane);
		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);
		Path path = new Path();

		path.setLaneList(laneList);
		return path;
	}

	/**
	 * Inits the path occupied lanes.
	 *
	 * @return the path
	 */
	public static Path initPathOccupiedLanes() {
		Lane lane = initOccupiedLane();
		HibernateGeneric.saveObject(lane);
		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);
		Path path = new Path();

		path.setLaneList(laneList);
		return path;
	}

	/**
	 * Inits the path occupied and free lanes.
	 *
	 * @return the path
	 */
	public static Path initPathOccupiedAndFreeLanes() {
		Lane lane = initOccupiedLane();
		Lane lane2 = initFreeLane();
		HibernateGeneric.saveObject(lane);
		HibernateGeneric.saveObject(lane2);
		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);
		laneList.add(lane2);
		Path path = new Path();

		path.setLaneList(laneList);
		return path;
	}

	/**
	 * Inits the free lane.
	 *
	 * @return the lane
	 */
	public static Lane initFreeLane() {
		Lane lane = initCompleteLane();
		lane.setStatus(FREE);
		return lane;

	}

	/**
	 * Inits the occupied lane.
	 *
	 * @return the lane
	 */
	public static Lane initOccupiedLane() {
		Lane lane = initCompleteLane();
		lane.setStatus(OCCUPIED);
		return lane;

	}

	/**
	 * Inits the terminal.
	 *
	 * @return the terminal
	 */
	public static Terminal initTerminal() {
		Terminal terminal = new Terminal();
		terminal.setName(TERMINAL_NAME);
		return terminal;

	}

	/**
	 * Inits the terminal.
	 *
	 * @param airport the airport
	 * @return the terminal
	 */
	public static Terminal initTerminal(Airport airport) {
		Terminal terminal = initTerminal();
		terminal.setAirport(airport);
		return terminal;

	}

	/**
	 * Inits the complete route.
	 *
	 * @return the route
	 */
	public static Route initCompleteRoute() {

		Address address = initAddress();
		HibernateGeneric.saveObject(address);
		
		Node positionNode = initNode();
		HibernateGeneric.saveObject(positionNode);

		Airport airport = initAirport(address, positionNode);
		HibernateGeneric.saveObject(airport);

		Terminal terminal = initTerminal(airport);
		HibernateGeneric.saveObject(terminal);

		Gate gate = initGate(terminal);
		HibernateGeneric.saveObject(gate);

		return initRoute(gate, gate);
	}

	/**
	 * Inits the route.
	 *
	 * @param arrivalGate the arrival gate
	 * @param departureGate the departure gate
	 * @return the route
	 */
	public static Route initRoute(Gate arrivalGate, Gate departureGate) {
		Route route = new Route();
		route.setArrivalGate(arrivalGate);
		route.setDepartureGate(departureGate);
		return route;
	}

	/**
	 * Inits the plane status.
	 *
	 * @return the plane status
	 */
	public static PlaneStatus initPlaneStatus() {
		PlaneStatus planeStatus = new PlaneStatus();
		planeStatus.setTechnicalStatus(TECHNICAL_STATUS);
		planeStatus.setPositionStatus(POSITION_STATUS);
		return planeStatus;
	}

	/**
	 * Inits the plane movement.
	 *
	 * @return the plane movement
	 */
	public static PlaneMovement initPlaneMovement() {
		PlaneMovement planeMovement = new PlaneMovement();
		planeMovement.setDirectionX(DIRECTION);
		planeMovement.setDirectionY(DIRECTION);
		planeMovement.setPositionX(POSITION);
		planeMovement.setPositionY(POSITION);
		planeMovement.setSpeed(SPEED);
		return planeMovement;

	}

	/**
	 * Inits the complete plane model.
	 *
	 * @return the plane model
	 */
	public static PlaneModel initCompletePlaneModel() {

		PlaneMaker planeMaker = initPlaneMaker();
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = initPlaneModel(planeMaker);
		return planeModel;
	}

	/**
	 * Inits the plane model.
	 *
	 * @return the plane model
	 */
	public static PlaneModel initPlaneModel() {
		PlaneModel planeModel = new PlaneModel();
		planeModel.setName(SERIAL);
		return planeModel;
	}

	/**
	 * Inits the plane model.
	 *
	 * @param planeMaker the plane maker
	 * @return the plane model
	 */
	public static PlaneModel initPlaneModel(PlaneMaker planeMaker) {
		PlaneModel planeModel = initPlaneModel();
		planeModel.setPlaneMaker(planeMaker);
		return planeModel;
	}

	/**
	 * Inits the plane maker.
	 *
	 * @return the plane maker
	 */
	public static PlaneMaker initPlaneMaker() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		return planeMaker;
	}

	/**
	 * Inits the complete plane.
	 *
	 * @return the plane
	 */
	public static Plane initCompletePlane() {

		Airline airline = initAirline();
		DAOUser.deleteUserWithUsername(airline);
		HibernateGeneric.saveObject(airline);

		PlaneMaker planeMaker = initPlaneMaker();
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = initPlaneModel(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		PlaneStatus planeStatus = initPlaneStatus();
		HibernateGeneric.saveObject(planeStatus);

		PlaneMovement planeMovement = initPlaneMovement();

		return initPlane(airline, planeModel, new Date(), planeStatus, planeMovement);
	}

	/**
	 * Inits the plane.
	 *
	 * @return the plane
	 */
	public static Plane initPlane() {
		Plane plane = new Plane();
		plane.setSerial(SERIAL);
		return plane;

	}

	/**
	 * Inits the plane.
	 *
	 * @param airline the airline
	 * @param planeModel the plane model
	 * @param date the date
	 * @param planeStatus the plane status
	 * @param planeMovement the plane movement
	 * @return the plane
	 */
	public static Plane initPlane(Airline airline, PlaneModel planeModel, Date date, PlaneStatus planeStatus,
			PlaneMovement planeMovement) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setModel(planeModel);
		plane.setFabricationDate(date);
		plane.setPlaneStatus(planeStatus);
		plane.setPlaneMovement(planeMovement);

		return plane;

	}

	/**
	 * Inits the plane.
	 *
	 * @param airline the airline
	 * @param date the date
	 * @param planeStatus the plane status
	 * @return the plane
	 */
	public static Plane initPlane(Airline airline, Date date, PlaneStatus planeStatus) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setFabricationDate(date);
		plane.setPlaneStatus(planeStatus);

		return plane;

	}


	/**
	 * Inits the plane.
	 *
	 * @param planeModel the plane model
	 * @param date the date
	 * @param planeStatus the plane status
	 * @param planeMovement the plane movement
	 * @return the plane
	 */
	public static Plane initPlane(PlaneModel planeModel, Date date, PlaneStatus planeStatus,
			PlaneMovement planeMovement) {
		Plane plane = initPlane();
		plane.setModel(planeModel);
		plane.setFabricationDate(date);
		plane.setPlaneStatus(planeStatus);
		plane.setPlaneMovement(planeMovement);

		return plane;

	}

	/**
	 * Inits the plane.
	 *
	 * @param airline the airline
	 * @param planeModel the plane model
	 * @param planeStatus the plane status
	 * @return the plane
	 */
	public static Plane initPlane(Airline airline, PlaneModel planeModel, PlaneStatus planeStatus) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setModel(planeModel);
		plane.setPlaneStatus(planeStatus);

		return plane;
	}

	/**
	 * Inits the plane.
	 *
	 * @param planeModel the plane model
	 * @param date the date
	 * @param airline the airline
	 * @return the plane
	 */
	public static Plane initPlane(PlaneModel planeModel, Date date, Airline airline) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setModel(planeModel);
		plane.setFabricationDate(date);

		return plane;
	}

	/**
	 * Inits the passenger.
	 *
	 * @param username the username
	 * @param password the password
	 * @param date the date
	 * @return the passenger
	 */
	public static Passenger initPassenger(String username, String password, Date date) {
		Passenger passenger = new Passenger();
		passenger.setUsername(username);
		passenger.setPassword(password);
		passenger.setBirthDate(date);
		return passenger;
	}

	/**
	 * Inits the passenger.
	 *
	 * @param date the date
	 * @param password the password
	 * @return the passenger
	 */
	public static Passenger initPassenger(Date date, String password) {
		Passenger passenger = new Passenger();
		passenger.setPassword(password);
		passenger.setBirthDate(date);
		return passenger;
	}

	/**
	 * Inits the passenger.
	 *
	 * @param username the username
	 * @param date the date
	 * @return the passenger
	 */
	public static Passenger initPassenger(String username, Date date) {
		Passenger passenger = new Passenger();
		passenger.setUsername(username);
		passenger.setBirthDate(date);
		return passenger;
	}

	/**
	 * Inits the passenger.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the passenger
	 */
	public static Passenger initPassenger(String username, String password) {
		Passenger user = new Passenger();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}

	/**
	 * Inits the complete passenger.
	 *
	 * @return the passenger
	 */
	public static Passenger initCompletePassenger() {

		Address address = initAddress();
		HibernateGeneric.saveObject(address);

		Passenger passenger = new Passenger();
		passenger.setUsername(USERNAME_2);
		passenger.setPassword(PASSWORD_2);
		passenger.setAddress(address);
		passenger.setEmail(EMAIL);
		passenger.setBirthDate(new Date());
		return passenger;
	}

	/**
	 * Inits the address.
	 *
	 * @return the address
	 */
	public static Address initAddress() {

		Address address = new Address();
		address.setCity(BERGARA);
		address.setCountry(EUSKAL_HERRIA);
		address.setPostCode(POSTCODE);
		address.setRegion(REGION);
		address.setStreetAndNumber(STREET_AND_NUMBER);
		return address;
	}

	/**
	 * Inits the node.
	 *
	 * @return the node
	 */
	public static Node initNode() {
		Node node = new Node();
		node.setName(NODE_NAME);
		node.setPositionX(POSITION_X);
		node.setPositionY(POSITION_Y);
		return node;
	}

	/**
	 * Inits the complete lane.
	 *
	 * @return the lane
	 */
	public static Lane initCompleteLane() {
		Node startNode = initNode();
		HibernateGeneric.saveObject(startNode);

		Node endNode = initNode();
		HibernateGeneric.saveObject(endNode);

		Address address = initAddress();
		HibernateGeneric.saveObject(address);

		Airport airport = initAirport(address, endNode);
		HibernateGeneric.saveObject(airport);

		return initLane(startNode, endNode, PRINCIPAL, true, airport);
	}

	/**
	 * Inits the lane.
	 *
	 * @return the lane
	 */
	public static Lane initLane() {
		Lane lane = new Lane();
		lane.setName(LANE_NAME);
		return lane;

	}

	/**
	 * Inits the lane.
	 *
	 * @param node1 the node 1
	 * @param node2 the node 2
	 * @param type the type
	 * @param status the status
	 * @param airport the airport
	 * @return the lane
	 */
	public static Lane initLane(Node node1, Node node2, String type, boolean status, Airport airport) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setType(type);
		lane.setStatus(status);
		lane.setAirport(airport);
		return lane;

	}

	/**
	 * Inits the lane.
	 *
	 * @param node1 the node 1
	 * @param node2 the node 2
	 * @param type the type
	 * @param airport the airport
	 * @return the lane
	 */
	public static Lane initLane(Node node1, Node node2, String type, Airport airport) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setAirport(airport);
		lane.setType(type);

		return lane;

	}

	/**
	 * Inits the lane.
	 *
	 * @param node1 the node 1
	 * @param node2 the node 2
	 * @param airport the airport
	 * @param status the status
	 * @return the lane
	 */
	public static Lane initLane(Node node1, Node node2, Airport airport, boolean status) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setAirport(airport);
		lane.setStatus(status);

		return lane;

	}

	/**
	 * Inits the lane.
	 *
	 * @param node1 the node 1
	 * @param node2 the node 2
	 * @param status the status
	 * @param type the type
	 * @return the lane
	 */
	public static Lane initLane(Node node1, Node node2, boolean status, String type) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setStatus(status);
		lane.setType(type);

		return lane;
	}

	/**
	 * Inits the lane.
	 *
	 * @param status the status
	 * @param type the type
	 * @param airport the airport
	 * @return the lane
	 */
	public static Lane initLane(boolean status, String type, Airport airport) {
		Lane lane = initLane();
		lane.setStatus(status);

		lane.setType(type);
		lane.setAirport(airport);

		return lane;
	}

	/**
	 * Inits the gate.
	 *
	 * @param node the node
	 * @return the gate
	 */
	public static Gate initGate(Node node) {
		Gate gate = initGate();
		gate.setPositionNode(node);
		return gate;

	}

	/**
	 * Inits the gate.
	 *
	 * @param node the node
	 * @param terminal the terminal
	 * @return the gate
	 */
	public static Gate initGate(Node node, Terminal terminal) {
		Gate gate = initGate();
		gate.setPositionNode(node);
		gate.setTerminal(terminal);
		return gate;

	}

	/**
	 * Inits the gate.
	 *
	 * @param terminal the terminal
	 * @return the gate
	 */
	public static Gate initGate(Terminal terminal) {
		Gate gate = initGate();
		gate.setTerminal(terminal);
		return gate;

	}

	/**
	 * Inits the gate.
	 *
	 * @return the gate
	 */
	public static Gate initGate() {
		Gate gate = new Gate();
		gate.setNumber(GATE_NUM);
		return gate;
	}

	/**
	 * Inits the airline.
	 *
	 * @return the airline
	 */
	public static Airline initAirline() {
		Address address = initAddress();
		HibernateGeneric.saveObject(address);

		Airline airline = new Airline();
		airline.setName(NARANAIR);
		airline.setUsername(USERNAME);
		airline.setPassword(PASSWORD);
		airline.setAddress(address);
		return airline;

	}

	/**
	 * Inits the complete airport.
	 *
	 * @return the airport
	 */
	public static Airport initCompleteAirport() {

		Address address = initAddress();
		HibernateGeneric.saveObject(address);
		
		Node positionNode = initNode();
		HibernateGeneric.saveObject(positionNode);


		return initAirport(address, positionNode);
	}


	/**
	 * Inits the airport.
	 *
	 * @param address the address
	 * @param positionNode the position node
	 * @return the airport
	 */
	public static Airport initAirport(Address address, Node positionNode) {

		Airport airport = initAirport();
		airport.setAddress(address);
		airport.setPositionNode(positionNode);
		return airport;
	}

	/**
	 * Inits the airport.
	 *
	 * @return the airport
	 */
	public static Airport initAirport() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		airport.setMaxFlights(MAX_FLIGHTS);
		return airport;
	}

	/**
	 * Inits the complete delay.
	 *
	 * @return the delay
	 */
	public static Delay initCompleteDelay() {
		PlaneMaker planeMaker = initPlaneMaker();
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = initPlaneModel(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		Airline airline = initAirline();
		DAOUser.deleteUserWithUsername(airline);
		HibernateGeneric.saveObject(airline);

		PlaneStatus planeStatus = initPlaneStatus();
		HibernateGeneric.saveObject(planeStatus);

		PlaneMovement planeMovement = initPlaneMovement();
		HibernateGeneric.saveObject(planeMovement);

		Plane plane = initPlane(airline, planeModel, new Date(), planeStatus, planeMovement);
		HibernateGeneric.saveObject(plane);

		Passenger passenger = initCompletePassenger();
		DAOUser.deleteUserWithUsername(passenger);
		HibernateGeneric.saveObject(passenger);

		List<Passenger> passengerList = new ArrayList<>();
		passengerList.add(passenger);

		Node node = initNode();
		HibernateGeneric.saveObject(node);

		Address address = initAddress();
		HibernateGeneric.saveObject(address);
		
		Node positionNode = initNode();
		HibernateGeneric.saveObject(positionNode);

		Airport airport = initAirport(address, positionNode);
		HibernateGeneric.saveObject(airport);

		Terminal terminal = initTerminal(airport);
		HibernateGeneric.saveObject(terminal);

		Gate gate = initGate(node, terminal);
		HibernateGeneric.saveObject(gate);

		Route route = initRoute(gate, gate);
		HibernateGeneric.saveObject(route);

		Flight flight = initFlight(plane, route, passengerList);
		HibernateGeneric.saveObject(flight);

		return initDelay(flight);
	}

	/**
	 * Inits the complete flight.
	 *
	 * @return the flight
	 */
	public static Flight initCompleteFlight() {
		PlaneMaker planeMaker = initPlaneMaker();
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = initPlaneModel(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		Airline airline = initAirline();
		DAOUser.deleteUserWithUsername(airline);
		HibernateGeneric.saveObject(airline);

		PlaneStatus planeStatus = initPlaneStatus();
		HibernateGeneric.saveObject(planeStatus);

		PlaneMovement planeMovement = initPlaneMovement();
		HibernateGeneric.saveObject(planeMovement);

		Plane plane = initPlane(airline, planeModel, new Date(), planeStatus, planeMovement);
		HibernateGeneric.saveObject(plane);

		Passenger passenger = initCompletePassenger();
		DAOUser.deleteUserWithUsername(passenger);
		HibernateGeneric.saveObject(passenger);

		List<Passenger> passengerList = new ArrayList<>();
		passengerList.add(passenger);

		Node node = initNode();
		HibernateGeneric.saveObject(node);

		Address address = initAddress();
		HibernateGeneric.saveObject(address);
		
		Node positionNode = initNode();
		HibernateGeneric.saveObject(positionNode);

		Airport airport = initAirport(address, positionNode);
		HibernateGeneric.saveObject(airport);

		Terminal terminal = initTerminal(airport);
		HibernateGeneric.saveObject(terminal);

		Gate gate = initGate(node, terminal);
		HibernateGeneric.saveObject(gate);

		Route route = initRoute(gate, gate);

		HibernateGeneric.saveObject(route);

		return initFlight(plane, route, passengerList);
	}

	/**
	 * Inits the flight.
	 *
	 * @param plane the plane
	 * @param route the route
	 * @return the flight
	 */
	public static Flight initFlight(Plane plane, Route route) {
		Flight flight = initFlight(plane);
		flight.setRoute(route);
		return flight;

	}

	/**
	 * Inits the flight.
	 *
	 * @param plane the plane
	 * @param route the route
	 * @param passengerList the passenger list
	 * @return the flight
	 */
	public static Flight initFlight(Plane plane, Route route, List<Passenger> passengerList) {
		Flight flight = initFlight(plane);
		flight.setRoute(route);
		flight.setPassengerList(passengerList);
		return flight;

	}

	/**
	 * Inits the flight.
	 *
	 * @param plane the plane
	 * @param passengerList the passenger list
	 * @return the flight
	 */
	public static Flight initFlight(Plane plane, List<Passenger> passengerList) {
		Flight flight = initFlight(plane);
		flight.setPassengerList(passengerList);
		return flight;

	}

	/**
	 * Inits the flight.
	 *
	 * @param plane the plane
	 * @return the flight
	 */
	public static Flight initFlight(Plane plane) {
		Flight flight = new Flight();
		flight.setExpectedArrivalDate(new Date());
		flight.setExpectedDepartureDate(new Date());
		flight.setPlane(plane);
		flight.setRealArrivalDate(new Date());
		flight.setRealDepartureDate(new Date());

		return flight;

	}

	/**
	 * Inits the delay.
	 *
	 * @param flight the flight
	 * @return the delay
	 */
	public static Delay initDelay(Flight flight) {
		Delay delay = new Delay();
		delay.setAffectedFlight(flight);
		return delay;

	}
	
	/**
	 * Initialize example on DB.
	 *
	 * @return the airport
	 */
	public static Airport initializeExampleOnDB() {

		Address address = Initializer.initAddress();
		HibernateGeneric.saveObject(address);

		Airport myAirport = new Airport();
		myAirport.setMaxFlights(MAX_ACTIVE_PLANES);
		myAirport.setName(AIRPORT_NAME);
		myAirport.setAddress(address);
		myAirport.setLocale(true);
		HibernateGeneric.saveObject(myAirport);

		Airport airport = new Airport();
		airport.setMaxFlights(MAX_ACTIVE_PLANES);
		airport.setName(AIRPORT_NAME);
		airport.setAddress(address);
		HibernateGeneric.saveObject(airport);

		Node node = Initializer.initNode();
		HibernateGeneric.saveObject(node);
		Node myNode = Initializer.initNode();
		HibernateGeneric.saveObject(myNode);

		Terminal myTerminal = new Terminal();
		myTerminal.setAirport(myAirport);
		myTerminal.setName(TERMINAL_NAME);
		HibernateGeneric.saveObject(myTerminal);

		Terminal terminal = new Terminal();
		terminal.setAirport(airport);
		terminal.setName(TERMINAL_NAME);
		HibernateGeneric.saveObject(terminal);

		Gate gate = new Gate();
		gate.setNumber(GATE_NUMBER);
		gate.setTerminal(terminal);
		gate.setPositionNode(node);
		HibernateGeneric.saveObject(gate);
		Gate myGate = new Gate();
		myGate.setNumber(GATE_NUMBER);
		myGate.setTerminal(myTerminal);
		myGate.setPositionNode(myNode);
		HibernateGeneric.saveObject(myGate);

		Route arrivalRoute = new Route();
		arrivalRoute.setArrivalGate(myGate);
		arrivalRoute.setDepartureGate(gate);
		HibernateGeneric.saveObject(arrivalRoute);

		Route departureRoute = new Route();
		departureRoute.setArrivalGate(gate);
		departureRoute.setDepartureGate(myGate);
		HibernateGeneric.saveObject(departureRoute);

		Lane lane = new Lane();
		lane.setAirport(myAirport);
		lane.setType("PRINCIPAL");
		lane.setName(LANE_NAME);
		lane.setSemaphore(new Semaphore(1, true));
		lane.setStartNode(node);
		lane.setEndNode(node);
		lane.setStatus(true);
		HibernateGeneric.saveObject(lane);

		HibernateGeneric.saveObject(Initializer.initCompletePlaneModel());

		return myAirport;

	}

}
