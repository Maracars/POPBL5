package initialization;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import domain.dao.HibernateGeneric;
import domain.model.Address;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Node;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.PlaneMovement;
import domain.model.PlaneStatus;
import domain.model.Route;
import domain.model.Terminal;
import domain.model.users.Admin;
import domain.model.users.Airline;
import domain.model.users.Controller;
import domain.model.users.Mantainance;
import domain.model.users.Passenger;

/**
 * The Class DemoInitialization.
 * It contains inserts to the database that will execute the first time.
 */
public class DemoInitialization implements ServletContextListener {

	/** The Constant RANDOM_MARGIN. */
	private static final int RANDOM_MARGIN = 1000 - 1;
	
	/** The Constant RANDOM_INT. */
	private static final int RANDOM_INT = 5;
	
	/** The Constant SPEED. */
	private static final double SPEED = 0.0;
	
	/** The Constant POSITION_Y. */
	private static final double POSITION_Y = 0.0;
	
	/** The Constant POSITION_X. */
	private static final double POSITION_X = 0.0;
	
	/** The Constant DIRECTION_Y. */
	private static final double DIRECTION_Y = 0.0;
	
	/** The Constant DIRECTION_X. */
	private static final double DIRECTION_X = 0.0;
	
	/** The Constant BARAJAS_TERMINAL_LONGITUDE. */
	private static final double BARAJAS_TERMINAL_LONGITUDE = -3.5936845999999605;
	
	/** The Constant BARAJAS_TERMINAL_LATITUDE. */
	private static final double BARAJAS_TERMINAL_LATITUDE = 40.4913889;
	
	/** The Constant BARAJAS_TERMINAL_NAME. */
	private static final String BARAJAS_TERMINAL_NAME = "Terminal 4";
	
	/** The Constant BILBAO_TERMINAL_LONGITUDE. */
	private static final double BILBAO_TERMINAL_LONGITUDE = -2.9052159190177917;
	
	/** The Constant BILBAO_TERMINAL_LATITUDE. */
	private static final double BILBAO_TERMINAL_LATITUDE = 43.30463946350521;
	
	/** The Constant BILBAO_TERMINAL_NAME. */
	private static final String BILBAO_TERMINAL_NAME = "Terminal";
	
	/** The Constant TEGEL_TERMINAL_LONGITUDE. */
	private static final double TEGEL_TERMINAL_LONGITUDE = 13.29126980000001;
	
	/** The Constant TEGEL_TERMINAL_LATITUDE. */
	private static final double TEGEL_TERMINAL_LATITUDE = 52.5537933;
	
	/** The Constant TEGEL_TERMINAL_NAME. */
	private static final String TEGEL_TERMINAL_NAME = "Terminal A";
	
	/** The Constant TECHNICAL_STATUS. */
	private static final String TECHNICAL_STATUS = "OK";
	
	/** The Constant POSITION_STATUS. */
	private static final String POSITION_STATUS = "ON AIRPORT";
	
	/** The Constant SERIAL_LENGTH. */
	private static final int SERIAL_LENGTH = 5;
	
	/** The Constant PLANE_MODEL_DO_17. */
	private static final String PLANE_MODEL_DO_17 = "Do 17";
	
	/** The Constant PLANE_MODEL_DO_X. */
	private static final String PLANE_MODEL_DO_X = "Do X";
	
	/** The Constant PLANE_MODEL_DO_J. */
	private static final String PLANE_MODEL_DO_J = "Do J";
	
	/** The Constant PLANE_MAKER_DORNIER. */
	private static final String PLANE_MAKER_DORNIER = "Dornier";
	
	/** The Constant PLANE_MODEL_737. */
	private static final String PLANE_MODEL_737 = "737";
	
	/** The Constant PLANE_MODEL_717. */
	private static final String PLANE_MODEL_717 = "717";
	
	/** The Constant PLANE_MODEL_707. */
	private static final String PLANE_MODEL_707 = "707";
	
	/** The Constant PLANE_MAKER_BOEING. */
	private static final String PLANE_MAKER_BOEING = "Boeing";
	
	/** The Constant PLANE_MODEL_F_13. */
	private static final String PLANE_MODEL_F_13 = "F 13";
	
	/** The Constant PLANE_MODEL_EF_132. */
	private static final String PLANE_MODEL_EF_132 = "EF 132";
	
	/** The Constant PLANE_MODEL_EF_128. */
	private static final String PLANE_MODEL_EF_128 = "EF 128";
	
