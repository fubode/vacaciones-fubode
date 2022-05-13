var codigo = '';
function editarSolicitud(btn) {
    codigo = btn.id;
    var url = "srvAdministrador?accion=editaSolicitud&cod=" + codigo;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            document.getElementById('fecha_solicitud').value = data.fecha_solicitud;
            document.getElementById('fecha_salida').value = data.fecha_salida;
            document.getElementById('fecha_retorno').value = data.fecha_retorno;
            if (data.turno_salida == 'MAÑANA') {
                $('#turno_salida > option[value="MAÑANA"]').attr('selected', 'selected');
            } else {
                $('#turno_salida > option[value="TARDE"]').attr('selected', 'selected');
            }
            if (data.turno_retorno == 'MAÑANA') {
                $('#turno_retorno > option[value="MAÑANA"]').attr('selected', 'selected');
            } else {
                $('#turno_retorno > option[value="TARDE"]').attr('selected', 'selected');
            }
            document.getElementById('dias').value = data.dias;
            var tipo = data.tipo;
            switch (tipo) {
                case "VACACION":
                    document.querySelector('#VACACION').checked = true;
                    document.getElementById('compesacion_m').style.display = "none"; // hide
                    break;
                case "LICENCIA":
                    document.querySelector('#LICENCIA').checked = true;
                    document.getElementById('compesacion_m').style.display = "none"; // hide
                    break;
                case "ASUELTO":
                    document.querySelector('#ASUELTO').checked = true;
                    document.getElementById('compesacion_m').style.display = "block"; // show
                    break;
                case "COMPENSACION":
                    document.querySelector('#COMPENSACION').checked = true;
                    document.getElementById('compesacion_m').style.display = "block"; // show
                    document.getElementById('r_compensacion').value = data.detalle_compensacion;
                    break;
            }
            switch (data.estado) {
                case "ANULADO":
                    document.getElementById('aceptado_m').style.display = "none"; // hide
                    document.getElementById('rechazado_m').style.display = "none"; // hide
                    document.getElementById('anulado_m').style.display = "block"; // show
                    break;
                case "ACEPTADO":
                    document.getElementById('aceptado_m').style.display = "block"; // show
                    document.getElementById('rechazado_m').style.display = "none"; // hide
                    document.getElementById('anulado_m').style.display = "none"; // hide
                    break;
                case "RECHAZADO":
                    document.getElementById('rechazado_m').style.display = "block"; // show
                    document.getElementById('aceptado_m').style.display = "none"; // hide
                    document.getElementById('anulado_m').style.display = "none"; // hide
                    document.getElementById('detalleRechazo').value = data.descripcion_estado;
                    break;
            }
            estadoSolicitud = data.estado;
            $("#detalle").val('');
            $("#editarSolicitud").modal("show");
            $(".modal-title").text("DETALLE RECHAZO DE SOLICITUD " + codigo);
        },

    }).done(function (data) {
    }
    ).fail(function (data) {
    });
}

function aceptarSolicitud(btn) {
    codigo = btn.id;
    swal({
        title: "ESTA SEGURO DE ACEPTAR LA SOLICITUD " + codigo + "?",
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
                    modificacionSolicitud(codigo, 'ACEPTADO', 'NINGUNO');
                    swal("ACEPTADO!", "LA SOLICITUD FUE ACEPTADA", "success");
                    setTimeout(function () {
                        parent.location.href = "srvAdministrador?accion=solicitudes";
                    }, 1800);
                } else {
                    swal("CANCELADO", "NO ACEPTASTE LA SOLICITUD", "error");
                }
            });
}

function rechazarSolicitud(btn) {
    codigo = btn.id;
    $("#detalle").val('');
    $("#modalRechazar").modal("show");
    $(".modalRechazar").text("DETALLE RECHAZO DE SOLICITUD " + codigo);
}

