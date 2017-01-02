package initialization;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import domain.dao.HibernateGeneric;
import domain.model.Address;
import domain.model.Airport;
import domain.model.Node;
import domain.model.Terminal;

public class TerminalInitialization implements ServletContextListener {

	private static final double TERMINAL_2_LONGITUDE = -0.4473375000000033;
	private static final double TERMINAL_2_LATITUDE = 51.4599986;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		List<Object> terminalList = HibernateGeneric.loadAllObjects(new Terminal());
		if(terminalList.isEmpty()){
			
			Airport airport = airportInitialization();
			
			Terminal terminal = new Terminal();
			terminal.setName("Terminal 2");
			terminal.setAirport(airport);
			
			Node node = new Node();
			node.setPositionX(TERMINAL_2_LATITUDE);
			node.setPositionY(TERMINAL_2_LONGITUDE);
			terminal.setPositionNode(node);
			
			HibernateGeneric.saveOrUpdateObject(node);
			HibernateGeneric.saveOrUpdateObject(terminal);
			
		}
		
	}

	private Airport airportInitialization() {
		
		Airport airport = new Airport();
		airport.setName("Heathrow");
		airport.setMaxFlights(6);
		
		Address a = new Address();
		a.setCity("London");
		a.setCountry("England");
		a.setPostCode("69420");
		a.setRegion("United Kingdom");
		a.setStreetAndNumber("Heathrow");
		airport.setAddress(a);
		
		HibernateGeneric.saveOrUpdateObject(a);
		HibernateGeneric.saveOrUpdateObject(airport);
		
		return airport;
		
	}

}
