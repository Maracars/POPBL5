package domain.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import domain.dao.DAOTerminal;
import domain.model.Terminal;

public class TestDaoTerminal {



	private static final String ERROR_REMOVING = "Error removing one terminal from database";
	private static final String ERROR_GETTING = "Error getting all terminals of an airport from database";
	private static final String ERROR_INSERT = "Error insert terminal into database";
	private static final String TERMINAL_NAME = "3";
	
	@Test
	public void testInsertTerminalWithoutGatesIntoDB() {
		Terminal terminal = new Terminal();
		terminal.setName(TERMINAL_NAME);
		boolean result = DAOTerminal.insertTerminal(terminal);
		assertEquals(ERROR_INSERT, true, result);
	}
	@Test
	public void testInsertNullTerminalIntoDB() {
		assertEquals(ERROR_INSERT, false, DAOTerminal.insertTerminal(null));
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
		Terminal terminal = new Terminal();
		terminal.setId(1);
		boolean result = DAOTerminal.deleteTerminal(terminal); // aukeran Terminal bidaldu edo terminalId
		assertEquals(ERROR_REMOVING, true, result);
	}
	@Test
	public void testRemoveOneNullTerminal() {
		assertEquals(ERROR_REMOVING, false, DAOTerminal.deleteTerminal(null));
	}

}
