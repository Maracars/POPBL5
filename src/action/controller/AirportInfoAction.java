package action.controller;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Gate;
import domain.model.Terminal;

public class AirportInfoAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Object> terminalList;
	
	List<Object> gatesList;

	@Override
	public String execute() throws Exception {
		terminalList = HibernateGeneric.loadAllObjects(new Terminal());
		gatesList = HibernateGeneric.loadAllObjects(new Gate());
		return SUCCESS;
	}

	public List<Object> getTerminalList() {
		return terminalList;
	}

	public List<Object> getGatesList() {
		return gatesList;
	}

	public void setGatesList(List<Object> gatesList) {
		this.gatesList = gatesList;
	}

}
