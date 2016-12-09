package domain.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import domain.dao.DAOGate;
import domain.model.Airport;
import domain.model.City;
import domain.model.Gate;
import domain.model.Node;
import domain.model.State;
import domain.model.Terminal;

public class TestDaoGate {

	private static final String ERROR_REMOVING = "Error removing one gate from database";
	private static final String ERROR_GETTING = "Error getting all gates of a terminal from database";
	private static final String ERROR_INSERT = "Error insert gate into database";
	private static final int GATE_NUM = 3;

	@Test
	public void testInsertGateIntoDB() {
		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);
		
		City city = TestDaoCity.initCity(state);
		HibernateGeneric.insertObject(city);
		
		Airport airport = TestDaoAirport.initAirport(city);
		HibernateGeneric.insertObject(airport);

		Terminal terminal = TestDaoTerminal.initTerminal(airport);
		HibernateGeneric.insertObject(terminal);

		boolean result = HibernateGeneric.insertObject(initGate(terminal));
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
		Node node = TestDaoNode.initNode();
		HibernateGeneric.insertObject(node);
		
		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);
		
		City city = TestDaoCity.initCity(state);
		HibernateGeneric.insertObject(city);
		
		Airport airport = TestDaoAirport.initAirport(city);
		HibernateGeneric.insertObject(airport);

		Terminal terminal = TestDaoTerminal.initTerminal(airport);
		HibernateGeneric.insertObject(terminal);
		
		Gate gate = initGate(node, terminal);
		HibernateGeneric.insertObject(gate);
		
		boolean result = HibernateGeneric.deleteObject(gate);
		assertEquals(ERROR_REMOVING, true, result);
	}

	public static Gate initGate(Node node) {
		Gate gate = initGate();
		gate.setPositionNode(node);
		return gate;

	}

	public static Gate initGate(Node node, Terminal terminal) {
		Gate gate = initGate();
		gate.setPositionNode(node);
		gate.setTerminal(terminal);
		return gate;

	}

	public static Gate initGate(Terminal terminal) {
		Gate gate = initGate();
		gate.setTerminal(terminal);
		return gate;

	}

	public static Gate initGate() {
		Gate gate = new Gate();
		gate.setNumber(GATE_NUM);
		return gate;
	}

}
