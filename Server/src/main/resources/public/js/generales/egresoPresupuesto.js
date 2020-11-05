import {agregateFilaEnTablaSimpleConBorrador, tablaTieneElementos} from './tabla.js';
import {
    agregaContenidoEnDesplegable,
    cleanDesplegable,
    contenidoDesplegableEs,
    contenidoSeleccionadoEn,
    sacarDelDesplegableEscondiendo,
    seleccionarValorPara,
} from './desplegable.js';
import {generaCategoria} from './categoria.js';
import {generaTextbox} from './textbox.js';
import {Burbuja, Desplegable} from "./burbuja.js";


/** Constantes **/
const burbujas = [
    new Burbuja('burbuja-proveedor', 'proveedor'),
    new Burbuja('burbuja-compra', 'detalle-compra'),
    new Burbuja('burbuja-pago', 'medio-de-pago'),
    new Burbuja('burbuja-configuracion', 'configuracion')
]

const ocultadores = [
    new Desplegable('categoria', 'agregar-categoria'),

];

const habilitadores = [
    new Desplegable('pais', 'provincia'),
    new Desplegable('provincia', 'ciudad'),
    new Desplegable('tipoDeItem', 'bien')
];

const desplegable = {
    razonSocial: document.getElementById('razon-social'),
    bien: document.getElementById('bien'),
    categoria: document.getElementById('categoria'),
    comprobante: document.getElementById('tipo-comprobante'),
    tipoDeItem: document.getElementById("tipoDeItem"),
    pais: document.getElementById('pais'),
    provincia: document.getElementById('provincia'),
    ciudad: document.getElementById('ciudad')
};

const entrada = {
    cuit: document.getElementById('cuit'),
    comprobante: document.getElementById('comprobante'),
    cantidad: document.getElementById('cantidad'),
    precioUnitario: document.getElementById('precioUnitario'),
    calle: document.getElementById("calle"),
    altura: document.getElementById("altura"),
    piso: document.getElementById("piso"),
    dpto: document.getElementById("dpto"),
    path: document.getElementById("path"),
    fecha: document.getElementById("fecha"),
    montoTotal: document.getElementById("total") //No es un input en "limpio"
};

const boton = {
    agregarTabla: document.getElementById('agregar-tabla'),
    agregarCategoria: document.getElementById('agregar-categoria'),
    razonSocial: document.getElementById('nueva-razon-social'),
    nuevoItem: document.getElementById('nuevo-item')

};

const seccion = {
    tablaDetalle: document.getElementById('tabla-detalle'),
    msgTablaVacia: document.getElementById('msg-tabla-vacia'),
    categoria: document.getElementById('categorias-seleccionadas'),
    msgCategoriasVacia: document.getElementById('msg-categorias-vacia')
};

/** JSON **/
let idProveedorSeleccionado = -1;// -1 (Selecciono en Nuevo) --- !=-1 (Selecciono en desplegable)

let montoTotal = 0.00;
let jPedido = {pedido: []}
let jCategorias = {idCategorias: []}

/** Functions **/

/** Proveedor **/
function getProveedoresFromAPI() {
    let idProveedor = document.getElementById("razon-social").value;
    let url = "/api/get-proveedor/" + idProveedor;

    fetch(url)
        .then(response => response.json())
        .then(dataProveedor => {
            setNodesProveedor(dataProveedor)
            idProveedorSeleccionado = idProveedor;
        })
        .catch(reason => console.log(reason))
}

function setNodesProveedor(dataAPIEgreso) {
    let cuit = document.getElementById("cuit");
    let pais = document.getElementById("pais");
    let provincia = document.getElementById("provincia");
    let ciudad = document.getElementById("ciudad");
    let calle = document.getElementById("calle");
    let altura = document.getElementById("altura");
    let piso = document.getElementById("piso");
    let dpto = document.getElementById("dpto");
    let direccion = dataAPIEgreso.dirPostal;

    //inputs
    cuit.value = dataAPIEgreso.CUIT;
    calle.value = direccion.calle;
    piso.value = direccion.piso;
    dpto.value = direccion.dpto;
    altura.value = direccion.altura;

    //desplegables
    agregaContenidoEnDesplegable(pais, direccion.pais, true)
    agregaContenidoEnDesplegable(provincia, direccion.provincia, true)
    agregaContenidoEnDesplegable(ciudad, direccion.ciudad, true)


}

