package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.City;
import domain.model.Delay;

public class TestDaoDelay {

	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";


	@Test
	public void testInsertDelayWithEverythingIntoDB() {

		boolean result = HibernateGeneric.insertObject(Initializer.initCompleteDelay());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertDelayWithoutFlightIntoDB() {

		Delay delay = new Delay();
		boolean result = HibernateGeneric.insertObject(delay);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testLoadAllDelays() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new City()));

	}

	@Test
	public void testRemoveOneSpecificDelay() {

		HibernateGeneric.insertObject(Initializer.initCompleteDelay());
		boolean result = HibernateGeneric.deleteObject(
				(Delay) HibernateGeneric.loadAllObjects(new Delay()).get(0));

		assertEquals(REMOVE_ERROR, true, result);
	}



}
