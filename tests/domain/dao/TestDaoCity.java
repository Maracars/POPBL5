package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.dao.DAOCity;
import domain.model.City;
import domain.model.State;

public class TestDaoCity {

	private static final String EUSKAL_HERRIA = "Euskal herria";
	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String BERGARA = "Bergara";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";

	@Test
	public void testInsertCityWithoutStateIntoDB() {
		City city = new City();
		city.setName(BERGARA);
		boolean result = DAOCity.insertCity(city);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertCityWithStateIntoDB() {
		State state = new State();
		state.setName(EUSKAL_HERRIA);
		DAOState.insertState(state);
		
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
		DAOCity.insertCity(city);
		boolean result = DAOCity.deleteCity(DAOCity.loadAllCities().get(0)); 
		
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testRemoveOneNullCity() {
		assertEquals(REMOVE_ERROR, false, DAOCity.deleteCity(null));
	}

}
