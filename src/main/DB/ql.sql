-- Inserimento utenti
INSERT INTO Utente (nome, cognome, dataNascita, email, cf, password) VALUES
('Mario', 'Rossi', '1990-01-01', 'mario.rossi@email.it', 'RSSMRA90A01H501U', 'a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8'),
('Luca', 'Bianchi', '1985-03-15', 'luca.bianchi@email.it', 'BNCLCU85C15H501W', 'abcdef1234567890abcdef1234567890abcdef1234567890abcdef1234567890'),
('Sara', 'Verdi', '1995-06-20', 'sara.verdi@email.it', 'VRDSRA95H60H501Z', '0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcd'),
('Giulia', 'Neri', '1992-12-12', 'giulia.neri@email.it', 'NRIGLU92T12H501R', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'),
('Marco', 'Russo', '1988-09-09', 'marco.russo@email.it', 'RSSMRC88P09H501T', 'bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb');

-- Carte di pagamento collegate agli utenti
INSERT INTO Carta_Pagamento (titolare, numeroCarta, scadenza, codiceCVC, FkUtente) VALUES
('Mario Rossi', '1234567812345678', '2027-12-31', '123', 1),
('Luca Bianchi', '1111222233334444', '2026-06-30', '456', 2),
('Sara Verdi', '5555666677778888', '2028-01-01', '789', 3),
('Giulia Neri', '9999000011112222', '2029-10-15', '321', 4),
('Marco Russo', '4444333322221111', '2025-08-31', '654', 5);

-- Articoli (9, senza Zelda/Switch)
INSERT INTO Articolo (logo, nome, piattaforma) VALUES
('eldenring.png', 'Elden Ring', 'PC'),
('fifa24.png', 'FIFA 24', 'PS5'),
('cyberpunk.png', 'Cyberpunk 2077', 'PC'),
('gowr.png', 'God of War Ragnarok', 'PS5'),
('hogwarts.png', 'Hogwarts Legacy', 'PC'),
('codmw3.png', 'COD: Modern Warfare 3', 'Xbox'),
('rdr2.png', 'Red Dead Redemption 2', 'PC'),
('ffxvi.png', 'Final Fantasy XVI', 'PS5'),
('minecraft.png', 'Minecraft', 'PC');

-- Ordini
INSERT INTO Ordine (dataAcquisto, conferma, fkUtente, fkCarta) VALUES
('2024-01-10', true, 1, 1),
('2024-01-12', true, 2, 2),
('2024-01-15', true, 3, 3),
('2024-02-01', true, 4, 4),
('2024-02-05', true, 5, 5);

-- Chiavi (niente riferimenti all’articolo Switch)
INSERT INTO Chiave (codice, prezzo, FkOrdine, FkArticolo) VALUES
('ELDEN-KEY-000000000001', 59.99, 1, 1),
('FIFA24-KEY-00000000002', 49.99, 2, 2),
('CYBER-KEY-00000000003', 39.99, 3, 3),
('GOWR-KEY-000000000004', 69.99, 4, 4),
('HOGW-KEY-000000000006', 54.99, 5, 5),
('CODMW3-KEY-000000007', 59.99, 1, 6),
('RDR2-KEY-00000000008', 49.99, 1, 7),
('FFXVI-KEY-0000000009', 69.99, 2, 8),
('MINECRAFT-KEY-000010', 19.99, 3, 9);

-- Composizione
INSERT INTO Composizione (prezzoPagato, FkArticolo, FkOrdine) VALUES
(59.99, 1, 1),
(49.99, 2, 2),
(39.99, 3, 3),
(69.99, 4, 4),
(54.99, 5, 5),
(59.99, 6, 1),
(49.99, 7, 1),
(69.99, 8, 2),
(19.99, 9, 3);

-- Recensioni (nessuna su Zelda)
INSERT INTO Recensione (testo, voto, dataRecensione, FkUtente, FkArticolo) VALUES
('Capolavoro assoluto!', 5, '2024-01-11', 1, 1),
('Buono ma non rivoluzionario.', 3, '2024-01-13', 2, 2),
('Tanta potenzialità, pochi bug ormai.', 4, '2024-01-16', 3, 3),
('Grafica pazzesca, combat divertente!', 5, '2024-02-02', 4, 4),
('Ottima ambientazione, gameplay ok.', 4, '2024-02-06', 5, 5),
('Multiplayer competitivo e fluido.', 4, '2024-02-07', 1, 6),
('Narrativa profonda e ben scritta.', 5, '2024-02-08', 1, 7),
('Combattimenti spettacolari.', 5, '2024-02-09', 2, 8),
('Creativo, senza tempo.', 5, '2024-02-10', 3, 9);