package life.dashyeah.LibMgr.Data;

/**
 * Role definition.
 * 
 * @author Dash Wong
 *
 */
public abstract class Role {
	/**
	 * USER
	 */
	public static final String ROLE_USER = "user";
	/**
	 * Administrator
	 */
	public static final String ROLE_ADMIN = "admin";
	/**
	 * Administrator privilege of users.
	 */
	public static final String PREVILLEGE_ADMIN_USER = "users";
	/**
	 * Administrator privilege of books.
	 */
	public static final String PREVILLEGE_ADMIN_BOOK = "books";
	/**
	 * None type role.
	 */
	public static final String ROLE_NONE = "none";
	
	private Role() {}
}
