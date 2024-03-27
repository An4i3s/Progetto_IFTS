class Portata:
    nome = None
    image_url = None

    def __init__(self, nome, image_url):
        self.nome = nome
        self.image_url = image_url


class Piatto:
    id = None
    difficolta = None
    tempo = None
    nome_piatto = None
    portata = None
    provenienza = None
    procedimento = None
    image_url = None
    ricettario = []
    
    def __init__(self, id, difficolta, tempo, nome_piatto, portata, provenienza, procedimento, image_name):
        self.id = id
        self.difficolta = difficolta
        self.tempo = tempo
        self.nome_piatto = nome_piatto
        self.portata = portata
        self.provenienza = provenienza
        self.procedimento = procedimento
        self.image_name = image_name
        self.ricettario = []
    



class Ricettario:
    nome_ingrediente = None
    quantita_ingrediente = None


    def __init__(self, nome_ingrediente, quantita_ingrediente):
        self.nome_ingrediente = nome_ingrediente
        self.quantita_ingrediente = quantita_ingrediente


