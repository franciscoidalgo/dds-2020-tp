import {Desplegable} from "../generales/burbuja.js";
import {agregaContenidoEnDesplegable, cleanDesplegable, contenidoSeleccionadoEn} from "../generales/desplegable.js";

const habilitadores = [
    new Desplegable('pais', 'provincia'),
    new Desplegable('provincia', 'ciudad')
];

const desplegable = {
    seleccionEntidad: document.getElementById("seleccion-tipo-entidad"),
    pais: document.getElementById('pais'),
    provincia: document.getElementById('provincia'),
    ciudad: document.getElementById('ciudad')
}

const entrada = {
    nombreFicticio: document.getElementById("nombre-ficticio"),
    descripcion: document.getElementById("descripcion"),
    razonSocial: document.getElementById("razon-social"),
    igj: document.getElementById("igj"),

    cuit: document.getElementById("cuit"),
    actividad: document.getElementById("actividad"),
    cantPersonal: document.getElementById("cant-personal"),
    promVenta: document.getElementById("prom-venta")
}

let seccionesContenedoras = document.getElementsByClassName("contenedor-entrada");


function habilitarEntradaParaEntidad(value) {
    for (let i = 0; i < seccionesContenedoras.length; i++) {
        seccionesContenedoras[i].hidden = false;
    }
    entrada.nombreFicticio.disabled = value === "";
    entrada.descripcion.disabled = value === "";
    entrada.igj.disabled = value === "base";
    entrada.cuit.disabled = value === "base";
    entrada.razonSocial.disabled = value === "base";

    entrada.actividad.disabled = value === "base" || value === "os";
    entrada.cantPersonal.disabled = value === "base" || value === "os";
    entrada.promVenta.disabled = value === "base" || value === "os";
}

function cargarDesplegableDesdeAPIML(desplegable, url) {

    function cargarEnDesplegable(dataAPIML) {
        cleanDesplegable(desplegable);
        for (let i = 0; i < dataAPIML.length; i++) {
            agregaContenidoEnDesplegable(desplegable, dataAPIML[i].name, false);
        }
    }

    fetch(url)
        .then(response => response.json())
        .catch(reason => console.log(reason))
        .then(data => cargarEnDesplegable(data))


}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
habilitadores.forEach((d) => {
    d.elemento.onchange = () => d.habilitaObjetivo();
});
desplegable.pais.onchange = () => {
    let pais = contenidoSeleccionadoEn(desplegable.pais).innerText;
    let url = "/api/provincias/" + pais;
    cargarDesplegableDesdeAPIML(desplegable.provincia, url);
    desplegable.provincia.disabled = false;
}

desplegable.provincia.onchange = () => {
    let provincia = contenidoSeleccionadoEn(desplegable.provincia).innerText;
    let url = "/api/ciudades/" + provincia;
    cargarDesplegableDesdeAPIML(desplegable.ciudad, url);
    desplegable.ciudad.disabled = false;
}
desplegable.seleccionEntidad.onchange = () => {
    let valorDesplegable = desplegable.seleccionEntidad.value;
    habilitarEntradaParaEntidad(valorDesplegable);
}


