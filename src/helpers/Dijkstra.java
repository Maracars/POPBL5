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

public class Dijkstra {

    private final List<Node> nodes;
    private final List<Path> paths;
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Double> distance;

    public Dijkstra(Graph graph) {
            // create a copy of the array so that we can operate on this array
            this.nodes = new ArrayList<Node>(graph.getNodes());
            this.paths = new ArrayList<Path>(graph.getPath());
    }

    public void execute(Node source) {
            settledNodes = new HashSet<Node>();
            unSettledNodes = new HashSet<Node>();
            distance = new HashMap<Node, Double>();
            predecessors = new HashMap<Node, Node>();
            distance.put(source, 0.0);
            unSettledNodes.add(source);
            while (unSettledNodes.size() > 0) {
                    Node node = getMinimum(unSettledNodes);
                    settledNodes.add(node);
                    unSettledNodes.remove(node);
                    findMinimalDistances(node);
            }
    }

    private void findMinimalDistances(Node node) {
            List<Node> adjacentNodes = getNeighbors(node);
            for (Node target : adjacentNodes) {
                    if (getShortestDistance(target) > getShortestDistance(node)
                                    + getDistance(node, target)) {
                            distance.put(target, getShortestDistance(node)
                                            + getDistance(node, target));
                            predecessors.put(target, node);
                            unSettledNodes.add(target);
                    }
            }

    }

    private double getDistance(Node node, Node target) {
            for (Path path : paths) {
                    if (path.getLaneList().get(0).getStartNode().getId() == node.getId()
                                    && path.getLaneList().get(path.getLaneList().size() - 1).getEndNode().getId() == target.getId()) {
                    	//
                            return path.getDistance();
                    }
            }
            throw new RuntimeException("Should not happen");
    }

    private List<Node> getNeighbors(Node node) {
            List<Node> neighbors = new ArrayList<Node>();
            for (Path path : paths) {
                    if (path.getLaneList().get(0).getStartNode().getId() == node.getId()
                                    && !isSettled(path.getLaneList().get(path.getLaneList().size() - 1).getEndNode())) {
                            neighbors.add(path.getLaneList().get(path.getLaneList().size() - 1).getEndNode());
                    }
            }
            return neighbors;
    }

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

    private boolean isSettled(Node vertex) {
            return settledNodes.contains(vertex);
    }

    private Double getShortestDistance(Node vertex) {
            Double d = distance.get(vertex);
            if (d == null) {
                    return Double.MAX_VALUE;
            } else {
                    return d;
            }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Node> getPath(Node target) {
            LinkedList<Node> path = new LinkedList<Node>();
            Node step = target;
            // check if a path exists
            if (predecessors.get(step) == null) {
                    return null;
            }
            path.add(step);
            while (predecessors.get(step) != null) {
                    step = predecessors.get(step);
                    path.add(step);
            }
            // Put it into the correct order
            Collections.reverse(path);
            return path;
    }

}