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
import domain.dao.HibernateGeneric;
import domain.model.Address;
import domain.model.users.Passenger;

public class TestLoginAction {

	private static final String TEST_CITY = "TEST CITY";
	private static final String TEST_COUNTRY = "TEST COUNTRY";
	private static final String TEST_STREET_0 = "TEST STREET 0";
	private static final String TEST_REGION = "TEST REGION";
	private static final String TEST_PC = "00000";
	private static final String INCORRECT_RESULT = "Incorrect result";
	private static final String ACTION_ERROR_NOT_GENERATED = "Action error not generated";
	private static final String OTHER_PASSWORD = "Example";
	private static final String TEST_PASSWORD = "password";
	private static final String TEST_USERNAME = "TESTEXAMPLE@#$!";
	private static final String FIELD_ERROR_NOT_GENERATED = "Field errors should have been generated";
	private static final int _2_ERRORS = 2;
	private static final int _1_ERRORS = 1;
	private static final int _0_ERRORS = 0;

	ActionContext ac;
	LoginAction la;

	@Before
	public void prepareTests() {

		ac = Mockito.mock(ActionContext.class);
		Mockito.when(ac.getSession()).thenReturn(new HashMap<>());
		
		la = Mockito.spy(new LoginAction());
		Mockito.doReturn(new Locale("en")).when(la).getLocale();	

		ActionContext.setContext(ac);
	}

	@After
	public void destroyTests() {
		ac = null;
	}

	@Test
	public void validateEmptyParams() {
		la.setUsername("");
		la.setPassword("");
		la.validate();
		assertEquals(FIELD_ERROR_NOT_GENERATED, _2_ERRORS, la.getFieldErrors().size());
	}

	@Test
	public void validateEmptyPass() {
		la.setPassword("");
		la.setUsername(TEST_USERNAME);
		la.validate();
		assertEquals(FIELD_ERROR_NOT_GENERATED, _1_ERRORS, la.getFieldErrors().size());
	}

	@Test
	public void validateEmptyUser() {
		la.setUsername("");
		la.setPassword(TEST_PASSWORD);
		la.validate();
		assertEquals(FIELD_ERROR_NOT_GENERATED, _1_ERRORS, la.getFieldErrors().size());
	}

	@Test
	public void validateBothFieldsFilled() {
		la.setPassword(OTHER_PASSWORD);
		la.setUsername(TEST_USERNAME);
		la.validate();
		assertEquals(FIELD_ERROR_NOT_GENERATED, _0_ERRORS, la.getFieldErrors().size());
	}

	@Test
	public void loginNonExistingUsernameCheckResult() {
		la.setUsername(TEST_USERNAME);
		la.setPassword(OTHER_PASSWORD);

		String result = null;
		try {
			result = la.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(INCORRECT_RESULT, LoginAction.LOGIN, result);
	}

	@Test
	public void loginNonExistingUsernameCheckError() {
		la.setUsername(TEST_USERNAME);
		la.setPassword(OTHER_PASSWORD);

		try {
			la.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(ACTION_ERROR_NOT_GENERATED, _1_ERRORS, la.getActionErrors().size());
	}

	@Test
	public void loginIncorretPasswordCheckResult() {
		Passenger user = new Passenger();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setBirthDate(new Date());
		
		Address a = new Address();
		a.setCity(TEST_CITY);
		a.setCountry(TEST_COUNTRY);
		a.setPostCode(TEST_PC);
		a.setRegion(TEST_REGION);
		a.setStreetAndNumber(TEST_STREET_0);
		
		user.setAddress(a);

		la.setUsername(TEST_USERNAME);
		la.setPassword(OTHER_PASSWORD);
		
		
		
		HibernateGeneric.saveOrUpdateObject(a);
		HibernateGeneric.saveOrUpdateObject(user);

		String result = null;
		try {
			result = la.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DAOUser.deleteUserWithUsername(user);
		assertEquals(INCORRECT_RESULT, LoginAction.LOGIN, result);
	}

	@Test
	public void loginIncorretPasswordCheckErrors() {

		Passenger user = new Passenger();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setBirthDate(new Date());
		
		Address a = new Address();
		a.setCity(TEST_CITY);
		a.setCountry(TEST_COUNTRY);
		a.setPostCode(TEST_PC);
		a.setRegion(TEST_REGION);
		a.setStreetAndNumber(TEST_STREET_0);
		
		user.setAddress(a);

		la.setUsername(TEST_USERNAME);
		la.setPassword(OTHER_PASSWORD);
		
		HibernateGeneric.saveOrUpdateObject(a);
		HibernateGeneric.saveOrUpdateObject(user);

		try {
			la.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DAOUser.deleteUserWithUsername(user);
		
		assertEquals(ACTION_ERROR_NOT_GENERATED, _1_ERRORS, la.getActionErrors().size());
	}

	@Test
	public void loginCorrectlyCheckErrors() {
				
		Passenger user = new Passenger();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setBirthDate(new Date());
		
		Address a = new Address();
		a.setCity(TEST_CITY);
		a.setCountry(TEST_COUNTRY);
		a.setPostCode(TEST_PC);
		a.setRegion(TEST_REGION);
		a.setStreetAndNumber(TEST_STREET_0);
		
		user.setAddress(a);


		la.setPassword(user.getPassword());
		la.setUsername(user.getUsername());

		HibernateGeneric.saveOrUpdateObject(a);
		HibernateGeneric.saveOrUpdateObject(user);

		try {
			la.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		DAOUser.deleteUserWithUsername(user);

		assertEquals(ACTION_ERROR_NOT_GENERATED, _0_ERRORS, la.getActionErrors().size());
	}

	@Test
	public void loginCorrectlyCheckResult() {
		Passenger user = new Passenger();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setBirthDate(new Date());
		
		Address a = new Address();
		a.setCity(TEST_CITY);
		a.setCountry(TEST_COUNTRY);
		a.setPostCode(TEST_PC);
		a.setRegion(TEST_REGION);
		a.setStreetAndNumber(TEST_STREET_0);
		
		user.setAddress(a);

		la.setPassword(user.getPassword());
		la.setUsername(user.getUsername());

		HibernateGeneric.saveOrUpdateObject(a);
		HibernateGeneric.saveOrUpdateObject(user);

		String result = null;
		try {
			result = la.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DAOUser.deleteUserWithUsername(user);

		assertEquals(INCORRECT_RESULT, LoginAction.SUCCESS, result);
	}

}
