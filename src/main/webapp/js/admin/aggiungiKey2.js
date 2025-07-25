"use strict";

function checkForm() {

	let valoreSelect = document.getElementById("articolo").value;
	let codice = document.getElementById("codice").value;
	let errorMsg = "";
	let focusErrore = false;

	if (codice === "") {
		errorMsg += "Compila tutti i campi <br>";
		if (!focusErrore) {
			document.getElementById("codice").focus();
			focusErrore = true;
		}
	}

	if (errorMsg !== "") {
		document.getElementById("errore").innerHTML = errorMsg;
		document.getElementById("errore").hidden = false;
		return false;
	}

	let formData = new FormData();
	formData.append("AdminAction", "addSettedKey");
	formData.append("idArticolo", valoreSelect);
	formData.append("codice", codice);



	fetch("GestioneAdmin", {
		method: "POST",
		body: formData
	})
		.then(response => response.json())
		.then(data => {
			console.log("Response from GestioneAdmin:", data);
			if (data.result === "success") {
				document.getElementById("errore").className = "messaggio-successo";
				document.getElementById("errore").innerHTML = "Articolo aggiunto con successo! Verrai reindirizzato tra 3 secondi.";
				setTimeout(() => {
					window.location.href = "./index.jsp";
				}, 3000);
			} else {
				document.getElementById("errore").className = "messaggio-errore";
				document.getElementById("errore").innerHTML = "Errore durante l'aggiunta dell'articolo: " + data.message;
			}
		})
		.catch(error => {
			console.error("Error during article addition:", error);
			document.getElementById("errore").className = "messaggio-errore";
			document.getElementById("errore").innerHTML = "Errore durante l'aggiunta dell'articolo. Riprova più tardi.";
		});
	document.getElementById("errore").hidden = false;
	return false;
}