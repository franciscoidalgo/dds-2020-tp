import {cleanDesplegable, crearOptionEgreso} from "./generales/desplegable.js";
import {getTemplateJson} from "./generales/egresoPresupuesto.js";
import {generarModalFail, generarModalOK} from "./generales/modal.js";


/** Funciones **/
function getJsonPresupuesto() {

    let idEgreso = document.getElementById("egreso-a-asociar").value;
    let template = getTemplateJson();
    template.idEgreso = idEgreso;

    return template
}

/** Eventos **/

document.getElementById("fecha").onchange = () => {
    function cargarDataEgresos(desplegable, dataAPIEgreso) {

        cleanDesplegable(desplegable);
        for (let i = 0; i < dataAPIEgreso.length; i++) {
            console.log(dataAPIEgreso[i]);
            crearOptionEgreso(desplegable, dataAPIEgreso[i]);
        }
    }

    let desplegableEgreso = document.getElementById("egreso-a-asociar");
    let fecha = document.getElementById("fecha");
    let url = '/api/get-egreso-segun-fecha/' + fecha.value;
    console.log(fecha.value);
    desplegableEgreso.disabled = false;
    desplegableEgreso.value = "";
    fetch(url)
        .then(response => response.json())
        .then(dataAPIEgreso => cargarDataEgresos(desplegableEgreso, dataAPIEgreso))
        .catch(reason => console.log(reason))

}

document.getElementById("presupuesto").onsubmit = (e) => {
    let url = '/presupuesto';
    let jsonEgreso = getJsonPresupuesto();
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
        .then(data => generarModalOK("Se registro el presupuesto correctamente en el sistema. Podras verlo en la seccion de busqueda de operacion egreso#" + data.idEgreso))
        .catch(reason => generarModalFail("No se pudo registrar el presupuesto. Verifique que todos los campos esten completos y que exista al menos, un bien  agregado al detalle"))
}




