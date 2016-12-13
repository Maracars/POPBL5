package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import domain.model.Airline;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.PlaneStatus;

public class TestDaoPlane {

	private static final int ADDED_TIME = 111111;
	private static final String ERROR_LOADING_PLANE_WITHOUT_FLIGHT = "ERROR LOAD PLANE WITHOUT FLIGHT";
	private static final String ERROR_LOAD_FREE_PLANE_WITH_PAST_FLIGHT = "ERROR LOAD FREE PLANE WITH PAST FLIGHT";
	private static final String ERROR_LOAD = "Error load all planes from database";
	private static final String ERROR_INSERT = "Error insert plane into database";

	@Test
	public void testInsertPlaneWithEverythingIntoDB() {

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initCompletePlane());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testselectPlaneNeedToReviseDB() {

		Plane planeExpected = Initializer.initCompletePlane();

		HibernateGeneric.saveOrUpdateObject(planeExpected);
		Plane planeResult = DAOPlane.selectPlaneNeedToRevise();
		assertNotNull(ERROR_INSERT, planeResult);
	}

	@Test
	public void testInsertPlaneWithoutModelIntoDB() {

		Airline airline = Initializer.initAirline();
		HibernateGeneric.saveOrUpdateObject(airline);

		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		HibernateGeneric.saveOrUpdateObject(planeStatus);

		Plane plane = Initializer.initPlane(airline, new Date(), planeStatus);
		boolean result = HibernateGeneric.saveOrUpdateObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutFabricationDateIntoDB() {

		Airline airline = Initializer.initAirline();
		HibernateGeneric.saveOrUpdateObject(airline);

		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.saveOrUpdateObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.saveOrUpdateObject(planeModel);

		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		HibernateGeneric.saveOrUpdateObject(planeStatus);

		Plane plane = Initializer.initPlane(airline, planeModel, planeStatus);
		boolean result = HibernateGeneric.saveOrUpdateObject(plane);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertPlaneWithoutAirlineIntoDB() {

		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.saveOrUpdateObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.saveOrUpdateObject(planeModel);

		PlaneStatus planeStatus = Initializer.initPlaneStatus();
		HibernateGeneric.saveOrUpdateObject(planeStatus);

		Plane plane = Initializer.initPlane(planeModel, new Date(), planeStatus);
		boolean result = HibernateGeneric.saveOrUpdateObject(plane);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertPlaneWithoutPlaneStatusIntoDB() {

		Airline airline = Initializer.initAirline();
		HibernateGeneric.saveOrUpdateObject(airline);

		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.saveOrUpdateObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.saveOrUpdateObject(planeModel);

		Plane plane = Initializer.initPlane(planeModel, new Date(), airline);
		boolean result = HibernateGeneric.saveOrUpdateObject(plane);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void getSoonArrivingPlanesFromDB() {
		/*Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.saveOrUpdateObject(plane);
*/
		Flight flight = Initializer.initCompleteFlight();
		Date date = new Date();
		date.setTime(date.getTime() + ADDED_TIME);
		flight.setExpectedArrivalDate(date);
		HibernateGeneric.saveOrUpdateObject(flight);
		int id = flight.getRoute().getArrivalGate().getTerminal().getAirport().getId();

		assertNotNull(ERROR_LOAD, DAOPlane.getArrivingPlanesSoon(id));

	}

	@Test
	public void getSoonDeparturingPlanesFromDB() {
		Plane plane = Initializer.initCompletePlane();
		plane.getPlaneStatus().setTechnicalStatus("OK");
		plane.getPlaneStatus().setPositionStatus("ON AIRPORT");
		HibernateGeneric.saveOrUpdateObject(plane);

		Flight flight = Initializer.initFlight(plane);
		Date date = new Date();
		date.setTime(date.getTime() + ADDED_TIME);
		flight.setExpectedDepartureDate(date);
		HibernateGeneric.saveOrUpdateObject(flight);

		Airport airport = Initializer.initCompleteAirport();
		HibernateGeneric.saveOrUpdateObject(airport);

		assertNotNull(ERROR_LOAD, DAOPlane.getDeparturingPlanesSoon(airport.getId()));

	}

	@Test
	public void testLoadAllPlanes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Plane()));

	}

	@Test
	public void testGetFreePlaneWithPastFlight() {
		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.saveOrUpdateObject(plane);

		Flight flight = Initializer.initFlight(plane);
		Date date = new Date();
		date.setTime(date.getTime() - ADDED_TIME);
		flight.setRealArrivalDate(date);
		HibernateGeneric.saveOrUpdateObject(flight);

		Plane resultPlane = DAOPlane.getFreePlane();
		assertNotNull(ERROR_LOAD_FREE_PLANE_WITH_PAST_FLIGHT, resultPlane);
	}

	@Test
	public void testGetFreePlaneWithoutFlight() {
		Plane plane = Initializer.initCompletePlane();
		HibernateGeneric.saveOrUpdateObject(plane);

		Plane resultPlane = DAOPlane.getFreePlane();
		assertNotNull(ERROR_LOADING_PLANE_WITHOUT_FLIGHT, resultPlane);
	}

}
