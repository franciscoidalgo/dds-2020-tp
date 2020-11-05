function generaModalAlert(titulo, descripcion) {
    let contenedor = document.createElement('div');
    let contenido = document.createElement('h1');

    //H1
    contenido.innerText = titulo;
    contenedor.appendChild(contenido);

    //p
    contenido = document.createElement('p');
    contenido.innerText = descripcion;
    contenedor.appendChild(contenido);

    //Class
    contenedor.className = "modal-contenido"
    contenido.style.margin = "auto"
    contenido.style.maxWidth = "70%"

    //Modal
    contenido = contenedor;
    contenedor = document.createElement("div");
    contenedor.appendChild(contenido);
    contenedor.className = "modal"
    return contenedor;
}

function generaBoton(descripcion, evento) {
    let boton = document.createElement("button");
    boton.innerHTML = descripcion;

    boton.className = "btn btn-secundario";
    boton.id = descripcion;
    boton.onclick = () => {
        evento()
    };
    return boton;
}

function generaBotonera() {
    let botonera = document.createElement("div");
    botonera.className = "d-flex jc-se"
    return botonera;
}

function generarModalOK(mensaje) {
    let modal = generaModalAlert("¡Operacion realizada!", mensaje);
    let botonera = generaBotonera();
    let boton = generaBoton("Seguir", recargarPagina);
    let titulo = modal.firstChild.firstChild;

    botonera.appendChild(boton);
    modal.firstElementChild.appendChild(botonera);
    document.body.appendChild(modal);
    modal.className = "modal modal-success"

    titulo.innerHTML += `<i class="fas fa-check"></i>`

    return modal
}

function generarModalFail(mensaje) {
    let modal = generaModalAlert("Operacion Invalida", mensaje);
    let botonera = generaBotonera();
    let boton = generaBoton("Revisar", cerrarModal);
    let titulo = modal.firstChild.firstChild;

    botonera.appendChild(boton);
    modal.firstElementChild.appendChild(botonera);
    document.body.appendChild(modal);
    modal.className = "modal modal-fail"
    titulo.innerHTML += `<i class="fas fa-exclamation-triangle"></i>`


    return modal;
}

window.cerrarModal = () => {
    let modal = document.querySelector(".modal");
    modal.remove();
}

window.recargarPagina = () => {
    window.location.reload(true);
    window.scrollTo(0, 0)
    window.scrollIntoView({behavior: "smooth"});
}

export {generaModalAlert, generarModalFail, generarModalOK, generaBoton};