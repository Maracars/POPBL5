package action.airline;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import domain.dao.DAOUser;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Plane;
import domain.model.users.Airline;
import initialization.AdminInitialization;
import initialization.HibernateInit;
import initialization.SocketIOInit;

public class TestAirplaneMoreInfoAction {
	
	ActionContext ac;
	AirplaneMoreInfoAction aMInfoAc;
	
	private static final String FIELD_ACTION_ERROR = "Field errors not generated properly";
	private static final int _1_ERRORS = 1;
	private static final String JSON_RESULT = "json";
	
	@Before
	public void prepareTests(){
		
		AdminInitialization initaializer = new AdminInitialization();
		initaializer.contextInitialized(null);

		HashMap<String, Object> map = new HashMap<>();
		map.put("user", DAOUser.getUser("admin"));

		ac = Mockito.mock(ActionContext.class);
		Mockito.when(ac.getSession()).thenReturn(map);

		aMInfoAc = Mockito.spy(new AirplaneMoreInfoAction());
		Mockito.doReturn(new Locale("en")).when(aMInfoAc).getLocale();
		Mockito.doReturn("dd-MM-yyyy").when(aMInfoAc).getText(Mockito.anyString());

		ActionContext.setContext(ac);

	}
	
	@After
	public void destroyTets(){
		ac = null;
	}
	
	
	@Test
	public void testExecuteSerialEmpty(){
		aMInfoAc.setSerial("");
		
		try {
			aMInfoAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(FIELD_ACTION_ERROR, _1_ERRORS, aMInfoAc.getActionErrors().size());
	}
	
	
	@Test
	public void testExecutePlaneNull(){
		aMInfoAc.setPlane(null);
		aMInfoAc.setSerial("A");
		
		try {
			aMInfoAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(FIELD_ACTION_ERROR, _1_ERRORS, aMInfoAc.getActionErrors().size());
	}
	
	@Test
	public void testExecuteSerialContainsJSON(){
		
		Plane plane = Initializer.initCompletePlane();
		Airline airline = new Airline();
		airline.setId(DAOUser.getUser("admin").getId());
		plane.setAirline(airline);
		HibernateGeneric.saveObject(plane);
		aMInfoAc.setPlane(plane);
		aMInfoAc.setSerial("airline/JSON/"+plane.getSerial());
		String result = null;
		
		try {
			result = aMInfoAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(FIELD_ACTION_ERROR, JSON_RESULT, result);
	}
	
	@Test
	public void testExecuteSerial(){
		Plane plane = Initializer.initCompletePlane();
		Airline airline = new Airline();
		airline.setId(DAOUser.getUser("admin").getId());
		plane.setAirline(airline);
		String result = null;
		
		HibernateGeneric.saveObject(plane);
		aMInfoAc.setPlane(plane);
		aMInfoAc.setSerial(plane.getSerial());
		
		try {
			result = aMInfoAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(FIELD_ACTION_ERROR, AirplaneMoreInfoAction.SUCCESS, result);
		
	}
	

}
