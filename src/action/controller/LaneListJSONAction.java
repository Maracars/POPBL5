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

public class LaneListJSONAction<sincronized> extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Integer draw = 0, recordsTotal = 1, recordsFiltered = 0;
	private List<LaneView> data = new ArrayList<LaneView>();
	String error = null;

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

	public ArrayList<LaneView> generateData(String search, int orderCol, String orderDir, int start, int length) {
		List<Lane> laneList = null;
		ArrayList<LaneView> laneViews = new ArrayList<LaneView>();
		String colName = getOrderColumnName(orderCol);
		String state = "Occupied";

		laneList = DAOLane.loadLanesForTable(colName, orderDir, start, length);

		if (laneList != null) {

			for (Lane l : laneList) {
				String name = l.getName();
				if (l.isFree()) {
					state = "Free";
				} else {
					state = "Occupied";
				}

				laneViews.add(new LaneView(name, state));
			}
		}

		return laneViews;
	}

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

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<LaneView> getData() {
		return data;
	}

	public void setData(List<LaneView> data) {
		this.data = data;
	}

	public class LaneView {
		String name;
		String state;

		public LaneView(String name, String state) {
			this.name = name;
			this.state = state;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

	}

}
