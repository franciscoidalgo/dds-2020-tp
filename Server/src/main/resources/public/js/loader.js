import {esconderLoader,mostrarLoader} from "./generales/loader.js";


window.onloadstart =() => mostrarLoader()
window.onload =() => esconderLoader();
