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
		Terminal terminal = new Terminal();
		Gate gate = new Gate();
		terminalList = HibernateGeneric.loadAllObjects(terminal);
		System.out.println(terminalList.size());
		gatesList = HibernateGeneric.loadAllObjects(gate);
		System.out.println(gatesList.size());
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
