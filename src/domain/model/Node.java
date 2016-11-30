package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AirportNode")
public class Node {

	@Id
	@GeneratedValue
	Integer id;
	double positionX;
	double positionY;
	String name; // Beharrezkoa??

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public void setPositionX(double positionX2) {
		this.positionX = positionX2;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getPositionX() {
		return positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
