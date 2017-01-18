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

	private static final int LENGTH = 10;
	private static final int START = 0;
	private static final String ORDER_COL_DIR = "asc";
	private static final String ORDER_COL_NAME = "terminal.name";
	private static final String ERROR_REMOVING = "Error removing one gate from database";
	private static final String ERROR_GETTING = "Error getting all gates of a terminal from database";
	private static final String ERROR_INSERT = "Error insert gate into database";
	private static final String ERROR_LOAD_GATES_TABLE = "Error load gates for the table";
	private static final String ERROR_LOAD_ALL_GATES = "Error load all gates from airport";

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

	@Test
	public void testLoadGatesForTable(){
		Gate gate = Initializer.initCompleteGate();
		
		HibernateGeneric.saveObject(gate);
		
		assertNotNull(ERROR_LOAD_GATES_TABLE, DAOGate.loadGatesForTable(gate.getTerminal().getAirport().getId(), ORDER_COL_NAME, ORDER_COL_DIR, START, LENGTH));
	}
	
	@Test
	public void testLoadAllGatesFromAirport(){
		Gate gate = Initializer.initCompleteGate();
		
		HibernateGeneric.saveObject(gate);
		
		assertNotNull(ERROR_LOAD_ALL_GATES, DAOGate.loadAllGatesFromAirport(gate.getTerminal().getAirport().getId()));
	}
	
	@Test
	public void testLoadGateFromPosition(){
		Gate gate = Initializer.initCompleteGate();
		
		HibernateGeneric.saveObject(gate);
		
		assertNotNull(ERROR_LOAD_ALL_GATES, DAOGate.getNodeFromPosXPosY(gate.getPositionNode().getPositionX(), gate.getPositionNode().getPositionY()));
	}
	


}
