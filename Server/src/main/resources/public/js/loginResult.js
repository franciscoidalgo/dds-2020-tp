import {generaBoton, generaModalAlert} from "./generales/modal.js"
import {esconderLoader} from "./generales/loader.js";

const TITULO_MENSAJE = "Error al iniciar sesión";
const CUERPO_MENSAJE = "El nombre de usuario y la contraseña que ingresaste no coinciden con nuestros registros. Por favor, revisa e inténtalo de nuevo.";

/*Funciones!*/
function generaBotonera() {
    let botonera = document.createElement("div");
    botonera.className = "d-flex jc-se"
    return botonera;
}


/*Eventos!*/
window.onload = () => {
    let modal = generaModalAlert( TITULO_MENSAJE,CUERPO_MENSAJE);
    let botonera = generaBotonera();

    //Agrego Boton a Botonera
    let boton = generaBoton("Ok", cerrarModal);
    botonera.appendChild(boton);

    //Agrego Boton a Modal
    modal.firstElementChild.appendChild(botonera);
    document.body.appendChild(modal);
}


window.cerrarModal = () => {
    let modal = document.querySelector(".modal");
    modal.remove();
    esconderLoader();
}


