package action.user;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;
import domain.model.users.User;
import helpers.MD5;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

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

	@Override
	public String execute() throws Exception {
		String ret = LOGIN;
		User user = DAOUser.getUser(username);
		if (user != null) {
			String md5pass = MD5.encrypt(password);
			if (user.getPassword().equals(md5pass)) {
				Map<String, Object> session = ActionContext.getContext().getSession();
				session.put("user", user);
				ret = SUCCESS;
			} else {
				addActionError(getText("user.incorrectPassword"));
			}
		} else {
			addActionError(getText("user.unknownUsername"));
		}
		password = "";
		return ret;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
