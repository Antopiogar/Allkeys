function modificaRecensione(idRecensione) {
    //cattura dati
    let card = document.getElementById("recensione" + idRecensione);
    let divStelle = document.getElementById("stelle" + idRecensione);
    let divTesto = document.getElementById("testo" + idRecensione);
    let testo = divTesto.innerHTML;
    console.log("Testo della recensione:", testo);
    

    //creazione nuova card
    let newCard = document.createElement("div");
    let divErrore = document.createElement("div");
    divErrore.id = "errore";
    newCard.appendChild(divErrore);
    newCard.className = "recensione-card";
    let form = document.createElement("form");
    form.method = "POST";
    form.action = "modificaRecensione";
    form.className = "recensione-form";
    let labelStelle = document.createElement("label");
    labelStelle.textContent = "Stelle:";
    labelStelle.htmlFor = "stelle";
    form.appendChild(labelStelle);
    let stelleInput = document.createElement("select");

    // Cattura il numero di stelle della recensione
    let icone = divStelle.getElementsByTagName("i");
    let voto = 0;
    for (var i = 0; i < icone.length; i++) {
        if (icone[i].classList.contains("fa-solid")) {
            voto++;
        }
    }
    console.log("Voto attuale:", voto);
    
    //crea input per le stelle
    stelleInput.name = "voto";
    stelleInput.id = "voto";
    for (let i = 0; i < 5; i++) {
        let stelleOption = document.createElement("option");
        stelleOption.value = i + 1;
        stelleOption.textContent = i + 1;
        stelleInput.appendChild(stelleOption);
    }
    stelleInput.childNodes[voto - 1].selected = true; // Seleziona l'ultima stella
    stelleInput.value = voto; // Imposta il valore dell'input delle stelle
    form.appendChild(stelleInput);

    //crea input per il testo
    let labelTesto = document.createElement("label");
    labelTesto.textContent = "Testo:";
    labelTesto.htmlFor = "recensione";
    let testoInput = document.createElement("textarea");
    testoInput.name = "recensione";
    testoInput.value = testo;
    testoInput.id = "recensione";
    form.appendChild(labelTesto);
    form.appendChild(testoInput);

    newCard.appendChild(form);
    let submitButton = document.createElement("button");
    submitButton.type = "button";
    submitButton.textContent = "Modifica";
    //uso una lamdba per i check ed eventuale invio
    submitButton.onclick = () => {
        console.log("idRecensione:", idRecensione);
        
        checkForm(idRecensione);
    }
    form.appendChild(submitButton);

    card.replaceWith(newCard);
}
async function checkForm(idRecensione) {
    let stelleInput = document.getElementById("voto");
    let testoInput = document.getElementById("testo");
    let errori = "";
    let stelleValue = stelleInput.value;
    let testoValue = testoInput.value;
    //check per tutti i campi
    if(stelleValue === "") {
        errori += "Il numero di stelle non può essere vuoto.<br>";

    }
    if(testoValue === "") {
        errori += "Il testo della recensione non può essere vuoto.<br>";

    }
    if(errori !== "") {
        document.getElementById("errore").innerHTML = errori;
        return;
    }
    if(idRecensione===null)
        // Se idRecensione è null, è una nuova recensione
        document.getElementById("formAggiungi").submit();
    else
        // Altrimenti, invia la richiesta di modifica della recensione
        send(idRecensione);

}

async function send(idRecensione) {
    let stelleValue = document.getElementById("voto").value;
    let testoInput = document.getElementById("recensione").value;
    
    
    let data = await fetch("GestioneRecensioniServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            "idRecensione": idRecensione,
            "voto": stelleValue,
            "testo": testoInput
        }),
    }).then(response => response.json()).catch(error => {
        console.error("Errore durante la modifica della recensione:", error);
        return {success: false, message: "Errore durante la modifica della recensione."};
    });
    if (data.result === "success") {
        console.log("Modifica effettuata con successo:", data);
        document.getElementById("errore").innerHTML = "Recensione modificata con successo!";
        document.getElementById("errore").className = "messaggio-successo";
        // Aggiorna la recensione con i nuovi dati
        setTimeout(() => {
            window.location.reload(); // Ricarica la pagina per vedere le modifiche dopo 5 secondi
        }, 5000);
    } else {
        document.getElementById("errore").innerHTML = data.message;
        document.getElementById("errore").className = "messaggio-errore";
    }
}