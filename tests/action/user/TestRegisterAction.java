package action.user;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;


public class TestRegisterAction {
	private static final String SOMETHING_ELSE = "Something else";
	private static final String SOMETHING = "Something";
	private static final String FIELD_ACTION_ERROR = "Field errors not generated properly";
	private static final int _3_ERRORS = 3;
	ActionContext ac;

	@Test
	public void testValidateNoData() {

		RegisterAction la = new RegisterAction();

		la.validate();

		assertEquals(FIELD_ACTION_ERROR, _3_ERRORS, la.getFieldErrors().size());

	}

	@Test
	public void testValidateUnder18() {

		RegisterAction la = new RegisterAction();
		la.birthdate = "11-11-3000";

		la.validate();

		assertEquals(FIELD_ACTION_ERROR, _3_ERRORS, la.getFieldErrors().size());

	}

	@Test
	public void testValidateRepeatPassEmpty() {

		RegisterAction la = new RegisterAction();
		la.user.setPassword(SOMETHING);

		la.validate();

		assertEquals(FIELD_ACTION_ERROR, _3_ERRORS, la.getFieldErrors().size());

	}

	@Test
	public void testValidatePasswordsNotMatch() {

		RegisterAction la = new RegisterAction();
		la.user.setPassword(SOMETHING);
		la.repeatPassword = SOMETHING_ELSE;

		la.validate();

		assertEquals(FIELD_ACTION_ERROR, _3_ERRORS, la.getFieldErrors().size());

	}

	@Test
	public void testExecuteOK() {

		RegisterAction la = new RegisterAction();

		la.user.setUsername(SOMETHING);
		la.user.setBirthDate(new Date());
		la.user.setPassword(SOMETHING_ELSE);

		String result = null;
		try {
			result = la.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals("Incorrect result", RegisterAction.SUCCESS, result);

	}

	
}
