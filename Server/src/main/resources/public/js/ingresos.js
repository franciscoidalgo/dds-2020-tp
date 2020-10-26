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

    //Agrego Boton a Botonera
    boton.className="btn btn-danger";
    botonera.appendChild(boton);

    //Agrego Boton a Botonera
    boton = generaBoton("Vincular",eventoVincular);
    botonera.appendChild(boton);

    //Agrego Boton a Modal
    modal.firstElementChild.appendChild(botonera);
    document.body.appendChild(modal);
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

//Magia para vincular
function getIngreso (){
    var data = {
            "tipo": $("#tipo-ingreso").val(),
            "monto":$("#monto-total").val(),
            "descripcion": $("#descripcion").value

    };
    return data;
}

function vincular (){
    var data= {
        ingreso: getIngreso(),
        listaEgresos: [],
    };
    console.log(data);
    fetch("/ingresoAsociado", {
        method: 'POST', // potencial 'POST'
        body: JSON.stringify(data),
        headers:{
            'Content-Type': "application/json; charset=utf-f"
        }
    }).then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then(response => console.log('Success:', response));
}
