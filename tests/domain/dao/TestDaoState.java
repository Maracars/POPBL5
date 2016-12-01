package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;

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
		boolean result = HibernateGeneric.insertObject(state);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertStateIntoDBSendingNullAsParameter() {
		assertEquals(ERROR_INSERT, false, HibernateGeneric.insertObject(null));
	}

	@Test
	public void testLoadAllStates() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new State()));

	}

	@Test
	public void testRemoveOneSpecificState() {
		State state = new State();
		state.setId(1);

		HibernateGeneric.insertObject(state);
		State s = (State) HibernateGeneric.loadAllObjects(new State()).get(0);
		boolean result = HibernateGeneric.deleteObject(s);
		assertEquals(ERROR_REMOVING, true, result);
	}

	@Test
	public void testRemoveOneSpecificStateSendingNullAsParameter() {

		assertEquals(ERROR_REMOVING, false, HibernateGeneric.deleteObject(null));
	}

}
