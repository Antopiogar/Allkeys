"use strict";

function modificaDati(path){
	let div = document.getElementById("contenitore");
	let varNome = document.getElementById("nome").innerHTML;
	let varCognome = document.getElementById("cognome").innerHTML;
	let varData = document.getElementById("dataN").innerHTML;
	let varCf = document.getElementById("cf").innerHTML;
	let varEmail = document.getElementById("email").innerHTML;
	let htmlForm = `
	<form action="${path}/ModificaUtenteServlet" method = "POST">
				
			<label for="nome">Nome</label>
			<input type="text" name = "nome" required id ="nome" value= "${varNome}">
			<br><br><label for="cognome">Cognome</label>
			<input type="text" name = "cognome" required id ="cognome" value= "${varCognome}">
			<br><br><label for="dataN">Data di nascita</label>
			<input type="date" name = "dataN" required id ="dataN" value="${varData}">
			<br><br><label for="cf">Codice Fiscale</label>
			<input type="text" name = "cf" required id ="cf" value="${varCf}">
			<br><br><label for="email">Email</label>
			<input type="text" name = "email" required id ="email" value="${varEmail}">
			<input type="text" name = "action" value="modifica" hidden="true">
			<br><br><input type="submit" value="Modifica informazioni">
	`;
	div.innerHTML= htmlForm;
}