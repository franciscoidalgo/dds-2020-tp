import {crearTablaEgresos, crearTablaIngresos} from './generales/tabla.js';

const seccionSeleccionCategoria = document.getElementById("seccion-seleccion-categoria");
const seccionSeleccionCriterio = document.getElementById("seccion-seleccion-criterio");
const seccionOperacion = document.getElementById("operacion");

function renderizarIngreso() {
    let url = '/api/get-ingreso'

    fetch(url)
        .then(response => response.json())
        .catch(reason => console.log(reason))
        .then(data => armarIngreso(data));

}

function renderizarEgreso() {
    let url = '/api/get-egresos'

    fetch(url)
        .then(response => response.json())
        .catch(reason => console.log(reason))
        .then(data => armarEgreso(data));

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
        // seccionSeleccionCriterio.hidden = false;
        // seccionSeleccionCategoria.hidden = false;
        renderizarEgreso();
    } else {
        renderizarIngreso();
    }

}



