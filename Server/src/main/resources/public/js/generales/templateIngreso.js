function buildTemplateIngreso(ingreso, contenedorHTML) {
    console.log(ingreso)
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
        <footer>
            <button id="btn-ver-egresos" class="btn btn-formulario-danger">Ver Egresos vinculados</button>
        </footer>
    `
    contenedorHTML.innerHTML = template;
}


function renderizarIngresoDetallado(dataIngreso, contenedorHTML) {
    buildTemplateIngreso(dataIngreso, contenedorHTML);
    buildBotonVerEgresosVinculados(dataIngreso);
    console.log(dataIngreso);


    contenedorHTML.scrollIntoView({behavior: "smooth"});
}

function buildBotonVerEgresosVinculados(egreso) {
    let btn = document.getElementById("btn-ver-egresos");
    btn.onclick = () => verEgresos(egreso.id);
}

function verEgresos(egresoID) {
    //let url = "/api/revisor/agregar/" + egresoID;
    /*mostrarLoader();
    fetch(url, {method: "PUT"})
        .then(response => response.json())
        .then(data => {
            esconderLoader();
        })
        .catch(reason => console.log(reason));
        */
    console.log("Debo agregar la magia aca ")
}

export {renderizarIngresoDetallado}