INSERT INTO ingredienti (nome_ingrediente, categoria_ingrediente) VALUES
('Maionese','Condimento'),
('Capperi Dissalati','Verdura'),
('Filetti di Acciughe Sott Olio','Pesce'),
('Tonno Sott Olio','Pesce'),
('Magatello di Vitello','Carne'),
('Pane in Cassetta','Cereali'),
('Latte','Liquido'),
('Vino Rosso','Liquido'),
('Trita di Manzo','Carne'),
('Sedano','Verdura'),
('Cipolla','Verdura'),
('Besciamella','Condimento'),
('Sfoglia Lasagna','Cereali'),
('Salsa di Soia','Condimento'),
('Olio di Semi','Condimento'),
('Zucchine','Verdura'),
('Carote','Vertdura'),
('Gnocchi di Riso','Cereali'),
('Cacao Amaro','Condimento'),
('Caffè','Liquido'),
('Zucchero','Condimento'),
('Mascarpone','Formaggio'),
('Savoiardi','Cereali'),
('Vongole','Pesce'),
('Salsa di Pomodoro','Verdura'),
('Provola','Formaggio'),
('Prosciutto Cotto','Salume'),
('Melanzane','Verdura'),
('Zafferano','Spezia'),
('Aglio','Verdura'),
('Pinoli','Semi'),
('Basilico','Erbe Aromatiche'),
('Mozzarella','Formaggio'),
('Pomodori Pelati','Verdura'),
('Lievito di Birra Secco','Lievito'),
('Acqua','Liquidi'),
('Pan Grattato','Cereali'),
('Farina','Cereali'),
('Lombata di Vitello','Carne'),
('Prezzemolo','Erbe Aromatiche'),
('Burro','Condimento'),
('Brodo Vegetale','Liquido'),
('Vino Bianco','Liquido'),
('Scalogno','Verdura'),
('Funghi Porcini','Verdura'),
('Riso','Cereali'),
('Pepe','Spezia'),
('Sale','Condimento'),
('Olio','Condimento'),
('Olio Evo','Condimento'),
('Pecorino','Formaggio'),
('Grana','Formaggio'),
('Uova','Uova'),
('Guanciale','Salume'),
('Spaghetti','Cereali');


