import {agregateContenidoEnTablaSimple, tablaTieneElementos} from './tabla.js';
import {
    agregaContenidoEnDesplegable, cleanDesplegable,
    contenidoDesplegableEs,
    contenidoSeleccionadoEn,
    sacarDelDesplegableEscondiendo,
    seleccionarValorPara,
} from './desplegable.js';
import generaCategoria from './categoria.js';
import generaTextbox from './textbox.js';
import {Burbuja, Desplegable} from "./burbuja.js";
import {generaBoton, generaModalAlert} from "./modal.js"

const burbujas = [
    new Burbuja('burbuja-proveedor', 'proveedor'),
    new Burbuja('burbuja-compra', 'detalle-compra'),
    new Burbuja('burbuja-pago', 'medio-de-pago'),
    new Burbuja('burbuja-configuracion', 'configuracion')
]

const ocultadores = [
    new Desplegable('bien', 'agregar-tabla'),
    new Desplegable('categoria', 'agregar-categoria'),
];

const habilitadores = [
    new Desplegable('pais', 'provincia'),
    new Desplegable('provincia', 'ciudad')
];

/* Estados Iniciales */
const desplegable = {
    razonSocial: document.getElementById('razon-social'),
    bien: document.getElementById('bien'),
    categoria: document.getElementById('categoria'),
    comprobante: document.getElementById('tipo-comprobante')
};

const entrada = {
    cuit: document.getElementById('cuit'),
    comprobante: document.getElementById('comprobante')
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

/**********************************************************************/
let jsonPost = {
    proveedor:{
        razonSocial:"",
        cuit:0,
        pais:"",
        provincia:"",
        ciudad:"",
        altura:0,
        calle:"",
        piso:"",
        dpto:"",
        id:-1
    },
    pedido:[],
    idCategorias:[],
    comprobante:{
        tipoComprobante:1,
        path:""
    },
    fecha:"2021-02-28",
    idEgresos:0
}


function buildProveedor(){
    jsonPost.proveedor.razonSocial = document.querySelectorAll("select#razon-social")?
                                            contenidoSeleccionadoEn(desplegable.razonSocial).innerText:
                                            document.getElementById("razon-social").value;
    jsonPost.proveedor.cuit = document.getElementById("cuit").value
    jsonPost.proveedor.pais = contenidoSeleccionadoEn(document.getElementById("pais")).innerText
    jsonPost.proveedor.provincia = contenidoSeleccionadoEn(document.getElementById("provincia")).innerText
    jsonPost.proveedor.ciudad = contenidoSeleccionadoEn(document.getElementById("ciudad")).innerText
    jsonPost.proveedor.calle = document.getElementById("calle").value
    jsonPost.proveedor.altura = document.getElementById("altura").value
    jsonPost.proveedor.piso = document.getElementById("piso").value
    jsonPost.proveedor.dpto = document.getElementById("dpto").value

    jsonPost.fecha = document.getElementById("fecha").value;
    jsonPost.idEgresos = document.getElementById("egreso-a-asociar").value;
    jsonPost.comprobante.path = contenidoDesplegableEs(desplegable.comprobante, 'Ninguno')?"":document.getElementById("comprobante").value;
}

/* Funciones */

function agregaEnCategoriaValorDesplegable(disparador, seccion, desplegable) {
    let nodoContenido = contenidoSeleccionadoEn(desplegable);
    let contenido = nodoContenido.innerHTML;
    let categoria = generaCategoria(contenido, true);
    let idCategoria = desplegable.value;
    let jCategoria ={id:idCategoria};

    sacarContenidoSeteandoInicial(desplegable, nodoContenido, disparador);
    seccion.appendChild(categoria);
    jsonPost.idCategorias.push(jCategoria);
    categoria.onclick = () => {
        eliminaCategoria(categoria,jCategoria)
    };//comportamiento en window
}

function setDisabledProveedor(esNuevo) {
    let cuit = document.getElementById("cuit");
    let pais = document.getElementById("pais");
    let provincia = document.getElementById("provincia");
    let ciudad = document.getElementById("ciudad");
    let calle = document.getElementById("calle");
    let altura = document.getElementById("altura");
    let piso = document.getElementById("piso");
    let dpto = document.getElementById("dpto");

    cuit.disabled  = esNuevo;

    pais.disabled  = esNuevo;

    cleanDesplegable(provincia);
    cleanDesplegable(ciudad);

    calle.disabled  = esNuevo;
    altura.disabled  = esNuevo;
    piso.disabled  = esNuevo;
    dpto.disabled = esNuevo;

}

function setNodesProveedor(data){
    let cuit = document.getElementById("cuit");
    let pais = document.getElementById("pais");
    let provincia = document.getElementById("provincia");
    let ciudad = document.getElementById("ciudad");
    let calle = document.getElementById("calle");
    let altura = document.getElementById("altura");
    let piso = document.getElementById("piso");
    let dpto = document.getElementById("dpto");
    let direccion = data.dirPostal;

    //inputs
    cuit.value = data.CUIT;
    calle.value = direccion.calle;
    piso.value = direccion.piso;
    dpto.value = direccion.dpto;
    altura.value = direccion.altura;

    //desplegables
    agregaContenidoEnDesplegable(pais,direccion.pais,true)
    agregaContenidoEnDesplegable(provincia,direccion.provincia,true)
    agregaContenidoEnDesplegable(ciudad,direccion.ciudad,true)


}

function getProveedoresFromAPI() {
    let id = document.getElementById("razon-social").value;
    let url = "/api/get-proveedor/" + id;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            setNodesProveedor(data)
            jsonPost.proveedor.id = id;
        })
        .catch(reason => console.log(reason))
}

