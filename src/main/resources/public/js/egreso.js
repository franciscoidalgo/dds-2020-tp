import agregateEnTablaSimple from './tabla.js';
import { contenidoSeleccionadoEn, seleccionarValorPara, contenidoDesplegableEs, quitarDelDesplegableSegunContenido, tieneSeleccionables } from './desplegable.js';

import generaCategoria from './categoria.js';

var burbuja = {
    proveedor: document.getElementById('burbuja-proveedor'),
    detalle: document.getElementById('burbuja-compra'),
    pago: document.getElementById('burbuja-pago')
}

var formulario = {
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
    categoria: document.getElementById('categorias-seleccionadas')
}


/*funciones!*/
export function setHidden(etiqueta, valor) {
    etiqueta.hidden = valor;
}

function modificaIcono(disparador, clase) {
    disparador.children[1].children[0].className = clase;
}

/**todo: Refactorizar esto */
function setHiddenSegun(disparador, etiqueta) {
    etiqueta.hidden ? modificaIcono(disparador, "fas fa-minus") : modificaIcono(disparador, "fas fa-plus");
    setHidden(etiqueta, !etiqueta.hidden);
    disparador.focus();
    disparador.scrollIntoView({ behavior: "smooth" });

}


function setDisabled(etiqueta, puedeHabilitar) {
    etiqueta.disabled = puedeHabilitar;
}


function enfocarElemento(etiqueta) {
    etiqueta.focus();
    etiqueta.scrollIntoView({ behavior: "smooth" });
}

function manejaSeleccionables(desplegable, disparador) {
    if (!tieneSeleccionables(desplegable)) {
        disparador.srcElement.hidden = true;
        desplegable.disabled = true;
    }
}

function agregaEnTablaSegunDesplegable(disparador, tabla, desplegable) {

    var nodoContenido = contenidoSeleccionadoEn(desplegable);
    var contenido = contenidoSeleccionadoEn(desplegable);

    agregateEnTablaSimple(tabla, contenido.innerHTML);
    seleccionarValorPara(desplegable, 0);

    setHiden(disparador.srcElement, true);
    enfocarElemento(tabla);
    quitarDelDesplegableSegunContenido(desplegable, nodoContenido);
    manejaSeleccionables(desplegable, disparador)
}



function agregaEnCategoriaValorDesplegable(disparador, seccion, desplegable) {
    var contenido = contenidoSeleccionadoEn(desplegable);
    var categoria = generaCategoria(contenido.innerHTML, true);

    seccion.appendChild(categoria);

    seleccionarValorPara(desplegable, 0);
    enfocarElemento(desplegable);
    quitarDelDesplegableSegunContenido(desplegable, contenido);
    manejaSeleccionables(desplegable, disparador)
}




/*disparadores*/
burbuja.proveedor.onclick = () => setHiddenSegun(burbuja.proveedor, formulario.proveedor);
burbuja.detalle.onclick = () => setHiddenSegun(burbuja.detalle, formulario.compra);
burbuja.pago.onclick = () => setHiddenSegun(burbuja.pago, formulario.pago);

desplegable.producto.onchange = () => setHidden(boton.agregarTabla, false);

desplegable.razonSocial.onchange = () => setDisabled(desplegable.vendedor, false);
desplegable.pais.onchange = () => setDisabled(desplegable.provincia, false);
desplegable.provincia.onchange = () => setDisabled(desplegable.ciudad, false);
desplegable.moneda.onchange = () => setDisabled(desplegable.tipoPago, false);

desplegable.comprobante.onchange = () => {
    var coincidenTexto = contenidoDesplegableEs(desplegable.comprobante, 'Ninguno');
    setDisabled(seleccionArchivo.comprobante, coincidenTexto);
};

boton.agregarTabla.onclick = (e) => agregaEnTablaSegunDesplegable(e, seccion.tablaDetalle, desplegable.producto);
boton.agregarCategoria.onclick = (e) => agregaEnCategoriaValorDesplegable(e, seccion.categoria, desplegable.categoria);