package action.controller;

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

import domain.dao.DAOFlight;
import domain.model.Flight;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ServletActionContext.class, DAOFlight.class})
public class TestFlightListJSONAction {
	
	private static final String ERROR_GENERATING_DATA = "Error generating JSON data";
	ActionContext ac;
	HttpParameters map;
	HttpServletRequest mockRequest;
	@SuppressWarnings("rawtypes")
	FlightListJSONAction flAction;
	Map<String, String[]> mapValues;
	
	@SuppressWarnings("rawtypes")
	@Before
	public void prepareTests(){
		ac = Mockito.mock(ActionContext.class);
		map = Mockito.mock(HttpParameters.class);
		
		createParameters();
		Mockito.when(ac.getParameters()).thenReturn(map);
		
		
		mockRequest = Mockito.mock(HttpServletRequest.class);
		Mockito.when(mockRequest.getHeader("referer")).thenReturn("/url");
		
		PowerMockito.mockStatic(ServletActionContext.class);
		PowerMockito.when(ServletActionContext.getRequest()).thenReturn(mockRequest);
		
		flAction = Mockito.spy(new FlightListJSONAction());
		
		PowerMockito.mockStatic(DAOFlight.class);
		PowerMockito.when(DAOFlight.loadFlightsForTable(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Flight>());

		ActionContext.setContext(ac);
		
	}
	
	@After
	public void destroyTests() {
		ac = null;
		mockRequest = null;
		flAction = null;
		map = null;
	}
	
	
	public void createParameters(){
		

		mapValues = new HashMap<String, String[]>();
		
		
		String[] value = {""};
		String key = "search[value]";
		mapValues.put(key, value);
		
		key = "order[0][column]";
		value = new String[]{"0"};
		mapValues.put(key,  value);
		
		key = "order[0][dir]";
		value = new String[]{"asc"};
		mapValues.put(key, value);
		
		key = "start";
		value = new String[]{"0"};
		mapValues.put(key, value);
		
		key = "length";
		value = new String[]{"10"};
		mapValues.put(key, value);
		
		map = HttpParameters.create(mapValues).build();
		
	}
	

	@Test
	public void testExecute(){
		
		try {
			flAction.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(ERROR_GENERATING_DATA, flAction.getData());
	
		
	}
	

}
