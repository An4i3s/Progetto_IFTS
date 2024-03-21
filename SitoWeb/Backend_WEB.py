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
    'database': 'bvl9qu0y8urzicrcjonj' #COOKIDEA #
}

# Connessione al database
db = pymysql.connect(**config)
cursor = db.cursor(pymysql.cursors.DictCursor)




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





if __name__ == "__main__":
    appWebApi.run(host='0.0.0.0', port=8000, debug=True)



