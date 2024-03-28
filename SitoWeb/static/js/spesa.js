//   // Aggiungi un event listener alla lista della spesa
//   document.getElementById('shopping-list').addEventListener('click', function(event) {
//     // Verifica se l'elemento cliccato è un ingrediente (elemento <li>)
//     if (event.target.tagName === 'LI') {
//         // Chiama la funzione per barrare l'ingrediente
//         toggleIngredient(event.target);
//     }
// });

// Funzione per barrare o sbarrare un ingrediente
function toggleIngredient(ingredient) {
    ingredient.style.textDecoration = (ingredient.style.textDecoration === 'line-through') ? 'none' : 'line-through';
}

document.querySelectorAll('.toggle-ingrdient').forEach(function(button){
    button.addEventListener('click', function(){
        var ingredient = this.parentNode;
        toggleIngredient(ingredient);
    });
});

document.getElementById('clear-checked').addEventListener('click', function(){
    var ingredients = document.querySelectorAll('#shopping-list li');
    ingredients.forEach(function(ingredient){
        if (ingredient.style.textDecoration == 'line-through'){
            ingredient.remove();
        }
    });
});