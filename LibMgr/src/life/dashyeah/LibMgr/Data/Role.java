package life.dashyeah.LibMgr.Data;

public abstract class Role {
	public static final String ROLE_USER = "user";
	public static final String ROLE_ADMIN = "admin";
	public static final String PREVALLEGE_ADMIN_USER = "users";
	public static final String PREVALLEGE_ADMIN_BOOK = "books";
	
	public static final String ROLE_NONE = "none";
	
	private Role() {}
}
