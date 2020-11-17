import {esconderLoader, mostrarLoader} from "./loader.js";
import {crearTablaEgresos} from "./tabla.js";

function buildTemplateIngreso(ingreso, contenedorHTML) {
    const template = `
        <header>
            <div class="d-flex jc-sb ai-center fw-700">
                <div class="tooltip">   
                    <h2>Ingreso #${ingreso.id}</h2>      
                </div>
                <p>Fecha: ${ingreso.fechaRealizada}</p>
            </div>
        </header>
        <main>
            <section>
                <h3>Detalle de la operacion</h3>
                <div class="d-flex jc-sb">  
                    <div>
                        <p><span>Tipo ingreso:</span> ${ingreso.tipoIngreso.nombre}</p>
                        <p><span>Descripcion:</span> ${ingreso.descripcion}</p>
                        <p><span>Monto ingresado:</span>$${ingreso.monto}</p>
                        <p><span>Costo egresos vinculados:</span>$${ingreso.costo}</p>
                        <p><span>Saldo:</span>$${ingreso.saldo}</p>
                    </div>
                    <div>
                         <p><span>Fecha realizacion:</span> ${ingreso.fechaRealizada}</p>
                         <p><span>Fecha aceptabilidad:</span> ${ingreso.fechaAceptacion}</p>
                         <p><span># Egresos vinculados: </span>${ingreso.listaEgresos.length}</p>
                    </div>
                </div>
            </section>
        </main>
        <h3>Egresos Vinculados</h3>
        <p><em>Puedes hacer click en el egreso para más detalle</em></p>
        <footer id="egresos-vinculados">
           
        </footer>
    `
    contenedorHTML.innerHTML = template;
}


function renderizarIngresoDetallado(dataIngreso, contenedorHTML) {
    buildTemplateIngreso(dataIngreso, contenedorHTML);
    buildTablaEgresosVinculados(dataIngreso.listaEgresos);
    contenedorHTML.scrollIntoView({behavior: "smooth"});
}

function buildTablaEgresosVinculados(dataEgresos){
    let seccionEgresosViculados = document.getElementById("egresos-vinculados");
    seccionEgresosViculados.appendChild(crearTablaEgresos(dataEgresos));

}

export {renderizarIngresoDetallado}