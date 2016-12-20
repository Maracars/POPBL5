package action.user;

import org.json.JSONObject;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;

public class UserCheckAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String username;
	private Boolean result;
	private String jsonString;

	@Override
	public void validate() {

	}

	@Override
	public String execute() throws Exception {
		JSONObject json = new JSONObject();
		result = DAOUser.checkUsernameExists(username);
		json.append("result", result);
		jsonString = json.toString();
		return Action.SUCCESS;
	}

	public Boolean getResult() {
		return result;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getUsername() {
		return username;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

}
