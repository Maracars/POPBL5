package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
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
import domain.model.Plane;
import domain.model.PlaneMovement;
import domain.model.Route;
import domain.model.State;
import domain.model.Terminal;
import domain.model.User;

public class TestDaoAirline {

	private static final String NARANAIR = "Naranair";
	private static final String ERROR_LOAD = "Error load all airlines from database";
	private static final String INSERT_ERROR = "Error insert airline into database";
	private static final String REMOVE_ERROR = "Error removing one airline from database";
	private static final String USERNAME = "naranair";
	private static final String PASSWORD = "Nestor123";

	@Test
	public void testInsertAirlineWithoutRoutes() {
		Airline airline = initAirline();
		deleteAllUsers();

		boolean result = HibernateGeneric.insertObject(airline);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertAirlineWithRoutes() {

		assertEquals(INSERT_ERROR, true, HibernateGeneric.insertObject(initCompleteAirline()));
	}

	@Test
	public void testLoadAllAirlines() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airline()));

	}

	@Test
	public void testRemoveOneSpecificAirline() {
		/* Create airline */

		HibernateGeneric.insertObject(initCompleteAirline());
		Airline airline = (Airline) HibernateGeneric.loadAllObjects(new Airline()).get(0);
		boolean result = HibernateGeneric.deleteObject(airline);
		assertEquals(REMOVE_ERROR, true, result);
	}

	private Airline initCompleteAirline() {
		deleteAllPlaneMovements();
		deleteAllDelays();
		deleteAllFlights();
		deleteAllPlanes();
		deleteAllUsers();

		Node n1 = TestDaoNode.initNode();
		HibernateGeneric.insertObject(n1);

		Node n2 = TestDaoNode.initNode();
		HibernateGeneric.insertObject(n2);
		
		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);
		
		City city = TestDaoCity.initCity(state);
		HibernateGeneric.insertObject(city);
		
		Airport airport = TestDaoAirport.initAirport(city);
		HibernateGeneric.insertObject(airport);

		Terminal terminal = TestDaoTerminal.initTerminal(airport);
		HibernateGeneric.insertObject(terminal);
		
		Gate arrivalGate = TestDaoGate.initGate(n1, terminal);
		HibernateGeneric.insertObject(arrivalGate);

		Gate departureGate = TestDaoGate.initGate(n2, terminal);
		HibernateGeneric.insertObject(departureGate);

		Route route = TestDaoRoute.initRoute(arrivalGate, departureGate);
		HibernateGeneric.insertObject(route);

		Collection<Route> routeList = new ArrayList<>();
		routeList.add(route);

		Airline airline = initAirline(routeList);
		DAOUser.deleteUserWithUsername(airline);

		return airline;
	}
	public static Airline initAirline(){
		Airline airline = new Airline();
		airline.setName(NARANAIR);
		airline.setUsername(USERNAME);
		airline.setPassword(PASSWORD);
		airline.setBirthDate(new Date());
		return airline;
		
	}
	
	
	public static Airline initAirline(Collection<Route> routeList){
		Airline airline = initAirline();
		airline.setRoutesList(routeList);
		return airline;
		
	}

	private void deleteAllFlights() {
		List<Object> listFlight = HibernateGeneric.loadAllObjects(new Flight());
		for (Object flight : listFlight) {
			HibernateGeneric.deleteObject((Flight) flight);
		}
	}

	private void deleteAllUsers() {
		List<Object> listUser = HibernateGeneric.loadAllObjects(new User());
		for (Object user : listUser) {
			HibernateGeneric.deleteObject((User) user);
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

	private void deleteAllPlaneMovements() {
		List<Object> planeMovementList = HibernateGeneric.loadAllObjects(new PlaneMovement());
		for (Object planeMovements : planeMovementList) {
			HibernateGeneric.deleteObject((PlaneMovement) planeMovements);
		}
	}

}
