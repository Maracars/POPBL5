package domain.dao;


import static org.junit.Assert.*;

import org.junit.Test;

import domain.dao.DAOPlaneMaker;
import domain.model.PlaneMaker;

public class TestDaoPlaneMaker {

	@Test
	public void testInsertPlaneMakerIntoDB() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName("Boeing");
		boolean result = DAOPlaneMaker.insertPlaneMaker(planeMaker);
		assertEquals("Error insert plane maker into database", true, result);
	}
	

	@Test
	public void testLoadAllPlaneMakers() {
		assertNotNull("Error load all cities from database",DAOPlaneMaker.loadAllPlaneMakers());

	}
	@Test
	public void testInsertNullPlaneMakerIntoDB() {
		assertEquals("Error insert city into database", false, DAOPlaneMaker.insertPlaneMaker(null));
	}


	@Test
	public void testRemoveOneSpecificTerminal() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setId(1);
		boolean result = DAOPlaneMaker.deletePlaneMaker(planeMaker); // aukeran Terminal bidaldu edo terminalId
		assertEquals("Error removing one city from database", true, result);
	}
	@Test
	public void testRemoveOneNullTerminal() {
		assertEquals("Error removing one city from database", false, DAOPlaneMaker.deletePlaneMaker(null));
	}

}
