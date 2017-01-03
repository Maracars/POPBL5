package action;

import java.util.List;

import org.json.JSONObject;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.Plane;

public class FlightInitAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private List<Object> planeList;
	private String jsonString;
	private String username;


	@Override
	public void validate() {

	}

	@Override
	public String execute() throws Exception {
		JSONObject json = new JSONObject();
		planeList =  HibernateGeneric.loadAllObjects(new Plane());
		json.append("result", planeList);
		jsonString = json.toString();
		return Action.SUCCESS;
	}
	public String getUsername() {
		return username;
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

}
