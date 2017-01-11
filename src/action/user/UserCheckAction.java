package action.user;

import org.json.JSONObject;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;

// TODO: Auto-generated Javadoc
/**
 * The Class UserCheckAction.
 * Action to check if the username is in use returns result prepared for json format read on front-end
 */
public class UserCheckAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The username. */
	private String username;
	
	/** The result. */
	private Boolean result;
	
	/** The json string. */
	private String jsonString;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		JSONObject json = new JSONObject();
		result = DAOUser.checkUsernameExists(username);
		json.append("result", result);
		jsonString = json.toString();
		return Action.SUCCESS;
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public Boolean getResult() {
		return result;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the json string.
	 *
	 * @return the json string
	 */
	public String getJsonString() {
		return jsonString;
	}

	/**
	 * Sets the json string.
	 *
	 * @param jsonString the new json string
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(Boolean result) {
		this.result = result;
	}

}
