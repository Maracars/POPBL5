package action.user;

import java.text.SimpleDateFormat;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;
import domain.model.users.Admin;
import domain.model.users.Controller;
import domain.model.users.Mantainance;
import domain.model.users.Passenger;
import domain.model.users.User;

public class ProfileAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	String username;
	String birthdate;
	User user;
	String type;

	@Override
	public String execute() throws Exception {
		String ret = ERROR;
		if (username == null || username.equals("")) {
			addActionError("user.usernameBlank");
		} else {
			user = DAOUser.getUser(username);
			if (user != null) {
				birthdate = getBirthdate(user);
				type = user.getClass().getSimpleName().toLowerCase();
				ret = SUCCESS;
			} else {
				addActionError(getText("user.userNotFound"));
			}
		}
		return ret;
	}

	private String getBirthdate(User user) {
		String birthdate = null;
		SimpleDateFormat df = new SimpleDateFormat(getText("global.dateFormat"));
		if (user instanceof Passenger)
			birthdate = df.format(((Passenger) user).getBirthDate());
		if (user instanceof Mantainance)
			birthdate = df.format(((Mantainance) user).getBirthDate());
		if (user instanceof Controller)
			birthdate = df.format(((Controller) user).getBirthDate());
		if (user instanceof Admin)
			birthdate = df.format(((Admin) user).getBirthDate());
		return birthdate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
