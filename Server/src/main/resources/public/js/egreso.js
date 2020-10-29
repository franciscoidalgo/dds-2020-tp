import {agregateContenidoEnTablaSimple, tablaTieneElementos} from './tabla.js';
import {
    agregaContenidoEnDesplegable,
    contenidoDesplegableEs,
    contenidoSeleccionadoEn,
    sacarDelDesplegableEscondiendo,
    seleccionarValorPara,
} from './desplegable.js';
import generaCategoria from './categoria.js';
import generaTextbox from './textbox.js';
import {Burbuja,  Desplegable} from "./burbuja.js";
import {generaModalAlert,generaBoton} from "./modal.js"


const burbujas = [
        new Burbuja('burbuja-proveedor','proveedor'),
        new Burbuja('burbuja-compra','detalle-compra'),
        new Burbuja('burbuja-pago','medio-de-pago')
]

const ocultadores = [
        new Desplegable('producto', 'agregar-tabla'),
        new Desplegable('categoria', 'agregar-categoria'),
];

const habilitadores = [
        new Desplegable('razon-social', 'vendedor'),
        new Desplegable('pais', 'provincia'),
        new Desplegable('provincia', 'ciudad'),
        new Desplegable('moneda', 'tipo-pago'),
];

/* Estados Iniciales */
const desplegable = {
    razonSocial: document.getElementById('razon-social'),
    vendedor: document.getElementById('vendedor'),
    producto: document.getElementById('producto'),
    categoria: document.getElementById('categoria'),
    comprobante: document.getElementById('tipo-comprobante')
};

const entrada = {
    dni: document.getElementById('dni'),
    comprobante: document.getElementById('comprobante')
};

const boton = {
    agregarTabla: document.getElementById('agregar-tabla'),
    agregarCategoria: document.getElementById('agregar-categoria'),
    razonSocial: document.getElementById('nueva-razon-social'),
    vendedor: document.getElementById('nuevo-vendedor'),
    nuevoItem: document.getElementById('nuevo-item')

};

const seccion = {
    tablaDetalle: document.getElementById('tabla-detalle'),
    msgTablaVacia: document.getElementById('msg-tabla-vacia'),
    categoria: document.getElementById('categorias-seleccionadas'),
    msgCategoriasVacia: document.getElementById('msg-categorias-vacia')
};


/* Funciones */

function agregaEnTablaSegunDesplegable(disparador, tabla, desplegable) {

    let nodoContenido = contenidoSeleccionadoEn(desplegable);
    let contenido = nodoContenido.innerHTML;

    agregateContenidoEnTablaSimple(tabla, contenido);
    sacarContenidoSeteandoInicial(desplegable,nodoContenido,disparador);
}

function agregaEnCategoriaValorDesplegable(disparador, seccion, desplegable) {
    let nodoContenido = contenidoSeleccionadoEn(desplegable);
    let contenido = nodoContenido.innerHTML;
    let categoria = generaCategoria(contenido, true);

    sacarContenidoSeteandoInicial(desplegable,nodoContenido,disparador);
    seccion.appendChild(categoria);
    categoria.onclick = ()=>{eliminaCategoria(categoria)};//comportamiento en window
}


function cambiaATextboxParaVendedor(){
    let contenedor = document.getElementById('vendedor').parentNode;
    let contenido = generaTextbox(desplegable.vendedor);

    boton.vendedor.value="Volver";
    desplegable.vendedor.remove();

    contenedor.appendChild(contenido);
    boton.razonSocial.hidden = true;

    desplegable.razonSocial.disabled = true;
    entrada.dni.disabled = false;
}

function cambiaATextboxParaRazonSocial(){
    let contenedor = desplegable.razonSocial.parentNode;
    let textbox = generaTextbox(desplegable.razonSocial);

    cambiaATextboxParaVendedor();

    boton.razonSocial.value="Volver";
    boton.razonSocial.hidden=false;
    boton.vendedor.hidden = true;

    desplegable.razonSocial.remove();
    contenedor.appendChild(textbox);


}

function cambiaADesplegableParaVendedor(){
    let contenedor = document.getElementById('vendedor').parentNode;

   /* Rollback del estado incial */
    boton.vendedor.value="Nuevo Vendedor";
    boton.razonSocial.hidden = false;
    entrada.dni.disabled = true;

    /*Vuelo textbox para poner desplegable*/
    contenedor.lastChild.remove();
    contenedor.appendChild(desplegable.vendedor);
}

