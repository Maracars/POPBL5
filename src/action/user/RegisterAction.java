package action.user;

import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.users.User;

public abstract class RegisterAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_SAVING = "user.errorSaving";
	private static final String PASS_NOT_MATCH = "user.passwordNotMatch";
	private static final String REPEAT_PASSWORD = "repeatPassword";
	private static final String PASSWORD_BLANK = "user.passwordBlank";
	private static final String PASSWORD = "user.password";
	private static final String USERNAME_BLANK = "user.usernameBlank";
	private static final String USERNAME = "user.username";
	private static final String EMAIL_BLANK = "user.emailBlank";
	private static final String EMAIL = "user.email";
	private static final String NO_PERMISSION = "user.noPermission";
	
	User user = new User();
	String repeatPassword;
	ArrayList<Class<?>> allowedUsers = new ArrayList<>();
	
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
	
	abstract void userSpecificValidate();
	
	@Override
	public String execute() throws Exception {
		String ret = SUCCESS;

		ret = validateUserAccess();

		if (ret != ERROR) {
			ret = userSpecificInsert();
			ret = HibernateGeneric.insertObject(user) ? SUCCESS : ERROR;
			addActionError(getText(ERROR_SAVING));
		}

		if (!getFieldErrors().isEmpty()) {
			repeatPassword = "";
			user.setPassword("");
		}

		return ret;
	}
	
	public abstract String userSpecificInsert();

	private String validateUserAccess() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		User sessionUser = (User) session.get("user");
		String ret = SUCCESS;

		if (allowedUsers.size() > 0) {
			ret = ERROR;
			if (user != null) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public ArrayList<Class<?>> getAllowedUsers() {
		return allowedUsers;
	}

	public void setAllowedUsers(ArrayList<Class<?>> allowedUsers) {
		this.allowedUsers = allowedUsers;
	}
	
	

}
