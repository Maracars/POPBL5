package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import domain.model.Address;
import domain.model.Airport;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Terminal;

public class TestDaoGate {

	private static final String ERROR_REMOVING = "Error removing one gate from database";
	private static final String ERROR_GETTING = "Error getting all gates of a terminal from database";
	private static final String ERROR_INSERT = "Error insert gate into database";

	@Test
	public void testInsertGateIntoDB() {
		Address address = Initializer.initAddress();
		HibernateGeneric.saveObject(address);

		Node positionNode = Initializer.initNode();
		HibernateGeneric.saveObject(positionNode);

		Airport airport = Initializer.initAirport(address, positionNode);
		HibernateGeneric.saveObject(airport);

		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.saveObject(terminal);

		boolean result = HibernateGeneric.saveObject(Initializer.initGate(terminal));
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadFreeGatesFromOneSpecificTerminal() {
		Address address = Initializer.initAddress();
		HibernateGeneric.saveObject(address);
		Node positionNode = Initializer.initNode();
		HibernateGeneric.saveObject(positionNode);
		Airport airport = Initializer.initAirport(address, positionNode);
		HibernateGeneric.saveObject(airport);
		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.saveObject(terminal);
		Gate gate = Initializer.initGate(terminal);
		gate.setFree(true);
		HibernateGeneric.saveObject(gate);
		List<Gate> gateList;
		gateList = DAOGate.loadFreeGatesFromTerminal(terminal.getId());
		assertNotNull(ERROR_GETTING, gateList);
	}

	@Test
	public void testRemoveOneSpecificGate() {
		Node node = Initializer.initNode();
		HibernateGeneric.saveObject(node);
		
		Address address = Initializer.initAddress();
		HibernateGeneric.saveObject(address);

		Node positionNode = Initializer.initNode();
		HibernateGeneric.saveObject(positionNode);

		Airport airport = Initializer.initAirport(address, positionNode);
		HibernateGeneric.saveObject(airport);

		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.saveObject(terminal);
		
		Gate gate = Initializer.initGate(node, terminal);
		HibernateGeneric.saveObject(gate);
		
		boolean result = HibernateGeneric.deleteObject(gate);
		assertEquals(ERROR_REMOVING, true, result);
	}



}
