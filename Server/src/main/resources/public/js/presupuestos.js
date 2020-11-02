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
import {getTemplateJson} from "./egresoPresupuesto.js";


/** Funciones **/
function getJsonPresupuesto() {

    let idEgreso = document.getElementById("egreso-a-asociar").value;
    let template = getTemplateJson();

    template.idEgreso = idEgreso;

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

document.getElementById("fecha").onchange = () =>{
    function cargarDataEgresos(desplegable,dataAPIEgreso){
        for(let i = 0 ; i< dataAPIEgreso.length; i++) {
            crearOptionEgreso(desplegable, dataAPIEgreso[i]);
        }
    }

    let desplegableEgreso = document.getElementById("egreso-a-asociar");
    let fecha = document.getElementById("fecha");
    let url ='/api/get-egresos-vincular/'+ fecha.value;

    desplegableEgreso.disabled = false;

     fetch(url)
         .then(response => response.json())
         .catch(reason => console.log(reason))
         .then(dataAPIEgreso => cargarDataEgresos(desplegableEgreso,dataAPIEgreso))

}

document.getElementById("presupuesto").onsubmit = (e) => {
    let url = '/presupuesto';
    let jsonPresupuesto = getJsonPresupuesto();
    let init = {
        method: 'POST',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        redirect: 'follow',
        body: JSON.stringify(jsonPresupuesto)
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


