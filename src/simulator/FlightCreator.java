package simulator;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOPlane;
import domain.dao.DAORoute;
import domain.dao.DAOSimulator;
import domain.dao.HibernateGeneric;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.Route;

public class FlightCreator implements Runnable {

	private static final int TWELVE_HOURS = 43200000;
	private static final boolean ARRIVAL = false;
	private static final boolean DEPARTURE = true;
	private static final int MAX_ACTIVE_PLANES = 6;

	private AtomicInteger activePlanesNum = new AtomicInteger(0);

	private AirportController controller;
	private Airport airport;

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

	private void createThreadsOfFlights() {
		List<Plane> planeList = DAOPlane.getArrivingPlanesSoon(airport.getId());
		for (Plane plane : planeList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				Thread arrivalPlane = new Thread(new ArrivingPlane(plane, controller));
				activePlanesNum.incrementAndGet();
				arrivalPlane.start();
				System.out.println("New plane ARRIVING.");
			}
		}

		planeList = DAOPlane.getDeparturingPlanesSoon(airport.getId());
		for (Plane plane : planeList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				Thread departurePlane = new Thread(new DeparturingPlane(plane, controller));
				activePlanesNum.incrementAndGet();
				departurePlane.start();
				System.out.println("New plane wants to DEPARTURE");
			}
		}
	}

	private void programFlights() {
		Plane plane = new Plane();
		while (!checkScheduleFull(airport)) {
			Route route = selectRandomArrivalRoute();
			if ((plane = DAOPlane.getFreePlane()) == null) {
				plane = createPlane(route);
			}
			assignRouteInSpecificTime(route, plane, ARRIVAL);
			System.out.println("New ARRIVING flight created.");
			route = DAORoute.selectDepartureRouteFromAirport(airport.getId());
			assignRouteInSpecificTime(route, plane, DEPARTURE);
			System.out.println("New DEPARTURING flight created.");
		}
	}

	private Route selectRandomArrivalRoute() {
		List<Route> routeList = DAORoute.getRandomArrivalRouteFromAirport(airport.getId());
		// TODO aukeratu bat aleatoriamente listatik
		return routeList.get(0);
	}

	private void assignRouteInSpecificTime(Route route, Plane plane, boolean mode) {
		Date date = selectDate(mode);
		Flight flight = createFlight(route, plane, date, mode);
		HibernateGeneric.saveOrUpdateObject(flight);
	}

	private Date selectDate(boolean mode) {
		// TODO Auto-generated method stub
		Date date = new Date();

		return new Date(date.getTime() + TWELVE_HOURS);
	}

	private Flight createFlight(Route route, Plane plane, Date date, boolean mode) {
		Flight flight = new Flight();
		if (mode == ARRIVAL) {
			flight.setExpectedArrivalDate(date);
		} else {
			flight.setExpectedDepartureDate(date);
		}
		flight.setRoute(route);
		flight.setPlane(plane);
		// prezioa hemen ipini??
		return flight;
	}

	private Plane createPlane(Route route) {
		// TODO Auto-generated method stub

		// plane status ARRIVING???
		return null;
	}

	private boolean checkScheduleFull(Airport airport) {

		long weekPlane = DAOSimulator.getNumberOfFlightsInAWeekFromAirport(airport.getId());

		return weekPlane > airport.getMaxFlights();
	}

	public synchronized void activePlaneFinished() {
		activePlanesNum.decrementAndGet();
	}

}
