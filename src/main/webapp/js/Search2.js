"use strict";

function onPageLoad() {
    const inputElement = document.getElementById('fastSearchBar');
    inputElement.addEventListener("input", () => {
        search();
    });
}

let outsideClickHandler = null;

function search() {
    const stringaNavBar = document.getElementById('fastSearchBar').value;
    const searchBarContainer = document.querySelector('.searchBar');

    const oldDropdown = document.getElementById('searchResultsDropdown');
    if (oldDropdown) {
        oldDropdown.remove();
    }

    if (outsideClickHandler) {
        document.removeEventListener('click', outsideClickHandler);
        outsideClickHandler = null;
    }

    if (stringaNavBar.length >= 3) {
        fetch(`${path}/FastSearch?fastSearch=${encodeURIComponent(stringaNavBar)}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(jsonData => {
                if (jsonData["result"] === "successo") {
                    const articoli = jsonData["articoli"];

                    const dropdown = document.createElement('div');
                    dropdown.id = 'searchResultsDropdown';
                    dropdown.className = 'search-results-dropdown';

                    articoli.forEach(articolo => {
                        const itemElement = document.createElement('a');
                        itemElement.href = `${path}/DettagliArticoloServlet?articolo=${articolo.id}`;
                        itemElement.className = 'search-result-item';

                        const nomeElement = document.createElement('span');
                        nomeElement.className = 'item-name';
                        nomeElement.textContent = articolo.nome;

                        const prezzoElement = document.createElement('span');
                        prezzoElement.className = 'item-price';
                        prezzoElement.textContent = ` €${articolo.prezzo.toFixed(2)}`;

                        itemElement.appendChild(nomeElement);
                        itemElement.appendChild(prezzoElement);
                        dropdown.appendChild(itemElement);
                    });

                    searchBarContainer.appendChild(dropdown);

                    outsideClickHandler = function (e) {
                        if (!searchBarContainer.contains(e.target)) {
                            dropdown.remove();
                            document.removeEventListener('click', outsideClickHandler);
                            outsideClickHandler = null;
                        }
                    };

                    document.addEventListener('click', outsideClickHandler);
                } else {
                    console.log("Nessun risultato trovato.");
                }
            })
            .catch(error => {
                console.error("Errore durante la ricerca:", error);
            });
    } else {
        console.log("Inserisci almeno 3 caratteri per avviare la ricerca.");
    }
}

onPageLoad();
