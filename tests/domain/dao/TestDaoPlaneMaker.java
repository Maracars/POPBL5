package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.dao.DAOPlaneMaker;
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
		boolean result = DAOPlaneMaker.insertPlaneMaker(planeMaker);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPlaneMakers() {
		assertNotNull(ERROR_LOAD, DAOPlaneMaker.loadAllPlaneMakers());

	}

	@Test
	public void testInsertNullPlaneMakerIntoDB() {
		assertEquals(ERROR_INSERT, false, DAOPlaneMaker.insertPlaneMaker(null));
	}

	@Test
	public void testRemoveOneSpecificTerminal() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setId(1);
		boolean result = DAOPlaneMaker.deletePlaneMaker(planeMaker);
		assertEquals(ERROR_REMOVING, true, result);
	}

	@Test
	public void testRemoveOneNullTerminal() {
		assertEquals(ERROR_REMOVING, false, DAOPlaneMaker.deletePlaneMaker(null));
	}

}
