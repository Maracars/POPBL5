package domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Node {

	@Id
	@GeneratedValue
	Integer id;

	Integer positionX;

	Integer positionY;

	String name; // Beharrezkoa??

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPositionX() {
		return positionX;
	}

	public void setPositionX(Integer positionX) {
		this.positionX = positionX;
	}

	public Integer getPositionY() {
		return positionY;
	}

	public void setPositionY(Integer positionY) {
		this.positionY = positionY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
