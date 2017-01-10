package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Address;
import domain.model.Airport;
import domain.model.Node;

public class TestDaoAirport {

	private static final String ERROR_LOAD = "Error load all airports from database";
	private static final String INSERT_ERROR = "Error insert airport into database";

	@Test
	public void testInsertAirportWithoutCityAndTerminalIntoDB() {
		Airport airport = Initializer.initAirport();
		boolean result = HibernateGeneric.saveObject(airport);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertAirportWithCityAndWithoutTerminalIntoDB() {

		Address address = Initializer.initAddress();
		HibernateGeneric.saveObject(address);

		Node positionNode = Initializer.initNode();
		HibernateGeneric.saveObject(positionNode);

		Airport airport = Initializer.initAirport(address, positionNode);
		boolean result = HibernateGeneric.saveObject(airport);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertAirportWithTerminalAndWithCityIntoDB() {

		boolean result = HibernateGeneric.saveObject(Initializer.initCompleteAirport());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllAirports() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airport()));

	}
	
	@Test
	public void testGetLocaleAirport() {
		Airport localeAirport = Initializer.initCompleteAirport();
		localeAirport.setLocale(true);
		HibernateGeneric.saveObject(localeAirport);
		assertNotNull(ERROR_LOAD, DAOAirport.getLocaleAirport());
	}

}
