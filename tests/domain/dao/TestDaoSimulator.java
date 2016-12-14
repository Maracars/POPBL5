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

	private static final int ADDED_TIME = 3600000;
	private static final String ERROR_GET_FLIGHTS_NUMBER = "Error getting the number of flights in a week";

	@Test
	public void testGetNumberOfFlightsPerWeek() {
		Date date = new Date();
		Flight flight1 = Initializer.initCompleteFlight();
		flight1.setExpectedDepartureDate(new Date(date.getTime() + ADDED_TIME));
		HibernateGeneric.saveOrUpdateObject(flight1);

		Airport airport = flight1.getRoute().getDepartureGate().getTerminal().getAirport();

		long result = DAOSimulator.getNumberOfFlightsInAWeekFromAirport(airport.getId());
		assertEquals(ERROR_GET_FLIGHTS_NUMBER, 1, result);
	}

	@Test
	public void testGetCorrectDateFromSchedule() {
		Date date = new Date();

		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.saveOrUpdateObject(plane);
		
		Airport airport = Initializer.initCompleteAirport();
		HibernateGeneric.saveOrUpdateObject(airport);
		
		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.saveOrUpdateObject(terminal);
		
		Node node = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(node);
		
		Gate gate = Initializer.initGate(node, terminal);
		HibernateGeneric.saveOrUpdateObject(gate);
		
		Route route = new Route();
		route.setArrivalGate(gate);
		route.setDepartureGate(gate);
		HibernateGeneric.saveOrUpdateObject(route);
		
		Flight flight = new Flight();
		flight.setPlane(plane);
		flight.setRoute(route);
		flight.setExpectedArrivalDate(new Date(date.getTime() + 5 * ADDED_TIME));
		flight.setExpectedDepartureDate(new Date(date.getTime() + ADDED_TIME));
		HibernateGeneric.saveOrUpdateObject(flight);

		Date correctDate = DAOSimulator.getCorrectDateFromSchedule(plane.getId(), airport.getId());

		assertNotNull(ERROR_GET_FLIGHTS_NUMBER, correctDate);
	}
}
