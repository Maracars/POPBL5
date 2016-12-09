package simulator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.Route;

public class FlightCreator implements Runnable {

	private static final boolean ARRIVAL = false;
	private static final boolean DEPARTURE = true;
	private static final int MAX_ACTIVE_PLANES = 6;

	private final AtomicInteger activePlanesNum = new AtomicInteger(0);

	private AirportController controller;

	@Override
	public void run() {
		while (true) {
			programFlights();
			createThreadsOfFlights();
		}
	}

	private void createThreadsOfFlights() {
		ArrayList<Plane> planeList = HibernateGeneric.getArrivingPlanesSoon();
		for (Plane plane : planeList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				new Thread(new ArrivingPlane(plane, controller));
				activePlanesNum.incrementAndGet();
			}
		}
		planeList = HibernateGeneric.getDeparturingPlanesSoon();
		for (Plane plane : planeList) {
			if (activePlanesNum.get() < MAX_ACTIVE_PLANES) {
				new Thread(new DeparturingPlane(plane, controller));
				activePlanesNum.incrementAndGet();
			}
		}
	}

	private void programFlights() {
		Plane plane = new Plane();
		while (!checkScheduleFull()) {
			Route route = selectRandomArrivalRoute();
			if ((plane = HibernateGeneric.getFreePlane()) == null) {
				plane = createPlane(route);
			}
			assignRouteInSpecificTime(route, plane, ARRIVAL);
			route = HibernateGeneric.selectDepartureRouteFromAirport(plane.getAirline().getId());
			assignRouteInSpecificTime(route, plane, DEPARTURE);
		}
	}

	private Route selectRandomArrivalRoute() {
		List<Route> routeList = HibernateGeneric.getRandomArrivalRouteFromAirport(1);
		// TODO aukeratu bat aleatoriamente listatik eta airporId hori behar dan
		// moduen hartu eta ez MAGICNUMBER
		return routeList.get(0);
	}

	private void assignRouteInSpecificTime(Route route, Plane plane, boolean mode) {
		Date date = selectDate(mode);// select date
		Flight flight = createFlight(route, plane, date);
		HibernateGeneric.insertObject(flight);
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

	private boolean checkScheduleFull() {
		// TODO
		return false;
	}

	public synchronized void activePlaneFinished() {
		activePlanesNum.decrementAndGet();
	}

}
