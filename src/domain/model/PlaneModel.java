package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PlaneModel {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	@ManyToOne(optional = false)
	// @Cascade(value=CascadeType.DELETE) Hau nunbaitt jarri bihar da,
	private PlaneMaker planeMaker;

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

	public PlaneMaker getPlaneMaker() {
		return planeMaker;
	}

	public void setPlaneMaker(PlaneMaker planeMaker) {
		this.planeMaker = planeMaker;
	}

}
