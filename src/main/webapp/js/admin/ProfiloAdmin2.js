"use strict";

function modificaDati(path) {
	let div = document.getElementById("contenitore");
	let varNome = document.getElementById("nome").innerHTML;
	let varCognome = document.getElementById("cognome").innerHTML;
	let varData = document.getElementById("dataN").innerHTML;
	let varCf = document.getElementById("cf").innerHTML;
	let varEmail = document.getElementById("email").innerHTML;
	let htmlForm = `
	<form action="${path}/ModificaUtenteServlet" method = "POST" id="formProfilo">
				
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
			<input type="text" name = "action" value="modifica" hidden="true"> <br><br>
			<div id="error" hidden = "true"></div>

			<button type="button" class="center-submit-button" onclick="checkForm()">Modifica informazioni</button>
	`;
	div.innerHTML = htmlForm;
}


function checkForm() {
	let nome = document.getElementById("nome").value.trim();
	let cognome = document.getElementById("cognome").value.trim();
	let dataN = document.getElementById("dataN").value;
	let cf = document.getElementById("cf").value.trim();
	let email = document.getElementById("email").value.trim();
	let divError = document.getElementById("error");
	let errorMsg = '';
	let focusSet = false;
	let expressionNome = /^[A-Za-zÀ-ÿ\s]+$/;
	if (!expressionNome.test(nome)) {
		document.getElementById('nome').focus();
		focusSet = true;
		errorMsg += 'Il nome non è valido.<br>';
	}
	if (!expressionNome.test(cognome)) {
		if (!focusSet) {
			document.getElementById('cognome').focus();
			focusSet = true;
		}
		errorMsg += 'Il cognome non è valido.<br>';
	}
	let today = new Date();
	let birthDate = new Date(dataN);
	if (!dataN || birthDate >= today) {
		if (!focusSet) {
			document.getElementById('dataN').focus();
			focusSet = true;
		}
		errorMsg += 'La data di nascita non è valida.<br>';
	}
	let expressionCF = /^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$/i;
	if (!expressionCF.test(cf)) {
		if (!focusSet) {
			document.getElementById('cf').focus();
			focusSet = true;
		}
		errorMsg += 'Il codice fiscale non è valido.<br>';
	}
	let expressionEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	if (!expressionEmail.test(email)) {
		if (!focusSet) {
			document.getElementById('email').focus();
			focusSet = true;
		}
		errorMsg += 'L\'email non è valida.<br>';
	}
	if (errorMsg !== '') {
		divError.innerHTML = errorMsg;
		divError.hidden = false;
		divError.classList.add("messaggio-errore");
		return false;
	} else {
		divError.hidden = true;
		document.getElementById("formProfilo").submit();
		return true;
	}
}