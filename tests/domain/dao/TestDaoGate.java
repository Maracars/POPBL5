package domain.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import domain.dao.DAOGate;
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
		HibernateGeneric.saveOrUpdateObject(address);

		Airport airport = Initializer.initAirport(address);
		HibernateGeneric.saveOrUpdateObject(airport);

		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.saveOrUpdateObject(terminal);

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initGate(terminal));
		assertEquals(ERROR_INSERT, true, result);
	}

	@Ignore
	public void testLoadAllGatesFromOneSpecificTerminal() {
		int terminalId = 1;
		ArrayList<Gate> gateList;
		gateList = DAOGate.loadAllGatesFromTerminal(terminalId);
		assertNotNull(ERROR_GETTING, gateList);
	}

	@Test
	public void testRemoveOneSpecificGate() {
		Node node = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(node);
		
		Address address = Initializer.initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport airport = Initializer.initAirport(address);
		HibernateGeneric.saveOrUpdateObject(airport);

		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.saveOrUpdateObject(terminal);
		
		Gate gate = Initializer.initGate(node, terminal);
		HibernateGeneric.saveOrUpdateObject(gate);
		
		boolean result = HibernateGeneric.deleteObject(gate);
		assertEquals(ERROR_REMOVING, true, result);
	}



}
