package action.user;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import domain.dao.HibernateGeneric;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HibernateGeneric.class)
public class TestRegisterActionStaticMock {
	
	private static final String SOMETHING_ELSE = "Something else";
	private static final String SOMETHING = "Something";

	@Test
	public void testExecuteError() {

		RegisterAction la = new RegisterAction();

		la.user.setUsername(SOMETHING);
		la.user.setBirthDate(new Date());
		la.user.setPassword(SOMETHING_ELSE);
		
		PowerMockito.mockStatic(HibernateGeneric.class);
		PowerMockito.when(HibernateGeneric.insertObject(la.user)).thenReturn(false);

		String result = null;
		try {
			result = la.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals("Incorrect result", RegisterAction.ERROR, result);
	}

	
}
