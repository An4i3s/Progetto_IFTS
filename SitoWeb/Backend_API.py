from flask import Flask, jsonify, request
import json
import pymysql
import random
from models import *
from Database import *
import time


appWebApi = Flask(__name__)


db = None


@appWebApi.route("/main")
def principale():
    return "ok funziona "


# api a1   (TUTTI I PIATTI)
# restituisce tutti i record di entità piatto (id, nome_piatto, difficoltà, tempo, provenienza, portata, procedimento, image_name)
# http://192.168.1.94:8000/api/piatti
@appWebApi.route("/api/piatti")
def getAllRecipes():
    query = "select * from piatti"
    result = db.fetchAll(query)
    return json.dumps(result)

# api a2 (TUTTI GLI UTENTI)
# restituisce tutti i record di entità utenti (id, nome, cognonme, data_nascita, email, username, password)
# http://192.168.1.117:8000/api/utenti
@appWebApi.route("/api/utenti")
def getAllUsers():
    query = "select * from utenti"
    result = db.fetchAll(query)
    return json.dumps(result)

# api a3 LOGIN
# in caso di richiesta POST (username  + password9) con esito "success" restituisce i dati dello user 
# http://192.168.1.117:8000/api/login
@appWebApi.route("/api/login", methods=["POST"])
def login():
    try:
        data = request.get_json()
        username = data["username"]
        password = data["password"]

        query = "select * from utenti where username = %s and password = %s"
        result = db.fetchOne(query, (username, password))

        user = User(**result)
        
        if user is None:
            return json.dumps({"success": False, "message": "Utente non trovato"}), 401
        else:
            return json.dumps(user, default=vars), 200
        
    except Exception as e:
        print("Errore durante il login:", e)
        return jsonify({"success": False, "message": "Errore durante il login"}), 500

    



# api a4 RICERCA PER NOME PIATTO  (anche solo una parte del nome)
# restituisce un record di entità piatto (id, nome_piatto, difficoltà, tempo, provenienza, portata, image_name)
# filtrato per nome_piatto
# http://192.168.0.110:8000/api/ricercaPerNome/funghi
@appWebApi.route("/api/ricercaPerNome/<nome>")
def getRecipesfromName(nome):

    query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE nome_piatto LIKE %s"
    result = db.fetchAll(query, ('%' + nome+ '%',))

    print(result)

    return json.dumps(result, default=vars)


# api a5 RICERCA PER NOME PORTATA 
# restituisce un record di entità piatto (id, nome_piatto, difficoltà, tempo, provenienza, portata, image_name)
# filtrato per portata
# http://192.168.0.110:8000/api/ricercaPerPortata/primo
@appWebApi.route("/api/ricercaPerPortata/<portata>")
def getRecipesfromPortata(portata):

    query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE portata = %s"
    result = db.fetchAll(query, (portata,))

    return json.dumps(result, default=vars)


# api a6 ELENCO PORTATE
# restituisce una lista di portate (primo, secondo..) con relativo url immagine, prese dalla tabella piatti, senza duplicati
# http://192.168.0.110:8000/api/portate
@appWebApi.route("/api/portate")
def getPortate():
    query = "SELECT DISTINCT piatti.portata FROM piatti"
    try:
        queryResult = db.fetchAll(query)
        print(queryResult)
        if queryResult is None:
            return jsonify([])
            
        
        resultWithUrl = []

        for record in queryResult:
            portata = record["portata"]
            url_portata = f"/static/img/{portata.lower()}.jpg"
            resultWithUrl.append({"portata": portata, "urlportata": url_portata})  
                
        return jsonify(resultWithUrl) 
    
    except Exception as e:
        return jsonify({"error": str(e)})
    

    
# api a7 piatti random
# restituisce una lista di piatti casuali con id, nome_piatto e url immagine (image_name) senza duplicati
# http://192.168.0.110:8000/api/randomPiattoIdNomeImg
@appWebApi.route("/api/randomPiattoIdNomeImg")
def getPiattiImmagini():
    try:
        piattiDaRestituire = 5
        query = f"""SELECT id, nome_piatto, image_name FROM piatti ORDER BY RAND() LIMIT {piattiDaRestituire}"""

        print(query)
        timeIniz = time.time()
        queryResult = db.fetchAll(query)
        timeFin = time.time()

        print(queryResult)
        ricetteCasuali = []

        if queryResult is None:
            return jsonify([])

        for record in queryResult:
            id = record["id"]
            nome = record["nome_piatto"]
            imgName = record["image_name"]
            url_portata = f"/static/recipes/{imgName.lower()}"
            ricetteCasuali.append({"id": id, "nome_piatto": nome,"image_name": url_portata}) 
        return jsonify(ricetteCasuali)
    
    except Exception as e:
        print("An error occurred:", str(e))
        return jsonify({'error': 'Errore'}), 500






# api a8 RICETTA COMPLETA DA ID
# restituisce un piatto/ricetta con tutti i campi di piatto (modellato con calsse Piatto) ma con in più con un elenco (lista) di ingredienti (modellato con la classe Ricettario)
# http://192.168.0.110:8000/api/ricerca/ricettaFromId?id_piatto=5
@appWebApi.route("/api/ricerca/ricettaFromId")
def getRicettaCompletaFromId():
    idPiatto = request.args.get("id_piatto")

    query = """SELECT p.id, p.difficolta, p.tempo, p.nome_piatto, p.portata, p.provenienza, p.procedimento, p.image_name
               FROM piatti p WHERE p.id = %s"""
    
    result = db.fetchOne(query, (idPiatto))

    if result is not None:

        piatto = Piatto(**result)
        
        query = """SELECT i.nome_ingrediente, r.quantita_ingrediente
                FROM piatti p JOIN ricettario r ON p.id = r.id_piatto
                JOIN ingredienti i ON r.id_ingrediente = i.id WHERE p.id = %s;""" 
        
        result = db.fetchAll(query, (idPiatto))
        
        for row in result:
            ricettario = Ricettario(**row)
            piatto.ricettario.append(ricettario)
            
        return json.dumps(piatto, default=vars)
    
    else:
        return json.dumps([])
    

# api a9  REGISTRAZIONE
# http://192.168.1.117:8000/api/signup
# @appWebApi.route("/api/signup", methods=["POST"])
# def register():

#     newUser = UserRegister (**request.get_json())

#     try:
#         query = f"""INSERT INTO `utenti` ("nome", "cognome", "data_nascita", "email", "username", "password")
#                 VALUES ({newUser.nome},{newUser.cognome},{newUser.data_nascita}, {newUser.email}, {newUser.username}, {newUser.password}mple.com','mario123','password123');"""
   
   
#     # query = "select * from utenti where username = %s and password = %s"
#     # user = db.fetchOne(query, (username, password))

        
#     # if user is None:
#     #     return json.dumps({"success": False, "message": "Utente non trovato"}), 401
#     # else:
#     #     return json.dumps({"success": True, "user": user}), 200









if __name__ == "__main__":
    try:
        db = Database()
        appWebApi.run(host='0.0.0.0', port=8000)
    except KeyboardInterrupt:
        db.close()



