"use strict"

function register() {
    let email = document.getElementById("email").value.trim();
    let errorDiv = document.getElementById("error");
    let regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!regexEmail.test(email)) {
        errorDiv.hidden = false;
        document.getElementById("error2").hidden = false;
        errorDiv.classList.add('messaggio-errore');
        errorDiv.classList.remove('messaggio-successo');
        errorDiv.innerHTML = "L'email inserita non è valida.";
        document.getElementById("email").focus();
        return false;
    }
    console.log("Email to check:", email);

    fetch(`./CheckEmailServlet`, {
        method: 'post',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({ email: email })
    })
        .then(response => response.json())
        .then(jsonData => {
            console.log("Response from CheckEmailServlet:", jsonData);
            if (jsonData["exists"] === true) {
                document.getElementById("error").hidden = false;
                document.getElementById("error").innerHTML = "Email già registrata. Inserisci un'altra email.";
                document.getElementById("email").focus();
            } else {
                let registerForm = document.getElementById("form");
                if (checkForm()) {
                    registerForm.submit();
                }
            }
        })
        .catch(error => {
            console.error("Error during registration:", error);
            alert("Errore durante la registrazione. Riprova più tardi.");
        });
}

function checkForm() {
    let nome = document.getElementById('nome').value.trim();
    let cognome = document.getElementById('cognome').value.trim();
    let dataN = document.getElementById('dataN').value;
    let cf = document.getElementById('cf').value.trim();
    let email = document.getElementById('email').value.trim();
    let password = document.getElementById('password').value;
    let divError = document.getElementById('error');
    let errorMsg = '';
    let focusSet = false;
    let expressionNome = /^[A-Za-zÀ-ÿ\s]+$/;
    if (!expressionNome.test(nome)) {
        document.getElementById('nome').focus();
        focusSet = true;
        errorMsg += 'Il nome non è valido.<br>';
    }
    if (!expressionNome.test(cognome)) {
        if (!focusSet) {
            document.getElementById('cognome').focus();
            focusSet = true;
        }
        errorMsg += 'Il cognome non è valido.<br>';
    }

    let today = new Date();
    let birthDate = new Date(dataN);
    if (!dataN || birthDate >= today) {
        if (!focusSet) {
            document.getElementById('dataN').focus();
            focusSet = true;
        }
        errorMsg += 'La data di nascita non è valida.<br>';
    }

    let expressionCF = /^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$/i;
    if (!expressionCF.test(cf)) {
        if (!focusSet) {
            document.getElementById('cf').focus();
            focusSet = true;
        }
        errorMsg += 'Il codice fiscale non è valido.<br>';
    }

    let expressionEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!expressionEmail.test(email)) {
        if (!focusSet) {
            document.getElementById('email').focus();
            focusSet = true;
        }
        errorMsg += 'L\'email non è valida.<br>';
    }

    let expressionPassword = /^(?=.*[A-Za-z])(?=.*\d).{6,}$/;
    if (!expressionPassword.test(password)) {
        if (!focusSet) {
            document.getElementById('password').focus();
            focusSet = true;
        }
        errorMsg += 'La password deve contenere almeno 6 caratteri, di cui almeno una lettera e un numero.<br>';
    }

    divError.hidden = false;
    if (errorMsg) {
        document.getElementById("error2").hidden = false;
        divError.innerHTML = errorMsg;
        divError.classList.add('messaggio-errore');
        return false;
    } else {
        divError.hidden = true;
        return true;
    }
}
