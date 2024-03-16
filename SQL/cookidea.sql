
DROP TABLE IF EXISTS `ricettario`;
DROP TABLE IF EXISTS `ingredienti`;
DROP TABLE IF EXISTS `preferiti`;
DROP TABLE IF EXISTS `menu_settimanale`;
DROP TABLE IF EXISTS `piatti`;
DROP TABLE IF EXISTS `utenti`;

DROP TABLE IF EXISTS `tipo_pasto`;



CREATE TABLE `ingredienti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome_ingrediente` varchar(45) NOT NULL,
  `categoria_ingrediente` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO ingredienti (nome_ingrediente, categoria_ingrediente) VALUES 
('Pomodori', 'Verdura'),
('Cipolle', 'Verdura'),
('Aglio', 'Verdura'),
('Basilico', 'Erbe Aromatiche'),
('Origano', 'Erbe Aromatiche'),
('Olio Extra Vergine di Oliva', 'Condimento'),
('Sale', 'Condimento'),
('Pepe', 'Condimento'),
('Farina', 'Farine'),
('Latte', 'Latticini'),
('Pepe nero', 'Spezie'),
('Pasta per pizza', 'Farine e cereali'),
('Mozzarella', 'Latticini'),
('Pinoli', 'Frutta secca'),
('Parmigiano', 'Formaggi'),
('Riso', 'Cereali'),
('Brodo vegetale', 'Brodi'),
('Zafferano', 'Spezie'),
('Melanzane', 'Frutta e verdura'),
('Formaggio', 'Formaggi'),
('Vongole', 'Frutti di mare'),
('Mascarpone', 'Latticini'),
('Zucchero', 'Dolcificanti'),
('Savoiardi', 'Dolci e dessert'),
('Caffè', 'Bevande');



CREATE TABLE `piatti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome_piatto` varchar(45) NOT NULL,
  `difficolta` int NOT NULL,
  `tempo` int NOT NULL,
  `provenienza` varchar(45) NOT NULL,
  `portata` varchar(45) NOT NULL,
  `procedimento` text NOT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `piatti` (`nome_piatto`, `difficolta`, `tempo`, `provenienza`, `portata`, `procedimento`) VALUES 
('Pasta alla Carbonara', 2, 25, 'Lazio', 'Primo', 'Cuocere la pasta, rosolare pancetta e aglio, aggiungere la pasta cotta e mescolare con uova e pecorino.'),
('Risotto ai Funghi Porcini', 2, 30, 'Piemonte', 'Primo', 'Soffriggere cipolla e funghi, aggiungere il riso e sfumare con il vino, cuocere aggiungendo brodo vegetale.'),
('Ossobuco alla Milanese', 3, 120, 'Lombardia', 'Secondo', 'Rosolare l`ossobuco, aggiungere vino bianco e brodo, cuocere con pomodoro, sedano, carota e cipolla.'),
('Cacio e Pepe', 1, 20, 'Lazio', 'Primo', 'Cuocere la pasta, mantecare con pecorino e pepe nero.'),
('Pizza', 2, 45, 'Campania', 'Secondo', 'Stendere la pasta, condire con pomodoro, mozzarella e basilico, cuocere in forno.'),
('Pesto alla Genovese', 1, 15, 'Liguria', 'Condimento', 'Frullare basilico, pinoli, aglio, parmigiano e olio extravergine d`oliva.'),
('Risotto alla Milanese', 2, 30, 'Lombardia', 'Primo', 'Rosolare la cipolla, tostare il riso, sfumare con vino bianco, cuocere aggiungendo brodo e zafferano.'),
('Parmigiana di Melanzane', 2, 60, 'Sicilia', 'Secondo', 'Friggere le melanzane, alternare strati di melanzane, salsa di pomodoro e formaggio, cuocere al forno.'),
('Spaghetti alle Vongole', 2, 25, 'Campania', 'Primo', 'Rosolare aglio e peperoncino, aggiungere vongole e vino bianco, cuocere la pasta e mantecare con le vongole.'),
('Tiramisù', 3, 60, 'Veneto', 'Dolce', 'Preparare una crema con uova, zucchero e mascarpone, inzuppare i biscotti nel caffè, alternare strati di biscotti e crema, spolverare con cacao amaro in polvere.');



