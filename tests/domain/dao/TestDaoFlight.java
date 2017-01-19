package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import domain.model.Flight;
import domain.model.Plane;

public class TestDaoFlight {

	private static final int LENGTH = 10;
	private static final int START = 0;
	private static final int FLIGHT_HOURS = 1;
	private static final int WEEK_FLIGHTS = 1;
	private static final int ARRIVAL_ON_TIME = 1;
	private static final int DEPARTURE_SIZE = 1;
	private static final int ARRIVAL_ON_NOT_TIME = 1;
	private static final int DEPARTURE_ON_TIME = 1;
	private static final int DEPARTURE_ON_NOT_TIME = 1;
	private static final String ORDER_COL_DIR = "asc";
	private static final String ORDER_COL_NAME = "plane";
	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String ERROR_LOAD_FLIGHT_HOURS = "Error load flight hours from database";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";
	private static final String ERROR_LOAD_FLIGHTS_TABLE = "Error loading flights for table";
	private static final int HOUR_IN_MILIS = 3600000;
	private static final String ERROR_LOAD_NEXT_DEPARTURE_FLIGHTS = "Error loading next departure flights";
	


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
		
	public void testLoadFlightsForTable() {

		Flight flight = Initializer.initCompleteFlight();
		HibernateGeneric.saveObject(flight);
	

		assertNotNull(ERROR_LOAD_FLIGHTS_TABLE,
				DAOFlight.loadFlightsForTable(ORDER_COL_NAME, ORDER_COL_DIR, START, LENGTH));

	}

	
	@Test
	public void testLoadPlaneFlightHours() {
		Date date = new Date();
		Flight flight = Initializer.initCompleteFlight();
		flight.setExpectedArrivalDate(date);
		flight.setExpectedDepartureDate(new Date(date.getTime() + HOUR_IN_MILIS));
		HibernateGeneric.saveObject(flight);

		assertEquals(ERROR_LOAD_FLIGHT_HOURS, FLIGHT_HOURS, 
				DAOFlight.loadPlaneFlightHours(flight.getPlane().getId()));
	}


	@Test

	public void testLoadOneWeekFlights() {
		
		List<Object> flightList = HibernateGeneric.loadAllObjects(new Flight());
		for(Object flight : flightList){
			((Flight) flight).setPassengerList(null);
			HibernateGeneric.updateObject(flight);
		}
		
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());

		Date date = new Date();
		Flight flight = Initializer.initCompleteFlight();
		flight.setExpectedArrivalDate(date);
		flight.setExpectedDepartureDate(new Date(date.getTime() + HOUR_IN_MILIS));
		HibernateGeneric.saveObject(flight);
		

		assertEquals(ERROR_LOAD_FLIGHT_HOURS, WEEK_FLIGHTS, 
				DAOFlight.loadOneWeekFlights().size());
	}


	@Test

	public void testLoadDayFlightsArriveOnTime() {
		
		List<Object> flightList = HibernateGeneric.loadAllObjects(new Flight());
		for(Object flight : flightList){
			((Flight) flight).setPassengerList(null);
			HibernateGeneric.updateObject(flight);
		}
		
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());

		Date date = new Date();
		Flight flight = Initializer.initCompleteFlight();
		flight.setExpectedArrivalDate(date);
		flight.setRealArrivalDate(new Date(date.getTime() - HOUR_IN_MILIS));
		HibernateGeneric.saveObject(flight);
		

		assertEquals(ERROR_LOAD_FLIGHT_HOURS, ARRIVAL_ON_TIME, 
				DAOFlight.loadDayFlightsArriveOnTime());
	}


	@Test

	public void testLoadDayFlightsDepartureOnTime() {
		
		List<Object> flightList = HibernateGeneric.loadAllObjects(new Flight());
		for(Object flight : flightList){
			((Flight) flight).setPassengerList(null);
			HibernateGeneric.updateObject(flight);
		}
		
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());

		Date date = new Date();
		Flight flight = Initializer.initCompleteFlight();
		flight.setExpectedDepartureDate(date);
		flight.setRealDepartureDate(new Date(date.getTime() - HOUR_IN_MILIS));
		HibernateGeneric.saveObject(flight);
		

		assertEquals(ERROR_LOAD_FLIGHT_HOURS, DEPARTURE_ON_TIME, 
				DAOFlight.loadDayFlightsDepartureOnTime());
	}

	@Test

	public void testloadDayFlightsArriveOnNotTime() {
		
		List<Object> flightList = HibernateGeneric.loadAllObjects(new Flight());
		for(Object flight : flightList){
			((Flight) flight).setPassengerList(null);
			HibernateGeneric.updateObject(flight);
		}
		
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());

		Date date = new Date();
		Flight flight = Initializer.initCompleteFlight();
		flight.setExpectedArrivalDate(date);
		flight.setRealArrivalDate(new Date(date.getTime() + HOUR_IN_MILIS));
		HibernateGeneric.saveObject(flight);
		

		assertEquals(ERROR_LOAD_FLIGHT_HOURS, ARRIVAL_ON_NOT_TIME, 
				DAOFlight.loadDayFlightsArriveOnNotTime());
	}


	@Test

	public void testloadDayFlightsDepartureOnNotTime() {
		
		List<Object> flightList = HibernateGeneric.loadAllObjects(new Flight());
		for(Object flight : flightList){
			((Flight) flight).setPassengerList(null);
			HibernateGeneric.updateObject(flight);
		}
		
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());

		Date date = new Date();
		Flight flight = Initializer.initCompleteFlight();
		flight.setExpectedDepartureDate(date);
		flight.setRealDepartureDate(new Date(date.getTime() + HOUR_IN_MILIS));
		HibernateGeneric.saveObject(flight);


		assertEquals(ERROR_LOAD_FLIGHT_HOURS, DEPARTURE_ON_NOT_TIME,
				DAOFlight.loadDayFlightsDepartureOnNotTime());
	}
	
	@Test
	public void testLoadNextDepartureFlights(){
		
		List<Object> flightList = HibernateGeneric.loadAllObjects(new Flight());
		for(Object flight : flightList){
			((Flight) flight).setPassengerList(null);
			HibernateGeneric.updateObject(flight);
		}
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());
		
		Flight flight = Initializer.initCompleteFlight();
		flight.getRoute().getDepartureTerminal().getAirport().setLocale(true);
		HibernateGeneric.updateObject(flight.getRoute().getDepartureTerminal().getAirport());
		flight.setRealArrivalDate(null);
		flight.setRealDepartureDate(null);
		flight.getRoute().getArrivalTerminal().getAirport().setLocale(false);
		Date date = new Date();
		flight.setExpectedDepartureDate(new Date(date.getTime() + HOUR_IN_MILIS));
		HibernateGeneric.saveObject(flight);
		
		assertEquals(ERROR_LOAD_NEXT_DEPARTURE_FLIGHTS, DEPARTURE_SIZE, DAOFlight.loadNextDepartureFlights().size());
	}

}
