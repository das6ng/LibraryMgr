package life.dashyeah.LibMgr.Interceptor;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AdminChecker extends AbstractInterceptor{
	
	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;

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
