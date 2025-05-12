drop schema if exists AllKeys;
create schema if not exists AllKeys;
use AllKeys;

create table Utente(
	idUtente int auto_increment primary key,
	nome varchar(50) not null,
	cognome varchar(50) not null,
	dataNascita date not null,
	email varchar(50) not null unique,
	cf char(16) not null,
	password char (64) not null,
	isAdmin boolean not null default 0
);

create table Carta_Pagamento(
	idCarta int auto_increment primary key,
	titolare varchar(50) not null,
	numeroCarta char(16) not null,
	scadenza date not null,
	codiceCVC char(3) not null,
	FkUtente int null, 
	foreign key (FkUtente) references Utente(idUtente) 
);

create table Articolo(
	idArticolo int auto_increment primary key,
	logo varchar(50),
	nome varchar(50) not null,
	prezzo decimal(10,2) not null check (prezzo>=0),
	piattaforma varchar(20) not null
);


create table Ordine(
	idOrdine int auto_increment primary key,
	dataAcquisto datetime not null,
	conferma boolean not null,

	fkUtente int not null,
	fkCarta int null,
	foreign key (fkCarta) references Carta_Pagamento(idCarta) 
	on delete restrict on update cascade,
	foreign key (fkUtente) references Utente(idUtente)
	on delete restrict on update cascade
);

create table Chiave(
	idChiave int auto_increment primary key,
	codice varchar(24) not null,

	FkOrdine int null,
	FkArticolo int not null,
	foreign key (FkOrdine) references Ordine(idOrdine)
	on delete restrict on update cascade,
	foreign key (FkArticolo) references Articolo(idArticolo)
	on delete restrict on update cascade

);


create table Composizione(
	idComposizione int auto_increment primary key,
	prezzoPagato decimal(10,2) not null check(prezzoPagato >= 0),
	
	qta int,
	FkArticolo int null,
	FkOrdine int null,

	foreign key (FkArticolo) references Articolo(idArticolo)
	on delete restrict on update cascade,
	foreign key (FkOrdine) references Ordine(idOrdine)
	on delete restrict on update cascade

);

create table Recensione(
	idRecensione int auto_increment primary key,
	testo varchar(500) not null,
	voto int not null check(voto>= 1 && voto <=5),
	dataRecensione date not null,
	FkUtente int not null,
	FkArticolo int not null,

	foreign key (FkUtente) references Utente(idUtente)
	on delete restrict on update cascade,
	foreign key (FkArticolo) references Articolo(idArticolo)
	on delete restrict on update cascade
);


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
('KEY-TLOU-PS5-005', NULL, 2),
('KEY-CP2077-PC-006', NULL, 1),
('KEY-CP2077-PC-007', NULL, 1),
('KEY-CP2077-PC-008', NULL, 1),
('KEY-CP2077-PC-009', NULL, 1);

-- Inserimento Composizione
INSERT INTO Composizione (prezzoPagato,qta, FkArticolo, FkOrdine) VALUES 
(59.99,1, 1, 1),
(69.99,1, 2, 2);

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
CREATE VIEW ViewCatalogo as 
SELECT DISTINCT 
	a.*
FROM
	articolo as a
	join chiave as c on a.idArticolo = c.fkArticolo where c.fkOrdine is null;
	
DROP VIEW IF EXISTS N_Chiavi_Disponibili;
CREATE VIEW N_Chiavi_Disponibili AS
SELECT 
    c.FkArticolo AS idArticolo,
    a.nome AS nome,
    COUNT(*) AS qta
FROM
    allkeys.chiave c
JOIN allkeys.articolo a ON c.FkArticolo = a.idArticolo
WHERE
    c.FkOrdine IS NULL
GROUP BY c.FkArticolo, a.nome;