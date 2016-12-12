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
		boolean result = HibernateGeneric.saveOrUpdateObject(route);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertRouteWithGatesIntoDB() {

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initCompleteRoute());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllRoutes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Route()));

	}

	@Test
	public void testRemoveOneSpecificRoute() {

		HibernateGeneric.saveOrUpdateObject(Initializer.initCompleteRoute());
		// TODO Hemen gero loadAll biharrian load bakarra einbiko litzake
		boolean result = HibernateGeneric.deleteObject((Route) HibernateGeneric.loadAllObjects(new Route()).get(0));
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testGetListOfArrivalRoutesOfAirportByAirportId() {

		State state = Initializer.initState();
		HibernateGeneric.saveOrUpdateObject(state);

		City city = Initializer.initCity(state);
		HibernateGeneric.saveOrUpdateObject(city);

		Airport airport = Initializer.initAirport(city);
		HibernateGeneric.saveOrUpdateObject(airport);

		Terminal terminal = Initializer.initTerminal(airport);
		HibernateGeneric.saveOrUpdateObject(terminal);

		Gate gate = Initializer.initGate(terminal);
		HibernateGeneric.saveOrUpdateObject(gate);

		Route expectedRoute = Initializer.initRoute(gate, gate);
		HibernateGeneric.saveOrUpdateObject(expectedRoute);

		Route actualRoute = DAORoute.getRandomArrivalRouteFromAirport(airport.getId()).get(0);

		assertEquals(expectedRoute.getId(), actualRoute.getId());

	}



}
