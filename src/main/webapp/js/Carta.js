function checkForm() {
	event.preventDefault(); // Previene il submit normale
	let titolare = document.getElementById('titolare').value.trim();
	let numeroCarta = document.getElementById('numeroCarta').value.trim();
	let scadenza = document.getElementById('scadenza').value.trim();
	let cvc = document.getElementById('cvc').value.trim();
	let divError = document.getElementById('errore');
	let titolareRegex = /^[A-Za-zÀ-ÿ\s]+$/;
	let numeroCartaRegex = /^\d{16}$/;
	let scadenzaRegex = /^(0[1-9]|1[0-2])\/\d{4}$/; // Formato MM/AAAA
	// Regex per il CVV: 3 cifre
	let cvcRegex = /^\d{3}$/;
	let errorMsg = '';
	let focusSet = false;

	if (!titolareRegex.test(titolare)) {
		document.getElementById('titolare').focus();
		focusSet = true;
		errorMsg += 'Il nome del titolare non è valido.<br>';
	}
	if (!numeroCartaRegex.test(numeroCarta)) {
		if (!focusSet) {
			document.getElementById('numeroCarta').focus();
			focusSet = true;
		}
		errorMsg += 'Il numero della carta non è valido.<br>';
	}
	if (!scadenzaRegex.test(scadenza)) {
		if (!focusSet) {
			document.getElementById('scadenza').focus();
			focusSet = true;
		}
		errorMsg += 'La data di scadenza non è valida.<br>';
	}
	if (cvc.length !== 3 || !cvcRegex.test(cvc)) {
		if (!focusSet) {
			document.getElementById('cvc').focus();
			focusSet = true;
		}
		errorMsg += 'Il CVC non è valido.<br>';
	}

	if (errorMsg) {
		divError.innerHTML = errorMsg;
		divError.hidden = false;
		divError.classList.add('messaggio-errore');
		return false;
	} else {
		divError.innerHTML = '';
		divError.hidden = true;
		divError.classList.remove('messaggio-errore');
	}


	// La tua fetch...
	let params = `titolare=${encodeURIComponent(titolare)}&numeroCarta=${encodeURIComponent(numeroCarta)}&scadenza=${encodeURIComponent(scadenza)}&cvc=${encodeURIComponent(cvc)}`;

	fetch(`${path}/AddPaymentMethod`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: params
	})
		.then(response => response.json())
		.then(data => {
			if (data.result === 'success') {
				document.getElementById('errore').innerHTML = 'Metodo di pagamento aggiunto con successo!<br>Verrai reindirizzato al catalogo.';
				document.getElementById('errore').className = 'messaggio-successo';
				document.getElementById('errore').style.display = 'block';
				setTimeout(() => {
					window.location.href = `${path}/index.jsp`;
				}, 3000);
			} else {
				document.getElementById('errore').innerHTML = data.message;
				document.getElementById('errore').className = 'messaggio-errore';
				document.getElementById('errore').style.display = 'block';
			}
		})
		.catch(error => {
			console.error('Errore:', error);
			document.getElementById('errore').innerHTML = 'Errore di connessione. Riprova più tardi.';
			document.getElementById('errore').className = 'messaggio-errore';
			document.getElementById('errore').style.display = 'block';
		});

	return false;
}
