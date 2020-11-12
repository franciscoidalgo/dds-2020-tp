import {esconderLoader, mostrarLoader} from "./loader.js";
import {generarModalOK} from "./modal.js";
import {agregateFilaEnTablaDetalleSimple} from "./tabla.js";

const MENSAJE_REVISOR = 'Te agregaste como revisor de este egreso. Cuando se ejecute la tarea de validacion, recibiras un mensaje en la seccion "Resultados de Validaciones" ';

let cantTotalPresupuestos = 0;
let indicePresupuesto = 0;
let presupuestos = [];
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
                <table id="tabla-detalle-egreso" class="txt-centrado tabla tabla-detalle">
                    <thead class="fw-700 th-principal">
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
                        <td id="total-egreso"></td>
                    </tr>
                    </tfoot>                
                </table>
            </section>
            <h3>Presupuestos</h3>
            <section>
            <p id="msg-sin-presupuesto"><em>No se han cargado presupuestos.</em></p>
                <div id="carrusel-presupuestos" class="d-flex jc-sb" style="padding: 0">
                    <button id="anterior"><i class="fas fa-chevron-left"></i></button>
                     <div id="presupuesto" style="min-width:80%"></div>                  
                    <button id="siguiente"><i class="fas fa-chevron-right"></i></button>
                </div>
            </section>
        </main>  
        <footer class="d-flex jc-se">
            <button id="btn-revisar" class="btn btn-formulario">Revisar</button>
            <button id="btn-editar" class="btn btn-formulario">Editar</button>
        </footer>  
    `
    contenedorHTML.innerHTML = template;
}

function buildBotonRevisar(egreso) {
    let btn = document.getElementById("btn-revisar");
    btn.onclick = () => revisarEgreso(egreso.id);
}

function buildBotonSiguiente(){
    let btn = document.getElementById("siguiente");
    btn.onclick = () =>{
        indicePresupuesto++;
        indicePresupuesto = Math.min(indicePresupuesto,cantTotalPresupuestos-1);
        renderPresupuesto()

    };
}

function buildBotonAnterior(){
    let btn = document.getElementById("anterior");
    btn.onclick = () =>{
        indicePresupuesto--;
        indicePresupuesto = Math.max(indicePresupuesto,0);
            renderPresupuesto()

    };
}

function buildBotonModificar(egreso) {
    let btn = document.getElementById("btn-editar");
    btn.onclick = () => editEgreso(egreso.id);
}

function editEgreso(egresoID) {
    let url = "/egreso/editar/" + egresoID;
    mostrarLoader();
    fetch(url)
        .then(r => r.json())
        .then(value => window.location.href = "/egreso");
}


function revisarEgreso(egresoID) {
    let url = "/revisor/agregar/" + egresoID;
    mostrarLoader();
    fetch(url, {method: "PUT"})
        .then(response => response.json())
        .then(data => {
            esconderLoader();
            generarModalOK(MENSAJE_REVISOR);
        })
        .catch(reason => console.log(reason));
}


function buildTablaDetalle(vectorItems, HTMLTabla, HTMLTotal) {
    let contadorMontos = 0;

    function agregarEnTablaDetalleDesdeAPI(pedido) {
        let item = pedido.item;
        let subtotal = item.precioUnitario * pedido.cantidad;

        agregateFilaEnTablaDetalleSimple(HTMLTabla, item.descripcion, item.tipoDeItem.nombre, pedido.cantidad, item.precioUnitario);
        contadorMontos += subtotal;
    }

    for (let i = 0; i < vectorItems.length; i++) {
        agregarEnTablaDetalleDesdeAPI(vectorItems[i]);
    }

    HTMLTotal.innerText = contadorMontos;

}

function buildPresupuesto(presupuesto) {
    const proveedor = presupuesto.detalle.proveedor;
    const direccion = proveedor.dirPostal;
    const template = `
        <header class="fade">
            <div class="d-flex jc-sb ai-center fw-700 ">
                <div class="tooltip">   
                    <h2>Presupuesto #${presupuesto.id}</h2>      
                </div>
            </div>
            <div id='contenedor-categorias' class="d-flex contenedor-categorias">
            </div>
        </header> 
        <main class="fade">
            <section>
                <h4 style="text-decoration: underline">Informacion del Proveedor</h4>
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
                <h4 style="text-decoration: underline">Detalle de la operacion</h4>
                <p><span>Monto total:</span>$${presupuesto.montoTotal}</p>
                <p><span>Tipo comprobante:</span> ${presupuesto.detalle.comprobante.tipoComprobante.nombre}</p>
                <p><span>Comprobante</span>: <a id="ver-comprobante" href="tipo comprobante" target="blank">ver comprobante</a></p>
            </section>
            <section>
                <h4 style="text-decoration: underline">  Detalle de pedidos </h4>
                <table id="tabla-detalle-presupuesto" class="txt-centrado tabla tabla-detalle">
                    <thead class="fw-700 th-principal">
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
                        <td id="total-prusupuesto"></td>
                    </tr>
                    </tfoot>                
                </table>
            </section>
        </main>  
    `
    return template;
}

function renderPresupuesto() {
    let carrusel = document.getElementById("carrusel-presupuestos");
    let msg = document.getElementById("msg-sin-presupuesto");
    if (cantTotalPresupuestos > 0) {
        let template = buildPresupuesto(presupuestos[indicePresupuesto]);
        let seccion = document.getElementById("presupuesto");
        seccion.innerHTML = template;
        let tabla = document.getElementById("tabla-detalle-presupuesto");
        let costo = document.getElementById("total-prusupuesto");
        buildTablaDetalle(presupuestos[indicePresupuesto].detalle.pedidos, tabla, costo);
        carrusel.hidden = false;
        msg.hidden=true
    }else{
        carrusel.hidden = true;
        msg.hidden=false
    }
}

function renderizarEgresoDetallado(dataEgreso, contenedorHTML) {
    presupuestos = dataEgreso.presupuestos;
    cantTotalPresupuestos = presupuestos.length;

    buildTemplateEgreso(dataEgreso, contenedorHTML);
    let tablaEgreso = document.getElementById("tabla-detalle-egreso");
    let total = document.getElementById("total-egreso");
    buildBotonRevisar(dataEgreso);
    buildBotonModificar(dataEgreso);
    buildTablaDetalle(dataEgreso.detalle.pedidos, tablaEgreso, total);
    renderPresupuesto();
    buildBotonSiguiente();
    buildBotonAnterior();

    contenedorHTML.scrollIntoView({behavior: "smooth"});
}

export {renderizarEgresoDetallado}