package domain.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import domain.dao.DAOGate;
import domain.model.Gate;

public class TestDaoGate {

	private static final String ERROR_REMOVING = "Error removing one gate from database";
	private static final String ERROR_GETTING = "Error getting all gates of a terminal from database";
	private static final String ERROR_INSERT = "Error insert gate into database";
	private static final int GATE_NUM = 3;

	@Test
	public void testInsertGateIntoDB() {
		Gate gate = new Gate();
		gate.setNumber(GATE_NUM);
		boolean result = HibernateGeneric.insertObject(gate);
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
		Gate gate = new Gate();
		gate.setId(1);
		HibernateGeneric.insertObject(gate);
		boolean result = HibernateGeneric.deleteObject(
				(Gate) HibernateGeneric.loadAllObjects(new Gate()).get(0));
		assertEquals(ERROR_REMOVING, true, result);
	}



}
