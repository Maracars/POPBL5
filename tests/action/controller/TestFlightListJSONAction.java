package action.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.opensymphony.xwork2.ActionContext;

import action.airline.AirplaneListJSONAction;
import domain.dao.DAOFlight;
import domain.dao.DAOUser;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.users.Airline;
import initialization.HibernateInit;
import initialization.SocketIOInit;


public class TestFlightListJSONAction {
	
	private static final String JSON_DATA_EMPTY_ERROR = "Error, JSON data is not empty";
	private static final String JSON_DATA_NOT_EMPTY_ERROR = "Error, JSON data is empty";
	private static final String FILTER_ERROR = "Table filter not working";
	
	private static final int JSON_DATA_EMPTY_LENGTH = 0;
	private static final int JSON_DATA_NOT_EMPTY_LENGTH = 1;
	private static final int FILTER_LENGTH = 1;
	
	ActionContext ac;
	HttpParameters paramsMap;
	@SuppressWarnings("rawtypes")
	FlightListJSONAction flAction;
	

	@Before
	public void prepareTests(){
		
		ac = Mockito.mock(ActionContext.class);
		
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);

		ActionContext.setContext(ac);
		
	}
	
	@After
	public void destroyTests() {
		ac = null;
	}
	
	
	public void createParameters(String search, String orderCol, String orderDir, String start, String length){
		

		Map<String, String[]> mapValues = new HashMap<String, String[]>();
		
		
		String[] value = {search};
		String key = "search[value]";
		mapValues.put(key, value);
		
		key = "order[0][column]";
		value = new String[]{orderCol};
		mapValues.put(key,  value);
		
		key = "order[0][dir]";
		value = new String[]{orderDir};
		mapValues.put(key, value);
		
		key = "start";
		value = new String[]{start};
		mapValues.put(key, value);
		
		key = "length";
		value = new String[]{length};
		mapValues.put(key, value);
		
		paramsMap = HttpParameters.create(mapValues).build();
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testExecuteNullData(){
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());
		flAction = new FlightListJSONAction();
		
		createParameters("", "0", "asc", "0", "10");
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);
		
		try {
			flAction.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(JSON_DATA_EMPTY_ERROR, JSON_DATA_EMPTY_LENGTH, flAction.getData().size());
	}
	

	@SuppressWarnings("rawtypes")
	@Test
	public void testExecuteNotNullData(){
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());
		
		Flight flight = Initializer.initCompleteFlight();
		
		HibernateGeneric.saveObject(flight);
		flAction = new FlightListJSONAction();
		
		createParameters("", "0", "asc", "0", "10");
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);
		
		try {
			flAction.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(JSON_DATA_NOT_EMPTY_ERROR, JSON_DATA_NOT_EMPTY_LENGTH, flAction.getData().size());
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testFilterWorkingProperly(){
		HibernateGeneric.deleteAllObjects(new Plane());
		HibernateGeneric.deleteAllObjects(new Flight());
		
		Flight firstFlight = Initializer.initCompleteFlight();
		Flight secondFlight = Initializer.initCompleteFlight();
		secondFlight.getPlane().setSerial("SERIAL");
		
		HibernateGeneric.saveObject(firstFlight);
		HibernateGeneric.saveObject(secondFlight);
		
		flAction = new FlightListJSONAction();
		
		createParameters(firstFlight.getPlane().getSerial(), "0", "asc", "0", "10");
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);
		
		try {
			flAction.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(FILTER_ERROR, FILTER_LENGTH, flAction.getData().size());
		
	}

}
