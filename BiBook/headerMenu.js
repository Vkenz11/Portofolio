function toggleMenu(menu) {
    menu.classList.toggle('open');
}

function toggleBar(){
    var x = document.getElementById('nmenu');
    if(x.style.display === 'none'){
        x.style.display = 'block';
    }
    else{
        x.style.display = 'none'
    }
}

function dark(){
    var x = document.getElementById('hide');
    if(x.style.display === 'none'){
        x.style.display = 'block';
    }
    else{
        x.style.display = 'none';
    }
}