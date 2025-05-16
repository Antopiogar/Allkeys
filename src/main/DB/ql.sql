-- Inserimento Utenti con password "admin"
INSERT INTO Utente (nome, cognome, dataNascita, email, cf, password, isAdmin) VALUES 
('Mario', 'Rossi', '1990-05-15', 'mario.rossi@email.com', 'RSSMRA90E15H501X', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',0),
('Luca', 'Verdi', '1985-11-20', 'luca.verdi@email.com', 'VRDLUC85S20H501T', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',1);

-- Inserimento Carte di pagamento
INSERT INTO Carta_Pagamento (titolare, numeroCarta, scadenza, codiceCVC, FkUtente) VALUES 
('Mario Rossi', '1111222233334444', '2026-10-01', '123', 1),
('Luca Verdi', '5555666677778888', '2027-04-15', '456', 2);

-- Inserimento Articoli
INSERT INTO Articolo (logo, nome, prezzo, piattaforma,descrizione) VALUES 
('logo1.png', 'Cyberpunk 2077', 59.99, 'PC','Un vasto RPG ambientato in una futuristica e decadente Night City, dove ogni scelta cambia il destino del protagonista in un mondo aperto e pieno di tecnologia.'),
('logo2.png', 'The Last of Us', 69.99, 'PS5','Un avventura emozionante e toccante in un mondo post-apocalittico, in cui Joel ed Ellie combattono per sopravvivere tra pericoli umani e infetti.'),
('logo3.png', 'Halo Infinite', 49.99, 'Xbox',"Master Chief torna in una battaglia decisiva per salvare l’umanità, con un gameplay rinnovato e un mondo semi-aperto ricco di azione e misteri."),
('logo4.png', 'Elden Ring', 59.99, 'PC',"Un epico gioco di ruolo fantasy creato da FromSoftware, con un mondo oscuro e sconfinato da esplorare, ricco di boss impegnativi e lore affascinante."),
('logo5.png', 'God of War: Ragnarok', 69.99, 'PS5',"Kratos e Atreus intraprendono un viaggio mitologico per affrontare divinità norrene, in un'avventura intensa tra emozioni, battaglie e rivelazioni."),
('logo6.png', 'Forza Horizon 5', 49.99, 'Xbox',"Corri attraverso paesaggi mozzafiato del Messico con centinaia di auto in gare spettacolari e libere, in uno dei migliori racing open-world mai creati."),
('logo7.png', 'Red Dead Redemption 2', 39.99, 'PC',"Vivi la vita di un fuorilegge in un western realistico e profondo, con una trama coinvolgente, ambientazioni curate e personaggi memorabili."),
('logo8.png', 'Spider-Man: Miles Morales', 44.99, 'PS5',"Indossa il costume di Miles in un’avventura urbana adrenalinica, tra acrobazie spettacolari, nemici unici e una New York innevata da proteggere."),
('logo9.png', 'Gears 5', 29.99, 'Xbox',"Un intenso sparatutto in terza persona con una trama emozionante, cooperativa online e combattimenti dinamici in un universo sci-fi devastato dalla guerra.");

-- Inserimento Ordini
INSERT INTO Ordine (dataAcquisto, conferma, fkUtente, fkCarta) VALUES 
(now(), true, 1, 1),
(now(), true, 2, 2);

-- Inserimento Chiavi, alcune con fkOrdine NULL
INSERT INTO Chiave (codice, FkOrdine, FkArticolo) VALUES 
('KEY-CP2077-PC-001', 1, 1),
('KEY-TLOU-PS5-002', 2, 2),
('KEY-HALO-XBOX-003', NULL, 3),
('KEY-CP2077-PC-004', NULL, 1),
('KEY-TLOU-PS5-005', NULL, 2),
('KEY-CP2077-PC-006', NULL, 1),
('KEY-CP2077-PC-007', NULL, 1),
('KEY-CP2077-PC-008', NULL, 1),
('KEY-CP2077-PC-009', NULL, 1),
('KEY-CP2077-PC-010', NULL, 1),
('KEY-CP2077-PC-011', NULL, 1),
('KEY-CP2077-PC-012', NULL, 1),
('KEY-TLOU-PS5-006', NULL, 2),
('KEY-TLOU-PS5-007', NULL, 2),
('KEY-HALO-XBOX-004', NULL, 3),
('KEY-ELDEN-PC-001', NULL, 4),
('KEY-ELDEN-PC-002', NULL, 4),
('KEY-GOWR-PS5-001', NULL, 5),
('KEY-GOWR-PS5-002', NULL, 5),
('KEY-FH5-XBOX-001', NULL, 6),
('KEY-FH5-XBOX-002', NULL, 6),
('KEY-RDR2-PC-001', NULL, 7),
('KEY-RDR2-PC-002', NULL, 7),
('KEY-SMM-PS5-001', NULL, 8),
('KEY-SMM-PS5-002', NULL, 8),
('KEY-GEARS5-XBOX-001', NULL, 9),
('KEY-GEARS5-XBOX-002', NULL, 9);

-- Inserimento Composizione
INSERT INTO Composizione (prezzoPagato,qta, FkArticolo, FkOrdine) VALUES 
(59.99,1, 1, 1),
(69.99,1, 2, 2);

-- Inserimento Recensioni
INSERT INTO Recensione (testo, voto, dataRecensione, FkUtente, FkArticolo) VALUES 
('Gioco eccezionale, ambientazione mozzafiato!', 5, '2024-04-11', 1, 1),
('Trama coinvolgente e grafica top!', 4, '2024-04-13', 2, 2);
