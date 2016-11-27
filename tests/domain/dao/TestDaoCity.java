package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.dao.DAOCity;
import domain.model.City;
import domain.model.State;

public class TestDaoCity {

	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String BERGARA = "Bergara";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";

	@Test
	public void testInsertCityWithoutIntoDB() {
		City city = new City();
		city.setName(BERGARA);
		boolean result = DAOCity.insertCity(city);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertCityWithStateIntoDB() {
		State state = new State();
		state.setName("Euskal herria");
		City city = new City();
		city.setName(BERGARA);
		city.setState(state);
		boolean result = DAOCity.insertCity(city);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllCities() {
		assertNotNull(ERROR_LOAD, DAOCity.loadAllCities());

	}

	@Test
	public void testInsertNullCityIntoDB() {
		assertEquals(INSERT_ERROR, false, DAOCity.insertCity(null));
	}

	@Test
	public void testRemoveOneSpecificCity() {
		City city = new City();
		city.setId(1);
		boolean result = DAOCity.deleteCity(city); 
		// aukeran Terminal bidaldu
		// edo terminalId
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testRemoveOneNullCity() {
		assertEquals(REMOVE_ERROR, false, DAOCity.deleteCity(null));
	}

}
