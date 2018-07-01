package life.dashyeah.LibMgr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.dashyeah.LibMgr.Data.Book;

@SuppressWarnings("unchecked")
public class UserAction extends ActionSupport implements ModelDriven<Book>,SessionAware{
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
	private Book book = new Book();
	
	private JSONObject result = new JSONObject();
	
	public String userinfo() {
		result.clear();
		System.out.print("[MSG] userinfo: ");
		
		String user = (String) session.get("user");
		String sql = "select name,birthdate,gender from users where username='"+user+"'";
		
		System.out.println("  SQL: "+sql);
		conn = DBConn.getConn();
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			rs.next();
			result.put("status", "OK");
			result.put("username", user);
			result.put("name", rs.getString("name"));
			result.put("birthday", rs.getString("birthdate"));
			result.put("gender", rs.getString("gender"));
			conn.close();
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
	
	public String rentingList() {
		result.clear();
		System.out.print("[MSG] rentlist: ");
		
		String user = (String) session.get("user");
		String sql = "select copyid,bookname,author,price,press,rentdate from rentings where username='"+user+"'";
		
		System.out.println("  SQL: "+sql);
		conn = DBConn.getConn();
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			JSONArray list = new JSONArray();
			while(rs.next()) {
				JSONObject copy = new JSONObject();
				copy.put("copyid", rs.getString("copyid"));
				copy.put("bookname", rs.getString("bookname"));
				copy.put("author", rs.getString("author"));
				copy.put("price", rs.getString("price"));
				copy.put("rentdate", rs.getString("rentdate"));
				copy.put("press", rs.getString("press"));
				
				list.add(copy);
			}
			result.put("status", "OK");
			result.put("rentlist", list);
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

	public String rent() {
		result.clear();
		System.out.print("[MSG] rent: ");
		
		String user = (String) session.get("user");
		String sql = "";
		
		if(!BookMgr.existCopy(book.getCopyid())) {
			result.put("status", "ERROR");
			result.put("info", "No such book.");
		}else if(BookMgr.isRent(book.getCopyid())){
			result.put("status", "ERROR");
			result.put("info", "This copy is rent already.");
		}
		
		sql = "select COUNT(*) cnt from copies where username='"+user+"'";
		
		System.out.println("  SQL: "+sql);
		this.conn = DBConn.getConn();
		if(!sql.equals(""))
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			rs.next();
			int count = rs.getInt("cnt");
			int maxrent = Integer.parseInt(BookMgr.getConfig("maxrent"));
			if(count < maxrent) {
				String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
				sql = "update copies set username='"+user+"',rentdate='"+date+
					  "' where id="+book.getCopyid();
				conn.prepareStatement(sql).executeUpdate();
				
				result.put("status", "OK");
			}else {
				result.put("status", "ERROR");
				result.put("info", "Rentings already reach limit-"+maxrent);
			}
			conn.close();
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
	
	public String restore() {
		result.clear();
		System.out.print("[MSG] restore: ");
		
		String user = (String) session.get("user");
		conn = DBConn.getConn();
		String sql = "select username from copies where id="+book.getCopyid();
		
		boolean flag = false; // isRenting
		
		System.out.println("  SQL: "+sql);
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			if(rs.next() && user.equals(rs.getString("username")))
				flag = true;
			else {
				result.put("status", "ERROR");
				result.put("info", "You are not renting this book.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			result.put("status", "ERROR");
			result.put("info", "remote sql error.");
		}
		
		sql = "select rentdate,price from rentings where"
			+ " copyid="+book.getCopyid();
		System.out.println("  SQL: "+sql);
		if(flag) try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			rs.next();
			
			Date rentdate = DateFormat.getDateInstance(DateFormat.MEDIUM).parse(rs.getString("rentdate"));
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(new Date());c2.setTime(rentdate);
			long days =  (c1.getTime().getTime() - c2.getTime().getTime())/(1000*60*60*24);
			
			Double cost;
			Double costrate = Double.parseDouble(BookMgr.getConfig("costrate"));
			if(days > BookMgr.MAX_RENTING_DAYS) cost = (days-30)*costrate*rs.getDouble("price");
			else cost = 0.0;
			
			sql = "update copies set username=NULL,rentdate=NULL where"+
				 " id="+book.getCopyid();
			System.out.println("  SQL: "+sql);
			conn.prepareStatement(sql).executeUpdate();
			
			result.put("status", "OK");
			result.put("cost", cost.toString());
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
			
			result.put("status", "ERROR");
			result.put("info", "remote sql error.");
		}
		
		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(re.getBytes(StandardCharsets.UTF_8));
		return SUCCESS;
	}
	
	public String searchBook() {
		result.clear();
		System.out.print("[MSG] search: ");
		
		String key = book.getName();
		JSONArray r = BookMgr.searchBook(key);
		
		result.put("status", "OK");
		result.put("result", r);

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
	public Book getModel() {
		return book;
	}
}

