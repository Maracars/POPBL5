package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Gate;
import domain.model.Route;

public class TestDaoRoute {

	private static final String ERROR_LOAD = "Error load all routes from database";
	private static final String INSERT_ERROR = "Error insert route into database";
	private static final String REMOVE_ERROR = "Error removing one route from database";
	private static final Integer GATENUM1 = 5;
	private static final Integer GATENUM2 = 9;

	@Test
	public void testInsertRouteWithoutGateIntoDB() {
		Route route = new Route();
		boolean result = DAORoute.insertRoute(route);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertRouteWithGatesIntoDB() {
		Gate arrivalGate = new Gate();
		arrivalGate.setNumber(GATENUM1);
		Gate departureGate = new Gate();
		departureGate.setNumber(GATENUM2);
		Route route = new Route();
		DAOGate.insertGate(arrivalGate);
		DAOGate.insertGate(departureGate);
		route.setArrivalGate(arrivalGate);
		route.setDepartureGate(departureGate);
		boolean result = DAORoute.insertRoute(route);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllRoutes() {
		assertNotNull(ERROR_LOAD, DAORoute.loadAllRoutes());

	}

	@Test
	public void testInsertNullRouteIntoDB() {
		assertEquals(INSERT_ERROR, false, DAORoute.insertRoute(null));
	}

	@Test
	public void testRemoveOneSpecificRoute() {
		Route route = new Route();
		route.setId(1);
		DAORoute.insertRoute(route);
		//TODO Hemen gero loadAll biharrian load bakarra einbiko litzake
		boolean result = DAORoute.deleteRoute(DAORoute.loadAllRoutes().get(0));
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testRemoveOneNullRoute() {
		assertEquals(REMOVE_ERROR, false, DAORoute.deleteRoute(null));
	}

}