CREATE TABLE `ricettario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_piatto` int NOT NULL,
  `id_ingrediente` int NOT NULL,
  `quantita_ingrediente` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_ingrediente_ricettario_idx` (`id_ingrediente`),
  KEY `id_piatto_piatti_idx` (`id_piatto`),
  CONSTRAINT `id_ingrediente_ricettario` FOREIGN KEY (`id_ingrediente`) REFERENCES `ingredienti` (`id`),
  CONSTRAINT `id_piatto_ricettario` FOREIGN KEY (`id_piatto`) REFERENCES `piatti` (`id`)
);
-- Inserimento dei dati nel ricettario per il piatto 'Pasta alla Carbonara'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(1, 12, 200), -- Pasta per pizza
(1, 18, 100), -- Parmigiano
(1, 11, 1),   -- Pepe nero
(1, 10, 2),   -- Latte
(1, 17, 100); -- Aglio

-- Inserimento dei dati nel ricettario per il piatto 'Risotto ai Funghi Porcini'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(2, 13, 2),  -- Pomodoro
(2, 14, 200), -- Mozzarella
(2, 5, 5),   -- Basilico
(2, 19, 30), -- Olio extravergine d'oliva
(2, 7, 1),   -- Sale
(2, 8, 1);   -- Pepe

-- Inserimento dei dati nel ricettario per il piatto 'Ossobuco alla Milanese'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(3, 12, 1),  -- Pasta per pizza
(3, 13, 2),  -- Pomodoro
(3, 3, 1),   -- Aglio
(3, 5, 3),   -- Basilico
(3, 19, 30), -- Olio extravergine d'oliva
(3, 7, 1),   -- Sale
(3, 8, 1);   -- Pepe

-- Inserimento dei dati nel ricettario per il piatto 'Cacio e Pepe'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(4, 13, 2),  -- Pomodoro
(4, 18, 200), -- Mozzarella
(4, 5, 5),   -- Basilico
(4, 19, 30), -- Olio extravergine d'oliva
(4, 7, 1),   -- Sale
(4, 8, 1);   -- Pepe

-- Inserimento dei dati nel ricettario per il piatto 'Pizza'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(5, 13, 2),  -- Pomodoro
(5, 14, 200), -- Mozzarella
(5, 5, 5),   -- Basilico
(5, 19, 30), -- Olio extravergine d'oliva
(5, 7, 1),   -- Sale
(5, 8, 1);   -- Pepe

-- Inserimento dei dati nel ricettario per il piatto 'Pesto alla Genovese'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(6, 13, 2),  -- Pomodoro
(6, 14, 200), -- Mozzarella
(6, 5, 5),   -- Basilico
(6, 19, 30), -- Olio extravergine d'oliva
(6, 7, 1),   -- Sale
(6, 8, 1);   -- Pepe

-- Inserimento dei dati nel ricettario per il piatto 'Risotto alla Milanese'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(7, 13, 2),  -- Pomodoro
(7, 14, 200), -- Mozzarella
(7, 5, 5),   -- Basilico
(7, 19, 30), -- Olio extravergine d'oliva
(7, 7, 1),   -- Sale
(7, 8, 1);   -- Pepe

-- Inserimento dei dati nel ricettario per il piatto 'Parmigiana di Melanzane'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(8, 13, 2),  -- Pomodoro
(8, 14, 200), -- Mozzarella
(8, 5, 5),   -- Basilico
(8, 19, 30), -- Olio extravergine d'oliva
(8, 7, 1),   -- Sale
(8, 8, 1);   -- Pepe

-- Inserimento dei dati nel ricettario per il piatto 'Spaghetti alle Vongole'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(9, 13, 2),  -- Pomodoro
(9, 14, 200), -- Mozzarella
(9, 5, 5),   -- Basilico
(9, 19, 30), -- Olio extravergine d'oliva
(9, 7, 1),   -- Sale
(9, 8, 1);   -- Pepe

-- Inserimento dei dati nel ricettario per il piatto 'Tiramisù'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(10, 13, 2),  -- Pomodoro
(10, 14, 200), -- Mozzarella
(10, 5, 5),   -- Basilico
(10, 19, 30), -- Olio extravergine d'oliva
(10, 7, 1),   -- Sale
(10, 8, 1);   -- Pepe


CREATE TABLE `utenti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `data_nascita` date NOT NULL,
  `email` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
);
INSERT INTO `utenti` VALUES (1,'Mario','Rossi','1990-05-15','mario.rossi@example.com','mario123','password123'),(2,'Laura','Bianchi','1985-09-20','laura.bianchi@example.com','laura456','password456'),(3,'Giovanni','Verdi','1988-07-10','giovanni.verdi@example.com','giovanni789','password789');


CREATE TABLE `tipo_pasto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome_tipo_pasto` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `tipo_pasto` VALUES (1,'Primo'),(2,'Secondo'),(3,'Contorno'),(4,'Dolce');


