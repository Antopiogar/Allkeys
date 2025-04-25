use allkeys;
-- Inserimento Utenti con password "admin"
INSERT INTO Utente (nome, cognome, dataNascita, email, cf, password) VALUES 
('Mario', 'Rossi', '1990-05-15', 'mario.rossi@email.com', 'RSSMRA90E15H501X', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918'),
('Luca', 'Verdi', '1985-11-20', 'luca.verdi@email.com', 'VRDLUC85S20H501T', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');

-- Inserimento Carte di pagamento
INSERT INTO Carta_Pagamento (titolare, numeroCarta, scadenza, codiceCVC, FkUtente) VALUES 
('Mario Rossi', '1111222233334444', '2026-10-01', '123', 1),
('Luca Verdi', '5555666677778888', '2027-04-15', '456', 2);

-- Inserimento Articoli
INSERT INTO Articolo (logo, nome, prezzo, piattaforma) VALUES 
('logo1.png', 'Cyberpunk 2077', 59.99, 'PC'),
('logo2.png', 'The Last of Us', 69.99, 'PS5'),
('logo3.png', 'Halo Infinite', 49.99, 'Xbox');

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
('KEY-TLOU-PS5-005', NULL, 2);

-- Inserimento Composizione
INSERT INTO Composizione (prezzoPagato,qta, FkArticolo, FkOrdine) VALUES 
(59.99, 1, 1, 1),
(69.99, 1, 2, 2);

-- Inserimento Recensioni
INSERT INTO Recensione (testo, voto, dataRecensione, FkUtente, FkArticolo) VALUES 
('Gioco eccezionale, ambientazione mozzafiato!', 5, '2024-04-11', 1, 1),
('Trama coinvolgente e grafica top!', 4, '2024-04-13', 2, 2);
