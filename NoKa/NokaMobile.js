const menu = document.querySelector("#mobilemenu");
const menuList = document.querySelector(".navmenu");

menu.addEventListener('click', function() {
    menu.classList.toggle('isActive');
    menuList.classList.toggle('active');
})