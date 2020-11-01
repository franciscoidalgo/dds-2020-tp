function contenidoSeleccionadoEn(desplegable) {
    return desplegable.options[desplegable.selectedIndex];
}

function seleccionarValorPara(desplegable, posValor) {
    desplegable.selectedIndex = desplegable.options[posValor];
}

function contenidoDesplegableEs(desplegable, posibleMatch) {
    return contenidoSeleccionadoEn(desplegable).text === posibleMatch;
}

function sacarDelDesplegableEscondiendo(desplegable, nodoVictima) {
    puedeEliminar(desplegable, nodoVictima) ? desplegable.removeChild(nodoVictima) : "";
}

function puedeEliminar(desplegable, nodoVictima) {
    return desplegable.contains(nodoVictima) && nodoVictima.value !== "";
}

function tieneSeleccionables(desplegable) {
    return desplegable.options.length > 1;
}

function agregaContenidoEnDesplegable(desplegable,contenido,setSeleccion){
    let option = document.createElement("option");
    option.value=contenido;
    option.id=contenido;
    option.innerText=contenido;
    desplegable.appendChild(option);
    option.selected = setSeleccion;
}

function cleanDesplegable(desplegable){
    desplegable.innerHTML = ""
    agregaContenidoEnDesplegable(desplegable,"-- Seleccione --",true);
    desplegable.children[0].disable=true;



}
export { contenidoSeleccionadoEn,
    seleccionarValorPara,
    contenidoDesplegableEs,
    sacarDelDesplegableEscondiendo,
    tieneSeleccionables,
    agregaContenidoEnDesplegable,
    cleanDesplegable };