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

/**
 * The Class CreateAirplaneSubmitAction.
 */
public class CreateAirplaneSubmitAction extends ActionSupport {

	/** The Constant _0_0. */
	private static final double _0_0 = 0.0;

	/** The Constant POSITION_STATUS. */
	private static final String POSITION_STATUS = "LANDED";

	/** The Constant TECHNICAL_STATUS. */
	private static final String TECHNICAL_STATUS = "OK";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant MODEL_NAME. */
	private static final String MODEL_NAME = "planeModel.name";
	
	/** The Constant MODEL_NAME_BLANK. */
	private static final String MODEL_NAME_BLANK = "airline.planeModelBlank";

	/** The Constant MAKER_NAME. */
	private static final String MAKER_NAME = "planeMaker.name";
	
	/** The Constant MAKER_NAME_BLANK. */
	private static final String MAKER_NAME_BLANK = "airline.planeMakerBlank";

	/** The Constant SERIAL_NAME. */
	private static final String SERIAL_NAME = "plane.serial";

	/** The Constant SERIAL_NAME_BLANK. */
	private static final String SERIAL_NAME_BLANK = "airline.serialNameBlank";

	/** The plane. */
	Plane plane = new Plane();
	
	/** The plane model. */
	PlaneModel planeModel = new PlaneModel();
	
	/** The plane maker. */
	PlaneMaker planeMaker = new PlaneMaker();
	
	/** The list plane model. */
	List<Object> listPlaneModel;
	
	/** The list plane maker. */
	List<Object> listPlaneMaker;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
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

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
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

	/**
	 * Gets the plane.
	 *
	 * @return the plane
	 */
	public Plane getPlane() {
		return plane;
	}

	/**
	 * Sets the plane.
	 *
	 * @param plane the new plane
	 */
	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	/**
	 * Gets the plane model.
	 *
	 * @return the plane model
	 */
	public PlaneModel getPlaneModel() {
		return planeModel;
	}

	/**
	 * Sets the plane model.
	 *
	 * @param planeModel the new plane model
	 */
	public void setPlaneModel(PlaneModel planeModel) {
		this.planeModel = planeModel;
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
