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

import domain.dao.DAOAirport;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Address;
import domain.model.Airport;
import domain.model.Gate;
import domain.model.Node;
import domain.model.Terminal;

// TODO: Auto-generated Javadoc
/**
 * The Class GatesInitialization.
 */
public class GatesInitialization implements ServletContextListener {

	/** The Constant FIRST_AIRPORT. */
	private static final int FIRST_AIRPORT = 0;

	/** The Constant NODE_POSITION_Y. */
	private static final double NODE_POSITION_Y = -0.461389;

	/** The Constant NODE_POSITION_X. */
	private static final double NODE_POSITION_X = 51.4775;

	/** The Constant NODE_NAME. */
	private static final String NODE_NAME = "Heathrow";

	/** The Constant AGATES_JSON_FILE. */
	private static final String AGATES_JSON_FILE = "AGates.json";

	/** The Constant BGATES_JSON_FILE. */
	private static final String BGATES_JSON_FILE = "BGates.json";

	/** The Constant CGATES_JSON_FILE. */
	private static final String CGATES_JSON_FILE = "CGates.json";

	/** The Constant TERMINALS_JSON_FILE. */
	private static final String TERMINALS_JSON_FILE = "Terminal.json";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Airport airport = null;

		airport = DAOAirport.getLocaleAirport();
		if (airport == null) {
			airport = createAirport();
		}
		List<Terminal> terminalList = loadTerminalsJSON();
		if (terminalList != null && HibernateGeneric.loadAllObjects(new Terminal()) != null) {
			for (Terminal terminal : terminalList) {
				terminal.setAirport(airport);
				HibernateGeneric.saveObject(terminal.getPositionNode());
				HibernateGeneric.saveObject(terminal);
			}
			List<Gate> gatesList = loadGatesJSON();
			if (gatesList != null && HibernateGeneric.loadAllObjects(new Gate()) != null) {
				for (Gate gate : gatesList) {
					Random random = new Random();
					gate.setTerminal(terminalList.get(random.nextInt(terminalList.size())));
					HibernateGeneric.saveObject(gate.getPositionNode());
					HibernateGeneric.saveObject(gate);
				}
			}
		}
	}

	/**
	 * Load gates JSON.
	 *
	 * @return the list
	 */
	public List<Gate> loadGatesJSON() {
		List<Object> gates = HibernateGeneric.loadAllObjects(new Gate());
		List<Gate> gateList = null;
		if (gates.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				URL url = getClass().getResource(AGATES_JSON_FILE);
				System.out.println(url);
				gateList = mapper.readValue(new File(url.getPath()), new TypeReference<List<Gate>>() {
				});
				url = getClass().getResource(BGATES_JSON_FILE);
				System.out.println(url);
				gateList.addAll(mapper.readValue(new File(url.getPath()), new TypeReference<List<Gate>>() {
				}));
				url = getClass().getResource(CGATES_JSON_FILE);
				System.out.println(url);
				gateList.addAll(mapper.readValue(new File(url.getPath()), new TypeReference<List<Gate>>() {
				}));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return gateList;
	}

	/**
	 * Load terminals JSON.
	 *
	 * @return the list
	 */
	public List<Terminal> loadTerminalsJSON() {
		List<Object> terminal = HibernateGeneric.loadAllObjects(new Terminal());
		List<Terminal> terminalList = null;
		if (terminal.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				URL url = getClass().getResource(TERMINALS_JSON_FILE);
				System.out.println(url);
				terminalList = mapper.readValue(new File(url.getPath()), new TypeReference<List<Terminal>>() {
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return terminalList;
	}

	/**
	 * Creates the airport.
	 *
	 * @return the airport
	 */
	public Airport createAirport() {
		Address address = Initializer.initAddress();
		Node node = new Node();
		node.setName(NODE_NAME);
		node.setPositionX(NODE_POSITION_X);
		node.setPositionY(NODE_POSITION_Y);
		Airport airport = Initializer.initAirport(address, node);
		airport.setLocale(true);
		HibernateGeneric.saveObject(node);
		HibernateGeneric.saveObject(address);
		HibernateGeneric.saveObject(airport);
		return airport;

	}
}
