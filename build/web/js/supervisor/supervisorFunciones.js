cod = '';
function rechazarSolicitud(comp) {
    var id = comp.id;
    cod = id;
    $("#detalle").val('');
    $("#modalRechazar").modal("show");
}

function aceptarSolicitud(comp) {
    var cod = comp.id;
    swal({
        title: "ESTA SEGURO DE ACEPTAR LA SOLICITUD " + cod + "?",
        text: "UNA VEZ ACEPTADO NO PODRA CAMBIARLO ",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "SI, ACEPTAR!",
        cancelButtonText: "NO, CANCELAR!",
        closeOnConfirm: false,
        closeOnCancel: false
    },
            function (isConfirm) {
                if (isConfirm) {
                    modificacionSolicitud(cod, 'ACEPTADO', 'NINGUNO');
                    swal("ACEPTADO!", "LA SOLICITUD FUE ACEPTADA", "success");
                    setTimeout(function () {
                        parent.location.href = "svrSupervisor?accion=pendientes"
                    }, 1800);
                } else {
                    swal("CANCELADO", "NO ACEPTASTE LA SOLICITUD", "error");
                }
            });
}


$("#rechazarSolicitud").click(function (e) {
    var detalle = document.getElementById('detalle').value;
    if (detalle != '') {
        swal({
            title: "ESTA SEGURO DE RECHAZAR LA SOLICITUD " + cod + "?",
            text: "UNA VEZ RECHAZADO NO PODRA RECUPERARLO ",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-danger",
            confirmButtonText: "SI, RECHAZAR!",
            cancelButtonText: "NO, CANCELAR!",
            closeOnConfirm: false,
            closeOnCancel: false
        },
                function (isConfirm) {
                    if (isConfirm) {
                        modificacionSolicitud(cod, 'RECHAZADO', detalle);
                        swal("RECHAZADO!", "LA SOLICITUD FUE RECHAZADA", "success");
                        setTimeout(function () {
                            parent.location.href = "svrSupervisor?accion=pendientes"
                        }, 1800);
                    } else {
                        swal("CANCELADO", "CANCELASTE LA ANULACION", "error");
                    }
                });
    } else {
        swal('DEBE INGRESAR UN DETALLE PARA RECHAZAR LA SOLICITUD')
    }
});

function modificacionSolicitud(cod, estado, detalle) {
    var url = "svrSupervisor?accion=modificar&detalle=" + detalle + "&estado=" + estado + "&cod=" + cod;
    $.ajax({
        type: 'POST',
        url: url,
        async: true,
        success: function (r) {

        }
    });
}
