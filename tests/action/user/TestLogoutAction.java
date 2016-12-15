package action.user;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.opensymphony.xwork2.ActionContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ServletActionContext.class)
public class TestLogoutAction {
	ActionContext ac;
	HttpServletRequest mockRequest;

	@Before
	public void prepareTests() {

		ac = Mockito.mock(ActionContext.class);
		Mockito.when(ac.getSession()).thenReturn(new HashMap<>());
		
		mockRequest = Mockito.mock(HttpServletRequest.class);
		Mockito.when(mockRequest.getHeader("referer")).thenReturn("/url");
		
		PowerMockito.mockStatic(ServletActionContext.class);
		PowerMockito.when(ServletActionContext.getRequest()).thenReturn(mockRequest);

		ActionContext.setContext(ac);
	}

	@After
	public void destroyTests() {
		ac = null;
		mockRequest = null;
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
