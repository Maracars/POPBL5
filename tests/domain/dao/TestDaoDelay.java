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

	private static final String PLANEMAKER_NAME = "B";
	private static final String NAME = "JOanes";
	private static final String USERNAME = "naranairapp";
	private static final String PASSWORD = "Nestor123";
	private static final double NODE_POS = 3.2;
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
		boolean result = HibernateGeneric.deleteObject((Delay) HibernateGeneric.loadAllObjects(new Delay()).get(0));

		assertEquals(REMOVE_ERROR, true, result);
	}

	private Delay initCompleteDelay() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(PLANEMAKER_NAME);
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = new PlaneModel();
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		deleteAllFlights();
		deleteAllPlanes();
		deleteAllUsers();

		Airline airline = new Airline();
		airline.setBirthDate(new Date());
		airline.setName(NAME);
		airline.setPassword(PASSWORD);
		airline.setUsername(USERNAME);
		HibernateGeneric.insertObject(airline);

		Plane plane = new Plane();
		plane.setFabricationDate(new Date());
		plane.setModel(planeModel);
		plane.setAirline(airline);
		HibernateGeneric.insertObject(plane);

		Passenger passenger = new Passenger();
		passenger.setBirthDate(new Date());
		passenger.setPassword(PASSWORD_2);
		passenger.setUsername(USERNAME_2);
		HibernateGeneric.insertObject(passenger);

		List<Passenger> passengerList = new ArrayList<>();
		passengerList.add(passenger);

		Node node = new Node();
		node.setPositionX(NODE_POS);
		node.setPositionY(NODE_POS);
		HibernateGeneric.insertObject(node);

		Gate gate = new Gate();
		gate.setPositionNode(node);
		HibernateGeneric.insertObject(gate);

		Route route = new Route();
		route.setArrivalGate(gate);
		route.setDepartureGate(gate);
		HibernateGeneric.insertObject(route);

		Flight flight = new Flight();
		flight.setExpectedArrivalDate(new Date());
		flight.setExpectedDepartureDate(new Date());
		flight.setPlane(plane);
		flight.setPassengerList(passengerList);
		flight.setRealArrivalDate(new Date());
		flight.setRealDepartureDate(new Date());
		flight.setRoute(route);
		HibernateGeneric.insertObject(flight);

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
