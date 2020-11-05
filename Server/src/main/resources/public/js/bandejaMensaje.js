import {generaCategoria} from './generales/categoria.js';
import {agregateFilaEnTablaDetalleSimple} from "./generales/tabla.js";
import {esconderLoader, mostrarLoader} from "./generales/loader.js";
import {generarModalOK} from "./generales/modal.js";


const boton = {
    siguiente: document.getElementById("siguiente"),
    anterior: document.getElementById("anterior")
}
let countRender = 0;
let cantMaxima = 0;
let dataMensajes = {};

const MENSAJE_REVISOR = "Dejaste de ser revisor de esta operacion. Puedes volver a seleccionarte como revisor, en el panel de busqueda"

/* Funciones */
function fueLeido(dataMensaje) {
    console.log(dataMensaje.fechaEnvio.year);
    console.log(dataMensaje.fechaLeido.year);
    console.log(dataMensaje.fechaEnvio);
    console.log(dataMensaje.fechaLeido);

    return dataMensaje.fechaEnvio.year ===  dataMensaje.fechaLeido.year &&
        dataMensaje.fechaEnvio.month ===  dataMensaje.fechaLeido.month &&
        dataMensaje.fechaEnvio.day ===  dataMensaje.fechaLeido.day &&
        dataMensaje.horaEnvio.hour === dataMensaje.horaLeido.hour &&
        dataMensaje.horaEnvio.minute === dataMensaje.horaLeido.minute &&
        dataMensaje.horaEnvio.second === dataMensaje.horaLeido.second ;

}

function buildFila(data) {
    const tbodyMensajes = document.getElementById("seccion-mensajes");
    let tr = document.createElement("tr");

    //Discriminador
    let asuntoSeparado = data.asunto.split("--")
    let idEgreso = asuntoSeparado[0].slice(8);
    let esValida = true;
    for (let i = 0; i < asuntoSeparado.length; i++) {
        let asunto = asuntoSeparado[i].toLocaleLowerCase()
        esValida = "valida" === asuntoSeparado[i].toLocaleLowerCase()
        esValidacion(asunto) ?
            setDataARow(tr, setIconoValidoSegun(esValida)) :
            setDataARow(tr, asuntoSeparado[i]);
    }

    tr.className = "txt-left"
    tr.style.fontWeight = fueLeido(data) ? "700" : "400";
    tr.onclick = () => {
        if (idEgreso !== "") {
            getMensajeDesdeApi(idEgreso, esValida, data.mensaje,data.id)
            tr.style.fontWeight = "400";

        } else {
            //render mensajeComun
        }
    };
    tbodyMensajes.appendChild(tr);
    if (asuntoSeparado.length === 1) {
        let mensajeGenerico = tr.firstChild;
        mensajeGenerico.setAttribute("colspan", "6");
        mensajeGenerico.className = "txt-centrado";
    }
}

