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
import domain.model.Node;
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
		boolean result = HibernateGeneric.insertObject(airline);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertAirlineWithRoutes() {
		Airline airline = new Airline();
		airline.setName(NARANAIR);
		airline.setUsername(USERNAME);
		airline.setPassword(PASSWORD);
		airline.setBirthDate(new Date());

		Node n1 = new Node();
		n1.setName("NODE_1");
		n1.setPositionX(3.2);
		n1.setPositionY(3.2);
		HibernateGeneric.insertObject(n1);
		
		Node n2 = new Node();
		n2.setName("NODE_2");
		n2.setPositionX(3.2);
		n2.setPositionY(3.2);
		HibernateGeneric.insertObject(n2);
		
		Gate arrivalGate = new Gate();
		arrivalGate.setPositionNode(n1);
		HibernateGeneric.insertObject(arrivalGate);
			
		Gate departureGate = new Gate();
		departureGate.setPositionNode(n2);
		HibernateGeneric.insertObject(departureGate);
		
		Route route = new Route();
		route.setArrivalGate(arrivalGate);
		route.setDepartureGate(departureGate);
		HibernateGeneric.insertObject(route);
		
		Collection<Route> routeList = new ArrayList<>();
		routeList.add(route);
		airline.setRoutesList(routeList);

		deleteAllUsers();
		assertEquals(INSERT_ERROR, true, HibernateGeneric.insertObject(airline));
	}

	@Test
	public void testLoadAllAirlines() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airline()));

	}

	@Test
	public void testRemoveOneSpecificAirline() {
		/* Create airline */
		Airline airline = new Airline();
		airline.setName(NARANAIR);
		airline.setUsername(USERNAME);
		airline.setPassword(PASSWORD);
		airline.setBirthDate(new Date());

		Node n1 = new Node();
		n1.setName("NODE_1");
		n1.setPositionX(3.2);
		n1.setPositionY(3.2);
		HibernateGeneric.insertObject(n1);
		
		Node n2 = new Node();
		n2.setName("NODE_2");
		n2.setPositionX(3.2);
		n2.setPositionY(3.2);
		HibernateGeneric.insertObject(n2);
		
		Gate arrivalGate = new Gate();
		arrivalGate.setPositionNode(n1);
		HibernateGeneric.insertObject(arrivalGate);
			
		Gate departureGate = new Gate();
		departureGate.setPositionNode(n2);
		HibernateGeneric.insertObject(departureGate);
		
		Route route = new Route();
		route.setArrivalGate(arrivalGate);
		route.setDepartureGate(departureGate);
		HibernateGeneric.insertObject(route);
		
		Collection<Route> routeList = new ArrayList<>();
		routeList.add(route);
		airline.setRoutesList(routeList);
		
		/* delete all users to avoid duplicated error */
		// TODO sortu beharko zan delete funtzioa username emonda
		deleteAllUsers();
		HibernateGeneric.insertObject(airline);
		airline = (Airline) HibernateGeneric.loadAllObjects(new Airline()).get(0);
		boolean result = HibernateGeneric.deleteObject(airline);
		assertEquals(REMOVE_ERROR, true, result);
	}

	/* For testing, delete all users */
	private void deleteAllUsers() {
		List<Object> listUsers = HibernateGeneric.loadAllObjects(new User());
		for (Object user : listUsers) {
			HibernateGeneric.deleteObject((User) user);
		}
	}

}
