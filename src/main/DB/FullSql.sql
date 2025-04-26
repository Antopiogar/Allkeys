DROP SCHEMA IF EXISTS AllKeys;
CREATE SCHEMA IF NOT EXISTS AllKeys;
USE AllKeys;

-- Creazione Tabelle
CREATE TABLE Utente(
    idUtente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    dataNascita DATE NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    cf CHAR(16) NOT NULL,
    password CHAR(64) NOT NULL
);

CREATE TABLE Carta_Pagamento(
    idCarta INT AUTO_INCREMENT PRIMARY KEY,
    titolare VARCHAR(50) NOT NULL,
    numeroCarta CHAR(16) NOT NULL,
    scadenza DATE NOT NULL,
    codiceCVC CHAR(3) NOT NULL,
    FkUtente INT NULL, 
    FOREIGN KEY (FkUtente) REFERENCES Utente(idUtente)
);

CREATE TABLE Articolo(
    idArticolo INT AUTO_INCREMENT PRIMARY KEY,
    logo VARCHAR(50),
    nome VARCHAR(50) NOT NULL,
    prezzo DECIMAL(10,2) NOT NULL CHECK (prezzo >= 0),
    piattaforma VARCHAR(20) NOT NULL
);

CREATE TABLE Ordine(
    idOrdine INT AUTO_INCREMENT PRIMARY KEY,
    dataAcquisto DATETIME NOT NULL,
    conferma BOOLEAN NOT NULL,
    fkUtente INT NOT NULL,
    fkCarta INT NULL,
    FOREIGN KEY (fkCarta) REFERENCES Carta_Pagamento(idCarta)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (fkUtente) REFERENCES Utente(idUtente)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Chiave(
    idChiave INT AUTO_INCREMENT PRIMARY KEY,
    codice VARCHAR(24) NOT NULL,
    FkOrdine INT NULL,
    FkArticolo INT NOT NULL,
    FOREIGN KEY (FkOrdine) REFERENCES Ordine(idOrdine)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (FkArticolo) REFERENCES Articolo(idArticolo)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Composizione(
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

CREATE TABLE Recensione(
    idRecensione INT AUTO_INCREMENT PRIMARY KEY,
    testo VARCHAR(500) NOT NULL,
    voto INT NOT NULL CHECK(voto >= 1 AND voto <= 5),
    dataRecensione DATE NOT NULL,
    FkUtente INT NOT NULL,
    FkArticolo INT NOT NULL,
    FOREIGN KEY (FkUtente) REFERENCES Utente(idUtente)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (FkArticolo) REFERENCES Articolo(idArticolo)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Inserimento Dati
INSERT INTO Utente (nome, cognome, dataNascita, email, cf, password) VALUES 
('Mario', 'Rossi', '1990-05-15', 'mario.rossi@email.com', 'RSSMRA90E15H501X', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918'),
('Luca', 'Verdi', '1985-11-20', 'luca.verdi@email.com', 'VRDLUC85S20H501T', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');

INSERT INTO Carta_Pagamento (titolare, numeroCarta, scadenza, codiceCVC, FkUtente) VALUES 
('Mario Rossi', '1111222233334444', '2026-10-01', '123', 1),
('Luca Verdi', '5555666677778888', '2027-04-15', '456', 2);

INSERT INTO Articolo (logo, nome, prezzo, piattaforma) VALUES 
('logo1.png', 'Cyberpunk 2077', 59.99, 'PC'),
('logo2.png', 'The Last of Us', 69.99, 'PS5'),
('logo3.png', 'Halo Infinite', 49.99, 'Xbox');

INSERT INTO Ordine (dataAcquisto, conferma, fkUtente, fkCarta) VALUES 
(NOW(), TRUE, 1, 1),
(NOW(), TRUE, 2, 2);

INSERT INTO Chiave (codice, FkOrdine, FkArticolo) VALUES 
('KEY-CP2077-PC-001', 1, 1),
('KEY-TLOU-PS5-002', 2, 2),
('KEY-HALO-XBOX-003', NULL, 3),
('KEY-CP2077-PC-004', NULL, 1),
('KEY-TLOU-PS5-005', NULL, 2);

INSERT INTO Composizione (prezzoPagato, qta, FkArticolo, FkOrdine) VALUES 
(59.99, 1, 1, 1),
(69.99, 1, 2, 2);

INSERT INTO Recensione (testo, voto, dataRecensione, FkUtente, FkArticolo) VALUES 
('Gioco eccezionale, ambientazione mozzafiato!', 5, '2024-04-11', 1, 1),
('Trama coinvolgente e grafica top!', 4, '2024-04-13', 2, 2);

-- Creazione Viste

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

DROP VIEW IF EXISTS N_Chiavi_Disponibili;
CREATE VIEW N_Chiavi_Disponibili AS
SELECT 
    COUNT(idChiave) AS qta,
    c.idChiave AS idChiave,
    c.FkArticolo AS idArticolo,
    a.nome AS nome
FROM allkeys.chiave c
JOIN allkeys.articolo a ON c.FkArticolo = a.idArticolo
WHERE c.FkOrdine IS NULL
GROUP BY c.FkArticolo;

DROP VIEW IF EXISTS ViewCatalogo;
CREATE VIEW ViewCatalogo AS
SELECT 
    a.*
FROM allkeys.articolo a
JOIN N_Chiavi_Disponibili c ON a.idArticolo = c.idArticolo
WHERE c.qta >= 1;
