import {esconderLoader} from "../generales/loader.js";
import {generarModalFail, generarModalOK} from "../generales/modal.js";
import {agregaContenidoEnDesplegableConID} from "../generales/desplegable.js";

const desplegable = {
    criterios: document.getElementById("criterios"),
    criterioPadre: document.getElementById("criterios"),
    categorias: document.getElementById("categorias")
}

const entrada = {
    nombreCriterio: document.getElementById("nuevo-criterio"),
    nombreCategoria: document.getElementById("nueva-categoria")
}

const boton = {
    nuevoCriterio: document.getElementById("btn-nuevo-criterios"),
    nuevaCategoria: document.getElementById("btn-nuevo-categoria"),
    editarCriterio: document.getElementById("btn-editar-criterios")
}

function cargarDesplegableCategoria() {
    let idCriterio = desplegable.criterioPadre.value;
    let url = `/entidad/categorias/${idCriterio}`

    fetch(url)
        .then(response => response.json())
        .then(data => {
            for (let i = 0; i < data.length; i++) {
                agregaContenidoEnDesplegableConID(data[i].id, desplegable.categorias, data[i].descripcion, false)
            }
        })

}


boton.nuevoCriterio.onclick = () => {
    let idCriterio = desplegable.criterioPadre.value;
    let nombre = entrada.nombreCriterio.value;
    let url = `/entidad/criterio/nuevo/${idCriterio}/${nombre}`;

    fetch(url)
        .then(response => {
            if (response.status === 200) {
                generarModalOK("Se ha actualizado satisfactoriamente");
            } else {
                generarModalFail("No se ha podido actualizar/crear.");
                esconderLoader();
            }
        })


}

boton.nuevaCategoria.onclick = () => {
    let idCriterio = desplegable.criterioPadre.value;
    let nombre = entrada.nombreCategoria.value;
    let url = `entidad/categoria/nuevo/${idCriterio}/${nombre}`;

    fetch(url)
        .then(response => {
            if (response.status === 200) {
                generarModalOK("Se ha actualizado satisfactoriamente");
            } else {
                generarModalFail("No se ha podido actualizar/crear.");
                esconderLoader();
            }
        })


}
desplegable.criterioPadre.onchange = () => {
    let valorDesplegable = desplegable.criterioPadre.value;
    let puedeBuscarCategoria = valorDesplegable > 0 && valorDesplegable !== "";
    if (puedeBuscarCategoria) {
        cargarDesplegableCategoria()
    }
    boton.editarCriterio.hidden = !puedeBuscarCategoria;
}