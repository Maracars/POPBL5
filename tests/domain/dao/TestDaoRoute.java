package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import domain.model.Address;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Route;
import domain.model.Terminal;
import initialization.HibernateInit;
import initialization.SocketIOInit;

public class TestDaoRoute {

	private static final String ERROR_GET_LIST_OF_ROUTES_OF_AIRPORT_BY_AIRPORTID = 
			"ERROR GET LIST OF ROUTES OF AIRPORT BY AIRPORTID";
	private static final String ERROR_LOAD = "Error load all routes from database";
	private static final String INSERT_ERROR = "Error insert route into database";
	private static final String REMOVE_ERROR = "Error removing one route from database";
	private static final String ERROR_GET_AIRLINE_ROUTES = "Error getting airline routes";
	

	@Test
	public void testInsertRouteWithoutGateIntoDB() {
		Route route = new Route();
		boolean result = HibernateGeneric.saveObject(route);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertRouteWithGatesIntoDB() {

		boolean result = HibernateGeneric.saveObject(Initializer.initCompleteRoute());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllRoutes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Route()));

	}

	@Test
	public void testRemoveOneSpecificRoute() {

		Route route = Initializer.initCompleteRoute();
		HibernateGeneric.saveObject(route);
		boolean result = HibernateGeneric.deleteObject(route);
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testGetListOfArrivalRoutesOfAirportByAirportId() {

		Address address = Initializer.initAddress();
		HibernateGeneric.saveObject(address);

		Node positionNode = Initializer.initNode();
		HibernateGeneric.saveObject(positionNode);

		Airport airport = Initializer.initAirport(address, positionNode);
		HibernateGeneric.saveObject(airport);

		Terminal terminal = Initializer.initTerminal(airport, positionNode);
		HibernateGeneric.saveObject(terminal);

		Gate gate = Initializer.initGate(terminal);
		HibernateGeneric.saveObject(gate);

		Route expectedRoute = Initializer.initRoute(terminal, terminal);
		HibernateGeneric.saveObject(expectedRoute);

		Route actualRoute = DAORoute.getRandomArrivalRouteFromAirport(airport.getId()).get(0);

		assertEquals(ERROR_GET_LIST_OF_ROUTES_OF_AIRPORT_BY_AIRPORTID, expectedRoute.getId(), actualRoute.getId());

	}
	
	@Test
	public void testGetRouteListFromAirline(){
		Flight flight = Initializer.initCompleteFlight();
		HibernateGeneric.saveObject(flight);
		
		assertNotNull(ERROR_GET_AIRLINE_ROUTES, DAORoute.getRouteListFromAirline(flight.getPlane().getAirline().getId()));
	}

}
