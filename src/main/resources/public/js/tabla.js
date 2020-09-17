function agregateEnTablaSimple(tabla, descripcion) {
    let classDelete = "fas fa-minus-square";
    let icono = document.createElement("i");
    let fila = tabla.children[1].insertRow(-1);
    let cell = fila.insertCell(0);

    cell.innerHTML=descripcion;

    icono.className = classDelete;
    cell = fila.insertCell(1);
    cell.appendChild(icono);

    icono.onclick = ()=>{eliminarFila(fila)};
}

function tablaTieneElementos(tabla){
    let tBody = tabla.children[1]; /*0:thead|1:tbody*/
    return tBody.childElementCount>0;
}

export {agregateEnTablaSimple, tablaTieneElementos};