from flask import Flask, jsonify, request
import json
import pymysql
import random
from models import *
from Database import *
import time
from datetime import datetime
from datetime import date




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
    result = db.getAllData(query)
    return json.dumps(result)

# api a2 (TUTTI GLI UTENTI)
# restituisce tutti i record di entità utenti (id, nome, cognonme, data_nascita, email, username, password)
# http://192.168.1.117:8000/api/utenti
@appWebApi.route("/api/utenti")
def getAllUsers():
    query = "select * from utenti"
    result = db.getAllData(query)
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
        result = db.getSingleData(query, (username, password))

        user = User(**result)
        
        if user is None:
            return json.dumps({"success": False, "message": "Utente non trovato"}), 401
        else:
            print(result)
            return json.dumps(user, default=vars), 200
        
    except Exception as e:
        print("Errore durante il login:", e)
        return jsonify({"success": False, "message": "Errore durante il login"}), 500
    

    
# api a9  REGISTRAZIONE
# http://192.168.1.117:8000/api/signup
@appWebApi.route("/api/signup", methods=["POST"])
def register():
    user_data = request.get_json()

    nome = user_data.get('nome')
    cognome = user_data.get('cognome')
    data_nascita_str = user_data.get('data_nascita')
    data_nascita = datetime.strptime(data_nascita_str, '%b %d, %Y %I:%M:%S %p')
    email = user_data.get('email')
    username = user_data.get('username')
    password = user_data.get('password')

    query = """INSERT INTO utenti (nome, cognome, data_nascita, email, username, password)
               VALUES (%s, %s, %s, %s, %s, %s);"""
    values = (nome, cognome, data_nascita, email, username, password)

    
    if db.insert(query, values) == True:
        query = "select * from utenti order by id desc limit 1"
        result = db.getSingleData(query)
        newUser = User(**result)
        return json.dumps(newUser, default=vars), 201
    else:
        return "Errore: Username i email già esistente.", 500




# api a4 RICERCA PER NOME PIATTO  (anche solo una parte del nome)
# restituisce un record di entità piatto (id, nome_piatto, difficoltà, tempo, provenienza, portata, image_name)
# filtrato per nome_piatto
# http://192.168.0.110:8000/api/ricercaPerNome/funghi
@appWebApi.route("/api/ricercaPerNome/<nome>")
def getRecipesfromName(nome):


    query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE nome_piatto LIKE %s"

    result = db.getAllData(query, ('%' + nome+ '%',))
    return json.dumps(result, default=vars)


# api a5 RICERCA PER NOME PORTATA 
# restituisce un record di entità piatto (id, nome_piatto, difficoltà, tempo, provenienza, portata, image_name)
# filtrato per portata
# http://192.168.0.110:8000/api/ricercaPerPortata/primo
@appWebApi.route("/api/ricercaPerPortata/<portata>")
def getRecipesfromPortata(portata):

    query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE portata = %s"
    result = db.getAllData(query, (portata,))

    return json.dumps(result, default=vars)


# api a6 ELENCO PORTATE
# restituisce una lista di portate (primo, secondo..) con relativo url immagine, prese dalla tabella piatti, senza duplicati
# http://192.168.0.110:8000/api/portate
@appWebApi.route("/api/portate")
def getPortate():
    query = "SELECT DISTINCT piatti.portata FROM piatti"
    try:
        queryResult = db.getAllData(query)
        print(query, queryResult)
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
        queryResult = db.getAllData(query)
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
    
    result = db.getSingleData(query, (idPiatto))

    if result is not None:

        piatto = Piatto(**result)
        
        query = """SELECT i.nome_ingrediente, r.quantita_ingrediente
                FROM piatti p JOIN ricettario r ON p.id = r.id_piatto
                JOIN ingredienti i ON r.id_ingrediente = i.id WHERE p.id = %s;""" 
        
        result = db.getAllData(query, (idPiatto))
        
        for row in result:
            ricettario = Ricettario(**row)
            piatto.ricettario.append(ricettario)
            
        return json.dumps(piatto, default=vars)
    
    else:
        return json.dumps([])
    




# api 10 ELENCO PREFERITI
# http://192.168.0.110:8000/api/preferiti?id_utente=1
@appWebApi.route("/api/preferitiFromIdUtente")
def getPreferiti():
    idUtente = request.args.get("id_utente")
    query = """select p.id, nome_piatto, difficolta, tempo, portata, provenienza,image_name
               from piatti p JOIN preferiti pref ON p.id = pref.id_piatto WHERE pref.id_utente = %s"""
    result = db.getAllData (query, (idUtente))
    return json.dumps(result, default=vars)


