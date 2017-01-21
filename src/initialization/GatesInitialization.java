package initialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.dao.DAOAirport;
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

/**
 * The Class GatesInitialization.
 */
public class GatesInitialization implements ServletContextListener {

	/** The Constant PATH_NUMBER_. */
	private static final int PATH_NUMBER_ = 2;

	/** The Constant END_LANE. */
	private static final int END_LANE = 1;

	/** The Constant START_LANE. */
	private static final int START_LANE = 0;

	/** The Constant NODE_INFO_ROWS. */
	private static final int NODE_INFO_ROWS = 3;

	/** The Constant HEATHROW_LANES_JSON. */
	private static final String HEATHROW_LANES_JSON = "HeathrowLanes.json";

	/** The Constant STRING_LINE. */
	private static final String STRING_LINE = "-";

	/** The Constant CARACAS_POSY. */
	private static final double CARACAS_POSY = -66.817;

	/** The Constant CARACAS_POSX. */
	private static final double CARACAS_POSX = 10.288;

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

	private static final String HEATRHOW_AIRPORT_COUNTRY = "United Kingdom";

	private static final String HEATRHOW_AIRPORT_REGION = "Greater London";

	private static final String HEATRHOW_AIRPORT_CITY = "Hounslow";

	private static final String HEATRHOW_AIRPORT_POSTCODE = "TW3";

	private static final String HEATRHOW_AIRPORT_STREETANDNUMBER = "Nelson Road";

	private static final String HEATRHOW_AIRPORT_NODE_NAME = "Heathrow center";

	private static final double HEATRHOW_AIRPORT_LATITUDE = 51.4775;

	private static final double HEATRHOW_AIRPORT_LONGITUDE = -0.461389;

	private static final Integer HEATRHOW_AIRPORT_MAX_FLIGHTS = 6;

	/** The Constant HEATHROW_LANES_NODES. */
	private static String[][] HEATHROW_LANES_NODES;

	/** The node list. */
	private List<Node> nodeList;

	/** The lane list. */
	private List<Lane> laneList = new ArrayList<Lane>();

	/** The locale airport. */
	private Airport localeAirport = null;

	/** The terminal list. */
	private List<Terminal> terminalList;

	/** The path list. */
	private static List<Path> pathList = new ArrayList<Path>();

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

