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
	private static final String ERROR_REMOVING = "Error removing one plane maker from database";
	private static final String ERROR_LOAD = "Error load all plane makers from database";
	private static final String ERROR_INSERT = "Error insert plane maker into database";
	@Test
	public void testInsertPlaneMovementsWithNothingIntoDB() {
		PlaneMovement planeMovement = new PlaneMovement();
		boolean result = HibernateGeneric.insertObject(planeMovement);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneMovementlWithEverythingIntoDB() {

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

		Node positionNode = TestDaoNode.initNode();
		HibernateGeneric.insertObject(positionNode);

		Gate gate = TestDaoGate.initGate(positionNode);
		HibernateGeneric.insertObject(gate);

		Route route = TestDaoRoute.initRoute(gate, gate);
		HibernateGeneric.insertObject(route);

		Collection<Route> routesList = new ArrayList<Route>();
		routesList.add(route);

		Airline airline = TestDaoAirline.initAirline(routesList);
		deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		PlaneMaker planeMaker = TestDaoPlaneMaker.initPlaneMaker();
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = TestDaoPlaneModel.initPlaneModel(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		Plane plane = TestDaoPlane.initPlane(airline, planeModel, new Date());
		HibernateGeneric.insertObject(plane);

		PlaneMovement planeMovement = initPlaneMovement(plane);
		return planeMovement;
	}
	
	public static PlaneMovement initPlaneMovement() {
		PlaneMovement planeMovement = new PlaneMovement();
		planeMovement.setDirectionX(DIRECTION);
		planeMovement.setDirectionY(DIRECTION);
		planeMovement.setPositionX(POSITION);
		planeMovement.setPositionY(POSITION);
		planeMovement.setSpeed(SPEED);
		return planeMovement;
		
	}
	public static PlaneMovement initPlaneMovement(Plane plane) {
		PlaneMovement planeMovement = initPlaneMovement();
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
