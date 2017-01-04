package action.user;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;
import domain.model.users.User;
import helpers.MD5;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginAction.
 */
public class LoginAction extends ActionSupport {

	/** The Constant STRING_LAST_PAGE. */
	private static final String STRING_LAST_PAGE = "lastPage";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The url. */
	private String url;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		if (username == null || username.isEmpty()) {
			addFieldError("username", getText("user.usernameBlank"));
		}
		if (password == null || password.isEmpty()) {
			addFieldError("password", getText("user.passwordBlank"));
		}
		if (!getFieldErrors().isEmpty())
			password = "";
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		String ret = LOGIN;
		User user = DAOUser.getUser(username);
		if (user != null) {
			String md5pass = MD5.encrypt(password);
			if (user.getPassword().equals(md5pass)) {
				Map<String, Object> session = ActionContext.getContext().getSession();
				session.put("user", user);
				session.put("listenerUser", getListenerUser(user));
				session.put("listenerRole", getListenerRole(user));
				ret = SUCCESS;
			} else {
				addActionError(getText("user.incorrectPassword"));
			}
		} else {
			addActionError(getText("user.unknownUsername"));
		}
		password = "";

		if (ret.equals(SUCCESS)) {
			url = (String) ActionContext.getContext().getSession().get(STRING_LAST_PAGE);
			ActionContext.getContext().getSession().remove(STRING_LAST_PAGE);
		}
		return ret;
	}

	/**
	 * Gets the listener role.
	 *
	 * @param user the user
	 * @return the listener role
	 */
	private String getListenerRole(User user) {
		String ret = null;
		ret = MD5.encrypt(user.getClass().getSimpleName());

		return ret;
	}

	/**
	 * Gets the listener user.
	 *
	 * @param user the user
	 * @return the listener user
	 */
	private String getListenerUser(User user) {
		String ret = null;
		ret = MD5.encrypt(user.getUsername());

		return ret;
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