# api 11 CONTROLLA SE PREFERITO IN BASE A ID_UTENTE E ID_PIATTO
# http://192.168.0.110:8000/api/preferiti/
@appWebApi.route("/api/checkPreferito")
def checkPreferiti():
    idUtente = request.args.get("id_utente")
    idPiatto = request.args.get("id_piatto")
    query = """select preferiti.id FROM preferiti WHERE id_utente = %s AND id_piatto = %s;"""
    result = db.getSingleData (query, (idUtente, idPiatto,))
    if result is None:
        return json.dumps(0)
    else:
        return json.dumps(1)
    
    
# api  Aggiungi / togli da preferiti
# http://192.168.0.110:8000/api/preferiti/
@appWebApi.route("/api/updatePreferito")
def updatePreferiti():

    idUtente = request.args.get("id_utente")
    idPiatto = request.args.get("id_piatto")
    query = """select preferiti.id FROM preferiti WHERE id_utente = %s AND id_piatto = %s;"""
    result = db.getSingleData (query, (idUtente, idPiatto,))

    if result is None:
        query = "INSERT into preferiti (id_utente, id_piatto) VALUES (%s, %s)"
        db.insert(query,(idUtente, idPiatto))
        return json.dumps([])
    
    else:
        query = "delete from preferiti where id_utente = %s and id_piatto = %s;"
        db.delete(query, (idUtente, idPiatto))
        return json.dumps([])



# api 12 bis UPDATE DATI UTENTE
@appWebApi.route("/api/agg_DatiUtente", methods=["PUT"])
def update_dati():
    user_data = request.get_json()

    id = user_data.get("id")
    nome = user_data.get("nome")
    cognome = user_data.get("cognome")
    data_nascita_str = user_data.get("data_nascita")
    data_nascita = datetime.strptime(data_nascita_str, "%b %d, %Y %I:%M:%S %p")
    password = user_data.get("password")

    try:
        query = """
            UPDATE utenti
            SET nome = %s,
                cognome = %s,
                data_nascita = %s,
                password = %s
            WHERE id = %s
        """
        values = (nome, cognome, data_nascita, password, id)

       # cursor = db.connection.cursor()
       # cursor.execute(query, values)
       # db.connection.commit()  # Commit changes

        db.update(query, values)

        query = "SELECT * FROM utenti WHERE id = %s"
        #cursor.execute(query, (id,))
        #result = cursor.fetchone()
        result = db.getSingleData(query, (id,))

        if result:
            new_user = User(**result)
            return json.dumps(new_user, default=vars), 201
        else:
            return "User not found after update", 404

    except Exception as e:
        return f"Error updating user data: {str(e)}", 500
    

@appWebApi.route("/api/getTipoPasto", methods=["GET"])
def getTipoPasto():
    query = "select * from tipo_pasto"
    result = db.getAllData(query)
    print(query, result)
    return json.dumps(result, default=vars)


# api  INSERISCI MENU SETTIMANALE
# http://192.168.0.110:8000/api/insertWeeklyMenu/
@appWebApi.route("/api/insertWeeklyMenu", methods = ["PUT"])
def insertWeeklyMenu():

    idUtente = request.args.get("id_utente")
    idPiatto = request.args.get("id_piatto")
    idPasto = request.args.get("id_pasto")
    stringData = request.args.get("data")
    stringData = stringData.replace(' GMT', '')

    formato_data = '%a %b %d %H:%M:%S %Y'
    data = datetime.strptime(stringData, formato_data)

    query = """insert into menu_settimanale (id_utente, id_piatto, id_pasto, data)
               VALUES (%s, %s, %s, %s)"""
    
    if db.insert(query, (idUtente, idPiatto, idPasto, data)) == True:
                return json.dumps(1), 201
    else:
         return json.dumps(0), 500
    

def convert_to_serializable(obj):
    if isinstance(obj, date):
        return obj.strftime('%Y-%m-%d')
    raise TypeError("Object of type '%s' is not JSON serializable" % type(obj).__name__)


# api  GET MENU SETTIMANALE
# http://192.168.0.110:8000/api/getWeeklyMenu
@appWebApi.route("/api/getWeeklyMenu", methods = ["GET"])

def getWeeklyMenu():

    idUtente = request.args.get("id_utente")
    
    query = """select `data`, nome_piatto, image_name, nome_tipo_pasto from menu_settimanale join piatti
               on id_piatto = piatti.id join tipo_pasto on id_pasto = tipo_pasto.id where id_utente = %s
               AND `data` BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 6 DAY);"""
    
    result = db.getAllData(query, (idUtente,))
    return json.dumps(result, default=convert_to_serializable)










if __name__ == "__main__":
    try:
        db = Database()
        appWebApi.run(host='0.0.0.0', port=8000)
    except KeyboardInterrupt:
        db.close()



