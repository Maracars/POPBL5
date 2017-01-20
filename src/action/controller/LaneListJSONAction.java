package action.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOLane;
import domain.dao.HibernateGeneric;
import domain.model.Lane;

/**
 * The Class LaneListJSONAction.
 *
 * @param <sincronized> the generic type
 */
public class LaneListJSONAction<sincronized> extends ActionSupport {

	/** The Constant OCCUPIED. */
	private static final String OCCUPIED = "Occupied";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The draw. */
	private Integer draw = 0;
	
	/** The records total. */
	private Integer recordsTotal = 1;
	
	/** The records filtered. */
	private Integer recordsFiltered = 0;
	
	/** The data. */
	private List<LaneView> data = new ArrayList<LaneView>();
	
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

		data = generateData(search, orderCol, orderDir, start, length);

		recordsTotal = HibernateGeneric.loadAllObjects(new Lane()).size();

		filter(data, search);

		recordsFiltered = HibernateGeneric.loadAllObjects(new Lane()).size();

		return SUCCESS;
	}

	/**
	 * Filter.
	 *
	 * @param data the data
	 * @param search the search
	 * @return the list
	 */
	private List<LaneView> filter(List<LaneView> data, String search) {
		String searchToLower = search.toLowerCase();
		for (Iterator<LaneView> fIt = data.iterator(); fIt.hasNext();) {
			LaneView fv = fIt.next();
			if (fv.getName().toLowerCase().contains(searchToLower))
				continue;
			if (fv.getState().toLowerCase().contains(searchToLower))
				continue;
			fIt.remove();
		}
		return data;
	}

	/**
	 * Generate data.
	 *
	 * @param search the search
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @return the array list
	 */
	public ArrayList<LaneView> generateData(String search, int orderCol, String orderDir, int start, int length) {
		List<Lane> laneList = null;
		ArrayList<LaneView> laneViews = new ArrayList<LaneView>();
		String colName = getOrderColumnName(orderCol);
		String state = OCCUPIED;

		laneList = DAOLane.loadLanesForTable(colName, orderDir, start, length);

		if (laneList != null) {

			for (Lane l : laneList) {
				String name = l.getName();
				if (l.isFree()) {
					state = "Free";
				} else {
					state = OCCUPIED;
				}

				laneViews.add(new LaneView(name, state));
			}
		}

		return laneViews;
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
			colName = "name";
			break;
		case 1:
			colName = "status";
			break;
		default:
			colName = "plane";
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
	public List<LaneView> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<LaneView> data) {
		this.data = data;
	}

	/**
	 * The Class LaneView.
	 */
	public class LaneView {
		
		/** The name. */
		String name;
		
		/** The state. */
		String state;

		/**
		 * Instantiates a new lane view.
		 *
		 * @param name the name
		 * @param state the state
		 */
		public LaneView(String name, String state) {
			this.name = name;
			this.state = state;
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
		 * Gets the state.
		 *
		 * @return the state
		 */
		public String getState() {
			return state;
		}

		/**
		 * Sets the state.
		 *
		 * @param state the new state
		 */
		public void setState(String state) {
			this.state = state;
		}

	}

}
