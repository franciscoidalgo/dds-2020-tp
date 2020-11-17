import {esconderLoader, mostrarLoader} from "./generales/loader.js";
import {generarModalFail, generarModalOK} from "./generales/modal.js";

const seleccionEntidad = document.getElementById("seleccion-entidad");
const cambioEntidadOK = "Se realizo cambio de entidad satisfactoriamente. Recuerda que ahora solo podras buscar y generar operaciones de la entidad que seleccionaste, pero podras ver en resultado de validacion las operaciones de todos los egresos"
const cambioEntidadFail = "No se pudo cambiar de entidad, intenta de nuevo"
function cambiarEntidad() {
    let entidadSeleccionada = seleccionEntidad.value;
    let url = "/usuario/entidad/" + entidadSeleccionada;

    mostrarLoader();

    fetch(url)
        .then(response => response.status === 200 ? response.json():generarModalFail(cambioEntidadFail))
        .then(value => {
            esconderLoader();
            generarModalOK(cambioEntidadOK);
        })



}


seleccionEntidad.onchange = () => cambiarEntidad()