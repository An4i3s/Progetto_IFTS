from flask import Flask, request, render_template, json
import pymysql
from models import *


appWebApi = Flask(__name__)



@appWebApi.route("/main")
def principale():
    return "ok funziona"

config = {
    'host': 'bvl9qu0y8urzicrcjonj-mysql.services.clever-cloud.com',
    'user': 'udihp2ytyzgp09os',
    'password': 'Vy2duKaXFXncXE5gSwEh',
    'database': 'bvl9qu0y8urzicrcjonj' #COOKIDEA #
}

# Connessione al database
db = pymysql.connect(**config)
cursor = db.cursor(pymysql.cursors.DictCursor)


# ENDPOINT WEB

@appWebApi.route("/")
def homepage():
    return render_template("index.html")



# WEB   (TUTTI I PIATTI)
# http://192.168.1.94:8000/piatti
@appWebApi.route("/piatti")
def webGetAllRecipes():

    query = "select * from piatti"
    cursor.execute(query)
    result = cursor.fetchall()

    return render_template("piatti_esempio.html", piatti = result)



# WEB / RICERCA PER NOME PIATTO  (anche solo una parte del nome)
# http://192.168.0.110:8000/ricercaPerNome/Funghi
@appWebApi.route("/ricercaPerNome/<nome>")
def webGetRecipesfromName(nome):

    query = "select * from piatti WHERE nome_piatto LIKE %s"
    cursor.execute(query, ('%' + nome+ '%',))
    result = cursor.fetchall()

    return render_template("piatti_esempio.html", piatti = result)



# web / RESTITUISCE UNA RICETTA COMPLETA (CON JOIN VARI) IN BASE ALL'ID
@appWebApi.route("/web/ricerca/ricettaFromId")
def webGetRicettaCompletaFromId():
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
        

    return render_template("piatti_esempio.html", piatto=piatto)







if __name__ == "__main__":
    appWebApi.run(host='0.0.0.0', port=8000, debug=True)



