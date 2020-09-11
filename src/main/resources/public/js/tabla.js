function agregateEnTablaSimple(tabla, descripcion) {
    var template = `
        <td>${descripcion}</td>
        <td>icono</td>
        `;

    var tBody = tabla.children[1]; /*0:thead|1:tbody*/
    var fila = document.createElement("tr");

    fila.innerHTML = template

    tBody.appendChild(fila);
}



export default agregateEnTablaSimple;