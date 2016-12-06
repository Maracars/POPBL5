package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Lane;
import domain.model.Node;

public class TestDaoLane {

	private static final String ERROR_LOAD = "Error load all lanes from database";
	private static final String INSERT_ERROR = "Error insert lane into database";
	private static final String REMOVE_ERROR = "Error removing one lane from database";
	private static final String LANE_NAME = "Principal lane";

	@Test
	public void testInsertLaneWithoutIntoDB() {
		boolean result = HibernateGeneric.insertObject(initLane());
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertLaneWithNodesIntoDB() {

		boolean result = HibernateGeneric.insertObject(initCompleteLane());

		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllLanes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Lane()));

	}

	@Test
	public void testRemoveOneSpecificLane() {
		Lane lane = initCompleteLane();
		HibernateGeneric.insertObject(lane);
		boolean result = HibernateGeneric.deleteObject(lane);
		assertEquals(REMOVE_ERROR, true, result);
	}

	private Lane initCompleteLane() {
		Node startNode = TestDaoNode.initNode();
		HibernateGeneric.insertObject(startNode);

		Node endNode = TestDaoNode.initNode();
		HibernateGeneric.insertObject(endNode);

		return initLane(startNode, endNode);
	}

	public static Lane initLane() {
		Lane lane = new Lane();
		lane.setName(LANE_NAME);
		return lane;

	}

	public static Lane initLane(Node node1, Node node2) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		return lane;

	}

}
