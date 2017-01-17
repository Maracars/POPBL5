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

public class AirplaneListJSONAction<sincronized> extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Integer draw = 0, recordsTotal = 1, recordsFiltered = 0;
	private List<AirplaneView> data = new ArrayList<AirplaneView>();
	String error = null;

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

	private List<AirplaneView> filter(List<AirplaneView> data, String search) {
		search = search.toLowerCase();
		for (Iterator<AirplaneView> pIt = data.iterator(); pIt.hasNext();) {
			AirplaneView pv = pIt.next();
			if (pv.getSerial().toLowerCase().contains(search))
				continue;
			if (pv.getTechnicalStatus().toLowerCase().contains(search))
				continue;
			if (pv.getPositionStatus().toLowerCase().contains(search))
				continue;
			pIt.remove();
		}
		return data;
	}

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

	public String getOrderColumnName(int orderCol) {
		String colName = null;
		switch (orderCol) {
		case 0:
			colName = "serial";
			break;
		case 1:
			colName = "status.technicalStatus";
			break;
		case 2:
			colName = "status.positionStatus";
			break;
		default:
			colName = "serial";
			break;
		}
		return colName;
	}

	public class AirplaneView {
		String serial;
		String technicalStatus;
		String positionStatus;

		public AirplaneView(String serial, String technicalStatus, String positionStatus) {
			this.serial = serial;
			this.technicalStatus = technicalStatus;
			this.positionStatus = positionStatus;
		}

		public String getSerial() {
			return serial;
		}

		public void setSerial(String serial) {
			this.serial = serial;
		}

		public String getTechnicalStatus() {
			return technicalStatus;
		}

		public void setTechnicalStatus(String technicalStatus) {
			this.technicalStatus = technicalStatus;
		}

		public String getPositionStatus() {
			return positionStatus;
		}

		public void setPositionStatus(String positionStatus) {
			this.positionStatus = positionStatus;
		}

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

	public List<AirplaneView> getData() {
		return data;
	}

	public void setData(List<AirplaneView> data) {
		this.data = data;
	}

}