function cambiaATextboxParaRazonSocial() {
    let contenedor = desplegable.razonSocial.parentNode;
    let textbox = generaTextbox(desplegable.razonSocial);
    textbox.required = true;
    boton.razonSocial.value = "Volver";
    boton.razonSocial.hidden = false;
    idProveedorSeleccionado = -1;
    desplegable.razonSocial.remove();
    desplegable.pais.value = "";
    contenedor.appendChild(textbox);
}

function cambiaADesplegableParaRazonSocial() {
    let contenedor = document.getElementById('razon-social').parentNode;

    /* Rollback del estado incial */
    boton.razonSocial.value = "Nueva Razon Social";

    /*Vuelo textbox para poner desplegable*/
    contenedor.lastChild.remove();
    contenedor.appendChild(desplegable.razonSocial);
    desplegable.razonSocial.disabled = false;
    desplegable.razonSocial.value = "";

}

function setDisabledProveedor(esNuevo) {

    let inputs = [entrada.cuit, entrada.calle, entrada.altura, entrada.piso, entrada.dpto];
    //Inputs
    inputs.forEach(input => {
        input.disabled = esNuevo;
        !esNuevo ? input.value = "" : false;
    })
    //Desplegables
    desplegable.pais.disabled = esNuevo;
    desplegable.provincia.disabled = true;
    desplegable.ciudad.disabled = true;
    cleanDesplegable(desplegable.provincia);
    cleanDesplegable(desplegable.ciudad);

}

function cargarDesplegableDesdeAPIML(desplegable, url) {

    function cargarEnDesplegable(dataAPIML) {
        cleanDesplegable(desplegable);
        for (let i = 0; i < dataAPIML.length; i++) {
            agregaContenidoEnDesplegable(desplegable, dataAPIML[i].name, false);
        }
    }

    fetch(url)
        .then(response => response.json())
        .catch(reason => console.log(reason))
        .then(data => cargarEnDesplegable(data))


}

/** Utils **/
function escondeMostrandoA(nodoEsconder, nodoMostrar) {
    nodoEsconder.hidden = true;
    nodoMostrar.hidden = false;
}

function sacarContenidoSeteandoInicial(desplegable, nodoContenido, disparador) {
    seleccionarValorPara(desplegable, 0);//Setea --Seleccione--
    sacarDelDesplegableEscondiendo(desplegable, nodoContenido);
    disparador["srcElement"].hidden = true;
}

/** Detalle Compra**/
function habilitarBtnAgregarTabla() {
    let bien = document.getElementById("bien").value;

    function estaVacio(elementoHTML) {
        return elementoHTML === "";
    }

    boton.agregarTabla.hidden = (entrada.cantidad.value <= 0 || entrada.precioUnitario.value <= 0) || estaVacio(bien) || estaVacio(desplegable.tipoDeItem.value);

}

function getItemsSinRepetidos(dataAPIItems) {
    let mapDescripcion = dataAPIItems.map(value => value.descripcion);
    return mapDescripcion.filter((el, index) => mapDescripcion.indexOf(el) === index);
}

function cargarDesplegableItems(dataAPIItems) {
    let items = getItemsSinRepetidos(dataAPIItems);
    cleanDesplegable(desplegable.bien);
    for (let i = 0; i < items.length; i++) {
        agregaContenidoEnDesplegable(desplegable.bien, items[i], false)
    }
    desplegable.bien.disabled = false;
}

function getItemsFromAPI() {
    let idTipo = desplegable.tipoDeItem.value;
    let esTextboxSeleccionBien = desplegable.bien.hasAttribute("type", "text");
    let url = "/api/get-item-segun-tipo/" + idTipo;

    if (!esTextboxSeleccionBien) {
        fetch(url)
            .then(response => response.json())
            .then(data => cargarDesplegableItems(data))
            .catch(reason => console.log(reason))

    }
}

/** Agregar Categorias **/

