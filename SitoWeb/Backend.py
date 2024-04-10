from flask import Flask, jsonify, request, render_template, json, redirect, url_for, session
# from flask_wtf import CSRFProtect
from models import *
from Database import *
import json
import pymysql
# import bcrypt
import random
import time
from datetime import datetime
from datetime import date
from decimal import Decimal


appWebApi = Flask(__name__)
appWebApi.secret_key= '123456'
# csrf = CSRFProtect(appWebApi)

db = None


# *********************************************************************************************************************************
# *********************************************************************************************************************************
# ***************************************************** ENDPOINT API **************************************************************
# *********************************************************************************************************************************
# *********************************************************************************************************************************

@appWebApi.route("/main")
def principale():
    return "ok funziona "



# api a3 - login
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
    


# api a4 RICERCA PER NOME PIATTO  (anche solo una parte del nome)
@appWebApi.route("/api/ricercaPerNome/<nome>")
def getRecipesfromName(nome):

    query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE nome_piatto LIKE %s"

    result = db.getAllData(query, ('%' + nome+ '%',))
    return json.dumps(result, default=vars)



# api a5 RICERCA PER NOME PORTATA 
@appWebApi.route("/api/ricercaPerPortata/<portata>")
def getRecipesfromPortata(portata):

    query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE portata = %s"
    result = db.getAllData(query, (portata,))

    return json.dumps(result, default=vars)





# api a6 ELENCO PORTATE
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
# restituisce un piatto/ricetta con tutti i campi di piatto (modellato con classe Piatto) ma con in più con un elenco (lista) di ingredienti (modellato con la classe Ricettario)
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
    

# api a9 - registrazione
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
    

    
# api 12 UPDATE DATI UTENTE
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

        db.update(query, values)

        query = "SELECT * FROM utenti WHERE id = %s"
        result = db.getSingleData(query, (id,))

        if result:
            new_user = User(**result)
            return json.dumps(new_user, default=vars), 201
        else:
            return "User not found after update", 404

    except Exception as e:
        return f"Error updating user data: {str(e)}", 500
    



# API 13 RETURN USER BY ID
@appWebApi.route("/api/getUserById")
def getUser():
    id_utente = request.args.get("id_utente")
    query = """select * FROM utenti WHERE id = %s"""
    result = db.getSingleData (query, (id_utente,))
    if result:
        new_user = User(**result)
        return json.dumps(new_user, default=vars), 201
    else:
        return "User not found", 404
    

# api a14 Aggiungi / togli da preferiti
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



# API 15 RETURN INGREDIENTS BY DATE
# http://192.168.0.110:8000/api/getDailyMenu
@appWebApi.route("/api/getDailyIngredients", methods = ["GET"])

def getDailyMenu():

    idUtente = request.args.get("id_utente")
    data = request.args.get("data")

    query = """select nome_ingrediente, SUM(quantita_ingrediente) as quantita_ingrediente from menu_settimanale join ricettario
               on menu_settimanale.id_piatto = ricettario.id_piatto join ingredienti on ricettario.id_ingrediente = ingredienti.id
               WHERE id_utente = %s AND menu_settimanale.data = %s group by nome_ingrediente;"""
    
    result = db.getAllData(query, (idUtente, data))
  
    converted_result = [{k: float(v) if isinstance(v, Decimal) else v for k, v in row.items()} for row in result]

    return json.dumps(converted_result)



# API 16 RETURN INGREDIENTS BY WEEKLY MENU
@appWebApi.route("/api/getWeeklyIngredients", methods = ["GET"])
def getWeeklyIngredients():

    idUtente = request.args.get("id_utente")

    query = """select nome_ingrediente, SUM(quantita_ingrediente) as quantita_ingrediente from menu_settimanale join ricettario
               on menu_settimanale.id_piatto = ricettario.id_piatto join ingredienti on ricettario.id_ingrediente = ingredienti.id
               WHERE id_utente = %s  AND data BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 6 DAY) group by nome_ingrediente order by nome_ingrediente;"""
    
    result = db.getAllData(query, (idUtente,))
  
    converted_result = [{k: float(v) if isinstance(v, Decimal) else v for k, v in row.items()} for row in result]

    return json.dumps(converted_result)




# api 17 
@appWebApi.route("/api/getTipoPasto", methods=["GET"])
def getTipoPasto():
    query = "select * from tipo_pasto"
    result = db.getAllData(query)
    print(query, result)
    return json.dumps(result, default=vars)




