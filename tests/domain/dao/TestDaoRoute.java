package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Airport;
import domain.model.City;
import domain.model.Gate;
import domain.model.Route;
import domain.model.State;
import domain.model.Terminal;

public class TestDaoRoute {

	private static final String ERROR_LOAD = "Error load all routes from database";
	private static final String INSERT_ERROR = "Error insert route into database";
	private static final String REMOVE_ERROR = "Error removing one route from database";

	@Test
	public void testInsertRouteWithoutGateIntoDB() {
		Route route = new Route();
		boolean result = HibernateGeneric.insertObject(route);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertRouteWithGatesIntoDB() {

		boolean result = HibernateGeneric.insertObject(Initializer.initCompleteRoute());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllRoutes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Route()));

	}

	@Test
	public void testRemoveOneSpecificRoute() {

		HibernateGeneric.insertObject(Initializer.initCompleteRoute());
		// TODO Hemen gero loadAll biharrian load bakarra einbiko litzake
		boolean result = HibernateGeneric.deleteObject((Route) HibernateGeneric.loadAllObjects(new Route()).get(0));
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testGetListOfArrivalRoutesOfAirportByAirportId() {

		State state = Initializer.initState();
		HibernateGeneric.insertObject(state);

		City city = Initializer.initCity(state);
		HibernateGeneric.insertObject(city);

		Airport airport = Initializer.initAirport(city);
		HibernateGeneric.insertObject(airport);

		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.insertObject(terminal);

		Gate gate = Initializer.initGate(terminal);
		HibernateGeneric.insertObject(gate);

		Route expectedRoute = Initializer.initRoute(gate, gate);
		HibernateGeneric.insertObject(expectedRoute);

		Route actualRoute = HibernateGeneric.getRandomArrivalRouteFromAirport(airport.getId()).get(0);

		assertEquals(expectedRoute.getId(), actualRoute.getId());

	}



}
