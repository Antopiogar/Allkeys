"use strict";

function loadButton() {
	let contenitore = document.getElementById("BottoniFiltro");
	contenitore.style.display = "flex";
	contenitore.style.flexWrap = "wrap"; // Permette ai bottoni di andare a capo se necessario
	contenitore.style.gap = "20px"; // Spaziatura tra i bottoni

	fetch(`./PiattaformeServlet`, { method: 'get' })
		.then(response => response.json())
		.then(jsonData => {
			console.log("Response from PiattaformeServlet:", jsonData);
			console.log(jsonData["piattaforme"].length, " piattaforme trovate.");

			// Rimuove filtri precedenti
			let form = document.createElement("form");
			form.method = "get";
			form.action = "./ViewCatalog";
			form.style.display = "inline-block";
			form.style.gap = "20px";

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
				form.style.display = "inline-block";

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
		})
		.catch(error => {
			console.error("Error during registration:", error);
			alert("Errore durante la registrazione. Riprova più tardi.");
		});
}
loadButton();

function showCustomConfirm(message) {
	return new Promise((resolve) => {
		let existingAlert = document.getElementById("custom-alert");
		if (existingAlert) {
			existingAlert.remove();
		}

		let alertBox = document.createElement("div");
		alertBox.id = "custom-alert";
		alertBox.className = "messaggio-info";

		let alertMessage = document.createElement("p");
		alertMessage.textContent = message;

		let yesButton = document.createElement("button");
		yesButton.textContent = "Sì";

		let noButton = document.createElement("button");
		noButton.textContent = "No";

		yesButton.addEventListener("click", () => {
			alertBox.remove();
			resolve(true);
		});

		noButton.addEventListener("click", () => {
			alertBox.remove();
			resolve(false);
		});

		let buttonWrapper = document.createElement("div");
		buttonWrapper.style.display = "flex";
		buttonWrapper.style.justifyContent = "center";
		buttonWrapper.style.gap = "20px";
		buttonWrapper.appendChild(yesButton);
		buttonWrapper.appendChild(noButton);

		alertBox.appendChild(alertMessage);
		alertBox.appendChild(buttonWrapper);
		document.body.appendChild(alertBox);
	});
}

function elimina(idArticolo, nomeArticolo) {
	showCustomConfirm(`Sei sicuro di voler eliminare ${nomeArticolo}?`).then(risposta => {
		if (risposta) {
			let formData = new FormData();
			formData.append("AdminAction", "elimina");
			formData.append("idArticolo", idArticolo);

			fetch("GestioneArticoliServlet", {
				method: "POST",
				body: formData
			})
				.then(response => response.json())
				.then(data => {
					console.log("Response from GestioneArticoliServlet:", data);
					if (data["result"] === "success") {
						window.location.reload();
					} else {
						alert("Errore durante l'eliminazione dell'articolo: " + data.message);
					}
				})
				.catch(error => {
					console.error("Error during article addition:", error);
					document.getElementById("errore").innerHTML = "Errore durante l'aggiunta dell'articolo. Riprova più tardi.";
					document.getElementById("errore").style.color = "red";
				});
		} else {
			console.log("Utente ha scelto No");
		}
	});
}
