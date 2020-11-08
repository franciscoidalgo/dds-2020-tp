import {getTemplateJson, montoTotal} from "./generales/egresoPresupuesto.js";
import {contenidoSeleccionadoEn} from "./generales/desplegable.js";
import {generarModalFail, generarModalOK} from "./generales/modal.js";
import {esconderLoader, mostrarLoader} from "./generales/loader.js";


/** Funciones **/
function getJsonEgreso() {

    let cantPresupuestos = document.getElementById("cantPresupuestos").value;
    let moneda = document.getElementById("moneda");
    let idTipoDePago = document.getElementById("tipo-pago").value;
    let template = getTemplateJson();
    let medioDePago = {}
    template.cantPresupuestos = cantPresupuestos;
    medioDePago.moneda = contenidoSeleccionadoEn(moneda).innerText
    medioDePago.idTipoDePago = idTipoDePago;
    template.medioDePago = medioDePago;

    console.log(template);
    return template

}


/** Eventos **/
async function postEgreso(url, init) {
    mostrarLoader();
    const response = await fetch(url, init);
    if (response.status === 200) {
        const json = await response.json();
        return json;
    } else {
        generarModalFail("No se pudo registrar el egreso. Verifique que todos los campos esten completos y que exista al menos, un bien  agregado al detalle")
        esconderLoader();
    }

}

document.getElementById("operacion-egreso").onsubmit = (e) => {
    let url = '/egreso';
    let jsonEgreso = getJsonEgreso();
    e.preventDefault();
    let init = {
        method: 'POST',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        body: JSON.stringify(jsonEgreso)
    }
    if(montoTotal<=0){
        generarModalFail("No se pudo registrar el egreso. Verifique que todos los campos esten completos y que exista al menos, un bien  agregado al detalle")
    }
    postEgreso(url, init).then(data => {
        generarModalOK("Se registro el egreso correctamente en el sistema. Podras identificarlo como Egreso#" + data.idEgreso)
        esconderLoader();
    })

}



