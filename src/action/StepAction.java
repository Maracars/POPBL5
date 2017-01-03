package action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class StepAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getHeader("referer");
		
		ActionContext.getContext().getSession().put("lastPage", url);
		
		return SUCCESS;
	}
}
