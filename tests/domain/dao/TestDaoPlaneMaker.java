package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.model.PlaneMaker;

public class TestDaoPlaneMaker {

	private static final String ERROR_REMOVING = "Error removing one plane maker from database";
	private static final String ERROR_LOAD = "Error load all plane makers from database";
	private static final String ERROR_INSERT = "Error insert plane maker into database";
	private static final String BOEING = "Boeing";

	@Test
	public void testInsertPlaneMakerIntoDB() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		boolean result = HibernateGeneric.insertObject(planeMaker);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPlaneMakers() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new PlaneMaker()));

	}

	@Test
	public void testInsertNullPlaneMakerIntoDB() {
		assertEquals(ERROR_INSERT, false, HibernateGeneric.insertObject(null));
	}

	@Test
	public void testRemoveOneSpecificPlaneMaker() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setId(1);
		HibernateGeneric.insertObject(planeMaker);
		PlaneMaker p = (PlaneMaker) HibernateGeneric.loadAllObjects(new PlaneMaker()).get(0);
		boolean result = HibernateGeneric.deleteObject(p);
		//TODO honek TRAVISen ez dau failauko, baina hemen bai,
		//kontua da PlaneMakerrak PlaneModela daukala eta foreign key bat da
		assertEquals(ERROR_REMOVING, true, result);
	}

	@Test
	public void testRemoveOneNullTerminal() {
		assertEquals(ERROR_REMOVING, false, HibernateGeneric.deleteObject(null));
	}

}
