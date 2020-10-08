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
                "categorias": ["xxx", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx"]
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
const dataJson = JSON.parse(mockJson(20)); //Simulacion de la data que traeria en un AJAX

/* Funciones */
function generarFila(data) {
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

function templateMensaje(data) {
    const egreso = data.egreso;
    const proveedor = egreso.proveedor;
    const medioDePago = egreso.medioDePago;
    const msj = `
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
                <p><span>Comprobante: </span>${medioDePago.comprobante.path}</p>
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
    return msj;
}

function mostrarMensaje(data) {
    const categorias = data.egreso.categorias;
    const items = data.egreso.detalle.items;
    const msjResultado = data.cuerpoMensaje;
    var contenedorHTML = document.getElementById("mensaje-detalle");
    var contenido = templateMensaje(data);

    //Asocio el Template al contenedor Mensaje
    contenedorHTML.innerHTML = contenido;
    //Asocio las categorias al Mensaje
    contenedorHTML = document.getElementById("contenedor-categorias");
    for (var i = 0; i < categorias.length; i++) {
        contenido = generaCategoria(categorias[i], true);
        contenedorHTML.appendChild(contenido);
    }
    //Asocio las Items del Egreso al Mensaje
    contenedorHTML = document.getElementById("tabla-detalle");
    for (var i = 0; i < items.length; i++) {
        var tr = document.createElement("tr");
        contenido = document.createElement("td");
        contenido.innerHTML = items[i];
        tr.appendChild(contenido);
        contenedorHTML.appendChild(tr);
    }
    contenedorHTML.scrollIntoView({ behavior: "smooth" });
    //Asocio los mensajes al TOOLTIP
    contenedorHTML = document.getElementById("mensaje-resultado");
    for (var i = 0; i < msjResultado.length; i++) {
        contenido = document.createElement("p");
        contenido.innerHTML = msjResultado[i];
        contenedorHTML.appendChild(contenido);
    }

}

/* Eventos */
window.onload = () => {
    var data = dataJson.data.sort(orderByID); //Ordenado por id

    for (var i = 0; i < data.length; i++) {
        generarFila(data[i]);

    }
}