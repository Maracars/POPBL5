package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.City;

public class TestDaoCity {

	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";

	@Test
	public void testInsertCityWithoutStateIntoDB() {
		City city = Initializer.initCity();
		boolean result = HibernateGeneric.insertObject(city);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertCityWithStateIntoDB() {

		boolean result = HibernateGeneric.insertObject(Initializer.initCompleteCity());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllCities() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new City()));

	}

	@Test
	public void testRemoveOneSpecificCity() {

		City city = Initializer.initCompleteCity();
		HibernateGeneric.insertObject(city);
		boolean result = HibernateGeneric.deleteObject(city);

		assertEquals(REMOVE_ERROR, true, result);
	}

	
}
