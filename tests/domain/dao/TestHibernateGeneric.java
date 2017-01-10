package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Airport;
import domain.model.Flight;

public class TestHibernateGeneric {

	private static final String ERROR_LOAD = "Error load all airlines from database";
	private static final String INSERT_ERROR = "Error insert airline into database";
	private static final String REMOVE_ERROR = "Error removing one airline from database";

	@Test
	public void testDeleteHibernateWithNull() {

		assertEquals(REMOVE_ERROR, false, HibernateGeneric.deleteObject(null));
	}

	@Test
	public void testLoadAllAirports() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airport()));

	}

	@Test
	public void testInsertHibernateWithNull() {

		assertEquals(INSERT_ERROR, false, HibernateGeneric.saveObject(null));
	}
	
	@Test
	public void testDeleteAllObjects() {
		assertEquals(REMOVE_ERROR, true, HibernateGeneric.deleteAllObjects(new Flight()));
	}

}
