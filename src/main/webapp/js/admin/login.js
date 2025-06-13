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
    if(email === "" || !emailRegex.test(email)) {
        errore += "<br><br>Email non può essere vuota o non valida";
        document.getElementById("email").focus();
        isFocus = true;
    }
    if(pass === "" ) {
        errore += "<br><br>Password non può essere vuota";
        if(!isFocus) {
            document.getElementById("pass").focus();
            isFocus = true;
        }
    }
    if(errore !== ""){
		let errorInfoDiv = document.getElementById("errorInfo");
		    if (errorInfoDiv) {
		        errorInfoDiv.style.display="none";
		    }
        document.getElementById("errore").className = "messaggio-errore";
        document.getElementById("errore").innerHTML = errore;
        return;
    }
    else {
        // Se i campi sono validi, invia il form
        if(sorgente==='user')
            form.action = "loginServlet";
        else
            form.action = "LoginAdminServlet";
			let errorInfoDiv = document.getElementById("errorInfo");
			    if (errorInfoDiv) {
			        errorInfoDiv.style.display="none";
			    }
        	console.log("Form is valid, submitting...");
        	document.getElementById("errore").className = "messaggio-successo";
        	document.getElementById("errore").innerHTML = "<br><br>Login in corso...";
        	form.submit();
    }
}