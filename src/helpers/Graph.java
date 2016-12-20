package helpers;

import java.util.List;

import domain.model.Node;
import domain.model.Path;

public class Graph {
	
	//Node vertex
	//Edge path
	
    private final List<Node> nodes;
    private final List<Path> paths;

    public Graph(List<Node> nodes, List<Path> paths) {
            this.nodes = nodes;
            this.paths = paths;
    }

    public List<Node> getNodes() {
            return nodes;
    }

    public List<Path> getPath() {
            return paths;
    }

}