# api 18
@appWebApi.route("/api/insertWeeklyMenu", methods = ["PUT"])
def insertWeeklyMenu():

    idUtente = request.args.get("id_utente")
    idPiatto = request.args.get("id_piatto")
    idPasto = request.args.get("id_pasto")
    stringData = request.args.get("data")
    stringData = stringData.replace(' GMT', '')

    #formato_data = '%a %b %d %H:%M:%S %Y'
    formato_data = '%a %b %d %H:%M:%S%z %Y'

    data = datetime.strptime(stringData, formato_data)
    print (data)

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



# api 19 
# http://192.168.0.110:8000/api/getWeeklyMenu
@appWebApi.route("/api/getWeeklyMenu", methods = ["GET"])

def getWeeklyMenu():

    idUtente = request.args.get("id_utente")
    
    query = """select `data`, nome_piatto, image_name, tipo_pasto.id, nome_tipo_pasto from menu_settimanale join piatti
               on id_piatto = piatti.id join tipo_pasto on id_pasto = tipo_pasto.id where id_utente = %s
               AND `data` BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 6 DAY) order by `data`, tipo_pasto.id;"""
    
    result = db.getAllData(query, (idUtente,))
    return json.dumps(result, default=convert_to_serializable)




# *********************************************************************************************************************************
# *********************************************************************************************************************************
# ***************************************************** ENDPOINT WEB **************************************************************
# *********************************************************************************************************************************
# *********************************************************************************************************************************

@appWebApi.route("/")
def index():
    if 'username' in session:
        print("sono dentro")
        return redirect('/connect?username=' + session['username'])
    else:
        print("sono fuori")
        query = "SELECT DISTINCT piatti.portata FROM piatti"
        result = db.getAllData(query)
        listaPortate = []
        for record in result:
            nomePortata = record["portata"]
            image_url = f"/static/img/{nomePortata.lower()}.jpg"
            portata = Portata(nomePortata, image_url)
            listaPortate.append(portata)
        
        piattiDaRestituire = 5
        query = """SELECT image_name
                   FROM piatti ORDER BY RAND() LIMIT %s"""
        result = db.getAllData(query, (piattiDaRestituire))
        listaImmagini = []
        for record in result:
            immagine = record["image_name"]
            listaImmagini.append(immagine)

        return render_template("index.html", listaPortate=listaPortate, listaImmagini=listaImmagini)




    
# web - login
@appWebApi.route('/login', methods= ['GET', 'POST'])
def webLogin():

    if request.method == 'POST':
        data = request.form
        username = data.get('username')
        password = data.get('password')
        remember = data.get('rememeber')

        query = "select * from utenti where username = %s"
        user = db.getSingleData(query, (username,))

        if user is None:
            print("utente non trovato nel database!")
            return render_template('login.html')
        
            #verifica la password hashata
            # if bcrypt.checkpw(password.encode('utf-8'), user['password'].encode('utf-8')):
        if password == user['password']:
            print("Utente OK!")
            session['logged_in'] = True
            session['username'] = user['username']
            return redirect('/connect')
        else:
            print("Password non corretta!")
            return render_template('login.html')
        
    return render_template('login.html')




@appWebApi.route('/connect')
def connected():
    if 'logged_in' in session and session['logged_in']:
        
        query = "SELECT DISTINCT piatti.portata FROM piatti"
        result = db.getAllData(query)
        listaPortate = []
        for record in result:
            nomePortata = record["portata"]
            image_url = f"/static/img/{nomePortata.lower()}.jpg"
            portata = Portata(nomePortata, image_url)
            listaPortate.append(portata)
        
        piattiDaRestituire = 5
        query = """SELECT image_name
                   FROM piatti ORDER BY RAND() LIMIT %s"""
        result = db.getAllData(query, (piattiDaRestituire))
        listaImmagini = []
        for record in result:
            immagine = record["image_name"]
            listaImmagini.append(immagine)

        return render_template("connect.html", listaPortate=listaPortate, listaImmagini=listaImmagini)

    else:
        return redirect('/login')




