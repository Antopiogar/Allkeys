function loadPiattaforme() {
	let contenitore = document.getElementById("piattaforma");
	let nuovaPiattaforma = document.getElementById("nuovaPiattaforma");

	fetch(`${path}/PiattaformeServlet`, { method: 'get' })
		.then(response => response.json())
		.then(jsonData => {
			console.log("Response from PiattaformeServlet:", jsonData);
			console.log(jsonData["piattaforme"].length, " piattaforme trovate.");

			let option = document.createElement("option");
			option.value = "Nuova piattaforma";
			option.text = "Nuova piattaforma";
			contenitore.add(option);

			for (let i = 0; i < jsonData["piattaforme"].length; i++) {
				let option = document.createElement("option");
				option.value = jsonData["piattaforme"][i];
				option.text = jsonData["piattaforme"][i];
				option.selected = false;
				contenitore.add(option);
			}

			if (jsonData["piattaforme"].length > 0) {
				contenitore.value = jsonData["piattaforme"][0];
			}
		})
		.catch(error => {
			console.error("errore durante il caricamento delle piattaforme:", error);
		});



}
loadPiattaforme();
function switchPiattaforma() {
	let select = document.getElementById("piattaformaArticolo");
	let selectedValue = select.value;

	console.log("Selected platform:", selectedValue);

	if (selectedValue === "Nuova piattaforma") {
		document.getElementById("nuovaPiattaformaContainer").hidden = false;
		document.getElementById("nuovaPiattaforma").value = "";
		document.getElementById("nuovaPiattaforma").focus();
	} else {
		document.getElementById("nuovaPiattaformaContainer").hidden = true;
	}
}

function checkForm() {
	let nome = document.getElementById("nome").value.trim();
	let prezzo = document.getElementById("prezzo").value.trim();
	let descrizione = document.getElementById("descrizione").value.trim();
	let piattaformaSelect = document.getElementById("piattaforma");
	let nuovaPiattaformaInput = document.getElementById("nuovaPiattaforma").value.trim();
	let immagine = document.getElementById("fileInput").files[0];
	let valoreSelect;
	let errorMsg = "";
	let focusErrore = false;

	if (piattaformaSelect.value === "Nuova piattaforma") {
		if (nuovaPiattaformaInput === "") {
			errorMsg += "Inserisci il nome della nuova piattaforma.<br>";
			document.getElementById("nuovaPiattaforma").focus();
			focusErrore = true;
		}
		valoreSelect = nuovaPiattaformaInput;
	} else {
		valoreSelect = piattaformaSelect.value;
	}

	if (nome === "" || prezzo === "" || descrizione === "") {
		errorMsg += "Compila tutti i campi obbligatori.<br>";
		if (!focusErrore) {
			if (nome === "")
				document.getElementById("nome").focus();
			else if (prezzo === "")
				document.getElementById("prezzo").focus();
			else if (descrizione === "")
				document.getElementById("descrizione").focus();
			focusErrore = true;
		}
	}

	if (isNaN(prezzo) || parseFloat(prezzo) <= 0) {
		errorMsg += "Inserisci un prezzo valido (numero positivo).<br>";
		if (!focusErrore) {
			document.getElementById("prezzo").focus();
			focusErrore = true;
		}
	}

	if (immagine) {
		if (!immagine.name.toLowerCase().endsWith(".png")) {
			errorMsg += "Seleziona un'immagine PNG valida.<br>";
			if (!focusErrore) {
				fileInput.focus();
				focusErrore = true;
			}
		}
	}

	if (errorMsg !== "") {
		document.getElementById("errore").innerHTML = errorMsg;
		document.getElementById("errore").hidden = false;
		return false;
	}

	let formData = new FormData();
	formData.append("articolo", IdArt);
	formData.append("nome", nome);
	formData.append("prezzo", prezzo);
	formData.append("descrizione", descrizione);
	formData.append("piattaforma", valoreSelect);
	if (immagine)
		formData.append("fileInput", immagine);


	fetch("UpdateArticolo", {
		method: "POST",
		body: formData
	})
		.then(response => response.json())
		.then(data => {
			console.log("Response from UpdateArticolo:", data);
			if (data.result === "success") {
				document.getElementById("errore").innerHTML = "Articolo modificato con successo! Verrai reindirizzato tra 3 secondi.";
				document.getElementById("errore").className = "messaggio-successo";
				setTimeout(() => {
					window.location.href = "./index.jsp";
				}, 3000);
			} else {
				document.getElementById("errore").innerHTML = "Errore durante la modifica dell'articolo: " + data.message;
				document.getElementById("errore").className = "messaggio-errore";
			}
		})
		.catch(error => {
			console.error("Errore durante la modifica:", error);
			document.getElementById("errore").innerHTML = "Errore durante la modifica dell'articolo. Riprova più tardi.";
			document.getElementById("errore").className = "messaggio-errore";
		});
	document.getElementById("errore").hidden = false;
	return false;
}