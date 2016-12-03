package action.user;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;

public class TestLogoutAction {
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
	public void testExecute() {

		LogoutAction la = new LogoutAction();
		String result = null;
		try {
			result = la.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals("Logout did not work properly", LoginAction.SUCCESS, result);

	}

}
