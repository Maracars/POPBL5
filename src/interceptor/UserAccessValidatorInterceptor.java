package interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

import domain.model.users.Admin;
import domain.model.users.Airline;
import domain.model.users.Controller;
import domain.model.users.Mantainance;
import domain.model.users.Passenger;
import domain.model.users.User;

// TODO: Auto-generated Javadoc
/**
 * The Class UserAccessValidatorInterceptor.
 * Struts2 interceptor that manages whether a user has permission or not to access an action
 */
public class UserAccessValidatorInterceptor implements Interceptor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant NO_PERMISSION. */
	private static final String NO_PERMISSION = "global.noPermission";

	/** The Constant PASSENGER. */
	final static Class<?> PASSENGER = new Passenger().getClass();

	/** The Constant AIRLINE. */
	final static Class<?> AIRLINE = new Airline().getClass();

	/** The Constant CONTROLLER. */
	final static Class<?> CONTROLLER = new Controller().getClass();

	/** The Constant MANTAINANCE. */
	final static Class<?> MANTAINANCE = new Mantainance().getClass();

	/** The Constant ADMIN. */
	final static Class<?> ADMIN = new Admin().getClass();

	/** The allowed. */
	String allowed;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.
	 * opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		String result;

		Map<String, Object> session = ActionContext.getContext().getSession();
		User sessionUser = (User) session.get("user");
		boolean hasPermission = false;
		if (sessionUser != null) {
			String[] allows = allowed.split(",");
			for (String s : allows) {
				switch (s) {
				case "PASSENGER":
					if (sessionUser instanceof Passenger)
						hasPermission = true;
					break;
				case "MANTAINANCE":
					if (sessionUser instanceof Mantainance)
						hasPermission = true;
					break;
				case "AIRLINE":
					if (sessionUser instanceof Airline)
						hasPermission = true;
					break;
				case "CONTROLLER":
					if (sessionUser instanceof Controller)
						hasPermission = true;
					break;
				case "ADMIN":
					if (sessionUser instanceof Admin)
						hasPermission = true;
					break;
				default:

					break;
				}
				if (sessionUser.getUsername().equals(s))
					hasPermission = true;
			}
			try {
				Method closeMethod = ai.getAction().getClass().getMethod("getUsername", null);
				String us = null;
				if (closeMethod != null)
					us = (String) closeMethod.invoke(ai.getAction(), null);
				if (sessionUser.getUsername().equals(us))
					hasPermission = true;
			} catch (Exception e) {
			}

		}

		if (hasPermission) {
			result = ai.invoke();
		} else {
			try {
				((ActionSupport) ai.getAction())
						.addActionError(((ActionSupport) ai.getAction()).getText(NO_PERMISSION));
			} catch (Exception e) {
			}
			result = "noPermission";
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the allowed.
	 *
	 * @return the allowed
	 */
	public String getAllowed() {
		return allowed;
	}

	/**
	 * Sets the allowed.
	 *
	 * @param allowed
	 *            the new allowed
	 */
	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}

	/*
	 * public String validateUserAccess() { String ret = ERROR;
	 * 
	 * Map<String, Object> session = ActionContext.getContext().getSession();
	 * User sessionUser = (User) session.get("user");
	 * 
	 * if (sessionUser != null) { for (Class<?> c : allowedRoles) { if (c ==
	 * sessionUser.getClass()) ret = SUCCESS; } for (String c :
	 * allowedUsernames) { if (c.equals(sessionUser.getUsername())) ret =
	 * SUCCESS; }
	 * 
	 * } if (ret == ERROR) { addActionError(getText(NO_PERMISSION)); }
	 * 
	 * return ret;
	 * 
	 * }
	 */

}