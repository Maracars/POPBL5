package helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import domain.model.Node;
import domain.model.Path;

// TODO: Auto-generated Javadoc
/**
 * The Class Dijkstra.
 * Class that contains the methots to find the best path throught the airports lanes
 */
public class Dijkstra {

	/** The Constant ARRIVAL_MODE. */
	public static final boolean ARRIVAL_MODE = true;

	/** The Constant DEPARTURE_MODE. */
	public static final boolean DEPARTURE_MODE = false;

	/** The paths. */
	private final List<Path> paths;

	/** The settled nodes. */
	private Set<Node> settledNodes;

	/** The un settled nodes. */
	private Set<Node> unSettledNodes;

	/** The predecessors. */
	private Map<Node, Node> predecessors;

	/** The distance. */
	private Map<Node, Double> distance;

	/** The execution mode. */
	private boolean executionMode;

	/**
	 * Instantiates a new dijkstra.
	 *
	 * @param paths
	 *            the paths
	 */
	public Dijkstra(List<Path> paths) {
		this.paths = paths;
	}

	/**
	 * Execute.
	 *
	 * @param source
	 *            the source
	 * @param mode
	 *            the mode
	 */
	public void execute(Node source, boolean mode) {
		settledNodes = new HashSet<Node>();
		unSettledNodes = new HashSet<Node>();
		distance = new HashMap<Node, Double>();
		predecessors = new HashMap<Node, Node>();
		executionMode = mode;
		distance.put(source, 0.0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Node node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	/**
	 * Find minimal distances.
	 *
	 * @param node
	 *            the node
	 */
	private void findMinimalDistances(Node node) {
		List<Node> adjacentNodes = getNeighbors(node);
		for (Node target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	/**
	 * Gets the path from two nodes.
	 *
	 * @param node
	 *            the node
	 * @param target
	 *            the target
	 * @return the path from two nodes
	 */
	private Path getPathFromTwoNodes(Node node, Node target) {

		for (Path path : paths) {
			if (executionMode == ARRIVAL_MODE) {
				if (checkPathExist(node, target, path))
					return path;
			} else {
				if (checkPathExist(target, node, path))
					return path;
			}
		}
		throw new RuntimeException("Should not happen");
	}

	/**
	 * Gets the distance.
	 *
	 * @param node
	 *            the node
	 * @param target
	 *            the target
	 * @return the distance
	 */
	private double getDistance(Node node, Node target) {
		for (Path path : paths) {
			if (checkPathExist(node, target, path))
				return path.getDistance();
		}
		throw new RuntimeException("Should not happen");
	}

	/**
	 * Check path exist.
	 *
	 * @param node
	 *            the node
	 * @param target
	 *            the target
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	private boolean checkPathExist(Node node, Node target, Path path) {
		boolean checker = false;
		if (path.getLaneList().get(0).getStartNode().getId() == node.getId()) {
			if (path.getLaneList().get(path.getLaneList().size() - 1).getEndNode().getId() == target.getId()) {
				checker = true;
			}
		}
		if (path.getLaneList().get(0).getStartNode().getId() == target.getId()) {
			if (path.getLaneList().get(path.getLaneList().size() - 1).getEndNode().getId() == node.getId()) {
				checker = true;
			}
		}
		return checker;
	}

	/**
	 * Gets the neighbors.
	 *
	 * @param node
	 *            the node
	 * @return the neighbors
	 */
	private List<Node> getNeighbors(Node node) {
		List<Node> neighbors = new ArrayList<Node>();
		for (Path path : paths) {
			if (executionMode == ARRIVAL_MODE) {
				if (path.getLaneList().get(0).getStartNode().getId() == node.getId()
						&& !isSettled(path.getLaneList().get(path.getLaneList().size() - 1).getEndNode())) {
					neighbors.add(path.getLaneList().get(path.getLaneList().size() - 1).getEndNode());
				}
			} else {
				if (path.getLaneList().get(0).getEndNode().getId() == node.getId()
						&& !isSettled(path.getLaneList().get(path.getLaneList().size() - 1).getStartNode())) {
					neighbors.add(path.getLaneList().get(path.getLaneList().size() - 1).getStartNode());
				}
			}

		}
		return neighbors;
	}

	/**
	 * Gets the minimum.
	 *
	 * @param unSettledNodes2
	 *            the un settled nodes 2
	 * @return the minimum
	 */
	private Node getMinimum(Set<Node> unSettledNodes2) {
		Node minimum = null;
		for (Node vertex : unSettledNodes2) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	/**
	 * Checks if is settled.
	 *
	 * @param vertex
	 *            the vertex
	 * @return true, if is settled
	 */
	private boolean isSettled(Node vertex) {
		return settledNodes.contains(vertex);
	}

	/**
	 * Gets the shortest distance.
	 *
	 * @param vertex
	 *            the vertex
	 * @return the shortest distance
	 */
	private Double getShortestDistance(Node vertex) {
		Double d = distance.get(vertex);
		if (d == null) {
			return Double.MAX_VALUE;
		} else {
			return d;
		}
	}

	/**
	 * Gets the path.
	 *
	 * @param target
	 *            the target
	 * @return the path
	 */
	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 */
	public LinkedList<Path> getPath(Node target) {
		LinkedList<Node> path = new LinkedList<Node>();
		LinkedList<Path> pathList = new LinkedList<Path>();
		Node step = target;
		Node stepBefore;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			stepBefore = step;
			step = predecessors.get(step);
			path.add(step);
			pathList.add(getPathFromTwoNodes(step, stepBefore));
		}
		// Put it into the correct order
		Collections.reverse(path);
		Collections.reverse(pathList);

		return pathList;
	}

}