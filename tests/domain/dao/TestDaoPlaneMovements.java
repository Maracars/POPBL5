package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.Airline;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.PlaneMovement;
import domain.model.Route;
import domain.model.User;

public class TestDaoPlaneMovements {

	private static final double SPEED = 0.0;
	private static final double POSITION = 3.2;
	private static final double DIRECTION = 3.2;
	private static final String SERIAL = "SS88";
	private static final String ERROR_REMOVING = "Error removing one plane maker from database";
	private static final String ERROR_LOAD = "Error load all plane makers from database";
	private static final String ERROR_INSERT = "Error insert plane maker into database";
	private static final String BOEING = "Boeing";
	private static final int GATE_NUM = 99;
	private static final double NODE_POS = 6.6666;
	private static final String USERNAME = "Luffy";
	private static final String PASSWORD = "1234";
	private static final String LUFTHANSA = "Lufthansa";

	@Test
	public void testInsertPlaneModelWithNothingIntoDB() {
		PlaneModel planeModel = new PlaneModel();
		planeModel.setName(SERIAL);
		boolean result = HibernateGeneric.insertObject(planeModel);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneModelWithPlaneMakerIntoDB() {

		boolean result = HibernateGeneric.insertObject(initCompletePlaneMovements());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPlaneMovements() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new PlaneMovement()));

	}

	@Test
	public void testRemoveOneSpecificPlaneMovement() {

		boolean result = HibernateGeneric.deleteObject(initCompletePlaneMovements());
		assertEquals(ERROR_REMOVING, true, result);
	}

	private PlaneMovement initCompletePlaneMovements() {
		deleteAllPlaneMovements();
		deleteAllFlights();
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
		HibernateGeneric.insertObject(plane);
		
		PlaneMovement planeMovement = new PlaneMovement();
		planeMovement.setDirectionX(DIRECTION);
		planeMovement.setDirectionY(DIRECTION);
		planeMovement.setPositionX(POSITION);
		planeMovement.setPositionY(POSITION);
		planeMovement.setSpeed(SPEED);
		planeMovement.setPlane(plane);
		return planeMovement;
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
	private void deleteAllFlights() {
		List<Object> listFligths = HibernateGeneric.loadAllObjects(new Flight());
		for (Object flight : listFligths) {
			HibernateGeneric.deleteObject((Flight) flight);
		}
	}
	
	private void deleteAllPlaneMovements() {
		List<Object> planeMovementList = HibernateGeneric.loadAllObjects(new PlaneMovement());
		for (Object planeMovements : planeMovementList) {
			HibernateGeneric.deleteObject((PlaneMovement) planeMovements);
		}
	}


}
