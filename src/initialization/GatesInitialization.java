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

	private static final int PATH_NUMBER_ = 2;

	private static final int END_LANE = 1;

	private static final int START_LANE = 0;

	private static final int NODE_INFO_ROWS = 3;

	private static final int PATH_NUMBER = 106;

	private static final String HEATHROW_LANES_JSON = "HeathrowLanes.json";

	private static final String STRING_LINE = "-";

	private static final double CARACAS_POSY = -66.817;

	private static final double CARACAS_POSX = 10.288;

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
	private static String[][] HEATHROW_LANES_NODES;

	/** The node list. */
	private List<Node> nodeList;

	/** The lane list. */
	private List<Lane> laneList = new ArrayList<Lane>();

	/** The locale airport. */
	private Airport localeAirport = null;

	private List<Terminal> terminalList;

	private static List<Path> pathList = new ArrayList<Path>();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		MainThread.finishSimulator();
	}

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

	private void createGates() {
		/*
		 * BEREZ hau in beharko zan baia probetako MIERDA bat programauko dot :D
		 * List<Gate> gatesList = loadGatesJSON(); for (Gate gate : gatesList) {
		 * Random random = new Random();
		 * gate.setTerminal(terminalList.get(random.nextInt(terminalList.size())
		 * )); gate.setFree(true);
		 * HibernateGeneric.saveObject(gate.getPositionNode());
		 * HibernateGeneric.saveObject(gate); }
		 */

		for (Node node : nodeList) {
			switch (node.getName()) {
			case "NIDEA":
			case "4":
			case "2":
			case "13":
			case "18":
			case "24":
			case "39":
				Gate gate = new Gate();
				Random random = new Random();

				gate.setTerminal(terminalList.get(0));
				gate.setFree(true);
				gate.setPositionNode(node);
				HibernateGeneric.saveObject(gate);
			default:
				break;
			}
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

		for (Integer i = 1; i <= PATH_NUMBER; i++) {
			path = getPath(i.toString());

			if (path != null) {
				pathList.add(path);
				HibernateGeneric.saveObject(path);
			}
		}
	}

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
