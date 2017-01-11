package action.controller;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Gate;
import domain.model.Terminal;

// TODO: Auto-generated Javadoc
/**
 * The Class AirportInfoAction.
 * Loads terminals and gates info to be sent as json to the front-end
 */
public class AirportInfoAction extends ActionSupport{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The terminal list. */
	List<Object> terminalList;
	
	/** The gates list. */
	List<Object> gatesList;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		terminalList = HibernateGeneric.loadAllObjects(new Terminal());
		gatesList = HibernateGeneric.loadAllObjects(new Gate());
		return SUCCESS;
	}

	/**
	 * Gets the terminal list.
	 *
	 * @return the terminal list
	 */
	public List<Object> getTerminalList() {
		return terminalList;
	}

	/**
	 * Gets the gates list.
	 *
	 * @return the gates list
	 */
	public List<Object> getGatesList() {
		return gatesList;
	}

	/**
	 * Sets the gates list.
	 *
	 * @param gatesList the new gates list
	 */
	public void setGatesList(List<Object> gatesList) {
		this.gatesList = gatesList;
	}

}
