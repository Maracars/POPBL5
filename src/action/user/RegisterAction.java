package action.user;

import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.users.User;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterAction.
 */
public abstract class RegisterAction extends ActionSupport {

	/** The Constant STRING_LAST_PAGE. */
	private static final String STRING_LAST_PAGE = "lastPage";
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant ERROR_SAVING. */
	private static final String ERROR_SAVING = "user.errorSaving";
	
	/** The Constant PASS_NOT_MATCH. */
	private static final String PASS_NOT_MATCH = "user.passwordNotMatch";
	
	/** The Constant REPEAT_PASSWORD. */
	private static final String REPEAT_PASSWORD = "repeatPassword";
	
	/** The Constant PASSWORD_BLANK. */
	private static final String PASSWORD_BLANK = "user.passwordBlank";
	
	/** The Constant PASSWORD. */
	private static final String PASSWORD = "user.password";
	
	/** The Constant USERNAME_BLANK. */
	private static final String USERNAME_BLANK = "user.usernameBlank";
	
	/** The Constant USERNAME. */
	private static final String USERNAME = "user.username";
	
	/** The Constant EMAIL_BLANK. */
	private static final String EMAIL_BLANK = "user.emailBlank";
	
	/** The Constant EMAIL. */
	private static final String EMAIL = "user.email";
	
	/** The Constant NO_PERMISSION. */
	private static final String NO_PERMISSION = "user.noPermission";

	/** The user. */
	User user = new User();
	
	/** The repeat password. */
	String repeatPassword;
	
	/** The url. */
	String url;
	
	/** The allowed users. */
	ArrayList<Class<?>> allowedUsers = new ArrayList<>();

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {

		if (user.getEmail() == null || user.getEmail().isEmpty())
			addFieldError(EMAIL, getText(EMAIL_BLANK));
		if (user.getPassword() == null || user.getPassword().isEmpty())
			addFieldError(PASSWORD, getText(PASSWORD_BLANK));
		else {
			if (!user.getPassword().equals(repeatPassword))
				addFieldError(REPEAT_PASSWORD, getText(PASS_NOT_MATCH));
		}
		if (user.getUsername() == null || user.getUsername().isEmpty())
			addFieldError(USERNAME, getText(USERNAME_BLANK));

		userSpecificValidate();

		if (!getFieldErrors().isEmpty()) {
			repeatPassword = "";
			user.setPassword("");
		}
	}

	/**
	 * User specific validate.
	 */
	abstract void userSpecificValidate();


	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		String ret = SUCCESS;

		ret = validateUserAccess();
		if (ret != ERROR)
			ret = userSpecificInsert();
		if (ret != ERROR) {

			ret = HibernateGeneric.saveObject(user) ? SUCCESS : ERROR;
			addActionError(getText(ERROR_SAVING));
		}

		if (!getFieldErrors().isEmpty()) {
			repeatPassword = "";
			user.setPassword("");
		} else {
			url = (String) ActionContext.getContext().getSession().get(STRING_LAST_PAGE);
			ActionContext.getContext().getSession().remove(STRING_LAST_PAGE);
		}

		return ret;
	}

	/**
	 * User specific insert.
	 *
	 * @return the string
	 */
	public abstract String userSpecificInsert();

	/**
	 * Validate user access.
	 *
	 * @return the string
	 */
	private String validateUserAccess() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		User sessionUser = (User) session.get("user");
		String ret = SUCCESS;

		if (allowedUsers.size() > 0) {
			ret = ERROR;
			if (sessionUser != null) {
				for (Class<?> c : allowedUsers) {
					if (c == sessionUser.getClass())
						ret = SUCCESS;
				}
			}
			if (ret == ERROR) {
				addActionError(getText(NO_PERMISSION));
			}
		}
		return ret;
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

	/**
	 * Gets the repeat password.
	 *
	 * @return the repeat password
	 */
	public String getRepeatPassword() {
		return repeatPassword;
	}

	/**
	 * Sets the repeat password.
	 *
	 * @param repeatPassword the new repeat password
	 */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	/**
	 * Gets the allowed users.
	 *
	 * @return the allowed users
	 */
	public ArrayList<Class<?>> getAllowedUsers() {
		return allowedUsers;
	}

	/**
	 * Sets the allowed users.
	 *
	 * @param allowedUsers the new allowed users
	 */
	public void setAllowedUsers(ArrayList<Class<?>> allowedUsers) {
		this.allowedUsers = allowedUsers;
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
