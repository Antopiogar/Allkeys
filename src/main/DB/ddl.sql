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
