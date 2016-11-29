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
	
	private static final String ERROR_LOAD = "Error load all airports from database";
	private static final String HEATHROW = "Heathrow";
	private static final String INSERT_ERROR = "Error insert airport into database";
	private static final String REMOVE_ERROR = "Error removing one airport from database";

	@Test
	public void testInsertAirportWithoutCityAndTerminalIntoDB() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		boolean result = DAOAirport.insertAirport(airport);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertAirportWithCityAndWithoutTerminalIntoDB() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		City city = new City();
		city.setName("Bergara");
		airport.setCity(city);
		boolean result = DAOAirport.insertAirport(airport);
		assertEquals(INSERT_ERROR, false, result);
	}
	
	@Test
	public void testInsertAirportWithTerminalAndWithoutCityIntoDB() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		Terminal terminal = new Terminal();
		terminal.setName("T1");
		Collection<Terminal> terminalList = new ArrayList<>();
		terminalList.add(terminal);
		airport.setTerminalList(terminalList);
		boolean result = DAOAirport.insertAirport(airport);
		assertEquals(INSERT_ERROR, false, result);
	}
	
	@Test
	public void testInsertAirportWithTerminalAndWithCityIntoDB() {
		Airport airport = new Airport();
		airport.setName(HEATHROW);
		Terminal terminal = new Terminal();
		terminal.setName("T1");
		Collection<Terminal> terminalList = new ArrayList<>();
		terminalList.add(terminal);
		airport.setTerminalList(terminalList);
		City city = new City();
		city.setName("Bergara");
		State state = new State();
		state.setName("Euskal Herria");
		city.setState(state);
		airport.setCity(city);
		airport.setMaxFlights(300);
		boolean result = DAOAirport.insertAirport(airport);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllAirports() {
		assertNotNull(ERROR_LOAD, DAOAirport.loadAllAirports());

	}

	@Test
	public void testInsertNullAirportIntoDB() {
		assertEquals(INSERT_ERROR, false, DAOAirport.insertAirport(null));
	}

	@Test
	public void testRemoveOneSpecificAirport() {
		Airport airport = new Airport();
		airport.setId(1);
		boolean result = DAOAirport.deleteAirport(airport); 
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testRemoveOneNullAirport() {
		assertEquals(REMOVE_ERROR, false, DAOAirport.deleteAirport(null));
	}

}
