import {esconderLoader, mostrarLoader} from "./loader.js";
import {generarModalFail, generarModalOK} from "./modal.js";

export default function enviarInformacionSimple(url, evento, valor) {
    let init = {
        method: 'POST',
        body: valor
    }
    evento.preventDefault();
    mostrarLoader();
    fetch(url, init)
        .then(response => {
            if (response.status === 200) {
                const json = response.json();
                return json;
            } else {
                generarModalFail("No se ha podido actualizar.");
                esconderLoader();
            }
        })
        .catch(err => {
            console.log(data);
            esconderLoader();
            generarModalFail("No se ha podido actualizar.");
        })
        .then(data => {
            console.log(data);
            esconderLoader();
            generarModalOK("Se ha actualizado satisfactoriamente");

        });


}