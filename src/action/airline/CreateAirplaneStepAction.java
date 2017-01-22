package action.airline;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;

/**
 * The Class CreateAirplaneStepAction.
 */
public class CreateAirplaneStepAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The list plane model. */
	List<Object> listPlaneModel;
	
	/** The list plane maker. */
	List<Object> listPlaneMaker;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		listPlaneModel = HibernateGeneric.loadAllObjects(new PlaneModel());
		listPlaneMaker = HibernateGeneric.loadAllObjects(new PlaneMaker());

		return SUCCESS;
	}

	/**
	 * Gets the list plane model.
	 *
	 * @return the list plane model
	 */
	public List<Object> getListPlaneModel() {
		return listPlaneModel;
	}

	/**
	 * Sets the list plane model.
	 *
	 * @param listPlaneModel the new list plane model
	 */
	public void setListPlaneModel(List<Object> listPlaneModel) {
		this.listPlaneModel = listPlaneModel;
	}

	/**
	 * Gets the list plane maker.
	 *
	 * @return the list plane maker
	 */
	public List<Object> getListPlaneMaker() {
		return listPlaneMaker;
	}

	/**
	 * Sets the list plane maker.
	 *
	 * @param listPlaneMaker the new list plane maker
	 */
	public void setListPlaneMaker(List<Object> listPlaneMaker) {
		this.listPlaneMaker = listPlaneMaker;
	}

}
