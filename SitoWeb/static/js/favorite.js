document.addEventListener("DOMContentLoaded", function() {
    const favoriteButton = document.getElementById('favoriteButton');
    favoriteButton.addEventListener('click', function() {
      if (favoriteButton.classList.contains('favorite')) {
        favoriteButton.classList.remove('favorite');
      } else {
        favoriteButton.classList.add('favorite');
      }
      favoriteButton.classList.add('animate-heart');
      setTimeout(() => {
        favoriteButton.classList.remove('animate-heart');
      }, 300);


      const currentClass = favoriteButton.classList.value;
      const piattoId = favoriteButton.getAttribute('id_piatto');
      const username = favoriteButton.getAttribute('username');
        fetch('/web/updatePreferito?username='+username+'&piattoId='+piattoId, {
            method: 'GET'})
        })
        .then(response => {
            // Gestisci la risposta dal backend se necessario
            console.log('Richiesta al backend completata con successo');
        })
        .catch(error => {
            console.error('Si è verificato un errore durante la richiesta al backend:', error);
        });

        // httpRequest = XMLHttpRequest();
        // httpRequest.open("PUT", "/web/updatePreferito");
        // httpRequest.send(JSON.stringify({
        //   currentClass: currentClass,
        //   username: username,
        //   piattoId: piattoId
          
      // }));

    });

  