package life.dashyeah.LibMgr.Data;

/**
 * encapsulated book information
 * 
 * @author Dash Wong dashengyeah@github
 *
 */
public class Book {
	/**
	 * book id
	 */
	private String bookid;
	/**
	 * copy id
	 */
	private String copyid;
	/**
	 * book name
	 */
	private String name;
	/**
	 * author of the book.
	 */
	private String author;
	/**
	 * press of the book.
	 */
	private String press;
	/**
	 * price of the book.
	 */
	private String price;
	/**
	 * amount of copies of the book.
	 */
	private int copies;
	
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getCopyid() {
		return copyid;
	}
	public void setCopyid(String copyid) {
		this.copyid = copyid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public int getCopies() {
		return copies;
	}
	public void setCopies(int copies) {
		this.copies = copies;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	/**
	 * get SQL String of the book.
	 * @return SQL String.
	 */
	public String toSQLString() {
		return "('"+name
			 + "','"+author
			 + "','"+press
			 + "',"+price
			 + ")";
	}
}
