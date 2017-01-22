

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import action.airline.TestAirplaneListJSONAction;
import action.airline.TestAirplaneMoreInfoAction;
import action.airline.TestCreateAirplaneSubmitAction;
import action.airline.TestMainPageAction;
import action.airline.TestPieChartAction;
import action.controller.TestFlightListJSONAction;
import action.controller.TestLaneListJSONAction;
import action.controller.TestTerminalListJSONAction;
import action.passenger.TestFlightJSONActionPassenger;
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
import domain.dao.TestDaoAddress;
import domain.dao.TestDaoAirline;
import domain.dao.TestDaoAirport;
import domain.dao.TestDaoDelay;
import domain.dao.TestDaoFlight;
import domain.dao.TestDaoGate;
import domain.dao.TestDaoLane;
import domain.dao.TestDaoNode;
import domain.dao.TestDaoPassenger;
import domain.dao.TestDaoPath;
import domain.dao.TestDaoPlane;
import domain.dao.TestDaoPlaneMaker;
import domain.dao.TestDaoPlaneModel;
import domain.dao.TestDaoPlaneMovements;
import domain.dao.TestDaoRoute;
import domain.dao.TestDaoSimulator;
import domain.dao.TestDaoTerminal;
import domain.dao.TestHibernateGeneric;
import initialization.HibernateInit;
import initialization.SocketIOInit;
import simulator.TestAirportController;
import simulator.TestCompleteSimulator;
import simulator.TestFlightCreator;
import test.dijkstra.TestDijkstra;

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
		TestDaoSimulator.class,
		TestDaoPlaneMovements.class,
		TestDaoPassenger.class,
		TestDaoPath.class,
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
		TestRegisterAirlineActionStaticMock.class,
		TestFlightListJSONAction.class,
		TestFlightJSONActionPassenger.class,
		TestCreateAirplaneSubmitAction.class,
		TestAirplaneMoreInfoAction.class,
		TestMainPageAction.class,
		TestPieChartAction.class,
		TestAirplaneListJSONAction.class,
		TestLaneListJSONAction.class,
		TestTerminalListJSONAction.class,
		TestDijkstra.class,
		TestFlightCreator.class,
		TestAirportController.class,
		TestCompleteSimulator.class,
		})

public class AllTests {
	
	static HibernateInit  init;
	static SocketIOInit initio;
	
	@BeforeClass
	public static void init() {
		init = new HibernateInit();
		init.contextInitialized(null);
		
		initio = new SocketIOInit();
		initio.contextInitialized(null);
		
		
	}
	
	@AfterClass
	public static void destroy() {
		init.contextDestroyed(null);
		initio.contextDestroyed(null);
	}
	
}
