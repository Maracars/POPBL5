package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import domain.model.Airline;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.PlaneStatus;

public class TestDaoPlane {

	private static final String ERROR_LOAD = "Error load all planes from database";
	private static final String ERROR_INSERT = "Error insert plane into database";

	@Test
	public void testInsertPlaneWithEverythingIntoDB() {

		boolean result = HibernateGeneric.insertObject(Initializer.initCompletePlane());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testselectPlaneNeedToReviseDB() {

		Plane planeExpected = Initializer.initCompletePlane();

		HibernateGeneric.insertObject(planeExpected);
		Plane planeResult = HibernateGeneric.selectPlaneNeedToRevise();
		assertEquals(ERROR_INSERT, planeExpected.getId(), planeResult.getId());
	}

	@Test
	public void testInsertPlaneWithoutModelIntoDB() {

		Initializer.deleteAllPlanes();

		Airline airline = Initializer.initAirline();
		Initializer.deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		HibernateGeneric.insertObject(planeStatus);

		Plane plane = Initializer.initPlane(airline, new Date(), planeStatus);
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutFabricationDateIntoDB() {

		Initializer.deleteAllPlanes();

		Airline airline = Initializer.initAirline();
		Initializer.deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		HibernateGeneric.insertObject(planeStatus);

		Plane plane = Initializer.initPlane(airline, planeModel, planeStatus);
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutAirlineIntoDB() {

		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		HibernateGeneric.insertObject(planeStatus);

		Plane plane = Initializer.initPlane(planeModel, new Date(), planeStatus);
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertPlaneWithoutPlaneStatusIntoDB() {

		Initializer.deleteAllPlanes();

		Airline airline = Initializer.initAirline();
		Initializer.deleteAllUsers();
		HibernateGeneric.insertObject(airline);

		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		Plane plane = Initializer.initPlane(planeModel, new Date(), airline);
		boolean result = HibernateGeneric.insertObject(plane);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void getSoonArrivingPlanesFromDB() {
		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.insertObject(plane);

		Flight flight = Initializer.initFlight(plane);
		Date date = new Date();
		date.setTime(date.getTime() + 111111);
		flight.setExpectedArrivalDate(date);
		HibernateGeneric.insertObject(flight);

		assertNotNull(ERROR_LOAD, HibernateGeneric.getArrivingPlanesSoon());

	}

	@Test
	public void getSoonDeparturingPlanesFromDB() {
		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.insertObject(plane);

		Flight flight = Initializer.initFlight(plane);
		Date date = new Date();
		date.setTime(date.getTime() + 111111);
		flight.setExpectedDepartureDate(date);
		HibernateGeneric.insertObject(flight);

		assertNotNull(ERROR_LOAD, HibernateGeneric.getDeparturingPlanesSoon());

	}

	@Test
	public void testLoadAllPlanes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Plane()));

	}

	@Test
	public void testGetFreePlaneWithPastFlight() {
		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.insertObject(plane);

		Flight flight = Initializer.initFlight(plane);
		Date date = new Date();
		date.setTime(date.getTime() - 111111);
		flight.setRealArrivalDate(date);
		HibernateGeneric.insertObject(flight);

		Plane resultPlane = HibernateGeneric.getFreePlane();
		assertNotNull("a", resultPlane);
	}

	@Test
	public void testGetFreePlaneWithoutFlight() {
		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.insertObject(plane);

		Plane resultPlane = HibernateGeneric.getFreePlane();
		assertNotNull("a", resultPlane);
	}

}
