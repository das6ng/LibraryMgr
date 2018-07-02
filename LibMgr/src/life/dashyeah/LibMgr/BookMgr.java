package life.dashyeah.LibMgr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import life.dashyeah.LibMgr.Data.Book;


/**
 * Library management system helper util:
 * book related.
 * 
 * @author Dash Wong dashengyeah@github
 *
 */
public abstract class BookMgr {
	private BookMgr() {}
	
	/**
	 * To find out if a book exists.
	 * 
	 * @param bookid specified book's id
	 * @return true  yes <br>false  no
	 */
	public static boolean existBook(String bookid) {
		Connection conn = DBConn.getConn();
		String sql = "select id from books where id="+bookid;
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
	 *  To find out if there is any copy of a book.
	 * 
	 * @param bookid specified book's id
	 * @return true  yes <br>false  no
	 */
	public static boolean hasCopy(String bookid) {
		Connection conn = DBConn.getConn();
		String sql = "select id from copies where bookid="+bookid;
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
	 * To find out if a copy exists.
	 * 
	 * @param copyid specified copy's id
	 * @return true  yes <br>false  no
	 */
	public static boolean existCopy(String copyid) {
		Connection conn = DBConn.getConn();
		String sql = "select id from copies where id="+copyid;
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
	 * Add a book to database.
	 * 
	 * @param book information of the book.
	 * @return true successful <br>false failed
	 */
	public static boolean addBook(Book book) {
		Connection conn = DBConn.getConn();
		String sql = "insert into books(name,author,press,price) values"+book.toSQLString();
		
		System.out.println("  SQL: "+sql);
		try {
			conn.prepareStatement(sql).executeUpdate();
			sql = "select id from books where name='"+book.getName()+"' and author='"+book.getAuthor()+"'";
			ResultSet rs = conn.prepareCall(sql).executeQuery();
			rs.next();
			book.setBookid(rs.getString("id"));
			addCopy(book.getBookid(), book.getCopies());
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * delete a book and all its copies from database.
	 * 
	 * @param bookid specified book's id
	 * @return true successful <br>false failed
	 */
	public static boolean deleteBook(String bookid) {
		//if(hasCopy(bookid)) return false;
		
		if(!deleteCopy(bookid)) return false;
		Connection conn = DBConn.getConn();
		String sql = "delete from books where id="+bookid;
		
		System.out.println("  SQL: "+sql);
		try {
			conn.prepareStatement(sql).executeUpdate();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Add copy of a book.
	 * 
	 * @param bookid specified book's id
	 * @param count amount of copies.
	 * @return true successful <br>false failed
	 */
	public static boolean addCopy(String bookid, int count) {
		Connection conn = DBConn.getConn();
		String sql = "insert into copies(bookid) values("+bookid+")";
		
		System.out.println("  SQL: "+sql);
		try {
			for(int i=0; i<count; i++)
				conn.prepareStatement(sql).executeUpdate();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * delete a copy from database.
	 * 
	 * @param copyid specified copy's id.
	 * @return true successful <br>false failed
	 */
	public static boolean deleteCopy(String copyid) {
		if(!existCopy(copyid)) return true;

		Connection conn = DBConn.getConn();
		String sql = "delete from copies where bookid="+copyid;
		
		System.out.println("  SQL: "+sql);
		try {
			conn.prepareStatement(sql).executeUpdate();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * To find out if a copy is borrowed by someone.
	 * 
	 * @param copyid specified copy's id
	 * @return true yes <br>false no
	 */
	public static boolean isRent(String copyid) {
		Connection conn = DBConn.getConn();
		String sql = "select username from copies where id="+copyid;
		
		System.out.println("  SQL: "+sql);
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			if(rs.next() && !(rs.getString("username") == null || rs.getString("username") == "")) {
				conn.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * To get a configuration's value of the system.
	 * 
	 * @param key configuration name
	 * @return configuration value
	 */
	public static String getConfig(String key) {
		Connection conn = DBConn.getConn();
		String sql = "select config_value from syscfg where config_item='"+key+"'";
		
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			rs.next();
			String re = rs.getString("config_value");
			conn.close();
			return re;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * search books in database which name contains any of the keywords.
	 * 
	 * @param key keywords, using ' ' to separate more one keyword.
	 * @return a JSONArray contains all results.
	 */
	@SuppressWarnings("unchecked")
	public static JSONArray searchBook(String key) {
		Connection conn = DBConn.getConn();
		JSONArray result = new JSONArray();
		
		String sql = "select copyid,bookname,author,press,price,username from rentings";
		String keys[] = key.split(" ");
		
		result.clear();
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			while(rs.next()) {
				String name = rs.getString("bookname");
				boolean f = false;
				for(String k:keys) {
					if(name.contains(k)) f = true;
				}
				if(f){
					JSONObject copy = new JSONObject();
					copy.put("copyid", rs.getString("copyid"));
					copy.put("bookname", rs.getString("bookname"));
					copy.put("author", rs.getString("author"));
					copy.put("press", rs.getString("press"));
					copy.put("price", rs.getString("price"));
					if(rs.getString("username") != null)
							copy.put("rent", "yes");
					else copy.put("rent", "no");
					
					result.add(copy);
				}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