CREATE TABLE `menu_settimanale` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_utente` int NOT NULL,
  `id_piatto` int NOT NULL,
  `id_pasto` int NOT NULL,
  `data` date NOT NULL,
  `numero_persone` int NOT NULL,
  `comprato` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_utente_menusettimanale_idx` (`id_utente`),
  KEY `id_piatto_menusettimanale_idx` (`id_piatto`),
  KEY `id_pasto_menusettimanale_idx` (`id_pasto`),
  CONSTRAINT `id_pasto_menusettimanale` FOREIGN KEY (`id_pasto`) REFERENCES `tipo_pasto` (`id`),
  CONSTRAINT `id_piatto_menusettimanale` FOREIGN KEY (`id_piatto`) REFERENCES `piatti` (`id`),
  CONSTRAINT `id_utente_menusettimanale` FOREIGN KEY (`id_utente`) REFERENCES `utenti` (`id`)
);



CREATE TABLE `preferiti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_utente` int NOT NULL,
  `id_piatto` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_utente_preferiti_idx` (`id_utente`),
  KEY `id_piatto_preferiti_idx` (`id_piatto`),
  CONSTRAINT `id_piatto_preferiti` FOREIGN KEY (`id_piatto`) REFERENCES `piatti` (`id`),
  CONSTRAINT `id_utente_preferiti` FOREIGN KEY (`id_utente`) REFERENCES `utenti` (`id`)
);


ALTER TABLE `bvl9qu0y8urzicrcjonj`.`piatti` 
ADD COLUMN `image_url` TINYTEXT NULL AFTER `procedimento`;

UPDATE `bvl9qu0y8urzicrcjonj`.`piatti` SET `image_url` = 'https://ricette.giallozafferano.it/images/speciali/897/hd1200x800.jpg' WHERE (`id` = '10');
UPDATE `bvl9qu0y8urzicrcjonj`.`piatti` SET `image_url` = 'https://ricette.giallozafferano.it/images/speciali/897/hd1200x800.jpg' WHERE (`id` = '8');
UPDATE `bvl9qu0yricettarioquantita_ingrediente8urzicrcjonj`.`piatti` SET `image_url` = 'https://ricette.giallozafferano.it/images/speciali/897/hd1200x800.jpg' WHERE (`id` = '9');
UPDATE `bvl9qu0y8urzicrcjonj`.`piatti` SET `image_url` = 'https://ricette.giallozafferano.it/images/speciali/897/hd1200x800.jpg' WHERE (`id` = '6');
UPDATE `bvl9qu0y8urzicrcjonj`.`piatti` SET `image_url` = 'https://i.pinimg.com/originals/0f/f2/9e/0ff29e3eb6a8276d7596e9de990a1751.jpg' WHERE (`id` = '1');
UPDATE `bvl9qu0y8urzicrcjonj`.`piatti` SET `image_url` = 'https://staticcookist.akamaized.net/wp-content/uploads/sites/21/2021/10/Risotto-ai-funghi-1200x675.jpg' WHERE (`id` = '2');
UPDATE `bvl9qu0y8urzicrcjonj`.`piatti` SET `image_url` = 'https://media-assets.lacucinaitaliana.it/photos/635169cdbc2c8a8e10d1f342/16:9/w_2560%2Cc_limit/GettyImages-482478191.jpg' WHERE (`id` = '3');
UPDATE `bvl9qu0y8urzicrcjonj`.`piatti` SET `image_url` = 'https://staticcookist.akamaized.net/wp-content/uploads/sites/21/2022/07/Cacio-e-pepe-0D6A4774.jpg' WHERE (`id` = '4');
UPDATE `bvl9qu0y8urzicrcjonj`.`piatti` SET `image_url` = 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/640px-Eq_it-na_pizza-margherita_sep2005_sml.jpg' WHERE (`id` = '5');
UPDATE `bvl9qu0y8urzicrcjonj`.`piatti` SET `image_url` = 'https://ricette.giallozafferano.it/images/speciali/897/hd1200x800.jpg' WHERE (`id` = '7');



select * from menu_settimanale;
select * from preferiti;
select * from utenti;
select * from tipo_pasto;
select * from piatti;
select * from ingredienti;
select * from ricettario;


SELECT p.id, p.nome_piatto, p.difficolta, p.tempo, p.provenienza, p.portata, p.procedimento, p.image_url, 
       r.quantita_ingrediente, i.nome_ingrediente
FROM piatti p
JOIN ricettario r ON p.id = r.id_piatto
JOIN ingredienti i ON r.id_ingrediente = i.id;
