package action.action;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.HttpParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;

import action.NextDeparturingFlightJSONAction;
import action.passenger.FlightListJSONAction;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Flight;
import domain.model.Plane;
import initialization.HibernateInit;
import initialization.SocketIOInit;

public class TestNextDeparturingFlightJSONAction {
	
	private static final String EMPTY = "";
	private static final String TEN = "10";
	private static final String ASC = "asc";
	private static final String _0 = "0";
	private static final String RAWTYPES = "rawtypes";
	private static final String JSON_DATA_EMPTY_ERROR = "Error, JSON data is not empty";
	private static final String JSON_DATA_NOT_EMPTY_ERROR = "Error, JSON data is empty";

	private static final int JSON_DATA_EMPTY_LENGTH = 0;
	private static final int JSON_DATA_NOT_EMPTY_LENGTH = 1;
	
	private static final int HOUR_IN_MILIS = 3600000;
	
	ActionContext ac;
	@SuppressWarnings(RAWTYPES)
	NextDeparturingFlightJSONAction ndAction;
	HttpParameters paramsMap;
	
	@Before
	public void prepareTests() {

		ac = Mockito.mock(ActionContext.class);

		ActionContext.setContext(ac);
	}
	
	@After
	public void destroyTests() {
		ac = null;
	}
	
	public void createParameters(String search, String orderCol, String orderDir, String start, String length) {

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
		ndAction = new NextDeparturingFlightJSONAction();

		createParameters(EMPTY, _0, ASC, _0, TEN);
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		try {
			ndAction.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(JSON_DATA_EMPTY_ERROR, JSON_DATA_EMPTY_LENGTH, ndAction.getData().size());
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
		flight.setExpectedDepartureDate(new Date(date.getTime() + HOUR_IN_MILIS));

		HibernateGeneric.saveObject(flight);

		ndAction = new NextDeparturingFlightJSONAction();

		createParameters(EMPTY, _0, ASC, _0, TEN);
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		try {
			ndAction.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(JSON_DATA_NOT_EMPTY_ERROR, JSON_DATA_NOT_EMPTY_LENGTH, ndAction.getData().size());

	}

}
