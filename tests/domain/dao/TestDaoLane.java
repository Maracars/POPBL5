package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Address;
import domain.model.Airport;
import domain.model.Lane;
import domain.model.Node;

public class TestDaoLane {

	private static final String ERROR_LOAD = "Error load all lanes from database";
	private static final String INSERT_ERROR = "Error insert lane into database";
	private static final String REMOVE_ERROR = "Error removing one lane from database";
	private static final String ERROR_GETFREELANES = "Error loading free lanes from database";

	@Test
	public void testInsertLaneWithoutIntoDB() {
		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initLane());
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertLaneWithNodesIntoDB() {

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initCompleteLane());

		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertLaneWithoutNodesIntoDB() {

		Airport airport = Initializer.initAirport();
		HibernateGeneric.saveOrUpdateObject(airport);

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initLane(true, true, airport));

		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertLaneWithoutStatusIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(endNode);

		Airport airport = Initializer.initAirport();
		HibernateGeneric.saveOrUpdateObject(airport);
		
		Lane lane = Initializer.initLane(startNode, endNode, airport, true);

		boolean result = HibernateGeneric.saveOrUpdateObject(lane);

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testInsertLaneWithoutPrincipalIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(endNode);

		Airport airport = Initializer.initAirport();
		HibernateGeneric.saveOrUpdateObject(airport);
		
		Lane lane = Initializer.initLane(startNode, endNode, true, airport);


		boolean result = HibernateGeneric.saveOrUpdateObject(lane);

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testInsertLaneWithoutAirportIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(endNode);
		
		Lane lane = Initializer.initLane(startNode, endNode, true, true);


		boolean result = HibernateGeneric.saveOrUpdateObject(lane);

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testLoadAllLanes() {

		HibernateGeneric.saveOrUpdateObject(Initializer.initCompleteLane());
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Lane()));

	}

	@Test
	public void testRemoveOneSpecificLane() {
		Lane lane = Initializer.initCompleteLane();
		HibernateGeneric.saveOrUpdateObject(lane);
		boolean result = HibernateGeneric.deleteObject(lane);
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testGetFreeLanes() {


		Node startNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(endNode);

		Address address = Initializer.initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport airport = Initializer.initAirport(address);
		HibernateGeneric.saveOrUpdateObject(airport);

		Lane lane = Initializer.initLane(startNode, endNode, true, true, airport);
		HibernateGeneric.saveOrUpdateObject(lane);

		assertNotNull(ERROR_GETFREELANES, DAOLane.getFreeLanes(airport.getId()));
	}

}
