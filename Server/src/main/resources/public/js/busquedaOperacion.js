import {crearTablaEgresos, crearTablaIngresos} from './generales/tabla.js';
import {esconderLoader, mostrarLoader} from "./generales/loader.js";
import {agregaContenidoEnDesplegableConID, cleanDesplegable, contenidoSeleccionadoEn} from "./generales/desplegable.js";
import {generaCategoria} from "./generales/categoria.js";

//SECCION

const seccion = {
    seleccionCategoria: document.getElementById("seccion-seleccion-categoria"),
    seleccionCriterio: document.getElementById("seccion-seleccion-criterio"),
    operacion: document.getElementById("operacion"),
    categoriasSeleccionados: document.getElementById("seccion-categoria-seleccionadas"),
}

const desplegable = {
    criterio: document.getElementById("criterio"),
    categorias: document.getElementById("categorias"),
    operacion: document.getElementById("seleccion-operacion")
}

const boton = {
    buscarEgreso: document.getElementById("btn-busqueda-seleccionada"),
    seleccionCategoria: document.getElementById("btn-seleccionar"),
    verTodas: document.getElementById("btn-ver-todas")
}

let categoriasSeleccionadas = {idCategorias:[]}

function renderizarIngreso() {
    let url = '/ingreso/buscar/todos'
    mostrarLoader()
    fetch(url)
        .then(response => response.json())
        .catch(reason => console.log(reason))
        .then(data => {
            esconderLoader();
            armarIngreso(data);
        });

}

function renderizarEgreso() {
    let url = '/egreso/buscar/todos'
    mostrarLoader();
    fetch(url)
        .then(response => response.json())
        .catch(reason => console.log(reason))
        .then(data => {
            esconderLoader();
            armarEgreso(data)
        });

}

function armarIngreso(data) {
    let tablaIngresos = crearTablaIngresos(data);
    seccion.operacion.appendChild(tablaIngresos);
}

function armarEgreso(data) {
    let tablaEgresos = crearTablaEgresos(data);
    seccion.operacion.appendChild(tablaEgresos);
}

function agregarEnCategoria(idCategoria, idDesplegable, contenido) {
    let categoria = generaCategoria(idCategoria, contenido, true);
    seccion.categoriasSeleccionados.appendChild(categoria);
    categoria.onclick = () => {
        eliminaCategoria(categoria, idDesplegable, idCategoria)
    };//comportamiento en window
}

desplegable.operacion.onchange = () => {
    let seleccionoEgreso = desplegable.operacion.value === "ingreso";
    boton.verTodas.hidden = false;
    seccion.operacion.children[0] ? seccion.operacion.children[0].remove() : false;

    seccion.seleccionCriterio.hidden = seleccionoEgreso;
    seccion.seleccionCategoria.hidden = seleccionoEgreso;
    document.getElementById("mensaje-detalle").innerHTML = "";
};

boton.verTodas.onclick = () => {
    let seleccion = desplegable.operacion.value;
    seccion.operacion.innerHTML="";
    if (seleccion === "egreso") {
        seccion.seleccionCriterio.hidden = false;
        seccion.seleccionCategoria.hidden = false;
        renderizarEgreso();
    } else {
        renderizarIngreso();
    }

}

desplegable.criterio.onchange = () => {
    let url = "/api/categoria/" + desplegable.criterio.value;

    function cargarEnDesplegable(dataCategoria) {
        cleanDesplegable(desplegable.categorias);
        console.log(dataCategoria);
        for (let i = 0; i < dataCategoria.length; i++) {
            agregaContenidoEnDesplegableConID(dataCategoria[i].id, desplegable.categorias, dataCategoria[i].descripcion, false);
            desplegable.categorias.disabled = false;
            desplegable.categorias.hidden = false;
        }
        esconderLoader();
        desplegable.categorias.disabled = false;
    }

    mostrarLoader();
    fetch(url)
        .then(response => response.json())
        .then(data => cargarEnDesplegable(data.categorias))

}

boton.seleccionCategoria.addEventListener("click", () => {
    let idCategoria = desplegable.categorias.value;
    let descripcionCategoria = contenidoSeleccionadoEn(desplegable.categorias).innerText;
    let posEnDesplegable = desplegable.categorias.selectedIndex;


    agregarEnCategoria(idCategoria, posEnDesplegable, descripcionCategoria);
    desplegable.categorias.options[posEnDesplegable].hidden = true;
    categoriasSeleccionadas.idCategorias.push(idCategoria);
    desplegable.categorias.value = "";
    //buscar en api
})

boton.buscarEgreso.onclick = () => {
    let url = "/egreso/buscar/segun-categorias";
    let init = {
        method: 'POST',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        body: JSON.stringify(categoriasSeleccionadas)
    }
    seccion.operacion.innerHTML="";
    mostrarLoader();
    fetch(url, init)
        .then(response => response.json())
        .then(data => {
            armarEgreso(data)
            esconderLoader();
        });

}

window.eliminaCategoria = (nodoCategoria, idDesplegable, idCategoria) => {
    let indice = categoriasSeleccionadas.idCategorias.indexOf(idCategoria);
    desplegable.categorias.options[idDesplegable].hidden = false;
    categoriasSeleccionadas.idCategorias.splice(indice, 1);
    nodoCategoria.remove();
}


//TODO ARREGLAR LA PARTE DE LAS TABLAS!