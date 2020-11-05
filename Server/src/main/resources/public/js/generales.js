/*Menu hamb*/
document.getElementById("btn-menu").onclick = () => {
    let menuHamb = document.getElementById("navbar");
    let btnHamb = document.getElementById("btn-menu");
    menuHamb.className = "navbar navbar-on";
    btnHamb.setAttribute("style", "opacity:.1;");

}

document.getElementById("btn-close").onclick = () => {
    let menuHamb = document.getElementById("navbar");
    let btnHamb = document.getElementById("btn-menu");
    menuHamb.className = "navbar";
    btnHamb.removeAttribute("style");
}

