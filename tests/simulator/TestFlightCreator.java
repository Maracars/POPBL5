package simulator;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import domain.dao.DAOSimulator;
import domain.dao.Initializer;
import domain.model.Airport;

public class TestFlightCreator {

	private static final String ERROR_REMOVING = "Error removing one node from database";
	private static final String ERROR_LOAD = "Error load all nodes from database";
	private static final String ERROR_INSERT = "Error insert node into database";

	Airport airport;
	AirportController ac;
	FlightCreator fg;

	@Before
	public void initialize() {
		airport = Initializer.initializeExampleOnDB();
		ac = new AirportController(airport);
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
		assertNotNull(ERROR_INSERT, result);
	}

	
	/*Eztakit zerba baia eztau detektetan getArrivalPlaneSoon*/
	@Ignore
	public void testActivatePlanes() {
		try {
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
		assertNotEquals(ERROR_INSERT, result, 0);
	}

}
