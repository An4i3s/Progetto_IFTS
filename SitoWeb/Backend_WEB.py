from flask import Flask, request, render_template, json, redirect, url_for, session
from flask_wtf import CSRFProtect
import pymysql
from models import *
from Database import *
import bcrypt


appWebApi = Flask(__name__)
appWebApi.secret_key= '123456'
csrf = CSRFProtect(appWebApi)
db = None
 
@appWebApi.route("/")
def index():
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
def register():
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
        
        hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

        query = "INSERT INTO utenti (nome, cognome, data_nascita, email, username, password) values (%s, %s, %s, %s,  %s, %s)"
        db.insert(query, (nome, cognome, data_nascita, email, username, hashed_password))
        return redirect('/login')
    
    return render_template('register.html')

@appWebApi.route('/login', methods= ['GET', 'POST'])
def login():
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
        if bcrypt.checkpw(password.encode('utf-8'), user['password'].encode('utf-8')):
            print("Utente OK!")
            session['logged_in'] = True
            session['username'] = user['username']
            return redirect('/connect')
        else:
            print("Password non corretta!")
            return render_template('login.html')
        
    return render_template('login.html')

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

        return render_template("piatto_singoloCon.html", piatto=piatto)
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

# WEB   (TUTTI I PIATTI)
# http://192.168.1.94:8000/piatti
@appWebApi.route("/piatti")
def webGetAllRecipes():

    query = "select * from piatti"
    result = db.getAllData(query)

    return render_template("piatto_singolo.html", piatti = result)


            # # WEB / RICERCA PER NOME PIATTO  (anche solo una parte del nome)
            # # http://192.168.0.110:8000/ricercaPerNome/Funghi
            # @appWebApi.route("/web/ricerca/ricercaPerNome")
            # def webGetRecipesfromName():
            #     nomePiatto = request.args.get("nome_piatto")


            #     query = """SELECT p.id, p.difficolta, p.tempo, p.nome_piatto, p.portata, p.provenienza, p.procedimento, p.image_name
            #             FROM piatti p WHERE p.nome_piatto LIKE %s"""
                
            #     result = db.getSingleData(query,('%' + nomePiatto + '%',) )

            #     piatto = Piatto(**result)

            #     currentIdPiatto = piatto.id

            #     query = """SELECT i.nome_ingrediente, r.quantita_ingrediente
            #             FROM piatti p JOIN ricettario r ON p.id = r.id_piatto
            #             JOIN ingredienti i ON r.id_ingrediente = i.id WHERE p.id = %s;""" 
                
            #     result = db.getAllData(query, (currentIdPiatto,))
            
                
            #     for row in result:
            #         ricettario = Ricettario(**row)
            #         piatto.ricettario.append(ricettario)
                    

            #     return render_template("piatto_singolo.html", piatto=piatto)



if __name__ == "__main__":
    try:
        db = Database()
        appWebApi.run(host='0.0.0.0', port=8000, debug=True)
    except KeyboardInterrupt:
        db.close()