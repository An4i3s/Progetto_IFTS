from flask import Flask, request, render_template, json
import pymysql
from models import *
from Database import *


appWebApi = Flask(__name__)



db = None



@appWebApi.route("/")
def homepage():
    return render_template("index.html")



# WEB   (TUTTI I PIATTI)
# http://192.168.1.94:8000/piatti
@appWebApi.route("/piatti")
def webGetAllRecipes():

    query = "select * from piatti"
    result = db.fetchAll(query)

    return render_template("piatti_esempio.html", piatti = result)



# WEB / RICERCA PER NOME PIATTO  (anche solo una parte del nome)
# http://192.168.0.110:8000/ricercaPerNome/Funghi
@appWebApi.route("/web/ricercaPerNome/<nome>")
def webGetRecipesfromName(nome):

    query = "select id, nome_piatto, difficolta, tempo, portata, provenienza, image_name from piatti WHERE nome_piatto LIKE %s"
    result = db.fetchAll(query,('%' + nome+ '%',) )

    return render_template("piatti_esempio.html", piatti = result)


# # web / 5 piatti random
# # restituisce 5 piatti (id, nome_piatto, image_name) a random dal database
# # http://192.168.0.110:8000/web/randomPiattoIdNomeImg
# @appWebApi.route("/web/randomPiattoIdNomeImg")
# def webGetPiattiImmagini():
#     piattiDaRestituire = 5
#     query = """SELECT id, nome_piatto, image_name
#                FROM piatti ORDER BY RAND() LIMIT %s"""
#     cursor.execute(query, (piattiDaRestituire))
#     result = cursor.fetchall()
#     return jsonify(result)



# web / RESTITUISCE UNA RICETTA COMPLETA (CON JOIN VARI) IN BASE ALL'ID
@appWebApi.route("/web/ricerca/ricettaFromId")
def webGetRicettaCompletaFromId():
    idPiatto = request.args.get("id_piatto")

    query = """SELECT p.id, p.difficolta, p.tempo, p.nome_piatto, p.provenienza, p.procedimento, p.image_name
               FROM piatti p WHERE p.id = %s"""
    
    result = db.fetchOne(query, (idPiatto,))

    piatto = Piatto(**result)

    query = """SELECT i.nome_ingrediente, r.quantita_ingrediente
               FROM piatti p JOIN ricettario r ON p.id = r.id_piatto
               JOIN ingredienti i ON r.id_ingrediente = i.id WHERE p.id = %s;""" 
    
    result = db.fetchAll(query, (idPiatto,))
   
    
    for row in result:
        ricettario = Ricettario(**row)
        piatto.ricettario.append(ricettario)
        

    return render_template("piatti_esempio.html", piatto=piatto)








if __name__ == "__main__":
    try:
        db = Database()
        appWebApi.run(host='0.0.0.0', port=8000, debug=True)
    except KeyboardInterrupt:
        db.close()



