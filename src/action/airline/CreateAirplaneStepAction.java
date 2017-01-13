package action.airline;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;

public class CreateAirplaneStepAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	List<Object> listPlaneModel;
	List<Object> listPlaneMaker;

	@Override
	public String execute() throws Exception {
		listPlaneModel = HibernateGeneric.loadAllObjects(new PlaneModel());
		listPlaneMaker = HibernateGeneric.loadAllObjects(new PlaneMaker());

		return SUCCESS;
	}

	public List<Object> getListPlaneModel() {
		return listPlaneModel;
	}

	public void setListPlaneModel(List<Object> listPlaneModel) {
		this.listPlaneModel = listPlaneModel;
	}

	public List<Object> getListPlaneMaker() {
		return listPlaneMaker;
	}

	public void setListPlaneMaker(List<Object> listPlaneMaker) {
		this.listPlaneMaker = listPlaneMaker;
	}



}
