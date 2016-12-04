package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.model.PlaneStatus;
import domain.model.State;

public class TestDaoPlaneStatus {

	private static final String ERROR_REMOVING = "Error removing one planeStatus from database";
	private static final String ERROR_LOAD = "Error load all planeStatus from database";
	private static final String ERROR_INSERT = "Error insert planeStatus into database";

	@Test
	public void testInsertPlaneStatusIntoDB() {
		PlaneStatus planeStatus = new PlaneStatus();
		planeStatus.setDescription("The plane does not have any problem");
		planeStatus.setName("OK");
		boolean result = HibernateGeneric.insertObject(planeStatus);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertPlaneStatusWithoutNameIntoDB() {

		boolean result = HibernateGeneric.insertObject(new PlaneStatus());
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testLoadAllStates() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new PlaneStatus()));

	}

	@Test
	public void testRemoveOneSpecificState() {
		PlaneStatus planeStatus = new PlaneStatus();

		HibernateGeneric.insertObject(planeStatus);
		State s = (State) HibernateGeneric.loadAllObjects(new State()).get(0);
		boolean result = HibernateGeneric.deleteObject(s);
		assertEquals(ERROR_REMOVING, true, result);
	}

}
