package simulator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import domain.dao.DAOLane;
import domain.dao.DAORoute;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Lane;
import domain.model.Route;

public class TestAirportController {

	private static final String ERROR_GET_PERMISSION_DEPARTURE = "Error getting permission to departure";
	private static final String ERROR_GET_PERMISSION_ARRIVE = "Error getting permission to arrive";
	private static final String ERROR_FAIL_GETTING_PERMISSION = "Error, should not get permission";
	Airport airport;
	AirportController ac;

	@Before
	public void initialize() {
		airport = Initializer.initializeExampleOnDB();
		ac = new AirportController(airport);
	}

	@Test
	public void testGetPermissionCorrectlyForDeparture() {
		DeparturingPlane plane = new DeparturingPlane(Initializer.initCompletePlane(), ac, new AtomicInteger(),
				new Flight());
		boolean result = ac.askPermission(plane);
		assertTrue(ERROR_GET_PERMISSION_DEPARTURE, result);
	}

	@Test
	public void testGetPermissionCorrectlyForArrival() {
		Flight flight = new Flight();
		Route route = DAORoute.getRandomArrivalRouteFromAirport(airport.getId()).get(0);
		flight.setRoute(route);
		System.out.println();
		ArrivingPlane plane = new ArrivingPlane(Initializer.initCompletePlane(), ac, new AtomicInteger(), flight);
		boolean result = ac.askPermission(plane);
		assertTrue(ERROR_GET_PERMISSION_ARRIVE, result);
	}

	@Test
	public void testFailGetPermissionCorrectly() {
		ArrivingPlane plane = new ArrivingPlane(Initializer.initCompletePlane(), ac, new AtomicInteger(), new Flight());
		List<Lane> freeLaneList = DAOLane.getFreeLanes(airport.getId());
		for (Lane lane : freeLaneList) {
			lane.setStatus(false);
			HibernateGeneric.updateObject(lane);
		}
		boolean result = ac.askPermission(plane);
		assertFalse(ERROR_FAIL_GETTING_PERMISSION, result);
	}

}
