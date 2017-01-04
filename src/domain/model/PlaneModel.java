package domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

// TODO: Auto-generated Javadoc
/**
 * The Class PlaneModel.
 */
@Entity
public class PlaneModel {

	/** The id. */
	@Id
	@GeneratedValue
	private Integer id;
	
	/** The name. */
	private String name;

	/** The plane maker. */
	@ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

	private PlaneMaker planeMaker;

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

	/**
	 * Gets the plane maker.
	 *
	 * @return the plane maker
	 */
	public PlaneMaker getPlaneMaker() {
		return planeMaker;
	}

	/**
	 * Sets the plane maker.
	 *
	 * @param planeMaker the new plane maker
	 */
	public void setPlaneMaker(PlaneMaker planeMaker) {
		this.planeMaker = planeMaker;
	}

}
