package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.Airline;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.Route;
import domain.model.User;

public class TestDaoPlane {

	private static final int GATE_NUM = 99;
	private static final double NODE_POS = 6.6666;
	private static final String SERIAL = "SSSS";
	private static final String USERNAME = "Luffy";
	private static final String PASSWORD = "1234";
	private static final String LUFTHANSA = "Lufthansa";
	private static final String ERROR_LOAD = "Error load all plane makers from database";
	private static final String ERROR_INSERT = "Error insert plane maker into database";
	private static final String BOEING = "Boeing";

	@Test
	public void testInsertPlaneWithEverythingIntoDB() {
		// Hau lehenengua ingot berez ezin dalako airline bat delete ein foreign
		// key bat baldin badauka plane batek

		boolean result = HibernateGeneric.insertObject(initCompletePlane());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertPlaneWithoutModelIntoDB() {
		// Hau lehenengua ingot berez ezin dalako airline bat delete ein foreign
		// key bat baldin badauka plane batek
		deleteAllPlanes();

		Node positionNode = new Node();
		positionNode.setPositionX(NODE_POS);
		positionNode.setPositionY(NODE_POS);
		HibernateGeneric.insertObject(positionNode);

		Gate gate = new Gate();
		gate.setPositionNode(positionNode);
		gate.setNumber(GATE_NUM);
		HibernateGeneric.insertObject(gate);

		Route route = new Route();
		route.setArrivalGate(gate);
		route.setDepartureGate(gate);
		HibernateGeneric.insertObject(route);

		Collection<Route> routesList = new ArrayList<Route>();
		routesList.add(route);

		Airline airline = new Airline();
		airline.setBirthDate(new Date());
		airline.setName(LUFTHANSA);
		airline.setPassword(PASSWORD);
		airline.setUsername(USERNAME);
		airline.setRoutesList(routesList);
		deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		Plane plane = new Plane();
		plane.setAirline(airline);
		plane.setFabricationDate(new Date());
		plane.setSerial(SERIAL);
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutFabricationDateIntoDB() {
		// Hau lehenengua ingot berez ezin dalako airline bat delete ein foreign
		// key bat baldin badauka plane batek
		deleteAllPlanes();

		Node positionNode = new Node();
		positionNode.setPositionX(NODE_POS);
		positionNode.setPositionY(NODE_POS);
		HibernateGeneric.insertObject(positionNode);

		Gate gate = new Gate();
		gate.setPositionNode(positionNode);
		gate.setNumber(GATE_NUM);
		HibernateGeneric.insertObject(gate);

		Route route = new Route();
		route.setArrivalGate(gate);
		route.setDepartureGate(gate);
		HibernateGeneric.insertObject(route);

		Collection<Route> routesList = new ArrayList<Route>();
		routesList.add(route);

		Airline airline = new Airline();
		airline.setBirthDate(new Date());
		airline.setName(LUFTHANSA);
		airline.setPassword(PASSWORD);
		airline.setUsername(USERNAME);
		airline.setRoutesList(routesList);
		deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = new PlaneModel();
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		Plane plane = new Plane();
		plane.setAirline(airline);
		plane.setModel(planeModel);
		plane.setSerial(SERIAL);
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutAirlineIntoDB() {

		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = new PlaneModel();
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		Plane plane = new Plane();
		plane.setModel(planeModel);
		plane.setFabricationDate(new Date());
		plane.setSerial(SERIAL);
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPlanes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Plane()));

	}

	private Plane initCompletePlane() {

		deleteAllPlanes();

		Node positionNode = new Node();
		positionNode.setPositionX(NODE_POS);
		positionNode.setPositionY(NODE_POS);
		HibernateGeneric.insertObject(positionNode);

		Gate gate = new Gate();
		gate.setPositionNode(positionNode);
		gate.setNumber(GATE_NUM);
		HibernateGeneric.insertObject(gate);

		Route route = new Route();
		route.setArrivalGate(gate);
		route.setDepartureGate(gate);
		HibernateGeneric.insertObject(route);

		Collection<Route> routesList = new ArrayList<Route>();
		routesList.add(route);

		Airline airline = new Airline();
		airline.setBirthDate(new Date());
		airline.setName(LUFTHANSA);
		airline.setPassword(PASSWORD);
		airline.setUsername(USERNAME);
		airline.setRoutesList(routesList);
		deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = new PlaneModel();
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		Plane plane = new Plane();
		plane.setAirline(airline);
		plane.setFabricationDate(new Date());
		plane.setModel(planeModel);
		plane.setSerial(SERIAL);

		return plane;
	}

	/* For testing, delete all users */
	private void deleteAllUsers() {
		List<Object> listUsers = HibernateGeneric.loadAllObjects(new User());
		for (Object user : listUsers) {
			HibernateGeneric.deleteObject((User) user);
		}
	}

	private void deleteAllPlanes() {
		List<Object> listPlanes = HibernateGeneric.loadAllObjects(new Plane());
		for (Object plane : listPlanes) {
			HibernateGeneric.deleteObject((Plane) plane);
		}
	}

}
