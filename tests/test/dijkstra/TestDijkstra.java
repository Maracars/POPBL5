package test.dijkstra;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import domain.model.Lane;
import domain.model.Node;
import domain.model.Path;
import helpers.Dijkstra;

public class TestDijkstra {

	private static final String GOT_MORE_THAN_A_POSIBLE_PATH = "GOT MORE THAN A POSIBLE PATH";
	private static final String PATH_EXISTS = "PATH EXISTS";
	private static final int FIVE = 5;
	private static final int EIGHT = 8;
	private static final int SIX = 6;
	private static final int NINE = 9;
	private static final int TEN = 10;
	private static final int ELEVEN = 11;
	private static final int SEVEN = 7;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int TWO = 2;
	private static final int ONE = 1;
	private static final int _0 = 0;
	private static final int TWELVE = 12;
	private static final int END_NODE = 11;
	private static final int INIT_NODE = 9;
	private static final String NOT_SAME_ARRAYS = "error, arrays are not the same";
	private static List<Node> nodes;
	private static List<Path> paths;
	private static Dijkstra dijkstra;

	@BeforeClass
	public static void beforeTests() {
		initializePaths();

		dijkstra = new Dijkstra(paths);
	}

	@Test
	public void testExcuteArrival() {

		dijkstra.execute(nodes.get(INIT_NODE));
		LinkedList<Path> paths = dijkstra.getPath(nodes.get(END_NODE));

		assertNotNull(PATH_EXISTS, paths);
		assertTrue(GOT_MORE_THAN_A_POSIBLE_PATH, paths.size() > 0);

	}

	@Test
	public void testExcuteDeparture() {

		dijkstra.execute(nodes.get(INIT_NODE));
		LinkedList<Path> paths = dijkstra.getPath(nodes.get(END_NODE));

		assertNotNull(PATH_EXISTS, paths);
		assertTrue(GOT_MORE_THAN_A_POSIBLE_PATH, paths.size() > 0);

	}

	@Test
	public void testExecuteBothCheckAreEqual() {

		dijkstra.execute(nodes.get(END_NODE));
		LinkedList<Path> pathsArrive = dijkstra.getPath(nodes.get(INIT_NODE));

		assertNotNull(PATH_EXISTS, pathsArrive);
		assertTrue(GOT_MORE_THAN_A_POSIBLE_PATH, pathsArrive.size() > 0);

		dijkstra.execute(nodes.get(INIT_NODE));
		LinkedList<Path> pathsDeparture = dijkstra.getPath(nodes.get(END_NODE));

		assertNotNull(PATH_EXISTS, pathsDeparture);
		assertTrue(GOT_MORE_THAN_A_POSIBLE_PATH, pathsDeparture.size() > 0);

		int j = pathsArrive.size() - 1;
		for (int i = 0; i < pathsArrive.size() - 1; i++) {
			assertEquals(NOT_SAME_ARRAYS, pathsArrive.get(i).getId(), pathsDeparture.get(j).getId());
			j--;
		}
	}

	private static void addLane(String laneId, int sourceLocNo, int destLocNo) {
		Node src = nodes.get(sourceLocNo);
		Node dst = nodes.get(destLocNo);

		Lane lane = new Lane();
		lane.setStartNode(src);
		lane.setEndNode(dst);

		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);

		Path path = new Path();
		path.setLaneList(laneList);
		path.setId(sourceLocNo * TEN + destLocNo);

		paths.add(path);
	}

	private static void initializePaths() {
		nodes = new ArrayList<Node>();
		paths = new ArrayList<Path>();
		for (int i = 0; i < TWELVE; i++) {
			Node positionNode = new Node();
			positionNode.setId(i);
			positionNode.setName("Node_" + i);
			positionNode.setPositionX(i * 1.0);
			positionNode.setPositionY(0);
			nodes.add(positionNode);
		}

		addLane("Edge_0", _0, ONE);
		addLane("Edge_1", _0, TWO);
		addLane("Edge_2", _0, FOUR);
		addLane("Edge_3", TWO, SIX);
		addLane("Edge_4", TWO, SEVEN);
		addLane("Edge_5", THREE, SEVEN);
		addLane("Edge_6", FIVE, EIGHT);
		addLane("Edge_7", EIGHT, NINE);
		addLane("Edge_8", SEVEN, NINE);
		addLane("Edge_9", FOUR, NINE);
		addLane("Edge_10", NINE, TEN);
		addLane("Edge_11", ONE, TEN);
		addLane("Edge_12", SEVEN, ELEVEN);
		addBigPath();
	}

	private static void addBigPath() {
		ArrayList<Lane> laneList = new ArrayList<>();

		Lane lane1 = new Lane();
		lane1.setStartNode(nodes.get(TWO));
		lane1.setEndNode(nodes.get(THREE));

		laneList.add(lane1);

		Lane lane2 = new Lane();
		lane2.setStartNode(nodes.get(THREE));
		lane2.setEndNode(nodes.get(SEVEN));

		laneList.add(lane2);

		Path path = new Path();
		path.setLaneList(laneList);
		path.setId(_0);

		paths.add(path);

	}

}
