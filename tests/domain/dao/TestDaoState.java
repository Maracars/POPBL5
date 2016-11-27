package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.dao.DAOState;
import domain.model.State;

public class TestDaoState {

	private static final String ERROR_REMOVING = "Error removing one gate from database";
	private static final String ERROR_LOAD = "Error load all states from database";
	private static final String ERROR_INSERT = "Error insert gate into database";
	private static final String EUSKAL_HERRIA = "Euskal Herria";

	@Test
	public void testInsertStateIntoDB() {
		State state = new State();
		state.setName(EUSKAL_HERRIA);
		boolean result = DAOState.insertState(state);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertStateIntoDBSendingNullAsParameter() {
		assertEquals(ERROR_INSERT, false, DAOState.insertState(null));
	}

	@Test
	public void testLoadAllStates() {
		assertNotNull(ERROR_LOAD, DAOState.loadAllStates());

	}

	@Test
	public void testRemoveOneSpecificState() {
		State state = new State();
		state.setId(1);
		boolean result = DAOState.deleteState(state);
		assertEquals(ERROR_REMOVING, true, result);
	}

	@Test
	public void testRemoveOneSpecificStateSendingNullAsParameter() {

		assertEquals(ERROR_REMOVING, false, DAOState.deleteState(null));
	}

}
