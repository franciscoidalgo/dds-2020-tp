function agregateContenidoEnTablaSimple(tabla, descripcion,tipo,cantidad,precioUnitario,item) {
    let classDelete = "fas fa-minus-square";
    let icono = document.createElement("i");
    let fila = tabla.children[1].insertRow(-1);

    console.log(tipo)
    console.log(cantidad)
    addFilaInnerHTML(fila,descripcion,0)
    addFilaInnerHTML(fila,tipo,1)
    addFilaInnerHTML(fila,cantidad,2)
    addFilaInnerHTML(fila,precioUnitario,3)

    icono.className = classDelete;
    fila.insertCell(4).appendChild(icono);

    icono.onclick = ()=>{eliminarFila(fila,item)};
}

function tablaTieneElementos(tabla){
    let tBody = tabla.children[1]; /*0:thead|1:tbody*/
    return tBody.childElementCount>0;
}

function addFilaInnerHTML(fila,descripcion,posicion){
    let cell = fila.insertCell(posicion);
    cell.innerHTML=descripcion;
}

export {agregateContenidoEnTablaSimple, tablaTieneElementos};