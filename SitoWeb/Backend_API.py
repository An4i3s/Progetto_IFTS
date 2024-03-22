from flask import Flask, jsonify, request, render_template, json
import pymysql
import random


appWebApi = Flask(__name__)



@appWebApi.route("/main")
def principale():
    return "ok funziona "

config = {
    'host': 'bvl9qu0y8urzicrcjonj-mysql.services.clever-cloud.com',
    'user': 'udihp2ytyzgp09os',
    'password': 'Vy2duKaXFXncXE5gSwEh',
    'database': 'bvl9qu0y8urzicrcjonj' #COOKIDEAfgfgfg
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




# api / RICERCA PER NOME PIATTO  (anche solo una parte del nome)
# http://192.168.0.110:8000/api/ricercaPerNome/funghi
@appWebApi.route("/api/ricercaPerNome/<nome>")
def getRecipesfromName(nome):

    query = "select * from piatti WHERE nome_piatto LIKE %s"
    cursor.execute(query, ('%' + nome+ '%',))
    result = cursor.fetchall()
   
    return json.dumps(result, default=vars)


# @@@@@PROVE VARIE PER RICERCA AVANZATA
# api / RICERCA PER PORTATA E DIFFICOLTA     // questa versione è meglio perchè viene indicizzata dai motori di ricerca//
# http://192.168.0.110:8000/api/ricerca/primo/2
@appWebApi.route("/api/ricerca/<portata>/<difficolta>")
def getRecipesfromPortataAndDifficolta(portata, difficolta):
    query = "select * from piatti WHERE portata = %s AND difficolta = %s"
    cursor.execute(query, (portata, difficolta))
    result = cursor.fetchall()
    return json.dumps(result)


# @@@@@PROVE VARIE PER RICERCA AVANZATA
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







if __name__ == "__main__":
    appWebApi.run(host='0.0.0.0', port=8000, debug=True)



