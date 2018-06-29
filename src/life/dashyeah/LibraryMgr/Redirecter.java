package life.dashyeah.LibraryMgr;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class Redirecter extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SessionMap<String,Object> session;
	
	public String index() {
		String role = (String) session.get("role");
		if("user".equals(role)) {
			System.out.println("--> role: user");
			return "user";
		}
		else if("admin".equals(role)) {
			System.out.println("--> role: admin");
			return "admin";
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
