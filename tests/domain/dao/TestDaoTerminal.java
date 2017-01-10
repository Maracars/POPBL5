package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import domain.model.Airport;
import domain.model.Terminal;

public class TestDaoTerminal {

	private static final String ERROR_REMOVING = "Error removing one terminal from database";
	private static final String ERROR_GETTING = "Error getting all terminals of an airport from database";
	private static final String ERROR_INSERT = "Error insert terminal into database";

	@Test
	public void testInsertTerminalWithoutGatesIntoDB() {
		Airport airport = Initializer.initCompleteAirport();
		HibernateGeneric.saveObject(airport);
		
		Terminal terminal = Initializer.initTerminal(airport);
		boolean result = HibernateGeneric.saveObject(terminal);
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
		
		
		Airport airport = Initializer.initCompleteAirport();
		HibernateGeneric.saveObject(airport);

		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.saveObject(terminal);

		boolean result = HibernateGeneric.deleteObject(terminal);
		// aukeran Terminal bidaldu edo terminalId
		assertEquals(ERROR_REMOVING, true, result);
	}


}
