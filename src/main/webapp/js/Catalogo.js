"use strict"

async function loadButton() {
	let contenitore = document.getElementById("BottoniFiltro");
	contenitore.style.display = "flex";
	contenitore.style.flexWrap = "wrap"; // Permette ai bottoni di andare a capo se necessario
	contenitore.style.gap = "20px"; // Spaziatura tra i bottoni
	let jsonData;
	try {
		let response = await fetch(`./PiattaformeServlet`, {
			method: 'get'
		});
		jsonData = await response.json();
		console.log("Response from PiattaformeServlet:", jsonData);

	} catch (error) {
		console.error("Error during registration:", error);
		alert("Errore durante la registrazione. Riprova più tardi.");
		return;
	}
	console.log(jsonData["piattaforme"].length, " piattaforme trovate.");

	//Rimuove filtri precedenti
	let form = document.createElement("form");
	form.method = "get";
	form.action = "./ViewCatalog";
	form.style.display = "inline-block"; // Disposizione orizzontale
	form.style.gap = "20px"; // Spaziatura tra i bottoni

	let button = document.createElement("button");
	button.type = "submit";
	button.innerHTML = "Tutte le piattaforme";

	form.appendChild(button);
	contenitore.appendChild(form);


	for (let i = 0; i < jsonData["piattaforme"].length; i++) {
		console.log("Piattaforma:", jsonData["piattaforme"][i]);

		let form = document.createElement("form");
		form.method = "get";
		form.action = "./ViewCatalog";
		form.style.display = "inline-block"; // Disposizione orizzontale

		let hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = "piattaforma";
		hiddenInput.value = jsonData["piattaforme"][i];

		let button = document.createElement("button");
		button.type = "submit";
		button.innerHTML = jsonData["piattaforme"][i];

		form.appendChild(hiddenInput);
		form.appendChild(button);
		contenitore.appendChild(form);
	}

}
loadButton();

function showCustomConfirm(message) {
	return new Promise((resolve) => {
		// Rimuove eventuali alert precedenti
		let existingAlert = document.getElementById("custom-alert");
		if (existingAlert) {
			existingAlert.remove();
		}

		// Crea il contenitore dell'alert
		let alertBox = document.createElement("div");
		alertBox.id = "custom-alert";
		alertBox.className = "messaggio-info";

		// Crea il messaggio
		let alertMessage = document.createElement("p");
		alertMessage.textContent = message;

		// Crea i pulsanti
		let yesButton = document.createElement("button");
		yesButton.textContent = "Sì";

		let noButton = document.createElement("button");
		noButton.textContent = "No";

		// Gestione eventi
		yesButton.addEventListener("click", () => {
			alertBox.remove();
			resolve(true); // Risposta: Sì
		});

		noButton.addEventListener("click", () => {
			alertBox.remove();
			resolve(false); // Risposta: No
		});

		// Componi l'alert
		alertBox.appendChild(alertMessage);

		let buttonWrapper = document.createElement("div");
		buttonWrapper.style.display = "flex";
		buttonWrapper.style.justifyContent = "center";
		buttonWrapper.style.gap = "20px";
		buttonWrapper.appendChild(yesButton);
		buttonWrapper.appendChild(noButton);

		alertBox.appendChild(buttonWrapper);
		document.body.appendChild(alertBox);
	});
}


async function elimina(idArticolo, nomeArticolo) {
	let risposta = await showCustomConfirm(`Sei sicuro di voler eliminare ${nomeArticolo}?`);
	if (risposta) {

		let formData = new FormData();

		formData.append("AdminAction", "elimina");
		formData.append("idArticolo", idArticolo);


		let result;
		await fetch("GestioneArticoliServlet", {
			method: "POST",
			body: formData
		})
			.then(response => response.json())
			.then(data => {
				result = data;
				console.log("Response from GestioneArticoliServlet:", data);
			})
			.catch(error => {
				console.error("Error during article addition:", error);
				document.getElementById("errore").innerHTML = "Errore durante l'aggiunta dell'articolo. Riprova più tardi.";
				document.getElementById("errore").style.color = "red";
			});
		if (result["result"] === "success") {
			window.location.reload(); // Ricarica la pagina per aggiornare il catalogo
		}
		else {
			alert("Errore durante l'eliminazione dell'articolo: " + result.message);
		}
	} else {
		console.log("Utente ha scelto No");
	}
}
