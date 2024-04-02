import pymysql
from DB_PERSONAL import *




class Database:
    def __init__(self, host = DB_HOST, user = DB_USER, password = DB_PASSWORD, database= DB_NAME):
        self.connection = pymysql.connect(
            host=host,
            user=user,
            password=password,
            database=database,
            cursorclass=pymysql.cursors.DictCursor
        )

    def getAllData(self, query, params=None):
        with self.connection.cursor() as cursor:
            try:
                cursor.execute(query, params)
                result = cursor.fetchall()
            except: 
                result = None
        return result

    def getSingleData(self, query, params=None):
        with self.connection.cursor() as cursor:
            try:                    
                cursor.execute(query, params)
                result = cursor.fetchone()
            except:
                result = None
        return result
    
    def insert(self, query, params=None):
        with self.connection.cursor() as cursor:
            try:
                cursor.execute(query, params)
                self.connection.commit()
                return True
            except Exception as e:
                print("Errore durante la registrazione dell'utente:", e)
                self.connection.rollback()
                return False



 
    def close(self):
        self.connection.close()

