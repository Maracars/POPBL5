package action;

import com.opensymphony.xwork2.ActionSupport;

import simulator.MainThread;

public class StartSimulatorAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	//@Override
	public String startSimulator() throws Exception {

		MainThread.createMainThread(null);
		addActionMessage(getText("global.simulatorStarted"));
		return SUCCESS;
	}

	public String stopSimulator() throws Exception {
		MainThread.finishThreads();
		addActionMessage(getText("global.simulatorFinished"));
		return SUCCESS;
	}

}
