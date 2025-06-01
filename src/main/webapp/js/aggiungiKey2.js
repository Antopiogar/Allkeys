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



	fetch("AggiungiArticolo", {
		method: "POST",
		body: formData
	})
		.then(response => response.json())
		.then(data => {
			console.log("Response from AggiungiArticoloServlet:", data);
			if (data.result === "success") {
				document.getElementById("errore").innerHTML = "Articolo aggiunto con successo! Verrai reindirizzato tra 10 secondi.";
				document.getElementById("errore").style.color = "green";
				setTimeout(() => {
					window.location.href = "./index.jsp";
				}, 10000);
			} else {
				document.getElementById("errore").innerHTML = "Errore durante l'aggiunta dell'articolo: " + data.message;
				document.getElementById("errore").style.color = "red";
			}
		})
		.catch(error => {
			console.error("Error during article addition:", error);
			document.getElementById("errore").innerHTML = "Errore durante l'aggiunta dell'articolo. Riprova più tardi.";
			document.getElementById("errore").style.color = "red";
		});
	document.getElementById("errore").hidden = false;
	return false;
}