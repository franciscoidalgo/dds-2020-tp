const seccionSeleccionCategoria =document.getElementById("seccion-seleccion-cate");
const seccionSeleccionCriterio =document.getElementById("seccion-seleccion-criterio");


    document.getElementById("seleccion-operacion").onchange = () =>{
    let seleccion = document.getElementById("seleccion-operacion").value;

    if(seleccion === "egreso"){
        console.log("debo buscar en la api criterios y categorias");
        seccionSeleccionCriterio.hidden = false;
        seccionSeleccionCategoria.hidden = false;
    }else{
        console.log("debo solo mostrar ingresos");

    }

}



