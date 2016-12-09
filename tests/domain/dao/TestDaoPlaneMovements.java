package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.PlaneMovement;

public class TestDaoPlaneMovements {


	private static final String ERROR_REMOVING = "Error removing one plane maker from database";
	private static final String ERROR_LOAD = "Error load all plane makers from database";
	private static final String ERROR_INSERT = "Error insert plane maker into database";

	@Test
	public void testInsertPlaneMovementsWithNothingIntoDB() {
		PlaneMovement planeMovement = new PlaneMovement();
		boolean result = HibernateGeneric.insertObject(planeMovement);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneMovementlWithEverythingIntoDB() {

		boolean result = HibernateGeneric.insertObject(Initializer.initCompletePlaneMovements());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPlaneMovements() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new PlaneMovement()));

	}

	@Test
	public void testRemoveOneSpecificPlaneMovement() {

		boolean result = HibernateGeneric.deleteObject(Initializer.initCompletePlaneMovements());
		assertEquals(ERROR_REMOVING, true, result);
	}



}
