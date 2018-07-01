package life.dashyeah.LibMgr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.dashyeah.LibMgr.Data.User;
import life.dashyeah.LibMgr.Data.Role;

@SuppressWarnings("unchecked")
public class Login extends ActionSupport implements ModelDriven<User>,SessionAware{
	/**
	 * default UID
	 */
	private static final long serialVersionUID = 1L;
	
	private InputStream inputStream;
	
	private Connection conn = null;
	
	private SessionMap<String,Object> session;
	
	/**
	 * receiving data.
	 */
	private User user = new User();
	
	private JSONObject result = new JSONObject();

	public String login() {
		result.clear();
		System.out.print("[MSG] login: ");
		
		System.out.print(user.toString());
		
		String role = user.getRole();
		String sql = "";
		if(role != null)
			switch(role) {
			case Role.ROLE_USER:
				sql = "select username,password from users where username='"+
			           user.getUsername()+"';";
				break;
			case Role.ROLE_ADMIN:
				sql = "select username,password from admins where username='"+
			           user.getUsername()+"';";
				break;
			default:
			}
		else {
			result.put("status", "ERROR");
			result.put("info", "wrong role type!");
		}
		
		//System.out.println("  sql: "+sql);
		this.conn = DBConn.getConn();
		if(!sql.equals(""))
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			
			if(rs.next() && rs.getString("password").equals(user.getPassword())) {
				System.out.println(" -- Accepted. ["+rs.getString("username")+"]");
				
				session.put("role", role);
				session.put("user", user.getUsername());
				
				result.put("status", "OK");
				result.put("username", user.getUsername());
				result.put("role", role);
			}else {
				System.out.println(" -- Denied.");
				
				result.put("status", "ERROR");
				result.put("info", "wrong username or password.");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
			result.put("status", "ERROR");
			result.put("info", "remote database error.");
		}
		
		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(re.getBytes(StandardCharsets.UTF_8));
	    return SUCCESS;
	}
	
	public String logout() {
		result.clear();
		System.out.println("[MSG] logout -- user: "+session.get("user"));
		
		result.put("status", "OK");
		result.put("username", session.get("user"));

		session.invalidate();
		
		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(re.getBytes(StandardCharsets.UTF_8));
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> sess) {
		session = (SessionMap<String, Object>)sess;
	}
	
	public InputStream getInputStream() {
	    return inputStream;
	}

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
}