function cambiaADesplegableParaRazonSocial(){
    let contenedor = document.getElementById('razon-social').parentNode;

    /* Rollback del estado incial */
    boton.razonSocial.value = "Nueva Razon Social";
    boton.vendedor.hidden = false;

    /*Vuelo textbox para poner desplegable*/
    contenedor.lastChild.remove();
    contenedor.appendChild(desplegable.razonSocial);

    cambiaADesplegableParaVendedor();
}


function escondeMostrandoA(nodoEsconder,nodoMostrar){
    nodoEsconder.hidden=true;
    nodoMostrar.hidden=false;
}
function esconderMostrandoOpuestosSegun(parametroNot,opuesto,funcion){
    parametroNot.hidden = !funcion(parametroNot);
    opuesto.hidden = funcion(parametroNot);
}

function hayCategoriasSeleccionadas(nodoCategoria){
    return nodoCategoria.childElementCount>1;
}

function manejadorEliminar (nodoObjetivo,desplegable){
    let contenido = nodoObjetivo.firstElementChild.textContent;
    agregaContenidoEnDesplegable(desplegable,contenido);
    nodoObjetivo.remove();
}

function sacarContenidoSeteandoInicial(desplegable,nodoContenido,disparador){
    seleccionarValorPara(desplegable, 0);//Setea --Seleccione--
    sacarDelDesplegableEscondiendo(desplegable, nodoContenido);
    disparador["srcElement"].hidden = true;
}


/*Eventos!*/

/*Repetidos*/
burbujas.forEach((b)=>{
    b.elemento.onclick = () => b.ocultaBurbuja();
})

ocultadores.forEach((d) =>{
    d.elemento.onchange = () => d.mostraObjetivo();
});

habilitadores.forEach((d) =>{
    d.elemento.onchange = () => d.habilitaObjetivo();
});

desplegable.comprobante.onchange = () => {
    entrada.comprobante.disabled = contenidoDesplegableEs(desplegable.comprobante, 'Ninguno');
}

/*Boton*/
boton.agregarTabla.onclick = (e) => {
    let botonNuevo = e.target["nextElementSibling"];
    let contenido = document.getElementById('producto');
    let contenedor = contenido.parentNode;

    escondeMostrandoA(seccion.msgTablaVacia,seccion.tablaDetalle);

    if(botonNuevo.value !== "Cancelar"){
        agregaEnTablaSegunDesplegable(e, seccion.tablaDetalle, contenido);
    }else{
        agregateContenidoEnTablaSimple(seccion.tablaDetalle, contenido.value);
        contenido.remove();
        boton.nuevoItem.value = "Nuevo";
        contenedor.appendChild(desplegable.producto);
        document.getElementById('agregar-tabla').hidden=true;
    }
}

boton.agregarCategoria.onclick = (e) => {
    escondeMostrandoA(seccion.msgCategoriasVacia, seccion.categoria);
    agregaEnCategoriaValorDesplegable(e, seccion.categoria, desplegable.categoria);
}

boton.razonSocial.onclick = () =>{
     boton.razonSocial.value !== "Volver"?
         cambiaATextboxParaRazonSocial():cambiaADesplegableParaRazonSocial();
}

boton.vendedor.onclick = () =>{
    boton.vendedor.value !== "Volver"?
        cambiaATextboxParaVendedor():cambiaADesplegableParaVendedor();
}

boton.nuevoItem.onclick = ()=>{
    let contenedor = document.getElementById('producto').parentNode;
    let valorBoton =  boton.nuevoItem.value;

    if(valorBoton !== "Cancelar"){
    let contenido = generaTextbox(desplegable.producto);
    boton.nuevoItem.value = "Cancelar";
    desplegable.producto.remove();
    contenedor.appendChild(contenido);
        contenido.onkeyup = ()=>{
            boton.agregarTabla.hidden = contenido.value==="";
        }
    }else{
        let contenido = document.getElementById('producto');
        boton.nuevoItem.value = "Nuevo";
        contenido.remove();
        contenedor.appendChild(desplegable.producto);
    }
}

/*En ejecucion*/

window.eliminarFila = (nodoFila) => {
    manejadorEliminar(nodoFila,desplegable.producto);
    esconderMostrandoOpuestosSegun(seccion.tablaDetalle,seccion.msgTablaVacia,tablaTieneElementos);
}

window.eliminaCategoria = (nodoCategoria) => {
    manejadorEliminar(nodoCategoria,desplegable.categoria);
    esconderMostrandoOpuestosSegun(seccion.categoria,seccion.msgCategoriasVacia,hayCategoriasSeleccionadas);
}

