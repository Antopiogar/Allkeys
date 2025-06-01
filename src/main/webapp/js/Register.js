"use strict"

async function register() {
	let email = document.getElementById("email").value.trim();
	console.log("Email to check:", email);
	let jsonData;
	try {
		let response = await fetch(`./CheckEmailServlet`, {
			method: 'post',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: new URLSearchParams({ email: email })
		});

		jsonData = await response.json();
	} catch (error) {
		console.error("Error during registration:", error);
		alert("Errore durante la registrazione. Riprova più tardi.");
		return;
	}
	console.log("Response from CheckEmailServlet:", jsonData);
	if (jsonData["exists"] === true) {
		document.getElementById("error").hidden = false;
		document.getElementById("error").innerHTML = "Email già registrata. Inserisci un'altra email.";
		document.getElementById("email").value = ""; // Pulisce il campo email
		document.getElementById("email").focus(); // Riporta il focus sul campo email

	}
	else {
		let registerForm = document.getElementById("form");
		if (checkForm()) {
			registerForm.submit(); // Check superati, invia il form			
		}
		else {
			return false; // Check falliti, non inviare il form
		}
	}
}

function checkForm() {
	let nome = document.getElementById('nome').value.trim();
	let cognome = document.getElementById('cognome').value.trim();
	let dataN = document.getElementById('dataN').value;
	let cf = document.getElementById('cf').value.trim();
	let email = document.getElementById('email').value.trim();
	let password = document.getElementById('password').value;
	let divError = document.getElementById('error');
	let errorMsg = '';
	let focusSet = false; // Variabile per gestire il focus

	// Nome e Cognome: solo lettere
	let expressionNome = /^[A-Za-zÀ-ÿ\s]+$/;
	if (!expressionNome.test(nome)) {
		nome.focus(); // Riporta il focus sul campo nome
		focusSet = true; // Indica che il focus è stato impostato
		errorMsg += 'Il nome non è valido.<br>';
	}
	if (!expressionNome.test(cognome)) {
		if (!focusSet) { // Imposta il focus solo se non è già stato impostato
			cognome.focus(); // Riporta il focus sul campo cognome
			focusSet = true; // Indica che il focus è stato impostato
		}
		errorMsg += 'Il cognome non è valido.<br>';
	}

	// Data di nascita: deve essere una data valida e non futura
	let today = new Date();
	let birthDate = new Date(dataN);
	if (!dataN || birthDate >= today) {
		if (!focusSet) { // Imposta il focus solo se non è già stato impostato
			document.getElementById('dataN').focus(); // Riporta il focus sul campo data di nascita
			focusSet = true; // Indica che il focus è stato impostato
		}
		errorMsg += 'La data di nascita non è valida.<br>';
	}

	// Codice Fiscale: deve avere 16 caratteri alfanumerici
	let expressionCF = /^[A-Z0-9]{16}$/i;
	if (!expressionCF.test(cf)) {
		if (!focusSet) { // Imposta il focus solo se non è già stato impostato
			document.getElementById('cf').focus(); // Riporta il focus sul campo codice fiscale
			focusSet = true; // Indica che il focus è stato impostato
		}
		errorMsg += 'Il codice fiscale non è valido.<br>';
	}

	// Email: formato email standard
	let expressionEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	if (!expressionEmail.test(email)) {
		if (!focusSet) { // Imposta il focus solo se non è già stato impostato
			document.getElementById('email').focus(); // Riporta il focus sul campo email
			focusSet = true; // Indica che il focus è stato impostato
		}
		errorMsg += 'L\'email non è valida.<br>';
	}

	// Password: minimo 8 caratteri, almeno una lettera e un numero
	let expressionPassword = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
	if (!expressionPassword.test(password)) {
		if (!focusSet) { // Imposta il focus solo se non è già stato impostato
			document.getElementById('password').focus(); // Riporta il focus sul campo password
			focusSet = true; // Indica che il focus è stato impostato
		}
		errorMsg += 'La password deve contenere almeno 6 caratteri, di cui almeno una lettera e un numero.<br>';
	}
	divError.hidden = false;
	if (errorMsg) {
		divError.innerHTML = errorMsg;
		return false; // Blocca l'invio del form
	} else {
		divError.hidden = true; // Nasconde il messaggio di errore
		return true; // Permette l'invio del form
	}
}
