import {esconderLoader, mostrarLoader} from "./loader.js";
import {generarModalOK} from "./modal.js";
import {agregateFilaEnTablaDetalleSimple} from "./tabla.js";

const MENSAJE_REVISOR = 'Te agregaste como revisor de este egreso. Cuando se ejecute la tarea de validacion, recibiras un mensaje en la seccion "Resultados de Validaciones" ';

function buildTemplateEgreso(egreso, contenedorHTML) {
    const proveedor = egreso.detalle.proveedor;
    const direccion = proveedor.dirPostal;
    let medioDePago = egreso.medioDePago;
    const template = `
        <header>
            <div class="d-flex jc-sb ai-center fw-700">
                <div class="tooltip">   
                    <h2>Egreso #${egreso.id}</h2>      
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
                <p><span># Presupuestos faltantes: </span>${egreso.cantPresupuestosFaltantes}</p>
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
            <button id="btn-revisar" class="btn btn-formulario-danger">Revisar</button>
        </footer>  
    `
    contenedorHTML.innerHTML = template;
}

function buildBotonRevisar(egreso) {
    let btn = document.getElementById("btn-revisar");
    btn.onclick = () => revisarEgreso(egreso.id);
}

function revisarEgreso(egresoID) {
    let url = "/api/revisor/agregar/" + egresoID;
    mostrarLoader();
    fetch(url, {method: "PUT"})
        .then(response => response.json())
        .then(data => {
            esconderLoader();
            generarModalOK(MENSAJE_REVISOR);
        })
        .catch(reason => console.log(reason));
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

function renderizarEgresoDetallado(dataEgreso, contenedorHTML) {
    buildTemplateEgreso(dataEgreso, contenedorHTML);
    buildBotonRevisar(dataEgreso);
    buildTablaDetalle(dataEgreso.detalle.pedidos);

    contenedorHTML.scrollIntoView({behavior: "smooth"});
}

export {renderizarEgresoDetallado}