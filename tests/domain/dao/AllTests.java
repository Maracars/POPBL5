package domain.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestDaoAirport.class, TestDaoCity.class, TestDaoGate.class,
	TestDaoPlaneMaker.class, TestDaoRoute.class, TestDaoState.class, 
	TestDaoTerminal.class, TestDaoUser.class, TestDaoNode.class, 
	TestDaoPlaneModel.class, TestDaoLane.class, TestDaoAirline.class })

public class AllTests {

}
