package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Terminal {
	@Id
	@GeneratedValue
	Integer id;
	String name;
	
	@ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	Airport airport;
	
	@OneToOne
	Node positionNode;


	public Node getPositionNode() {
		return positionNode;
	}

	public void setPositionNode(Node positionNode) {
		this.positionNode = positionNode;
	}

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