INSERT INTO `piatti` (`nome_piatto`, `difficolta`, `tempo`, `provenienza`, `portata`, `procedimento`,`image_name`) VALUES 
('Pasta alla Carbonara', 2, 20, 'Lazio', 'Primo', 'Mettiamo l’acqua per la pasta sul fuoco e nel frattempo preparate il condimento. Saltiamo in padella il guanciale insieme a un filo d’olio per un paio di minuti, quindi spegniamo il fuoco e mettiamo da parte.\nIn una terrina capiente sgusciamo l’uovo intero e aggiungiamo anche il tuorlo, i formaggi grattugiati e abbondante pepe. Lavoriamo tutti gli ingredienti con una frusta fino a ottenere un composto cremoso e ben amalgamato.\nCuociamo gli spaghetti in acqua salata e scoiamoli al dente direttamente nella ciotola delle uova. Aggiungiamo i cubetti di guanciale e mantechiamo velocemente la pasta.\n', 'carbonara.jpg'),
('Risotto ai Funghi Porcini', 3, 30, 'Piemonte', 'Primo', 'Pulite i funghi porcini. Con un coltellino bisogna raschiare il gambo e asportare con cura le parti più dure e legnose. Quindi, aiutandovi con uno spazzolino o un pennello dalle setole dure,  eliminate tutta la terra.\nTritate lo scalogno molto sottile e mettetelo a soffriggere in poco olio. Quando sarà appassito e diventato quasi trasparente, aggiungete il riso e fatelo tostare per bene, per qualche minuto.\nOra, tenendo la fiamma alta, sfumate con il vino bianco, mescolando fino a quando l’alcol non sarà evaporato del tutto.\nCominciate a bagnare il riso con il brodo. Poi aggiungete i gambi dei funghi, che avrete già tagliato a dadini, e continuate ancora a bagnare il riso, senza esagerare.\nMentre il riso comincia a cuocere tagliate a fettine i cappelli dei funghi porcini, che aggiungerete quando il riso sarà quasi cotto.\nUna volta terminata la cottura del riso, spegnete il fuoco e aggiungete il burro, il parmigiano grattugiato e il prezzemolo tritato per mantecare il risotto.\nFate riposare per un paio di minuti il risotto, che deve essere ancora all’onda. Impiattate e servite con una spolverata di pepe nero.\n', 'risottofunghi.jpg'),
('Cotoletta alla Milanese', 1, 25, 'Lombardia', 'Secondo', 'Puliamo le fette di carne rimuovendo il grasso in eccesso e liberando in parte l’osso con un coltello.\nBattiamo la carne con l’apposito accessorio per qualche attimo, oppure usiamo il fondo di un bicchiere resistente.\nInfariniamo le fette di vitello su entrambi i lati facendo aderire bene la farina.\nOra procediamo con la panatura. Teniamo a portata di mano un piatto fondo con le uova sbattute e un piatto piano con il pangrattato.\nImmergiamo la carne infarinata nelle uova, poi passiamola nel pane in modo da panarla completamente.\nIn una padella sciogliamo il burro, scaldiamolo e poi friggiamo a fuoco medio-alto le cotolette una alla volta, girandole a metà cottura. A seconda delle dimensioni, 2-3 minuti per lato dovrebbero bastare.\nPreleviamo ciascuna cotoletta alla milanese dalla padella con delle pinze, poggiamole un momento sulla carta assorbente, completiamo con i fiocchi di sale.\n', 'cotolettamilanese.jpg'),
('Cacio e Pepe', 2, 15, 'Lazio', 'Primo', 'Per preparare gli spaghetti cacio e pepe per prima cosa mettete sul fuoco una pentola con l’acqua per cuocere la pasta.\nNel frattempo grattugiate il Pecorino romano e trasferitelo quasi tutto in una ciotola, tenendone un po’ da parte per l’impiattamento.\nQuando l’acqua sarà arrivata a bollore, salate moderatamente e tuffate gli spaghetti. Intanto pestate i grani di pepe con un batticarne (oppure usate un macinino lasciandolo piuttosto lento in modo da avere anche pezzetti più grandi di pepe).\nVersate il pepe in un’ampia padella antiaderente e tostatelo a fuoco dolce, mescolando. Sfumate con un paio di mestoli dell’acqua di cottura della pasta. Continuate a mescolare a fuoco dolce.\nScolate gli spaghetti 2-3 minuti prima rispetto al tempo indicato sulla confezione e trasferiteli nella padella, avendo cura di conservare l’acqua di cottura. Terminate la cottura degli spaghetti in padella, risottandoli, aggiungendo quindi acqua calda al bisogno e rimestando continuamente con le pinze. Aspettate che il fondo della padella sia quasi asciutto prima di aggiungere altra acqua.\nPotete preparare la crema di Pecorino: versate un mestolo di acqua di cottura calda nella ciotola con il Pecorino grattugiato, poi mescolate energicamente con una frusta a mano. La consistenza che dovrete ottenere è più pastosa che cremosa, non preoccupatevi se sarà molto asciutta. Qualora una volta pronta la crema la pasta non dovesse essere pronta per la mantecatura, ponete brevemente la ciotola sulla pentola con l’acqua calda continuando a mescolare con la frusta, così da avere la crema a una temperatura simile a quella della pasta.\nQuando gli spaghetti saranno giunti a cottura, spegnete il fuoco e unite la crema di Pecorino, rimestando di continuo con le pinze. Per la cremosità ideale regolatevi aggiungendo più Pecorino oppure poca acqua di cottura al bisogno. Servite i vostri spaghetti cacio e pepe spolverizzando con il restante Pecorino grattugiato e altro pepe macinato al momento a piacere.\n', 'caciopepe.jpg'),
('Pizza', 2, 80, 'Campania', 'Primo', 'Per realizzare la pizza margherita iniziate dal prefermento: in una ciotola capiente mescolate la farina con il lievito, poi versate l’acqua.\nAmalgamate gli ingredienti con movimenti delicati. Quando avrete ottenuto un composto grossolano e non vedrete più tracce di farina sul fondo della ciotola, copritela con pellicola e bucherellate con una forchetta, così che possa respirare. Riponete subito in frigorifero e lasciate maturare almeno per 24 ore, o comunque fino ad un massimo di 48 ore.\nTrascorso il tempo di maturazione tagliate il prefermento a pezzi e trasferiteli nuovamente nella ciotola, poi copriteli con l’acqua.\nUnite la farina e il lievito e iniziate a lavorare l’impasto nella ciotola.\nQuando l’acqua sarà stata completamente assorbita aggiungete il sale e continuate a lavorare con movimenti dall’alto verso il basso. Infine versate l’olio e impastate ancora per incorporarlo in modo uniforme. Se preferite potete realizzare questi passaggi in una planetaria, dotata di gancio.\nQuando avrete ottenuto una massa compatta trasferitevi sul piano di lavoro leggermente infarinato e lavorate l’impasto energicamente fino ad ottenere una consistenza liscia e omogenea. Riponete nuovamente l’impasto nella ciotola, coprite con un canovaccio umido e lasciate riposare per 15 minuti a temperatura ambiente.\nTrascorso questo tempo siete pronti per stagliare la pasta: dividete l’impasto in 6 porzioni utilizzando un tarocco, poi roteate ciascuna sul piano di lavoro per sigillarla e arrotondarla. Adagiate man mano le palline nella cassetta per la lievitazione distanziandole fra loro. Chiudete con il coperchio (in alternativa potete utilizzare un vassoio leggermente infarinato coperto con pellicola o con un canovaccio umido) e lasciate lievitare per circa 3 ore.\nNel frattempo preparate il condimento: versate i pelati in una ciotola e schiacciateli con le mani, poi condite con olio e basilico, spezzettando le foglioline con le mani.\nSalate e date una mescolata. Tagliate le mozzarelle prima a metà e poi a listarelle. Infine grattugiate il Grana Padano DOP.\nTrascorso il tempo di lievitazione siete pronti per stendere i panetti: spolverizzate leggermente l’impasto con la farina, poi schiacciatelo partendo dal centro e andando verso il bordo in alto, così da spingere l’aria nel cornicione.\nCapovolgete l’impasto e procedete nello stesso modo, schiacciando sempre dal centro verso il bordo in alto. Ora allargate l’impasto tenendolo fermo con una mano e tirandolo delicatamente con l’altra, poi ribaltatelo sull’altra mano e sbattetelo delicatamente sul piano. Quando avrete ottenuto un disco del diametro di circa 28 cm cospargete il pomodoro in modo uniforme, partendo dal centro e lasciando liberi i bordi.\nCospargete con il Grana Padano DOP grattugiato e distribuite la mozzarella, poi condite con un filo di olio.\nTrasferite la pizza sulla pala (se preferite potete stenderla direttamente sulla pala) e cuocete a 250° (o alla massima temperatura) per 5-7 minuti, posizionandola direttamente sulla pietra refrattaria preriscaldata, posizionata nella parte alta del forno. Una volta cotta, sfornate e fate scivolare la pizza nel piatto.\n', 'pizza.jpg'),
('Pesto alla Genovese', 1, 30, 'Liguria', 'Antipasto', 'Per realizzare il pesto alla genovese, per prima cosa staccate le foglioline dai rametti di basilico e mettetele in un colino. Sciacquatele brevemente sotto l’acqua fredda corrente, poi trasferitele su un canovaccio e asciugatele tamponando e sfregando delicatamente. Prestate attenzione all’incavo della foglia in cui si raccoglie l’acqua, dovranno risultare ben asciutte.\nPrendete un mortaio di marmo e inserite all’interno gli spicchi di aglio privati dell’anima e divisi a metà. Lavorate l’aglio con un pestello di legno fino ad ottenere una crema. A questo punto aggiungete i pinoli e procedete nello stesso modo.\nQuando li avrete ridotti in pasta, unite le foglie di basilico e il sale grosso.\nIniziate sempre prima con dei movimenti di percussione per poi proseguire con movimenti rotatori. Abbiate cura di raccogliere con un cucchiaio gli ingredienti dai bordi interni del mortaio così da ottenere un pesto uniforme. Quando la consistenza risulterà cremosa e omogenea, aggiungete il Pecorino a tocchetti e pestate nello stesso modo per incorporarlo, poi unite anche il Parmigiano Reggiano DOP Stravecchio e fate la stessa cosa.\nQuando tutti gli ingredienti saranno ridotti in crema versate l’olio e roteate il pestello ancora per pochi istanti.', 'pestogenovese.jpg'),
('Risotto alla Milanese', 2, 65, 'Lombardia', 'Primo', 'Cuocete il brodo contando almeno mezz’ora dal momento del bollore.\nIn una pentola capiente sciogliete una noce di burro insieme a un filo d’olio d’oliva, quindi soffriggete la cipolla tritata finemente.Imbiondita la cipolla, versate il riso e fatelo tostare un paio di minuti mescolandolo in continuazione. Sfumate col vino bianco.\nEvaporata la parte alcolica del vino, aggiungete la bustina di zafferano e cominciate a cuocere il riso a fiamma media, mescolando in continuazione e aggiungendo un mestolo di brodo per volta.Solo quando il primo mestolo di brodo sarà assorbito, aggiungetene un altro fino a terminare la cottura del riso.\nCotto il riso al dente, spegnete il fuoco, aggiungete il formaggio grattugiato e un’altra noce di burro e mantecate energicamente.Fate riposare il riso in pentola per un paio di minuti prima di servirlo in tavola, chiudendo con un coperchio.\nEcco il risotto allo zafferano servito in tavola!', 'risottomilanese.jpg'),
('Parmigiana di Melanzane', 1, 40, 'Sicilia', 'Secondo', 'Friggiamo le fettine di melanzane in abbondante olio di semi. Dopo la frittura, eliminiamo l’olio in eccesso dalle melanzane, asciugandole con carta assorbente. Sporchiamo il fondo di una teglia (la mia è circolare e ha un diametro di 22 cm) con 2 – 3 cucchiaiate di salsa di pomodoro. La salsa di pomodoro è cotta e condita con cipolla, sale, basilico e olio di oliva. Ricopriamo il fondo della teglia con uno strato di melanzane fritte.\nFarciamo il tortino con il prosciutto cotto, le fette sottili di provola dolce e le uova sode.\nCospargiamo tutti gli ingredienti con abbondante salsa di pomodoro e una generosa spolverizzata di parmigiano grattugiato.\nRicopriamo con un altro strato di melanzane fritte, versiamo ancora la salsa di pomodoro e abbondante formaggio grattugiato. Cuociamo la parmigiana in forno preriscaldato a 180 °C per 30 minuti.\nEcco il tortino di melanzane servito in tavola. Vi consiglio di lasciarlo raffreddare una mezz’ora prima di affettarlo e porzionarlo.\n', 'parmigianamelanzane.jpg'),
('Spaghetti alle Vongole', 1, 20, 'Campania', 'Primo', 'Iniziamo la preparazione dei nostri spaghetti alle vongole: in una padella mettiamo un filo d’olio, l’aglio e il peperoncino.\nTagliamo i gambi del prezzemolo, buttiamoli in padella e facciamo soffriggere.\nRimuoviamo dalla padella l’aglio e i gambi del prezzemolo e versiamoci le vongole.\nDopo un paio di minuti di cottura, sfumiamo le vongole con il vino.\nCopriamo con un coperchio e facciamole cuocere affinché si aprano.\nIntanto buttiamo gli spaghetti in acqua bollente salata e facciamoli lessare.\nScoliamoli al dente direttamente nella padella, senza passare dallo scolapasta, in modo che trattengano un po’ d’acqua di cottura man mano che li trasferiamo.\nTritiamo il prezzemolo, mettiamolo sulla pasta e facciamola saltare in padella ancora per qualche secondo.\nCompletiamo con un pochino di prezzemolo tritato a crudo e portiamo in tavola questa gustosissima pasta alle vongole!', 'spaghettivongole.jpg'),
('Tiramisù', 1, 40, 'Veneto', 'Dessert', 'Per preparare il tiramisù preparate il caffé con la moka per ottenerne 300 g, poi zuccherate a piacere (noi abbiamo messo un cucchiaino) e lasciatelo raffreddare in una ciotolina bassa e ampia. Separate le uova dividendo gli albumi dai tuorli, ricordando che per montare bene gli albumi non dovranno presentare alcuna traccia di tuorlo. Montate i tuorli con le fruste elettriche, versando solo metà dose di zucchero. Non appena il composto sarà diventato chiaro e spumoso, e con le fruste ancora in funzione, potrete aggiungere il mascarpone, poco alla volta.\n Incorporato tutto il formaggio avrete ottenuto una crema densa e compatta; tenetela da parte. Pulite molto bene le fruste e passate a montare gli albumi. Quando saranno schiumosi versate il restante zucchero un po’ alla volta.\nDovrete montarli a neve ben ferma; otterrete questo risultato quando rovesciando la ciotola la massa non si muoverà. Prendete una cucchiaiata di albumi e versatela nella ciotola con la crema di mascarpone e mescolate energicamente con una spatola, così stempererete il composto. Dopodiché procedete ad aggiungere la restante parte di albumi, poco alla volta mescolando molto delicatamente dal basso verso l alto.\n La crema al mascarpone è ora pronta. Distribuitene una generosa cucchiaiata sul fondo di una pirofila di vetro, grande 30x19,5cm e distribuite per bene su tutta la base. Inzuppate per pochi istanti i savoiardi nel caffè freddo prima da un lato e poi dall’altro.\n Man mano distribuite i savoiardi imbevuti nella pirofila, cercando di sistemarli tutti in un verso, così da ottenere un primo strato di biscotti. Aggiungete altra crema al mascarpone e livellatela in modo da coprirli completamente.\n E continuate a distribuire i savoiardi imbevuti nel caffè, poi realizzate un altro strato di crema e livellate bene la superficie.\n Trasferite la crema rimasta in un sac-à-poche con beccuccio liscio di diametro 12 mm e realizzare dei ciuffetti per tutta la dimensione della teglia. Spolverizzatela con del cacao amaro in polvere e lasciate rassodare in frigorifero per un paio d’ore.\n', 'tiramisu.jpg'),
('Gnocchi di riso con verdure', 1, 40, 'Cina', 'Primo', 'Cominciamo impastando la farina 00 e la farina di riso con l’acqua e lavoriamo poi l’impasto a mano fino a raggiungere una consistenza liscia.\nAvvolgiamo l’impasto nella pellicola per alimenti e lasciamolo riposare per circa mezz’ora.\nIntanto, prepariamo le verdure: laviamole, asciughiamole, puliamole e affettiamole.\nScaldiamo l’olio in padella e soffriggiamo l’aglio tritato e lo zenzero.\nAggiungiamo le verdure e lasciamole sul fuoco per 10 minuti, mescolando ogni tanto.\nSfumiamo con la salsa di soia e il condimento è pronto.\nTrascorso il tempo di riposo della pasta, preleviamone piccole quantità e lavoriamole con le dita appiattendole con il pollice sul piano di lavoro per dare forma agli gnocchi.\nPortiamo a bollore l’acqua in una pentola, saliamola, cuociamo gli gnocchi e scoliamoli.\nRipassiamoli nella padella con il condimento e i nostri gnocchi di riso con le verdure sono pronti da servire caldi.', 'gnocchidiriso.jpg'),
('Lasagna', 1, 180, 'Emilia Romagna', 'Primo', 'Cominciamo la preparazione del ragù alla bolognese rosolando la pancetta da sola. Teniamola sul fuoco a fiamma alta fino a quando non si sarà sciolto un po di grasso.Aggiungiamo olio d’oliva, sedano, carota e cipolla tritati e mescoliamo con un mestolo. Cuociamo il soffritto per qualche minuto.\nSgraniamo un pochino la carne tritata mentre la versiamo in pentola, e mescoliamo per distribuirla in modo uniforme. Dopo 5 minuti, sfumiamo con il vino e continuiamo la cottura per altri 5 minuti.\nA questo punto uniamo la passata di pomodoro e aggiustiamo di sale e pepe, dando una bella mescolata.\nFacciamo cuocere a fiamma bassissima per almeno un’ora e mezzo con il coperchio, per far addensare il nostro ragù.\nVersiamo in pentola anche il latte e finiamo di cuocere per altri 5 minuti. La consistenza deve essere omogenea e soprattutto cremosa, se serve lasciamo cuocere anche per 10 minuti.\nUna volta che il ragù è pronto possiamo assemblare la lasagna: mettiamo sul fondo di una pirofila da forno qualche cucchiaio di ragù.\nSistemiamo sopra una sfoglia per lasagne e ricopriamo con altro ragù.\nLasciamo scivolare sul ragù qualche cucchiaio di besciamella.\nCospargiamo con una manciata di formaggio grattugiato.\nCopriamo con un’altra sfoglia e ripetiamo: ragù, besciamella, formaggio grattugiato. Nell’ultimo strato possiamo abbondare sia con la besciamella che con il formaggio.\nInforniamo e cuociamo in forno preriscaldato a 180 °C per 40 minuti.\nSforniamo, attendiamo qualche minuto per farle assestare e poi serviamo.\n', 'lasagna.jpg'),
('Pasta in bianco', 1, 10, 'Lombardia', 'Primo', 'Cuoci la pasta e condiscila con olio o burro.\nAggiungi parmigiano a piacere', 'pastainbianco.jpg'),
('Mozzarella in Carrozza', 1, 30, 'Campania', 'Antipasto', 'Tagliamo le mozzarelle ben scolate a fette e lasciamole riposare qualche attimo sulla carta assorbente in modo che si asciughino.\nEliminiamo la crosta dalle fette di pane tagliandola con un coltello.\nStendiamo le fette di pane su un tagliere, copriamole con le fettine di mozzarella senza arrivare ai bordi, saliamo e chiudiamo con altrettante fette di pane facendo pressione con la mano.\nInzuppiamo i bordi del pane su tutti e quattro i lati nel latte e poi passiamoli nella farina.\nTeniamo a portata di mano una ciotola con le uova sbattute e un piatto di pangrattato, poi passiamo le mozzarelle in carrozza da entrambi i lati prima nelle uova e poi nel pane, aiutandoci con due forchette.\nMan mano che le paniamo, poggiamole su un tagliere o in una teglia e facciamo un po’ di pressione con un coltello con la lama larga per far aderire la panatura alla perfezione.\nScaldiamo l’olio in una padella e friggiamo le mozzarelle in carrozza in modo da raggiungere una doratura uniforme.\nScoliamole ed eliminiamo l’olio in eccesso posando le mozzarelle sulla carta assorbente per qualche momento.Saliamo, tagliamole in diagonale e serviamo le nostre mozzarelle in carrozza.\n', 'mozzarellacarrozza.jpg'),
('Vitello Tonnato', 2, 80, 'Piemonte', 'Antipasto', 'Versiamo mezzo bicchiere d’olio d’oliva in una pentola. Aggiungiamo anche la carne.\nMettiamo in pentola anche una cipolla intera, un sedano tagliato a metà e una carota tagliata a metà.\nPortiamo sul fuoco e lasciamo rosolare su tutti i lati per qualche minuto. Man mano che giriamo la carne, saliamo.\nUna volta che la carne è diventata bianca, aggiungiamo anche il vino bianco e l’acqua. Facciamo cuocere la carne per un’oretta girandola di tanto in tanto.\nDedichiamoci alla salsa tonnata. Mettiamo in un tritatutto la maionese, il tonno ben sgocciolato, le acciughe, i capperi dissalati e un po’ di prezzemolo spezzato. Frulliamo il tutto.\nMettiamo la salsa in una ciotola e lasciamola riposare.\nQuando la carne è cotta, lasciamola raffreddare completamente. Poi tagliamola a fettine sottili.\nPrendiamo un piatto da portata e mettiamo al centro le due metà di un’arancia rivolte verso l’alto. Disponiamo le fette di carne tutte intorno e sopra l’arancia.\nRicopriamo tutto con abbondante salsa tonnata.\nInfine decoriamo con capperi, olive, foglioline di prezzemolo e qualche fettina di limone.\n', 'vitellotonnato.jpg');


