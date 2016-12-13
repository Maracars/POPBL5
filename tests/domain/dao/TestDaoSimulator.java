package domain.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import domain.model.Airport;
import domain.model.Flight;

public class TestDaoSimulator {

	private static final String ERROR_GET_FLIGHTS_NUMBER = "Error getting the number of flights in a week";

	@Test
	public void testGetNumberOfFlightsPerWeek() {
		Date date = new Date();
		Flight flight1 = Initializer.initCompleteFlight();
		flight1.setExpectedDepartureDate(new Date(date.getTime() + 6000000));
		HibernateGeneric.saveOrUpdateObject(flight1);

		Airport airport = flight1.getRoute().getDepartureGate().getTerminal().getAirport();

		long result = DAOSimulator.getNumberOfFlightsInAWeekFromAirport(airport.getId());
		assertEquals(ERROR_GET_FLIGHTS_NUMBER, 1, result);
	}
}
