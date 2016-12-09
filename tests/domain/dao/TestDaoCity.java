package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.City;
import domain.model.State;

public class TestDaoCity {

	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String BERGARA = "Bergara";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";

	@Test
	public void testInsertCityWithoutStateIntoDB() {
		City city = initCity();
		boolean result = HibernateGeneric.insertObject(city);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertCityWithStateIntoDB() {

		boolean result = HibernateGeneric.insertObject(initCompleteCity());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllCities() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new City()));

	}

	@Test
	public void testRemoveOneSpecificCity() {

		City city = initCompleteCity();
		HibernateGeneric.insertObject(city);
		boolean result = HibernateGeneric.deleteObject(city);

		assertEquals(REMOVE_ERROR, true, result);
	}

	private City initCompleteCity() {
		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);

		return initCity(state);
	}

	public static City initCity() {
		City city = new City();
		city.setName(BERGARA);
		return city;
	}

	public static City initCity(State state) {
		City city = initCity();
		city.setState(state);
		return city;
	}
}
