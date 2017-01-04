package action.user;

import java.text.SimpleDateFormat;

import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;
import domain.model.users.Admin;
import domain.model.users.Controller;
import domain.model.users.Mantainance;
import domain.model.users.Passenger;
import domain.model.users.User;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfileAction.
 */
public class ProfileAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The username. */
	String username;
	
	/** The birthdate. */
	String birthdate;
	
	/** The user. */
	User user;
	
	/** The type. */
	String type;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
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

	/**
	 * Gets the birthdate.
	 *
	 * @param user the user
	 * @return the birthdate
	 */
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

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the birthdate.
	 *
	 * @return the birthdate
	 */
	public String getBirthdate() {
		return birthdate;
	}

	/**
	 * Sets the birthdate.
	 *
	 * @param birthdate the new birthdate
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
