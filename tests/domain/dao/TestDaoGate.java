package domain.dao;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import domain.dao.DAOGate;
import domain.model.Gate;

public class TestDaoGate {


	@Test
	public void testInsertGateIntoDB() {
		Gate gate = new Gate();
		gate.setNumber(3);
		boolean result = DAOGate.insertGate(gate);
		assertEquals("Error insert gate into database", true, result);
	}
	@Test
	public void testInsertGateIntoDBSendingNullAsParameter() {
		assertEquals("Error insert gate into database", false, DAOGate.insertGate(null));
	}

	@Ignore
	public void testLoadAllGatesFromOneSpecificTerminal() {
		int terminalId = 1;
		ArrayList<Gate> gateList;
		gateList = DAOGate.loadAllGatesFromTerminal(terminalId);
		assertNotNull("Error getting all gates of a terminal from database", gateList);
	}

	@Test
	public void testRemoveOneSpecificGate() {
		Gate gate = new Gate();
		gate.setId(1);
		boolean result = DAOGate.deleteGate(gate);
		assertEquals("Error removing one gate from database", true, result);
	}
	@Test
	public void testRemoveOneSpecificGateSendingNullAsParameter() {

		assertEquals("Error removing one gate from database", false, DAOGate.deleteGate(null));
	}

}
