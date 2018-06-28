from connectDB import connectDB
from NamePicker import NamePicker
from random import randint

sql_insert_user = "insert into users(username,password,name) values('%s','%s','%s');"
sql_insert_admin = "insert into admins values('%s','%s','%s','%s');"
sql_insert_book = "insert into books(name,author,subject) values('%s','%s','%s');"
sql_insert_copy = "insert into copies(bookid) values(%s);"

np = NamePicker()

def insertUsers(conn):
	cursor = conn.cursor()
	count = 0
	for year in range(2012, 2018):
		for month in range(2,11):
			for no_ in range(100,112):
				username = str(year) + str(month) + str(no_)
				password = '123456'
				name = np.pickName()
				user = (username,password,name)
				try:
					cursor.execute(sql_insert_user % user)
					print(user)
					count += 1
				except Exception as e:
					print(e)
	conn.commit()
	return count

def insertAdmins(conn):
	cursor = conn.cursor()
	count = 0
	admins = (
		('root','users books'), 
	    ('useradmin','users'), 
		('bookadmin','books')
	)
	for admin in admins:
		admin = (admin[0],'123456',np.pickName(),admin[1])
		try:
			cursor.execute(sql_insert_admin % admin)
			print(admin)
			count += 1
		except Exception as e:
			print(e)
	conn.commit()
	return count

def insertBooks(conn):
	cursor = conn.cursor()
	count = 0
	with open('Books.txt',mode='r',encoding='UTF-8') as file:
		books = []
		for line in file:
			line = line[:-1]
			if line != '':
				if line[0] != '#':
					books.append(line.split('|'))
				elif line[0] == '#':
					subject = line.split('#')[-1]
					for book in books:
						book = (book[0],book[1],subject)
						print(book)
						try:
							cursor.execute(sql_insert_book % book)
							count += 1
						except Exception as e:
							print(e)
					books = []
	conn.commit()
	return count
	
def insertCopies(conn):
	cursor = conn.cursor()
	count = 0
	sql = 'select id from books;'
	cursor.execute(sql)
	for book in cursor.fetchall():
		for i in range(randint(1,4)):
			copy = (book[0])
			try:
				cursor.execute(sql_insert_copy % copy)
				count += 1
				# print(copy)
			except Exception as e:
				print(e)
	conn.commit()
	return count

if __name__ == "__main__":
	conn = connectDB()
	u = insertUsers(conn)
	a = insertAdmins(conn)
	b = insertBooks(conn)
	c = insertCopies(conn)
	conn.close()
	
	print("\n\nTotal: %d users, %d admins, %d books, %d copies" % (u,a,b,c))