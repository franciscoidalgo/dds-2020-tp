import enviarInformacionSimple from "./generales/envioSimple.js";

const formulario = {
    apellido: document.getElementById("cambiar-apellido"),
    nombre: document.getElementById("cambiar-nombre"),
    password: document.getElementById("cambiar-password")
}

const entrada = {
    apellido: document.getElementById("apellido"),
    nombre: document.getElementById("nombre"),
    password: document.getElementById("password")
}

formulario.apellido.onsubmit = (e) => {
    let url = "/usuario/apellido";
    let apellido = entrada.apellido.value;

    console.log(apellido);
    enviarInformacionSimple(url, e, apellido);
}

formulario.nombre.onsubmit = (e) => {
    let url = "/usuario/nombre";
    let nombre = entrada.nombre.value;
    console.log(nombre);
    enviarInformacionSimple(url, e, nombre);
}

formulario.password.onsubmit = (e) => {
    let url = "/usuario/password";
    let password = entrada.password.value;
    enviarInformacionSimple(url, e, password);

}