/*Menu hamb*/
document.getElementById("btn-menu").onclick = () =>{
    var menuHamb =document.getElementById("navbar");
    var btnHamb =document.getElementById("btn-menu");
    menuHamb.className = "navbar navbar-on";
    btnHamb.setAttribute("style", "opacity:.1;");

}
document.getElementById("btn-close").onclick = () =>{
    var menuHamb =document.getElementById("navbar");
    var btnHamb =document.getElementById("btn-menu");
    menuHamb.className = "navbar";
    btnHamb.removeAttribute("style");
}