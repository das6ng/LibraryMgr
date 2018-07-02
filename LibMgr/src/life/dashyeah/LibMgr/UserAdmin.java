package life.dashyeah.LibMgr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.dashyeah.LibMgr.Data.User;

/**
 * Library management system administrator util:
 * user administration.
 * 
 * @author Dash Wong dashengyeah@github
 *
 */
public class UserAdmin extends ActionSupport implements ModelDriven<User>{
	/**
	 * default UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * struts2 return data stream
	 */
	private InputStream inputStream;
	
	/**
	 * Receiving data from client.<br>
	 * this is set by {@link com.opensymphony.xwork2.ModelDriven}
	 */
	private User newUser = new User();
	
	/**
	 * result data JSONObject
	 */
	private JSONObject result = new JSONObject();
	
	/**
	 * Add a user to the system.
	 * parameter {@link #newUser}
	 * @return Always <code>SUCCESS</code>
	 */
	@SuppressWarnings("unchecked")
	public String addUser() {
		result.clear();
		System.out.print("[MSG] delete user: ");
		
		String username = newUser.getUsername();
		if(existUser(username)) {
			result.put("status", "ERROR");
		}
		Connection conn = DBConn.getConn();
		String sql = "insert into users(username,password,name,birthdate,gender) values"+newUser.toSQLString();
		System.out.println("  SQL: "+sql);
		try {
			conn.prepareStatement(sql).executeUpdate();
			result.put("status", "OK");
		} catch (SQLException e) {
			e.printStackTrace();
			
			result.put("status", "ERROR");
			result.put("info", "remote sql error.");
		}
		
		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(re.getBytes(StandardCharsets.UTF_8));
	    return SUCCESS;
	}
	
	/**
	 * Delete a user from the system.
	 * parameter username is in {@link #newUser}
	 * @return Always <code>SUCCESS</code>
	 */
	@SuppressWarnings("unchecked")
	public String deleteUser() {
		result.clear();
		System.out.print("[MSG] delete user: ");
		String username = newUser.getUsername();
		if(existUser(username)) {
			result.put("status", "ERROR");
		}
		Connection conn = DBConn.getConn();
		String sql = "delete from users where username='"+username+"'";
		try {
			conn.prepareStatement(sql).executeUpdate();
			result.put("status", "OK");
		} catch (SQLException e) {
			e.printStackTrace();
			
			result.put("status", "ERROR");
			result.put("info", "remote sql error.");
		}
		
		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(re.getBytes(StandardCharsets.UTF_8));
	    return SUCCESS;
	}
	
	/**
	 * To find out if a user exists.
	 * @param username
	 * @return true  yes <br>false  no
	 */
	private boolean existUser(String username) {
		Connection conn = DBConn.getConn();
		String sql = "select username from users where username='"+username+"'";
		boolean flag = false;
		
		System.out.println("  SQL: "+sql);
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			if(rs.next()) flag = true;
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 
	 * @return result stream to struts framework
	 */
	public InputStream getInputStream() {
	    return inputStream;
	}

	@Override
	public User getModel() {
		return newUser;
	}
}

