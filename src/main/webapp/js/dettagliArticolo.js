"use strict";

function modificaRecensione(idRecensione) {
    let card = document.getElementById("recensione" + idRecensione);
    let divStelle = document.getElementById("stelle" + idRecensione);
    let divTesto = document.getElementById("testo" + idRecensione);
    let testo = divTesto.innerHTML;

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

    let icone = divStelle.getElementsByTagName("i");
    let voto = 0;
    for (let i = 0; i < icone.length; i++) {
        if (icone[i].classList.contains("fa-solid")) {
            voto++;
        }
    }

    stelleInput.name = "voto";
    stelleInput.id = "voto";
    for (let i = 0; i < 5; i++) {
        let stelleOption = document.createElement("option");
        stelleOption.value = i + 1;
        stelleOption.textContent = "⭐".repeat(i + 1);
        stelleInput.appendChild(stelleOption);
    }
    stelleInput.childNodes[voto - 1].selected = true;
    stelleInput.value = voto;
    form.appendChild(stelleInput);

    let br = document.createElement("br");
    form.appendChild(br);

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
    submitButton.className = "center-submit-button";

    submitButton.onclick = () => {
        checkForm(idRecensione);
    }

    form.appendChild(submitButton);

    card.replaceWith(newCard);
}

function checkForm(idRecensione) {
    console.log("idRecensione:", idRecensione);
    
    let stelleInput = document.getElementById("voto");
    let testoInput = document.getElementById("recensione");
    let errori = "";
    let stelleValue = stelleInput.value;
    let testoValue = testoInput.value;

    if (stelleValue === "") {
        errori += "Il numero di stelle non può essere vuoto.<br>";
    }
    if (testoValue === "") {
        errori += "Il testo della recensione non può essere vuoto.<br>";
    }
    if (errori !== "") {
        document.getElementById("errore").innerHTML = errori;
        return;
    }
    if (idRecensione === undefined) {
        document.getElementById("formAggiungi").submit();
    } else {
        send(idRecensione);
    }
}

function send(idRecensione) {
    let stelleValue = document.getElementById("voto").value;
    let testoInput = document.getElementById("recensione").value;
    let messaggioElement = document.getElementById("messaggio");
    if( messaggioElement) {
        messaggioElement.remove();
    }
    fetch("GestioneRecensioniServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            "idRecensione": idRecensione,
            "voto": stelleValue,
            "testo": testoInput
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.result === "success") {
            document.getElementById("errore").innerHTML = "Recensione modificata con successo!";
            document.getElementById("errore").className = "messaggio-successo";
            setTimeout(() => {
                window.location.reload();
            }, 2000);
        } else {
            document.getElementById("errore").innerHTML = data.message;
            document.getElementById("errore").className = "messaggio-errore";
        }
    })
    .catch(error => {
        console.error("Errore durante la modifica della recensione:", error);
        document.getElementById("errore").innerHTML = "Errore durante la modifica della recensione.";
        document.getElementById("errore").className = "messaggio-errore";
    });
}
