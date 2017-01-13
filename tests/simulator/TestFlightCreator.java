package simulator;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import domain.dao.DAORoute;
import domain.dao.DAOSimulator;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.PlaneStatus;

public class TestFlightCreator {

	private static final int ADDED_TIME = 111111;
	private static final String ERROR_PROGRAM_FLIGHT = "Error creating flights";
	private static final String ERROR_ACTIVATING_PLANES = "Error activating planes";

	Airport airport;
	AirportController ac;
	FlightCreator fg;

	@Before
	public void initialize() {
		HibernateGeneric.deleteAllObjects(new Flight());
		HibernateGeneric.deleteAllObjects(new Plane());
		airport = Initializer.initializeExampleOnDB();
		ac = new AirportController(airport, null);
		fg = new FlightCreator(airport, ac);
	}

	@Test
	public void testProgramFlightsCorrectly() {
		try {
			Method method = fg.getClass().getDeclaredMethod("programFlights");
			method.setAccessible(true);
			method.invoke(fg);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Long result = null;
		result = DAOSimulator.getNumberOfFlightsInAWeekFromAirport(airport.getId());
		assertNotNull(ERROR_PROGRAM_FLIGHT, result);
	}

	@Test
	public void testActivatePlanes() {
		try {
			Plane plane = Initializer.initCompletePlane();
			plane.getPlaneStatus().setTechnicalStatus("OK");
			plane.getPlaneStatus().setPositionStatus("ON AIRPORT");
			PlaneStatus status = plane.getPlaneStatus();
			HibernateGeneric.saveObject(status);
			plane.setPlaneStatus(status);
			HibernateGeneric.saveObject(plane);

			Flight flight = Initializer.initFlight(plane);
			Date date = new Date();
			date.setTime(date.getTime() + ADDED_TIME);
			
			flight.setExpectedDepartureDate(date);
			flight.setRoute(DAORoute.selectDepartureRouteFromAirport(airport.getId()));
			HibernateGeneric.saveObject(flight);

			Method method = fg.getClass().getDeclaredMethod("createThreadsOfFlights");
			method.setAccessible(true);
			method.invoke(fg);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int result = 0;
		result = fg.activePlanesNum.get();
		assertNotEquals(ERROR_ACTIVATING_PLANES, result, 0);
	}

}
