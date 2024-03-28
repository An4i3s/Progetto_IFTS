DROP SCHEMA IF EXISTS COOKIDEA;

CREATE SCHEMA COOKIDEA;
USE COOKIDEA;

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

CREATE TABLE `piatti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome_piatto` varchar(45) NOT NULL,
  `difficolta` int NOT NULL,
  `tempo` int NOT NULL,
  `provenienza` varchar(45) NOT NULL,
  `portata` varchar(45) NOT NULL,
  `procedimento` text NOT NULL,
  `image_name` TINYTEXT,
  PRIMARY KEY (`id`)
);

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

CREATE TABLE `utenti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `data_nascita` date NOT NULL,
  `email` varchar(45) NOT NULL UNIQUE,
  `username` varchar(45) NOT NULL UNIQUE,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);

CREATE TABLE `tipo_pasto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome_tipo_pasto` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

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


