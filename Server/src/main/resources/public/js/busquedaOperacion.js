import {crearTablaEgresos, crearTablaIngresos} from './generales/tabla.js';
import {esconderLoader, mostrarLoader} from "./generales/loader.js";
import {
    agregaContenidoEnDesplegableConID,
    cleanDesplegable
} from "./generales/desplegable.js";

//SECCION
const seccionSeleccionCategoria = document.getElementById("seccion-seleccion-categoria");
const seccionSeleccionCriterio = document.getElementById("seccion-seleccion-criterio");
const seccionOperacion = document.getElementById("operacion");

const desplegable = {
    criterio: document.getElementById("criterio"),
    categorias: document.getElementById("categorias")
}

function renderizarIngreso() {
    let url = '/api/ingreso/todos'
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
    let url = '/api/egresos/todos'
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
    seccionOperacion.appendChild(tablaIngresos);
}

function armarEgreso(data) {
    let tablaEgresos = crearTablaEgresos(data);
    seccionOperacion.appendChild(tablaEgresos);
}

document.getElementById("seleccion-operacion").onchange = () => {
    let btn = document.getElementById("btn-seleccion");
    btn.hidden = false;
    seccionOperacion.children[0] ? seccionOperacion.children[0].remove() : false
};

document.getElementById("btn-seleccion").onclick = () => {
    let seleccion = document.getElementById("seleccion-operacion").value;


    if (seleccion === "egreso") {
        console.log("debo buscar en la api criterios y categorias");
        seccionSeleccionCriterio.hidden = false;
        seccionSeleccionCategoria.hidden = false;
        renderizarEgreso();
    } else {
        renderizarIngreso();
    }

}




desplegable.criterio.onchange = ()=>{
    let url = "/api/categoria/"+desplegable.criterio.value;

    function cargarEnDesplegable(dataCategoria) {
        cleanDesplegable(desplegable.categorias);
        console.log(dataCategoria);
        for (let i = 0; i < dataCategoria.length; i++) {
            agregaContenidoEnDesplegableConID(dataCategoria[i].id,desplegable.categorias, dataCategoria[i].descripcion, false);
        }
        esconderLoader();
        desplegable.categorias.disabled = false;
    }
    mostrarLoader();
    fetch(url)
        .then(response => response.json())
        .then(data => cargarEnDesplegable(data.categorias))

}
