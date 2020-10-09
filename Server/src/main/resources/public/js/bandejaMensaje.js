import generaCategoria from './categoria.js';
/* Genero mock de Jsons */
function mockJson(cantMensajes) {
    var data = [];
    var object = {};
    const proveedores = ["Pedro", "Juan", "Jose", "Simon", "Maria", "Flor", "Caro", "Fer"];
    const moneda = ["US", "AR", "YU", "YE", "RU", "RE", "EU", "UR"];
    const razonSocial = ["una SA", "una SRL", "una Organizacion"];
    const tipoComprobante = ["Ticket", "Factura", "Recibo"];
    const msjValido = ["Cantidad Presupuesto: Valida", "Solicitud Coinciden Con Presupuesto: Valida", "Seleccion Presupuesto: Valida"];
    const msjInvalido = ["Cantidad Presupuesto: Invalida", "Solicitud Coinciden Con Presupuesto: Invalida", "Seleccion Presupuesto: Invalida"];

    for (var i = 0; i < cantMensajes; i++) {
        var resValidacion = getRandomInt(0, 2)
        data.push({
            "resultadoValidacion": resValidacion,
            "cuerpoMensaje": resValidacion ? msjValido : msjInvalido,
            "visto": getRandomInt(0, 2),
            "egreso": {
                "id": getRandomInt(0, 99999),
                "fecha": getRandomDate(),
                "proveedor": {
                    "razonSocial": razonSocial[getRandomInt(0, razonSocial.length)],
                    "cuit": "99-999999-9",
                    "nombre": proveedores[getRandomInt(0, proveedores.length)],
                    "pais": "Argentina",
                    "provincia": "Buenos Aires",
                    "ciudad": "San Fernando",
                    "calle": "una calle",
                    "altura": 1234,
                    "piso": 1,
                    "dpto": "b"
                },
                "detalle": {
                    "items": ["producto", "producto", "producto", "producto", "producto", "producto"],

                },
                "medioDePago": {
                    "moneda": moneda[getRandomInt(0, moneda.length)],
                    "monto": getRandomInt(0, 99999),
                    "comprobante": {
                        "tipo": tipoComprobante[getRandomInt(0, tipoComprobante.length)],
                        "path": "./zaraza/zaraza.jpg"
                    }
                },
                "presupuestos": {
                    "id": [1, 2, 3, 4]
                },
                "categorias": ["categoria", "categoria", "categoria", "categoria", "categoria", "categoria", "categoria", "categoria"]
            }
        });
    }

    object.data = data;
    return JSON.stringify(object);
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

function randomDate(start, end) {
    return new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
}

function getRandomDate() {
    const date = randomDate(new Date(2012, 0, 1), new Date());
    return `${date.getDate()}-${date.getMonth()+1}-${date.getFullYear()}`
}

/*********************************************************************/
const dataJson = JSON.parse(mockJson(45)); //Simulacion de la data que traeria en un AJAX
var countRender = 0;
var btnSiguiente = document.getElementById("siguiente");
var btnAnterior = document.getElementById("anterior");

/* Funciones */
function buildFila(data) {
    const tbodyMensajes = document.getElementById("seccion-mensajes");
    var tr = document.createElement("tr");
    const egreso = data.egreso;
    setDataARow(tr, egreso.id);
    setDataARow(tr, egreso.proveedor.razonSocial);
    setDataARow(tr, egreso.proveedor.nombre);
    setDataARow(tr, `${egreso.medioDePago.moneda} $${egreso.medioDePago.monto}`);
    setDataARow(tr, egreso.fecha);
    setDataARow(tr, setIconoValidoSegun(data.resultadoValidacion));

    if (!data.visto) {
        tr.className += "fw-700"
    }

    tr.onclick = () => { mostrarMensaje(data) };
    tbodyMensajes.appendChild(tr);
}

function setIconoValidoSegun(condicion) {
    return condicion ?
        '<i class="fas fa-check-circle valido"></i>' :
        '<i class="fas fa-exclamation-circle invalido"></i>';
}

function setDataARow(tr, data) {
    var tdHTML = document.createElement("td");

    tdHTML.innerHTML = data;
    tr.appendChild(tdHTML);
}

function orderByID(a, b) {
    return a.egreso.id - b.egreso.id;
}

function buildTemplateMensaje(data) {
    const contenedorHTML = document.getElementById("mensaje-detalle");
    const egreso = data.egreso;
    const proveedor = egreso.proveedor;
    const medioDePago = egreso.medioDePago;
    const template = `
        <header>
            <div class="d-flex jc-sb ai-center fw-700">
                <div class="tooltip">   
                    <h2>Egreso #${egreso.id}${setIconoValidoSegun(data.resultadoValidacion)}</h2>
                    <div id="mensaje-resultado" class="tooltiptext tooltiptext-${data.resultadoValidacion?"valida":"invalida"}">
                    </div>
                </div>
                <p>Fecha: ${egreso.fecha}</p> 
            </div>
            <div id='contenedor-categorias' class="d-flex contenedor-categorias">
            </div>
        </header>  
        <main>
            <section>
                <h3>Informacion del Proveedor</h3>
                <div class="d-flex jc-sb">  
                    <div>
                        <p><span>CUIT/DNI:</span> ${proveedor.cuit}</p>
                        <p><span>Nombre vendedor:</span> ${proveedor.nombre}</p>
                        <p><span>Razon Social:</span> ${proveedor.razonSocial}</p>
                    </div>
                    <div>
                        <p><span>Region:</span> ${proveedor.pais}, ${proveedor.provincia}, ${proveedor.ciudad}</p>
                        <p><span>Direccion:</span> ${proveedor.calle} ${proveedor.altura}, ${proveedor.piso} ${proveedor.dpto}</p>
                    </div>
                </div>
            </section>
            <section>
                <h3>Detalle de la operacion</h3>
                <p><span>Monto total:</span> ${medioDePago.moneda} $${medioDePago.monto}</p>
                <p><span>Tipo comprobante:</span> ${medioDePago.comprobante.tipo}</p>
                <p><span>Comprobante</span>: <a id="ver-comprobante" href=${medioDePago.comprobante.path} target="blank">ver comprobante</a></p>
                <p><span>Cantidad Presupuestos: </span>${egreso.presupuestos.id.length}</p>
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
        contenido.innerHTML = vectorItems[i];
        tr.appendChild(contenido);
        contenedorHTML.appendChild(tr);
    }
}

function buildTooltip(vectorMsjResultado) {
    const contenedorHTML = document.getElementById("mensaje-resultado");
    contenedorHTML.scrollIntoView({ behavior: "smooth" });
    for (var i = 0; i < vectorMsjResultado.length; i++) {
        let contenido = document.createElement("p");
        contenido.innerHTML = vectorMsjResultado[i];
        contenedorHTML.appendChild(contenido);
    }
}

function mostrarMensaje(data) {
    const categorias = data.egreso.categorias;
    const items = data.egreso.detalle.items;
    const msjResultado = data.cuerpoMensaje;

    buildTemplateMensaje(data);
    buildCategorias(categorias);
    buildTablaDetalle(items);
    buildTooltip(msjResultado);

}

function renderTabla(data) {
    var paginaHTML = document.getElementById("cant-paginas");
    var data = dataJson.data.sort(orderByID); //Ordenado por id

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


window.onload = () => renderTabla(dataJson);

btnSiguiente.onclick = () => renderTabla(dataJson)

btnAnterior.onclick = () => {
    countRender -= 2 + ((countRender - 1) % 10); /****************VER ACA */
    renderTabla(dataJson);
}