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

public class Initializer {

	private static final boolean OCCUPIED = false;
	private static final boolean FREE = true;
	private static final String PRINCIPAL = "PRINCIPAL";
	private static final String EMAIL = "jajaja@mondragon.edu";
	private static final String STREET_AND_NUMBER = "Sofia erreginaren kalea jajajaja";
	private static final String REGION = "DEBAGOIENA";
	private static final String POSTCODE = "20570";
	private static final String USERNAME = "naranair";
	private static final String PASSWORD = "Nestor123";
	private static final String NARANAIR = "Naranair";
	private static final int MAX_FLIGHTS = 6;
	private static final String HEATHROW = "Heathrow";
	private static final String BERGARA = "Bergara";
	private static final String PASSWORD_2 = "Joxantonio";
	private static final String USERNAME_2 = "Nestor";
	private static final int GATE_NUM = 3;
	private static final String LANE_NAME = "Principal lane";

	private static final String NODE_NAME = "Node A";
	private static final double POSITION_X = 33.2;
	private static final double POSITION_Y = 13.2;

	private static final String SERIAL = "SSSS";

	private static final String BOEING = "Boeing";

	private static final double SPEED = 0.0;
	private static final double POSITION = 3.2;
	private static final double DIRECTION = 3.2;

	private static final String POSITION_STATUS = "ARRIVING";
	private static final String TECHNICAL_STATUS = "NEEDS REVISION";

	private static final String EUSKAL_HERRIA = "Euskal Herria";

	private static final String TERMINAL_NAME = "3";
	
	private static final int GATE_NUMBER = 2;
	private static final String AIRPORT_NAME = "Naranair";
	private static final int MAX_ACTIVE_PLANES = 6;

	public static Path initPathWithFreeLanes() {
		Lane lane = initFreeLane();
		HibernateGeneric.saveOrUpdateObject(lane);
		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);
		Path path = new Path();

