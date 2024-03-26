from flask import Flask, jsonify, request, json
import pymysql
import random
from models import *


appWebApi = Flask(__name__)



@appWebApi.route("/main")
def principale():
    return "ok funziona "

config = {
    'host': 'bvl9qu0y8urzicrcjonj-mysql.services.clever-cloud.com',
    'user': 'udihp2ytyzgp09os',
    'password': 'Vy2duKaXFXncXE5gSwEh',
    'database': 'bvl9qu0y8urzicrcjonj' #COOKIDEA
}

# Connessione al database
db = pymysql.connect(**config)
cursor = db.cursor(pymysql.cursors.DictCursor)


# api   (TUTTI I PIATTI)
# restituisce tutti i record di entità piatto (id, nome_piatto, difficoltà, tempo, provenienza, portata, procedimento, image_name)
# http://192.168.1.94:8000/api/piatti
@appWebApi.route("/api/piatti")
def getAllRecipes():
    query = "select * from piatti"
    cursor.execute(query)
    result = cursor.fetchall()
    return json.dumps(result)

# api (TUTTI GLI UTENTI)
# restituisce tutti i record di entità utenti (id, nome, cognonme, data_nascita, email, username, password)
# http://192.168.1.117:8000/api/utenti
@appWebApi.route("/api/utenti")
def getAllUsers():
    query = "select * from utenti"
    cursor.execute(query)
    result = cursor.fetchall()
    return json.dumps(result)

# api LOGIN
# in caso di richiesta POST (username  + password9) con esito "success" restituisce i dati dello user 
# http://192.168.1.117:8000/api/login
@appWebApi.route("/api/login", methods=["POST"])
def login():
    data = request.get_json()
    username = data["username"]
    password = data["password"]
    query = "select * from utenti where username = %s and password = %s"
    cursor.execute(query, (username, password))
    user = cursor.fetchone()
    if user is None:
        return json.dumps({"success": False, "message": "Utente non trovato"}), 401
    else:
        return json.dumps({"success": True, "user": user}), 200
    




# api / RICERCA PER NOME PIATTO  (anche solo una parte del nome)
# restituisce un record di entità piatto (id, nome_piatto, difficoltà, tempo, provenienza, portata, procedimento, image_name)
# filtrato per nome_piatto
# http://192.168.0.110:8000/api/ricercaPerNome/funghi
@appWebApi.route("/api/ricercaPerNome/<nome>")
def getRecipesfromName(nome):

    query = "select * from piatti WHERE nome_piatto LIKE %s"
    cursor.execute(query, ('%' + nome+ '%',))
    result = cursor.fetchall()
   
    return json.dumps(result, default=vars)


# api / ELENCO PORTATE
# restituisce un array di portate prese dalla tabella piatti (piatti.portate), senza duplicati
# http://192.168.0.110:8000/api/portate
@appWebApi.route("/api/portate")
def getPortate():
    query = "SELECT DISTINCT piatti.portata FROM piatti"
    cursor.execute(query)
    result = cursor.fetchall()
    nomi_portate = []
    for record in result:
        nomi_portate.append(record["portata"])
    return jsonify(nomi_portate)


# api / 5 piatti random
# restituisce 5 piatti (id, nome_piatto, image_name) a random dal database
# http://192.168.0.110:8000/api/randomPiattoIdNomeImg
@appWebApi.route("/api/randomPiattoIdNomeImg")
def getPiattiImmagini():
    piattiDaRestituire = 5
    query = """SELECT id, nome_piatto, image_name
               FROM piatti ORDER BY RAND() LIMIT %s"""
    cursor.execute(query, (piattiDaRestituire))
    result = cursor.fetchall()
    return jsonify(result)



# api / RICETTA COMPLETA DA ID
# restituisce un piatto/ricetta con tutti i campi di piatto ma con in più con un elenco (lista) di ingredienti
# http://192.168.0.110:8000/api/ricerca/ricettaFromId?id_piatto=5
@appWebApi.route("/api/ricerca/ricettaFromId")
def getRicettaCompletaFromId():
    idPiatto = request.args.get("id_piatto")

    query = """SELECT p.id, p.difficolta, p.tempo, p.nome_piatto, p.provenienza, p.procedimento, p.image_name
               FROM piatti p WHERE p.id = %s"""
    
    cursor.execute(query, (idPiatto,))
    result = cursor.fetchone()

    piatto = Piatto(**result)

    query = """SELECT i.nome_ingrediente, r.quantita_ingrediente
               FROM piatti p JOIN ricettario r ON p.id = r.id_piatto
               JOIN ingredienti i ON r.id_ingrediente = i.id WHERE p.id = %s;""" 
    
    cursor.execute(query, (idPiatto,))
    result = cursor.fetchall()
    
    
    for row in result:
        ricettario = Ricettario(**row)
        piatto.ricettario.append(ricettario)
        

    return json.dumps(piatto, default=vars)






if __name__ == "__main__":
    appWebApi.run(host='0.0.0.0', port=8000, debug=True)



