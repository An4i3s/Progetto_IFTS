from flask import Flask, request, render_template, json
import pymysql


appWebApi = Flask(__name__)



@appWebApi.route("/main")
def principale():
    return "ok funziona"

config = {
    'host': 'bvl9qu0y8urzicrcjonj-mysql.services.clever-cloud.com',
    'user': 'udihp2ytyzgp09os',
    'password': 'Vy2duKaXFXncXE5gSwEh',
    'database': 'bvl9qu0y8urzicrcjonj' #COOKIDEA
}

# Connessione al database
db = pymysql.connect(**config)
cursor = db.cursor(pymysql.cursors.DictCursor)


# endpoint


# api   (TUTTI I PIATTI)
# http://192.168.1.94:8000/api/ricette
@appWebApi.route("/api/ricette")
def getAllRecipes():
    query = "select * from piatti"
    cursor.execute(query)
    result = cursor.fetchall()
    print(result)
    return json.dumps(result)


# WEB   (TUTTI I PIATTI)
# http://192.168.1.94:8000/ricette
@appWebApi.route("/ricette")
def webGetRecipesfromName():

    query = "select * from piatti"
    cursor.execute(query)
    result = cursor.fetchall()

    return render_template("piatti_esempio.html", ricette = result)


# api / RICERCA PER NOME PIATTO  (anche solo una parte del nome)
# http://192.168.1.94:8000/api/ricercanome/Cacio%20e%20pepe
@appWebApi.route("/api/ricercanome/<nome>")
def getRecipesfromName(nome):
    query = "select * from piatti WHERE nome_piatto LIKE %s"
    cursor.execute(query, ('%' + nome+ '%',))
    result = cursor.fetchall()
    print(result)
    return json.dumps(result)



# api / RICERCA PER PORTATA E DIFFICOLTA     // questa versione è meglio perchè viene indicizzata dai motori di ricerca//
# http://192.168.1.94:8000/api/ricerca/primo/2
@appWebApi.route("/api/ricerca/<portata>/<difficolta>")
def getRecipesfromPortataAndDifficolta(portata, difficolta):
    query = "select * from piatti WHERE portata = %s AND difficolta = %s"
    cursor.execute(query, (portata, difficolta))
    result = cursor.fetchall()
    return json.dumps(result)


# api / RICERCA PER PORTATA E DIFFICOLTA VERSIONE 2   // questa versione è migliore nel caso di tanti parametri
# http://192.168.1.94:8000/api/ricerca/?portata=primo&difficolta=2
@appWebApi.route("/api/ricerca/")
def _getRecipesfromPortataAndDifficolta():
    portata = request.args.get('portata')
    difficolta = request.args.get('difficolta')
    query = "select * from piatti WHERE portata = %s AND difficolta = %s"
    cursor.execute(query, (portata, difficolta))
    result = cursor.fetchall()
    return json.dumps(result)



# api /restituisce tutti i piatti facendo join con ingredienti e ricettario
# ***** ANCORA DA TERMINARE, C'E' DA CAPIRE SE FARE LE CLASSI MODELLO ANCHE DI RICETTARIO E INGREDIENTE
# http://192.168.1.20:8000/api/piatti_ricette

class Piatto:
    def __init__(self, id, difficolta, tempo, nome_piatto, provenienza, portata, procedimento, image_url, ricettario):
        self.id = id
        self.difficolta = difficolta
        self.tempo = tempo
        self.nome_piatto = nome_piatto
        self.provenienza = provenienza
        self.portata = portata
        self.procedimento = procedimento
        self.image_url = image_url
        self.ricettario = ricettario


@appWebApi.route("/api/piatti_ricette")
def getPiattiRicette():
    query = """
        SELECT p.id, p.difficolta, p.tempo, p.nome_piatto, p.provenienza, p.portata, p.procedimento, p.image_url,
               r.quantita_ingrediente, i.nome_ingrediente
        FROM piatti p
        JOIN ricettario r ON p.id = r.id_piatto
        JOIN ingredienti i ON r.id_ingrediente = i.id
    """
    cursor.execute(query)
    results = cursor.fetchall()

    piatti = {}
    for row in results:
        piatto_id = row['id']
        if piatto_id not in piatti:
            piatti[piatto_id] = {
                "id": row['id'],
                "nome_piatto": row['nome_piatto'],
                "difficolta": row['difficolta'],
                "tempo": row['tempo'],
                "provenienza": row['provenienza'],
                "portata": row['portata'],
                "procedimento": row['procedimento'],
                "image_url": row['image_url'],
                "ricettario": []
            }

        ricettario = {
            "quantita_ingrediente": row['quantita_ingrediente'],
            "nome_ingrediente": row['nome_ingrediente']
        }
        piatti[piatto_id]["ricettario"].append(ricettario)

    return json.dumps(list(piatti.values()))









if __name__ == "__main__":
    appWebApi.run(host='0.0.0.0', port=8000, debug=True)









# come sompra ma con pysql
    

#     from flask import Flask, request, json
# import pymysql

# # import mysql.connector


# appWebApi = Flask(__name__)

# @appWebApi.route("/main")
# def principale():
#     return "ciao tutto bene"


# db_config = {
#     'host': 'localhost',
#     'user': 'root',
#     'password': 'Ciclope14',
#     'database': 'db_cena',

# }

# # Connessione al database con mysql.connector

# # conn = mysql.connector.connect(**db_config)
# # cursor = conn.cursor(dictionary=True)

# db = pymysql.connect(**db_config)
# cursor = db.cursor()

# @appWebApi.route("/users/<nome>")
# def getUserByName(nome):
#     query = """SELECT *
#                FROM anagrafica 
#                WHERE Nome = %s"""
#     cursor.execute(query, (nome,))
#     result = cursor.fetchall()
#     return json.dumps(result)


# @appWebApi.route("/users/")
# def getAllUsers():
#     query = "SELECT * FROM anagrafica"
#     try:
#         cursor.execute(query)
#         #db.commit()
#         result = cursor.fetchall()
#     except:
#         db.rollback()

#     db.close()
#     return json.dumps(result)



# if __name__ == "__main__":
#     appWebApi.run(host='0.0.0.0', port=5000)