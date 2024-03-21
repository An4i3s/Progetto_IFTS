from flask import Flask, request, render_template, json
import pymysql


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
cursor = db.cursor(pymysql)



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
@appWebApi.route("/api/portate/")
def getPortate():
    query = "SELECT DISTINCT piatti.portata FROM piatti"
    cursor.execute(query)
    result = cursor.fetchall()

    return json.dumps(result)






# api /restituisce tutti i piatti facendo join con ingredienti e ricettario
# http://192.168.1.20:8000/api/piatti_ricette

class Piatto:
   
    id = None
    difficolta = None
    tempo = None
    nome_piatto = None
    provenienza = None
    portata = None
    procedimento = None
    image_url = None
    ricettario = None
    
    def __init__(self, id, difficolta, tempo, nome_piatto, provenienza, portata, procedimento, image_url, ricettario):
        self.id = id
        self.difficolta = difficolta
        self.tempo = tempo
        self.nome_piatto = nome_piatto
        self.provenienza = provenienza
        self.portata =portata
        self.procedimento = procedimento
        self.image_url = image_url
        self.ricettario = ricettario

class Ricettario:
    id_piatto = None
    quantita_ingrediente = None
    nome_ingrediente = None
    categoria_ingrediente = None

    def __init__(self, q_i, n_i, c_i):
        self.quantita_ingrediente = q_i
        self.nome_ingrediente = n_i
        self.categoria_ingrediente = c_i



@appWebApi.route("/api/piatti_ricette")
def getPiattiRicette():
    

    filtroNome = request.args.get("nome_piatto")    
    filtroIngrediente = request.args.getlist("listaIngredienti")
    filtroDifficolta = request.args.getlist("listaDifficolta")
    filtroTempoMinimo = request.args.get("tempoMinimo", type=int)
    filtroTempoMassimo = request.args.get("tempoMassimo", type=int)
    filtroProvenienza = request.args.getlist("listaProvenienza")
    filtroPortata = request.args.getlist("listaPortata")


    query = """
        SELECT p.id, p.difficolta, p.tempo, p.nome_piatto, p.provenienza, p.portata, p.procedimento, p.image_url,
               r.quantita_ingrediente, i.nome_ingrediente, i.categoria_ingrediente
        FROM piatti p
        JOIN ricettario r ON p.id = r.id_piatto
        JOIN ingredienti i ON r.id_ingrediente = i.id
    """
    cursor.execute(query)

    results = cursor.fetchall()
    #print(type(results), results)


    listaPiatti = []
    

    for row in results:
        piatto_id = row["id"]
        inserito = False
        for elemento in listaPiatti:
            if elemento.id ==piatto_id:
                inserito = True
        if not inserito:
            id = row["id"]
            difficolta = row["difficolta"]
            tempo = row['tempo']
            nome_piatto = row['nome_piatto']
            provenienza = row['provenienza']
            portata = row['portata']
            procedimento= row['procedimento']
            image_url = row['image_url']
            ricettario = []

            listaPiatti.append(Piatto(id, difficolta, tempo, nome_piatto, provenienza, portata, procedimento, image_url, ricettario))

    for row in results:   
        for piatto in listaPiatti:
            if piatto.id == row["id"]:
                piatto.ricettario.append(Ricettario(row["quantita_ingrediente"], row["nome_ingrediente"], row["categoria_ingrediente"]))
            

    # filtroNome = ""                 
    # filtroIngrediente =["olio"]         #lista str
    # filtroDifficolta = [2, 3, 1]           #lista int
    # filtroTempo = None                #lista int
    # filtroTempoMinimo = None          #lista int
    # filtroTempoMassimo = None         #lista int
    # filtroProvenienza = None            #lista str
    # filtroPortata = None                #lista str
        

    listaFiltrata  = [piatto for piatto in listaPiatti
                      if (filtroNome is None or filtroNome.lower() in piatto.nome_piatto.lower())
                      and
                      all(ingrediente_filtrato(piatto, ingrediente) for ingrediente in filtroIngrediente)
                      and
                      (not filtroDifficolta or piatto.difficolta in filtroDifficolta)
                      and
                      ((filtroTempoMinimo is None or filtroTempoMassimo is None) or
                      (filtroTempoMinimo <= piatto.tempo <= filtroTempoMassimo))
                      and
                      (not filtroProvenienza or any(p.lower() in piatto.provenienza.lower() for p in filtroProvenienza))
                      and
                      (not filtroPortata or any(p.lower() in piatto.portata.lower() for p in filtroPortata))
                      ]
    
    for piatto in listaFiltrata:
        print(piatto.nome_piatto)

    return json.dumps(listaFiltrata, default=vars)

def ingrediente_filtrato(piatto, ingrediente):
    return any(ingrediente.strip().lower() in i.nome_ingrediente.strip().lower() for i in piatto.ricettario)

















if __name__ == "__main__":
    appWebApi.run(host='0.0.0.0', port=8000, debug=True)



