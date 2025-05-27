"use strict";

function onPageLoad() {
	var inputElement = document.getElementById('fastSearchBar');

	inputElement.addEventListener("input", (e) => {
		search();
	});
}


async function search(){
	let stringaNavBar = document.getElementById('fastSearchBar').value;
	let articoli;
	console.log("PIPPO");
	if(stringaNavBar.length>=3){
		let jsonData = await fetch(`./FastSearch?fastSearch=${stringaNavBar}`, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}}
		)
		.then(res => res.json())

		if(jsonData["result"] === "successo"){
			articoli = jsonData["articoli"];
			console.log(articoli)

			    // Seleziona il container della searchbar
			    var searchBarContainer = document.querySelector('.searchBar');
			    
			    // Rimuovi dropdown esistenti
			    var oldDropdown = document.getElementById('searchResultsDropdown');
			    if (oldDropdown) {
			        searchBarContainer.removeChild(oldDropdown);
			    }
			    
			    // Crea il container del dropdown
			    var dropdown = document.createElement('div');
			    dropdown.id = 'searchResultsDropdown';
			    dropdown.className = 'search-results-dropdown';
			    
			    // Popola il dropdown con gli articoli usando for
			    for (var i = 0; i < articoli.length; i++) {
			        var articolo = articoli[i];
			        
			        // Crea elemento articolo
			        var itemElement = document.createElement('a');
			        itemElement.href = './DettagliArticoloServlet?articolo=' + articolo.id;
			        itemElement.className = 'search-result-item';
			        
			        // Crea contenuto
			        var nomeElement = document.createElement('span');
			        nomeElement.className = 'item-name';
			        nomeElement.textContent = articolo.nome;
			        
			        var prezzoElement = document.createElement('span');
			        prezzoElement.className = 'item-price';
			        prezzoElement.textContent = ' €' + articolo.prezzo.toFixed(2);
			        
			        // Aggiungi elementi al DOM
			        itemElement.appendChild(nomeElement);
			        itemElement.appendChild(prezzoElement);
			        dropdown.appendChild(itemElement);
			    }
			    
			    // Aggiungi il dropdown al container
			    searchBarContainer.appendChild(dropdown);
			    
			    // Gestione chiusura al click esterno
			    document.addEventListener('click', function(e) {
			        if (!searchBarContainer.contains(e.target)) {
			            searchBarContainer.removeChild(dropdown);
			        }
			    });
		}
		else{
			console.log(`BOOOM`)

		}
		console.log(jsonData["result"]);

	}
	else{
		console.log("Stringa piccola");
	}
	

}

function creaRisultatoFastSearch(results){

	// Seleziona la searchbar e il suo container
	let searchBar = document.getElementById('fastSearchBar');
	let searchBarContainer = document.querySelector('.searchBar');
	
	
	
	// Se ci sono risultati, crea il dropdown
	if (results && results.length > 0) {
		
		
		// Aggiungi il dropdown al container della searchbar
		searchBarContainer.appendChild(dropdown);
		
		// Gestisci la chiusura del dropdown quando si clicca fuori
		document.addEventListener('click', function outsideClickHandler(e) {
			if (!searchBarContainer.contains(e.target)) {
				dropdown.remove();
				document.removeEventListener('click', outsideClickHandler);
			}
		});
	}

}
onPageLoad();