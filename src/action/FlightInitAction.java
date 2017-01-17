package action;

import java.util.List;

import org.json.JSONObject;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Plane;

/**
 * The Class FlightInitAction. Returns a json object for the maps on the front
 * end to be able to render the planes for the first time
 */
public class FlightInitAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The plane list. */
	private List<Object> planeList;

	/** The json string. */
	private String jsonString;

	/** The username. */
	private String username;

	/**
	 * Loads the data (planes) from the database and sends returns the state of
	 * the action to Struts2
	 */
	@Override
	public String execute() throws Exception {
		JSONObject json = new JSONObject();
		planeList = HibernateGeneric.loadAllObjects(new Plane());
		json.append("result", planeList);
		jsonString = json.toString();
		return Action.SUCCESS;
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
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
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
	 * @param jsonString
	 *            the new json string
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

}
