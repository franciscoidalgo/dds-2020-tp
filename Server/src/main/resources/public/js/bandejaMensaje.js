import generaCategoria from './categoria.js';

var countRender = 0;
var btnSiguiente = document.getElementById("siguiente");
var btnAnterior = document.getElementById("anterior");

/* Funciones */
function buildFila(data) {
    const tbodyMensajes = document.getElementById("seccion-mensajes");
    let tr = document.createElement("tr");

    //Discriminador
    let asuntoSeparado = data.asunto.split("--")
    let idEgreso = asuntoSeparado[0].slice(8);
    let esValida = true;

    for(let i = 0; i <asuntoSeparado.length;i++){
        let asunto = asuntoSeparado[i].toLocaleLowerCase()
        esValida = "valida" === asuntoSeparado[i].toLocaleLowerCase()
        esValidacion(asunto)?
            setDataARow(tr, setIconoValidoSegun(esValida)):
            setDataARow(tr,asuntoSeparado[i]);
    }

    tr.className = "txt-left"
    tr.onclick = () => {
        if(idEgreso!== ""){
        getMensajeDesdeApi(idEgreso,esValida,data.mensaje)

        }else{
            //render mensajeComun
        }
    };
    tbodyMensajes.appendChild(tr);
    if(asuntoSeparado.length === 1){
        let mensajeGenerico = tr.firstChild;
        mensajeGenerico.setAttribute("colspan","6");
        mensajeGenerico.className = "txt-centrado";
    }
}
function esValidacion (cadena){

    return "valida" === cadena || "invalida" === cadena;
}


function setDataARow(tr, data) {
    var tdHTML = document.createElement("td");

    tdHTML.innerHTML = data;
    tr.appendChild(tdHTML);
}

function setIconoValidoSegun(cadena) {
    return cadena ?
        '<i class="fas fa-check-circle valido"></i>' :
        '<i class="fas fa-exclamation-circle invalido"></i>';
}

function orderByID(a, b) {
    return a.egreso.id - b.egreso.id;
}

function buildTemplateMensaje(egreso,esValida) {
    const contenedorHTML = document.getElementById("mensaje-detalle");
    const proveedor = egreso.detalle.proveedor;
    const direccion = proveedor.dirPostal;
    //const medioDePago = egreso.medioDePago;
    const template = `
        <header>
            <div class="d-flex jc-sb ai-center fw-700">
                <div class="tooltip">   
                    <h2>Egreso #${egreso.id}${setIconoValidoSegun(esValida)}</h2>
                    <div id="mensaje-resultado" class="tooltiptext tooltiptext-${esValida?"valida":"invalida"}">
                    </div>
                </div>
                <p>Fecha: ${egreso.fecha.day}-${egreso.fecha.month}-${egreso.fecha.year}</p> 
            </div>
            <div id='contenedor-categorias' class="d-flex contenedor-categorias">
            </div>
        </header> 
        <main>
            <section>
                <h3>Informacion del Proveedor</h3>
                <div class="d-flex jc-sb">  
                    <div>
                        <p><span>CUIT/DNI:</span> ${proveedor.CUIT}</p>
                        <p><span>Nombre vendedor:</span> ${proveedor.nombre}</p>
                        <p><span>Razon Social:</span> ${proveedor.razonSocial}</p>
                    </div>
                    <div>
                        <p><span>Region:</span> ${direccion.pais}, ${direccion.provincia}, ${direccion.ciudad}</p>
                        <p><span>Direccion:</span> ${direccion.calle} ${direccion.altura}, ${direccion.piso} ${direccion.dpto}</p>
                    </div>
                </div>
            </section>
            <section>
                <h3>Detalle de la operacion</h3>
                <p><span>Monto total:</span> ${/*medioDePago.moneda*/"Monto"} $${egreso.montoTotal}</p>
                <p><span>Tipo comprobante:</span> ${/*medioDePago.comprobante.tipo*/"tipo comprobante"}</p>
                <p><span>Comprobante</span>: <a id="ver-comprobante" href=${/*medioDePago.comprobante.path*/"tipo comprobante"} target="blank">ver comprobante</a></p>
                <p><span>Cantidad Presupuestos: </span>${egreso.presupuestos.length}</p>
            </section>
            <table class="txt-centrado tabla">
                <caption class="fw-700">Servicios/Productos</caption>
                <thead class="bg-primario fw-700 th-principal">
                    <tr>
                        <th>Descripcion</th>
                    </tr>
                </thead>
                <tbody id="tabla-detalle" class="tabla-detalle">
                </tbody>
            </table>
        </main>    
    `
    contenedorHTML.innerHTML = template;
}

