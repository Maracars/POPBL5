package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The Class Terminal.
 */
@Entity
public class Terminal {
	
	/** The id. */
	@Id
	@GeneratedValue
	Integer id;
	
	/** The name. */
	String name;
	
	/** The airport. */
	@ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	Airport airport;
	
	/** The position node. */
	@OneToOne
	Node positionNode;


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

}
