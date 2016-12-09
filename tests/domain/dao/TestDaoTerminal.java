package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import domain.model.Airport;
import domain.model.City;
import domain.model.State;
import domain.model.Terminal;

public class TestDaoTerminal {

	private static final String ERROR_REMOVING = "Error removing one terminal from database";
	private static final String ERROR_GETTING = "Error getting all terminals of an airport from database";
	private static final String ERROR_INSERT = "Error insert terminal into database";

	@Test
	public void testInsertTerminalWithoutGatesIntoDB() {
		Terminal terminal = Initializer.initTerminal();
		boolean result = HibernateGeneric.insertObject(terminal);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Ignore
	public void testLoadAllTerminalsFromOneSpecificAirport() {
		int airportId = 1;
		ArrayList<Terminal> terminalList;
		terminalList = DAOTerminal.loadAllGatesFromTerminal(airportId);
		assertNotNull(ERROR_GETTING, terminalList);
	}

	@Test
	public void testRemoveOneSpecificTerminal() {
		State state = Initializer.initState();
		HibernateGeneric.insertObject(state);
		
		City city = Initializer.initCity(state);
		HibernateGeneric.insertObject(city);
		
		Airport airport = Initializer.initAirport(city);
		HibernateGeneric.insertObject(airport);

		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.insertObject(terminal);

		boolean result = HibernateGeneric.deleteObject(terminal);
		// aukeran Terminal bidaldu edo terminalId
		assertEquals(ERROR_REMOVING, true, result);
	}


}
