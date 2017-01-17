package action.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOAirport;
import domain.dao.DAOGate;
import domain.model.Gate;

public class TerminalListJSONAction<sincronized> extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private Integer draw = 0, recordsTotal = 1, recordsFiltered = 0;
	private List<GateView> data = new ArrayList<GateView>();
	String error = null;

	@Override
	public synchronized String execute() throws Exception {

		Map<String, String[]> map = ActionContext.getContext().getParameters().toMap();
		for (Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key:"+ key +" value: " + Arrays.toString((String[])value));
		}

		String search = map.get("search[value]")[0];
		int orderCol = Integer.parseInt(map.get("order[0][column]")[0]);
		String orderDir = map.get("order[0][dir]")[0];

		int start = Integer.parseInt(map.get("start")[0]);
		int length = Integer.parseInt(map.get("length")[0]);

		data = generateData(search, orderCol, orderDir, start, length);

		recordsTotal = DAOGate.loadAllGatesFromAirport(DAOAirport.getLocaleAirport().getId()).size();

		data = filter(data, search);

		recordsFiltered = data.size();

		return SUCCESS;
	}

	private List<GateView> filter(List<GateView> data, String search) {
		search = search.toLowerCase();
		for (Iterator<GateView> gIt = data.iterator(); gIt.hasNext();) {
			GateView gv = gIt.next();
			if (gv.getTerminalName().toLowerCase().contains(search))
				continue;
			if (gv.getGateName().toLowerCase().contains(search))
				continue;
			if (gv.getGateState().toLowerCase().contains(search))
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

		if(gateList != null){
			for(Gate g : gateList){

				String terminalName = g.getTerminal().getName();
				String gateName = g.getPositionNode().getName();
				if(g.isFree()){
					gateState = "Free";
				}else{
					gateState = "Occupied";
				}

				gateViews.add(new GateView(terminalName, gateName, gateState));
			}
		}

		return gateViews;
	}


	public String getOrderColumnName(int orderCol){
		String colName = null;
		switch(orderCol){
		case 0:
			colName = "terminal.name";
			break;
		case 1:
			colName = "positionNode.name";
			break;
		case 2:
			colName = "free";
			break;
		default:
			colName = "terminal.name";
			break;
		}
		return colName;
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


}
