package domain.model;

import java.util.concurrent.Semaphore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Lane {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	@Column(nullable = false)
	private Boolean status;

	@Column(nullable = false)
	private Boolean principal;

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Node startNode;

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Node endNode;

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Airport airport;

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

	@Override
	public String toString() {
		return "Start Node:" + startNode.toString() + " End Node:"+ endNode.toString();
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	public Boolean isFree() {
		return status;
	}

	public Boolean isPrincipal() {
		return principal;
	}

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

}
