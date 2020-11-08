import {generaCategoria} from "./categoria.js";
import {agregateFilaEnTablaDetalleSimple} from "./tabla.js";
import {esconderLoader, mostrarLoader} from "./loader.js";
import {generarModalOK} from "./modal.js";


const MENSAJE_REVISOR = "Dejaste de ser revisor de esta operacion. Puedes volver a seleccionarte como revisor, en el panel de busqueda"
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
        let contenido = generaCategoria(vectorCategorias[i].id,vectorCategorias[i].descripcion, true);
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

        agregateFilaEnTablaDetalleSimple(tabla, item.descripcion, item.tipoDeItem.nombre, pedido.cantidad, item.precioUnitario);
        contadorMontos += subtotal;
    }

    for (let i = 0; i < vectorItems.length; i++) {
        agregarEnTablaDetalleDesdeAPI(vectorItems[i]);
    }

    total.innerText = contadorMontos;

}

function setIconoValidoSegun(cadena) {
    return cadena ?
        '<i class="fas fa-check-circle valido"></i>' :
        '<i class="fas fa-exclamation-circle invalido"></i>';
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

function mostrarMensaje(egreso, esValida, detalleValidacion) {
    const detalle = egreso.detalle;

    //const msjResultado = data.cuerpoMensaje;

    buildTemplateMensaje(egreso, esValida);
    buildCategorias(egreso.detalle.categorias);
    buildTablaDetalle(detalle.pedidos);
    buildTooltip(detalleValidacion);
    buildBotonDejarRevisar(egreso);

}



function buildBotonDejarRevisar(egreso) {
    let btn = document.getElementById("btn-no-revisar");
    btn.onclick = () => dejarDeRevisar(egreso.id);
}

function dejarDeRevisar(egresoID) {
    let url = "/api/revisor/delete/" + egresoID;
    mostrarLoader();
    fetch(url, {method: "DELETE"})
        .then(response => response.json())
        .then(data => {
            esconderLoader();
            generarModalOK(MENSAJE_REVISOR);
        })
        .catch(reason => console.log(reason));


}

export {mostrarMensaje,setIconoValidoSegun}