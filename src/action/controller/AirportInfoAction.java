package action.controller;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOAirport;
import domain.dao.DAOGate;
import domain.dao.DAOTerminal;
import domain.model.Airport;

/**
 * The Class AirportInfoAction. Loads terminals and gates info to be sent as
 * json to the front-end
 */
public class AirportInfoAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The terminal list. */
	List<?> terminalList;

	/** The gates list. */
	List<?> gatesList;

	@Override
	public String execute() throws Exception {
		Airport local = DAOAirport.getLocaleAirport();
		terminalList = DAOTerminal.loadTerminalsFromAirport(local.getId());
		gatesList = DAOGate.loadAllGatesFromAirport(local.getId());
		return SUCCESS;
	}

	/**
	 * Gets the terminal list.
	 *
	 * @return the terminal list
	 */
	public List<?> getTerminalList() {
		return terminalList;
	}

	/**
	 * Gets the gates list.
	 *
	 * @return the gates list
	 */
	public List<?> getGatesList() {
		return gatesList;
	}

	/**
	 * Sets the gates list.
	 *
	 * @param gatesList
	 *            the new gates list
	 */
	public void setGatesList(List<Object> gatesList) {
		this.gatesList = gatesList;
	}

}
