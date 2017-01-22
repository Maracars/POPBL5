package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.users.Airline;

public class TestDaoAirline {

	private static final String ERROR_LOAD = "Error load all airlines from database";
	private static final String INSERT_ERROR = "Error insert airline into database";
	private static final String REMOVE_ERROR = "Error removing one airline from database";


	@Test
	public void testInsertAirlineRoutes() {
		Airline airline = Initializer.initAirline();
		
		DAOUser.deleteUserWithUsername(airline);
		boolean result = HibernateGeneric.saveObject(airline);
		assertEquals(INSERT_ERROR, true, result);
	}

	

	@Test
	public void testLoadAllAirlines() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airline()));

	}

	@Test
	public void testRemoveOneSpecificAirline() {
		/* Create airline */
		Airline airline = Initializer.initAirline();
		DAOUser.deleteUserWithUsername(airline);
		HibernateGeneric.saveObject(airline);
		Airline airline2 = (Airline) HibernateGeneric.loadAllObjects(new Airline()).get(0);
		boolean result = HibernateGeneric.deleteObject(airline2);
		assertEquals(REMOVE_ERROR, true, result);
	}

	


}
