package action.user;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;
import domain.model.Address;
import domain.model.users.Admin;
import domain.model.users.Controller;
import domain.model.users.Mantainance;
import domain.model.users.Passenger;
import domain.model.users.User;

/**
 * The Class EditUserStepAction. Action that saves calling page to the session
 * to return after the editions wizard-like process. Also return to the view the
 * target user after loading it from the database and adjuting it to the view
 */
public class EditUserStepAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The username. */
	String username;

	/** The user. */
	User user;

	/** The address. */
	Address address;

	/** The birthdate. */
	String birthdate;

	/** The type. */
	String type;

	@Override
	public String execute() throws Exception {
		String ret = SUCCESS;

		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getHeader("referer");

		ActionContext.getContext().getSession().put("lastPage", url);

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
	 * @param user
	 *            the user
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
	 * @param user
	 *            the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address
	 *            the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
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
	 * @param birthdate
	 *            the new birthdate
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
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
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
