package action.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import action.controller.FlightListJSONAction.AirplaneView;
import domain.dao.DAOAirport;
import domain.dao.DAOGate;
import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.Gate;
import domain.model.Lane;

/**
 * The Class TerminalListJSONAction.
 *
 * @param <sincronized> the generic type
 */
public class TerminalListJSONAction<sincronized> extends ActionSupport {

	/** The Constant TERMINAL_NAME. */
	private static final String TERMINAL_NAME = "terminal.name";

	/** The Constant FREE. */
	private static final int FREE = 2;

	/** The Constant NODE_NAME. */
	private static final int NODE_NAME = 1;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The draw. */
	private Integer draw = 0;
	
	/** The records total. */
	private Integer recordsTotal = 1;
	
	/** The records filtered. */
	private Integer recordsFiltered = 0;
	
	/** The data. */
	private List<GateView> data = new ArrayList<GateView>();
	
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
		
		List<Object> allGates = HibernateGeneric.loadAllObjects(new Gate());
		allGates.removeIf((Object g) -> !((Gate)g).getTerminal().getAirport().isLocale());

		recordsTotal = allGates.size();
		
		allGates = filter(allGates, search);
		
		recordsFiltered = allGates.size();
		
		data = orderAndTrim(allGates, orderCol, orderDir, start, length);

		return SUCCESS;
	}

	/**
	 * Filter.
	 *
	 * @param data the data
	 * @param search the search
	 * @return the list
	 */
	private List<Object> filter(List<Object> allLanes, String search) {
		if (search != null && !search.equals(""))
			allLanes.removeIf((Object g) -> !((Gate) g).getTerminal().getName()
					.toLowerCase().contains(search)
					&& !((Gate)g).getPositionNode().getName().toLowerCase().contains(search));

		return allLanes;
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
	private List<TerminalListJSONAction<sincronized>.GateView> orderAndTrim(List<Object> allGates, int orderCol,
			String orderDir, int start, int length) {

		switch (orderCol) {
		case 0: // TERMINAL NAME
			if (orderDir.equals("asc"))
				allGates.sort((Object g1, Object g2) -> ((Gate) g1).getTerminal()
						.getName()
						.compareToIgnoreCase(((Gate) g2).getTerminal().getName()));
			else
				allGates.sort((Object g1,
						Object g2) -> -((Gate) g1).getTerminal().getName()
						.compareToIgnoreCase(
								((Gate) g2).getTerminal().getName()));
			break;
		case 1: // GATE NAME
			if (orderDir.equals("asc"))
				allGates.sort((Object g1, Object g2) -> ((Gate) g1).getPositionNode()
						.getName()
						.compareToIgnoreCase(((Gate) g2).getPositionNode().getName()));
			else
				allGates.sort((Object g1,
						Object g2) -> -((Gate) g1).getPositionNode().getName()
						.compareToIgnoreCase(
								((Gate) g2).getPositionNode().getName()));
			break;
		default:
			allGates.sort((Object g1, Object g2) -> ((Gate) g1).getTerminal().getName()
					.compareToIgnoreCase(((Gate) g2).getTerminal().getName()));
			break;
		}
		allGates = allGates.subList(start, (start + length) > allGates.size() ? allGates.size() - start : (start + length));

		ArrayList<GateView> gvViewsList = new ArrayList<GateView>();

		for (Object o : allGates) {
			Gate gate = (Gate) o;
			String terminalName = gate.getTerminal().getName();
			String gateName = gate.getPositionNode().getName();
			String gateState = null;
			if(gate.isFree()){
				gateState = "Free";
			}else{
				gateState = "Occupied";
			}

			gvViewsList.add(new GateView(terminalName, gateName, gateState));

		}

		return gvViewsList;
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
	public List<GateView> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<GateView> data) {
		this.data = data;
	}

	/**
	 * The Class GateView.
	 */
	public class GateView {
		
		/** The terminal name. */
		String terminalName;
		
		/** The gate name. */
		String gateName;
		
		/** The gate state. */
		String gateState;

		/**
		 * Instantiates a new gate view.
		 *
		 * @param terminalName the terminal name
		 * @param gateName the gate name
		 * @param gateState the gate state
		 */
		public GateView(String terminalName, String gateName, String gateState) {
			this.terminalName = terminalName;
			this.gateName = gateName;
			this.gateState = gateState;
		}

		/**
		 * Gets the terminal name.
		 *
		 * @return the terminal name
		 */
		public String getTerminalName() {
			return terminalName;
		}

		/**
		 * Sets the terminal name.
		 *
		 * @param terminalName the new terminal name
		 */
		public void setTerminalName(String terminalName) {
			this.terminalName = terminalName;
		}

		/**
		 * Gets the gate name.
		 *
		 * @return the gate name
		 */
		public String getGateName() {
			return gateName;
		}

		/**
		 * Sets the gate name.
		 *
		 * @param gateName the new gate name
		 */
		public void setGateName(String gateName) {
			this.gateName = gateName;
		}

		/**
		 * Gets the gate state.
		 *
		 * @return the gate state
		 */
		public String getGateState() {
			return gateState;
		}

		/**
		 * Sets the gate state.
		 *
		 * @param gateState the new gate state
		 */
		public void setGateState(String gateState) {
			this.gateState = gateState;
		}

	}

}
