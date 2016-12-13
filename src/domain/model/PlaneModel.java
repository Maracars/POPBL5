package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class PlaneModel {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	@ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

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
