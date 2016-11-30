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
		boolean result = DAOLane.insertLane(lane);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertLaneWithNodesIntoDB() {
		Node startNode = new Node();
		Node endNode = new Node();
		List<Node> nodeList = null;
		Lane lane = new Lane();

		startNode.setName(NODE1_NAME);
		startNode.setPositionX(POSITION1);
		startNode.setPositionY(POSITION2);

		DAONode.insertNode(startNode);

		endNode.setName(NODE2_NAME);
		endNode.setPositionX(POSITION2);
		endNode.setPositionY(POSITION1);

		DAONode.insertNode(endNode);

		nodeList = DAONode.loadAllNodes();
		
		lane.setName(LANE_NAME);
		lane.setStartNode(nodeList.get(0));
		lane.setEndNode(nodeList.get(1));

		boolean result = DAOLane.insertLane(lane);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllNodes() {
		assertNotNull(ERROR_LOAD, DAOLane.loadAllLanes());

	}

	@Test
	public void testInsertNullNodeIntoDB() {
		assertEquals(INSERT_ERROR, false, DAOLane.insertLane(null));
	}

	@Test
	public void testRemoveOneSpecificLane() {
		Node startNode = new Node();
		Node endNode = new Node();
		List<Node> nodeList = null;
		Lane lane = new Lane();

		startNode.setName(NODE1_NAME);
		startNode.setPositionX(POSITION1);
		startNode.setPositionY(POSITION2);

		DAONode.insertNode(startNode);

		endNode.setName(NODE2_NAME);
		endNode.setPositionX(POSITION2);
		endNode.setPositionY(POSITION1);

		DAONode.insertNode(endNode);

		nodeList = DAONode.loadAllNodes();
		System.out.println(nodeList.get(0));
		System.out.println(nodeList.get(1));
		lane.setName(LANE_NAME);
		lane.setStartNode(nodeList.get(0));
		lane.setEndNode(nodeList.get(1));

		DAOLane.insertLane(lane);
		boolean result = DAOLane.deleteLane(lane);
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testRemoveOneNullCity() {
		assertEquals(REMOVE_ERROR, false, DAOCity.deleteCity(null));
	}

}
