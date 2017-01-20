package action.airline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOPlane;
import domain.model.Plane;
import domain.model.users.User;

/**
 * The Class AirplaneListJSONAction.
 *
 * @param <sincronized> the generic type
 */
public class AirplaneListJSONAction<sincronized> extends ActionSupport {

	/** The Constant SERIAL. */
	private static final String SERIAL = "serial";

	/** The Constant TECHNICAL_STATUS. */
	private static final int TECHNICAL_STATUS = 1;

	/** The Constant POSITION_STATUS. */
	private static final int POSITION_STATUS = 2;

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

		data = generateData(airlineId, search, orderCol, orderDir, start, length);

		if (DAOPlane.loadAllAirplanesFromAirline(airlineId) != null) {
			recordsTotal = DAOPlane.loadAllAirplanesFromAirline(airlineId).size();

			data = filter(data, search);

			recordsFiltered = DAOPlane.loadAllAirplanesFromAirline(airlineId).size();
		}
		return SUCCESS;
	}

	/**
	 * Filter.
	 *
	 * @param data the data
	 * @param search the search
	 * @return the list
	 */
	private List<AirplaneView> filter(List<AirplaneView> data, String search) {
		String searchToLower = search.toLowerCase();
		for (Iterator<AirplaneView> pIt = data.iterator(); pIt.hasNext();) {
			AirplaneView pv = pIt.next();
			if (pv.getSerial().toLowerCase().contains(searchToLower))
				continue;
			if (pv.getTechnicalStatus().toLowerCase().contains(searchToLower))
				continue;
			if (pv.getPositionStatus().toLowerCase().contains(searchToLower))
				continue;
			pIt.remove();
		}
		return data;
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
	public ArrayList<AirplaneView> generateData(int airlineId, String search, int orderCol, String orderDir, int start,
			int length) {
		List<Plane> planeList = null;
		ArrayList<AirplaneView> planeViews = new ArrayList<AirplaneView>();
		String colName = getOrderColumnName(orderCol);

		planeList = DAOPlane.loadAirplanesForTable(airlineId, colName, orderDir, start, length);

		if (planeList != null) {

			for (int i = 0; i < planeList.size(); i++) {
				if (planeList.get(i) instanceof Plane) {
					String serial = planeList.get(i).getSerial();
					String technicalStatus = planeList.get(i).getPlaneStatus().getTechnicalStatus();
					String positionStatus = planeList.get(i).getPlaneStatus().getPositionStatus();

					planeViews.add(new AirplaneView(serial, technicalStatus, positionStatus));
				}

			}
		}
		return planeViews;
	}

	/**
	 * Gets the order column name.
	 *
	 * @param orderCol the order col
	 * @return the order column name
	 */
	public String getOrderColumnName(int orderCol) {
		String colName = null;
		switch (orderCol) {
		case 0:
			colName = SERIAL;
			break;
		case TECHNICAL_STATUS:
			colName = "status.technicalStatus";
			break;
		case POSITION_STATUS:
			colName = "status.positionStatus";
			break;
		default:
			colName = SERIAL;
			break;
		}
		return colName;
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