		path.setLaneList(laneList);
		return path;
	}
	
	public static Path initPathWithFullandFreeLanes() {
		Lane lane = initFreeLane();
		HibernateGeneric.saveOrUpdateObject(lane);
		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);
		lane = initCompleteLane();
		lane.setStatus(OCCUPIED);
		HibernateGeneric.saveOrUpdateObject(lane);
		laneList.add(lane);
		Path path = new Path();

		path.setLaneList(laneList);
		return path;
	}

	public static Path initPathOccupiedLanes() {
		Lane lane = initOccupiedLane();
		HibernateGeneric.saveOrUpdateObject(lane);
		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);
		Path path = new Path();

		path.setLaneList(laneList);
		return path;
	}

	public static Path initPathOccupiedAndFreeLanes() {
		Lane lane = initOccupiedLane();
		Lane lane2 = initFreeLane();
		HibernateGeneric.saveOrUpdateObject(lane);
		HibernateGeneric.saveOrUpdateObject(lane2);
		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);
		laneList.add(lane2);
		Path path = new Path();

		path.setLaneList(laneList);
		return path;
	}

	public static Lane initFreeLane() {
		Lane lane = initCompleteLane();
		lane.setStatus(FREE);
		return lane;

	}

	public static Lane initOccupiedLane() {
		Lane lane = initCompleteLane();
		lane.setStatus(OCCUPIED);
		return lane;

	}

	public static Terminal initTerminal() {
		Terminal terminal = new Terminal();
		terminal.setName(TERMINAL_NAME);
		return terminal;

	}

	public static Terminal initTerminal(Airport airport) {
		Terminal terminal = initTerminal();
		terminal.setAirport(airport);
		return terminal;

	}

	public static Route initCompleteRoute() {

		Address address = initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport airport = initAirport(address);
		HibernateGeneric.saveOrUpdateObject(airport);

		Terminal terminal = initTerminal(airport);
		HibernateGeneric.saveOrUpdateObject(terminal);

		Gate gate = initGate(terminal);
		HibernateGeneric.saveOrUpdateObject(gate);

		return initRoute(gate, gate);
	}

	public static Route initRoute(Gate arrivalGate, Gate departureGate) {
		Route route = new Route();
		route.setArrivalGate(arrivalGate);
		route.setDepartureGate(departureGate);
		return route;
	}

	public static PlaneStatus initPlaneStatus() {
		PlaneStatus planeStatus = new PlaneStatus();
		planeStatus.setTechnicalStatus(TECHNICAL_STATUS);
		planeStatus.setPositionStatus(POSITION_STATUS);
		return planeStatus;
	}

	public static PlaneMovement initCompletePlaneMovements() {

		Airline airline = initAirline();
		DAOUser.deleteUserWithUsername(airline);
		HibernateGeneric.saveOrUpdateObject(airline);

		PlaneMaker planeMaker = initPlaneMaker();
		HibernateGeneric.saveOrUpdateObject(planeMaker);

		PlaneModel planeModel = initPlaneModel(planeMaker);
		HibernateGeneric.saveOrUpdateObject(planeModel);

		PlaneStatus planeStatus = initPlaneStatus();
		HibernateGeneric.saveOrUpdateObject(planeStatus);

		Plane plane = initPlane(airline, planeModel, new Date(), planeStatus);
		HibernateGeneric.saveOrUpdateObject(plane);

		PlaneMovement planeMovement = initPlaneMovement(plane);
		return planeMovement;
	}

	public static PlaneMovement initPlaneMovement() {
		PlaneMovement planeMovement = new PlaneMovement();
		planeMovement.setDirectionX(DIRECTION);
		planeMovement.setDirectionY(DIRECTION);
		planeMovement.setPositionX(POSITION);
		planeMovement.setPositionY(POSITION);
		planeMovement.setSpeed(SPEED);
		return planeMovement;

	}

	public static PlaneMovement initPlaneMovement(Plane plane) {
		PlaneMovement planeMovement = initPlaneMovement();
		planeMovement.setPlane(plane);
		return planeMovement;

	}

	public static PlaneModel initCompletePlaneModel() {

		PlaneMaker planeMaker = initPlaneMaker();
		HibernateGeneric.saveOrUpdateObject(planeMaker);

		PlaneModel planeModel = initPlaneModel(planeMaker);
		return planeModel;
	}

	public static PlaneModel initPlaneModel() {
		PlaneModel planeModel = new PlaneModel();
		planeModel.setName(SERIAL);
		return planeModel;
	}

	public static PlaneModel initPlaneModel(PlaneMaker planeMaker) {
		PlaneModel planeModel = initPlaneModel();
		planeModel.setPlaneMaker(planeMaker);
		return planeModel;
	}

	public static PlaneMaker initPlaneMaker() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		return planeMaker;
	}

	public static Plane initCompletePlane() {

		Airline airline = initAirline();
		DAOUser.deleteUserWithUsername(airline);
		HibernateGeneric.saveOrUpdateObject(airline);

		PlaneMaker planeMaker = initPlaneMaker();
		HibernateGeneric.saveOrUpdateObject(planeMaker);

		PlaneModel planeModel = initPlaneModel(planeMaker);
		HibernateGeneric.saveOrUpdateObject(planeModel);

		PlaneStatus planeStatus = initPlaneStatus();
		HibernateGeneric.saveOrUpdateObject(planeStatus);

		return initPlane(airline, planeModel, new Date(), planeStatus);
	}

	public static Plane initPlane() {
		Plane plane = new Plane();
		plane.setSerial(SERIAL);
		return plane;

	}

	public static Plane initPlane(Airline airline, PlaneModel planeModel, Date date, PlaneStatus planeStatus) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setModel(planeModel);
		plane.setFabricationDate(date);
		plane.setPlaneStatus(planeStatus);

		return plane;

	}

	public static Plane initPlane(Airline airline, Date date, PlaneStatus planeStatus) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setFabricationDate(date);
		plane.setPlaneStatus(planeStatus);

		return plane;

	}

	public static Plane initPlane(PlaneModel planeModel, Date date, PlaneStatus planeStatus) {
		Plane plane = initPlane();
		plane.setModel(planeModel);
		plane.setFabricationDate(date);
		plane.setPlaneStatus(planeStatus);

		return plane;

	}

	public static Plane initPlane(Airline airline, PlaneModel planeModel, PlaneStatus planeStatus) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setModel(planeModel);
		plane.setPlaneStatus(planeStatus);

		return plane;
	}

	public static Plane initPlane(PlaneModel planeModel, Date date, Airline airline) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setModel(planeModel);
		plane.setFabricationDate(date);

		return plane;
	}

	public static Passenger initPassenger(String username, String password, Date date) {
		Passenger passenger = new Passenger();
		passenger.setUsername(username);
		passenger.setPassword(password);
		passenger.setBirthDate(date);
		return passenger;
	}

	public static Passenger initPassenger(Date date, String password) {
		Passenger passenger = new Passenger();
		passenger.setPassword(password);
		passenger.setBirthDate(date);
		return passenger;
	}

	public static Passenger initPassenger(String username, Date date) {
		Passenger passenger = new Passenger();
		passenger.setUsername(username);
		passenger.setBirthDate(date);
		return passenger;
	}

	public static Passenger initPassenger(String username, String password) {
		Passenger user = new Passenger();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}

	public static Passenger initCompletePassenger() {

		Address address = initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Passenger passenger = new Passenger();
		passenger.setUsername(USERNAME_2);
		passenger.setPassword(PASSWORD_2);
		passenger.setAddress(address);
		passenger.setEmail(EMAIL);
		passenger.setBirthDate(new Date());
		return passenger;
	}

	public static Address initAddress() {

		Address address = new Address();
		address.setCity(BERGARA);
		address.setCountry(EUSKAL_HERRIA);
		address.setPostCode(POSTCODE);
		address.setRegion(REGION);
		address.setStreetAndNumber(STREET_AND_NUMBER);
		return address;
	}

	public static Node initNode() {
		Node node = new Node();
		node.setName(NODE_NAME);
		node.setPositionX(POSITION_X);
		node.setPositionY(POSITION_Y);
		return node;
	}

	public static Lane initCompleteLane() {
		Node startNode = initNode();
		HibernateGeneric.saveOrUpdateObject(startNode);

		Node endNode = initNode();
		HibernateGeneric.saveOrUpdateObject(endNode);

		Address address = initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport airport = initAirport(address);
		HibernateGeneric.saveOrUpdateObject(airport);

		return initLane(startNode, endNode, PRINCIPAL, true, airport);
	}

	public static Lane initLane() {
		Lane lane = new Lane();
		lane.setName(LANE_NAME);
		return lane;

	}

	public static Lane initLane(Node node1, Node node2, String type, boolean status, Airport airport) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setType(type);
		lane.setStatus(status);
		lane.setAirport(airport);
		return lane;

	}

	public static Lane initLane(Node node1, Node node2, String type, Airport airport) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setAirport(airport);
		lane.setType(type);

		return lane;

	}

	public static Lane initLane(Node node1, Node node2, Airport airport, boolean status) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setAirport(airport);
		lane.setStatus(status);

		return lane;

	}

	public static Lane initLane(Node node1, Node node2, boolean status, String type) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setStatus(status);
		lane.setType(type);

		return lane;
	}

	public static Lane initLane(boolean status, String type, Airport airport) {
		Lane lane = initLane();
		lane.setStatus(status);
		lane.setType(type);
		lane.setAirport(airport);

		return lane;
	}

	public static Gate initGate(Node node) {
		Gate gate = initGate();
		gate.setPositionNode(node);
		return gate;

	}

	public static Gate initGate(Node node, Terminal terminal) {
		Gate gate = initGate();
		gate.setPositionNode(node);
		gate.setTerminal(terminal);
		return gate;

	}

	public static Gate initGate(Terminal terminal) {
		Gate gate = initGate();
		gate.setTerminal(terminal);
		return gate;

	}

	public static Gate initGate() {
		Gate gate = new Gate();
		gate.setNumber(GATE_NUM);
		return gate;
	}

	public static Airline initAirline() {
		Address address = initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airline airline = new Airline();
		airline.setName(NARANAIR);
		airline.setUsername(USERNAME);
		airline.setPassword(PASSWORD);
		airline.setAddress(address);
		return airline;

	}

	public static Airport initCompleteAirport() {

		Address address = initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		return initAirport(address);
	}

	public static Airport initAirport(Address address) {
		Airport airport = initAirport();
		airport.setAddress(address);
		return airport;
	}

	public static Airport initAirport() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		airport.setMaxFlights(MAX_FLIGHTS);
		return airport;
	}

	public static Delay initCompleteDelay() {
		PlaneMaker planeMaker = initPlaneMaker();
		HibernateGeneric.saveOrUpdateObject(planeMaker);

		PlaneModel planeModel = initPlaneModel(planeMaker);
		HibernateGeneric.saveOrUpdateObject(planeModel);

		Airline airline = initAirline();
		DAOUser.deleteUserWithUsername(airline);
		HibernateGeneric.saveOrUpdateObject(airline);

		PlaneStatus planeStatus = initPlaneStatus();
		HibernateGeneric.saveOrUpdateObject(planeStatus);

		Plane plane = initPlane(airline, planeModel, new Date(), planeStatus);
		HibernateGeneric.saveOrUpdateObject(plane);

		Passenger passenger = initCompletePassenger();
		DAOUser.deleteUserWithUsername(passenger);
		HibernateGeneric.saveOrUpdateObject(passenger);

		List<Passenger> passengerList = new ArrayList<>();
		passengerList.add(passenger);

		Node node = initNode();
		HibernateGeneric.saveOrUpdateObject(node);

		Address address = initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport airport = initAirport(address);
		HibernateGeneric.saveOrUpdateObject(airport);

		Terminal terminal = initTerminal(airport);
		HibernateGeneric.saveOrUpdateObject(terminal);

		Gate gate = initGate(node, terminal);
		HibernateGeneric.saveOrUpdateObject(gate);

		Route route = initRoute(gate, gate);
		HibernateGeneric.saveOrUpdateObject(route);

		Flight flight = initFlight(plane, route, passengerList);
		HibernateGeneric.saveOrUpdateObject(flight);

		return initDelay(flight);
	}

	public static Flight initCompleteFlight() {
		PlaneMaker planeMaker = initPlaneMaker();
		HibernateGeneric.saveOrUpdateObject(planeMaker);

		PlaneModel planeModel = initPlaneModel(planeMaker);
		HibernateGeneric.saveOrUpdateObject(planeModel);

		Airline airline = initAirline();
		DAOUser.deleteUserWithUsername(airline);
		HibernateGeneric.saveOrUpdateObject(airline);

		PlaneStatus planeStatus = initPlaneStatus();
		HibernateGeneric.saveOrUpdateObject(planeStatus);

		Plane plane = initPlane(airline, planeModel, new Date(), planeStatus);
		HibernateGeneric.saveOrUpdateObject(plane);

		Passenger passenger = initCompletePassenger();
		DAOUser.deleteUserWithUsername(passenger);
		HibernateGeneric.saveOrUpdateObject(passenger);

		List<Passenger> passengerList = new ArrayList<>();
		passengerList.add(passenger);

		Node node = initNode();
		HibernateGeneric.saveOrUpdateObject(node);

		Address address = initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport airport = initAirport(address);
		HibernateGeneric.saveOrUpdateObject(airport);

		Terminal terminal = initTerminal(airport);
		HibernateGeneric.saveOrUpdateObject(terminal);

		Gate gate = initGate(node, terminal);
		HibernateGeneric.saveOrUpdateObject(gate);

		Route route = initRoute(gate, gate);

		HibernateGeneric.saveOrUpdateObject(route);

		return initFlight(plane, route, passengerList);
	}

	public static Flight initFlight(Plane plane, Route route) {
		Flight flight = initFlight(plane);
		flight.setRoute(route);
		return flight;

	}

	public static Flight initFlight(Plane plane, Route route, List<Passenger> passengerList) {
		Flight flight = initFlight(plane);
		flight.setRoute(route);
		flight.setPassengerList(passengerList);
		return flight;

	}

	public static Flight initFlight(Plane plane, List<Passenger> passengerList) {
		Flight flight = initFlight(plane);
		flight.setPassengerList(passengerList);
		return flight;

	}

	public static Flight initFlight(Plane plane) {
		Flight flight = new Flight();
		flight.setExpectedArrivalDate(new Date());
		flight.setExpectedDepartureDate(new Date());
		flight.setPlane(plane);
		flight.setRealArrivalDate(new Date());
		flight.setRealDepartureDate(new Date());

		return flight;

	}

	public static Delay initDelay(Flight flight) {
		Delay delay = new Delay();
		delay.setAffectedFlight(flight);
		return delay;

	}
	
	public static Airport initializeExampleOnDB() {

		Address address = Initializer.initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport myAirport = new Airport();
		myAirport.setMaxFlights(MAX_ACTIVE_PLANES);
		myAirport.setName(AIRPORT_NAME);
		myAirport.setAddress(address);
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
		lane.setType("PRINCIPAL");
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
