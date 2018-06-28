from connectDB import connectDB
from sqlcmds import *

def createAll(conn):
	cursor = conn.cursor()
	cursor.execute(sql_create_users_table)
	cursor.execute(sql_create_admin_table)
	cursor.execute(sql_create_book_table)
	cursor.execute(sql_create_copy_table)
	cursor.execute(sql_create_rentings_view)
	conn.commit()

def dropAll(conn):
	cursor = conn.cursor()
	cursor.execute(sql_drop_views)
	cursor.execute(sql_drop_tables)
	conn.commit()

if __name__ == "__main__":
	conn = connectDB()
	dropAll(conn)
	createAll(conn)