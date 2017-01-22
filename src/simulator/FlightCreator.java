package simulator;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOPlane;
import domain.dao.DAOPlaneModel;
import domain.dao.DAORoute;
import domain.dao.DAOSimulator;
import domain.dao.DAOUser;
import domain.dao.HibernateGeneric;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.PlaneMovement;
import domain.model.PlaneStatus;
import domain.model.Route;
import domain.model.users.Admin;
import domain.model.users.Airline;
import helpers.MD5;
import notification.Notification;

/**
 * The Class FlightCreator.
 */
public class FlightCreator implements Runnable {

	/** The Constant INITIALIZER_0. */
	private static final double INITIALIZER_0 = 0.0;

	/** The Constant ADMIN. */
	private static final String ADMIN = new Admin().getClass().getSimpleName();

	/** The Constant SLEEP_5_MINUTES_IN_MILIS. */
	private static final int SLEEP_2_MINUTES_IN_MILIS = 2 * 60 * 1000;

	/** The Constant POSITION_STATUS_WAITING_TO_ARRIVE. */
	private static final String POSITION_STATUS_WAITING_TO_ARRIVE = "WAITING TO ARRIVE";

	/** The Constant DAYS_IN_WEEK. */
	private static final int DAYS_IN_WEEK = 7;

	/** The Constant HOURS_IN_DAY. */
	private static final int HOURS_IN_DAY = 24;

	/** The Constant TECHNICAL_STATUS. */
	private static final String TECHNICAL_STATUS = "OK";

	/** The Constant POSITION_STATUS_ARRIVING. */
	private static final String POSITION_STATUS_ARRIVING = "ARRIVING";

	/** The Constant ARRIVAL. */
	private static final boolean ARRIVAL = false;

	/** The Constant DEPARTURE. */
	private static final boolean DEPARTURE = true;

	/** The Constant MAX_ACTIVE_PLANES. */
	private static final int MAX_ACTIVE_PLANES = 6;

	/** The Constant SERIAL_LENGTH. */
	private static final int SERIAL_LENGTH = 5;

	/** The Constant HOUR_IN_MILIS. */
	private static final int HOUR_IN_MILIS = 3600000;

	/** The Constant POSITION_STATUS_WAITING_TO_DEPARTURE. */
	private static final String POSITION_STATUS_WAITING_TO_DEPARTURE = "WAITING TO DEPARTURE";

	private static final int MAX_PRICE = 1200;

	private static final int MIN_PRICE = 100;

	/** The thread pool. */
	private static ExecutorService threadPool;

	/** The active planes num. */
	public AtomicInteger activePlanesNum = new AtomicInteger(0);

	/** The controller. */
	private AirportController controller;

	/** The airport. */
	private Airport airport;

