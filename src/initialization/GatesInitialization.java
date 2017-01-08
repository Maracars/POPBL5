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
	/** The Constant TERMINALS_JSON_FILE. */
	private static final String HEATHROW_NODES_JSON_FILE = "HeathrowNodes.json";

	private static final String[][] HEATHROW_GATES_NODES = { { "B", "A" }, { "A", "C" }, { "C", "P" }, { "C", "D" },
			{ "P", "Q" }, { "D", "Q" }, { "Q", "R" }, { "R", "S" }, { "D", "E" }, { "E", "R" }, { "E", "F" },
			{ "F", "S" }, { "F", "G" }, { "G", "T" }, { "S", "T" }, { "G", "H" }, { "T", "U" }, { "H", "U" },
			{ "H", "I" }, { "U", "V" }, { "I", "V" }, { "I", "J" }, { "V", "EZDAKIT" }, { "EZDAKIT", "W" },
			{ "J", "W" }, { "J", "K" }, { "W", "X" }, { "K", "X" }, { "K", "L" }, { "X", "Y" }, { "L", "Y" },
			{ "L", "M" }, { "Y", "Z" }, { "M", "Z" }, { "M", "N" }, { "Z", "ZEIZEN" }, { "N", "ZEIZEN" }, { "N", "69" },
			{ "ZEIZEN", "JARRI" }, { "69", "JARRI" }, { "69", "O" }, { "JARRI", "JAJAXD" }, { "JAJAXD", "666" },
			{ "O", "666" }, { "Q", "1" }, { "R", "4" }, { "1", "4" }, { "1", "2" }, { "4", "5" }, { "2", "5" },
			{ "2", "3" }, { "5", "6" }, { "3", "6" }, { "3", "20" }, { "6", "21" }, { "S", "22" }, { "T", "23" },
			{ "V", "7" }, { "EZDAKIT", "10" }, { "7", "10" }, { "7", "8" }, { "10", "11" }, { "8", "11" }, { "8", "9" },
			{ "9", "12" }, { "11", "12" }, { "9", "24" }, { "12", "25" }, { "JAJAXD", "13" }, { "666", "16" },
			{ "13", "16" }, { "13", "14" }, { "16", "17" }, { "14", "17" }, { "14", "15" }, { "17", "18" },
			{ "15", "18" }, { "15", "28" }, { "18", "36" }, { "19", "20" }, { "20", "21" }, { "21", "22" },
			{ "22", "23" }, { "23", "24" }, { "24", "25" }, { "25", "??" }, { "??", "26" }, { "26", "27" },
			{ "27", "28" }, { "28", "36" }, { "19", "37" }, { "20", "38" }, { "21", "39" }, { "22", "40" },
			{ "24", "41" }, { "25", "42" }, { "26", "43" }, { "27", "44" }, { "??", "45" }, { "37", "38" },
			{ "38", "39" }, { "39", "40" }, { "40", "41" }, { "41", "42" }, { "42", "43" }, { "43", "44" },
			{ "44", "45" } };

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
		loadNodesJSON();
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
				url = getClass().getResource(CGATES_JSON_FILE);
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

	public List<Node> loadNodesJSON() {
		List<Object> node = HibernateGeneric.loadAllObjects(new Node());
		List<Node> nodeList = null;
		if (!node.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				URL url = getClass().getResource(HEATHROW_NODES_JSON_FILE);
				System.out.println(url);
				nodeList = mapper.readValue(new File(url.getPath()), new TypeReference<List<Node>>() {
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return nodeList;
	}

	private Node getNodeByName(List<Node> nodeList, String name) {
		for (Node node : nodeList) {
			if (name.equals(node.getName())) {
				return node;
			}
		}
		return null;
	}
}
