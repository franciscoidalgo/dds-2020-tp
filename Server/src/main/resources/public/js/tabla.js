function agregateFilaEnTablaSimpleConBorrador(tabla, descripcion, tipo, cantidad, precioUnitario, item) {
    let classDelete = "fas fa-minus-square";
    let icono = document.createElement("i");
    let fila = agregateFilaEnTablaDetalleSimple(tabla, descripcion,tipo,cantidad,precioUnitario);

    icono.className = classDelete;
    fila.insertCell(5).appendChild(icono);

    icono.onclick = ()=>{eliminarFila(fila,item)};
}

function agregateFilaEnTablaDetalleSimple(tabla, descripcion,tipo,cantidad,precioUnitario) {
    let fila = tabla.children[1].insertRow(-1);

    console.log(tipo)
    console.log(cantidad)
    addFilaInnerHTML(fila,descripcion,0)
    addFilaInnerHTML(fila,tipo,1)
    addFilaInnerHTML(fila,cantidad,2)
    addFilaInnerHTML(fila,precioUnitario,3)
    addFilaInnerHTML(fila,cantidad*precioUnitario,4)
    return fila;
}

function tablaTieneElementos(tabla){
    let tBody = tabla.children[1]; /*0:thead|1:tbody*/
    return tBody.childElementCount>0;
}

function addFilaInnerHTML(fila,descripcion,posicion){
    let cell = fila.insertCell(posicion);
    cell.innerHTML=descripcion;
}

function crearTablaIngresos(data){
    let tablaIngresos = document.createElement("table");
    let tablaHeader = document.createElement("thead");
    let tablaBody = document.createElement("tbody");
    let tr = document.createElement("tr");

    tablaIngresos.className = "txt-centrado tabla";
    tablaHeader.className = "bg-primario fw-700 th-principal";

    tablaIngresos.appendChild(tablaHeader);
    tablaHeader.appendChild(tr);

    settearEncabezado(tr,"#ID")
    settearEncabezado(tr,"Descripcion")
    settearEncabezado(tr,"Fecha Realizada")
    settearEncabezado(tr,"Fecha Aceptabilidad")
    settearEncabezado(tr,"Monto")
    //TODO SALDO?
    tablaIngresos.appendChild(tablaBody);
    for(let i = 0 ; i < data.length;i++){
        settearFilaIngresos(tablaBody,data[i]);
    }
    return tablaIngresos;
}

function crearTablaEgresos(data){
    let tablaIngresos = document.createElement("table");
    let tablaHeader = document.createElement("thead");
    let tablaBody = document.createElement("tbody");
    let tr = document.createElement("tr");

    tablaIngresos.className = "txt-centrado tabla";
    tablaHeader.className = "bg-primario fw-700 th-principal";

    tablaIngresos.appendChild(tablaHeader);
    tablaHeader.appendChild(tr);

    settearEncabezado(tr,"#ID")
    settearEncabezado(tr,"Razon Social")
    settearEncabezado(tr,"Fecha")
    settearEncabezado(tr,"Necesita Presupuestos")
    settearEncabezado(tr,"Monto")
    //TODO SALDO?
    tablaIngresos.appendChild(tablaBody);
    for(let i = 0 ; i < data.length;i++){
        settearFilaEgreso(tablaBody,data[i]);
    }
    return tablaIngresos;
}

function settearEncabezado(tr,descripcion){
    let th = document.createElement("th");
    tr.appendChild(th);
    th.innerText = descripcion;
}

function settearFilaEgreso(tbody,data){
    let tr = document.createElement("tr");

    let td = `  <td>${data.id}</td>
                <td>${data.detalle.proveedor.razonSocial}</td>
                <td>${data.fecha.day}-${data.fecha.month}-${data.fecha.year}</td>
                <td>${data.cantPresupuestos}</td>
                <td>${data.montoTotal}</td>`
    tr.innerHTML = td;
    tbody.appendChild(tr);
}

function settearFilaIngresos(tbody,data){
    let tr = document.createElement("tr");

    let td = `  <td>${data.id}</td>
                <td>${data.descripcion}</td>
                <td>${data.fechaRealizada.day}-${data.fechaRealizada.month}-${data.fechaRealizada.year}</td>
                <td>${data.fechaAceptacion.day}-${data.fechaAceptacion.month}-${data.fechaAceptacion.year}</td>
                <td>${data.monto}</td>`
    tr.innerHTML = td;
    tbody.appendChild(tr);
}






export {agregateFilaEnTablaSimpleConBorrador, tablaTieneElementos,crearTablaIngresos,crearTablaEgresos,agregateFilaEnTablaDetalleSimple};