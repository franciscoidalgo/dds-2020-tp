function generaModalAlert(titulo,descripcion) {
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

    //Modal
    contenido = contenedor;
    contenedor = document.createElement("div");
    contenedor.appendChild(contenido);
    contenedor.className = "modal"
    return contenedor;
}

function generaBoton(descripcion,evento){
    let boton = document.createElement("button");
    boton.innerHTML = descripcion;

    boton.className = "btn btn-secundario";
    boton.id = descripcion;
    boton.onclick = () => {evento()};
    return boton;
}

export {generaModalAlert,generaBoton};