function agregaEnCategoriaValorDesplegable(disparador, seccion, desplegable) {
    let nodoContenido = contenidoSeleccionadoEn(desplegable);
    let contenido = nodoContenido.innerHTML;
    let categoria = generaCategoria(contenido, true);
    let idCategoria = desplegable.value;
    let jCategoria = {idCategoria};

    sacarContenidoSeteandoInicial(desplegable, nodoContenido, disparador);
    seccion.appendChild(categoria);
    jCategorias.idCategorias.push(jCategoria);
    categoria.onclick = () => {
        eliminaCategoria(categoria, jCategoria)
    };//comportamiento en window
}

function esconderMostrandoOpuestosSegun(parametroNot, opuesto, funcion) {
    parametroNot.hidden = !funcion(parametroNot);
    opuesto.hidden = funcion(parametroNot);
}

function hayCategoriasSeleccionadas(nodoCategoria) {
    return nodoCategoria.childElementCount > 1;
}

function manejadorEliminar(nodoObjetivo, desplegable) {
    let contenido = nodoObjetivo.firstElementChild.textContent;
    agregaContenidoEnDesplegable(desplegable, contenido, false);
    nodoObjetivo.remove();
}

function getProveedor() {
    let jProveedor = {proveedor: {}}
    let esTextboxRazonSocial = document.getElementById("razon-social").hasAttribute("type", "text");

    jProveedor.proveedor.razonSocial = !esTextboxRazonSocial ? contenidoSeleccionadoEn(desplegable.razonSocial).innerText : desplegable.razonSocial.value;
    jProveedor.proveedor.cuit = entrada.cuit.value;
    jProveedor.proveedor.pais = contenidoSeleccionadoEn(desplegable.pais).innerText;
    jProveedor.proveedor.provincia = contenidoSeleccionadoEn(desplegable.provincia).innerText;
    jProveedor.proveedor.ciudad = contenidoSeleccionadoEn(desplegable.ciudad).innerText;
    jProveedor.proveedor.calle = entrada.calle.value;
    jProveedor.proveedor.altura = entrada.altura.value;
    jProveedor.proveedor.piso = entrada.piso.value;
    jProveedor.proveedor.dpto = entrada.dpto.value;
    jProveedor.id = idProveedorSeleccionado;

    return jProveedor;
}

function getComprobante() {
    let jComprobante = {comprobante: {}}

    jComprobante.comprobante.tipoComprobante = desplegable.comprobante.value;
    jComprobante.comprobante.path = contenidoDesplegableEs(desplegable.comprobante, 'Ninguno') ? "" : entrada.path.value;

    return jComprobante.comprobante;

}

function getTemplateJson() {
    let jsonTemplate = {}

    jsonTemplate.fecha = entrada.fecha.value;
    jsonTemplate.pedido = jPedido.pedido;
    jsonTemplate.idCategorias = jCategorias.idCategorias;
    jsonTemplate.proveedor = getProveedor();
    jsonTemplate.comprobante = getComprobante();
    return jsonTemplate;
}

/** Eventos **/


burbujas.forEach((b) => {
    b.elemento.onclick = () => b.ocultaBurbuja();
})

ocultadores.forEach((d) => {
    d.elemento.onchange = () => d.mostraObjetivo();
});

habilitadores.forEach((d) => {
    d.elemento.onchange = () => d.habilitaObjetivo();
});

desplegable.comprobante.onchange = () => {
    entrada.comprobante.disabled = contenidoDesplegableEs(desplegable.comprobante, 'Ninguno');
}

desplegable.razonSocial.onchange = () => getProveedoresFromAPI();

