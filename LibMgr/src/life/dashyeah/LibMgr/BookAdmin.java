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

import life.dashyeah.LibMgr.Data.Book;

@SuppressWarnings("unchecked")
public class BookAdmin extends ActionSupport implements ModelDriven<Book>,SessionAware{
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
		System.out.print("[MSG] user: ");
		
		String user = (String) session.get("user");
		String sql = "select name,privalleges from admins where username='"+user+"'";
		
		System.out.println("  SQL: "+sql);
		conn = DBConn.getConn();
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			rs.next();
			result.put("status", "OK");
			result.put("username", user);
			result.put("name", rs.getString("name"));
			result.put("privalleges", rs.getString("privalleges"));
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
	
	public String deleteBook() {
		result.clear();
		System.out.print("[MSG] delete book: ");
		
		String bookid = book.getBookid();
		
		if(BookMgr.deleteBook(bookid)) {
			result.put("status", "OK");
		}else {
			result.put("status", "ERROR");
		}

		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(re.getBytes(StandardCharsets.UTF_8));
	    return SUCCESS;
	}

	public String addBook() {
		result.clear();
		System.out.print("[MSG] add book: ");
		
		if(BookMgr.addBook(book)) {
			result.put("status", "OK");
		}else {
			result.put("status", "ERROR");
		}
		
		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(re.getBytes(StandardCharsets.UTF_8));
	    return SUCCESS;
	}
	
	public String addCopy() {
		result.clear();
		System.out.print("[MSG] restore: ");
		
		if(BookMgr.addCopy(book.getBookid(),book.getCopies())) {
			result.put("status", "OK");
		}else {
			result.put("status", "ERROR");
		}
		
		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(re.getBytes(StandardCharsets.UTF_8));
		return SUCCESS;
	}
	
	public String deleteCopy() {
		result.clear();
		System.out.print("[MSG] restore: ");
		
		if(BookMgr.deleteCopy(book.getCopyid())) {
			result.put("status", "OK");
		}else {
			result.put("status", "ERROR");
		}
		
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

