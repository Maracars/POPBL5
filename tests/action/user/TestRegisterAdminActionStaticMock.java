package action.user;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.opensymphony.xwork2.ActionContext;

import domain.dao.HibernateGeneric;
import domain.model.users.Admin;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HibernateGeneric.class)
public class TestRegisterAdminActionStaticMock {

	private static final String SOMETHING_ELSE = "Something else";
	private static final String SOMETHING = "Something";

	@Test
	public void testExecuteError() {

		RegisterAdminAction la = Mockito.spy(new RegisterAdminAction());
		Mockito.doReturn("dd-MM-yyy").when(la).getText(Mockito.anyString());
		
		ActionContext ac = Mockito.mock(ActionContext.class);
		Mockito.when(ac.getSession()).thenReturn(new HashMap<>());
		
		ActionContext.setContext(ac);
		
		la.user.setUsername(SOMETHING);
		la.user = new Admin(la.user);
		((Admin)la.user).setBirthDate(new Date());
		la.user.setPassword(SOMETHING_ELSE);

		PowerMockito.mockStatic(HibernateGeneric.class);
		PowerMockito.when(HibernateGeneric.saveObject(Mockito.any())).thenReturn(false);

		String result = null;
		try {
			result = la.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals("Incorrect result", RegisterAction.ERROR, result);
		
		ac = null;
	}

}
