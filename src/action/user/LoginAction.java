package action.user;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;
import domain.model.User;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	@Override
	public void validate() {
		if(password == null || password.isEmpty()){
			addFieldError("username", "Username cannot be blank!");
		}
		if(password == null || password.isEmpty()){
			addFieldError("password", "Password cannot be blank!");
		}
	}
	
	@Override
	public String execute() throws Exception {
		String ret = LOGIN;
		User user = DAOUser.getUser(username);
		if(user != null){
			String md5pass = DAOUser.md5(password);
			if(user.getPassword().equals(md5pass)){
				Map<String, Object> session = ActionContext.getContext().getSession();
				session.put("user", user);			
				ret = SUCCESS;
			}else{
				addActionError("Incorrect password!");
			}
		}else{
			addActionError("Unknown username!");
		}
		return ret;
	}
	
	
}
