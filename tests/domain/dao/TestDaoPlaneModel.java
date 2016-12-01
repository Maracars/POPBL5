package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.PlaneMaker;
import domain.model.PlaneModel;

public class TestDaoPlaneModel {

	private static final String SERIAL = "SS88";
	private static final String ERROR_REMOVING = "Error removing one plane maker from database";
	private static final String ERROR_LOAD = "Error load all plane makers from database";
	private static final String ERROR_INSERT = "Error insert plane maker into database";
	private static final String BOEING = "Boeing";

	@Test
	public void testInsertPlaneModelWithoutPlaneMakerIntoDB() {
		PlaneModel planeModel = new PlaneModel();
		planeModel.setName(SERIAL);
		boolean result = HibernateGeneric.insertObject(planeModel);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneModelWithPlaneMakerIntoDB() {
		PlaneModel planeModel = new PlaneModel();
		planeModel.setName(SERIAL);
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		HibernateGeneric.insertObject(planeMaker);
		planeModel.setPlaneMaker(planeMaker);
		boolean result = HibernateGeneric.insertObject(planeModel);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPlaneModels() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new PlaneModel()));

	}

	@Test
	public void testInsertNullPlaneModelIntoDB() {
		assertEquals(ERROR_INSERT, false, HibernateGeneric.insertObject(null));
	}

	@Test
	public void testRemoveOneSpecificPlaneModel() {
		PlaneModel planeModel = new PlaneModel();
		planeModel.setName(SERIAL);
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		HibernateGeneric.insertObject(planeMaker);
		planeModel.setPlaneMaker(planeMaker);
		boolean result = HibernateGeneric.deleteObject(planeModel);
		assertEquals(ERROR_REMOVING, true, result);
	}

	@Test
	public void testRemoveOneNullPlaneModel() {
		assertEquals(ERROR_REMOVING, false, HibernateGeneric.deleteObject(null));
	}

}
