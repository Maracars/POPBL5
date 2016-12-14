package action.user;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;

import domain.dao.DAOUser;
import domain.dao.Initializer;
import domain.model.users.Passenger;

public class TestRegisterPassengerAction {
	private static final String SOMETHING_ELSE = "Something else";
	private static final String SOMETHING = "Something";
	private static final String FIELD_ACTION_ERROR = "Field errors not generated properly";
	private static final int _11_ERRORS = 11;
	ActionContext ac;
	
	RegisterPassengerAction la;
	
	@Before
	public void prepareTests() {

		ac = Mockito.mock(ActionContext.class);
		Mockito.when(ac.getSession()).thenReturn(new HashMap<>());
		
		la = Mockito.spy(new RegisterPassengerAction());
		Mockito.doReturn(new Locale("en")).when(la).getLocale();	
		Mockito.doReturn("dd-MM-yyyy").when(la).getText(Mockito.anyString());
		
		ActionContext.setContext(ac);
	}

	@After
	public void destroyTests() {
		ac = null;
	}

	@Test
	public void testValidateNoData() {
	
		la.validate();

		assertEquals(FIELD_ACTION_ERROR, _11_ERRORS, la.getFieldErrors().size());

	}

	@Test
	public void testValidateUnder18() {

		
		la.setBirthdate("11-11-3000");

		la.validate();

		assertEquals(FIELD_ACTION_ERROR, _11_ERRORS, la.getFieldErrors().size());

	}

	@Test
	public void testValidateRepeatPassEmpty() {
		la.user.setPassword(SOMETHING);

		la.validate();

		assertEquals(FIELD_ACTION_ERROR, _11_ERRORS, la.getFieldErrors().size());

	}

	@Test
	public void testValidatePasswordsNotMatch() {

		la.user.setPassword(SOMETHING);
		la.repeatPassword = SOMETHING_ELSE;

		la.validate();

		assertEquals(FIELD_ACTION_ERROR, _11_ERRORS, la.getFieldErrors().size());

	}

	@Test
	public void testValidateWrongDateFormat() {

		la.user.setPassword(SOMETHING);
		la.repeatPassword = SOMETHING_ELSE;

		la.setBirthdate("12---3");

		la.validate();

		assertEquals(FIELD_ACTION_ERROR, _11_ERRORS, la.getFieldErrors().size());

	}

	@Test
	public void testExecuteOK() {

		la.user.setUsername(SOMETHING);
		la.user = new Passenger(la.user);
		((Passenger)la.user).setBirthDate(new Date());
		((Passenger)la.user).setAddress(Initializer.initAddress());
		
		la.user.setPassword(SOMETHING_ELSE);
		
		String result = null;
		try {
			result = la.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DAOUser.deleteUserWithUsername(la.user);
		assertEquals("Incorrect result", RegisterAction.SUCCESS, result);

	}

}
