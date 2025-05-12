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

