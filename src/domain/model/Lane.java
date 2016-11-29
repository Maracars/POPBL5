package domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Lane {

	@Id
	@GeneratedValue
	Integer id;

	// TODO laneType definitu
	
	Node startNode;

	Node endNode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

}
