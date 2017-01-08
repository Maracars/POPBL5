package simulator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import domain.dao.DAOLane;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Lane;

public class TestAirportController {

	private static final String ERROR_INSERT = "AA";
	Airport airport;
	AirportController ac;

	@Before
	public void initialize() {
		airport = Initializer.initializeExampleOnDB();
		ac = new AirportController(airport);
	}

	@Test
	public void testGetPermissionCorrectlyForDeparture() {
		DeparturingPlane plane = new DeparturingPlane(Initializer.initCompletePlane(), ac, new AtomicInteger(), new Flight());
		boolean result = ac.askPermission(plane);
		assertTrue(ERROR_INSERT, result);
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
		assertFalse(ERROR_INSERT, result);
	}

}
