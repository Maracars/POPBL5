package action.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOAirport;
import domain.dao.DAOGate;
import domain.model.Gate;

public class TerminalListJSONAction<sincronized> extends ActionSupport {

	private static final String TERMINAL_NAME = "terminal.name";

	private static final int FREE = 2;

	private static final int NODE_NAME = 1;

	private static final long serialVersionUID = 1L;

	private Integer draw = 0, recordsTotal = 1, recordsFiltered = 0;
	private List<GateView> data = new ArrayList<GateView>();
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

		recordsTotal = DAOGate.loadAllGatesFromAirport(DAOAirport.getLocaleAirport().getId()).size();

		data = filter(data, search);

		recordsFiltered = DAOGate.loadAllGatesFromAirport(DAOAirport.getLocaleAirport().getId()).size();

		return SUCCESS;
	}

	private List<GateView> filter(List<GateView> data, String search) {
		String searchToLower = search.toLowerCase();
		for (Iterator<GateView> gIt = data.iterator(); gIt.hasNext();) {
			GateView gv = gIt.next();
			if (gv.getTerminalName().toLowerCase().contains(searchToLower))
				continue;
			if (gv.getGateName().toLowerCase().contains(searchToLower))
				continue;
			if (gv.getGateState().toLowerCase().contains(searchToLower))
				continue;
			gIt.remove();
		}
		return data;
	}

	public ArrayList<GateView> generateData(String search, int orderCol, String orderDir, int start, int length) {
		List<Gate> gateList = null;
		ArrayList<GateView> gateViews = new ArrayList<GateView>();
		String colName = getOrderColumnName(orderCol);
		int airportId = DAOAirport.getLocaleAirport().getId();
		String gateState = null;

		gateList = DAOGate.loadGatesForTable(airportId, colName, orderDir, start, length);

		if (gateList != null) {
			for (Gate g : gateList) {

				String terminalName = g.getTerminal().getName();
				String gateName = g.getPositionNode().getName();
				if (g.isFree()) {
					gateState = "Free";
				} else {
					gateState = "Occupied";
				}

				gateViews.add(new GateView(terminalName, gateName, gateState));
			}
		}

		return gateViews;
	}

	public String getOrderColumnName(int orderCol) {
		String colName = null;
		switch (orderCol) {
		case 0:
			colName = TERMINAL_NAME;
			break;
		case NODE_NAME:
			colName = "positionNode.name";
			break;
		case FREE:
			colName = "free";
			break;
		default:
			colName = TERMINAL_NAME;
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

	public List<GateView> getData() {
		return data;
	}

	public void setData(List<GateView> data) {
		this.data = data;
	}

	public class GateView {
		String terminalName;
		String gateName;
		String gateState;

		public GateView(String terminalName, String gateName, String gateState) {
			this.terminalName = terminalName;
			this.gateName = gateName;
			this.gateState = gateState;
		}

		public String getTerminalName() {
			return terminalName;
		}

		public void setTerminalName(String terminalName) {
			this.terminalName = terminalName;
		}

		public String getGateName() {
			return gateName;
		}

		public void setGateName(String gateName) {
			this.gateName = gateName;
		}

		public String getGateState() {
			return gateState;
		}

		public void setGateState(String gateState) {
			this.gateState = gateState;
		}

	}

}