function esValidacion(cadena) {

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

function buildTemplateMensaje(egreso, esValida) {
    const contenedorHTML = document.getElementById("mensaje-detalle");
    const proveedor = egreso.detalle.proveedor;
    const direccion = proveedor.dirPostal;
    let medioDePago = egreso.medioDePago;
    let faltanPresupuestos = egreso.cantPresupuestos > 0 ? egreso.cantPresupuestosFaltantes === 0 : egreso.cantPresupuestos === 0;
    const template = `
        <header>
            <div class="d-flex jc-sb ai-center fw-700">
                <div class="tooltip">   
                    <h2>Egreso #${egreso.id}${setIconoValidoSegun(esValida)}</h2>
                    <div id="mensaje-resultado" class="tooltiptext tooltiptext-${esValida ? "valida" : "invalida"}">
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
                        <p><span>CUIT/DNI:</span> ${proveedor.CUIT}</p>
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
                <p><span>Monto total:</span>$${egreso.montoTotal} (${medioDePago.tipoDePago.nombre} )</p>
                <p><span>Tipo comprobante:</span> ${egreso.detalle.comprobante.tipoComprobante.nombre}</p>
                <p><span>Comprobante</span>: <a id="ver-comprobante" href="tipo comprobante" target="blank">ver comprobante</a></p>
                <p><span># Presupuestos necesarios: </span>${egreso.cantPresupuestos}</p>
                <p><span># Presupuestos faltantes: </span>${egreso.cantPresupuestosFaltantes} ${setIconoValidoSegun(faltanPresupuestos)}</p>
            </section>
            <section>
                <h3>  Detalle de pedidos </h3>
                 <table id="tabla-detalle" class="txt-centrado tabla">
                
                    <thead class="bg-primario fw-700 th-principal">
                    <tr>
                        <th>Bien</th>
                        <th>Tipo</th>
                        <th>Cantidad</th>
                        <th>Precio Unitario</th>
                        <th>Subtotal</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody class="tabla-detalle">
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="4" style="text-align: right"><em>Total:</em></td>
                        <td id="total"></td>
                    </tr>
                    </tfoot>                
                </table>
            </section>
        </main>  
        <footer>
            <button id="btn-no-revisar" class="btn btn-formulario-danger">Dejar de Revisar</button>
        </footer>  
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
    let tabla = document.getElementById("tabla-detalle");
    let total = document.getElementById("total");
    let contadorMontos = 0;

    function agregarEnTablaDetalleDesdeAPI(pedido) {
        let item = pedido.item;
        let subtotal = item.precioUnitario * pedido.cantidad;

        //agregateContenidoEnTablaSimple(tabla, descripcion,tipo,cantidad,precioUnitario,item)
        agregateFilaEnTablaDetalleSimple(tabla, item.descripcion, item.tipoDeItem.nombre, pedido.cantidad, item.precioUnitario);
        contadorMontos += subtotal;
    }

    for (let i = 0; i < vectorItems.length; i++) {
        agregarEnTablaDetalleDesdeAPI(vectorItems[i]);
    }

    total.innerText = contadorMontos;

}

function buildTooltip(resultadoMensaje) {
    const contenedorHTML = document.getElementById("mensaje-resultado");
    let vectorMsjResultado = resultadoMensaje.split("\n");
    contenedorHTML.scrollIntoView({behavior: "smooth"});
    for (var i = 0; i < vectorMsjResultado.length; i++) {
        let contenido = document.createElement("p");
        contenido.innerHTML = vectorMsjResultado[i];
        contenedorHTML.appendChild(contenido);
    }
}

function dejarDeRevisar(egresoID) {
    let url = "/api/revisor/delete/" + egresoID;
    mostrarLoader();
    fetch(url,{method:"DELETE"})
        .then(response => response.json())
        .then(data => {
            esconderLoader();
            generarModalOK(MENSAJE_REVISOR)
        })
        .catch(reason => console.log(reason));


}

function buildBotonDejarRevisar(egreso) {
    let btn = document.getElementById("btn-no-revisar");
    btn.onclick = () => dejarDeRevisar(egreso.id);
}

function mostrarMensaje(egreso, esValida, detalleValidacion) {
    const detalle = egreso.detalle;

    //const msjResultado = data.cuerpoMensaje;

    buildTemplateMensaje(egreso, esValida);
    //buildCategorias(egreso.categorias);
    buildTablaDetalle(detalle.pedidos);
    buildTooltip(detalleValidacion);
    buildBotonDejarRevisar(egreso);

}

function renderTabla(data) {
    var paginaHTML = document.getElementById("cant-paginas");
    cleanTabla();

    for (let i = countRender, j = 10; j > 0 && i < data.length; i++, j--) {
        buildFila(data[i]);
        countRender = i + 1;
    }

    paginaHTML.innerText = setToStringCantPaginas();

    boton.anterior.hidden = countRender <= 10;
    boton.siguiente.hidden = countRender >= cantMaxima;

}

function cleanTabla() {
    const tbody = document.getElementById("seccion-mensajes");
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }
}

function setToStringCantPaginas() {
    if (cantMaxima == 0) {
        return "0 de 0"
    }
    return cantMaxima > countRender ?
        `${1 + countRender - 10}-${countRender} de ${cantMaxima}` : `${1 + countRender - cantMaxima % 10}-${cantMaxima} de ${countRender}`
}

function getMensajeDesdeApi(id, esValida, detalleValidacion,idMensaje) {
    let url = `/api/get-egreso/${id}/${idMensaje}`;

    fetch(url)
        .then(response => response.json())
        .then(data => mostrarMensaje(data, esValida, detalleValidacion))
        .catch(reason => console.log(reason));
}

/* Eventos */

window.addEventListener("load", () => {
    let url = "/mensajes/todos";
    mostrarLoader();
    fetch(url)
        .then(response => response.json())
        .then(data => {
            dataMensajes = data.mensajes;
            cantMaxima = data.cantMensajes;
            renderTabla(dataMensajes);
            esconderLoader();
        })
        .catch(reason => console.log(reason));
})


boton.siguiente.onclick = () => {
    renderTabla(dataMensajes)
    countRender = Math.min(countRender, cantMaxima);

}

boton.anterior.onclick = () => {
    let resto = countRender % 10;
    if (resto > 0) {
        countRender -= resto + 10;
    } else {
        countRender -= 20;
        countRender = Math.max(countRender, 0);
    }

    renderTabla(dataMensajes);
}