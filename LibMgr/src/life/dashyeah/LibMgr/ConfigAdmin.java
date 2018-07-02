package life.dashyeah.LibMgr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.dashyeah.LibMgr.Data.Config;

/**
 * Library management system administrator util:
 * configuration administration.
 * 
 * @author Dash Wong dashengyeah@github
 *
 */
public class ConfigAdmin extends ActionSupport implements ModelDriven<Config>{
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
	private Config newConfig = new Config();
	
	/**
	 * result data JSONObject
	 */
	private JSONObject result = new JSONObject();
	
	/**
	 * Set the max amount of books that one user can borrow.
	 * <br>
	 * parameter <code>maxrent</code> is in {@link #newConfig}
	 * 
	 * @return Always <code>SUCCESS</code>
	 */
	@SuppressWarnings("unchecked")
	public String setMaxrent() {
		result.clear();
		System.out.print("[MSG] delete user: ");
		
		String maxrent = newConfig.getMaxrent();
		Connection conn = DBConn.getConn();
		String sql = "update syscfg set config_value='"+maxrent+"' where config_item='maxrent'";
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
	 * Set the max days that one book can be borrowed freely.
	 * <br>
	 * parameter <code>maxdays</code> is in {@link #newConfig}
	 * 
	 * @return Always <code>SUCCESS</code>
	 */
	@SuppressWarnings("unchecked")
	public String setMaxdays() {
		result.clear();
		System.out.print("[MSG] delete user: ");
		
		String maxdays = newConfig.getMaxdays();
		Connection conn = DBConn.getConn();
		String sql = "update syscfg set config_value='"+maxdays+"' where config_item='maxdays'";
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
	 * Set the cost rate of books that is expired.
	 * <br>
	 * parameter <code>costrate</code> is in {@link #newConfig}
	 * 
	 * @return Always <code>SUCCESS</code>
	 */
	@SuppressWarnings("unchecked")
	public String setCostRate() {
		result.clear();
		System.out.print("[MSG] delete user: ");
		
		String costrate = newConfig.getCostrate();
		Connection conn = DBConn.getConn();
		String sql = "update syscfg set config_value='"+costrate+"' where config_item='costrate'";
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
	 * 
	 * @return result stream to struts framework
	 */
	public InputStream getInputStream() {
	    return inputStream;
	}

	@Override
	public Config getModel() {
		return newConfig;
	}
}

