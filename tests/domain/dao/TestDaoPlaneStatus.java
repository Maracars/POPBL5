package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.PlaneStatus;

public class TestDaoPlaneStatus {


	private static final String ERROR_REMOVING = "Error removing one planeStatus from database";
	private static final String ERROR_LOAD = "Error load all planeStatus from database";
	private static final String ERROR_INSERT = "Error insert planeStatus into database";

	@Test
	public void testInsertPlaneStatusIntoDB() {
		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		boolean result = HibernateGeneric.saveOrUpdateObject(planeStatus);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertPlaneStatusWithoutNameIntoDB() {

		boolean result = HibernateGeneric.saveOrUpdateObject(new PlaneStatus());
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testLoadAllStates() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new PlaneStatus()));

	}

	@Test
	public void testRemoveOneSpecificPlaneStatus() {
		PlaneStatus planeStatus = Initializer.initPlaneStatus();

		HibernateGeneric.saveOrUpdateObject(planeStatus);
		PlaneStatus s = (PlaneStatus) HibernateGeneric.loadAllObjects(new PlaneStatus()).get(0);
		boolean result = HibernateGeneric.deleteObject(s);
		assertEquals(ERROR_REMOVING, true, result);
	}



}
