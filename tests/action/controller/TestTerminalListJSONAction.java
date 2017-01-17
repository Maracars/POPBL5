package action.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.HttpParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;

import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Airport;
import domain.model.Flight;
import domain.model.Gate;
import initialization.HibernateInit;
import initialization.SocketIOInit;

public class TestTerminalListJSONAction {
	
	private static final String JSON_DATA_EMPTY_ERROR = "Error, JSON data is not empty";
	private static final String JSON_DATA_NOT_EMPTY_ERROR = "Error, JSON data is empty";
	private static final String FILTER_ERROR = "Table filter not working";
	
	private static final int JSON_DATA_EMPTY_LENGTH = 0;
	private static final int JSON_DATA_NOT_EMPTY_LENGTH = 1;
	private static final int FILTER_LENGTH = 1;
	
	ActionContext ac;
	HttpParameters paramsMap;
	TerminalListJSONAction tListJSONac;
	
	static HibernateInit  init;
	static SocketIOInit initio;
	
	
	@Before
	public void prepareTests(){
		init = new HibernateInit();
		init.contextInitialized(null);
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
		HibernateGeneric.deleteAllObjects(new Gate());
		tListJSONac = new TerminalListJSONAction();
		
		createParameters("", "0", "asc", "0", "10");
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);
		
		try {
			tListJSONac.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(JSON_DATA_EMPTY_ERROR, JSON_DATA_EMPTY_LENGTH, tListJSONac.getData().size());
	}
	

	@SuppressWarnings("rawtypes")
	@Test
	public void testExecuteNotNullData(){
		HibernateGeneric.deleteAllObjects(new Gate());
		HibernateGeneric.deleteAllObjects(new Flight());
		HibernateGeneric.deleteAllObjects(new Airport());
		
		Gate gate = Initializer.initCompleteGate();
		gate.getTerminal().getAirport().setLocale(true);
		HibernateGeneric.updateObject(gate.getTerminal().getAirport());
		
		HibernateGeneric.saveObject(gate);
		
		tListJSONac = new TerminalListJSONAction();
		
		createParameters("", "0", "asc", "0", "10");
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);
		
		try {
			tListJSONac.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(JSON_DATA_NOT_EMPTY_ERROR, JSON_DATA_NOT_EMPTY_LENGTH, tListJSONac.getData().size());
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testFilterWorkingProperly(){
		HibernateGeneric.deleteAllObjects(new Gate());
		HibernateGeneric.deleteAllObjects(new Flight());
		HibernateGeneric.deleteAllObjects(new Airport());
		
		Gate firstGate = Initializer.initCompleteGate();
		firstGate.getTerminal().getAirport().setLocale(true);
		Gate secondGate = Initializer.initCompleteGate();
		HibernateGeneric.updateObject(firstGate.getTerminal().getAirport());
		
		secondGate.getTerminal().setAirport(firstGate.getTerminal().getAirport());
		
		HibernateGeneric.saveObject(firstGate);
		HibernateGeneric.saveObject(secondGate);
		
		tListJSONac = new TerminalListJSONAction();
		
		createParameters(firstGate.getTerminal().getName(), "0", "asc", "0", "10");
		Mockito.when(ac.getParameters()).thenReturn(paramsMap);
		
		try {
			tListJSONac.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(FILTER_ERROR, FILTER_LENGTH, tListJSONac.getData().size());
		
	}

}
