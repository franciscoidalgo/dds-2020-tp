let datajson = {
    proveedor: {
        razonSocial: "",
        cuit: 0,
        pais: "dadad",
        provincia: "adada",
        ciudad: "dadad",
        altura: 0,
        calle: "adadad",
        piso: "adada",
        dpto: "dadad",
        id: -1
    },
    pedido: [{
        nombre: "",
        idTipo: 0,
        cantidad: 0,
        precioUnitario: 0.00
    }, {
        nombre: "",
        idTipo: 0,
        cantidad: 0,
        precioUnitario: 0.00
    }],
    medioDePago: {
        moneda: "dadadadad",
        idTipoDePago: 1
    },
    idCategorias: [{id:1},{id:2},{id:3},{id:4}],
    comprobante: {
        tipoComprobante: 1,
        path: "asdasdasdasd"
    },
    fecha: "2021-02-28",
    cantPresupuestos:10
}


window.onload = (e) => {
    var url = '/egreso';

    e.preventDefault();
    $.ajax({
        url: url,
        type: "POST",
        data: JSON.stringify(datajson),
        success: function (response) {
            console.log("ook")
        },
        error: function () {
            console.log("puta madre")
        }
    });

}
