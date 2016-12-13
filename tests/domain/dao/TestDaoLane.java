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

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initLane(startNode, endNode, airport, true));

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

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initLane(startNode, endNode, true, airport));

		assertEquals(INSERT_ERROR, false, result);

	}

	@Test
	public void testInsertLaneWithoutAirportIntoDB() {
		Node startNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(startNode);

		Node endNode = Initializer.initNode();
		HibernateGeneric.saveOrUpdateObject(endNode);

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initLane(startNode, endNode, true, true));

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

		State state = Initializer.initState();
		HibernateGeneric.saveOrUpdateObject(state);

		City city = Initializer.initCity(state);
		HibernateGeneric.saveOrUpdateObject(city);

		Airport airport = Initializer.initAirport(city);
		HibernateGeneric.saveOrUpdateObject(airport);

		Lane lane = Initializer.initLane(startNode, endNode, true, true, airport);
		HibernateGeneric.saveOrUpdateObject(lane);

		assertNotNull(ERROR_GETFREELANES, DAOLane.getFreeLanes(airport.getId()));
	}

}
