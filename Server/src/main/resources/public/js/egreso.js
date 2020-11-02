import {getTemplateJson} from "./egresoPresupuesto.js";
import {contenidoSeleccionadoEn} from "./desplegable.js";
import {generarModalFail, generarModalOK} from "./modal.js";


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

    return template

}


/** Eventos **/


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

    fetch(url, init)
        .then(response => response.json())
        .then(data => generarModalOK("Se registro el egreso correctamente en el sistema. Podras identificarlo como Egreso#"+data.idEgreso))
        .catch(reason => generarModalFail("No se pudo registrar el egreso. Verifique que todos los campos esten completos y que exista al menos, un bien  agregado al detalle"))
}



