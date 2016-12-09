package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Airport;
import domain.model.Lane;
import domain.model.Node;

public class TestDaoLane {

	private static final String ERROR_LOAD = "Error load all lanes from database";
	private static final String INSERT_ERROR = "Error insert lane into database";
	private static final String REMOVE_ERROR = "Error removing one lane from database";

	@Test
	public void testInsertLaneWithoutIntoDB() {
		boolean result = HibernateGeneric.insertObject(Initializer.initLane());
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertLaneWithNodesIntoDB() {

		boolean result = HibernateGeneric.insertObject(Initializer.initCompleteLane());

		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertLaneWithoutNodesIntoDB() {

		Airport airport = Initializer.initAirport();
		HibernateGeneric.insertObject(airport);

		boolean result = HibernateGeneric.insertObject(Initializer.initLane(true, true, airport));

		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertLaneWithoutStatusIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.insertObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.insertObject(endNode);

		Airport airport = Initializer.initAirport();
		HibernateGeneric.insertObject(airport);

		boolean result = HibernateGeneric.insertObject(Initializer.initLane(startNode, endNode, airport, true));

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testInsertLaneWithoutPrincipalIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.insertObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.insertObject(endNode);

		Airport airport = Initializer.initAirport();
		HibernateGeneric.insertObject(airport);

		boolean result = HibernateGeneric.insertObject(Initializer.initLane(startNode, endNode, true, airport));

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testInsertLaneWithoutAirportIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.insertObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.insertObject(endNode);

		boolean result = HibernateGeneric.insertObject(Initializer.initLane(startNode, endNode, true, true));

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testLoadAllLanes() {

		HibernateGeneric.insertObject(Initializer.initCompleteLane());
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Lane()));

	}

	@Test
	public void testRemoveOneSpecificLane() {
		Lane lane = Initializer.initCompleteLane();
		HibernateGeneric.insertObject(lane);
		boolean result = HibernateGeneric.deleteObject(lane);
		assertEquals(REMOVE_ERROR, true, result);
	}


}
