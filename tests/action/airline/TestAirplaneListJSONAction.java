package action.airline;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.HttpParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;

import domain.dao.DAOUser;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Plane;
import domain.model.users.Airline;

public class TestAirplaneListJSONAction {

	private static final String TEN = "10";
	private static final String ASC = "asc";
	private static final String ADMIN = "admin";
	private static final String _0 = "0";
	private static final String RAWTYPES = "rawtypes";
	private static final String JSON_DATA_EMPTY_ERROR = "Error, JSON data is not empty";
	private static final String JSON_DATA_NOT_EMPTY_ERROR = "Error, JSON data is empty";
	private static final String FILTER_ERROR = "Table filter not working";

	private static final int JSON_DATA_EMPTY_LENGTH = 0;
	private static final int JSON_DATA_NOT_EMPTY_LENGTH = 1;
	private static final int FILTER_LENGTH = 1;

	ActionContext ac;
	@SuppressWarnings(RAWTYPES)
	AirplaneListJSONAction alJSONac;
	HttpParameters paramsMap;

	@Before
	public void prepareTests() {

		HashMap<String, Object> sessionMap = new HashMap<>();
		sessionMap.put("user", DAOUser.getUser(ADMIN));

		ac = Mockito.mock(ActionContext.class);
		Mockito.when(ac.getSession()).thenReturn(sessionMap);

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
		HibernateGeneric.deleteAllObjects(new Plane());
		alJSONac = new AirplaneListJSONAction();

		createParameters("", _0, ASC, _0, TEN);
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		try {
			alJSONac.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(JSON_DATA_EMPTY_ERROR, JSON_DATA_EMPTY_LENGTH, alJSONac.getData().size());
	}

	@SuppressWarnings(RAWTYPES)
	@Test
	public void testExecuteNotNullData() {
		HibernateGeneric.deleteAllObjects(new Plane());

		Plane plane = Initializer.initCompletePlane();

		Airline airline = new Airline();
		airline.setId(DAOUser.getUser(ADMIN).getId());
		plane.setAirline(airline);

		HibernateGeneric.saveObject(plane);

		alJSONac = new AirplaneListJSONAction();

		createParameters("", _0, ASC, _0, TEN);
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		try {
			alJSONac.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(JSON_DATA_NOT_EMPTY_ERROR, JSON_DATA_NOT_EMPTY_LENGTH, alJSONac.getData().size());

	}

	@SuppressWarnings(RAWTYPES)
	@Test
	public void testFilterWorkingProperly() {
		HibernateGeneric.deleteAllObjects(new Plane());

		Plane firstPlane = Initializer.initCompletePlane();
		Plane secondPlane = Initializer.initCompletePlane();
		secondPlane.setSerial("SERIAL");

		Airline airline = new Airline();
		airline.setId(DAOUser.getUser(ADMIN).getId());
		firstPlane.setAirline(airline);
		secondPlane.setAirline(airline);

		HibernateGeneric.saveObject(firstPlane);
		HibernateGeneric.saveObject(secondPlane);

		alJSONac = new AirplaneListJSONAction();

		createParameters(firstPlane.getSerial(), _0, ASC, _0, TEN);
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		try {
			alJSONac.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(FILTER_ERROR, FILTER_LENGTH, alJSONac.getData().size());

	}

}
