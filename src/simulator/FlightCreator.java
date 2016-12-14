package simulator;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOPlane;
import domain.dao.DAOPlaneModel;
import domain.dao.DAORoute;
import domain.dao.DAOSimulator;
import domain.dao.HibernateGeneric;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.PlaneStatus;
import domain.model.Route;

public class FlightCreator implements Runnable {

	private static final String POSITION_STATUS_WAITING_TO_ARRIVE = "WAITING TO ARRIVE";
	private static final int DAYS_IN_WEEK = 7;
	private static final int HOURS_IN_DAY = 24;
	private static final String TECHNICAL_STATUS = "OK";
	private static final String POSITION_STATUS_ARRIVING = "ARRIVING";
	private static final boolean ARRIVAL = false;
	private static final boolean DEPARTURE = true;
	private static final int MAX_ACTIVE_PLANES = 6;
	private static final int SERIAL_LENGTH = 5;
	private static final int HOUR_IN_MILIS = 3600000;
	private static final String POSITION_STATUS_WAITING_TO_DEPARTURE = "WAITING TO DEPARTURE";

	public AtomicInteger activePlanesNum = new AtomicInteger(0);

	private AirportController controller;
	private Airport airport;

	int contador = 0;

	public FlightCreator(Airport airport, AirportController ac) {
		this.airport = airport;
		this.controller = ac;
	}

	@Override
	public void run() {
		while (true) {
			programFlights();
			createThreadsOfFlights();
		}
	}

	private void programFlights() {
		Plane plane = new Plane();
		while (!checkScheduleFull(airport)) {
			Route route = selectRandomArrivalRoute();
			if ((plane = DAOPlane.getFreePlane()) == null) {
				plane = createPlane();
			}
			assignRouteInSpecificTime(route, plane, ARRIVAL);
			System.out.println("New ARRIVING flight created.");
			route = DAORoute.selectDepartureRouteFromAirport(airport.getId());
			assignRouteInSpecificTime(route, plane, DEPARTURE);
			System.out.println("New DEPARTURING flight created.");
			plane.getPlaneStatus().setPositionStatus(POSITION_STATUS_ARRIVING);
			HibernateGeneric.updateObject(plane.getPlaneStatus());
			contador = contador + 2;
			if (contador > 1000) {
				System.out.println("AAA");
			}
		}
	}

	private void createThreadsOfFlights() {
		List<Plane> planeList = DAOPlane.getArrivingPlanesSoon(airport.getId());
		for (Plane plane : planeList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				Thread arrivalPlane = new Thread(new ArrivingPlane(plane, controller, activePlanesNum));
				activePlanesNum.incrementAndGet();
				arrivalPlane.start();
				plane.getPlaneStatus().setPositionStatus(POSITION_STATUS_WAITING_TO_ARRIVE);
				HibernateGeneric.updateObject(plane.getPlaneStatus());
				System.out.println("New plane ARRIVING.");
			}
		}

		planeList = DAOPlane.getDeparturingPlanesSoon(airport.getId());
		for (Plane plane : planeList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				Thread departurePlane = new Thread(new DeparturingPlane(plane, controller, activePlanesNum));
				activePlanesNum.incrementAndGet();
				departurePlane.start();
				plane.getPlaneStatus().setPositionStatus(POSITION_STATUS_WAITING_TO_DEPARTURE);
				HibernateGeneric.updateObject(plane.getPlaneStatus());
				System.out.println("New plane wants to DEPARTURE");
			}
		}
	}

	private Route selectRandomArrivalRoute() {
		List<Route> routeList = DAORoute.getRandomArrivalRouteFromAirport(airport.getId());
		// TODO aukeratu bat aleatoriamente listatik
		return routeList.get(0);
	}

	private void assignRouteInSpecificTime(Route route, Plane plane, boolean mode) {
		Date date = selectDate(mode, plane);
		if (date != null) {
			Flight flight = createFlight(route, plane, date, mode);
			HibernateGeneric.saveOrUpdateObject(flight);
		}
	}

	private Date selectDate(boolean mode, Plane plane) {
		Date date = null;
		date = DAOSimulator.getCorrectDateFromSchedule(plane.getId(), airport.getId());

		// TODO selectDate datubaseko funtzioagaz linkau
		return date;// new Date(date.getTime() + TWELVE_HOURS);
	}

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
		return flight;
	}

	private Plane createPlane() {
		PlaneStatus planestatus = new PlaneStatus();
		planestatus.setPositionStatus(POSITION_STATUS_ARRIVING);
		planestatus.setTechnicalStatus(TECHNICAL_STATUS);
		HibernateGeneric.saveOrUpdateObject(planestatus);

		Plane plane = new Plane();
		plane.setFabricationDate(new Date());
		plane.setPlaneStatus(planestatus);
		plane.setModel(DAOPlaneModel.getRandomPlaneModel());
		plane.setSerial(createSerial());
		HibernateGeneric.saveOrUpdateObject(plane);

		return plane;
	}

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

	private boolean checkScheduleFull(Airport airport) {

		long weekPlane = DAOSimulator.getNumberOfFlightsInAWeekFromAirport(airport.getId());

		return weekPlane >= (airport.getMaxFlights() * HOURS_IN_DAY * DAYS_IN_WEEK);
	}

	public synchronized void activePlaneFinished() {
		activePlanesNum.decrementAndGet();
	}

}