@appWebApi.route('/registrazione', methods =['GET', 'POST'])
def webRegister():
    if request.method == 'POST':
        data = request.form
        nome = data.get('nome')
        cognome = data.get('cognome')
        data_nascita = data.get('data_nascita')
        email = data.get('email')
        username = data.get('username')
        password = data.get('password')

        user = db.getSingleData("SELECT * FROM utenti WHERE username = %s and email = %s", (username, email))
        if user:
            return 'Username già utilizzato. Scegli un altro username! '
        
            # hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

        query = "INSERT INTO utenti (nome, cognome, data_nascita, email, username, password) values (%s, %s, %s, %s,  %s, %s)"
            # db.insert(query, (nome, cognome, data_nascita, email, username, hashed_password))
        db.insert(query, (nome, cognome, data_nascita, email, username, password))
        return redirect('/login')
    
    return render_template('register.html')




@appWebApi.route('/logout')
def logout():
    # Rimuovi l'utente dalla sessione
    session.pop('logged_in', None)
    session.pop('username', None)
    return redirect('/')





@appWebApi.route("/web/ricercaPerPortata/<portata>")
def webGetRecipesfromPortata(portata):
    if 'logged_in' in session and session['logged_in']:
        query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE portata = %s"
        result = db.getAllData(query,(portata,) )
        return render_template('lista_piattiCon.html', piatti = result)
    else: 
        query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE portata = %s"
        result = db.getAllData(query,(portata,) )
        return render_template('lista_piatti.html', piatti = result)
    


# web / RESTITUISCE UNA RICETTA COMPLETA (CON JOIN VARI) IN BASE ALL'ID
@appWebApi.route("/web/ricerca/ricercaFromId")
def webGetRicettaCompletaFromId():
    if 'logged_in' in session and session['logged_in']:
        loggedUser = session['username']
        idPiatto = request.args.get("id_piatto")

        query = """SELECT p.id, p.difficolta, p.tempo, p.nome_piatto, p.portata, p.provenienza, p.procedimento, p.image_name
                   FROM piatti p WHERE p.id = %s""" % (idPiatto)
    
        result = db.getSingleData(query)

        piatto = Piatto(**result)

        query = """SELECT i.nome_ingrediente, r.quantita_ingrediente
                   FROM piatti p JOIN ricettario r ON p.id = r.id_piatto
                   JOIN ingredienti i ON r.id_ingrediente = i.id WHERE p.id = %s;""" 
    
        result = db.getAllData(query, (idPiatto,))
    
        for row in result:
            ricettario = Ricettario(**row)
            piatto.ricettario.append(ricettario)
        
        #checkPreferito
        query = """select utenti.username, preferiti.id FROM preferiti join utenti on preferiti.id_utente = utenti.id
                   WHERE username = %s AND id_piatto = %s;"""
        result = db.getSingleData (query, (loggedUser, idPiatto,))
        if result is None:
            favoriteClass = "favorite-button"
        else:
            favoriteClass = "favorite-button favorite"

    
        return render_template("piatto_singoloCon.html", piatto=piatto, favoriteClass = favoriteClass)
    
    else:

        idPiatto = request.args.get("id_piatto")

        query = """SELECT p.id, p.difficolta, p.tempo, p.nome_piatto, p.portata, p.provenienza, p.procedimento, p.image_name
                   FROM piatti p WHERE p.id = %s"""
    
        result = db.getSingleData(query, (idPiatto,))

        piatto = Piatto(**result)

        query = """SELECT i.nome_ingrediente, r.quantita_ingrediente
                   FROM piatti p JOIN ricettario r ON p.id = r.id_piatto
                   JOIN ingredienti i ON r.id_ingrediente = i.id WHERE p.id = %s;""" 
    
        result = db.getAllData(query, (idPiatto,))
   
        for row in result:
            ricettario = Ricettario(**row)
            piatto.ricettario.append(ricettario)
        
        return render_template("piatto_singolo.html", piatto=piatto)







# listapiatti per nome o parte del nome
@appWebApi.route("/web/ricerca/ricercaPerNome")
def webGetRecipesfromName():
    if 'logged_in' in session and session['logged_in']:

        nomePiatto = request.args.get("nome_piatto")

        query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE nome_piatto LIKE %s"
        result = db.getAllData(query,('%' + nomePiatto + '%',) )

        return render_template("lista_piattiCon.html", piatti = result)
    else:
        nomePiatto = request.args.get("nome_piatto")

        query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE nome_piatto LIKE %s"
        result = db.getAllData(query,('%' + nomePiatto + '%',) )

        return render_template("lista_piatti.html", piatti = result)
    







