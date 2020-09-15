import {agregateEnTablaSimple,tablaTieneElementos} from './tabla.js';
import { contenidoSeleccionadoEn, seleccionarValorPara, contenidoDesplegableEs, quitarDelDesplegableSegunContenido, tieneSeleccionables,agregaContenidoEnDesplegable } from './desplegable.js';
import generaCategoria from './categoria.js';

var burbuja = {
    proveedor: document.getElementById('burbuja-proveedor'),
    detalle: document.getElementById('burbuja-compra'),
    pago: document.getElementById('burbuja-pago')
}

var contenedorFormulario = {
    proveedor: document.getElementById('proveedor'),
    compra: document.getElementById('detalle-compra'),
    pago: document.getElementById('medio-de-pago')
}

var desplegable = {
    razonSocial: document.getElementById('razon-social'),
    vendedor: document.getElementById('vendedor'),
    pais: document.getElementById('pais'),
    provincia: document.getElementById('provincia'),
    ciudad: document.getElementById('ciudad'),
    moneda: document.getElementById('moneda'),
    tipoPago: document.getElementById('tipo-pago'),
    comprobante: document.getElementById('tipo-comprobante'),
    producto: document.getElementById('producto'),
    categoria: document.getElementById('categoria')
}

var seleccionArchivo = {
    comprobante: document.getElementById('comprobante'),
}

var boton = {
    agregarTabla: document.getElementById('agregar-tabla'),
    agregarCategoria: document.getElementById('agregar-categoria'),

}

var seccion = {
    tablaDetalle: document.getElementById('tabla-detalle'),
    msgTablaVacia: document.getElementById('msg-tabla-vacia'),
    categoria: document.getElementById('categorias-seleccionadas'),
    msgCategoriasVacia: document.getElementById('msg-categorias-vacia')
}


/*funciones!*/

function iconoSegunExpansion(etiqueta){
     return etiqueta.hidden ? "fas fa-minus" : "fas fa-minus";
}

function enfocarElemento(etiqueta){
    etiqueta.focus();
    etiqueta.scrollIntoView({ behavior: "smooth" });
}

function setHiddenSegun(disparador, etiqueta) {
    let nodoIcono = disparador.children[1].children[0];

    nodoIcono.className = iconoSegunExpansion(etiqueta);
    etiqueta.hidden = !etiqueta.hidden;
    enfocarElemento(disparador)
}

function agregaEnTablaSegunDesplegable(disparador, tabla, desplegable) {

    let nodoContenido = contenidoSeleccionadoEn(desplegable);
    let contenido = nodoContenido.innerHTML;


    agregateEnTablaSimple(tabla, contenido);
    seleccionarValorPara(desplegable, 0);

    enfocarElemento(tabla);

    quitarDelDesplegableSegunContenido(desplegable, nodoContenido);
    disparador["srcElement"].hidden = true;

}



function agregaEnCategoriaValorDesplegable(disparador, seccion, desplegable) {

    let nodoContenido = contenidoSeleccionadoEn(desplegable);
    let contenido = nodoContenido.innerHTML;
    let categoria = generaCategoria(contenido, true);

    seccion.appendChild(categoria);
    seleccionarValorPara(desplegable, 0);

    enfocarElemento(desplegable);

    quitarDelDesplegableSegunContenido(desplegable, nodoContenido);
    disparador["srcElement"].hidden = true;
}




/*disparadores*/
burbuja.proveedor.onclick = () => setHiddenSegun(burbuja.proveedor, contenedorFormulario.proveedor);
burbuja.detalle.onclick = () => setHiddenSegun(burbuja.detalle, contenedorFormulario.compra);
burbuja.pago.onclick = () => setHiddenSegun(burbuja.pago, contenedorFormulario.pago);

desplegable.producto.onchange = () => {
    boton.agregarTabla.hidden = false;
}

desplegable.categoria.onchange = () => {
    boton.agregarCategoria.hidden = false;
}

desplegable.razonSocial.onchange = () => {
    desplegable.vendedor.disabled = false;
}

desplegable.pais.onchange = () => {
    desplegable.provincia.disabled = false;
}
desplegable.provincia.onchange = () => {
    desplegable.ciudad.disabled = false;
}
desplegable.moneda.onchange = () => {
    desplegable.tipoPago.disabled = false;
}

desplegable.comprobante.onchange = () => {
    let coincidenTexto = contenidoDesplegableEs(desplegable.comprobante, 'Ninguno');
    seleccionArchivo.comprobante.disabled = coincidenTexto;
}

boton.agregarTabla.onclick = (e) => {
    seccion.tablaDetalle.hidden = false;
    seccion.msgTablaVacia.hidden = true;
    agregaEnTablaSegunDesplegable(e, seccion.tablaDetalle, desplegable.producto);
}

boton.agregarCategoria.onclick = (e) => {
    seccion.categoria.hidden = false;
    seccion.msgCategoriasVacia.hidden = true;
    agregaEnCategoriaValorDesplegable(e, seccion.categoria, desplegable.categoria);
}

window.eliminarFila = (id) => {
    let nodoFila = document.getElementById(id);
    let contenido = nodoFila.firstElementChild.textContent;

    agregaContenidoEnDesplegable(desplegable.producto,contenido);
    nodoFila.remove();

    seccion.tablaDetalle.hidden = !tablaTieneElementos(seccion.tablaDetalle);
    seccion.msgTablaVacia.hidden = tablaTieneElementos(seccion.tablaDetalle);

}
window.eliminaCategoria = (id) => {
    let nodoCategoria = document.getElementById(id);
    let contenido = nodoCategoria.firstElementChild.textContent;

    agregaContenidoEnDesplegable(desplegable.categoria,contenido);
    nodoCategoria.remove();


    seccion.categoria.hidden = !seccion.categoria.childElementCount>1;
    seccion.msgCategoriasVacia.hidden = seccion.categoria.childElementCount>1;

}