package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.Airline;
import domain.model.Airport;
import domain.model.City;
import domain.model.Delay;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Passenger;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.PlaneMovement;
import domain.model.Route;
import domain.model.State;
import domain.model.Terminal;
import domain.model.User;

public class TestDaoFlight {

	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";
	private static final String PASSWORD_2 = "Joxantonio";
	private static final String USERNAME_2 = "Nestor";

	@Test
	public void testInsertFlightWithNothingIntoDB() {
		Flight flight = new Flight();
		boolean result = HibernateGeneric.insertObject(flight);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertFlightDB() {

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
		PlaneMaker planeMaker = TestDaoPlaneMaker.initPlaneMaker();
		HibernateGeneric.insertObject(planeMaker);

		PlaneModel planeModel = TestDaoPlaneModel.initPlaneModel(planeMaker);
		HibernateGeneric.insertObject(planeModel);

		deleteAllPlaneMovements();
		deleteAllDelays();
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

		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);
		
		City city = TestDaoCity.initCity(state);
		HibernateGeneric.insertObject(city);
		
		Airport airport = TestDaoAirport.initAirport(city);
		HibernateGeneric.insertObject(airport);

		Terminal terminal = TestDaoTerminal.initTerminal(airport);
		HibernateGeneric.insertObject(terminal);

		Gate gate = TestDaoGate.initGate(node, terminal);
		HibernateGeneric.insertObject(gate);

		Route route = TestDaoRoute.initRoute(gate, gate);

		HibernateGeneric.insertObject(route);

		return initFlight(plane, passengerList);
	}

	public static Flight initFlight(Plane plane, Route route) {
		Flight flight = initFlight(plane);
		flight.setRoute(route);
		return flight;

	}

	public static Flight initFlight(Plane plane, Route route, List<Passenger> passengerList) {
		Flight flight = initFlight(plane);
		flight.setRoute(route);
		flight.setPassengerList(passengerList);
		return flight;

	}

	public static Flight initFlight(Plane plane, List<Passenger> passengerList) {
		Flight flight = initFlight(plane);
		flight.setPassengerList(passengerList);
		return flight;

	}

	public static Flight initFlight(Plane plane) {
		Flight flight = new Flight();
		flight.setExpectedArrivalDate(new Date());
		flight.setExpectedDepartureDate(new Date());
		flight.setPlane(plane);
		flight.setRealArrivalDate(new Date());
		flight.setRealDepartureDate(new Date());

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

	private void deleteAllPlaneMovements() {
		List<Object> listPlaneMovements = HibernateGeneric.loadAllObjects(new PlaneMovement());
		for (Object planeMovement : listPlaneMovements) {
			HibernateGeneric.deleteObject((PlaneMovement) planeMovement);
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
