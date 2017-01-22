package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Address;
import domain.model.Airport;
import domain.model.Lane;
import domain.model.Node;

public class TestDaoLane {

	private static final int LENGTH = 10;
	private static final int START = 0;
	private static final String ORDER_COL_DIR = "asc";
	private static final String ORDER_COL_NAME = "name";
	private static final String PRINCIPAL = "PRINCIPAL";
	private static final String ERROR_LOAD = "Error load all lanes from database";
	private static final String INSERT_ERROR = "Error insert lane into database";
	private static final String REMOVE_ERROR = "Error removing one lane from database";
	private static final String ERROR_GETFREELANES = "Error loading free lanes from database";
	private static final String ERROR_LOAD_TABLE_LANES = "Error loading lanes for the table";

	@Test
	public void testInsertLaneWithoutIntoDB() {
		boolean result = HibernateGeneric.saveObject(Initializer.initLane());
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertLaneWithNodesIntoDB() {

		boolean result = HibernateGeneric.saveObject(Initializer.initCompleteLane());

		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertLaneWithoutNodesIntoDB() {

		Airport airport = Initializer.initAirport();
		HibernateGeneric.saveObject(airport);

		boolean result = HibernateGeneric.saveObject(Initializer.initLane(true, PRINCIPAL, airport));

		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertLaneWithoutStatusIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.saveObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.saveObject(endNode);

		Airport airport = Initializer.initAirport();
		HibernateGeneric.saveObject(airport);

		Lane lane = Initializer.initLane(startNode, endNode, airport, true);

		boolean result = HibernateGeneric.saveObject(lane);

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testInsertLaneWithoutPrincipalIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.saveObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.saveObject(endNode);

		Airport airport = Initializer.initAirport();
		HibernateGeneric.saveObject(airport);

		Lane lane = Initializer.initLane(startNode, endNode, PRINCIPAL, airport);

		boolean result = HibernateGeneric.saveObject(lane);

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testInsertLaneWithoutAirportIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.saveObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.saveObject(endNode);

		Lane lane = Initializer.initLane(startNode, endNode, true, PRINCIPAL);

		boolean result = HibernateGeneric.saveObject(lane);

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testLoadAllLanes() {

		HibernateGeneric.saveObject(Initializer.initCompleteLane());
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Lane()));

	}

	@Test
	public void testRemoveOneSpecificLane() {
		Lane lane = Initializer.initCompleteLane();
		HibernateGeneric.saveObject(lane);
		boolean result = HibernateGeneric.deleteObject(lane);
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testGetFreeLanes() {

		Node startNode = Initializer.initNode();
		HibernateGeneric.saveObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.saveObject(endNode);

		Address address = Initializer.initAddress();
		HibernateGeneric.saveObject(address);

		Node positionNode = Initializer.initNode();
		HibernateGeneric.saveObject(positionNode);

		Airport airport = Initializer.initAirport(address, positionNode);
		HibernateGeneric.saveObject(airport);

		Lane lane = Initializer.initLane(startNode, endNode, PRINCIPAL, true, airport);
		HibernateGeneric.saveObject(lane);

		assertNotNull(ERROR_GETFREELANES, DAOLane.getFreeLanes(airport.getId()));
	}

	@Test
	public void testLoadLanesForTable() {
		Lane lane = Initializer.initCompleteLane();
		HibernateGeneric.saveObject(lane);

		assertNotNull(ERROR_LOAD_TABLE_LANES, 
				DAOLane.loadLanesForTable(ORDER_COL_NAME, ORDER_COL_DIR, START, LENGTH));
	}

}