/*Paises, provincias y ciudades */
$(document).on("change", '#pais', function(e){
    const urlBase = "/api/get-lista-de-provincias/";
    const nombrePais = $("select#pais option:checked").text();
    const urlFinal = urlBase.concat(nombrePais);

    $.ajax({
        url : urlFinal,
        dataType: 'json',
        success: function (json){
            const $listaProvincias = $("#provincia");
            const $listaCiudades = $("#ciudad");
            $listaCiudades.prop("disabled", true);
            $listaCiudades.empty();
            $listaCiudades.append($("<option disabled selected></option>")
                          .attr("value", "").text("-- Seleccione --"));

            $listaProvincias.empty();
            $listaProvincias.append($("<option disabled selected></option>")
                            .attr("value", "").text("-- Seleccione --"));
            $.each(json, function(index, item){
                $listaProvincias.append($("<option></option>")
                                .attr("value", item.name).text(item.name));
            })
        }
    })
});

$(document).on("change", '#provincia', function(e){
    const urlBase = "/api/get-lista-de-ciudades/";
    const nombreProvincia = $("select#provincia option:checked").text();
    const urlFinal = urlBase.concat(nombreProvincia);

    $.ajax({
        url : urlFinal,
        dataType: 'json',
        success: function (json){
            const $listaCiudades = $("#ciudad");

            $listaCiudades.empty();
            $listaCiudades.append($("<option disabled selected></option>")
                            .attr("value", "").text("-- Seleccione --"));
            $.each(json, function(index, item){
                $listaCiudades.append($("<option></option>")
                                .attr("value", item.name).text(item.name));
            })
        }
    })
});
//PARA PROVEEDOR

/* SUBMIT */
function obtenerProductosSeleccionados(){
    let productos = document.getElementsByTagName("td")
    let data = []
    for(let i = 0 ; i < productos.length;i++){
        if(productos[i].innerText !== ""){
            data.push(productos[i].innerText);
        }
    }
    return data;
}

function obtenerCategoriasSeleccionadas(){
    let categorias = document.getElementsByClassName("categoria-seleccionada")
    let data = []
    for(let i = 0 ; i < categorias.length;i++){
        if(categorias[i].firstChild.innerText !== ""){
            data.push(categorias[i].firstChild.innerText)
        }
    }
    return data;
}

document.getElementById("operacion-egreso").onsubmit = (e) => {
    var url = '/egreso';
    e.preventDefault();
    $.ajax({
        url : url,
        type: "POST",
        data:{
            "items": JSON.stringify(obtenerProductosSeleccionados()),
            "categorias": JSON.stringify(obtenerCategoriasSeleccionadas()),
            "nombreProveedor": $("#vendedor").val(),
            "razonSocial": $("#razon-social").val(),
            "DNI": $("#dni").val(),
            "pais": $("#pais").val(),
            "provincia": $("#provincia").val(),
            "ciudad": $("#ciudad").val(),
            "calle": $("#calle").val(),
            "altura": $("#altura").val(),
            "piso": $("#piso").val(),
            "dpto": $("#dpto").val(),
            "tipoComprobante":$("#tipo-comprobante").val(),
            "path":$("#comprobante").val(),
            "medioDePago":$("#medio-de-pago").val(),
            "moneda":$("#moneda").val(),
            "montoTotal": $("#costo").val()
        },
        success: function(response){
                    let modal = generaModalAlert("Operacion exitosa","El egreso se genero correctamente.");
                    let botonera = generaBotonera();
                    let boton = generaBoton("Ok",recargarPagina);
                    botonera.appendChild(boton);
                    modal.firstElementChild.appendChild(botonera);
                    document.body.appendChild(modal);
                },
                error: function(){
                    let modal = generaModalAlert("Error", "Hubo un error al generar el egreso.");
                    let botonera = generaBotonera();
                    let boton = generaBoton("Ok",cerrarModal);
                    botonera.appendChild(boton);
                    modal.firstElementChild.appendChild(botonera);
                    document.body.appendChild(modal);
                }
    });

}

window.cerrarModal = () => {
    let modal = document.querySelector(".modal");
    modal.remove();
}

window.recargarPagina = () => {
    window.location.reload(true);
}

function generaBotonera(){
    let botonera = document.createElement("div");
    botonera.className = "d-flex jc-se"
    return botonera;
}