			initAirport();

		} else {
			getPathsFromDatabase();

		}
		MainThread.initSimulator(localeAirport, pathList);

	}

	/**
	 * Inits the airport.
	 */
	private void initAirport() {
		createHeathrowLanes();
		localeAirport = createAirport();
		nodeList = loadNodesJSON();
		createLanes();
		createPaths();
		createPlaneModel();
		createTerminals();
		createGates();
		insertRoutes();
	}

	/**
	 * Gets the paths from database.
	 *
	 * @return the paths from database
	 */
	public static List<Path> getPathsFromDatabase() {
		try {
			List<Object> list = HibernateGeneric.loadAllObjects(new Path());
			for (Object object : list) {
				Path path = (Path) object;
				for (Lane lane : path.getLaneList()) {
					if (lane.isFree()) {
						lane.setSemaphore(new Semaphore(1, true));
					} else {
						lane.setSemaphore(new Semaphore(0, true));
					}
				}

				pathList.add(path);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathList;

	}

	/**
	 * Creates the heathrow lanes.
	 */
	private void createHeathrowLanes() {
		JSONParser parser = new JSONParser();

		try {
			URL url = getClass().getResource(HEATHROW_LANES_JSON);
			Object object = parser.parse(new FileReader(url.getPath()));

			JSONArray arr = (JSONArray) object;

			HEATHROW_LANES_NODES = new String[arr.size()][NODE_INFO_ROWS];
			for (int i = 0; i < arr.size(); i++) {
				org.json.simple.JSONObject jo = (org.json.simple.JSONObject) arr.get(i);

				HEATHROW_LANES_NODES[i][START_LANE] = (String) jo.get("laneStart");
				HEATHROW_LANES_NODES[i][END_LANE] = (String) jo.get("laneEnd");
				HEATHROW_LANES_NODES[i][PATH_NUMBER_] = (String) jo.get("laneNumber");
			}

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the gates.
	 */
	private void createGates() {
		/*
		 * The correct function is the following one, but the gates of the json
		 * aren't linked to the lanes yet.
		 * 
		 * List<Gate> gatesList = loadGatesJSON(); for (Gate gate : gatesList) {
		 * Random random = new Random();
		 * gate.setTerminal(terminalList.get(random.nextInt(terminalList.size())
		 * )); gate.setFree(true);
		 * HibernateGeneric.saveObject(gate.getPositionNode());
		 * HibernateGeneric.saveObject(gate); }
		 */

		for (Node node : nodeList) {
			switch (node.getName()) {
			case "56":
			case "57":
			case "58":
			case "59":
			case "60":
			case "61":
			case "62":
				Gate gate = new Gate();
				Random random = new Random();

				gate.setTerminal(terminalList.get(0));
				gate.setFree(true);
				gate.setPositionNode(node);
				gate.setNumber(random.nextInt(40));
				HibernateGeneric.saveObject(gate);
			default:
				break;
			}
		}

	}

	/**
	 * Creates the terminals.
	 */
	private void createTerminals() {
		terminalList = loadTerminalsJSON();
		for (Terminal terminal : terminalList) {
			terminal.setAirport(localeAirport);
			HibernateGeneric.saveObject(terminal.getPositionNode());
			HibernateGeneric.saveObject(terminal);
		}
	}

	/**
	 * Creates the plane model.
	 */
	private void createPlaneModel() {
		PlaneMaker planeMaker = Initializer.initPlaneMaker();
		HibernateGeneric.saveObject(planeMaker);

		PlaneModel planeModel = Initializer.initPlaneModel(planeMaker);
		HibernateGeneric.saveObject(planeModel);

	}

	/**
	 * 
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

				gateList = mapper.readValue(new File(url.getPath()), new TypeReference<List<Gate>>() {
				});
				url = getClass().getResource(BGATES_JSON_FILE);

				gateList.addAll(mapper.readValue(new File(url.getPath()), new TypeReference<List<Gate>>() {
				}));
				url = getClass().getResource(CGATES_JSON_FILE);

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

		Address address = new Address();
		address.setCountry(HEATRHOW_AIRPORT_COUNTRY);
		address.setRegion(HEATRHOW_AIRPORT_REGION);
		address.setCity(HEATRHOW_AIRPORT_CITY);
		address.setPostCode(HEATRHOW_AIRPORT_POSTCODE);
		address.setStreetAndNumber(HEATRHOW_AIRPORT_STREETANDNUMBER);
		HibernateGeneric.saveObject(address);

		Node positionNode = new Node();
		positionNode.setName(HEATRHOW_AIRPORT_NODE_NAME);
		positionNode.setPositionX(HEATRHOW_AIRPORT_LATITUDE);
		positionNode.setPositionY(HEATRHOW_AIRPORT_LONGITUDE);
		HibernateGeneric.saveObject(positionNode);

		Airport airport = new Airport();
		airport.setAddress(address);
		airport.setLocale(true);
		airport.setMaxFlights(HEATRHOW_AIRPORT_MAX_FLIGHTS);
		airport.setName(HEATRHOW_AIRPORT_NODE_NAME);
		airport.setPositionNode(positionNode);
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

			lane.setName(laneNodes[0] + STRING_LINE + laneNodes[1]);
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

				String laneName = heathrowNodes[0] + STRING_LINE + heathrowNodes[1];
				lane = getLaneByName(laneName);
				if (lane != null) {
					laneList2.add(lane);

				}
			}
		}

		if (laneList2.size() > 0) {
			path.setLaneList(laneList2);
		} else {
			path = null;
		}
		return path;

	}

	/**
	 * Creates the paths.
	 */
	private void createPaths() {
		pathList = new ArrayList<Path>();
		Path path = null;

		for (Integer i = 1; i <= HEATHROW_LANES_NODES.length; i++) {
			path = getPath(i.toString());

			if (path != null) {
				pathList.add(path);
				HibernateGeneric.saveObject(path);
			}
		}
	}

	/**
	 * Insert routes.
	 */
	public void insertRoutes() {
		Terminal departureTerminal = terminalList.get(0);

		// HibernateGeneric.saveObject(departureTerminal);

		Address address = Initializer.initAddress();
		Node node = new Node();
		node.setName("Node OUT");
		node.setPositionX(CARACAS_POSX);
		node.setPositionY(CARACAS_POSY);
		HibernateGeneric.saveObject(node);
		Airport arrivalAirport = Initializer.initAirport(address, node);
		arrivalAirport.setLocale(false);
		Terminal arrivalTerminal = Initializer.initTerminal(arrivalAirport, node);

		HibernateGeneric.saveObject(address);
		HibernateGeneric.saveObject(arrivalAirport);
		HibernateGeneric.saveObject(arrivalTerminal);

		Route route = Initializer.initRoute(arrivalTerminal, departureTerminal);
		Route route2 = Initializer.initRoute(departureTerminal, arrivalTerminal);

		HibernateGeneric.saveObject(route);
		HibernateGeneric.saveObject(route2);

	}
}