function cambiaATextboxParaRazonSocial() {
    let contenedor = desplegable.razonSocial.parentNode;
    let textbox = generaTextbox(desplegable.razonSocial);

    boton.razonSocial.value = "Volver";
    boton.razonSocial.hidden = false;
    jsonPost.proveedor.id = -1;
    desplegable.razonSocial.remove();
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

}


function escondeMostrandoA(nodoEsconder, nodoMostrar) {
    nodoEsconder.hidden = true;
    nodoMostrar.hidden = false;
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
    agregaContenidoEnDesplegable(desplegable, contenido,false);
    nodoObjetivo.remove();
}

function sacarContenidoSeteandoInicial(desplegable, nodoContenido, disparador) {
    seleccionarValorPara(desplegable, 0);//Setea --Seleccione--
    sacarDelDesplegableEscondiendo(desplegable, nodoContenido);
    disparador["srcElement"].hidden = true;
}

function habilitarAgregarItem(){
    let tipoBien = document.getElementById("tipoDeItem");
    let bien = document.getElementById("bien");
    let cantidad = document.getElementById("cantidad");
    let costo = document.getElementById("costo");

    boton.agregarTabla.hidden = !(tipoBien.value !== "" && bien.value !=="" && cantidad.value && costo.value);
}
/*Eventos!*/

document.getElementById("cantidad").onchange = () =>{
    habilitarAgregarItem();
}
document.getElementById("costo").onchange= () => {
    habilitarAgregarItem();
}

/*Repetidos*/
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


/*Boton*/
boton.agregarTabla.onclick = (e) => {
    let botonNuevo = document.getElementById('nuevo-item');
    let bien = document.getElementById('bien');
    let tipo = document.getElementById('tipoDeItem');
    let cantidad = document.getElementById('cantidad');
    let costo = document.getElementById('costo');
    let contenedor = bien.parentNode;
    let btnAgregarTabla =document.getElementById('agregar-tabla')
    let item = {
        nombre:"",
        idTipo:0,
        cantidad:0,
        precioUnitario:0.00
    }
    escondeMostrandoA(seccion.msgTablaVacia, seccion.tablaDetalle);

    if (botonNuevo.value !== "Cancelar") {
        agregateContenidoEnTablaSimple(seccion.tablaDetalle, contenidoSeleccionadoEn(bien).innerText,contenidoSeleccionadoEn(tipo).innerText,cantidad.value,costo.value,item)
        item.nombre = contenidoSeleccionadoEn(bien).innerText
    } else {
        agregateContenidoEnTablaSimple(seccion.tablaDetalle, bien.value,contenidoSeleccionadoEn(tipo).innerText,cantidad.value,costo.value,item)
        bien.remove();
        boton.nuevoItem.value = "Nuevo";
        contenedor.appendChild(desplegable.bien);
        bien.disabled = true;
        item.nombre = bien.value
    }
    item.idTipo = tipo.value
    item.precioUnitario = costo.value
    item.cantidad = cantidad.value

    btnAgregarTabla.hidden = true;
    jsonPost.pedido.push(item);
}

boton.agregarCategoria.onclick = (e) => {
    escondeMostrandoA(seccion.msgCategoriasVacia, seccion.categoria);
    agregaEnCategoriaValorDesplegable(e, seccion.categoria, desplegable.categoria);
}

