package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import domain.model.Lane;
import domain.model.Node;

public class TestDaoLane {

	private static final String ERROR_LOAD = "Error load all lanes from database";
	private static final String INSERT_ERROR = "Error insert lane into database";
	private static final String REMOVE_ERROR = "Error removing one lane from database";
	private static final String LANE_NAME = "Principal lane";
	private static final String NODE1_NAME = "Node A";
	private static final String NODE2_NAME = "Node B";
	private static final double POSITION1 = 1.1;
	private static final double POSITION2 = 2.2;

	@Test
	public void testInsertLaneWithoutIntoDB() {
		Lane lane = new Lane();
		lane.setName(LANE_NAME);
		boolean result = HibernateGeneric.insertObject(lane);
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
		Node startNode = new Node();
		Node endNode = new Node();
		List<Object> nodeList = null;
		Lane lane = new Lane();

		startNode.setName(NODE1_NAME);
		startNode.setPositionX(POSITION1);
		startNode.setPositionY(POSITION2);

		HibernateGeneric.insertObject(startNode);

		endNode.setName(NODE2_NAME);
		endNode.setPositionX(POSITION2);
		endNode.setPositionY(POSITION1);

		HibernateGeneric.insertObject(endNode);

		nodeList = HibernateGeneric.loadAllObjects(new Node());

		lane.setName(LANE_NAME);
		lane.setStartNode((Node) nodeList.get(0));
		lane.setEndNode((Node) nodeList.get(1));

		return lane;
	}

}
