package life.dashyeah.LibraryMgr.Data;

public class Book {
	private String id;
	private String name;
	private String author;
	private String subject;
	
	public Book() {}
	public Book(String name, String author, String subject) {
		this.name = name;
		this.author = author;
		this.subject = subject;
	}
	public Book(String id, String name, String author, String subject) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.subject = subject;
	}
	
	public String toSQLString() {
		return "("
				+ id
				+ ",'"+name
				+ "','"+author
				+ "','"+subject
				+ "')";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
