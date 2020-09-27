function generaTextbox(etiqueta){
    let nodoTextbox = document.createElement("input");

    nodoTextbox.id = etiqueta.id;
    nodoTextbox.name = etiqueta.name;
    nodoTextbox.setAttribute("type","text");

    nodoTextbox.required;
    nodoTextbox.placeholder = "Ingrese nuevo...";
    nodoTextbox.innerText = "";

    nodoTextbox.style.setProperty("min-width","41.8rem");
    nodoTextbox.style.setProperty("margin","1rem 0");

    return nodoTextbox;
}

export default generaTextbox;