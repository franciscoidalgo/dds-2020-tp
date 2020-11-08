/*Menu hamb*/

function mostrarMenu(e){
    let menuHamb = document.getElementById("navbar");
    let btnHamb = document.getElementById("btn-menu");
    menuHamb.className = "navbar navbar-on";
    btnHamb.setAttribute("style", "opacity:.1;");
}

function ocultarMenu(e){
    let menuHamb = document.getElementById("navbar");
    let btnHamb = document.getElementById("btn-menu");
    menuHamb.className = "navbar";
    btnHamb.removeAttribute("style");
}

document.getElementById("btn-close").onclick = ()=>ocultarMenu();

document.getElementById("btn-menu").onclick = ()=>mostrarMenu();




