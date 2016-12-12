package domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PlaneStatus {
	@Id
	@GeneratedValue
	Integer id;
	@Column(nullable = false)
	String technicalStatus;
	
	@Column(nullable = false)
	String positionStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTechnicalStatus() {
		return technicalStatus;
	}

	public void setTechnicalStatus(String technicalStatus) {
		this.technicalStatus = technicalStatus;
	}

	public String getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

}
