package initialization;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.dao.DAOAirport;
import domain.dao.DAOPath;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Address;
import domain.model.Airport;
import domain.model.Gate;
import domain.model.Lane;
import domain.model.Node;
import domain.model.Path;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.Route;
import domain.model.Terminal;
import simulator.MainThread;

// TODO: Auto-generated Javadoc
/**
 * The Class GatesInitialization.
 */
public class GatesInitialization implements ServletContextListener {

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

	/** The Constant HEATHROW_LANES_NODES. */
	private static final String[][] HEATHROW_LANES_NODES = { { "B", "A", "1" }, { "A", "C", "1" }, { "C", "P", "2" },
			{ "C", "D", "3" }, { "P", "Q", "2" }, { "D", "Q", "30" }, { "Q", "R", "29" }, { "R", "S", "28" },
			{ "D", "E", "4" }, { "E", "R", "31" }, { "E", "F", "5" }, { "F", "S", "32" }, { "F", "G", "6" },
			{ "G", "T", "33" }, { "S", "T", "27" }, { "G", "H", "7" }, { "T", "U", "26" }, { "H", "U", "34" },
			{ "H", "I", "8" }, { "U", "V", "25" }, { "I", "V", "35" }, { "I", "J", "9" }, { "V", "EZDAKIT", "24" },
			{ "EZDAKIT", "W", "23" }, { "J", "W", "36" }, { "J", "K", "10" }, { "W", "X", "22" }, { "K", "X", "37" },
			{ "K", "L", "11" }, { "X", "Y", "21" }, { "L", "Y", "38" }, { "L", "M", "12" }, { "Y", "Z", "20" },
			{ "M", "Z", "40" }, { "M", "N", "13" }, { "Z", "ZEIZEN", "19" }, { "N", "ZEIZEN", "41" },
			{ "N", "69", "14" }, { "ZEIZEN", "JARRI", "18" }, { "69", "JARRI", "42" }, { "69", "O", "15" },
			{ "JARRI", "JAJAXD", "17" }, { "JAJAXD", "666", "16" }, { "O", "666", "15" }, { "Q", "1", "43" },
			{ "R", "4", "44" }, { "1", "4", "61" }, { "1", "2", "51" }, { "4", "5", "52" }, { "2", "5", "62" },
			{ "2", "3", "57" }, { "5", "6", "58" }, { "3", "6", "63" }, { "3", "20", "59" }, { "6", "21", "60" },
			{ "S", "22", "45" }, { "T", "23", "46" }, { "V", "7", "47" }, { "EZDAKIT", "10", "48" },
			{ "7", "10", "68" }, { "7", "8", "53" }, { "10", "11", "54" }, { "10", "NARANA", "71" },
			{ "NARANA", "NIDEA", "71" }, { "8", "11", "69" }, { "8", "9", "64" }, { "9", "12", "70" },
			{ "11", "12", "65" }, { "9", "24", "66" }, { "12", "25", "67" }, { "JAJAXD", "13", "49" },
			{ "666", "16", "50" }, { "13", "16", "76" }, { "13", "14", "55" }, { "16", "17", "56" },
			{ "14", "17", "68" }, { "14", "15", "72" }, { "17", "18", "73" }, { "15", "18", "78" },
			{ "15", "27", "74" }, { "18", "28", "75" }, { "20", "19", "80" }, { "20", "21", "81" },
			{ "21", "22", "82" }, { "22", "23", "83" }, { "23", "24", "84" }, { "24", "25", "85" },
			{ "25", "NIDEA", "86" }, { "NIDEA", "26", "87" }, { "26", "27", "88" }, { "27", "28", "89" },
			{ "36", "28", "90" }, { "19", "37", "80" }, { "20", "38", "91" }, { "21", "39", "92" },
			{ "22", "40", "93" }, { "23", "41", "94" }, { "24", "42", "95" }, { "25", "43", "96" },
			{ "26", "44", "97" }, { "27", "45", "98" }, { "28", "46", "99" }, { "37", "38", "80" },
			{ "38", "39", "100" }, { "39", "40", "101" }, { "40", "41", "102" }, { "41", "42", "103" },
			{ "42", "43", "104" }, { "43", "44", "105" }, { "44", "45", "106" }, { "46", "45", "99" },
			{ "54", "55", "90" }, { "55", "36", "90" } };

	/** The node list. */
	private List<Node> nodeList;

	/** The lane list. */
	private List<Lane> laneList = new ArrayList<Lane>();

	/** The locale airport. */
	private Airport localeAirport = null;

	private List<Terminal> terminalList;

	private List<Path> pathList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		MainThread.finishSimulator();
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

