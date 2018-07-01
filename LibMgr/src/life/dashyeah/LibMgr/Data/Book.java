package life.dashyeah.LibMgr.Data;

public class Book {
	private String bookid;
	private String copyid;
	
	private String name;
	private String author;
	private String press;
	private String price;
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
	
	public String toSQLString() {
		return "('"+name
			 + "','"+author
			 + "','"+press
			 + "',"+price
			 + ")";
	}
}
