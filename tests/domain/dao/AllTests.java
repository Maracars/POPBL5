package domain.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import action.user.TestLoginAction;
import action.user.TestLogoutAction;
import action.user.TestRegisterAdminAction;
import action.user.TestRegisterAdminActionStaticMock;
import action.user.TestRegisterAirlineAction;
import action.user.TestRegisterAirlineActionStaticMock;
import action.user.TestRegisterControllerAction;
import action.user.TestRegisterControllerActionStaticMock;
import action.user.TestRegisterMantainanceAction;
import action.user.TestRegisterMantainanceActionStaticMock;
import action.user.TestRegisterPassengerAction;
import action.user.TestRegisterPassengerActionStaticMock;

@RunWith(Suite.class)
@SuiteClasses({ 
		TestDaoAirport.class,
		TestDaoGate.class,
		TestDaoPlaneMaker.class,
		TestDaoRoute.class,
		TestDaoAddress.class,
		TestDaoTerminal.class,
		TestDaoNode.class,
		TestDaoPlaneModel.class,
		TestDaoLane.class,
		TestDaoAirline.class,
		TestHibernateGeneric.class,
		TestDaoPlane.class,
		TestDaoDelay.class,
		TestDaoFlight.class,
		TestDaoPlaneMovements.class,
		TestDaoPassenger.class, 
		//TestMD5.class,
		TestLoginAction.class,
		TestLogoutAction.class, 
		TestRegisterPassengerAction.class, 
		TestRegisterPassengerActionStaticMock.class,
		TestRegisterControllerAction.class, 
		TestRegisterControllerActionStaticMock.class,
		TestRegisterAdminAction.class, 
		TestRegisterAdminActionStaticMock.class,
		TestRegisterMantainanceAction.class, 
		TestRegisterMantainanceActionStaticMock.class,
		TestRegisterAirlineAction.class, 
		TestRegisterAirlineActionStaticMock.class
		})

public class AllTests {

}
