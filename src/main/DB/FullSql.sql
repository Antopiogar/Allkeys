DROP SCHEMA IF EXISTS AllKeys;
CREATE SCHEMA IF NOT EXISTS AllKeys;
USE AllKeys;

CREATE TABLE Utente (
    idUtente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    dataNascita DATE NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    cf CHAR(16) NOT NULL,
    password CHAR(64) NOT NULL,
    isAdmin BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE Carta_Pagamento (
    idCarta INT AUTO_INCREMENT PRIMARY KEY,
    titolare VARCHAR(50) NOT NULL,
    numeroCarta CHAR(16) NOT NULL,
    scadenza CHAR(8) NOT NULL,
    codiceCVC CHAR(3) NOT NULL,
    FkUtente INT NULL,
    FOREIGN KEY (FkUtente) REFERENCES Utente(idUtente)
);

CREATE TABLE Articolo (
    idArticolo INT AUTO_INCREMENT PRIMARY KEY,
    logo VARCHAR(50) UNIQUE,
    nome VARCHAR(50) NOT NULL,
    prezzo DECIMAL(10,2) NOT NULL CHECK (prezzo >= 0),
    piattaforma VARCHAR(20) NOT NULL,
    descrizione TEXT
);

CREATE TABLE Ordine (
    idOrdine INT AUTO_INCREMENT PRIMARY KEY,
    dataAcquisto DATETIME NOT NULL,
    conferma BOOLEAN NOT NULL,
    fattura VARCHAR(50),
    fkUtente INT NOT NULL,
    fkCarta INT NULL,
    FOREIGN KEY (fkCarta) REFERENCES Carta_Pagamento(idCarta)
    ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (fkUtente) REFERENCES Utente(idUtente)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Chiave (
    idChiave INT AUTO_INCREMENT PRIMARY KEY,
    codice VARCHAR(24) NOT NULL UNIQUE,
    FkOrdine INT NULL,
    FkArticolo INT NOT NULL,
    FOREIGN KEY (FkOrdine) REFERENCES Ordine(idOrdine)
    ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (FkArticolo) REFERENCES Articolo(idArticolo)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Composizione (
    idComposizione INT AUTO_INCREMENT PRIMARY KEY,
    prezzoPagato DECIMAL(10,2) NOT NULL CHECK(prezzoPagato >= 0),
    qta INT,
    FkArticolo INT NULL,
    FkOrdine INT NULL,
    FOREIGN KEY (FkArticolo) REFERENCES Articolo(idArticolo)
    ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (FkOrdine) REFERENCES Ordine(idOrdine)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Recensione (
    idRecensione INT AUTO_INCREMENT PRIMARY KEY,
    testo VARCHAR(500) NOT NULL,
    voto INT NOT NULL CHECK(voto >= 1 AND voto <= 5),
    dataRecensione DATE NOT NULL,
    FkUtente INT NOT NULL,
    FkArticolo INT NOT NULL,
    UNIQUE(FkUtente, FkArticolo),
    FOREIGN KEY (FkUtente) REFERENCES Utente(idUtente)
    ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (FkArticolo) REFERENCES Articolo(idArticolo)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Inserimento Utenti con password "admin" e secondo utente amministratore
INSERT INTO Utente (nome, cognome, dataNascita, email, cf, password, isAdmin) VALUES
('Mario', 'Rossi', '1990-05-15', 'mario.rossi@email.com', 'RSSMRA90E15H501X', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 0),
('Luca', 'Verdi', '1985-11-20', 'luca.verdi@email.com', 'VRDLUC85S20H501T', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 1);

-- Inserimento Carte di pagamento
INSERT INTO Carta_Pagamento (titolare, numeroCarta, scadenza, codiceCVC, FkUtente) VALUES
('Mario Rossi', '1111222233334444', '01/2027', '123', 1),
('Luca Verdi', '5555666677778888', '03/2029', '456', 2);

-- Inserimento Articoli originali
INSERT INTO Articolo (logo, nome, prezzo, piattaforma, descrizione) VALUES
('logo1.png', 'Cyberpunk 2077', 59.99, 'PC', 'Un vasto RPG ambientato in una futuristica e decadente Night City, dove ogni scelta cambia il destino del protagonista in un mondo aperto e pieno di tecnologia.'),
('logo2.png', 'The Last of Us', 69.99, 'PS5', 'Un avventura emozionante e toccante in un mondo post-apocalittico, in cui Joel ed Ellie combattono per sopravvivere tra pericoli umani e infetti.'),
('logo3.png', 'Halo Infinite', 49.99, 'Xbox', 'Master Chief torna in una battaglia decisiva per salvare l\'umanità, con un gameplay rinnovato e un mondo semi-aperto ricco di azione e misteri.'),
('logo4.png', 'Elden Ring', 59.99, 'PC', 'Un epico gioco di ruolo fantasy creato da FromSoftware, con un mondo oscuro e sconfinato da esplorare, ricco di boss impegnativi e lore affascinante.'),
('logo5.png', 'God of War: Ragnarok', 69.99, 'PS5', 'Kratos e Atreus intraprendono un viaggio mitologico per affrontare divinità norrene, in un\'avventura intensa tra emozioni, battaglie e rivelazioni.'),
('logo6.png', 'Forza Horizon 5', 49.99, 'Xbox', 'Corri attraverso paesaggi mozzafiato del Messico con centinaia di auto in gare spettacolari e libere, in uno dei migliori racing open-world mai creati.'),
('logo7.png', 'Red Dead Redemption 2', 39.99, 'PC', 'Vivi la vita di un fuorilegge in un western realistico e profondo, con una trama coinvolgente, ambientazioni curate e personaggi memorabili.'),
('logo8.png', 'Spider-Man: Miles Morales', 44.99, 'PS5', 'Indossa il costume di Miles in un\'avventura urbana adrenalinica, tra acrobazie spettacolari, nemici unici e una New York innevata da proteggere.'),
('logo9.png', 'Gears 5', 29.99, 'Xbox', 'Un intenso sparatutto in terza persona con una trama emozionante, cooperativa online e combattimenti dinamici in un universo sci-fi devastato dalla guerra.'),
('logo10.png', 'Dark Souls III: The Ringed City', 19.99, 'PC', 'DLC finale della saga Dark Souls con ambientazioni oscure e boss leggendari nella misteriosa Città degli Anelli.'),
('logo11.png', 'Sekiro: Shadows Die Twice', 39.99, 'PC', 'Un action-adventure ambientato nel Giappone feudale con combattimenti intensi e meccaniche di resurrezione uniche.'),
('logo12.png', 'Gears of War 4', 29.99, 'Xbox', 'Continua la saga epica con nuovi eroi in un mondo devastato, combinando azione intensa e momenti narrativi emozionanti.'),
('logo13.png', 'Total War: Warhammer III', 59.99, 'PC', 'Strategia su larga scala in un universo fantasy con battaglie epiche tra fazioni mitologiche e demoniache.'),
('logo14.png', 'Dark Souls', 19.99, 'PC', 'Il primo capitolo della leggendaria saga souls-like con un mondo interconnesso e una difficoltà che ha definito un genere.'),
('logo15.png', 'Dark Souls II: Scholar of the First Sin', 24.99, 'PC', 'Il secondo capitolo della saga con meccaniche rinnovate, nuove aree e una sfida ancora più intensa per i giocatori hardcore.'),
('logo16.png', 'Spider-Man Remastered', 49.99, 'PC', 'La versione definitiva delle avventure di Peter Parker con grafica migliorata e tutti i DLC inclusi.'),
('logo17.png', 'Spider-Man 2', 69.99, 'PS5', 'Nuova avventura con Peter Parker e Miles Morales che uniscono le forze contro minacce più grandi che mai.'),
('logo18.png', 'Horizon Zero Dawn', 39.99, 'PC', 'Avventura post-apocalittica con Aloy che esplora un mondo dominato da macchine meccaniche in paesaggi mozzafiato.'),
('logo19.png', 'Horizon Forbidden West', 59.99, 'PS5', 'Seguito di Zero Dawn con nuove regioni da esplorare, macchine più pericolose e misteri ancestrali da svelare.');

-- Inserimento Ordini
INSERT INTO Ordine (dataAcquisto, conferma, fkUtente, fkCarta, fattura) VALUES
(NOW(), TRUE, 1, 1, 'fattura1.pdf'),
(NOW(), TRUE, 2, 2, 'fattura2.pdf');

-- Inserimento Chiavi originali, alcune con fkOrdine NULL
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
('KEY-GEARS5-XBOX-002', NULL, 9),
('KEY-CP2077-PC-013', NULL, 1),
('KEY-CP2077-PC-014', NULL, 1),
('KEY-TLOU-PS5-008', NULL, 2),
('KEY-TLOU-PS5-009', NULL, 2),
('KEY-TLOU-PS5-010', NULL, 2),
('KEY-TLOU-PS5-011', NULL, 2),
('KEY-TLOU-PS5-012', NULL, 2),
('KEY-TLOU-PS5-013', NULL, 2),
('KEY-TLOU-PS5-014', NULL, 2),
('KEY-HALO-XBOX-005', NULL, 3),
('KEY-HALO-XBOX-006', NULL, 3),
('KEY-HALO-XBOX-007', NULL, 3),
('KEY-HALO-XBOX-008', NULL, 3),
('KEY-HALO-XBOX-009', NULL, 3),
('KEY-HALO-XBOX-010', NULL, 3),
('KEY-HALO-XBOX-011', NULL, 3),
('KEY-HALO-XBOX-012', NULL, 3),
('KEY-ELDEN-PC-003', NULL, 4),
('KEY-ELDEN-PC-004', NULL, 4),
('KEY-ELDEN-PC-005', NULL, 4),
('KEY-ELDEN-PC-006', NULL, 4),
('KEY-ELDEN-PC-007', NULL, 4),
('KEY-ELDEN-PC-008', NULL, 4),
('KEY-ELDEN-PC-009', NULL, 4),
('KEY-ELDEN-PC-010', NULL, 4),
('KEY-GOWR-PS5-003', NULL, 5),
('KEY-GOWR-PS5-004', NULL, 5),
('KEY-GOWR-PS5-005', NULL, 5),
('KEY-GOWR-PS5-006', NULL, 5),
('KEY-GOWR-PS5-007', NULL, 5),
('KEY-GOWR-PS5-008', NULL, 5),
('KEY-GOWR-PS5-009', NULL, 5),
('KEY-GOWR-PS5-010', NULL, 5),
('KEY-FH5-XBOX-003', NULL, 6),
('KEY-FH5-XBOX-004', NULL, 6),
('KEY-FH5-XBOX-005', NULL, 6),
('KEY-FH5-XBOX-006', NULL, 6),
('KEY-FH5-XBOX-007', NULL, 6),
('KEY-FH5-XBOX-008', NULL, 6),
('KEY-FH5-XBOX-009', NULL, 6),
('KEY-FH5-XBOX-010', NULL, 6),
('KEY-RDR2-PC-003', NULL, 7),
('KEY-RDR2-PC-004', NULL, 7),
('KEY-RDR2-PC-005', NULL, 7),
('KEY-RDR2-PC-006', NULL, 7),
('KEY-RDR2-PC-007', NULL, 7),
('KEY-RDR2-PC-008', NULL, 7),
('KEY-RDR2-PC-009', NULL, 7),
('KEY-RDR2-PC-010', NULL, 7),
('KEY-SMM-PS5-003', NULL, 8),
('KEY-SMM-PS5-004', NULL, 8),
('KEY-SMM-PS5-005', NULL, 8),
('KEY-SMM-PS5-006', NULL, 8),
('KEY-SMM-PS5-007', NULL, 8),
('KEY-SMM-PS5-008', NULL, 8),
('KEY-SMM-PS5-009', NULL, 8),
('KEY-SMM-PS5-010', NULL, 8),
('KEY-GEARS5-XBOX-003', NULL, 9),
('KEY-GEARS5-XBOX-004', NULL, 9),
('KEY-GEARS5-XBOX-005', NULL, 9),
('KEY-GEARS5-XBOX-006', NULL, 9),
('KEY-GEARS5-XBOX-007', NULL, 9),
('KEY-GEARS5-XBOX-008', NULL, 9),
('KEY-GEARS5-XBOX-009', NULL, 9),
('KEY-GEARS5-XBOX-010', NULL, 9),
('KEY-DS3RC-PC-001', NULL, 10),
('KEY-DS3RC-PC-002', NULL, 10),
('KEY-DS3RC-PC-003', NULL, 10),
('KEY-DS3RC-PC-004', NULL, 10),
('KEY-DS3RC-PC-005', NULL, 10),
('KEY-DS3RC-PC-006', NULL, 10),
('KEY-DS3RC-PC-007', NULL, 10),
('KEY-DS3RC-PC-008', NULL, 10),
('KEY-DS3RC-PC-009', NULL, 10),
('KEY-DS3RC-PC-010', NULL, 10),
('KEY-SEKIRO-PC-001', NULL, 11),
('KEY-SEKIRO-PC-002', NULL, 11),
('KEY-SEKIRO-PC-003', NULL, 11),
('KEY-SEKIRO-PC-004', NULL, 11),
('KEY-SEKIRO-PC-005', NULL, 11),
('KEY-SEKIRO-PC-006', NULL, 11),
('KEY-SEKIRO-PC-007', NULL, 11),
('KEY-SEKIRO-PC-008', NULL, 11),
('KEY-SEKIRO-PC-009', NULL, 11),
('KEY-SEKIRO-PC-010', NULL, 11),
('KEY-GOW4-XBOX-001', NULL, 12),
('KEY-GOW4-XBOX-002', NULL, 12),
('KEY-GOW4-XBOX-003', NULL, 12),
('KEY-GOW4-XBOX-004', NULL, 12),
('KEY-GOW4-XBOX-005', NULL, 12),
('KEY-GOW4-XBOX-006', NULL, 12),
('KEY-GOW4-XBOX-007', NULL, 12),
('KEY-GOW4-XBOX-008', NULL, 12),
('KEY-GOW4-XBOX-009', NULL, 12),
('KEY-GOW4-XBOX-010', NULL, 12),
('KEY-TWW3-PC-001', NULL, 13),
('KEY-TWW3-PC-002', NULL, 13),
('KEY-TWW3-PC-003', NULL, 13),
('KEY-TWW3-PC-004', NULL, 13),
('KEY-TWW3-PC-005', NULL, 13),
('KEY-TWW3-PC-006', NULL, 13),
('KEY-TWW3-PC-007', NULL, 13),
('KEY-TWW3-PC-008', NULL, 13),
('KEY-TWW3-PC-009', NULL, 13),
('KEY-TWW3-PC-010', NULL, 13),
('KEY-DS1-PC-001', NULL, 14),
('KEY-DS1-PC-002', NULL, 14),
('KEY-DS1-PC-003', NULL, 14),
('KEY-DS1-PC-004', NULL, 14),
('KEY-DS1-PC-005', NULL, 14),
('KEY-DS1-PC-006', NULL, 14),
('KEY-DS1-PC-007', NULL, 14),
('KEY-DS1-PC-008', NULL, 14),
('KEY-DS1-PC-009', NULL, 14),
('KEY-DS1-PC-010', NULL, 14),
('KEY-DS2-PC-001', NULL, 15),
('KEY-DS2-PC-002', NULL, 15),
('KEY-DS2-PC-003', NULL, 15),
('KEY-DS2-PC-004', NULL, 15),
('KEY-DS2-PC-005', NULL, 15),
('KEY-DS2-PC-006', NULL, 15),
('KEY-DS2-PC-007', NULL, 15),
('KEY-DS2-PC-008', NULL, 15),
('KEY-DS2-PC-009', NULL, 15),
('KEY-DS2-PC-010', NULL, 15),
('KEY-SMR-PC-001', NULL, 16),
('KEY-SMR-PC-002', NULL, 16),
('KEY-SMR-PC-003', NULL, 16),
('KEY-SMR-PC-004', NULL, 16),
('KEY-SMR-PC-005', NULL, 16),
('KEY-SMR-PC-006', NULL, 16),
('KEY-SMR-PC-007', NULL, 16),
('KEY-SMR-PC-008', NULL, 16),
('KEY-SMR-PC-009', NULL, 16),
('KEY-SMR-PC-010', NULL, 16),
('KEY-SM2-PS5-001', NULL, 17),
('KEY-SM2-PS5-002', NULL, 17),
('KEY-SM2-PS5-003', NULL, 17),
('KEY-SM2-PS5-004', NULL, 17),
('KEY-SM2-PS5-005', NULL, 17),
('KEY-SM2-PS5-006', NULL, 17),
('KEY-SM2-PS5-007', NULL, 17),
('KEY-SM2-PS5-008', NULL, 17),
('KEY-SM2-PS5-009', NULL, 17),
('KEY-SM2-PS5-010', NULL, 17),
('KEY-HZD-PC-001', NULL, 18),
('KEY-HZD-PC-002', NULL, 18),
('KEY-HZD-PC-003', NULL, 18),
('KEY-HZD-PC-004', NULL, 18),
('KEY-HZD-PC-005', NULL, 18),
('KEY-HZD-PC-006', NULL, 18),
('KEY-HZD-PC-007', NULL, 18),
('KEY-HZD-PC-008', NULL, 18),
('KEY-HZD-PC-009', NULL, 18),
('KEY-HZD-PC-010', NULL, 18),
('KEY-HFW-PS5-001', NULL, 19),
('KEY-HFW-PS5-002', NULL, 19),
('KEY-HFW-PS5-003', NULL, 19),
('KEY-HFW-PS5-004', NULL, 19),
('KEY-HFW-PS5-005', NULL, 19),
('KEY-HFW-PS5-006', NULL, 19),
('KEY-HFW-PS5-007', NULL, 19),
('KEY-HFW-PS5-008', NULL, 19),
('KEY-HFW-PS5-009', NULL, 19),
('KEY-HFW-PS5-010', NULL, 19);

-- Inserimento Composizione
INSERT INTO Composizione (prezzoPagato, qta, FkArticolo, FkOrdine) VALUES
(59.99, 1, 1, 1),
(69.99, 1, 2, 2);

-- Inserimento Recensioni
INSERT INTO Recensione (testo, voto, dataRecensione, FkUtente, FkArticolo) VALUES
('Gioco eccezionale, ambientazione mozzafiato!', 5, '2024-04-11', 1, 1),
('Trama coinvolgente e grafica top!', 4, '2024-04-13', 2, 2);

DELIMITER //

CREATE PROCEDURE createOrdine (
    IN p_IdUtente INT
)
BEGIN
    START TRANSACTION;

    INSERT INTO Ordine (dataAcquisto, conferma, fkUtente)
    VALUES (NOW(), FALSE, p_IdUtente);

    SELECT LAST_INSERT_ID() AS last_Id;

    COMMIT;
END;
//

DELIMITER ;

DROP VIEW IF EXISTS ViewCatalogo;
CREATE VIEW ViewCatalogo AS
SELECT DISTINCT
    a.*
FROM
    Articolo AS a
JOIN Chiave AS c ON a.idArticolo = c.FkArticolo
WHERE c.FkOrdine IS NULL
order by a.nome asc;

DROP VIEW IF EXISTS N_Chiavi_Disponibili;
CREATE VIEW N_Chiavi_Disponibili AS
SELECT
    c.FkArticolo AS idArticolo,
    COUNT(*) AS qta
FROM
    Chiave AS c
JOIN Articolo AS a ON c.FkArticolo = a.idArticolo
WHERE
    c.FkOrdine IS NULL
GROUP BY c.FkArticolo, a.nome;
