import pymysql


DB_HOST = 'bvl9qu0y8urzicrcjonj-mysql.services.clever-cloud.com'
DB_USER = 'udihp2ytyzgp09os'
DB_PASSWORD = 'Vy2duKaXFXncXE5gSwEh'
DB_NAME = 'bvl9qu0y8urzicrcjonj'

class Database:
    def __init__(self, host = DB_HOST, user = DB_USER, password = DB_PASSWORD, database= DB_NAME):
        self.connection = pymysql.connect(
            host=host,
            user=user,
            password=password,
            database=database,
            cursorclass=pymysql.cursors.DictCursor
        )

    def fetchAll(self, query, params=None):
        with self.connection.cursor() as cursor:
            try:
                cursor.execute(query, params)
                result = cursor.fetchall()
            except: 
                result = None
        return result

    def fetchOne(self, query, params=None):
        with self.connection.cursor() as cursor:
            try:                    
                cursor.execute(query, params)
                result = cursor.fetchone()
            except:
                result = None
        return result
 
    def close(self):
        self.connection.close()

