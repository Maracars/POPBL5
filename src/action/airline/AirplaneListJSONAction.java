package action.airline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import action.controller.TerminalListJSONAction;
import action.controller.TerminalListJSONAction.GateView;
import domain.dao.DAOPlane;
import domain.dao.HibernateGeneric;
import domain.model.Gate;
import domain.model.Plane;
import domain.model.users.User;

/**
 * The Class AirplaneListJSONAction.
 *
 * @param <sincronized> the generic type
 */
public class AirplaneListJSONAction<sincronized> extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The draw. */
	private Integer draw = 0;

	/** The records total. */
	private Integer recordsTotal = 1;

	/** The records filtered. */
	private Integer recordsFiltered = 0;

	/** The data. */
	private List<AirplaneView> data = new ArrayList<AirplaneView>();

	/** The error. */
	String error = null;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public synchronized String execute() throws Exception {

		Map<String, String[]> map = ActionContext.getContext().getParameters().toMap();

		String search = map.get("search[value]")[0];
		int orderCol = Integer.parseInt(map.get("order[0][column]")[0]);
		String orderDir = map.get("order[0][dir]")[0];

		int start = Integer.parseInt(map.get("start")[0]);
		int length = Integer.parseInt(map.get("length")[0]);

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		int airlineId = user.getId();

		List<Plane> allPlanes = DAOPlane.loadAllAirplanesFromAirline(airlineId);

		recordsTotal = allPlanes.size();

		allPlanes = filter(allPlanes, search);
		
		recordsFiltered = allPlanes.size();
		
		data = orderAndTrim(allPlanes, orderCol, orderDir, start, length);


		return SUCCESS;
	}

	/**
	 * Filter.
	 *
	 * @param data the data
	 * @param search the search
	 * @return the list
	 */
	private List<Plane> filter(List<Plane> allPlanes, String search) {
		if (search != null && !search.equals(""))
			allPlanes.removeIf((Object p) -> !((Plane) p).getSerial()
					.toLowerCase().contains(search.toLowerCase())
					&& !((Plane)p).getPlaneStatus().getTechnicalStatus().toLowerCase().contains(search.toLowerCase())
					&& !((Plane)p).getPlaneStatus().getPositionStatus().toLowerCase().contains(search.toLowerCase()));

		return allPlanes;
	}

	/**
	 * Generate data.
	 *
	 * @param airlineId the airline id
	 * @param search the search
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @return the array list
	 */
	private List<AirplaneListJSONAction<sincronized>.AirplaneView> orderAndTrim(List<Plane> allPlanes, int orderCol,
			String orderDir, int start, int length) {

		switch (orderCol) {
		case 0: // SERIAL
			if (orderDir.equals("asc"))
				allPlanes.sort((Object p1, Object p2) -> ((Plane) p1).getSerial()
						.compareToIgnoreCase(((Plane) p2).getSerial()));
			else
				allPlanes.sort((Object p1,
						Object p2) -> -((Plane) p1).getSerial()
						.compareToIgnoreCase(
								((Plane) p2).getSerial()));
			break;
		case 1: // TECHNICAL STATUS
			if (orderDir.equals("asc"))
				allPlanes.sort((Object p1, Object p2) -> ((Plane) p1).getPlaneStatus().getTechnicalStatus()
						.compareToIgnoreCase(((Plane) p2).getPlaneStatus().getTechnicalStatus()));
			else
				allPlanes.sort((Object p1,
						Object p2) -> -((Plane) p1).getPlaneStatus().getTechnicalStatus()
						.compareToIgnoreCase(
								((Plane) p2).getPlaneStatus().getTechnicalStatus()));
			break;
		case 2: //POSITION STATUS
			if (orderDir.equals("asc"))
				allPlanes.sort((Object p1, Object p2) -> ((Plane) p1).getPlaneStatus().getPositionStatus()
						.compareToIgnoreCase(((Plane) p2).getPlaneStatus().getPositionStatus()));
			else
				allPlanes.sort((Object p1,
						Object p2) -> -((Plane) p1).getPlaneStatus().getPositionStatus()
						.compareToIgnoreCase(
								((Plane) p2).getPlaneStatus().getPositionStatus()));
			break;
		default:
			allPlanes.sort((Object p1, Object p2) -> ((Plane) p1).getSerial()
					.compareToIgnoreCase(((Plane) p2).getSerial()));
			break;
		}
		allPlanes = allPlanes.subList(start, (start + length) > allPlanes.size() ? allPlanes.size() - start : (start + length));

		ArrayList<AirplaneView> avViewsList = new ArrayList<AirplaneView>();

		for (Object o : allPlanes) {
			Plane plane = (Plane) o;
			String serial = plane.getSerial();
			String technicalStatus = plane.getPlaneStatus().getTechnicalStatus();
			String positionStatus = plane.getPlaneStatus().getPositionStatus();

			avViewsList.add(new AirplaneView(serial, technicalStatus, positionStatus));

		}

		return avViewsList;
	}

	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public Integer getDraw() {
		return draw;
	}

	/**
	 * Sets the draw.
	 *
	 * @param draw the new draw
	 */
	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	/**
	 * Gets the records total.
	 *
	 * @return the records total
	 */
	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * Sets the records total.
	 *
	 * @param recordsTotal the new records total
	 */
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 * Gets the records filtered.
	 *
	 * @return the records filtered
	 */
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * Sets the records filtered.
	 *
	 * @param recordsFiltered the new records filtered
	 */
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<AirplaneView> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<AirplaneView> data) {
		this.data = data;
	}

	/**
	 * The Class AirplaneView.
	 */
	public class AirplaneView {

		/** The serial. */
		String serial;

		/** The technical status. */
		String technicalStatus;

		/** The position status. */
		String positionStatus;

		/**
		 * Instantiates a new airplane view.
		 *
		 * @param serial the serial
		 * @param technicalStatus the technical status
		 * @param positionStatus the position status
		 */
		public AirplaneView(String serial, String technicalStatus, String positionStatus) {
			this.serial = serial;
			this.technicalStatus = technicalStatus;
			this.positionStatus = positionStatus;
		}

		/**
		 * Gets the serial.
		 *
		 * @return the serial
		 */
		public String getSerial() {
			return serial;
		}

		/**
		 * Sets the serial.
		 *
		 * @param serial the new serial
		 */
		public void setSerial(String serial) {
			this.serial = serial;
		}

		/**
		 * Gets the technical status.
		 *
		 * @return the technical status
		 */
		public String getTechnicalStatus() {
			return technicalStatus;
		}

		/**
		 * Sets the technical status.
		 *
		 * @param technicalStatus the new technical status
		 */
		public void setTechnicalStatus(String technicalStatus) {
			this.technicalStatus = technicalStatus;
		}

		/**
		 * Gets the position status.
		 *
		 * @return the position status
		 */
		public String getPositionStatus() {
			return positionStatus;
		}

		/**
		 * Sets the position status.
		 *
		 * @param positionStatus the new position status
		 */
		public void setPositionStatus(String positionStatus) {
			this.positionStatus = positionStatus;
		}

	}

}
