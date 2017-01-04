package simulator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import domain.dao.DAOLane;
import domain.dao.Initializer;
import domain.model.Airport;
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
	public void testGetPermissionCorrectly() {
		ArrivingPlane plane = new ArrivingPlane(Initializer.initCompletePlane(), ac, new AtomicInteger());
		boolean result = ac.askPermission(plane);
		assertTrue(ERROR_INSERT, result);
	}

	@Test
	public void testFailGetPermissionCorrectly() {
		ArrivingPlane plane = new ArrivingPlane(Initializer.initCompletePlane(), ac, new AtomicInteger());
		List<Lane> freeLaneList = DAOLane.getFreeLanes(airport.getId());
		for (Lane lane : freeLaneList) {
			lane.setStatus(false);
			DAOLane.updateLane(lane);
		}
		boolean result = ac.askPermission(plane);
		assertFalse(ERROR_INSERT, result);
	}

}
