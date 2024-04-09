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
    });
  });
  