@appWebApi.route("/web/updatePreferito", methods = ['GET'])
def webUpdatePreferiti():

    username = request.args.get("username")
    idPiatto = request.args.get("piattoId")

    query = """select utenti.id from utenti where username = %s"""
    result = db.getSingleData(query, (username))
    idUtente = result["id"]
    print(idUtente)

    query = """select utenti.id, utenti.username, preferiti.id FROM preferiti join utenti on preferiti.id_utente = utenti.id
               WHERE username = %s AND id_piatto = %s;"""
    result = db.getSingleData (query, (username, idPiatto,))

    if result is None:
        query = "INSERT into preferiti (id_utente, id_piatto) VALUES (%s, %s)"
        db.insert(query,(idUtente, idPiatto))
        #return json.dumps([])
    
    else:
        query = "delete from preferiti where id_utente = %s and id_piatto = %s;"
        db.delete(query, (idUtente, idPiatto))
        #return json.dumps([])

    return redirect("/web/ricerca/ricercaFromId?id_piatto=" + idPiatto)
    






@appWebApi.route("/web/favorites")
def webGetPreferiti():
        
        username = session['username']
        query = """select utenti.id from utenti where username = %s"""
        result = db.getSingleData(query, (username))
        idUtente = result["id"]
          
        query = """select p.id, nome_piatto, difficolta, tempo, portata, provenienza,image_name
               from piatti p JOIN preferiti pref ON p.id = pref.id_piatto WHERE pref.id_utente = %s"""
        result = db.getAllData(query,(idUtente,) )
        return render_template('lista_piattiCon.html', piatti = result)





@appWebApi.route("/web/menu")
def webGetWeeklyMenu():
    
        username = session['username']
        query = """select utenti.id from utenti where username = %s"""
        result = db.getSingleData(query, (username))
        idUtente = result["id"]

        query = """ SELECT DISTINCT g, giorno_settimana 
                    FROM (
                        SELECT 
                            DAYOFWEEK(`data`) as g, 
                            CASE 
                                WHEN DAYOFWEEK(`data`) = 1 THEN 'Domenica'
                                WHEN DAYOFWEEK(`data`) = 2 THEN 'Lunedì'
                                WHEN DAYOFWEEK(`data`) = 3 THEN 'Martedì'
                                WHEN DAYOFWEEK(`data`) = 4 THEN 'Mercoledì'
                                WHEN DAYOFWEEK(`data`) = 5 THEN 'Giovedì'
                                WHEN DAYOFWEEK(`data`) = 6 THEN 'Venerdì'
                                WHEN DAYOFWEEK(`data`) = 7 THEN 'Sabato'
                            END AS giorno_settimana 
                        FROM 
                            menu_settimanale 
                        WHERE 
                            id_utente = %s
                            AND `data` >= CURDATE()
                    ) AS giorni_unici
                    ORDER BY 
                        CASE 
                            WHEN g - DAYOFWEEK(CURDATE()) < 0 THEN 7 + g - DAYOFWEEK(CURDATE())
                            ELSE g - DAYOFWEEK(CURDATE())
                        END;"""
        
        giorni_settimana = db.getAllData(query,(idUtente))     
          
        query = """ select piatti.id, CASE DAYOFWEEK(`data`)
                    WHEN 1 THEN 'Domenica'
                    WHEN 2 THEN 'Lunedì'
                    WHEN 3 THEN 'Martedì'
                    WHEN 4 THEN 'Mercoledì'
                    WHEN 5 THEN 'Giovedì'
                    WHEN 6 THEN 'Venerdì'
                    WHEN 7 THEN 'Sabato'
                    END AS giornosettimana,
                    nome_piatto, difficolta, tempo, portata, provenienza, image_name, nome_tipo_pasto from menu_settimanale join piatti
                    on id_piatto = piatti.id join tipo_pasto on id_pasto = tipo_pasto.id where id_utente = %s
                    AND `data` BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 6 DAY);"""
        
        result = db.getAllData(query,(idUtente,))
        
    

        return render_template('menu_settimanale.html', giorni_settimana = giorni_settimana, piatti = result)











if __name__ == "__main__":
    try:
        db = Database()
        appWebApi.run(host='0.0.0.0', port=8000)
    except KeyboardInterrupt:
        db.close()



