package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import domain.model.Airport;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Plane;
import domain.model.Route;
import domain.model.Terminal;

public class TestDaoSimulator {

	private static final int INCREMENT_NUMBER = 5;
	private static final int ADDED_TIME = 3600000;
	private static final String ERROR_GET_FLIGHTS_NUMBER = "Error getting the number of flights in a week";

	@Test
	public void testGetNumberOfFlightsPerWeek() {
		Date date = new Date();
		Flight flight1 = Initializer.initCompleteFlight();
		flight1.setExpectedDepartureDate(new Date(date.getTime() + ADDED_TIME));
		HibernateGeneric.saveObject(flight1);

		Airport airport = flight1.getRoute().getDepartureGate().getTerminal().getAirport();

		long result = DAOSimulator.getNumberOfFlightsInAWeekFromAirport(airport.getId());
		assertEquals(ERROR_GET_FLIGHTS_NUMBER, 1, result);
	}

	@Test
	public void testGetCorrectDateFromSchedule() {
		Date date = new Date();

		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.saveObject(plane);

		Airport airport = Initializer.initCompleteAirport();
		HibernateGeneric.saveObject(airport);

		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.saveObject(terminal);

		Node node = Initializer.initNode();
		HibernateGeneric.saveObject(node);

		Gate gate = Initializer.initGate(node, terminal);
		HibernateGeneric.saveObject(gate);

		Route route = new Route();
		route.setArrivalGate(gate);
		route.setDepartureGate(gate);
		HibernateGeneric.saveObject(route);

		Flight flight = new Flight();
		flight.setPlane(plane);
		flight.setRoute(route);
		flight.setExpectedArrivalDate(new Date(date.getTime() + ADDED_TIME * INCREMENT_NUMBER));
		flight.setExpectedDepartureDate(new Date(date.getTime() + ADDED_TIME));
		HibernateGeneric.saveObject(flight);

		Date correctDate = DAOSimulator.getCorrectDateFromSchedule(plane.getId(), airport.getId());

		assertNotNull(ERROR_GET_FLIGHTS_NUMBER, correctDate);
	}
}
