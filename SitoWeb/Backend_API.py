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
# http://192.168.1.94:8000/api/piatti
@appWebApi.route("/api/piatti")
def getAllRecipes():
    query = "select * from piatti"
    cursor.execute(query)
    result = cursor.fetchall()
    return json.dumps(result)

# api   (TUTTI GLI UTENTI)
# http://192.168.1.94:8000/api/utenti
@appWebApi.route("/api/utenti")
def getAllUsers():
    query = "select * from utenti"
    cursor.execute(query)
    result = cursor.fetchall()
    return json.dumps(result)


# api / RICERCA PER NOME PIATTO  (anche solo una parte del nome)
# http://192.168.0.110:8000/api/ricercaPerNome/funghi
@appWebApi.route("/api/ricercaPerNome/<nome>")
def getRecipesfromName(nome):

    query = "select * from piatti WHERE nome_piatto LIKE %s"
    cursor.execute(query, ('%' + nome+ '%',))
    result = cursor.fetchall()
   
    return json.dumps(result, default=vars)


# api / RESTITUISCE ELENCO PORTATE
@appWebApi.route("/api/portate")
def getPortate():
    query = "SELECT DISTINCT piatti.portata FROM piatti"
    cursor.execute(query)
    result = cursor.fetchall()
    nomi_portate = []
    for record in result:
        nomi_portate.append(record["portata"])
    return jsonify(nomi_portate)


# api / RESTITUISCE ID + NOME_PIATTO + IMMAGINE (5 RANDOM)
@appWebApi.route("/api/randomPiattoIdNomeImg")
def getPiattiImmagini():
    piattiDaRestituire = 5
    query = """SELECT id, nome_piatto, image_name
               FROM piatti ORDER BY RAND() LIMIT %s"""
    cursor.execute(query, (piattiDaRestituire))
    result = cursor.fetchall()
    return jsonify(result)



# api / RESTITUISCE UNA RICETTA COMPLETA (CON JOIN VARI) IN BASE ALL'ID
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



