import {Clases,  Desplegable} from "./clases.js";
import {generaModalAlert,generaBoton} from "./modal.js"


/*Funciones!*/

function generaBotonera(){
    let botonera = document.createElement("div");
    botonera.className = "d-flex jc-se"
    return botonera;
}



/*Eventos!*/


window.onload = ()=>{
    console.log("funca");
    let modal = generaModalAlert("Error al iniciar sesión","El nombre de usuario y la contraseña que ingresaste no coinciden con nuestros registros. Por favor, revisa e inténtalo de nuevo.")
    let botonera = generaBotonera();


    //Agrego Boton a Botonera
    let boton = generaBoton("Ok",cerrarModal);
    botonera.appendChild(boton);


     //Agrego Boton a Modal
     modal.firstElementChild.appendChild(botonera);
     document.body.appendChild(modal);
    }


window.cerrarModal = () => {
    let modal = document.querySelector(".modal");
    modal.remove();
}


