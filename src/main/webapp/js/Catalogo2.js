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