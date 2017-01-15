package action.airline;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOPlane;
import domain.model.Plane;
import domain.model.users.User;

public class AirplaneMoreInfoAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	String serial;
	Plane plane;

	@Override
	public String execute() throws Exception {
		String ret = SUCCESS;
		System.out.println(serial);
		if(serial == null || serial.equals("")){
			addActionError("airline.serialBlank");
		}else{
			Map<String, Object> session = ActionContext.getContext().getSession();
			User user = (User) session.get("user");
			int airlineId = user.getId();
			
			plane = DAOPlane.loadAirplaneWithSerial(airlineId, serial);
			
			if(plane != null){
				ret = SUCCESS;
			}else{
				addActionError("airline.planeNotFound");
			}
		}
		
		return ret;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	
	
	
	

}
