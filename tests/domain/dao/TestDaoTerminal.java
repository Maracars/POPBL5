package domain.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import domain.model.Airport;
import domain.model.Node;
import domain.model.Terminal;

public class TestDaoTerminal {

	private static final String ERROR_REMOVING = "Error removing one terminal from database";
	private static final String ERROR_INSERT = "Error insert terminal into database";

	@Test
	public void testInsertTerminalWithoutGatesIntoDB() {
		Node node = Initializer.initNode();
		HibernateGeneric.saveObject(node);

		Airport airport = Initializer.initCompleteAirport();
		HibernateGeneric.saveObject(airport);

		Terminal terminal = Initializer.initTerminal(airport, node);
		boolean result = HibernateGeneric.saveObject(terminal);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testRemoveOneSpecificTerminal() {
		Node node = Initializer.initNode();
		HibernateGeneric.saveObject(node);

		Airport airport = Initializer.initCompleteAirport();
		HibernateGeneric.saveObject(airport);

		Terminal terminal = Initializer.initTerminal(airport, node);
		HibernateGeneric.saveObject(terminal);

		boolean result = HibernateGeneric.deleteObject(terminal);
		// aukeran Terminal bidaldu edo terminalId
		assertEquals(ERROR_REMOVING, true, result);
	}

}
