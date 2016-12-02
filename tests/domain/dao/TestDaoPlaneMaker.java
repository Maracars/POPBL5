package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.model.PlaneMaker;

public class TestDaoPlaneMaker {

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



}
