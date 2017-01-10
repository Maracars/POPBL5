package action.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;
import domain.dao.HibernateGeneric;
import domain.model.users.User;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDeleteAction.
 */
public class UserDeleteAction extends ActionSupport {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The url. */
	String url;
	
	/** The username. */
	String username;
	
	/** The user. */
	String user;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
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
	 * Gets the user.
	 *
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	
	
}