-- Inserimento dei dati nel ricettario per il piatto 'Pasta alla Carbonara'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(1, 55, 200),
(1, 54, 100),
(1, 47, 5),
(1, 53, 2),
(1, 51, 25),
(1, 49, 10),
(1, 48, 5),
(1, 52, 25);

-- Inserimento dei dati nel ricettario per il piatto 'Risotto ai Funghi Porcini'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(2, 46, 280),
(2, 45, 4),
(2, 44, 1),
(2, 43, 100),
(2, 42, 1500),
(2, 41, 50),
(2, 40, 5),
(2, 50, 8),
(2, 47, 5),
(2, 48, 4),
(2, 52, 100);

-- Inserimento dei dati nel ricettario per il piatto 'otoletta alla Milanese'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(3, 39, 2),
(3, 53, 2),
(3, 38, 50),
(3, 37, 50),
(3, 41, 200),
(3, 48, 3);


-- Inserimento dei dati nel ricettario per il piatto 'Cacio e Pepe'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(4, 55, 320),
(4, 47, 5),
(4, 51, 200);

-- Inserimento dei dati nel ricettario per il piatto 'Pizza'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(5, 38, 1000),
(5, 36, 700),
(5, 35, 5),
(5, 50, 35),
(5, 48, 25),
(5, 34, 1000),
(5, 33, 600),
(5, 52, 70),
(5, 32, 6),
(5, 48, 30);

