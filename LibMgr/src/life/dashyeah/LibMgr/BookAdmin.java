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

/**
 * Library management system administrator util:
 * book administration.
 * 
 * @author Dash Wong dashengyeah@github
 *
 */
@SuppressWarnings("unchecked")
public class BookAdmin extends ActionSupport implements ModelDriven<Book>,SessionAware{
	/**
	 * default UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * struts2 return data stream
	 */
	private InputStream inputStream;
	
	/**
	 * database connection
	 */
	private Connection conn = null;
	
	/**
	 * Http session info
	 */
	private SessionMap<String,Object> session;
	
	/**
	 * Receiving data from client.<br>
	 * this is set by {@link com.opensymphony.xwork2.ModelDriven}
	 */
	private Book book = new Book();
	
	/**
	 * result data JSONObject
	 */
	private JSONObject result = new JSONObject();
	
	/**
	 * Get user's base information
	 * @return Always <code>SUCCESS</code>
	 */
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
	
	/**
	 * Delete a book from database by <code>bookid</code>
	 * parameter bookid, required parameter is in {@link #book}
	 * @return Always <code>SUCCESS</code>
	 */
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
	
	/**
	 * Add a book to database
	 * Required parameter {@link #book}
	 * 
	 * @return Always <code>SUCCESS</code>
	 */
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
	
	/**
	 * Add copies of a book
	 * Required parameter are in {@link #book}
	 * 
	 * @return Always <code>SUCCESS</code>
	 */
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
	
	/**
	 * Delete copies of a book
	 * Required parameter are in {@link #book}
	 * 
	 * @return Always <code>SUCCESS</code>
	 */
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
	
	/**
	 * 
	 * @return result stream to struts framework
	 */
	public InputStream getInputStream() {
	    return inputStream;
	}

	@Override
	public Book getModel() {
		return book;
	}
}

