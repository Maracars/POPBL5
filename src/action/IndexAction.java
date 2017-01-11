package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.model.users.User;

public class IndexAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the user logged in the session and  returns its type as string to Struts2 and "success" if there is no  user logged
	 */
	@Override
	public String execute() throws Exception {
		String ret = SUCCESS;
		
		User user = (User) ActionContext.getContext().getSession().get("user");
		
		if(user != null){
			ret = user.getClass().getSimpleName().toLowerCase();
		}
		
		return ret;
	}

}