-- Inserimento dei dati nel ricettario per il piatto 'Pesto alla Genovese'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(6, 32, 70),
(6, 52, 50),
(6, 50, 70),
(6, 31, 30),
(6, 48, 5),
(6, 30, 2);


-- Inserimento dei dati nel ricettario per il piatto 'Risotto alla Milanese'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(7, 42, 2000),
(7, 11, 1),
(7, 46, 320),
(7, 29, 1),
(7, 52, 40),
(7, 41, 30),
(7, 50, 8),
(7, 43, 100),
(7, 47, 5);

-- Inserimento dei dati nel ricettario per il piatto 'Parmigiana di Melanzane'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(8, 28, 2),
(8, 25, 500),
(8, 26, 150),
(8, 27, 100),
(8, 53, 3),
(8, 52, 60);

-- Inserimento dei dati nel ricettario per il piatto 'Spaghetti alle Vongole'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(9, 55, 320),
(9, 24, 1000),
(9, 30, 1),
(9, 43, 50),
(9, 50, 5),
(9, 40, 3);

-- Inserimento dei dati nel ricettario per il piatto 'Tiramisù'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(10, 22, 750),
(10, 23, 250),
(10, 20, 300),
(10, 53, 5),
(10, 21, 120),
(10, 19, 5);

