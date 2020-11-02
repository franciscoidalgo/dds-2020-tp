import {getTemplateJson} from "./egresoPresupuesto.js";


/** Funciones **/
function getJsonEgreso() {

    let cantPresupuestos = document.getElementById("cantPresupuestos").value;
    let moneda = document.getElementById("moneda");
    let idTipoDePago = document.getElementById("tipo-pago").value;
    let template = getTemplateJson();

    template.cantPresupuestos = cantPresupuestos;
    template.medioDePago.moneda = contenidoSeleccionadoEn(moneda).innerText
    template.medioDePago.idTipoDePago = idTipoDePago;

    return template

}

function generaBotonera() {
    let botonera = document.createElement("div");
    botonera.className = "d-flex jc-se"
    return botonera;
}

function generarModalOK(jsonConfirmacion) {
    let modal = generaModalAlert("Operacion exitosa", "El egreso se genero correctamente.");
    let botonera = generaBotonera();
    let boton = generaBoton("Ok", recargarPagina);
    botonera.appendChild(boton);
    modal.firstElementChild.appendChild(botonera);
    document.body.appendChild(modal);
}

function generarModalFail(jsonFail) {
    let modal = generaModalAlert("Error", "Hubo un error al generar el egreso.");
    let botonera = generaBotonera();
    let boton = generaBoton("Ok", cerrarModal);
    botonera.appendChild(boton);
    modal.firstElementChild.appendChild(botonera);
    document.body.appendChild(modal);
}

/** Eventos **/


document.getElementById("operacion-egreso").onsubmit = (e) => {
    let url = '/egreso';
    let jsonEgreso = getJsonEgreso();
    let init = {
        method: 'POST',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        redirect: 'follow',
        body: JSON.stringify(jsonEgreso)
    }

    console.log(jsonEgreso)
    e.preventDefault();
    fetch(url, init)
        .then(response => response.status === 200 ? generarModalOK(response) : generarModalFail(response))
        .catch(reason => generarModalFail(reason));
}

window.cerrarModal = () => {
    let modal = document.querySelector(".modal");
    modal.remove();
}

window.recargarPagina = () => {
    desplegable.razonSocial.focus();
    desplegable.razonSocial.scrollIntoView({behavior: "smooth"});
    window.location.reload(true);
}


