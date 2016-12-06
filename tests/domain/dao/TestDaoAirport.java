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
	private static final String ERROR_LOAD = "Error load all airports from database";
	private static final String HEATHROW = "Heathrow";
	private static final String INSERT_ERROR = "Error insert airport into database";
	private static final String REMOVE_ERROR = "Error removing one airport from database";

	@Test
	public void testInsertAirportWithoutCityAndTerminalIntoDB() {
		Airport airport = initAirport();
		boolean result = HibernateGeneric.insertObject(airport);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertAirportWithCityAndWithoutTerminalIntoDB() {

		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);

		City city = TestDaoCity.initCity(state);
		HibernateGeneric.insertObject(city);

		Airport airport = initAirport(city);
		airport.setTerminalList(null);
		boolean result = HibernateGeneric.insertObject(airport);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertAirportWithTerminalAndWithoutCityIntoDB() {

		Terminal terminal = TestDaoTerminal.initTerminal();
		Collection<Terminal> terminalList = new ArrayList<>();
		terminalList.add(terminal);
		Airport airport = initAirport(terminalList);
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

		Terminal terminal = TestDaoTerminal.initTerminal();
		HibernateGeneric.insertObject(terminal);

		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);

		City city = TestDaoCity.initCity(state);
		HibernateGeneric.insertObject(city);

		Collection<Terminal> terminalList = new ArrayList<>();
		terminalList.add(terminal);

		return initAirport(terminalList, city);
	}

	public static Airport initAirport(Collection<Terminal> terminalList, City city) {
		Airport airport = initAirport();
		airport.setTerminalList(terminalList);
		airport.setCity(city);
		return airport;
	}

	public static Airport initAirport(Collection<Terminal> terminalList) {
		Airport airport = initAirport();
		airport.setTerminalList(terminalList);
		return airport;
	}

	public static Airport initAirport(City city) {
		Airport airport = initAirport();
		airport.setCity(city);
		return airport;
	}

	public static Airport initAirport() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		airport.setMaxFlights(MAX_FLIGHTS);
		return airport;
	}

}
