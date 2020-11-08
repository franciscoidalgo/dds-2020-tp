import {esconderLoader, mostrarLoader} from "./generales/loader.js";
import {mostrarMensaje, setIconoValidoSegun} from "./generales/mensaje.js";
import {generarModalFail} from "./generales/modal";


const boton = {
    siguiente: document.getElementById("siguiente"),
    anterior: document.getElementById("anterior")
}

let countRender = 0;
let cantMaxima = 0;
let dataMensajes = {};

/* Funciones */
function fueLeido(dataMensaje) {
    return dataMensaje.fechaEnvio.year === dataMensaje.fechaLeido.year &&
        dataMensaje.fechaEnvio.month === dataMensaje.fechaLeido.month &&
        dataMensaje.fechaEnvio.day === dataMensaje.fechaLeido.day &&
        dataMensaje.horaEnvio.hour === dataMensaje.horaLeido.hour &&
        dataMensaje.horaEnvio.minute === dataMensaje.horaLeido.minute &&
        dataMensaje.horaEnvio.second === dataMensaje.horaLeido.second;

}

function buildFila(data) {
    const tbodyMensajes = document.getElementById("seccion-mensajes");
    let tr = document.createElement("tr");

    //Discriminador
    let asuntoSeparado = data.asunto.split("--")
    let idEgreso = asuntoSeparado[0].slice(8);
    let esValida = true;
    for (let i = 0; i < asuntoSeparado.length; i++) {
        let asunto = asuntoSeparado[i].toLocaleLowerCase()
        esValida = "valida" === asuntoSeparado[i].toLocaleLowerCase()
        esValidacion(asunto) ?
            setDataARow(tr, setIconoValidoSegun(esValida)) :
            setDataARow(tr, asuntoSeparado[i]);
    }

    tr.className = "txt-left"
    tr.style.fontWeight = fueLeido(data) ? "700" : "400";
    tr.onclick = () => {
        if (idEgreso !== "") {
            getMensajeDesdeApi(idEgreso, esValida, data.mensaje, data.id)
            tr.style.fontWeight = "400";

        } else {
            //render mensajeComun
        }
    };
    tbodyMensajes.appendChild(tr);
    if (asuntoSeparado.length === 1) {
        let mensajeGenerico = tr.firstChild;
        mensajeGenerico.setAttribute("colspan", "6");
        mensajeGenerico.className = "txt-centrado";
    }
}

function setDataARow(tr, data) {
    var tdHTML = document.createElement("td");

    tdHTML.innerHTML = data;
    tr.appendChild(tdHTML);
}

function esValidacion(cadena) {

    return "valida" === cadena || "invalida" === cadena;
}

function orderByID(a, b) {
    return a.egreso.id - b.egreso.id;
}

function getMensajeDesdeApi(id, esValida, detalleValidacion, idMensaje) {
    let url = `/api/get-egreso/${id}/${idMensaje}`;

    fetch(url)
        .then(response => response.json())
        .then(data => mostrarMensaje(data, esValida, detalleValidacion))
        .catch(reason => console.log(reason));
}

function renderTabla(data) {
    let paginaHTML = document.getElementById("cant-paginas");
    let msjNoTieneMensajes = document.getElementById("no-mensajes");
    cleanTabla();

    for (let i = countRender, j = 10; j > 0 && i < data.length; i++, j--) {
        buildFila(data[i]);
        countRender = i + 1;
    }

    paginaHTML.innerText = setToStringCantPaginas();

    boton.anterior.hidden = countRender <= 10;
    boton.siguiente.hidden = countRender >= cantMaxima;

    msjNoTieneMensajes.hidden = data.length !== 0;


}

function cleanTabla() {
    const tbody = document.getElementById("seccion-mensajes");
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }
}

function setToStringCantPaginas() {
    if (cantMaxima === 0) {
        return "0 de 0"
    }
    return cantMaxima > countRender ?
        `${1 + countRender - 10}-${countRender} de ${cantMaxima}` : `${1 + countRender - cantMaxima % 10}-${cantMaxima} de ${countRender}`
}

/* Eventos */
boton.siguiente.onclick = () => {
    renderTabla(dataMensajes)
    countRender = Math.min(countRender, cantMaxima);

}

boton.anterior.onclick = () => {
    let resto = countRender % 10;
    if (resto > 0) {
        countRender -= resto + 10;
    } else {
        countRender -= 20;
        countRender = Math.max(countRender, 0);
    }

    renderTabla(dataMensajes);
}


