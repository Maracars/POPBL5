package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Airline;

public class TestDaoAirline {

	private static final String ERROR_LOAD = "Error load all airlines from database";
	private static final String INSERT_ERROR = "Error insert airline into database";
	private static final String REMOVE_ERROR = "Error removing one airline from database";


	@Test
	public void testInsertAirlineWithoutRoutes() {
		Airline airline = Initializer.initAirline();
		Initializer.deleteAllUsers();

		boolean result = HibernateGeneric.insertObject(airline);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertAirlineWithRoutes() {

		assertEquals(INSERT_ERROR, true, HibernateGeneric.insertObject(Initializer.initCompleteAirline()));
	}

	@Test
	public void testLoadAllAirlines() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airline()));

	}

	@Test
	public void testRemoveOneSpecificAirline() {
		/* Create airline */

		HibernateGeneric.insertObject(Initializer.initCompleteAirline());
		Airline airline = (Airline) HibernateGeneric.loadAllObjects(new Airline()).get(0);
		boolean result = HibernateGeneric.deleteObject(airline);
		assertEquals(REMOVE_ERROR, true, result);
	}

	


}
