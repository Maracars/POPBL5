package action.user;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;

import domain.dao.DAOUser;
import domain.dao.HibernateGeneric;
import domain.model.User;

public class TestLoginAction {

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

	@Before
	public void prepareTests() {

		ac = Mockito.mock(ActionContext.class);
		Mockito.when(ac.getSession()).thenReturn(new HashMap<>());

		ActionContext.setContext(ac);
	}

	@After
	public void destroyTests() {
		ac = null;
	}

	@Test
	public void validateEmptyParams() {
		LoginAction la = new LoginAction();
		la.validate();
		assertEquals(FIELD_ERROR_NOT_GENERATED, _2_ERRORS, la.getFieldErrors().size());
	}

	@Test
	public void validateEmptyPass() {
		LoginAction la = new LoginAction();
		la.setUsername(OTHER_PASSWORD);
		la.validate();
		assertEquals(FIELD_ERROR_NOT_GENERATED, _1_ERRORS, la.getFieldErrors().size());
	}

	@Test
	public void validateEmptyUser() {
		LoginAction la = new LoginAction();
		la.setPassword(OTHER_PASSWORD);
		la.validate();
		assertEquals(FIELD_ERROR_NOT_GENERATED, _1_ERRORS, la.getFieldErrors().size());
	}

	@Test
	public void validateBothFieldsFilled() {
		LoginAction la = new LoginAction();
		la.setPassword(OTHER_PASSWORD);
		la.setUsername(TEST_USERNAME);
		la.validate();
		assertEquals(FIELD_ERROR_NOT_GENERATED, _0_ERRORS, la.getFieldErrors().size());
	}

	@Test
	public void loginNonExistingUsernameCheckResult() {
		LoginAction la = new LoginAction();
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
		LoginAction la = new LoginAction();
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
		LoginAction la = new LoginAction();

		User user = new User();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setBirthDate(new Date());

		la.setUsername(TEST_USERNAME);
		la.setPassword(OTHER_PASSWORD);

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
		LoginAction la = new LoginAction();

		User user = new User();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setBirthDate(new Date());

		la.setUsername(TEST_USERNAME);
		la.setPassword(OTHER_PASSWORD);

		HibernateGeneric.saveOrUpdateObject(user);

		try {
			la.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(ACTION_ERROR_NOT_GENERATED, _1_ERRORS, la.getActionErrors().size());
	}

	@Test
	public void loginCorrectlyCheckErrors() {
		LoginAction la = new LoginAction();
		User user = new User();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setBirthDate(new Date());

		la.setPassword(user.getPassword());
		la.setUsername(user.getUsername());

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
		LoginAction la = new LoginAction();
		User user = new User();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setBirthDate(new Date());

		la.setPassword(user.getPassword());
		la.setUsername(user.getUsername());

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
