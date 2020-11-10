import {Burbuja, Desplegable} from "./generales/burbuja.js";
import {generaBoton, generaModalAlert, generarModalFail, generarModalOK} from "./generales/modal.js"
import {cleanDesplegable, contenidoSeleccionadoEn} from "./generales/desplegable.js"
import {esconderLoader, mostrarLoader} from "./generales/loader.js";


const burbujas = [
    new Burbuja('burbuja-detalle', 'detalle-ingreso'),
    new Burbuja('burbuja-egreso', 'egreso')
]

const habilitadores = [
    new Desplegable('tipo-ingreso', 'monto-total'),
    new Desplegable('monto-total', 'descripcion')
];

const input = {
    monto: document.getElementById("monto-total"),
    fechaAceptacion: document.getElementById("fecha-aceptabilidad"),
    fecha: document.getElementById("fecha"),
    descripcion: document.getElementById("descripcion"),

}
const desplegable = {
    seleccionEgreso: document.getElementById("seleccion-egresos"),
    tipoPedido: document.getElementById("tipo-ingreso")
}

const boton = {
    vincularAuto: document.getElementById("vincular-auto"),
    vincularManual: document.getElementById("agregar-egreso")
}

const seccion = {
    estaVacio: document.getElementById("msg-egreso-vacia"),
    egresosSeleccionados: document.getElementById("egresos-seleccionados"),
    saldo: document.getElementById("saldo-total")
}


var dataEgresosCargados = [];
var egresosSeleccionados = [];
var saldoTotal = 0;

/*Funciones!*/

function generaBotonera() {
    let botonera = document.createElement("div");
    botonera.className = "d-flex jc-se"
    return botonera;
}

function generarOptionEgreso(data) {
    let template = `Egreso #${data.id} (proveedor: ${data.detalle.proveedor.razonSocial}, fecha: ${data.fecha}, monto: ${data.montoTotal})`
    let option = document.createElement("option");
    option.id = data.id
    option.value = data.id;
    option.innerText = template;

    return option;
}


function agregarEgresoALista(desplegableEgreso, indexDataEgreso) {
    let contenido = document.createElement("li");
    let btn = document.createElement("button");
    let optionSeleccionado = desplegableEgreso.selectedOptions[0];
    let dataEgresoSeleccionado = dataEgresosCargados[indexDataEgreso];
    let indexAux = 0;

    //Cargo LI
    contenido.innerText = contenidoSeleccionadoEn(desplegableEgreso).innerText;
    contenido.id = desplegableEgreso.value;
    contenido.appendChild(btn)
    btn.value = "Quitar";
    btn.innerText = "Quitar";
    btn.type = "button"
    btn.className = "btn btn-formulario-danger"
    contenido.style.fontSize = "1.4rem";
    contenido.style.fontWeight = "bold";

    //Saco del desplegable
    optionSeleccionado.remove();

    //cargo el saldo
    saldoTotal -= dataEgresoSeleccionado.montoTotal;
    renderSaldo();

    //cargo en auxiliar sacando del DataEgreso, me guardo el id para deshacer la operacion
    egresosSeleccionados.push(dataEgresosCargados[indexDataEgreso]);
    dataEgresosCargados.splice(indexDataEgreso, 1);

    function undo() {
        let costSeleccionado = dataEgresoSeleccionado.montoTotal;
        //Escondo y calculo el saldo
        boton.vincularManual.hidden = true;
        saldoTotal += costSeleccionado;
        renderSaldo();
        //Saco del axiliar para colocarlo en el dataEgresos
        dataEgresosCargados.push(egresosSeleccionados[indexAux]);
        egresosSeleccionados.splice(indexAux, 1)
        //Saco LI
        contenido.remove();
        //Agrego en el desplegable el que se saco
        desplegable.seleccionEgreso.appendChild(optionSeleccionado);
        desplegable.seleccionEgreso.value = "";
    }

    btn.addEventListener("click", () => undo());
    seccion.egresosSeleccionados.appendChild(contenido);

}

function getIngresoJSON() {
    let ingresoJSON = {}
    let tipoIngreso ={
        id:desplegable.tipoPedido.value,
        nombre:contenidoSeleccionadoEn(desplegable.tipoPedido).innerText
    }
    ingresoJSON.monto = input.monto.value;
    ingresoJSON.descripcion = input.descripcion.value;
    ingresoJSON.listaEgresos = egresosSeleccionados.map(value => {return {id:value.id}});
    ingresoJSON.fechaRealizada = input.fecha.value;
    ingresoJSON.fechaAceptacion = input.fechaAceptacion.value;
    ingresoJSON.tipoIngreso = tipoIngreso;

    console.log(ingresoJSON)
    return ingresoJSON;
}

async function postIngreso(url, init) {
    mostrarLoader();
    const response = await fetch(url, init);
    if (response.status === 200) {
        const json = await response.json();
        return json;
    } else {
        generarModalFail('No se pudo registrar el ingreso. Verifique que todos los campos esten completos. En caso de haber vinculado manualmente, verifique que los egresos seleccionado no superen al costo del ingreso');
        esconderLoader()
    }

}