boton.razonSocial.onclick = () => {
    let esVolver = boton.razonSocial.value === "Volver";

    setDisabledProveedor(esVolver);
     !esVolver?
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
            boton.agregarTabla.hidden = contenido.value === "";
        }
    } else {
        let contenido = document.getElementById('bien');
        boton.nuevoItem.value = "Nuevo";
        contenido.remove();
        contenedor.appendChild(desplegable.bien);
    }
}

document.querySelector("select#tipoDeItem").onchange = () =>{
    let idTipo =document.querySelector("select#tipoDeItem").value;
    let desplegable =document.querySelector("select#bien");
    let url ="/api/get-item-segun-tipo/"+idTipo;

    if(desplegable){

        fetch(url)
            .then(response => response.json())
            .then(data => {
                cleanDesplegable(desplegable);
                for(let i= 0; i<data.length ; i++){
                    agregaContenidoEnDesplegable(desplegable,data[i].descripcion,false)
                }
            })
            .catch(reason => console.log(reason))
        desplegable.disabled = false;
        habilitarAgregarItem
    }
}
/*En ejecucion*/

window.eliminarFila = (nodoFila,itemSeleccionado) => {
    manejadorEliminar(nodoFila, desplegable.bien);
    jsonPost.pedido.pop(itemSeleccionado);
    esconderMostrandoOpuestosSegun(seccion.tablaDetalle, seccion.msgTablaVacia, tablaTieneElementos);
}

window.eliminaCategoria = (nodoCategoria,idCategoria) => {
    manejadorEliminar(nodoCategoria, desplegable.categoria);
    jsonPost.idCategorias.pop(idCategoria);
    esconderMostrandoOpuestosSegun(seccion.categoria, seccion.msgCategoriasVacia, hayCategoriasSeleccionadas);
}

/*Paises, provincias y ciudades */
$(document).on("change", '#pais', function (e) {
    const urlBase = "/api/get-lista-de-provincias/";
    const nombrePais = $("select#pais option:checked").text();
    const urlFinal = urlBase.concat(nombrePais);

    $.ajax({
        url: urlFinal,
        dataType: 'json',
        success: function (json) {
            const $listaProvincias = $("#provincia");
            const $listaCiudades = $("#ciudad");
            $listaCiudades.prop("disabled", true);
            $listaCiudades.empty();
            $listaCiudades.append($("<option disabled selected></option>")
                .attr("value", "").text("-- Seleccione --"));

            $listaProvincias.empty();
            $listaProvincias.append($("<option disabled selected></option>")
                .attr("value", "").text("-- Seleccione --"));
            $.each(json, function (index, item) {
                $listaProvincias.append($("<option></option>")
                    .attr("value", item.name).text(item.name));
            })
        }
    })
});

$(document).on("change", '#provincia', function (e) {
    const urlBase = "/api/get-lista-de-ciudades/";
    const nombreProvincia = $("select#provincia option:checked").text();
    const urlFinal = urlBase.concat(nombreProvincia);

    $.ajax({
        url: urlFinal,
        dataType: 'json',
        success: function (json) {
            const $listaCiudades = $("#ciudad");

            $listaCiudades.empty();
            $listaCiudades.append($("<option disabled selected></option>")
                .attr("value", "").text("-- Seleccione --"));
            $.each(json, function (index, item) {
                $listaCiudades.append($("<option></option>")
                    .attr("value", item.name).text(item.name));
            })
        }
    })
});
//PARA PROVEEDOR

/* SUBMIT */
document.getElementById("operacion-egreso").onsubmit = (e) => {
    var url = '/presupuesto';

    buildProveedor();


    console.log(jsonPost)
    e.preventDefault();

    $.ajax({
        url: url,
        type: "POST",
        data: JSON.stringify(jsonPost),
        success: function (response) {
            let modal = generaModalAlert("Operacion exitosa", "El egreso se genero correctamente.");
            let botonera = generaBotonera();
            let boton = generaBoton("Ok", recargarPagina);
            botonera.appendChild(boton);
            modal.firstElementChild.appendChild(botonera);
            document.body.appendChild(modal);
        },
        error: function () {
            let modal = generaModalAlert("Error", "Hubo un error al generar el egreso.");
            let botonera = generaBotonera();
            let boton = generaBoton("Ok", cerrarModal);
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

function generaBotonera() {
    let botonera = document.createElement("div");
    botonera.className = "d-flex jc-se"
    return botonera;
}
