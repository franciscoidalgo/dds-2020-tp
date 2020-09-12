function agregateEnTablaSimple(tabla, descripcion) {
    let tBody = tabla.children[1]; /*0:thead|1:tbody*/
    let fila = document.createElement("tr");
    let id = `tr-${tBody.childElementCount}`;
    let template = `
            <td>${descripcion}</td>
            <td><i class="fas fa-minus-square" onclick="eliminarFila('${id}')"></i></td>
        `;
    fila.id = id;
    fila.innerHTML = template;
    tBody.appendChild(fila);
}

function tablaTieneElementos(tabla){
    let tBody = tabla.children[1]; /*0:thead|1:tbody*/
    return tBody.childElementCount>0;
}

export {agregateEnTablaSimple, tablaTieneElementos};