function enviarIngreso() {
    let url = "/ingreso";
    let jsonIngreso = getIngresoJSON();

    let init = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonIngreso)
    }

    postIngreso(url, init).then(data => {
        generarModalOK("Se registro el ingreso correctamente en el sistema. Podras identificarlo como Ingreso#" + data.idIngreso);
        esconderLoader()
    })

}

async function getEgresosVinculables(url) {
    const response = await fetch(url);
    const json = await response.json();
    return json;
}

function renderEgresosEnDesplegable() {

    let url = `/egreso/buscar/sin-vincular/${input.fechaAceptacion.value}`

    getEgresosVinculables(url).then(data => {
        cleanDesplegable(desplegable.seleccionEgreso);
        for (let i = 0; i < data.length; i++) {
            desplegable.seleccionEgreso.appendChild(generarOptionEgreso(data[i]));
        }

        desplegable.seleccionEgreso.disabled = false;
        dataEgresosCargados = data;
    });

}

function cleanInfoEgreso() {
    seccion.estaVacio.hidden = false;
    seccion.egresosSeleccionados.innerHTML = "";
    desplegable.seleccionEgreso.value = "";
    desplegable.seleccionEgreso.disabled = true;

    input.fechaAceptacion.value = "";
    input.fechaAceptacion.disabled = true;
    input.fecha.value = "";

    egresosSeleccionados = []
    dataEgresosCargados = []
}

function renderSaldo() {
    seccion.saldo.innerText = saldoTotal;
    seccion.saldo.className = saldoTotal < 0 ? "invalido" : "valido";
    seccion.estaVacio.hidden = !(saldoTotal === input.monto || egresosSeleccionados.length === 0);

}

/*Eventos!*/
burbujas.forEach((b) => {
    b.elemento.onclick = () => b.ocultaBurbuja();
})

habilitadores.forEach((d) => {
    d.elemento.onchange = () => d.habilitaObjetivo();
});


input.fecha.onchange = () => {
    let monto = input.monto.value;

    if (monto > 0) {
        desplegable.seleccionEgreso.disabled = true;
        input.fechaAceptacion.disabled = false;
        input.fechaAceptacion.value = "";
    }
}

input.descripcion.onkeyup = () => {
    let descripcion = input.descripcion.value;
    input.fechaAceptacion.disabled = descripcion.value === ""
}

input.fechaAceptacion.onchange = () => renderEgresosEnDesplegable();

boton.vincularAuto.onclick = () => {
    let modal = generaModalAlert("Realizar Vinculacion Automatica", "No se vincularan los Egresos seleccionados manualmente, ¿esta seguro de continuar?")
    let botonera = generaBotonera();
    let boton = generaBoton("Cancelar", eventoCancelar);
    let form = document.getElementById("operacion-egreso");

    //Agrego Boton a Botonera
    boton.className = "btn btn-danger";
    botonera.appendChild(boton);

    //Agrego Boton a Botonera
    boton = generaBoton("Vincular", eventoVincular);
    botonera.appendChild(boton);

    //Agrego Boton a Modal
    modal.firstElementChild.appendChild(botonera);
    document.body.appendChild(modal);
    form.onsubmit;
}

desplegable.seleccionEgreso.onchange = () => {
    boton.vincularManual.hidden = false;
}

boton.vincularManual.onclick = () => {
    let seleccionado = desplegable.seleccionEgreso;

    let indexDataEgreso = desplegable.seleccionEgreso.selectedIndex - 1;

    agregarEgresoALista(seleccionado, indexDataEgreso);
    renderSaldo();

}

input.monto.onkeyup = () => {
    let montoIngresado = input.monto.value;
    let tieneValorMonto = montoIngresado !== "";
    cleanInfoEgreso();
    saldoTotal = tieneValorMonto ? montoIngresado : 0;
    renderSaldo();
}

window.eventoVincular = () => {
    alert("Magia para vincular!");
    vincular();
    let modal = document.querySelector(".modal");
    modal.remove();
}

window.eventoCancelar = () => {
    let modal = document.querySelector(".modal");
    modal.remove();
}

window.cerrarModal = () => {
    let modal = document.querySelector(".modal");
    modal.remove();
}

window.recargarPagina = () => {
    let inicio = document.getElementById("burbuja-detalle")
    inicio.focus();
    inicio.scrollIntoView({behavior: "smooth"});
    window.location.reload(true);
}

document.getElementById("operacion-ingreso").onsubmit = (e) => {
    e.preventDefault();

    if (saldoTotal < 0) {
        generarModalFail("No puede cargar una operacion de ingreso con saldo inferior a 0. Verifique que el monto ingresado sea el correcto. Si realiza una verificacion manual, verifique que el egreso a asociar no supere al monto ingresado")
        return;
    }

    enviarIngreso();
}