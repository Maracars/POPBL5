package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The Class Gate.
 */
@Entity
public class Gate {

	/** The id. */
	@Id
	@GeneratedValue
	private Integer id;
	
	/** The number. */
	private Integer number;

	/** The position node. */
	@OneToOne
	Node positionNode;

	/** The terminal. */
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	Terminal terminal;
	
	/** The free. */
	boolean free;

	/**
	 * Sets the free.
	 *
	 * @param free the new free
	 */
	public void setFree(boolean free) {
		this.free = free;
	}

	/**
	 * Gets the terminal.
	 *
	 * @return the terminal
	 */
	public Terminal getTerminal() {
		return terminal;
	}

	/**
	 * Sets the terminal.
	 *
	 * @param terminal the new terminal
	 */
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	/**
	 * Gets the position node.
	 *
	 * @return the position node
	 */
	public Node getPositionNode() {
		return positionNode;
	}

	/**
	 * Sets the position node.
	 *
	 * @param positionNode the new position node
	 */
	public void setPositionNode(Node positionNode) {
		this.positionNode = positionNode;
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
	 * Gets the number.
	 *
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number the new number
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

}
