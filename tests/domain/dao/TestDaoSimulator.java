package domain.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;

import domain.model.Airport;
import domain.model.Flight;

public class TestDaoSimulator {

	private static final String ERROR_GET_FLIGHTS_NUMBER = "Error getting the number of flights in a week";

	@Ignore
	public void testGetNumberOfFlightsPerWeek() {
		Date date = new Date();
		Flight flight1 = Initializer.initCompleteFlight();
		Flight flight2 = Initializer.initCompleteFlight();
		flight1.setExpectedDepartureDate(new Date(date.getTime() + 1000));
		flight2.setExpectedArrivalDate(new Date(date.getTime() + 1000));

		Airport airport = flight1.getRoute().getDepartureGate().getTerminal().getAirport();

		HibernateGeneric.saveOrUpdateObject(flight1.getPlane());
		HibernateGeneric.saveOrUpdateObject(flight1);
		HibernateGeneric.saveOrUpdateObject(flight2);

		long result = DAOSimulator.getNumberOfFlightsInAWeekFromAirport(airport.getId());
		assertEquals(ERROR_GET_FLIGHTS_NUMBER, 2, result);
	}
}
