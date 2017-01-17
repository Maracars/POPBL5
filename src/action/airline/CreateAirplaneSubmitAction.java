package action.airline;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Plane;
import domain.model.PlaneMaker;
import domain.model.PlaneModel;
import domain.model.PlaneMovement;
import domain.model.PlaneStatus;
import domain.model.users.Airline;
import domain.model.users.User;

public class CreateAirplaneSubmitAction extends ActionSupport {

	private static final double _0_0 = 0.0;

	private static final String POSITION_STATUS = "LANDED";

	private static final String TECHNICAL_STATUS = "OK";

	private static final long serialVersionUID = 1L;

	private static final String MODEL_NAME = "planeModel.name";
	private static final String MODEL_NAME_BLANK = "airline.planeModelBlank";

	private static final String MAKER_NAME = "planeMaker.name";
	private static final String MAKER_NAME_BLANK = "airline.planeMakerBlank";

	private static final String SERIAL_NAME = "plane.serial";

	private static final String SERIAL_NAME_BLANK = "airline.serialNameBlank";

	Plane plane = new Plane();
	PlaneModel planeModel = new PlaneModel();
	PlaneMaker planeMaker = new PlaneMaker();
	List<Object> listPlaneModel;
	List<Object> listPlaneMaker;

	@Override
	public String execute() {

		if (planeModel.getId() == null) {

			HibernateGeneric.saveObject(planeMaker);
			planeModel.setPlaneMaker(planeMaker);

		} else {
			plane.setModel(planeModel);
		}

		if (planeMaker.getId() == null) {
			HibernateGeneric.saveObject(planeModel);
			plane.setModel(planeModel);
		} else {
			plane.getModel().setPlaneMaker(planeMaker);
		}

		plane.setFabricationDate(new Date());

		PlaneStatus planeStatus = new PlaneStatus();
		planeStatus.setPositionStatus(POSITION_STATUS);
		planeStatus.setTechnicalStatus(TECHNICAL_STATUS);
		HibernateGeneric.saveObject(planeStatus);
		plane.setPlaneStatus(planeStatus);

		PlaneMovement planeMovement = new PlaneMovement();
		planeMovement.setDirectionX(_0_0);
		planeMovement.setDirectionY(_0_0);
		planeMovement.setPositionX(_0_0);
		planeMovement.setPositionY(_0_0);
		planeMovement.setSpeed(_0_0);
		plane.setPlaneMovement(planeMovement);

		User user = (User) ActionContext.getContext().getSession().get("user");
		Airline airline = new Airline();
		airline.setId(user.getId());
		plane.setAirline(airline);
		HibernateGeneric.saveObject(plane);
		return SUCCESS;
	}

	@Override
	public void validate() {
		listPlaneModel = HibernateGeneric.loadAllObjects(new PlaneModel());
		listPlaneMaker = HibernateGeneric.loadAllObjects(new PlaneMaker());

		if (plane.getSerial() == null || plane.getSerial().isEmpty())
			addFieldError(SERIAL_NAME, getText(SERIAL_NAME_BLANK));
		if ((planeModel.getName() == null || planeModel.getName().isEmpty()) && planeModel.getId() == null)
			addFieldError(MODEL_NAME, getText(MODEL_NAME_BLANK));
		if ((planeMaker.getName() == null || planeMaker.getName().isEmpty()) && planeMaker.getId() == null)
			addFieldError(MAKER_NAME, getText(MAKER_NAME_BLANK));
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public PlaneModel getPlaneModel() {
		return planeModel;
	}

	public void setPlaneModel(PlaneModel planeModel) {
		this.planeModel = planeModel;
	}

	public PlaneMaker getPlaneMaker() {
		return planeMaker;
	}

	public void setPlaneMaker(PlaneMaker planeMaker) {
		this.planeMaker = planeMaker;
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
