import enviarInformacionSimple from "../generales/envioSimple.js";
import {generarModalFail, generarModalOK} from "../generales/modal.js";
import {esconderLoader} from "../generales/loader.js";

const form = {
    nombreOrganizacion: document.getElementById("cambiar-nombre-organizacion")
}
const entrada = {
    nombreOrganizacion: document.getElementById("nombre-organizacion")
}

const desplegable = {
    entidades: document.getElementById("seleccion-entidad-organizacion")
}

const boton = {
    borrar: document.getElementById("btn-borrar-entidad"),
    entidades: {
        editar: document.getElementById("btn-editar-entidad"),
        borrar: document.getElementById("btn-borrar-entidad"),
        nuevo: document.getElementById("btn-nuevo-entidad")
    }
}

/* Funciones */

/* Eventos */
form.nombreOrganizacion.onsubmit = (e) => {
    let url = "/organizacion/cambiar/nombre";
    let nombre = entrada.nombreOrganizacion.value;
    enviarInformacionSimple(url, e, nombre);
}

boton.borrar.onclick = () => {
    let entidad = desplegable.entidades.value;
    let url = "/organizacion/entidad/" + entidad;
    console.log(url);

    fetch(url, {method: "DELETE"})
        .then(response => {
            if (response.status === 200) {
                const json = response.json();
                return json;
            } else {
                generarModalFail("No se pudo borrar la entidad.");
                esconderLoader();
            }
        })
        .then(data => {
            esconderLoader();
            generarModalOK("Se borro la entidad");

        })
        .catch(err => {
            esconderLoader();
            generarModalFail("No se ha podido actualizar tu informacion.");
        });

}

desplegable.entidades.onchange = () => {
    boton.entidades.borrar.hidden = false;
    boton.entidades.editar.hidden = false;
}

// TODO FALTA CAMBIAR LAS DESCRIPCIONES DE LAS ENTIDADES
// TODO FALTA CAMBIAR LOS CRITERIOS/CATEGORIA
// TODO FALTA CAMBIAR LOS USUARIOS