	/** The Constant PLANE_MAKER_JUNKERS. */
	private static final String PLANE_MAKER_JUNKERS = "Junkers";
	
	/** The Constant PLANE_MODEL_A310_MRTT. */
	private static final String PLANE_MODEL_A310_MRTT = "A310 MRTT";
	
	/** The Constant PLANE_MODEL_A310. */
	private static final String PLANE_MODEL_A310 = "A310";
	
	/** The Constant PLANE_MODEL_A300. */
	private static final String PLANE_MODEL_A300 = "A300";
	
	/** The Constant PLANE_MAKER_AIRBUS. */
	private static final String PLANE_MAKER_AIRBUS = "Airbus";
	
	/** The Constant PASSENGER_USER_PASS. */
	private static final String PASSENGER_USER_PASS = "joseba";
	
	/** The Constant PASSENGER_SECOND_NAME. */
	private static final String PASSENGER_SECOND_NAME = "Gorospe";
	
	/** The Constant PASSENGER_NAME. */
	private static final String PASSENGER_NAME = "Joseba";
	
	/** The Constant PASSENGER_EMAIL. */
	private static final String PASSENGER_EMAIL = "joseba.gorospe@alumni.mondragon.edu";
	
	/** The Constant CONTROLLER_USER_PASS. */
	private static final String CONTROLLER_USER_PASS = "joanes";
	
	/** The Constant CONTROLLER_SECOND_NAME. */
	private static final String CONTROLLER_SECOND_NAME = "Plazaola";
	
	/** The Constant CONTROLLER_NAME. */
	private static final String CONTROLLER_NAME = "Joanes";
	
	/** The Constant CONTROLLER_EMAIL. */
	private static final String CONTROLLER_EMAIL = "joanes.plazaola@alumni.mondragon.edu";
	
	/** The Constant USER_ADDRESS_STREETANDNUMBER. */
	private static final String USER_ADDRESS_STREETANDNUMBER = "Goiru Kalea";
	
	/** The Constant USER_ADDRESS_POSTCODE. */
	private static final String USER_ADDRESS_POSTCODE = "20500";
	
	/** The Constant USER_ADDRESS_CITY. */
	private static final String USER_ADDRESS_CITY = "Arrasate";
	
	/** The Constant USER_ADDRESS_REGION. */
	private static final String USER_ADDRESS_REGION = "Gipuzkoa";
	
	/** The Constant USER_ADDRESS_COUNTRY. */
	private static final String USER_ADDRESS_COUNTRY = "Spain";
	
	/** The Constant AIRLINE_USER_PASS. */
	private static final String AIRLINE_USER_PASS = "ander";
	
	/** The Constant AIRLINE_NAME. */
	private static final String AIRLINE_NAME = "Ander";
	
	/** The Constant AIRLINE_EMAIL. */
	private static final String AIRLINE_EMAIL = "ander.gonzalez@alumni.mondragon.edu";
	
	/** The Constant MANTAINANCE_USER_PASS. */
	private static final String MANTAINANCE_USER_PASS = "mikel";
	
	/** The Constant MANTAINANCE_SECOND_NAME. */
	private static final String MANTAINANCE_SECOND_NAME = "Amutxastegi";
	
	/** The Constant MANTAINANCE_NAME. */
	private static final String MANTAINANCE_NAME = "Mikel";
	
	/** The Constant MANTAINANCE_EMAIL. */
	private static final String MANTAINANCE_EMAIL = "mikel.amuchastegui@alumni.mondragon.edu";
	
	/** The Constant BARAJAS_AIRPORT_MAXFLIGHTS. */
	private static final int BARAJAS_AIRPORT_MAXFLIGHTS = 0;
	
	/** The Constant BARAJAS_AIRPORT_LONGITUDE. */
	private static final double BARAJAS_AIRPORT_LONGITUDE = -3.5679514999999355;
	
	/** The Constant BARAJAS_AIRPORT_LATITUDE. */
	private static final double BARAJAS_AIRPORT_LATITUDE = 40.4839361;
	
	/** The Constant BARAJAS_AIRPORT_NODE_NAME. */
	private static final String BARAJAS_AIRPORT_NODE_NAME = "Adolfo Suárez Madrid Barajas";
	
	/** The Constant BARAJAS_AIRPORT_STREETANDNUMBER. */
	private static final String BARAJAS_AIRPORT_STREETANDNUMBER = "Aeropuerto Adolfo Suárez Madrid Barajas";
	
