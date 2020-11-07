import {cleanDesplegable, crearOptionEgreso} from "./generales/desplegable.js";
import {getTemplateJson, montoTotal} from "./generales/egresoPresupuesto.js";
import {generarModalFail, generarModalOK} from "./generales/modal.js";
import {esconderLoader, mostrarLoader} from "./generales/loader.js";

const MENSAJE_ERROR="No se pudo registrar el presupuesto. Verifique que todos los campos esten completos y que exista al menos, un bien  agregado al detalle";
const MENSAJE_OK= "Se registro el presupuesto correctamente en el sistema. Podras verlo en la seccion de busqueda de operacion egreso#"
/** Funciones **/
function getJsonPresupuesto() {

    let idEgreso = document.getElementById("egreso-a-asociar").value;
    let template = getTemplateJson();
    template.idEgreso = idEgreso;

    return template
}

async function postPresupuesto(url, init) {
    mostrarLoader();
    const response = await fetch(url, init);
    if (response.status === 200) {
        const json = await response.json();
        return json;
    } else {
        generarModalFail(MENSAJE_ERROR);
        esconderLoader();
    }

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
    if(montoTotal<=0){
        mostrarLoader();
    }

    if(montoTotal<=0){
        generarModalFail(MENSAJE_ERROR)
    }
    postPresupuesto(url, init).then(data => {
        generarModalOK( MENSAJE_OK+ data.idEgreso)
        esconderLoader();
    })
}




