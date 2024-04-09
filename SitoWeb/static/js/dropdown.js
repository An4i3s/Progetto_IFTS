// Script per il menu dropdown
var dropdownBtn = document.getElementById("dropdownBtn");
var dropdownContent = document.getElementById("dropdownContent");

dropdownBtn.addEventListener("click", function(event) {
    event.stopPropagation(); // Evita la propagazione dell'evento al click sul body
    dropdownContent.classList.toggle("show");
});

// Chiudi il dropdown menu quando si fa clic fuori
window.addEventListener("click", function(event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
});

// Script per il popup di accesso
var loginButton = document.getElementById("loginButton");
var loginPopup = document.getElementById("loginPopup");

// Ottieni l'elemento di chiusura del popup
var closePopup = document.getElementById("closePopup");

// Mostra il popup quando viene cliccato il pulsante di accesso
loginButton.onclick = function() {
    loginPopup.style.display = "block";
}

// Nascondi il popup quando viene cliccato il pulsante di chiusura
closePopup.onclick = function() {
    loginPopup.style.display = "none";
}

// Nascondi il popup quando viene cliccata qualsiasi parte esterna del popup
window.onclick = function(event) {
    if (event.target == loginPopup) {
        loginPopup.style.display = "none";
    }
}
