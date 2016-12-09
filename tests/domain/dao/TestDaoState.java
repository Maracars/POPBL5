package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.model.State;

public class TestDaoState {

	private static final String ERROR_REMOVING = "Error removing one gate from database";
	private static final String ERROR_LOAD = "Error load all states from database";
	private static final String ERROR_INSERT = "Error insert gate into database";

	@Test
	public void testInsertStateIntoDB() {
		State state = Initializer.initState();
		boolean result = HibernateGeneric.insertObject(state);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllStates() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new State()));

	}

	@Test
	public void testRemoveOneSpecificState() {
		State state = Initializer.initState();
		HibernateGeneric.insertObject(state);
		boolean result = HibernateGeneric.deleteObject(state);
		assertEquals(ERROR_REMOVING, true, result);
	}



}
