package life.dashyeah.LibraryMgr.Data;

public class User {
	private String username;
	private String password;
	private String role;
	private String name;
	private String maxRent = "10";
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMaxRent() {
		return maxRent;
	}
	public void setMaxRent(String maxRent) {
		this.maxRent = maxRent;
	}
	
	public String toString() {
		return username+" "+password+" "+role;
	}
	public String toSQLString() {
		return "('"+username
			 + "','"+password
			 + "','"+name
			 + "',"+maxRent
			 + ")";
	}
}