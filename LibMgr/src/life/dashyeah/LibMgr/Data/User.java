package life.dashyeah.LibMgr.Data;

/**
 * encapsulated user information
 * 
 * @author Dash Wong dashengyeah@github
 *
 */
public class User {
	/**
	 * username
	 */
	private String username;
	/**
	 * password
	 */
	private String password;
	/**
	 * user's role
	 * @see {@link life.dashyeah.LibMgr.Data.Role}
	 */
	private String role;
	
	/**
	 * user's name.
	 */
	private String name;
	/**
	 * user's birthday.
	 */
	private String birthdate;
	/**
	 * user's gender.
	 */
	private String gender;
	
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
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String toString() {
		return username+" "+password+" "+role;
	}
	
	/**
	 * get SQL String of the user.
	 * @return SQL String.
	 */
	public String toSQLString() {
		return "('"+username
			 + "','"+password
			 + "','"+name
			 + "','"+birthdate
			 + "','"+gender
			 + "')";
	}
}