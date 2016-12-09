package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.Airline;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.User;

public class TestDaoPlane {

	private static final String SERIAL = "SSSS";
	private static final String ERROR_LOAD = "Error load all planes from database";
	private static final String ERROR_INSERT = "Error insert plane into database";

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

		Airline airline = TestDaoAirline.initAirline();
		deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		Plane plane = initPlane(airline, new Date());
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutFabricationDateIntoDB() {
		// Hau lehenengua ingot berez ezin dalako airline bat delete ein foreign
		// key bat baldin badauka plane batek
		deleteAllPlanes();

		Airline airline = TestDaoAirline.initAirline();
		deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		PlaneMaker planeMaker = TestDaoPlaneMaker.initPlaneMaker();
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = TestDaoPlaneModel.initPlaneModel(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		Plane plane = initPlane(airline, planeModel);
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutAirlineIntoDB() {

		PlaneMaker planeMaker = TestDaoPlaneMaker.initPlaneMaker();
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = TestDaoPlaneModel.initPlaneModel(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		Plane plane = initPlane(planeModel, new Date());
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPlanes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Plane()));

	}
	
	@Test
	public void testGetFreePlaneWithPastFlight() {
		Plane plane = initCompletePlane();
		HibernateGeneric.insertObject(plane);
		
		Flight flight = TestDaoFlight.initFlight(plane);
		Date date = new Date();
		date.setTime(date.getTime() - 111111);
		flight.setRealArrivalDate(date);
		HibernateGeneric.insertObject(flight);
		
		Plane resultPlane = HibernateGeneric.getFreePlane();
		assertNotNull("a", resultPlane);
	}
	
	@Test
	public void testGetFreePlaneWithoutFlight() {
		Plane plane = initCompletePlane();
		HibernateGeneric.insertObject(plane);
		
		Plane resultPlane = HibernateGeneric.getFreePlane();
		assertNotNull("a", resultPlane);
	}
	

	private Plane initCompletePlane() {
		
		deleteAllFlights();
		deleteAllPlanes();

		Airline airline = TestDaoAirline.initAirline();
		deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		PlaneMaker planeMaker = TestDaoPlaneMaker.initPlaneMaker();
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = TestDaoPlaneModel.initPlaneModel(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		return initPlane(airline, planeModel, new Date());
	}

	public static Plane initPlane() {
		Plane plane = new Plane();
		plane.setSerial(SERIAL);
		return plane;

	}

	public static Plane initPlane(Airline airline, PlaneModel planeModel, Date date) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setModel(planeModel);
		plane.setFabricationDate(date);

		return plane;

	}

	public static Plane initPlane(Airline airline, Date date) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setFabricationDate(date);

		return plane;

	}

	public static Plane initPlane(PlaneModel planeModel, Date date) {
		Plane plane = initPlane();
		plane.setModel(planeModel);
		plane.setFabricationDate(date);
		return plane;

	}

	public static Plane initPlane(Airline airline, PlaneModel planeModel) {
		Plane plane = initPlane();
		plane.setAirline(airline);
		plane.setModel(planeModel);
		return null;
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
		List<Object> listFlight = HibernateGeneric.loadAllObjects(new Flight());
		for (Object flight : listFlight) {
			HibernateGeneric.deleteObject((Flight) flight);
		}
	}

}
