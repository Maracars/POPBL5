package action.airline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;

import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Flight;
import initialization.HibernateInit;
import initialization.SocketIOInit;

public class TestMainPageAction {

	private static final String FIELD_ACTION_ERROR = "Field errors not generated properly";
	private static final int DATA_EMPTY = 0;
	private static final int DATA_1 = 1;

	ActionContext ac;
	MainPageAction mpAc;

	@Before
	public void prepareTests(){

		ac = Mockito.mock(ActionContext.class);

		mpAc = Mockito.spy(new MainPageAction());

		ActionContext.setContext(ac);

	}

	@After
	public void destroyTets(){
		ac = null;
	}

	
	@Test
	public void testJSONDataIsEmpty(){
		HibernateGeneric.deleteAllObjects(new Flight());

		try {
			mpAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNull(FIELD_ACTION_ERROR, mpAc.getData());
	}


	@Test
	public void testJSONDataIsNotEmpty(){
		HibernateGeneric.deleteAllObjects(new Flight());

		Flight flight = Initializer.initCompleteFlight();
		HibernateGeneric.saveObject(flight);

		try {
			mpAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(FIELD_ACTION_ERROR, DATA_1, mpAc.getData().size());
	}

	@Test
	public void testJSONDataIsOK(){
		HibernateGeneric.deleteAllObjects(new Flight());

		Flight flight = Initializer.initCompleteFlight();
		List<Flight> flightList = new ArrayList<Flight>();
		flightList.add(flight);
		HibernateGeneric.saveObject(flight);

		try {
			mpAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(FIELD_ACTION_ERROR, mpAc.getData().get(0).getFlightId(), mpAc.generateData(flightList).get(0).getFlightId());
	}

}
