package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Airport;
import domain.model.City;
import domain.model.State;

public class TestDaoAirport {

	private static final String ERROR_LOAD = "Error load all airports from database";
	private static final String INSERT_ERROR = "Error insert airport into database";

	@Test
	public void testInsertAirportWithoutCityAndTerminalIntoDB() {
		Airport airport = Initializer.initAirport();
		boolean result = HibernateGeneric.insertObject(airport);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertAirportWithCityAndWithoutTerminalIntoDB() {

		State state = Initializer.initState();
		HibernateGeneric.insertObject(state);

		City city = Initializer.initCity(state);
		HibernateGeneric.insertObject(city);

		Airport airport = Initializer.initAirport(city);
		boolean result = HibernateGeneric.insertObject(airport);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertAirportWithTerminalAndWithCityIntoDB() {

		boolean result = HibernateGeneric.insertObject(Initializer.initCompleteAirport());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllAirports() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airport()));

	}

	
}