boton.agregarTabla.onclick = () => {
    let botonNuevo = document.getElementById('nuevo-item');
    let bien = document.getElementById('bien');
    let tipo = document.getElementById('tipoDeItem');
    let cantidad = document.getElementById('cantidad');
    let precioUnitario = document.getElementById('precioUnitario');
    let contenedor = bien.parentNode;
    let btnAgregarTabla = document.getElementById('agregar-tabla')
    let item = {
        nombre: "",
        idTipo: 0,
        cantidad: 0,
        precioUnitario: 0.00
    }

    escondeMostrandoA(seccion.msgTablaVacia, seccion.tablaDetalle);

    if (botonNuevo.value !== "Cancelar") {
        agregateFilaEnTablaSimpleConBorrador(seccion.tablaDetalle, contenidoSeleccionadoEn(bien).innerText, contenidoSeleccionadoEn(tipo).innerText, cantidad.value, precioUnitario.value, item)
        item.nombre = contenidoSeleccionadoEn(bien).innerText
    } else {
        agregateFilaEnTablaSimpleConBorrador(seccion.tablaDetalle, bien.value, contenidoSeleccionadoEn(tipo).innerText, cantidad.value, precioUnitario.value, item)
        bien.remove();
        boton.nuevoItem.value = "Nuevo";
        contenedor.appendChild(desplegable.bien);
        item.nombre = bien.value
    }
    item.idTipo = tipo.value
    item.precioUnitario = parseFloat(precioUnitario.value).toFixed(2)
    item.cantidad = Math.round(cantidad.value)
    btnAgregarTabla.hidden = true;
    desplegable.tipoDeItem.value = "";

    entrada.precioUnitario.value = "";
    entrada.cantidad.value = "";
    bien.disabled = true;
    jPedido.pedido.push(item);

    montoTotal += item.precioUnitario * item.cantidad;
    entrada.montoTotal.innerText =montoTotal ;

}

boton.agregarCategoria.onclick = (e) => {
    escondeMostrandoA(seccion.msgCategoriasVacia, seccion.categoria);
    agregaEnCategoriaValorDesplegable(e, seccion.categoria, desplegable.categoria);
}

boton.razonSocial.onclick = () => {
    let esVolver = boton.razonSocial.value === "Volver";

    setDisabledProveedor(esVolver);
    !esVolver ?
        cambiaATextboxParaRazonSocial() : cambiaADesplegableParaRazonSocial();
}

boton.nuevoItem.onclick = () => {
    let contenedor = document.getElementById('bien').parentNode;
    let valorBoton = boton.nuevoItem.value;

    if (valorBoton !== "Cancelar") {
        let contenido = generaTextbox(desplegable.bien);
        boton.nuevoItem.value = "Cancelar";
        desplegable.bien.remove();
        contenedor.appendChild(contenido);
        contenido.onkeyup = () => {
            habilitarBtnAgregarTabla();
        }
    } else {
        let contenido = document.getElementById('bien');
        boton.nuevoItem.value = "Nuevo";
        contenido.remove();
        contenedor.appendChild(desplegable.bien);
    }
}

entrada.precioUnitario.onkeyup = () => habilitarBtnAgregarTabla()

entrada.cantidad.onkeyup = () => habilitarBtnAgregarTabla()

desplegable.bien.onchange = () => habilitarBtnAgregarTabla()

desplegable.tipoDeItem.onchange = () => getItemsFromAPI();

desplegable.pais.onchange = () => {
    let provincia = contenidoSeleccionadoEn(desplegable.pais).innerText;
    let url = "/api/get-lista-de-provincias/" + provincia;
    cargarDesplegableDesdeAPIML(desplegable.provincia, url);
    desplegable.provincia.disabled = false;
}

desplegable.provincia.onchange = () => {
    let provincia = contenidoSeleccionadoEn(desplegable.provincia).innerText;
    let url = "/api/get-lista-de-ciudades/" + provincia;
    cargarDesplegableDesdeAPIML(desplegable.ciudad, url);
    desplegable.ciudad.disabled = false;
}

window.eliminarFila = (nodoFila, itemSeleccionado) => {
    manejadorEliminar(nodoFila, desplegable.bien);

    montoTotal -= itemSeleccionado.cantidad * itemSeleccionado.precioUnitario;
    console.log(montoTotal);
    entrada.montoTotal.innerText =montoTotal ;

    jPedido.pedido = jPedido.pedido.filter(value => value !== itemSeleccionado);
    esconderMostrandoOpuestosSegun(seccion.tablaDetalle, seccion.msgTablaVacia, tablaTieneElementos);
}

window.eliminaCategoria = (nodoCategoria, idCategoria) => {
    manejadorEliminar(nodoCategoria, desplegable.categoria);
    jCategorias.idCategorias = jCategorias.idCategorias.filter(value => value !== idCategoria);
    esconderMostrandoOpuestosSegun(seccion.categoria, seccion.msgCategoriasVacia, hayCategoriasSeleccionadas);
}

export {getTemplateJson};