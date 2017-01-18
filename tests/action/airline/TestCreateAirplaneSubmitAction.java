package action.airline;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;

import domain.dao.DAOUser;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import initialization.AdminInitialization;

public class TestCreateAirplaneSubmitAction {

	private static final String BOEING = "BOEING";
	private static final String _707 = "707";
	private static final String INCORRECT_RESULTS = "Incorrect results";
	private static final String A13AA = "A13AA";
	private static final String FIELD_ACTION_ERROR = "Field errors not generated properly";
	private static final int _3_ERRORS = 3;
	ActionContext ac;
	CreateAirplaneSubmitAction caAction;

	@Before
	public void prepareTests() {

		AdminInitialization initaializer = new AdminInitialization();
		initaializer.contextInitialized(null);

		HashMap<String, Object> map = new HashMap<>();
		map.put("user", DAOUser.getUser("admin"));

		ac = Mockito.mock(ActionContext.class);
		Mockito.when(ac.getSession()).thenReturn(map);

		caAction = Mockito.spy(new CreateAirplaneSubmitAction());
		Mockito.doReturn(new Locale("en")).when(caAction).getLocale();
		Mockito.doReturn("dd-MM-yyyy").when(caAction).getText(Mockito.anyString());

		ActionContext.setContext(ac);

	}

	@After
	public void destroyTets() {
		ac = null;
	}

	@Test
	public void testValidateNoData() {

		caAction.validate();

		assertEquals(FIELD_ACTION_ERROR, _3_ERRORS, caAction.getFieldErrors().size());

	}

	@Test
	public void testExecuteWithIDNull() {
		PlaneModel planeModel = new PlaneModel();
		planeModel.setName(_707);
		caAction.setPlaneModel(planeModel);

		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		planeModel.setPlaneMaker(planeMaker);
		caAction.setPlaneMaker(planeMaker);

		Plane plane = new Plane();
		plane.setSerial(A13AA);
		plane.setModel(planeModel);
		caAction.setPlane(plane);

		String result = caAction.execute();

		assertEquals(INCORRECT_RESULTS, CreateAirplaneSubmitAction.SUCCESS, result);

	}

	@Test
	public void testExecuteWithIDNotNull() {
		PlaneModel planeModel = new PlaneModel();
		planeModel.setName(_707);
		planeModel.setId(1);
		caAction.setPlaneModel(planeModel);

		PlaneMaker planeMaker = new PlaneMaker();
		planeMaker.setName(BOEING);
		planeMaker.setId(1);
		planeModel.setPlaneMaker(planeMaker);
		caAction.setPlaneMaker(planeMaker);

		Plane plane = new Plane();
		plane.setSerial(A13AA);
		plane.setModel(planeModel);
		caAction.setPlane(plane);

		String result = caAction.execute();

		assertEquals(INCORRECT_RESULTS, CreateAirplaneSubmitAction.SUCCESS, result);
	}

}
