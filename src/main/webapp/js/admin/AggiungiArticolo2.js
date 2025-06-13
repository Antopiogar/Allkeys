"use strict";

function loadPiattaforme() {
    let contenitore = document.getElementById("piattaforma");
    let nuovaPiattaforma = document.getElementById("nuovaPiattaforma");

    fetch(`./PiattaformeServlet`, { method: 'get' })
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
            console.error("Error during registration:", error);
            alert("Errore durante la registrazione. Riprova più tardi.");
        });
}
loadPiattaforme();

function switchPiattaforma() {
    let select = document.getElementById("piattaforma");
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