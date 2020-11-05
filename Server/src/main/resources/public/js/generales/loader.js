function mostrarLoader(){
    document.getElementById("loader").hidden = false;
}


function esconderLoader(){
    document.getElementById("loader").hidden = true;
}


export {mostrarLoader,esconderLoader};