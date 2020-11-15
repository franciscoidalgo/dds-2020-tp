import {esconderLoader, mostrarLoader} from "./generales/loader.js";
import {cleanDesplegable, contenidoSeleccionadoEn} from "./generales/desplegable.js";
import {generaBoton, generaModalAlert, generarModalFail, generarModalOK} from "./generales/modal.js";


const desplegable = {
    seleccionEgreso: document.getElementById("seleccion-egresos"),
    seleccionIngreso: document.getElementById("seleccion-ingreso")
}

const boton = {
    vincularAuto: document.getElementById("vincular-auto"),
    vincularManual: document.getElementById("agregar-egreso"),
    seleccionEgreso: document.getElementById("seleccionar-egreso"),
}
const seccion = {
    estaVacio: document.getElementById("msg-egreso-vacia"),
    egresosSeleccionados: document.getElementById("egresos-seleccionados"),
    saldo: document.getElementById("saldo-total")
}

let dataIngreso = [];
let saldoTotal = 0;
let dataEgresoCargados = [];
let egresosSeleccionados = [];


//funciones
function renderSaldo() {
    seccion.saldo.innerText = saldoTotal;
    seccion.saldo.className = saldoTotal < 0 ? "invalido" : "valido";
}

function construirOptionIngreso(ingreso) {
    return ` <option value= ${ingreso.id}> Ingreso #${ingreso.id} (${ingreso.fechaRealizada} al: ${ingreso.fechaAceptacion}
                            , tipo:${ingreso.tipoIngreso.nombre}, monto: ${ingreso.monto})
                        </option>`
}

function cargarDesplegableIngresoCon(dataIngresos) {
    for (let i = 0; i < dataIngresos.length; i++) {
        let option = construirOptionIngreso(dataIngresos[i]);
        desplegable.seleccionIngreso.innerHTML += option;
    }
}

async function getEgresosVinculables(url) {
    const response = await fetch(url);
    const json = await response.json();
    return json;
}

function generarOptionEgreso(data) {
    let template = `Egreso #${data.id} (proveedor: ${data.detalle.proveedor.razonSocial}, fecha: ${data.fecha}, monto: ${data.montoTotal})`
    let option = document.createElement("option");
    option.id = data.id
    option.value = data.id;
    option.innerText = template;

    return option;
}

function cargarDesplegableEgresoCon(ingresoSeleccionado) {

    let url = `/egreso/buscar/sin-vincular/${ingresoSeleccionado.fechaAceptacion}`
    mostrarLoader();
    getEgresosVinculables(url).then(data => {
        cleanDesplegable(desplegable.seleccionEgreso);
        for (let i = 0; i < data.length; i++) {
            desplegable.seleccionEgreso.appendChild(generarOptionEgreso(data[i]));
        }
        desplegable.seleccionEgreso.disabled = false;
        dataEgresoCargados = data;
    });

    for (let i = 0; i < dataEgresoCargados.length; i++) {
        let option = generarOptionEgreso(dataEgresoCargados [i]);
        desplegable.seleccionEgreso.innerHTML += option;
    }
    esconderLoader();
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
    let url = "/ingreso/vincular";
    let jsonIngreso = {
        id: parseInt(desplegable.seleccionIngreso.value),
        listaEgresos: egresosSeleccionados.map(value => {
            return {id: value.id}
        })
    };

    let init = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonIngreso)
    }

    postIngreso(url, init).then(data => {
        generarModalOK("Se registro el ingreso correctamente la vinculacion. Podras ver los cambios en la seccion de busqueda");
        esconderLoader()
    })
    console.log(jsonIngreso);

}

