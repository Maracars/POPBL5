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

	private static final boolean ARRIVAL = false;
	private static final boolean DEPARTURE = true;
	private static final int MAX_ACTIVE_PLANES = 6;

	private AtomicInteger activePlanesNum = new AtomicInteger(0);

	private AirportController controller;
	private Airport airport;
	

	public FlightCreator(Airport airport) {
		this.airport = airport;
	}

	@Override
	public void run() {
		while (true) {
			programFlights();
			createThreadsOfFlights();
		}
	}

	private void createThreadsOfFlights() {
		List<Plane> planeList = DAOPlane.getArrivingPlanesSoon();
		for (Plane plane : planeList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				new Thread(new ArrivingPlane(plane, controller));
				activePlanesNum.incrementAndGet();
				System.out.println("New plane ARRIVING.");
			}
		}

		planeList = DAOPlane.getDeparturingPlanesSoon(1);
		for (Plane plane : planeList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				new Thread(new DeparturingPlane(plane, controller));
				activePlanesNum.incrementAndGet();
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
			route = DAORoute.selectDepartureRouteFromAirport(plane.getAirline().getId());
			assignRouteInSpecificTime(route, plane, DEPARTURE);
			System.out.println("New DEPARTURING flight created.");
		}
	}

	private Route selectRandomArrivalRoute() {
		List<Route> routeList = DAORoute.getRandomArrivalRouteFromAirport(airport.getId());
		// TODO aukeratu bat aleatoriamente listatik eta airporId hori behar dan
		// moduen hartu eta ez MAGICNUMBER
		return routeList.get(0);
	}

	private void assignRouteInSpecificTime(Route route, Plane plane, boolean mode) {
		Date date = selectDate(mode);// select date
		Flight flight = createFlight(route, plane, date);
		HibernateGeneric.saveOrUpdateObject(flight);
	}

	private Date selectDate(boolean mode) {
		// TODO Auto-generated method stub
		return null;
	}

	private Flight createFlight(Route route, Plane plane, Date date) {
		Flight flight = new Flight();
		flight.setExpectedArrivalDate(date);
		flight.setRoute(route);
		flight.setPlane(plane);
		// prezioa hemen ipini??
		return flight;
	}

	private Plane createPlane(Route route) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean checkScheduleFull(Airport airport) {
		Integer weekPlane = DAOSimulator.getNumberOfFlightsInAWeekFromAirport(airport.getId());
		return weekPlane > airport.getMaxFlights();
	}

	public synchronized void activePlaneFinished() {
		activePlanesNum.decrementAndGet();
	}

}