function rechazar_solicitud() {
    var detalle = document.getElementById('r_detalle').value;
    if (detalle != '') {
        swal({
            title: "ESTA SEGURO DE RECHAZAR LA SOLICITUD " + codigo + "?",
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
                        modificacionSolicitud(codigo, 'RECHAZADO', detalle);
                        swal("RECHAZADO!", "LA SOLICITUD FUE RECHAZADA", "success");
                        setTimeout(function () {
                            parent.location.href = "srvAdministrador?accion=solicitudes";
                        }, 0);
                    } else {
                        swal("CANCELADO", "CANCELASTE LA ANULACION", "error");
                    }
                });
    } else {
        swal('DEBE INGRESAR UN DETALLE PARA RECHAZAR LA SOLICITUD');
    }
}

function compensacion() {
    $("#compensacion_modal").modal("show");
    $(".titulo").text("DETALLE");
}

function rechazo() {
    $("#rechazo_modal").modal("show");
    $(".titulo").text("DETALLE");
}
var estadoSolicitud = "";

function modificacionSolicitud(cod, estado, detalle) {
    var url = "srvAdministrador?accion=modificacionSolicitud&detalle=" + detalle + "&estado=" + estado + "&cod=" + cod;
    $.ajax({
        type: 'POST',
        url: url,
        async: true,
        success: function (r) {

        }
    });
}

function editar() {
    var fecha_solicitud = document.getElementById('fecha_solicitud').value;
    var fecha_salida = document.getElementById('fecha_salida').value;
    var turno_salida = document.getElementById('turno_salida').value;
    var fecha_retorno = document.getElementById('fecha_retorno').value;
    var turno_retorno = document.getElementById('turno_retorno').value;
    var dias = document.getElementById('dias').value
    var tipo = $('input:radio[name=tipo]:checked').val();
    var detalle_compensacion = document.getElementById('r_compensacion').value;
    var detalle_estado = document.getElementById('detalleRechazo').value;
    var estado = estadoSolicitud;

    if (estado == 'ACEPTADO') {
        estado = document.getElementById('aceptado_m').value;
    }
    console.log(fecha_solicitud, fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle_compensacion, detalle_estado, estado)
    console.log("cod=" + codigo
            + "fecha_solicitud=" + fecha_solicitud
            + "fecha_salida=" + fecha_salida
            + "&turno_salida=" + turno_salida
            + "&fecha_retorno=" + fecha_retorno
            + "&turno_retorno=" + turno_retorno
            + "&dias=" + dias,
            +"&tipo=" + tipo,
            +"&detalle_compensacion=" + detalle_compensacion,
            +"&detalle_estado=" + detalle_estado,
            +"&estado=" + estado);
    swal({
        title: "ESTA SEGURO DE MODIFICAR ESTE FUNCIONARIO?",
        text: "",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "SI, AGREGAR!",
        cancelButtonText: "NO, CANCELAR!",
        closeOnConfirm: false,
        closeOnCancel: false
    },
            function (isConfirm) {
                if (isConfirm) {
                    modificarSolicitud(fecha_solicitud, fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle_compensacion, detalle_estado, estado);
                    swal("AGREGADO!", "EL REGISTRO FUE AGREGADO", "success");
                    setTimeout(function () {
                        parent.location.href = "srvAdministrador?accion=solicitudes"
                    }, 1800);
                } else {
                    swal("CANCELADO", "EL REGISTRO NO FUE AGREGADO", "error");
                }
            });

}

function modificarSolicitud(fecha_solicitud, fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle_compensacion, detalle_estado, estado) {
    var url = "srvAdministrador?accion=modificarSolicitud&"
            + "cod=" + codigo
            + "&fecha_solicitud=" + fecha_solicitud
            + "&fecha_salida=" + fecha_salida
            + "&turno_salida=" + turno_salida
            + "&fecha_retorno=" + fecha_retorno
            + "&turno_retorno=" + turno_retorno
            + "&dias=" + dias
            + "&tipo=" + tipo
            + "&detalle_compensacion=" + detalle_compensacion
            + "&detalle_estado=" + detalle_estado
            + "&estado=" + estado;
    $.ajax({
        type: 'POST',
        url: url,
        async: true,
        success: function (r) {
            swal('Se registro correctamente la solicitud');
            parent.location.href = "srvAdministrador?accion=solicitudes";
        }
    });
}
