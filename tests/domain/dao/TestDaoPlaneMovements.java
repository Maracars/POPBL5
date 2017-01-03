package domain.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import domain.model.PlaneMovement;

public class TestDaoPlaneMovements {


	private static final String ERROR_INSERT = "Error insert plane maker into database";

	@Test
	public void testInsertPlaneMovementsWithNothingIntoDB() {
		PlaneMovement planeMovement = new PlaneMovement();
		boolean result = HibernateGeneric.saveOrUpdateObject(planeMovement);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneMovementlWithEverythingIntoDB() {
		PlaneMovement planeMovement = Initializer.initPlaneMovement();

		boolean result = HibernateGeneric.saveOrUpdateObject(planeMovement);
		assertEquals(ERROR_INSERT, false, result);
	}

}
