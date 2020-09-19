import {agregateEnTablaSimple, tablaTieneElementos} from './tabla.js';
import {
    agregaContenidoEnDesplegable,
    contenidoDesplegableEs,
    contenidoSeleccionadoEn,
    quitarDelDesplegableSegunContenido,
    seleccionarValorPara,
} from './desplegable.js';
import generaCategoria from './categoria.js';
import generaTextbox from './textbox.js';
import {Burbuja,  Desplegable} from "./burbuja.js";

const burbujas = [
        new Burbuja('burbuja-proveedor','proveedor'),
        new Burbuja('burbuja-compra','detalle-compra'),
        new Burbuja('burbuja-pago','medio-de-pago')
]

const ocultadores = [
        new Desplegable('producto', 'agregar-tabla'),
        new Desplegable('categoria', 'agregar-categoria'),
];

const habilitadores = [
        new Desplegable('razon-social', 'vendedor'),
        new Desplegable('pais', 'provincia'),
        new Desplegable('provincia', 'ciudad'),
        new Desplegable('moneda', 'tipo-pago'),
];

/* Estados Iniciales */
const desplegable = {
    razonSocial: document.getElementById('razon-social'),
    vendedor: document.getElementById('vendedor'),
    producto: document.getElementById('producto'),
    categoria: document.getElementById('categoria'),
    comprobante: document.getElementById('tipo-comprobante')
};

const entrada = {
    dni: document.getElementById('dni'),
    comprobante: document.getElementById('comprobante')
};

const boton = {
    agregarTabla: document.getElementById('agregar-tabla'),
    agregarCategoria: document.getElementById('agregar-categoria'),
    razonSocial: document.getElementById('nueva-razon-social'),
    vendedor: document.getElementById('nuevo-vendedor'),
    nuevoItem: document.getElementById('nuevo-item')

};

const seccion = {
    tablaDetalle: document.getElementById('tabla-detalle'),
    msgTablaVacia: document.getElementById('msg-tabla-vacia'),
    categoria: document.getElementById('categorias-seleccionadas'),
    msgCategoriasVacia: document.getElementById('msg-categorias-vacia')
};


/* Funciones */
function agregaEnTablaSegunDesplegable(disparador, tabla, desplegable) {

    let nodoContenido = contenidoSeleccionadoEn(desplegable);
    let contenido = nodoContenido.innerHTML;

    agregateEnTablaSimple(tabla, contenido);
    seleccionarValorPara(desplegable, 0);

    desplegable.focus();
    tabla.scrollIntoView({ behavior: "smooth" });

    quitarDelDesplegableSegunContenido(desplegable, nodoContenido);
    disparador["srcElement"].hidden = true;
}

function agregaEnCategoriaValorDesplegable(disparador, seccion, desplegable) {

    let nodoContenido = contenidoSeleccionadoEn(desplegable);
    let contenido = nodoContenido.innerHTML;
    let categoria = generaCategoria(contenido, true);

    seccion.appendChild(categoria);
    seleccionarValorPara(desplegable, 0);

    quitarDelDesplegableSegunContenido(desplegable, nodoContenido);

    desplegable.focus();
    desplegable.scrollIntoView({ behavior: "smooth" });
    disparador["srcElement"].hidden = true;
    categoria.onclick = ()=>{eliminaCategoria(categoria)};
}


/*Eventos!*/

/*Repetidos*/
burbujas.forEach((b)=>{
    b.elemento.onclick = () => b.ocultaBurbuja();
})

ocultadores.forEach((d) =>{
    d.elemento.onchange = () => d.mostraObjetivo();
});

habilitadores.forEach((d) =>{
    d.elemento.onchange = () => d.habilitaObjetivo();
});

desplegable.comprobante.onchange = () => {
    entrada.comprobante.disabled = contenidoDesplegableEs(desplegable.comprobante, 'Ninguno');
}

