package domain.model;

import java.util.concurrent.Semaphore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Lane {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private boolean status;

	@ManyToOne(optional = false)
	private Node startNode;

	@ManyToOne(optional = false)
	private Node endNode;

	// konprobau behar da ia benetan ez dauen sortuten
	@Transient
	private Semaphore semaphore;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public boolean isFree() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

}
