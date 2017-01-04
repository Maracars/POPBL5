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

		dijkstra.execute(nodes.get(0), Dijkstra.ARRIVAL_MODE);
		LinkedList<Path> paths = dijkstra.getPath(nodes.get(11));

		assertNotNull(paths);
		assertTrue(paths.size() > 0);

		for (Path path1 : paths) {
			System.out.println(path1);
		}

	}

	@Test
	public void testExcuteDeparture() {

		dijkstra.execute(nodes.get(11), Dijkstra.DEPARTURE_MODE);
		LinkedList<Path> paths = dijkstra.getPath(nodes.get(0));

		assertNotNull(paths);
		assertTrue(paths.size() > 0);

		for (Path path1 : paths) {
			System.out.println(path1);
		}

	}

	@Test
	public void testExecuteBothCheckAreEqual() {

		dijkstra.execute(nodes.get(0), Dijkstra.ARRIVAL_MODE);
		LinkedList<Path> pathsArrive = dijkstra.getPath(nodes.get(11));

		assertNotNull(pathsArrive);
		assertTrue(pathsArrive.size() > 0);

		dijkstra.execute(nodes.get(11), Dijkstra.DEPARTURE_MODE);
		LinkedList<Path> pathsDeparture = dijkstra.getPath(nodes.get(0));

		assertNotNull(pathsDeparture);
		assertTrue(pathsDeparture.size() > 0);

		int j = pathsArrive.size() - 1;
		for (int i = 0; i < pathsArrive.size() - 1; i++) {
			assertEquals(NOT_SAME_ARRAYS, pathsArrive.get(i).getId(), pathsDeparture.get(j).getId());
			j--;
		}
	}

	private static void addLane(String laneId, int sourceLocNo, int destLocNo) {
		Node src = nodes.get(sourceLocNo);
		Node dst = nodes.get(destLocNo);

		// Honek berez lane asko euki biharko littuzke baina bueno tt, lane
		// bakarrakin ingou probia
		Lane lane = new Lane();
		lane.setStartNode(src);
		lane.setEndNode(dst);

		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);

		Path path = new Path();
		path.setLaneList(laneList);
		path.setId(sourceLocNo * 10 + destLocNo);

		paths.add(path);
	}

	private static void initializePaths() {
		nodes = new ArrayList<Node>();
		paths = new ArrayList<Path>();
		for (int i = 0; i < 12; i++) {
			Node positionNode = new Node();
			positionNode.setId(i);
			positionNode.setName("Node_" + i);
			positionNode.setPositionX(i * 1.0);
			positionNode.setPositionY(0);
			nodes.add(positionNode);
		}

		addLane("Edge_0", 0, 1);
		addLane("Edge_1", 0, 2);
		addLane("Edge_2", 0, 4);
		addLane("Edge_3", 2, 6);
		addLane("Edge_4", 2, 7);
		addLane("Edge_5", 3, 7);
		addLane("Edge_6", 5, 8);
		addLane("Edge_7", 8, 9);
		addLane("Edge_8", 7, 9);
		addLane("Edge_9", 4, 9);
		addLane("Edge_10", 9, 10);
		addLane("Edge_11", 1, 10);
		addLane("Edge_12", 7, 11);
	}

}
