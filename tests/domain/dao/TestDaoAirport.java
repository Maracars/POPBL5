package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import domain.model.Airport;
import domain.model.City;
import domain.model.State;
import domain.model.Terminal;

public class TestDaoAirport {

	private static final int MAX_FLIGHTS = 300;
	private static final String EUSKAL_HERRIA = "Euskal Herria";
	private static final String T1 = "T1";
	private static final String BERGARA = "Bergara";
	private static final String ERROR_LOAD = "Error load all airports from database";
	private static final String HEATHROW = "Heathrow";
	private static final String INSERT_ERROR = "Error insert airport into database";
	private static final String REMOVE_ERROR = "Error removing one airport from database";

	@Test
	public void testInsertAirportWithoutCityAndTerminalIntoDB() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		boolean result = HibernateGeneric.insertObject(airport);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertAirportWithCityAndWithoutTerminalIntoDB() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		City city = new City();
		city.setName(BERGARA);
		HibernateGeneric.insertObject(city);
		airport.setCity(city);
		boolean result = HibernateGeneric.insertObject(airport);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertAirportWithTerminalAndWithoutCityIntoDB() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		Terminal terminal = new Terminal();
		terminal.setName(T1);
		Collection<Terminal> terminalList = new ArrayList<>();
		terminalList.add(terminal);
		airport.setTerminalList(terminalList);
		boolean result = HibernateGeneric.insertObject(airport);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertAirportWithTerminalAndWithCityIntoDB() {

		boolean result = HibernateGeneric.insertObject(initCompleteAirport());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllAirports() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airport()));

	}

	@Test
	public void testRemoveOneSpecificAirport() {

		HibernateGeneric.insertObject(initCompleteAirport());
		Airport a = (Airport) HibernateGeneric.loadAllObjects(new Airport()).get(0);
		boolean result = HibernateGeneric.deleteObject(a);

		assertEquals(REMOVE_ERROR, true, result);
	}

	private Airport initCompleteAirport() {

		Terminal terminal = new Terminal();
		terminal.setName(T1);
		HibernateGeneric.insertObject(terminal);

		State state = new State();
		state.setName(EUSKAL_HERRIA);
		HibernateGeneric.insertObject(state);
		City city = new City();
		city.setName(BERGARA);
		city.setState(state);
		HibernateGeneric.insertObject(city);

		Collection<Terminal> terminalList = new ArrayList<>();
		terminalList.add(terminal);

		Airport airport = new Airport();
		airport.setTerminalList(terminalList);
		airport.setName(HEATHROW);
		airport.setCity(city);
		airport.setMaxFlights(MAX_FLIGHTS);

		return airport;
	}

}
