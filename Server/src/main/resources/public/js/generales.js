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



function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}




document.getElementById("btn-close").onclick = ()=>ocultarMenu();

document.getElementById("btn-menu").onclick = ()=>mostrarMenu();


export{getCookie}

