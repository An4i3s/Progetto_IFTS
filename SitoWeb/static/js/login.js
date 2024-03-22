const wrapper = document.querySelector('.wrapper');
const loginLink = document.querySelector('.login-link');
const registerLink = document.querySelector('.register-link');
const btnPopup = document.querySelector('.btnLogin-popup');
const iconClose = document.querySelector('.icon-close');

const hiddenTab = document.querySelector('.hidden-tab')


registerLink.addEventListener('click', ()=> {
    wrapper.classList.add('active');
    hiddenTab.style.display = 'none';

});
loginLink.addEventListener('click', ()=> {
    wrapper.classList.remove('active');
    hiddenTab.style.display = 'flex'; // Cambiamo il display a 'block' per mostrare i quadri
  });
btnPopup.addEventListener('click', ()=> {
    wrapper.classList.add('active-popup');
    hiddenTab.style.display = 'grid';

});
iconClose.addEventListener('click', ()=> {
    wrapper.classList.remove('active-popup');
    hiddenTab.style.display = 'grid';

});


