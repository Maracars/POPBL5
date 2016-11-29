package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class PlaneMovement {

	@Id
	@GeneratedValue
	Integer id;
	@NotNull
	Double directionX;
	@NotNull
	Double directionY;
	@NotNull
	Double positionX;
	@NotNull
	Double positionY;
	@NotNull
	Double speed;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getDirectionX() {
		return directionX;
	}

	public void setDirectionX(Double directionX) {
		this.directionX = directionX;
	}

	public Double getDirectionY() {
		return directionY;
	}

	public void setDirectionY(Double directionY) {
		this.directionY = directionY;
	}

	public Double getPositionX() {
		return positionX;
	}

	public void setPositionX(Double positionX) {
		this.positionX = positionX;
	}

	public Double getPositionY() {
		return positionY;
	}

	public void setPositionY(Double positionY) {
		this.positionY = positionY;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

}