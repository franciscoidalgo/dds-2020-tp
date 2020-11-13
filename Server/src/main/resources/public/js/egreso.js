import {getTemplateJson, montoTotal} from "./generales/egresoPresupuesto.js";
import {contenidoSeleccionadoEn} from "./generales/desplegable.js";
import {generarModalFail, generarModalOK} from "./generales/modal.js";
import {esconderLoader, mostrarLoader} from "./generales/loader.js";

const form ={
    egreso:document.getElementById("operacion-egreso")
}

const entrada = {
    cantPresupuestos:document.getElementById("cantPresupuestos"),

}

const desplegable = {
    moneda: document.getElementById("moneda"),
    idTipoDePago:document.getElementById("tipo-pago")
}

const MENSAJE_OK = "Se registro el egreso correctamente en el sistema. Podras identificarlo como Egreso#"
const MENSAJE_FAIL = "No se pudo registrar el egreso. Verifique que todos los campos esten completos y que exista al menos, un bien  agregado al detalle";


/** Funciones **/
function getJsonEgreso() {
    let template = getTemplateJson();
    let medioDePago = {}
    template.cantPresupuestos = entrada.cantPresupuestos.value;
    medioDePago.moneda = contenidoSeleccionadoEn(desplegable.moneda).innerText
    medioDePago.idTipoDePago = desplegable.idTipoDePago.value;
    template.medioDePago = medioDePago;

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
        generarModalFail(MENSAJE_FAIL)
        esconderLoader();
    }
return false;
}

form.egreso.onsubmit = (e) => {
    e.preventDefault();
    e.stopPropagation();
    let url = '/egreso/nuevo';
    let jsonEgreso = getJsonEgreso();
    let init = {
        method: 'POST',
        contentType:'multipart/form-data',
        body: JSON.stringify(jsonEgreso)
    }



    if (montoTotal <= 0) {
        generarModalFail(MENSAJE_FAIL);
    } else {
        postEgreso(url, init).then(data => {
            generarModalOK(MENSAJE_OK + data.idEgreso)
            esconderLoader();
           if(document.getElementById("tipo-comprobante").value !== "1"){
                postImage();
            }
        })
    }

}

function postImage() {
    var data = new FormData();
    var files = $('#comprobante')[0].files;
    if(files.length > 0){
        data.append('file', files[0]);
        $.ajax({
            url: "/imagen-comprobante",
            type: "POST",
            contentType: false,
            processData: false,
            data: data,
            redirect:false
        });
    }else{
        console.log("No se selecciono ninguna imagen");
    };
}