-- Inserimento dei dati nel ricettario per il piatto 'Gnocchi di Riso'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(11, 18, 320),
(11, 16, 1),
(11, 17, 1),
(11, 15, 5),
(11, 14, 5),
(11, 30, 3);

-- Inserimento dei dati nel ricettario per il piatto 'Lasagna'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(12, 17, 1),
(12, 11, 1),
(12, 10, 1),
(12, 9, 500),
(12, 8, 200),
(12, 25, 400),
(12, 50, 5),
(12, 48, 5),
(12, 47, 3);

-- Inserimento dei dati nel ricettario per il piatto 'Pasta in Bianco'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(13, 55, 320),
(13, 41, 120),
(13, 52, 40);

-- Inserimento dei dati nel ricettario per il piatto 'Mozzarella in Carrozza'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(14, 33, 400),
(14, 6, 400),
(14, 53, 4),
(14, 7, 200),
(14, 38, 40),
(14, 37, 50),
(14, 15, 800),
(14, 48, 5);

-- Inserimento dei dati nel ricettario per il piatto 'Vitello Tonnato'
INSERT INTO ricettario (id_piatto, id_ingrediente, quantita_ingrediente) VALUES 
(15, 5, 800),
(15, 11, 1),
(15, 17, 1),
(15, 10, 1),
(15, 43, 50),
(15, 36, 50),
(15, 50, 50),
(15, 48, 5),
(15, 4, 300),
(15, 3, 30),
(15, 1, 20),
(15, 40, 3);

