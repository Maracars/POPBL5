package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import domain.dao.DAOTerminal;
import domain.model.Terminal;

public class TestDaoTerminal {



	@Test
	public void testInsertTerminalWithoutGatesIntoDB() {
		Terminal terminal = new Terminal();
		terminal.setName("3");
		boolean result = DAOTerminal.insertTerminal(terminal);
		assertEquals("Error insert terminal into database", true, result);
	}
	@Test
	public void testInsertNullTerminalIntoDB() {
		assertEquals("Error insert terminal into database", false, DAOTerminal.insertTerminal(null));
	}


	@Ignore
	public void testLoadAllTerminalsFromOneSpecificAirport() {
		int airportId = 1;
		ArrayList<Terminal> terminalList;
		terminalList = DAOTerminal.loadAllGatesFromTerminal(airportId);
		assertNotNull("Error getting all terminals of an airport from database", terminalList);
	}


	@Test
	public void testRemoveOneSpecificTerminal() {
		Terminal terminal = new Terminal();
		terminal.setId(1);
		boolean result = DAOTerminal.deleteTerminal(terminal); // aukeran Terminal bidaldu edo terminalId
		assertEquals("Error removing one terminal from database", true, result);
	}
	@Test
	public void testRemoveOneNullTerminal() {
		assertEquals("Error removing one terminal from database", false, DAOTerminal.deleteTerminal(null));
	}

}
