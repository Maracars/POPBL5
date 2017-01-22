package action.passenger;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.HttpParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;

import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Flight;
import domain.model.Plane;
import initialization.HibernateInit;
import initialization.SocketIOInit;

public class TestFlightJSONActionPassenger {
	
	private static final String EMPTY = "";
	private static final String _10 = "10";
	private static final String _0 = "0";
	private static final String ASC = "asc";
	private static final String RAWTYPES = "rawtypes";
	private static final String JSON_DATA_EMPTY_ERROR = "Error, JSON data is not empty";
	private static final String JSON_DATA_NOT_EMPTY_ERROR = "Error, JSON data is empty";
	private static final String FILTER_ERROR = "Table filter not working";

	private static final int JSON_DATA_EMPTY_LENGTH = 0;
	private static final int JSON_DATA_NOT_EMPTY_LENGTH = 1;
	private static final int FILTER_LENGTH = 1;
	
	/** The Constant HOUR_IN_MILIS. */
	private static final int HOUR_IN_MILIS = 3600000;
	
	ActionContext ac;
	HttpParameters paramsMap;
	@SuppressWarnings(RAWTYPES)
	FlightListJSONAction flAction;
	
	
	@Before
	public void prepareTests() {

		ac = Mockito.mock(ActionContext.class);

		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		ActionContext.setContext(ac);

	}
	

	@After
	public void destroyTests() {
		ac = null;
	}

	public void createParameters(String search, String orderCol, String orderDir, String start, String length, String originSearch, String destinationSearch) {

		Map<String, String[]> mapValues = new HashMap<String, String[]>();

		String[] value = { search };
		String key = "search[value]";
		mapValues.put(key, value);

		key = "order[0][column]";
		value = new String[] { orderCol };
		mapValues.put(key, value);

		key = "order[0][dir]";
		value = new String[] { orderDir };
		mapValues.put(key, value);

		key = "start";
		value = new String[] { start };
		mapValues.put(key, value);

		key = "length";
		value = new String[] { length };
		mapValues.put(key, value);
		
		key = "columns[0][search][value]";
		value = new String[]{ originSearch};
		mapValues.put(key, value);
		
		key = "columns[1][search][value]";
		value = new String[]{ destinationSearch};
		mapValues.put(key, value);
		

		paramsMap = HttpParameters.create(mapValues).build();

	}

	@SuppressWarnings(RAWTYPES)
	@Test
	public void testExecuteNullData() {
		for(Object o : HibernateGeneric.loadAllObjects(new Flight())){
			Flight flight = (Flight) o;
			flight.setPassengerList(null);
			HibernateGeneric.updateObject(flight);
		}
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());
		flAction = new FlightListJSONAction();

		createParameters(EMPTY, _0, ASC, _0, _10, EMPTY, EMPTY);
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		try {
			flAction.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(JSON_DATA_EMPTY_ERROR, JSON_DATA_EMPTY_LENGTH, flAction.getData().size());
	}
	
	@SuppressWarnings(RAWTYPES)
	@Test
	public void testExecuteNotNullData() {
		for(Object o : HibernateGeneric.loadAllObjects(new Flight())){
			Flight flight = (Flight) o;
			flight.setPassengerList(null);
			HibernateGeneric.updateObject(flight);
		}
		
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());
		
		Date date = new Date();

		Flight flight = Initializer.initCompleteFlight();
		flight.getRoute().getDepartureTerminal().getAirport().setLocale(true);
		flight.setExpectedDepartureDate(new Date(date.getTime() + HOUR_IN_MILIS));
		HibernateGeneric.updateObject(flight.getRoute().getDepartureTerminal().getAirport());

		HibernateGeneric.saveObject(flight);
		flAction = new FlightListJSONAction();

		createParameters(EMPTY, _0, ASC, _0, _10, EMPTY, EMPTY);
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		try {
			flAction.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(JSON_DATA_NOT_EMPTY_ERROR, JSON_DATA_NOT_EMPTY_LENGTH, flAction.getData().size());

	}
	
	@SuppressWarnings(RAWTYPES)
	@Test
	public void testFilterWorkingProperly() {
		for(Object o : HibernateGeneric.loadAllObjects(new Flight())){
			Flight flight = (Flight) o;
			flight.setPassengerList(null);
			HibernateGeneric.updateObject(flight);
		}
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());
		
		Date date = new Date();

		Flight firstFlight = Initializer.initCompleteFlight();
		firstFlight.setExpectedDepartureDate(new Date(date.getTime() + HOUR_IN_MILIS));
		HibernateGeneric.saveObject(firstFlight);
		
		Flight secondFlight = Initializer.initCompleteFlight();
		secondFlight.setExpectedDepartureDate(new Date(date.getTime() + HOUR_IN_MILIS));
		HibernateGeneric.saveObject(secondFlight);
		
		firstFlight.getRoute().getDepartureTerminal().getAirport().setLocale(true);
		firstFlight.getRoute().getDepartureTerminal().getAirport().setName("TEST");
		HibernateGeneric.updateObject(firstFlight.getRoute().getDepartureTerminal().getAirport());
		
		secondFlight.getRoute().getArrivalTerminal().getAirport().setLocale(true);
		HibernateGeneric.updateObject(secondFlight.getRoute().getArrivalTerminal().getAirport());
		

		flAction = new FlightListJSONAction();

		createParameters(firstFlight.getRoute().getDepartureTerminal().getAirport().getName(), _0, ASC, _0, _10, EMPTY, EMPTY);
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		try {
			flAction.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(FILTER_ERROR, FILTER_LENGTH, flAction.getData().size());

	}

}