	/**
	 * Instantiates a new flight creator.
	 *
	 * @param airport
	 *            the airport
	 * @param ac
	 *            the ac
	 */
	public FlightCreator(Airport airport, AirportController ac) {
		this.airport = airport;
		this.controller = ac;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		threadPool = Executors.newFixedThreadPool(MAX_ACTIVE_PLANES);

		while (!Thread.interrupted()) {
			programFlights();
			Notification.sendNotification(MD5.encrypt(ADMIN),
					"Schedule full, checking if any flight is arriving/departuring soon");
			createThreadsOfFlights();
			try {
				Thread.sleep(SLEEP_2_MINUTES_IN_MILIS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		threadPool.shutdownNow();
	}

	/**
	 * Program flights.
	 */
	private void programFlights() {
		Plane plane = new Plane();
		while (!checkScheduleFull(airport)) {
			Flight flight;
			Route route = selectRandomArrivalRoute();
			if ((plane = DAOPlane.getFreePlane()) == null) {
				plane = createPlane();
			}
			flight = assignRouteInSpecificTime(route, plane, ARRIVAL);

			if (flight != null) {
				Notification.sendNotification(MD5.encrypt(ADMIN), "ARRIVING flight created. " + "Plane "
						+ plane.getSerial() + " ArrivalDate:" + flight.getExpectedArrivalDate());
			}

			route = DAORoute.selectDepartureRouteFromAirport(airport.getId());
			flight = assignRouteInSpecificTime(route, plane, DEPARTURE);

			if (flight != null) {
				Notification.sendNotification(MD5.encrypt(ADMIN), "DEPARTURING flight created. " + "Plane: "
						+ plane.getSerial() + " Departure Date:" + flight.getExpectedDepartureDate());
			}

			plane.getPlaneStatus().setPositionStatus(POSITION_STATUS_ARRIVING);
			HibernateGeneric.updateObject(plane.getPlaneStatus());

		}
	}

	/**
	 * Creates the threads of flights.
	 */
	private void createThreadsOfFlights() {
		List<Flight> flightList = DAOPlane.getArrivingFlightsSoon(airport.getId());
		for (Flight flight : flightList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				Plane plane = flight.getPlane();
				activePlanesNum.incrementAndGet();
				threadPool.submit(new ArrivingPlane(plane, controller, activePlanesNum, flight));
				plane.getPlaneStatus().setPositionStatus(POSITION_STATUS_WAITING_TO_ARRIVE);
				HibernateGeneric.updateObject(plane.getPlaneStatus());
			}
		}

		flightList = DAOPlane.getDeparturingFlightsSoon(airport.getId());
		for (Flight flight : flightList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				Plane plane = flight.getPlane();
				activePlanesNum.incrementAndGet();
				threadPool.submit(new DeparturingPlane(plane, controller, activePlanesNum, flight));
				plane.getPlaneStatus().setPositionStatus(POSITION_STATUS_WAITING_TO_DEPARTURE);
				HibernateGeneric.updateObject(plane.getPlaneStatus());
			}
		}
	}

	/**
	 * Select random arrival route.
	 *
	 * @return the route
	 */
	private Route selectRandomArrivalRoute() {
		List<Route> routeList = DAORoute.getRandomArrivalRouteFromAirport(airport.getId());
		return routeList.get(0);
	}

	/**
	 * Assign route in specific time.
	 *
	 * @param route
	 *            the route
	 * @param plane
	 *            the plane
	 * @param mode
	 *            the mode
	 * @return the flight
	 */
	private Flight assignRouteInSpecificTime(Route route, Plane plane, boolean mode) {
		Flight flight = null;
		Date date = selectDate(plane);
		if (date != null) {
			flight = createFlight(route, plane, date, mode);
			HibernateGeneric.saveObject(flight);
		}
		return flight;
	}

	/**
	 * Select date.
	 *
	 * @param plane
	 *            the plane
	 * @return the date
	 */
	private Date selectDate(Plane plane) {
		Date date = null;
		date = DAOSimulator.getCorrectDateFromSchedule(plane.getId(), airport.getId());

		return date;
	}

	/**
	 * Creates the flight.
	 *
	 * @param route
	 *            the route
	 * @param plane
	 *            the plane
	 * @param date
	 *            the date
	 * @param mode
	 *            the mode
	 * @return the flight
	 */
	private Flight createFlight(Route route, Plane plane, Date date, boolean mode) {
		Flight flight = new Flight();
		if (mode == ARRIVAL) {
			flight.setExpectedArrivalDate(date);
			flight.setExpectedDepartureDate(new Date(date.getTime() - HOUR_IN_MILIS));
		} else {
			flight.setExpectedDepartureDate(date);
			flight.setExpectedArrivalDate(new Date(date.getTime() - HOUR_IN_MILIS));
		}
		flight.setRoute(route);
		flight.setPlane(plane);
		float randomPrice = new Random().nextFloat() * (MAX_PRICE - MIN_PRICE) + MIN_PRICE;
		flight.setPrice(randomPrice);
		return flight;
	}

	/**
	 * Creates the plane.
	 *
	 * @return the plane
	 */
	private Plane createPlane() {
		PlaneStatus planestatus = new PlaneStatus();
		planestatus.setPositionStatus(POSITION_STATUS_ARRIVING);
		planestatus.setTechnicalStatus(TECHNICAL_STATUS);
		HibernateGeneric.saveObject(planestatus);

		PlaneMovement planeMovement = new PlaneMovement();
		planeMovement.setDirectionX(INITIALIZER_0);
		planeMovement.setDirectionY(INITIALIZER_0);
		planeMovement.setPositionX(INITIALIZER_0);
		planeMovement.setPositionY(INITIALIZER_0);
		planeMovement.setSpeed(INITIALIZER_0);

		Plane plane = new Plane();
		plane.setFabricationDate(new Date());
		if(DAOUser.checkUsernameExists("ander")){
			plane.setAirline((Airline) DAOUser.getUser("ander"));

		}
		plane.setPlaneStatus(planestatus);
		plane.setModel(DAOPlaneModel.getRandomPlaneModel());
		plane.setSerial(createSerial());
		plane.setPlaneMovement(planeMovement);

		HibernateGeneric.saveObject(plane);

		return plane;
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
	 * Check schedule full.
	 *
	 * @param airport
	 *            the airport
	 * @return true, if successful
	 */
	private boolean checkScheduleFull(Airport airport) {

		long weekPlane = DAOSimulator.getNumberOfFlightsInAWeekFromAirport(airport.getId());

		return weekPlane >= (airport.getMaxFlights() * HOURS_IN_DAY * DAYS_IN_WEEK);
	}

	/**
	 * Active plane finished.
	 */
	public synchronized void activePlaneFinished() {
		activePlanesNum.decrementAndGet();
	}

	/**
	 * Interrupt.
	 */
	public void interrupt() {
		Thread.currentThread().interrupt();
	}

}
