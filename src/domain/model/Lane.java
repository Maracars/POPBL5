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

// TODO: Auto-generated Javadoc
/**
 * The Class Lane.
 */
@Entity
public class Lane {
	
	/** The Constant PRINCIPAL. */
	public static final String PRINCIPAL = "PRINCIPAL";
	
	/** The Constant SECONDARY. */
	public static final String SECONDARY = "SECONDARY";
	
	/** The id. */
	@Id
	@GeneratedValue
	private Integer id;

	/** The name. */
	private String name;

	/** The status. */
	@Column(nullable = false)
	private Boolean status;

	/** The type. */
	@Column(nullable = false)
	private String type;

	/** The start node. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Node startNode;

	/** The end node. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Node endNode;

	/** The airport. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Airport airport;

	/** The semaphore. */
	@Transient
	private Semaphore semaphore;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the start node.
	 *
	 * @return the start node
	 */
	public Node getStartNode() {
		return startNode;
	}

	/**
	 * Sets the start node.
	 *
	 * @param startNode the new start node
	 */
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	/**
	 * Gets the end node.
	 *
	 * @return the end node
	 */
	public Node getEndNode() {
		return endNode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Start Node:" + startNode.toString() + " End Node:" + endNode.toString();
	}

	/**
	 * Sets the end node.
	 *
	 * @param endNode the new end node
	 */
	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	/**
	 * Checks if is free.
	 *
	 * @return the boolean
	 */
	public Boolean isFree() {
		return status;
	}

	/**
	 * Gets the airport.
	 *
	 * @return the airport
	 */
	public Airport getAirport() {
		return airport;
	}

	/**
	 * Sets the airport.
	 *
	 * @param airport the new airport
	 */
	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * Gets the semaphore.
	 *
	 * @return the semaphore
	 */
	public Semaphore getSemaphore() {
		return semaphore;
	}

	/**
	 * Sets the semaphore.
	 *
	 * @param semaphore the new semaphore
	 */
	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
