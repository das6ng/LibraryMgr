package life.dashyeah.LibMgr;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import life.dashyeah.LibMgr.Data.Role;

/**
 * Library management system util:
 * redirect users to their own home page.
 * 
 * @author Dash Wong dashengyeah@github
 *
 */
public class Redirecter extends ActionSupport implements SessionAware {

	/**
	 * default UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * HTTP session info
	 */
	private SessionMap<String,Object> session;
	
	/**
	 * redirecting action
	 * @return {@link life.dashyeah.LibMgr.Data.Role} Role type String.
	 * <br>
	 * "error" if someone has no role.
	 */
	public String index() {
		String role = (String) session.get("role");
		if(Role.ROLE_USER.equals(role)) {
			System.out.println("--> role: "+Role.ROLE_USER);
			return Role.ROLE_USER;
		}
		else if(Role.ROLE_ADMIN.equals(role)) {
			System.out.println("--> role: "+Role.ROLE_ADMIN);
			return Role.ROLE_ADMIN;
		}
		else
			return "error";
	}

	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = (SessionMap<String, Object>) session;
	}

}
