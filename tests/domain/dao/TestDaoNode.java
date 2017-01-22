package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Node;

public class TestDaoNode {

	private static final String ERROR_REMOVING = "Error removing one node from database";
	private static final String ERROR_LOAD = "Error load all nodes from database";
	private static final String ERROR_INSERT = "Error insert node into database";


	@Test
	public void testInsertNodeIntoDB() {

		boolean result = HibernateGeneric.saveObject(Initializer.initNode());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllNodes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Node()));

	}

	@Test
	public void testRemoveOneSpecificnode() {
		Node node = Initializer.initNode();
		HibernateGeneric.saveObject(node);
		boolean result = HibernateGeneric.deleteObject(node);
		assertEquals(ERROR_REMOVING, true, result);
	}

	

}
