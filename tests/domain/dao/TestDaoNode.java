package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Node;

public class TestDaoNode {

	private static final String ERROR_REMOVING = "Error removing one node from database";
	private static final String ERROR_LOAD = "Error load all nodes from database";
	private static final String ERROR_INSERT = "Error insert node into database";
	private static final String NODE_NAME = "Node A";
	private static final double POSITION_X = 33.2;
	private static final double POSITION_Y = 13.2;


	@Test
	public void testInsertNodeIntoDB() {
		Node node = new Node();
		node.setName(NODE_NAME);
		node.setPositionX(POSITION_X);
		node.setPositionY(POSITION_Y);
		boolean result = DAONode.insertNode(node);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertNodeIntoDBSendingNullAsParameter() {
		assertEquals(ERROR_INSERT, false, DAONode.insertNode(null));
	}

	@Test
	public void testLoadAllNodes() {
		assertNotNull(ERROR_LOAD, DAONode.loadAllNodes());

	}

	@Test
	public void testRemoveOneSpecificnode() {
		Node node = new Node();
		node.setId(1);
		boolean result = DAONode.deleteNode(node);
		assertEquals(ERROR_REMOVING, true, result);
	}

	@Test
	public void testRemoveOneSpecificNodeSendingNullAsParameter() {

		assertEquals(ERROR_REMOVING, false,  DAONode.deleteNode(null));
	}

}
