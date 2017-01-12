package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import domain.model.Airport;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.PlaneMovement;
import domain.model.PlaneStatus;
import domain.model.users.Airline;
import domain.model.users.User;

public class TestDaoPlane {

	private static final int LENGTH = 10;
	private static final int START = 0;
	private static final String ORDER_COL_DIR = "asc";
	private static final String ORDER_COL_NAME = "serial";
	private static final int ADDED_TIME = 111111;
	private static final String ERROR_LOADING_PLANE_WITHOUT_FLIGHT = "ERROR LOAD PLANE WITHOUT FLIGHT";
	private static final String ERROR_LOADING_ALL_PLANES_AIRLINE = "ERROR LOAD ALL PLANES FROM AIRLINE";
	private static final String ERROR_LOADING_ALL_PLANES_AIRLINE_SERIAL = "ERROR LOAD ALL PLANES FROM AIRLINE AND SERIAL";
	private static final String ERROR_LOADING_ALL_PLANES_AIRLINE_TABLE = "ERROR LOAD ALL PLANES FROM AIRLINE FOR THE TABLE";
	private static final String ERROR_LOAD_FREE_PLANE_WITH_PAST_FLIGHT = "ERROR LOAD FREE PLANE WITH PAST FLIGHT";
	private static final String ERROR_LOAD = "Error load all planes from database";
	private static final String ERROR_INSERT = "Error insert plane into database";

	@Test
	public void testInsertPlaneWithEverythingIntoDB() {

		boolean result = HibernateGeneric.saveObject(Initializer.initCompletePlane());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testselectPlaneNeedToReviseDB() {

		Plane planeExpected = Initializer.initCompletePlane();

		HibernateGeneric.saveObject(planeExpected);
		Plane planeResult = DAOPlane.selectPlaneNeedToRevise();
		assertNotNull(ERROR_INSERT, planeResult);
	}

	@Test
	public void testInsertPlaneWithoutModelIntoDB() {

		Airline airline = Initializer.initAirline();
		HibernateGeneric.saveObject(airline);

		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		HibernateGeneric.saveObject(planeStatus);

		Plane plane = Initializer.initPlane(airline, new Date(), planeStatus);
		boolean result = HibernateGeneric.saveObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutFabricationDateIntoDB() {

		Airline airline = Initializer.initAirline();
		HibernateGeneric.saveObject(airline);

		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		HibernateGeneric.saveObject(planeStatus);

		Plane plane = Initializer.initPlane(airline, planeModel, planeStatus);
		boolean result = HibernateGeneric.saveObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutAirlineIntoDB() {

		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		HibernateGeneric.saveObject(planeStatus);

		PlaneMovement planeMovement = Initializer.initPlaneMovement();
		HibernateGeneric.saveObject(planeMovement);

		Plane plane = Initializer.initPlane(planeModel, new Date(), planeStatus, planeMovement);
		boolean result = HibernateGeneric.saveObject(plane);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertPlaneWithoutPlaneStatusIntoDB() {

		Airline airline = Initializer.initAirline();
		HibernateGeneric.saveObject(airline);

		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.saveObject(planeModel);

		Plane plane = Initializer.initPlane(planeModel, new Date(), airline);
		boolean result = HibernateGeneric.saveObject(plane);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void getSoonArrivingPlanesFromDB() {
		/*
		 * Plane plane = Initializer.initCompletePlane();
		 * HibernateGeneric.saveOrUpdateObject(plane);
		 */
		Flight flight = Initializer.initCompleteFlight();
		Date date = new Date();
		date.setTime(date.getTime() + ADDED_TIME);
		flight.setExpectedArrivalDate(date);
		HibernateGeneric.saveObject(flight);
		int id = flight.getRoute().getArrivalTerminal().getAirport().getId();

		assertNotNull(ERROR_LOAD, DAOPlane.getArrivingFlightsSoon(id));

	}

	@Test
	public void getSoonDeparturingPlanesFromDB() {
		Plane plane = Initializer.initCompletePlane();
		plane.getPlaneStatus().setTechnicalStatus("OK");
		plane.getPlaneStatus().setPositionStatus("ON AIRPORT");
		HibernateGeneric.saveObject(plane);

		Flight flight = Initializer.initFlight(plane);
		Date date = new Date();
		date.setTime(date.getTime() + ADDED_TIME);
		flight.setExpectedDepartureDate(date);
		HibernateGeneric.saveObject(flight);

		Airport airport = Initializer.initCompleteAirport();
		HibernateGeneric.saveObject(airport);

		assertNotNull(ERROR_LOAD, DAOPlane.getDeparturingFlightsSoon(airport.getId()));

	}

	@Test
	public void testLoadAllPlanes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Plane()));

	}

	@Test
	public void testGetFreePlaneWithPastFlight() {
		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.saveObject(plane);

		Flight flight = Initializer.initFlight(plane);
		Date date = new Date();
		date.setTime(date.getTime() - ADDED_TIME);
		flight.setRealArrivalDate(date);
		HibernateGeneric.saveObject(flight);

		Plane resultPlane = DAOPlane.getFreePlane();
		assertNotNull(ERROR_LOAD_FREE_PLANE_WITH_PAST_FLIGHT, resultPlane);
	}

	@Test
	public void testGetFreePlaneWithoutFlight() {
		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.saveObject(plane);

		Plane resultPlane = DAOPlane.getFreePlane();
		assertNotNull(ERROR_LOADING_PLANE_WITHOUT_FLIGHT, resultPlane);
	}
	
	@Test
	public void testLoadAllAirplanesFromAirline(){
		Plane plane = Initializer.initCompletePlane();
		
		HibernateGeneric.saveObject(plane);
		
		assertNotNull(ERROR_LOADING_ALL_PLANES_AIRLINE, DAOPlane.loadAllAirplanesFromAirline(plane.getAirline().getId()));
	}
	
	@Test
	public void testLoadAirplanesForTable(){
		Plane plane = Initializer.initCompletePlane();
		
		HibernateGeneric.saveObject(plane);
		
		assertNotNull(ERROR_LOADING_ALL_PLANES_AIRLINE_TABLE, DAOPlane.loadAirplanesForTable(plane.getAirline().getId(), ORDER_COL_NAME, ORDER_COL_DIR, START, LENGTH));

	}
	
	@Test
	public void testLoadAirplaneWithSerial(){
		
		Plane plane = Initializer.initCompletePlane();
		plane.setSerial("AAAAA");
		
		HibernateGeneric.saveObject(plane);
		
		assertNotNull(ERROR_LOADING_ALL_PLANES_AIRLINE_SERIAL, DAOPlane.loadAirplaneWithSerial(plane.getAirline().getId(), plane.getSerial()));
		
	}
	

}
