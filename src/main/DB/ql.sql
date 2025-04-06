use AllKeys;
-- Inserimento utenti
INSERT INTO Utente (nome, cognome, dataNascita, email, cf, password) VALUES
('Mario', 'Rossi', '1990-05-15', 'mario.rossi@example.com', 'RSSMRA90E15H501Z', SHA2('password1', 256)),
('Luca', 'Bianchi', '1985-09-20', 'luca.bianchi@example.com', 'BNCLCU85P20F205N', SHA2('password2', 256)),
('Giulia', 'Verdi', '1995-12-03', 'giulia.verdi@example.com', 'VRDGLI95T03D612W', SHA2('password3', 256)),
('Alessia', 'Moretti', '1998-07-22', 'alessia.moretti@example.com', 'MRTALS98L22F839T', SHA2('password4', 256)),
('Stefano', 'Ricci', '1992-04-10', 'stefano.ricci@example.com', 'RCCSTF92D10H501X', SHA2('password5', 256));

-- Inserimento carte di pagamento
INSERT INTO Carta_Pagamento (titolare, numeroCarta, scadenza, codiceCVC, FkUtente) VALUES
('Mario Rossi', '4111111111111111', '2026-07-01', '123', 1),
('Luca Bianchi', '5555444433331111', '2025-12-01', '456', 2),
('Giulia Verdi', '4012888888881881', '2027-06-01', '789', 3),
('Alessia Moretti', '6011111111111117', '2025-08-01', '234', 4),
('Stefano Ricci', '3530111333300000', '2026-09-01', '567', 5);

-- Inserimento articoli
INSERT INTO Articolo (logo, nome, piattaforma) VALUES
('logo1.png', 'Cyberpunk 2077', 'PC'),
('logo2.png', 'The Witcher 3', 'PC'),
('logo3.png', 'Red Dead Redemption 2', 'PS5'),
('logo4.png', 'Elden Ring', 'Xbox'),
('logo5.png', 'God of War Ragnarok', 'PS5'),
('logo6.png', 'FIFA 23', 'PS5'),
('logo7.png', 'Call of Duty: MW2', 'PC'),
('logo8.png', 'Hogwarts Legacy', 'Xbox');

-- Inserimento ordini
INSERT INTO Ordine (dataAcquisto, conferma, fkCarta) VALUES
('2024-03-01', 1, 1),
('2024-03-05', 1, 2),
('2024-03-10', 1, 3),
('2024-03-15', 1, 4),
('2024-03-20', 1, 5);

-- Inserimento chiavi
INSERT INTO Chiave (codice, prezzo, FkOrdine, FkArticolo) VALUES
('ABCDEF1234567890GHIJKL12', 49.99, 1, 1),
('XYZ7890123456789MNPQRSTU', 39.99, 2, 2),
('LMNOPQ7890123456RSTUVWXY', 59.99, 3, 3),
('QRSCDE5678901234FGHIJKLM', 69.99, 4, 4),
('ZYX9876543210TSRQPNOMLKJ', 29.99, 5, 5),
('ASDFGH0987654321ZXCVBNML', 39.99, 1, 6),
('QWERTY6543210987MNBVCXZP', 49.99, 2, 7),
('POIUYT9876543210LKJHGFDS', 59.99, 3, 8);

-- Inserimento composizioni
INSERT INTO Composizione (prezzoPagato, FkArticolo, FkOrdine) VALUES
(49.99, 1, 1),
(39.99, 2, 2),
(59.99, 3, 3),
(69.99, 4, 4),
(29.99, 5, 5),
(39.99, 6, 1),
(49.99, 7, 2),
(59.99, 8, 3);

-- Inserimento recensioni
INSERT INTO Recensione (testo, voto, dataRecensione, FkUtente, FkArticolo) VALUES
('Gioco fantastico, grafica pazzesca!', 5, '2024-03-02', 1, 1),
('Molto bello, ma con qualche bug', 4, '2024-03-06', 2, 2),
('Ottima trama e gameplay coinvolgente', 5, '2024-03-11', 3, 3),
('Storia avvincente, difficoltà ben bilanciata', 5, '2024-03-16', 4, 4),
('Grafica incredibile e storia emozionante', 5, '2024-03-21', 5, 5),
('Bel gioco, ma il gameplay è ripetitivo', 3, '2024-03-22', 1, 6),
('Sparatorie realistiche, ma troppi cheater online', 4, '2024-03-23', 2, 7),
('Mondo magico incredibile, ma qualche bug fastidioso', 4, '2024-03-24', 3, 8);
