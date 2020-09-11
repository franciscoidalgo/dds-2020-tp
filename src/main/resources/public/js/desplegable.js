function contenidoSeleccionadoEn(desplegable) {
    return desplegable.options[desplegable.selectedIndex];
}

function seleccionarValorPara(desplegable, posValor) {
    desplegable.selectedIndex = desplegable.options[posValor];
}

function contenidoDesplegableEs(desplegable, posibleMatch) {
    return contenidoSeleccionadoEn(desplegable).text === posibleMatch;
}

function quitarDelDesplegableSegunContenido(desplegable, nodoVictima) {
    puedeEliminar(desplegable, nodoVictima) ? desplegable.removeChild(nodoVictima) : "";
}

function puedeEliminar(desplegable, nodoVictima) {
    return desplegable.contains(nodoVictima) && nodoVictima.value !== "";
}

function tieneSeleccionables(desplegable) {

    return desplegable.options.length > 1;
}

export { contenidoSeleccionadoEn, seleccionarValorPara, contenidoDesplegableEs, quitarDelDesplegableSegunContenido, tieneSeleccionables };