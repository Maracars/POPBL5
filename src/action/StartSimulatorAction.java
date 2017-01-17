package action;

import com.opensymphony.xwork2.ActionSupport;

import simulator.MainThread;

/**
 * The Class StartSimulatorAction.
 */
public class StartSimulatorAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Start simulator.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	//@Override
	public String startSimulator() throws Exception {

		MainThread.initSimulator(null, null);
		addActionMessage(getText("global.simulatorStarted"));
		return SUCCESS;
	}

	/**
	 * Stop simulator.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String stopSimulator() throws Exception {
		MainThread.finishSimulator();
		addActionMessage(getText("global.simulatorFinished"));
		return SUCCESS;
	}

}
