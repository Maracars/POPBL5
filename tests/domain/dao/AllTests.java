package domain.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import action.user.TestLoginAction;
import action.user.TestLogoutAction;
import action.user.TestRegisterAction;
import action.user.TestRegisterActionStaticMock;

@RunWith(Suite.class)
@SuiteClasses({ TestDaoAirport.class,  TestDaoGate.class,
	TestDaoPlaneMaker.class, TestDaoRoute.class,
		TestDaoAddress.class, TestDaoTerminal.class, 
		TestDaoNode.class, TestDaoPlaneModel.class, TestDaoLane.class, TestDaoAirline.class,
		TestHibernateGeneric.class, TestDaoPlane.class, TestDaoDelay.class,
		TestDaoFlight.class, TestDaoPlaneMovements.class, TestDaoPassenger.class,
		TestMD5.class, TestLoginAction.class, TestLogoutAction.class, TestRegisterAction.class, TestRegisterActionStaticMock.class})

public class AllTests {

}
