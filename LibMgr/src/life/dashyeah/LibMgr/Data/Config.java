package life.dashyeah.LibMgr.Data;

/**
 * encapsulated configuration information
 * 
 * @author Dash Wong dashengyeah@github
 *
 */
public class Config {
	/**
	 * the cost rate of books that is expired.
	 */
	private String costrate;
	/**
	 * the max amount of books that one user can borrow.
	 */
	private String maxrent;
	/**
	 * the max days that one book can be borrowed freely.
	 */
	private String maxdays;
	
	public String getCostrate() {
		return costrate;
	}
	public void setCostrate(String costrate) {
		this.costrate = costrate;
	}
	public String getMaxrent() {
		return maxrent;
	}
	public void setMaxrent(String maxrent) {
		this.maxrent = maxrent;
	}
	public String getMaxdays() {
		return maxdays;
	}
	public void setMaxdays(String maxdays) {
		this.maxdays = maxdays;
	}
	
}
