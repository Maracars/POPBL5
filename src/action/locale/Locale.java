package action.locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class Locale extends ActionSupport {

	private static final long serialVersionUID = 1L;

	String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		url = request.getHeader("referer");
		return SUCCESS;
	}

}
