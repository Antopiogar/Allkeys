function check(sorgente) {
    let email = document.getElementById("email").value;
    let pass = document.getElementById("pass").value;
    let errore = "";
    let form = document.getElementById("formLogin");
    console.log(email + " " + pass);

    let isFocus = false;
    //controllo sui form con regex
    let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    // Controllo se i campi sono vuoti
    if (email === "" || !emailRegex.test(email)) {
        errore += "Email non può essere vuota o non valida<br>";
        document.getElementById("email").focus();
        isFocus = true;
    }
    if (pass === "") {
        errore += "Password non può essere vuota<br>";
        if (!isFocus) {
            document.getElementById("pass").focus();
            isFocus = true;
        }
    }
    if (errore !== "") {
        document.getElementById("errorInfo").className = "messaggio-errore";
        document.getElementById("errorInfo").innerHTML = errore;
        return;
    }
    else {
        // Se i campi sono validi, invia il form
        if (sorgente === 'user')
            form.action = "loginServlet";
        else
            form.action = "LoginAdminServlet";
        console.log("Form is valid, submitting...");
        document.getElementById("errorInfo").className = "messaggio-successo";
        document.getElementById("errorInfo").innerHTML = "Login in corso...";
        form.submit();
    }
}