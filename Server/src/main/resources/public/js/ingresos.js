import {Burbuja,  Desplegable} from "./burbuja.js";
import {generaModalAlert,generaBoton} from "./modal.js"
import {cleanDesplegable, contenidoDesplegableEs, contenidoSeleccionadoEn} from "./desplegable.js"


const burbujas = [
    new Burbuja('burbuja-detalle','detalle-ingreso'),
    new Burbuja('burbuja-egreso','egreso')
]

const habilitadores = [
    new Desplegable('tipo-ingreso', 'monto-total'),
    new Desplegable('monto-total', 'descripcion')
];

/*Funciones!*/

function generaBotonera(){
    let botonera = document.createElement("div");
    botonera.className = "d-flex jc-se"
    return botonera;
}
function generarOptionEgreso(data) {
    let template = `Egreso #${data.id} (proveedor: ${data.detalle.proveedor.razonSocial}, fecha: ${data.fecha.day}-${data.fecha.month}+${data.fecha.year}, monto: ${data.montoTotal})`
    let option = document.createElement("option");
    option.id = data.id
    option.value = data.id;
    option.innerText = template;

    return option;
}
function cargarDesplegableCon(data){
    let desplegableEgreso = document.getElementById("seleccion-egresos");
    for(let i = 0; i < data.length;i++){
        desplegableEgreso.appendChild(generarOptionEgreso(data[i])) ;
    }
}

function cleanDesplegableEgreso(){
    let desplegableEgreso = document.getElementById("seleccion-egresos");
    cleanDesplegable(desplegableEgreso);
    desplegableEgreso.innerHTML ='<option value="" disabled selected>-- Seleccione --</option> <option value="">Ninguno</option>'
}

function agregarEgresoALista(egreso){
    let seccion = document.getElementById("egresos-seleccionados");
    let contenido = document.createElement("li");

    contenido.innerText = contenidoSeleccionadoEn(egreso).innerText;
    contenido.id = egreso.value;

    //TODO AGREGAR BORRAR
    seccion.appendChild(contenido);
}

var egresosSeleccionados=[];

/*Eventos!*/
burbujas.forEach((b)=>{
    b.elemento.onclick = () => b.ocultaBurbuja();
})

habilitadores.forEach((d) =>{
    d.elemento.onchange = () => d.habilitaObjetivo();
});


document.getElementById("fecha").onchange = ()=>{
    let valor = document.getElementById("fecha").value;
    let fechaAceptabilidad = document.getElementById("fecha-aceptabilidad");
    let desplegableEgreso =document.getElementById("seleccion-egresos");

    desplegableEgreso.disabled = true;
    fechaAceptabilidad.disabled = false;
    fechaAceptabilidad.value = "";
}

document.getElementById("fecha-aceptabilidad").onchange = ()=>{
    let fechaMax = document.getElementById("fecha-aceptabilidad").value;
    let desplegableEgreso =document.getElementById("seleccion-egresos");
    cleanDesplegableEgreso();
    let url = `/api/get-egresos-vincular/${fechaMax}`;
    console.log(url);
    fetch(url)
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then(data => cargarDesplegableCon(data));

    desplegableEgreso.disabled = false;

}



document.getElementById("vincular-auto").onclick = ()=>{
    let modal = generaModalAlert("Realizar Vinculacion Automatica","No se vincularan los Egresos seleccionados manualmente, ¿esta seguro de continuar?")
    let botonera = generaBotonera();
    let boton = generaBoton("Cancelar",eventoCancelar);
    let form = document.getElementById("operacion-egreso");

    //Agrego Boton a Botonera
    boton.className="btn btn-danger";
    botonera.appendChild(boton);

    //Agrego Boton a Botonera
    boton = generaBoton("Vincular",eventoVincular);
    botonera.appendChild(boton);

    //Agrego Boton a Modal
    modal.firstElementChild.appendChild(botonera);
    document.body.appendChild(modal);
    form.onsubmit;
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

document.getElementById("agregar-egreso").onclick = () => {
    var seleccionado = document.getElementById("seleccion-egresos");
    if(seleccionado.value !== ""){
        egresosSeleccionados.push(seleccionado.value);
    }
    agregarEgresoALista(seleccionado);

}

document.getElementById("operacion-ingreso").onsubmit = (e)=> {
    e.preventDefault();

    $.ajax({
        url : "/ingreso",
        type: "POST",
        dataType: 'text',
        data:{
            "montoTotal" : $("#monto-total").val(),
            "descripcion" : $("#descripcion").val(),
            "listaEgresos" : JSON.stringify(egresosSeleccionados),
            "fecha":$("#fecha").val(),
            "fechaAceptabilidad":$("#fecha-aceptabilidad").val()
        },
        success: function(response){
            let modal = generaModalAlert("Operacion exitosa","El ingreso se genero correctamente.");
            let botonera = generaBotonera();
            let boton = generaBoton("Ok",recargarPagina);
            botonera.appendChild(boton);
            modal.firstElementChild.appendChild(botonera);
            document.body.appendChild(modal);
        },
        error: function(){
            let modal = generaModalAlert("Error", "Hubo un error al generar el ingreso.");
            let botonera = generaBotonera();
            let boton = generaBoton("Ok",cerrarModal);
            botonera.appendChild(boton);
            modal.firstElementChild.appendChild(botonera);
            document.body.appendChild(modal);
        }
    });
}

window.cerrarModal = () => {
    let modal = document.querySelector(".modal");
    modal.remove();
}

window.recargarPagina = () => {
    window.location.reload(true);
}
