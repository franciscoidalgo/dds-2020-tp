export class Clases {

    constructor(idElemento, idElementoObjetivo) {
        this.elemento = document.getElementById(idElemento);
        this.elementoObjetivo = document.getElementById(idElementoObjetivo);
        this.nodoIcono = this.elemento.children[1].children[0];
    }

    ocultaBurbuja(){
      this.nodoIcono.className = this.iconoSegunExpansion();
      this.elementoObjetivo.hidden = !this.elementoObjetivo.hidden;
      this.enfocarElemento()

    }


    enfocarElemento(){
        this.elementoObjetivo.focus();
        this.elementoObjetivo.scrollIntoView({ behavior: "smooth" });
    }


    iconoSegunExpansion(){
        return this.elementoObjetivo.hidden ? "fas fa-minus" : "fas fa-minus";
    }
}

export class Desplegable {
    constructor(idElemento, idElementoObjetivo) {
        this.elemento = document.getElementById(idElemento);
        this.elementoObjetivo = document.getElementById(idElementoObjetivo);
    }

    mostraObjetivo(){
        this.elementoObjetivo.hidden = false;
    }

    habilitaObjetivo(){
        this.elementoObjetivo.disabled = false;
    }

}