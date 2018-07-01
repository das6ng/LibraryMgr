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

public class ConfigAdmin extends ActionSupport implements ModelDriven<Config>{
	/**
	 * default UID
	 */
	private static final long serialVersionUID = 1L;
	
	private InputStream inputStream;
	
	/**
	 * receiving data.
	 */
	private Config newConfig = new Config();
	
	private JSONObject result = new JSONObject();
	
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
	
	public InputStream getInputStream() {
	    return inputStream;
	}

	@Override
	public Config getModel() {
		return newConfig;
	}
}

