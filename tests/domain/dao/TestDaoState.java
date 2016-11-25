package domain.dao;


import static org.junit.Assert.*;

import org.junit.Test;

import domain.dao.DAOState;
import domain.model.State;

public class TestDaoState {


	@Test
	public void testInsertStateIntoDB() {
		State state = new State();
		state.setName("Euskal Herria");
		boolean result = DAOState.insertState(state);
		assertEquals("Error insert gate into database", true, result);
	}
	@Test
	public void testInsertStateIntoDBSendingNullAsParameter() {
		assertEquals("Error insert gate into database", false, DAOState.insertState(null));
	}
	@Test
	public void testLoadAllStates() {
		assertNotNull("Error load all states from database",DAOState.loadAllStates());

	}
	

	@Test
	public void testRemoveOneSpecificState() {
		State state = new State();
		state.setId(1);
		boolean result = DAOState.deleteState(state);
		assertEquals("Error removing one gate from database", true, result);
	}
	@Test
	public void testRemoveOneSpecificStateSendingNullAsParameter() {

		assertEquals("Error removing one gate from database", false, DAOState.deleteState(null));
	}

}