/*Boton*/
boton.agregarTabla.onclick = (e) => {
    let botonNuevo = e.target["nextElementSibling"];
    let tabla = seccion.tablaDetalle;
    let contenido = document.getElementById('producto');
    seccion.tablaDetalle.hidden = false;
    seccion.msgTablaVacia.hidden = true;

    if(botonNuevo.value !== "Cancelar"){
        agregaEnTablaSegunDesplegable(e, seccion.tablaDetalle, contenido);
    }else{
        agregateEnTablaSimple(tabla, contenido.value);
        let contenedor = contenido.parentNode;
        contenido.remove();
        boton.nuevoItem.value = "Nuevo";
        contenedor.appendChild(desplegable.producto);
        document.getElementById('agregar-tabla').hidden=true;
    }


}

boton.agregarCategoria.onclick = (e) => {
    seccion.categoria.hidden = false;
    seccion.msgCategoriasVacia.hidden = true;
    agregaEnCategoriaValorDesplegable(e, seccion.categoria, desplegable.categoria);
}

boton.razonSocial.onclick = () =>{
    let contenedor = document.getElementById('razon-social').parentNode;

    if(boton.razonSocial.value !== "Volver"){
    let contenido = generaTextbox(desplegable.razonSocial);

    boton.razonSocial.setAttribute("value","Volver");
    desplegable.razonSocial.remove();

    contenedor.appendChild(contenido);
    boton.vendedor.hidden = true;

    contenido = generaTextbox(desplegable.vendedor);
    contenedor = document.getElementById('vendedor').parentNode;
    desplegable.vendedor.remove();

    contenedor.appendChild(contenido);
    entrada.dni.disabled = false;

    }else{

        boton.razonSocial.setAttribute("value","Nueva Razon Social");
        boton.vendedor.hidden = false;
        entrada.dni.disabled = true;

        contenedor.lastChild.remove();
        contenedor.appendChild(desplegable.razonSocial);

        contenedor = document.getElementById('vendedor').parentNode;

        contenedor.lastChild.remove();
        contenedor.appendChild(desplegable.vendedor);
    }

}

boton.vendedor.onclick = () =>{
    let contenedor = document.getElementById('vendedor').parentNode;

    if(boton.vendedor.value !== "Volver"){
        let contenido = generaTextbox(desplegable.vendedor);

        boton.vendedor.setAttribute("value","Volver");
        desplegable.vendedor.remove();

        contenedor.appendChild(contenido);
        boton.razonSocial.hidden = true;

        desplegable.razonSocial.disabled = true;
        entrada.dni.disabled = false;

    }else{

        boton.vendedor.setAttribute("value","Nuevo Vendedor");
        boton.razonSocial.hidden = false;
        entrada.dni.disabled = true;
        contenedor.lastChild.remove();
        contenedor.appendChild(desplegable.vendedor);
    }

}

boton.nuevoItem.onclick = ()=>{
    let contenedor = document.getElementById('producto').parentNode;
    let contenido = generaTextbox(desplegable.producto);

    boton.nuevoItem.value = "Cancelar";
    desplegable.producto.remove();
    contenedor.appendChild(contenido);

    contenido.onkeyup = ()=>{
        document.getElementById('agregar-tabla').hidden = contenido.value==="";
    }
}

/*En ejecucion*/
window.eliminarFila = (nodoFila) => {
    let contenido = nodoFila.firstElementChild.textContent;

    agregaContenidoEnDesplegable(desplegable.producto,contenido);
    nodoFila.remove();

    seccion.tablaDetalle.hidden = !tablaTieneElementos(seccion.tablaDetalle);
    seccion.msgTablaVacia.hidden = tablaTieneElementos(seccion.tablaDetalle);
}

window.eliminaCategoria = (nodoCategoria) => {
    let contenido = nodoCategoria.firstElementChild.textContent;

    agregaContenidoEnDesplegable(desplegable.categoria,contenido);
    nodoCategoria.remove();

    seccion.categoria.hidden = !seccion.categoria.childElementCount>1;
    seccion.msgCategoriasVacia.hidden = seccion.categoria.childElementCount>1;

}