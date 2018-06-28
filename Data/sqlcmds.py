sql_create_users_table = """
create table users(
	username varchar(64) primary key,
	password varchar(128) not null,
	name varchar(64) default null,
	max_rent integer default 10
);
"""

sql_create_admin_table = """
create table admins(
	username varchar(64) primary key,
	password varchar(128) not null,
	name varchar(64) not null,
	privalleges varchar(64) default 'book'
);
"""

sql_create_book_table = """
create table books(
	id integer AUTO_INCREMENT primary key,
	name varchar(512) not null,
	author varchar(128) not null,
	subject varchar(128) not null
);
"""

sql_create_copy_table = """
create table copies(
	id integer AUTO_INCREMENT primary key,
	bookid int not null,
	username varchar(64) default null,
	rentdate varchar(64) default null,
	
	FOREIGN KEY (bookid) REFERENCES books(id),
	FOREIGN KEY (username) REFERENCES users(username)
);
"""

sql_create_rentings_view = """
create view rentings(bookid,bookname,author,username,rentdate) as
	select books.id,books.name,books.author,copies.username,copies.rentdate
	from books,copies where books.id=bookid
;
"""

sql_drop_views = "drop view if exists rentings;"
sql_drop_tables = "drop table if exists copies,books,admins,users;"