function buildCategorias(vectorCategorias) {
    const contenedorHTML = document.getElementById("contenedor-categorias");

    for (var i = 0; i < vectorCategorias.length; i++) {
        let contenido = generaCategoria(vectorCategorias[i], true);
        contenedorHTML.appendChild(contenido);
    }
}

function buildTablaDetalle(vectorItems) {
    let contenedorHTML = document.getElementById("tabla-detalle");

    for (var i = 0; i < vectorItems.length; i++) {
        let tr = document.createElement("tr");
        let contenido = document.createElement("td");
        contenido.innerHTML = vectorItems[i].descripcion;
        tr.appendChild(contenido);
        contenedorHTML.appendChild(tr);
    }
}

function buildTooltip(resultadoMensaje) {
    const contenedorHTML = document.getElementById("mensaje-resultado");
    let vectorMsjResultado = resultadoMensaje.split("\n");
    contenedorHTML.scrollIntoView({ behavior: "smooth" });
    for (var i = 0; i < vectorMsjResultado.length; i++) {
        let contenido = document.createElement("p");
        contenido.innerHTML = vectorMsjResultado[i];
        contenedorHTML.appendChild(contenido);
    }
}

function mostrarMensaje(egreso,esValida,detalleValidacion) {
    const detalle = egreso.detalle;

    //const msjResultado = data.cuerpoMensaje;

    buildTemplateMensaje(egreso,esValida);
    buildCategorias(detalle.categorias);
    buildTablaDetalle(detalle.items);
    buildTooltip(detalleValidacion);

}

function renderTabla(data) {
    var paginaHTML = document.getElementById("cant-paginas");
    cleanTabla();

    for (var i = countRender, j = 10; j > 0 && i < data.length; i++, j--) {
        buildFila(data[i]);
        countRender = i + 1;
    }

    paginaHTML.innerText = setToStringCantPaginas(data.length);
}

function cleanTabla() {
    const tbody = document.getElementById("seccion-mensajes");
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }
}

function setToStringCantPaginas(size) {
    if (size == 0) {
        return "0 de 0"
    }
    return size > countRender ?
        `${1+countRender-10}-${countRender} de ${size}` : `${1+countRender-size%10}-${size} de ${countRender}`
}
/* Eventos */

window.onload = () => {
    //renderTabla(dataJson)
    var url = "/getMensajes";
    fetch(url)
        .then(response => response.json())
        .then(data => renderTabla(data))
        .catch(reason => console.log(reason));
};

function getMensajeDesdeApi(id,esValida,detalleValidacion){
    var url = "/api/get-egreso/"+id;
    console.log(url);
    fetch(url)
        .then(response => response.json())
        .then(data => mostrarMensaje(data,esValida,detalleValidacion))
        .catch(reason => console.log(reason));
}


window.onload = () => {
    //renderTabla(dataJson)
    var url = "/getMensajes";
    fetch(url)
        .then(response => response.json())
        .then(data => renderTabla(data))
        .catch(reason => console.log(reason));
};

btnSiguiente.onclick = () => renderTabla(dataJson)

btnAnterior.onclick = () => {
    countRender -= 2 + ((countRender - 1) % 10); /****************VER ACA */
    renderTabla(dataJson);
}