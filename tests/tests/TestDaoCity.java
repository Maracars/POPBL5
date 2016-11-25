package tests;


import static org.junit.Assert.*;

import org.junit.Test;

import domain.dao.DAOCity;
import domain.model.City;
import domain.model.State;

public class TestDaoCity {

	@Test
	public void testInsertCityWithoutIntoDB() {
		City city = new City();
		city.setName("Bergara");
		boolean result = DAOCity.insertCity(city);
		assertEquals("Error insert city into database", false, result);
	}
	
	@Test
	public void testInsertCityWithStateIntoDB() {
		State state = new State();
		state.setName("Euskal herria");
		City city = new City();
		city.setName("Bergara");
		city.setState(state);
		boolean result = DAOCity.insertCity(city);
		assertEquals("Error insert city into database", true, result);
	}
	@Test
	public void testLoadAllCities() {
		assertNotNull("Error load all cities from database",DAOCity.loadAllCities());

	}
	@Test
	public void testInsertNullCityIntoDB() {
		assertEquals("Error insert city into database", false, DAOCity.insertCity(null));
	}


	@Test
	public void testRemoveOneSpecificCity() {
		City city = new City();
		city.setId(1);
		boolean result = DAOCity.deleteCity(city); // aukeran Terminal bidaldu edo terminalId
		assertEquals("Error removing one city from database", true, result);
	}
	@Test
	public void testRemoveOneNullCity() {
		assertEquals("Error removing one city from database", false, DAOCity.deleteCity(null));
	}

}
