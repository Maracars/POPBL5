package domain.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestDaoAirport.class,  TestDaoGate.class,
	TestDaoPlaneMaker.class, TestDaoRoute.class,
		TestDaoAddress.class, TestDaoTerminal.class, 
		TestDaoNode.class, TestDaoPlaneModel.class, TestDaoLane.class, TestDaoAirline.class,
		TestHibernateGeneric.class, TestDaoPlane.class, TestDaoDelay.class,
		TestDaoFlight.class, TestDaoPlaneMovements.class, TestDaoPassenger.class,
		TestMD5.class})

public class AllTests {

}
