package action.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;
import domain.dao.HibernateGeneric;
import domain.model.users.User;

public class UserDeleteAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	String url;
	String username;
	String user;
	
	@Override
	public String execute() throws Exception {
		String ret = SUCCESS;
		
		HibernateGeneric.deleteObject(DAOUser.getUser(username));
		
		User sessionUser = (User) ActionContext.getContext().getSession().get("user");
		if(sessionUser.getUsername().equals(username)) ret = "selfDelete";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		url = request.getHeader("referer");
		
		return ret;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
	
}