		localeAirport = DAOAirport.getLocaleAirport();
		if (localeAirport == null) {
			localeAirport = createAirport();
			nodeList = loadNodesJSON();
			createLanes();
			createPaths();
			createPlaneModel();
			createTerminals();
			createGates();
			insertRoutes();

		} else {
			// TODO FUNTZIO HAU FALTA DA ARREGLETAKO
			getPathsFromDatabase();

		}
		System.out.println("List of paths: " + pathList);
		MainThread.initSimulator(localeAirport, pathList);

	}

	private void getPathsFromDatabase() {
		// TODO hemen path guztiek datubasetik kargau behar dire
		List<Object> list = HibernateGeneric.loadAllObjects(new Path());
		for (Object object : list) {
			Path path = (Path) object;
			// honek forak eztait funtzionauko dauen, ointxe bertan ipiniot, 
			//baia berez path guztiek get inde gero, lanetako semaforoak inizializau in behar dire.
			for(Lane lane : path.getLaneList()){
				if(lane.isFree()){
					lane.setSemaphore(new Semaphore(1,true));
				}else{
					lane.setSemaphore(new Semaphore(0,true));
				}
			}
			
			pathList.add(path);
		}
		
	}

	private void createGates() {
		List<Gate> gatesList = loadGatesJSON();
		for (Gate gate : gatesList) {
			Random random = new Random();
			gate.setTerminal(terminalList.get(random.nextInt(terminalList.size())));
			gate.setFree(true);
			HibernateGeneric.saveObject(gate.getPositionNode());
			HibernateGeneric.saveObject(gate);
		}
	}

	private void createTerminals() {
		terminalList = loadTerminalsJSON();
		for (Terminal terminal : terminalList) {
			terminal.setAirport(localeAirport);
			HibernateGeneric.saveObject(terminal.getPositionNode());
			HibernateGeneric.saveObject(terminal);
		}
	}

	private void createPlaneModel() {
		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.saveObject(planeModel);

	}

	/**
	 * Load gates JSON. Class that contains dummy functions that initializes
	 * model objects with predefined data
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

	/**
	 * Load nodes JSON.
	 *
	 * @return the list
	 */
	public List<Node> loadNodesJSON() {
		List<Object> nodes = HibernateGeneric.loadAllObjects(new Node());
		if (!nodes.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				URL url = getClass().getResource(HEATHROW_NODES_JSON_FILE);
				nodeList = mapper.readValue(new File(url.getPath()), new TypeReference<List<Node>>() {
				});
				for (Node node : nodeList) {
					HibernateGeneric.saveObject(node);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return nodeList;
	}

	/**
	 * Gets the node by name.
	 *
	 * @param name
	 *            the name
	 * @return the node by name
	 */
	private Node getNodeByName(String name) {
		for (Node node : nodeList) {
			if (name.equals(node.getName())) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Gets the lane by name.
	 *
	 * @param name
	 *            the name
	 * @return the lane by name
	 */
	private Lane getLaneByName(String name) {
		for (Lane lane : laneList) {
			if (name.equals(lane.getName())) {
				return lane;
			}
		}
		return null;
	}

	/**
	 * Creates the lanes.
	 */
	private void createLanes() {
		String[] principalNodes = { "B", "54" };
		for (String[] laneNodes : HEATHROW_LANES_NODES) {
			Lane lane = new Lane();

			lane.setStartNode(getNodeByName(laneNodes[0]));
			lane.setEndNode(getNodeByName(laneNodes[1]));
			lane.setName(laneNodes[0] + "-" + laneNodes[1]);
			if (Arrays.asList(principalNodes).contains(laneNodes[0])) {
				lane.setType(Lane.PRINCIPAL);

			} else {
				lane.setType(Lane.SECONDARY);
			}
			lane.setStatus(true);
			lane.setAirport(localeAirport);
			lane.setSemaphore(new Semaphore(1, true));
			laneList.add(lane);
			HibernateGeneric.saveObject(lane);

		}
	}

	/**
	 * Gets the path.
	 *
	 * @param number
	 *            the number
	 * @return the path
	 */
	private Path getPath(String number) {
		ArrayList<Lane> laneList2 = new ArrayList<>();
		Path path = new Path();
		Lane lane = null;

		for (String[] heathrowNodes : HEATHROW_LANES_NODES) {

			if (number.equals(heathrowNodes[2])) {
				String laneName = heathrowNodes[0] + "-" + heathrowNodes[1];
				lane = getLaneByName(laneName);
				if (lane != null) {
					laneList2.add(lane);

				}
			}
		}
		path.setLaneList(laneList2);
		return path;

	}

	/**
	 * Creates the paths.
	 */
	private void createPaths() {
		pathList = new ArrayList<Path>();
		Path path = null;
		for (Integer i = 1; i <= 106; i++) {
			path = getPath(i.toString());
			pathList.add(path);
			HibernateGeneric.saveObject(path);
		}
	}

	public void insertRoutes() {
		Terminal departureTerminal = terminalList.get(0);

		// HibernateGeneric.saveObject(departureTerminal);

		Address address = Initializer.initAddress();
		Airport arrivalAirport = Initializer.initAirport(address, nodeList.get(0));
		arrivalAirport.setLocale(false);
		Terminal arrivalTerminal = Initializer.initTerminal(arrivalAirport);

		HibernateGeneric.saveObject(address);
		HibernateGeneric.saveObject(arrivalAirport);
		HibernateGeneric.saveObject(arrivalTerminal);

		Route route = Initializer.initRoute(arrivalTerminal, departureTerminal);
		Route route2 = Initializer.initRoute(departureTerminal, arrivalTerminal);

		HibernateGeneric.saveObject(route);
		HibernateGeneric.saveObject(route2);

	}
}
