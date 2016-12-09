package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Airport;
import domain.model.City;
import domain.model.Lane;
import domain.model.Node;
import domain.model.State;

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
	public void testInsertLaneWithoutNodesIntoDB() {

		Airport airport = TestDaoAirport.initAirport();
		HibernateGeneric.insertObject(airport);

		boolean result = HibernateGeneric.insertObject(initLane(true, true, airport));

		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertLaneWithoutStatusIntoDB() {
		Node startNode = TestDaoNode.initNode();
		HibernateGeneric.insertObject(startNode);

		Node endNode = TestDaoNode.initNode();
		HibernateGeneric.insertObject(endNode);

		Airport airport = TestDaoAirport.initAirport();
		HibernateGeneric.insertObject(airport);

		boolean result = HibernateGeneric.insertObject(initLane(startNode, endNode, airport, true));

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testInsertLaneWithoutPrincipalIntoDB() {
		Node startNode = TestDaoNode.initNode();
		HibernateGeneric.insertObject(startNode);

		Node endNode = TestDaoNode.initNode();
		HibernateGeneric.insertObject(endNode);

		Airport airport = TestDaoAirport.initAirport();
		HibernateGeneric.insertObject(airport);

		boolean result = HibernateGeneric.insertObject(initLane(startNode, endNode, true, airport));

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testInsertLaneWithoutAirportIntoDB() {
		Node startNode = TestDaoNode.initNode();
		HibernateGeneric.insertObject(startNode);

		Node endNode = TestDaoNode.initNode();
		HibernateGeneric.insertObject(endNode);

		boolean result = HibernateGeneric.insertObject(initLane(startNode, endNode, true, true));

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testLoadAllLanes() {

		HibernateGeneric.insertObject(initCompleteLane());
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

		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);

		City city = TestDaoCity.initCity(state);
		HibernateGeneric.insertObject(city);

		Airport airport = TestDaoAirport.initAirport(city);
		HibernateGeneric.insertObject(airport);

		return initLane(startNode, endNode, true, true, airport);
	}

	public static Lane initLane() {
		Lane lane = new Lane();
		lane.setName(LANE_NAME);
		return lane;

	}

	public static Lane initLane(Node node1, Node node2, boolean principal, boolean status, Airport airport) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setPrincipal(principal);
		lane.setStatus(status);
		lane.setAirport(airport);
		return lane;

	}

	public static Lane initLane(Node node1, Node node2, boolean principal, Airport airport) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setAirport(airport);
		lane.setPrincipal(principal);

		return lane;

	}

	public static Lane initLane(Node node1, Node node2, Airport airport, boolean status) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setAirport(airport);
		lane.setStatus(status);

		return lane;

	}

	private Lane initLane(Node node1, Node node2, boolean status, boolean principal) {
		Lane lane = initLane();
		lane.setEndNode(node1);
		lane.setStartNode(node2);
		lane.setStatus(status);
		lane.setPrincipal(principal);

		return lane;
	}

	private Lane initLane(boolean status, boolean principal, Airport airport) {
		Lane lane = initLane();
		lane.setStatus(status);
		lane.setPrincipal(principal);
		lane.setAirport(airport);

		return lane;
	}

}