	/** The Constant BARAJAS_AIRPORT_POSTCODE. */
	private static final String BARAJAS_AIRPORT_POSTCODE = "28042";
	
	/** The Constant BARAJAS_AIRPORT_REGION. */
	private static final String BARAJAS_AIRPORT_REGION = "Madrid";
	
	/** The Constant LOIU_AIRPORT_MAX_FLIGHTS. */
	private static final int LOIU_AIRPORT_MAX_FLIGHTS = 0;
	
	/** The Constant LOIU_AIRPORT_LONGITUDE. */
	private static final double LOIU_AIRPORT_LONGITUDE = -2.9111159999999927;
	
	/** The Constant LOIU_AIRPORT_LATITUDE. */
	private static final double LOIU_AIRPORT_LATITUDE = 43.302494;
	
	/** The Constant LOIU_AIRPORT_NODE_NAME. */
	private static final String LOIU_AIRPORT_NODE_NAME = "Loiu - Bilbao";
	
	/** The Constant LOIU_AIRPORT_STREETANDNUMBER. */
	private static final String LOIU_AIRPORT_STREETANDNUMBER = "Aeropuerto de Loiu";
	
	/** The Constant LOIU_AIRPORT_POSTCODE. */
	private static final String LOIU_AIRPORT_POSTCODE = "48180";
	
	/** The Constant LOIU_AIRPORT_CITY. */
	private static final String LOIU_AIRPORT_CITY = "Bilbao";
	
	/** The Constant LOIU_AIRPORT_REGION. */
	private static final String LOIU_AIRPORT_REGION = "Biscay";
	
	/** The Constant LOIU_AIRPORT_COUNTRY. */
	private static final String LOIU_AIRPORT_COUNTRY = USER_ADDRESS_COUNTRY;
	
	/** The Constant TEGEL_AIRPORT_MAX_FLIGHTS. */
	private static final int TEGEL_AIRPORT_MAX_FLIGHTS = 0;
	
	/** The Constant TEGEL_AIRPORT_LONGITUDE. */
	private static final double TEGEL_AIRPORT_LONGITUDE = 13.270907699999952;
	
	/** The Constant TEGEL_AIRPORT_LATITUDE. */
	private static final double TEGEL_AIRPORT_LATITUDE = 52.5910076;
	
	/** The Constant TEGEL_AIRPORT_NODE_NAME. */
	private static final String TEGEL_AIRPORT_NODE_NAME = "Tegel - Berlin";
	
	/** The Constant TEGEL_AIRPORT_STREETANDNUMBER. */
	private static final String TEGEL_AIRPORT_STREETANDNUMBER = "Tegel airport Berlin";
	
	/** The Constant TEGEL_AIRPORT_POSTCODE. */
	private static final String TEGEL_AIRPORT_POSTCODE = "TXL";
	
	/** The Constant TEGEL_AIRPORT_CITY. */
	private static final String TEGEL_AIRPORT_CITY = "Tegel";
	
	/** The Constant TEGEL_AIRPORT_REGION. */
	private static final String TEGEL_AIRPORT_REGION = "Berlin";
	
