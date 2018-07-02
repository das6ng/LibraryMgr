package life.dashyeah.LibMgr.Interceptor;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Library management system intercepter util:
 * Check if the current user is administrator.
 * 
 * @author Dash Wong dashengyeah@github
 *
 */
public class AdminChecker extends AbstractInterceptor{
	
	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * intercept action
	 */
	@Override
	public String intercept(ActionInvocation inv) throws Exception {
		String result;
		
		HttpSession sess = ServletActionContext.getRequest().getSession(true);

		System.out.print("[MSG] Admin Interceptor: ");
		if(sess != null && "admin".equals(sess.getAttribute("role"))) {
			System.out.println("  user - "+sess.getAttribute("user"));
			result = inv.invoke();
		}
		else {
			System.out.println("  admin check failed.");
			result = "error";
		}
		
		return result;
	}

}
