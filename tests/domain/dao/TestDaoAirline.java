package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.Airline;
import domain.model.Gate;
import domain.model.Route;
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
		Airline airline = new Airline();
		airline.setName(NARANAIR);
		airline.setUsername(USERNAME);
		airline.setPassword(PASSWORD);
		airline.setBirthDate(new Date());
		deleteAllUsers();
		boolean result = DAOAirline.insertAirline(airline);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertAirlineWithRoutes() {
		Airline airline = new Airline();
		airline.setName(NARANAIR);
		airline.setUsername(USERNAME);
		airline.setPassword(PASSWORD);
		airline.setBirthDate(new Date());
		
		Route route = new Route();
		Gate arrivalGate = new Gate();
		route.setArrivalGate(arrivalGate);
		Gate departureGate = new Gate();
		route.setDepartureGate(departureGate);
		Collection<Route> routeList = new ArrayList<>();
		routeList.add(route);
		airline.setRoutesList(routeList);
		deleteAllUsers();
		boolean result = DAOAirline.insertAirline(airline);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllAirlines() {
		assertNotNull(ERROR_LOAD, DAOAirline.loadAllAirlines());

	}

	@Test
	public void testInsertNullAirlineIntoDB() {
		assertEquals(INSERT_ERROR, false, DAOAirline.insertAirline(null));
	}

	@Test
	public void testRemoveOneSpecificAirline() {
		/* Create airline */
		Airline airline = new Airline();
		airline.setName(NARANAIR);
		airline.setUsername(USERNAME);
		airline.setPassword(PASSWORD);
		airline.setBirthDate(new Date());
		
		Route route = new Route();
		Gate arrivalGate = new Gate();
		route.setArrivalGate(arrivalGate);
		Gate departureGate = new Gate();
		route.setDepartureGate(departureGate);
		Collection<Route> routeList = new ArrayList<>();
		routeList.add(route);
		airline.setRoutesList(routeList);
		
		/* delete all users to avoid duplicated error */
		// TODO sortu beharko zan delete funtzioa username emonda
		deleteAllUsers();
		DAOAirline.insertAirline(airline);
		airline = DAOAirline.loadAllAirlines().get(0);
		boolean result = DAOAirline.deleteAirline(airline);
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testRemoveOneNullAirline() {
		assertEquals(REMOVE_ERROR, false, DAOAirline.deleteAirline(null));
	}

	/* For testing, delete all users */
	private void deleteAllUsers() {
		List<User> listUsers = DAOUser.loadAllUsers();
		for (User user : listUsers) {
			DAOUser.deleteUser(user);
		}
	}

}
