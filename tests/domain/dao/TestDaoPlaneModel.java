package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.PlaneModel;

public class TestDaoPlaneModel {

	private static final String ERROR_REMOVING = "Error removing one plane maker from database";
	private static final String ERROR_LOAD = "Error load all plane makers from database";
	private static final String ERROR_INSERT = "Error insert plane maker into database";

	@Test
	public void testInsertPlaneModelWithoutPlaneMakerIntoDB() {
		PlaneModel planeModel = Initializer.initPlaneModel();
		boolean result = HibernateGeneric.saveOrUpdateObject(planeModel);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneModelWithPlaneMakerIntoDB() {

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initCompletePlaneModel());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPlaneModels() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new PlaneModel()));

	}

	@Test
	public void testRemoveOneSpecificPlaneModel() {

		boolean result = HibernateGeneric.deleteObject(Initializer.initCompletePlaneModel());
		assertEquals(ERROR_REMOVING, true, result);
	}


}
