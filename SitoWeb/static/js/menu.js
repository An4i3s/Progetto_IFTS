function toggleDropdown(event, blockNumber) {
    var dropdown = document.getElementById("dropdown" + blockNumber);
    dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";

    // Chiudi eventuali dropdown annidate
    var nestedDropdowns = document.querySelectorAll('.nested-dropdown');
    nestedDropdowns.forEach(function(item) {
        item.style.display = 'none';
    });

    event.stopPropagation(); // Evita la propagazione del click al body
}

function toggleNestedDropdown(event, blockNumber, nestedDropdownNumber) {
    var nestedDropdown = document.getElementById("nested-dropdown-" + blockNumber + "-" + nestedDropdownNumber);
    nestedDropdown.style.display = (nestedDropdown.style.display === "block") ? "none" : "block";

    event.stopPropagation(); // Evita la propagazione del click al body
}
