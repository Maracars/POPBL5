package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.Airline;
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

public class TestDaoFlight {

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
	public void testInsertFlightWithoutStateIntoDB() {
		Flight flight = new Flight();
		boolean result = HibernateGeneric.insertObject(flight);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertCityWithStateIntoDB() {

		boolean result = HibernateGeneric.insertObject(initCompleteFlight());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllFlights() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Flight()));

	}

	@Test
	public void testRemoveOneSpecificFlight() {

		HibernateGeneric.insertObject(initCompleteFlight());
		boolean result = HibernateGeneric.deleteObject((Flight) HibernateGeneric.loadAllObjects(new Flight()).get(0));

		assertEquals(REMOVE_ERROR, true, result);
	}

	private Flight initCompleteFlight() {
		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(PLANEMAKER_NAME);
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = new PlaneModel();
		planeModel.setPlaneMaker(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		deleteAllDelays();
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

		return flight;
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

	private void deleteAllDelays() {
		List<Object> listDelays = HibernateGeneric.loadAllObjects(new Delay());
		for (Object delay : listDelays) {
			HibernateGeneric.deleteObject((Delay) delay);
		}
	}
}
