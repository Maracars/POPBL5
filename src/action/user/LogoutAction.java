package action.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private String url;
	private String gotoIndex = "false";

	@Override
	public String execute() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove("user");
		session.remove("listenerUser");
		session.remove("listenerRole");	
		if(!gotoIndex.equals("true")){
			HttpServletRequest request = ServletActionContext.getRequest();
			url = request.getHeader("referer"); 
		}
		
		
		return SUCCESS;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGotoIndex() {
		return gotoIndex;
	}

	public void setGotoIndex(String gotoIndex) {
		this.gotoIndex = gotoIndex;
	}
	

}