	/** The Constant TEGEL_AIRPORT_COUNTRY. */
	private static final String TEGEL_AIRPORT_COUNTRY = "Germany";

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		List<Object> users = HibernateGeneric.loadAllObjects(new Admin());
		if (users.isEmpty()) {
			// initHeathrowAirport();
			Address address = initUsersAddress();
			initController(address);
			initPassenger(address);
			initMaitenance(address);
			Terminal madridTerminal = initMadridAirport();
			Terminal bilbaoTerminal = initBilbaoAirport();
			Terminal tegelTerminal = initTegelAirport();
			Airline airline = initAirline(address);
			initPlaneMakersAndModels();
			initRoutes(madridTerminal, bilbaoTerminal, tegelTerminal);
			PlaneStatus planeStatus = initPlaneStatus();
			PlaneMovement planeMovement = initPlaneMovement();
			initPlanes(airline, planeStatus, planeMovement);
			// initFlights();
		}

	}

	/**
	 * Inits the flights.
	 */
	private void initFlights() {
		Random random = new Random();
		List<Object> routesList = HibernateGeneric.loadAllObjects(new Route());
		List<Object> planesList = HibernateGeneric.loadAllObjects(new Plane());

		for (int i = 0; i < 20; i++) {
			int routePos = random.nextInt(routesList.size());
			int planePos = random.nextInt(planesList.size());

			int departureHour = random.nextInt(RANDOM_INT);
			int arrivalHour = random.nextInt(RANDOM_INT);

			double price = Math.random() * RANDOM_MARGIN;

			Date date = new Date();

			Flight flight = new Flight();
			flight.setPrice((float) price);
			flight.setRoute((Route) routesList.get(routePos));
			flight.setPlane((Plane) planesList.get(planePos));

			if (i % 2 == 0) {
				flight.setExpectedDepartureDate(new Date(date.getTime() + departureHour));
				flight.setExpectedArrivalDate(new Date(date.getTime() + arrivalHour));

			} else {
				flight.setExpectedDepartureDate(new Date(date.getTime() - departureHour));
				flight.setExpectedArrivalDate(new Date(date.getTime() - arrivalHour));
			}
			HibernateGeneric.saveObject(flight);
		}
	}

	/**
	 * Inits the plane movement.
	 *
	 * @return the plane movement
	 */
	private PlaneMovement initPlaneMovement() {
		PlaneMovement planeMovement = new PlaneMovement();
		planeMovement.setDirectionX(DIRECTION_X);
		planeMovement.setDirectionY(DIRECTION_Y);
		planeMovement.setPositionX(POSITION_X);
		planeMovement.setPositionY(POSITION_Y);
		planeMovement.setSpeed(SPEED);
		return planeMovement;
	}

	/**
	 * Inits the plane status.
	 *
	 * @return the plane status
	 */
	private PlaneStatus initPlaneStatus() {
		PlaneStatus planeStatus = new PlaneStatus();
		planeStatus.setPositionStatus(POSITION_STATUS);
		planeStatus.setTechnicalStatus(TECHNICAL_STATUS);
		HibernateGeneric.saveObject(planeStatus);
		return planeStatus;
	}

	/**
	 * Inits the planes.
	 *
	 * @param airline the airline
	 * @param planeStatus the plane status
	 * @param planeMovement the plane movement
	 */
	private void initPlanes(Airline airline, PlaneStatus planeStatus, PlaneMovement planeMovement) {
		List<Object> planeModelList = HibernateGeneric.loadAllObjects(new PlaneModel());
		Random random = new Random();

		for (int i = 0; i < 10; i++) {
			int planeModelPos = random.nextInt(planeModelList.size());
			Plane plane = new Plane();
			plane.setSerial(createSerial());
			plane.setAirline(airline);
			plane.setModel((PlaneModel) planeModelList.get(planeModelPos));
			plane.setPlaneStatus(planeStatus);
			plane.setFabricationDate(new Date());
			plane.setPlaneMovement(planeMovement);
			HibernateGeneric.saveObject(plane);
		}

	}

	/**
	 * Creates the serial.
	 *
	 * @return the string
	 */
	private String createSerial() {
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String serial = "";
		for (int i = 0; i < SERIAL_LENGTH; i++) {
			int numRandom = (int) Math.round(Math.random() * (letters.length - 1));
			serial = serial + letters[numRandom];
		}

		return serial;
	}

	/**
	 * Inits the routes.
	 *
	 * @param madridTerminal the madrid terminal
	 * @param bilbaoTerminal the bilbao terminal
	 * @param tegelTerminal the tegel terminal
	 */
	private void initRoutes(Terminal madridTerminal, Terminal bilbaoTerminal, Terminal tegelTerminal) {
		List<Object> heathrowTerminals = HibernateGeneric.loadAllObjects(new Terminal());
		Random random = new Random();
		int terminalPos = random.nextInt(heathrowTerminals.size());

		Route route = new Route();
		route.setDepartureTerminal((Terminal) heathrowTerminals.get(0));
		route.setArrivalTerminal(madridTerminal);
		HibernateGeneric.saveObject(route);

		terminalPos = random.nextInt(heathrowTerminals.size());
		route = new Route();
		route.setDepartureTerminal(bilbaoTerminal);

		route.setArrivalTerminal((Terminal) heathrowTerminals.get(0));
		HibernateGeneric.saveObject(route);

		terminalPos = random.nextInt(heathrowTerminals.size());
		route = new Route();

		route.setDepartureTerminal((Terminal) heathrowTerminals.get(terminalPos));
		route.setArrivalTerminal(tegelTerminal);
		HibernateGeneric.saveObject(route);
	}

	/**
	 * Inits the plane makers and models.
	 */
	private void initPlaneMakersAndModels() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(PLANE_MAKER_AIRBUS);
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_A300);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_A310);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_A310_MRTT);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeMaker = new PlaneMaker();
		planeMaker.setName(PLANE_MAKER_JUNKERS);
		HibernateGeneric.saveObject(planeMaker);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_EF_128);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_EF_132);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_F_13);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeMaker = new PlaneMaker();
		planeMaker.setName(PLANE_MAKER_BOEING);
		HibernateGeneric.saveObject(planeMaker);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_707);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_717);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_737);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeMaker = new PlaneMaker();
		planeMaker.setName(PLANE_MAKER_DORNIER);
		HibernateGeneric.saveObject(planeMaker);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_DO_J);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_DO_X);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		planeModel = new PlaneModel();
		planeModel.setName(PLANE_MODEL_DO_17);
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.saveObject(planeModel);

	}

	/**
	 * Inits the maitenance.
	 *
	 * @param address the address
	 */
	private void initMaitenance(Address address) {
		Mantainance mantainance = new Mantainance();
		mantainance.setAddress(address);
		mantainance.setEmail(MANTAINANCE_EMAIL);
		mantainance.setName(MANTAINANCE_NAME);
		mantainance.setSecondName(MANTAINANCE_SECOND_NAME);
		mantainance.setUsername(MANTAINANCE_USER_PASS);
		mantainance.setPassword(MANTAINANCE_USER_PASS);
		mantainance.setBirthDate(new Date());
		HibernateGeneric.saveObject(mantainance);

	}

	/**
	 * Inits the airline.
	 *
	 * @param address the address
	 * @return the airline
	 */
	private Airline initAirline(Address address) {
		Airline airline = new Airline();
		airline.setAddress(address);
		airline.setEmail(AIRLINE_EMAIL);
		airline.setName(AIRLINE_NAME);
		airline.setUsername(AIRLINE_USER_PASS);
		airline.setPassword(AIRLINE_USER_PASS);
		HibernateGeneric.saveObject(airline);

		return airline;

	}

	/**
	 * Inits the users address.
	 *
	 * @return the address
	 */
	private Address initUsersAddress() {
		Address address = new Address();
		address.setCountry(USER_ADDRESS_COUNTRY);
		address.setRegion(USER_ADDRESS_REGION);
		address.setCity(USER_ADDRESS_CITY);
		address.setPostCode(USER_ADDRESS_POSTCODE);
		address.setStreetAndNumber(USER_ADDRESS_STREETANDNUMBER);
		HibernateGeneric.saveObject(address);
		return address;
	}

	/**
	 * Inits the controller.
	 *
	 * @param address the address
	 */
	private void initController(Address address) {
		Controller controller = new Controller();
		controller.setAddress(address);
		controller.setEmail(CONTROLLER_EMAIL);
		controller.setName(CONTROLLER_NAME);
		controller.setSecondName(CONTROLLER_SECOND_NAME);
		controller.setUsername(CONTROLLER_USER_PASS);
		controller.setPassword(CONTROLLER_USER_PASS);
		controller.setBirthDate(new Date());
		HibernateGeneric.saveObject(controller);

	}

	/**
	 * Inits the passenger.
	 *
	 * @param address the address
	 */
	private void initPassenger(Address address) {
		Passenger passenger = new Passenger();
		passenger.setAddress(address);
		passenger.setEmail(PASSENGER_EMAIL);
		passenger.setName(PASSENGER_NAME);
		passenger.setSecondName(PASSENGER_SECOND_NAME);
		passenger.setUsername(PASSENGER_USER_PASS);
		passenger.setPassword(PASSENGER_USER_PASS);
		passenger.setBirthDate(new Date());
		HibernateGeneric.saveObject(passenger);

	}

	/**
	 * Inits the tegel airport.
	 *
	 * @return the terminal
	 */
	private Terminal initTegelAirport() {
		Address address = new Address();
		address.setCountry(TEGEL_AIRPORT_COUNTRY);
		address.setRegion(TEGEL_AIRPORT_REGION);
		address.setCity(TEGEL_AIRPORT_CITY);
		address.setPostCode(TEGEL_AIRPORT_POSTCODE);
		address.setStreetAndNumber(TEGEL_AIRPORT_STREETANDNUMBER);
		HibernateGeneric.saveObject(address);

		Node positionNode = new Node();
		positionNode.setName(TEGEL_AIRPORT_NODE_NAME);
		positionNode.setPositionX(TEGEL_AIRPORT_LATITUDE);
		positionNode.setPositionY(TEGEL_AIRPORT_LONGITUDE);
		HibernateGeneric.saveObject(positionNode);

		Airport airport = new Airport();
		airport.setAddress(address);
		airport.setLocale(false);
		airport.setMaxFlights(TEGEL_AIRPORT_MAX_FLIGHTS);
		airport.setName(TEGEL_AIRPORT_NODE_NAME);
		airport.setPositionNode(positionNode);
		HibernateGeneric.saveObject(airport);

		Terminal terminal = new Terminal();
		terminal.setAirport(airport);
		terminal.setName(TEGEL_TERMINAL_NAME);
		positionNode = new Node();
		positionNode.setPositionX(TEGEL_TERMINAL_LATITUDE);
		positionNode.setPositionY(TEGEL_TERMINAL_LONGITUDE);
		terminal.setPositionNode(positionNode);
		HibernateGeneric.saveObject(positionNode);
		HibernateGeneric.saveObject(terminal);

		return terminal;

	}

	/**
	 * Inits the bilbao airport.
	 *
	 * @return the terminal
	 */
	private Terminal initBilbaoAirport() {
		Address address = new Address();
		address.setCountry(LOIU_AIRPORT_COUNTRY);
		address.setRegion(LOIU_AIRPORT_REGION);
		address.setCity(LOIU_AIRPORT_CITY);
		address.setPostCode(LOIU_AIRPORT_POSTCODE);
		address.setStreetAndNumber(LOIU_AIRPORT_STREETANDNUMBER);
		HibernateGeneric.saveObject(address);

		Node positionNode = new Node();
		positionNode.setName(LOIU_AIRPORT_NODE_NAME);
		positionNode.setPositionX(LOIU_AIRPORT_LATITUDE);
		positionNode.setPositionY(LOIU_AIRPORT_LONGITUDE);
		HibernateGeneric.saveObject(positionNode);

		Airport airport = new Airport();
		airport.setAddress(address);
		airport.setLocale(false);
		airport.setMaxFlights(LOIU_AIRPORT_MAX_FLIGHTS);
		airport.setName(LOIU_AIRPORT_NODE_NAME);
		airport.setPositionNode(positionNode);
		HibernateGeneric.saveObject(airport);

		Terminal terminal = new Terminal();
		terminal.setAirport(airport);
		terminal.setName(BILBAO_TERMINAL_NAME);
		positionNode = new Node();
		positionNode.setPositionX(BILBAO_TERMINAL_LATITUDE);
		positionNode.setPositionY(BILBAO_TERMINAL_LONGITUDE);
		terminal.setPositionNode(positionNode);
		HibernateGeneric.saveObject(positionNode);
		HibernateGeneric.saveObject(terminal);

		return terminal;

	}

	/**
	 * Inits the madrid airport.
	 *
	 * @return the terminal
	 */
	private Terminal initMadridAirport() {
		Address address = new Address();
		address.setCountry(LOIU_AIRPORT_COUNTRY);
		address.setRegion(BARAJAS_AIRPORT_REGION);
		address.setCity(BARAJAS_AIRPORT_REGION);
		address.setPostCode(BARAJAS_AIRPORT_POSTCODE);
		address.setStreetAndNumber(BARAJAS_AIRPORT_STREETANDNUMBER);
		HibernateGeneric.saveObject(address);

		Node positionNode = new Node();
		positionNode.setName(BARAJAS_AIRPORT_NODE_NAME);
		positionNode.setPositionX(BARAJAS_AIRPORT_LATITUDE);
		positionNode.setPositionY(BARAJAS_AIRPORT_LONGITUDE);
		HibernateGeneric.saveObject(positionNode);

		Airport airport = new Airport();
		airport.setAddress(address);
		airport.setLocale(false);
		airport.setMaxFlights(BARAJAS_AIRPORT_MAXFLIGHTS);
		airport.setName(BARAJAS_AIRPORT_NODE_NAME);
		airport.setPositionNode(positionNode);
		HibernateGeneric.saveObject(airport);

		Terminal terminal = new Terminal();
		terminal.setAirport(airport);
		terminal.setName(BARAJAS_TERMINAL_NAME);
		positionNode = new Node();
		positionNode.setPositionX(BARAJAS_TERMINAL_LATITUDE);
		positionNode.setPositionY(BARAJAS_TERMINAL_LONGITUDE);
		terminal.setPositionNode(positionNode);
		HibernateGeneric.saveObject(positionNode);
		HibernateGeneric.saveObject(terminal);

		return terminal;

	}

}
