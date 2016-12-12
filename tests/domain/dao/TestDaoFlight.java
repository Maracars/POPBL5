package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Flight;

public class TestDaoFlight {

	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";

	@Test
	public void testInsertFlightWithNothingIntoDB() {
		Flight flight = new Flight();
		boolean result = HibernateGeneric.saveOrUpdateObject(flight);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertFlightDB() {

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initCompleteFlight());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllFlights() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Flight()));

	}

	@Test
	public void testRemoveOneSpecificFlight() {

		HibernateGeneric.saveOrUpdateObject(Initializer.initCompleteFlight());
		boolean result = HibernateGeneric.deleteObject((Flight) HibernateGeneric.loadAllObjects(new Flight()).get(0));

		assertEquals(REMOVE_ERROR, true, result);
	}

}
