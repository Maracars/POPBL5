package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import domain.model.Flight;
import initialization.HibernateInit;
import initialization.SocketIOInit;

public class TestDaoFlight {

	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";
	private static final String ERROR_LOAD_FLIGHTS_TABLE = "Error loading flights for table";

	@Test
	public void testInsertFlightWithNothingIntoDB() {
		Flight flight = new Flight();
		boolean result = HibernateGeneric.saveObject(flight);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertFlightDB() {

		boolean result = HibernateGeneric.saveObject(Initializer.initCompleteFlight());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllFlights() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Flight()));

	}

	@Test
	public void testRemoveOneSpecificFlight() {

		Flight flight = Initializer.initCompleteFlight();
		HibernateGeneric.saveObject(flight);
		boolean result = HibernateGeneric.deleteObject(flight);

		assertEquals(REMOVE_ERROR, true, result);
	}
	
	@Test
	public void testLoadFlightsForTable(){
		
		Flight flight = Initializer.initCompleteFlight();
		HibernateGeneric.saveObject(flight);
		
		assertNotNull(ERROR_LOAD_FLIGHTS_TABLE, DAOFlight.loadFlightsForTable("plane", "asc", 0, 10));
		
	}

}
