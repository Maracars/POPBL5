package domain.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.Airline;
import domain.model.City;
import domain.model.Delay;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Passenger;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.Route;
import domain.model.User;

public class TestDaoDelay {

	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";
	private static final String PASSWORD_2 = "Joxantonio";
	private static final String USERNAME_2 = "Nestor";

	@Test
	public void testInsertDelayWithEverythingIntoDB() {

		boolean result = HibernateGeneric.insertObject(initCompleteDelay());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertDelayWithoutFlightIntoDB() {

		Delay delay = new Delay();
		boolean result = HibernateGeneric.insertObject(delay);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testLoadAllDelays() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new City()));

	}

	@Test
	public void testRemoveOneSpecificDelay() {

		HibernateGeneric.insertObject(initCompleteDelay());
		boolean result = HibernateGeneric.deleteObject(
				(Delay) HibernateGeneric.loadAllObjects(new Delay()).get(0));

		assertEquals(REMOVE_ERROR, true, result);
	}

	private Delay initCompleteDelay() {
		PlaneMaker planeMaker = TestDaoPlaneMaker.initPlaneMaker();
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = TestDaoPlaneModel.initPlaneModel(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		deleteAllFlights();
		deleteAllPlanes();
		deleteAllUsers();

		Airline airline = TestDaoAirline.initAirline();
		HibernateGeneric.insertObject(airline);

		Plane plane = TestDaoPlane.initPlane(airline, planeModel, new Date());
		HibernateGeneric.insertObject(plane);

		Passenger passenger = TestDaoPassenger.initPassenger(USERNAME_2, PASSWORD_2, new Date());

		HibernateGeneric.insertObject(passenger);

		List<Passenger> passengerList = new ArrayList<>();
		passengerList.add(passenger);

		Node node = TestDaoNode.initNode();
		HibernateGeneric.insertObject(node);

		Gate gate = TestDaoGate.initGate(node);
		HibernateGeneric.insertObject(gate);

		Route route = TestDaoRoute.initRoute(gate, gate);
		HibernateGeneric.insertObject(route);

		Flight flight = TestDaoFlight.initFlight(plane, route, passengerList);
		HibernateGeneric.insertObject(flight);

		return initDelay(flight);
	}

	public static Delay initDelay(Flight flight) {
		Delay delay = new Delay();
		delay.setAffectedFlight(flight);
		return delay;

	}

	/* For testing, delete all users */
	private void deleteAllUsers() {
		List<Object> listUsers = HibernateGeneric.loadAllObjects(new User());
		for (Object user : listUsers) {
			HibernateGeneric.deleteObject((User) user);
		}
	}

	private void deleteAllFlights() {
		List<Object> listFlight = HibernateGeneric.loadAllObjects(new Flight());
		for (Object flight : listFlight) {
			HibernateGeneric.deleteObject((Flight) flight);
		}
	}

	private void deleteAllPlanes() {
		List<Object> listPlanes = HibernateGeneric.loadAllObjects(new Plane());
		for (Object plane : listPlanes) {
			HibernateGeneric.deleteObject((Plane) plane);
		}
	}

}
