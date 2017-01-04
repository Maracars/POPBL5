package initialization;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Address;
import domain.model.Airport;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Terminal;

public class GatesInitialization implements ServletContextListener{

	private static final int FIRST_AIRPORT = 0;
	private static final double NODE_POSITION_Y = -0.461389;
	private static final double NODE_POSITION_X = 51.4775;
	private static final String NODE_NAME = "Heathrow";
	private static final String AGATES_JSON_FILE = "AGates.json";
	private static final String BGATES_JSON_FILE = "BGates.json";
	private static final String CGATES_JSON_FILE = "CGates.json";
	private static final String TERMINALS_JSON_FILE = "Terminal.json";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Airport airport = null;
		
		List<Object> airportList = HibernateGeneric.loadAllObjects(new Airport());
		if(airportList.isEmpty()){
			airport = createAirport();
		}
		List<Terminal> terminalList = loadTerminalsJSON();
		if(terminalList != null && HibernateGeneric.loadAllObjects(new Terminal()) != null){
			for(Terminal terminal : terminalList){
				if(airport != null){
					terminal.setAirport(airport);
				}else{
					terminal.setAirport((Airport) airportList.get(FIRST_AIRPORT));
				}
				HibernateGeneric.saveOrUpdateObject(terminal.getPositionNode());
				HibernateGeneric.saveOrUpdateObject(terminal);
			}
			List<Gate> gatesList = loadGatesJSON();
			if(gatesList != null && HibernateGeneric.loadAllObjects(new Gate()) != null){
				for(Gate gate : gatesList){
					Random random = new Random();
					gate.setTerminal(terminalList.get(random.nextInt(terminalList.size())));
					HibernateGeneric.saveOrUpdateObject(gate.getPositionNode());
					HibernateGeneric.saveOrUpdateObject(gate);
				}
			}
		}
	}
	
	public List<Gate> loadGatesJSON(){
		List<Object> gates = HibernateGeneric.loadAllObjects(new Gate());
		List<Gate> gateList = null;
		if(gates.isEmpty()){
			ObjectMapper mapper = new ObjectMapper();
			try {
				URL url = getClass().getResource(AGATES_JSON_FILE);
				System.out.println(url);
				gateList = mapper.readValue(new File(url.getPath()), new TypeReference<List<Gate>>(){});
				url = getClass().getResource(BGATES_JSON_FILE);
				System.out.println(url);
				gateList.addAll(mapper.readValue(new File(url.getPath()), new TypeReference<List<Gate>>(){}));
				url = getClass().getResource(CGATES_JSON_FILE);
				System.out.println(url);
				gateList.addAll(mapper.readValue(new File(url.getPath()), new TypeReference<List<Gate>>(){}));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return gateList;
	}
	
	public List<Terminal> loadTerminalsJSON(){
		List<Object> terminal = HibernateGeneric.loadAllObjects(new Terminal());
		List<Terminal> terminalList = null;
		if(terminal.isEmpty()){
			ObjectMapper mapper = new ObjectMapper();
			try {
				URL url = getClass().getResource(TERMINALS_JSON_FILE);
				System.out.println(url);
				terminalList = mapper.readValue(new File(url.getPath()), new TypeReference<List<Terminal>>(){});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return terminalList;
	}
	
	public Airport createAirport(){
		Address address = Initializer.initAddress();
		Node node = new Node();
		node.setName(NODE_NAME);
		node.setPositionX(NODE_POSITION_X);
		node.setPositionY(NODE_POSITION_Y);
		Airport airport = Initializer.initAirport(address, node);
		HibernateGeneric.saveOrUpdateObject(node);
		HibernateGeneric.saveOrUpdateObject(address);
		HibernateGeneric.saveOrUpdateObject(airport);
		return airport;
		
	}
}