INSERT INTO `utenti` VALUES (1,'Mario','Rossi','1990-05-15','mario.rossi@example.com','mario123','password123'),(2,'Laura','Bianchi','1985-09-20','laura.bianchi@example.com','laura456','password456'),(3,'Giovanni','Verdi','1988-07-10','giovanni.verdi@example.com','giovanni789','password789');



INSERT INTO `tipo_pasto` VALUES (1,'Colazione'),(2,'Pranzo'),(3,'Cena'),(4,'Spuntino');




INSERT INTO `cookidea`.`preferiti` (`id`, `id_utente`, `id_piatto`) VALUES ('1', '1', '2');
INSERT INTO `cookidea`.`preferiti` (`id`, `id_utente`, `id_piatto`) VALUES ('2', '1', '3');
INSERT INTO `cookidea`.`preferiti` (`id`, `id_utente`, `id_piatto`) VALUES ('3', '1', '4');
INSERT INTO `cookidea`.`preferiti` (`id`, `id_utente`, `id_piatto`) VALUES ('4', '1', '5');
INSERT INTO `cookidea`.`preferiti` (`id`, `id_utente`, `id_piatto`) VALUES ('5', '2', '4');
INSERT INTO `cookidea`.`preferiti` (`id`, `id_utente`, `id_piatto`) VALUES ('6', '2', '8');




 --
select * from preferiti;