function mostrarSeleccionEgreso(egreso, egresoDescripcion, pos) {
    let contenido = document.createElement("li");
    let btn = document.createElement("button");

    //Cargo LI
    contenido.innerText = egresoDescripcion;
    contenido.id = egreso.value;
    contenido.appendChild(btn)
    btn.value = "Quitar";
    btn.innerText = "Quitar";
    btn.type = "button"
    btn.className = "btn btn-formulario-danger"
    contenido.style.fontSize = "1.4rem";
    contenido.style.fontWeight = "bold";

    btn.onclick = () => {
        desplegable.seleccionEgreso.options[pos + 1].hidden = false
        desplegable.seleccionEgreso.value = "";
        boton.seleccionEgreso.hidden = true;

        egresosSeleccionados = egresosSeleccionados.filter(value => value !== egreso);
        contenido.remove();
        seccion.estaVacio.hidden = egresosSeleccionados.length !== 0;
        console.log(egresosSeleccionados);
        //Monto
        saldoTotal += egreso.montoTotal;
        renderSaldo();
    };

    seccion.egresosSeleccionados.appendChild(contenido);
}


//Eventos

desplegable.seleccionIngreso.onchange = () => {
    let posicionSeleccionado = desplegable.seleccionIngreso.selectedIndex - 1;//Recordar el seleccione
    let ingresoSeleccionado = dataIngreso[posicionSeleccionado];

    mostrarLoader();
    desplegable.seleccionEgreso.hidden = true;
    boton.seleccionEgreso.hidden = true;

    cargarDesplegableEgresoCon(ingresoSeleccionado);
    saldoTotal = ingresoSeleccionado.saldo;
    console.log(saldoTotal);
    renderSaldo();
    desplegable.seleccionEgreso.hidden = false;
}

desplegable.seleccionEgreso.onchange = () => {
    boton.seleccionEgreso.hidden = false;
}

boton.seleccionEgreso.onclick = () => {
    let posicionSeleccionada = desplegable.seleccionEgreso.selectedIndex;
    let unEgreso = dataEgresoCargados[posicionSeleccionada - 1];
    let descripcionUnEgreso = contenidoSeleccionadoEn(desplegable.seleccionEgreso).innerText;

    //Escondo y guardo
    desplegable.seleccionEgreso.options[posicionSeleccionada].hidden = true;
    desplegable.seleccionEgreso.value = "";
    boton.seleccionEgreso.hidden = true;
    egresosSeleccionados.push(unEgreso);

    //Agregar LI y el boton undo
    mostrarSeleccionEgreso(unEgreso, descripcionUnEgreso, posicionSeleccionada - 1);
    seccion.estaVacio.hidden = egresosSeleccionados.length !== 0;

    //Agrego valor al costo
    saldoTotal -= unEgreso.montoTotal;
    renderSaldo();

}

window.addEventListener("load", () => {
    let url = "/ingreso/buscar/por-vincular";
    mostrarLoader();
    fetch(url)
        .then(response => response.json())
        .then(data => {
            dataIngreso = data;
            cargarDesplegableIngresoCon(dataIngreso);
            esconderLoader();
        })
        .catch(reason => console.log(reason));
})

document.getElementById("vincular-manual").onclick = (e) => {
    e.preventDefault();

    if (saldoTotal < 0) {
        generarModalFail("No puede cargar una operacion de ingreso con saldo inferior a 0. Verifique que el monto ingresado sea el correcto. Si realiza una verificacion manual, verifique que el egreso a asociar no supere al monto ingresado")
        return;
    }

    enviarIngreso();
}

function generaBotonera() {
    let botonera = document.createElement("div");
    botonera.className = "d-flex jc-se"
    return botonera;
}



window.eventoVincular = () => {
    let modal = document.querySelector(".modal");
    let url = "/ingreso/vincular-auto/"+desplegable.seleccionIngreso.value;
    fetch(url)
        .then(response => response.json())
        .then(data=> console.log(data));
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

document.getElementById("vincular-auto").onclick = (e) => {
    let modal = generaModalAlert("Realizar Vinculacion Automatica", "No se vincularan los Egresos seleccionados manualmente, ¿esta seguro de continuar?")
    let botonera = generaBotonera();
    let boton = generaBoton("Cancelar", eventoCancelar);
    e.preventDefault();
    //Agrego Boton a Botonera
    boton.className = "btn btn-danger";
    botonera.appendChild(boton);

    //Agrego Boton a Botonera
    boton = generaBoton("Vincular", eventoVincular);
    botonera.appendChild(boton);

    //Agrego Boton a Modal
    modal.firstElementChild.appendChild(botonera);
    document.body.appendChild(modal);

}