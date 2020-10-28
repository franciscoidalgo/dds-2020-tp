import {Burbuja,  Desplegable} from "./burbuja.js";
import {generaModalAlert,generaBoton} from "./modal.js"

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

var egresosSeleccionados=[];

/*Eventos!*/
burbujas.forEach((b)=>{
    b.elemento.onclick = () => b.ocultaBurbuja();
})

habilitadores.forEach((d) =>{
    d.elemento.onchange = () => d.habilitaObjetivo();
});


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
    var seleccionado = document.getElementById("egreso-seleccionado").value;
    if(seleccionado != ""){
        egresosSeleccionados.push(seleccionado);
    }
}

document.getElementById("operacion-ingreso").onsubmit = (e)=> {
    var url = "/ingreso"
    e.preventDefault();

    $.ajax({
        url : url,
        dataType: 'json',
        type: "POST",
        data:{
            "montoTotal" : $("#monto-total").val(),
            "descripcion" : $("#descripcion").val(),
            "listaEgresos" : JSON.stringify(egresosSeleccionados)
        }
    })

}