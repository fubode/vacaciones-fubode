//enviar solicitud de vacacion
var url = "";
$(document).ready(function () {
    $("#enviarSolicitud").click(function (e) {
        e.preventDefault();
        var fecha_salida = document.getElementById('fecha_salida').value;
        var turno_salida = document.getElementById('turno_salida').value;
        var fecha_retorno = document.getElementById('fecha_retorno').value;
        var turno_retorno = document.getElementById('turno_retorno').value;
        var dias = document.getElementById('dias').value;
        var VACACION = document.getElementById('VACACION');
        var LICENCIA = document.getElementById('LICENCIA');
        var COMPENSACION = document.getElementById('COMPENSACION');
        var ASUELTO = document.getElementById('ASUELTO');
        var VFI = document.getElementById('VFI');
        if (COMPENSACION.checked) {
            $("#modalCompensacion").modal("show");
        } else {
            if (ASUELTO.checked) {
                $("#modalAsuelto").modal("show");
            } else {
                if (VACACION.checked || LICENCIA.checked) {
                    if (VACACION.checked) {
                        registrarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, 'VACACION', 'NINGUNO');
                    } else {
                        registrarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, 'LICENCIA', 'NINGUNO');
                    }
                } else {
                    if (VFI.checked) {
                        registrarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, 'VFI', 'DIA DEL BIENESTAR');
                    } else {
                        swal('DEBE SELECIONAR UN TIPO DE SOLICITUD');
                    }
                }
            }
        }
        $("#enviarCompensacion").click(function (e) {
            var text = document.getElementById('compensacion').value;
            registrarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, 'COMPENSACION', text);
        });
        $("#enviarAsuelto").click(function (e) {
            var text = document.getElementById('textAsuelto').value;
            registrarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, 'ASUETO', text);
        });
    });
    function registrarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle) {
        var url = "srvUsuario?accion=enviarSolicitud&"
                + "fecha_salida=" + fecha_salida
                + "&turno_salida=" + turno_salida
                + "&fecha_retorno=" + fecha_retorno
                + "&turno_retorno=" + turno_retorno
                + "&dias=" + dias
                + "&tipo=" + tipo
                + "&detalle=" + detalle
                + "&duodesimas=" + "NO"
                + "&ACEPTAR=" + "";
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'JSON',
            success: function (data) {
                //console.log(data);
                if (data.solicitud == true) {
                    confirmacionSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle, 'NO', 'ACEPTAR');
                } else {
                    console.log(data.tieneDuodesimas);
                    if (data.tieneDuodesimas == true) {
                        confirmacionSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle, 'SI', 'ACEPTAR');
                    } else {
                        swal(data.mensaje);
                    }
                }
            },
        }).done(function (data) {
        }
        ).fail(function (data) {
        });
    }


    function  confirmacionSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle, duodesimas, aceptar) {
        url = "srvUsuario?accion=enviarSolicitud&"
                + "fecha_salida=" + fecha_salida
                + "&turno_salida=" + turno_salida
                + "&fecha_retorno=" + fecha_retorno
                + "&turno_retorno=" + turno_retorno
                + "&dias=" + dias
                + "&tipo=" + tipo
                + "&detalle=" + detalle
                + "&duodesimas=" + duodesimas
                + "&ACEPTAR=" + aceptar;
        $("#modal-confirmacion").modal("show");
        /*
         swal({
         title: "ESTA SEGURO DE ENVIAR ESTA SOLICITUD?",
         text: "",
         type: "warning",
         showCancelButton: true,
         confirmButtonClass: "btn-danger",
         confirmButtonText: "SI, ENVIAR!",
         cancelButtonText: "NO, CANCELAR!",
         closeOnConfirm: false,
         closeOnCancel: false
         },
         function (isConfirm) {
         if (isConfirm) {
         console.log('funcionario');
         $.ajax({
         url: url,
         type: 'GET',
         dataType: 'JSON',
         success: function (data) {
         console.log(data);
         if (data.solicitud == true) {
         swal("Solicitud enviada correctamente");
         setTimeout(function () {
         parent.location.href = "srvUsuario?accion=pendientes"
         }, 1800);
         } else {
         swal(data.mensaje);
         }
         },
         }).done(function (data) {
         console.log('data echo');
         }
         ).fail(function (data) {
         console.log('data fail' + data.mensaje);
         });
         } else {
         swal("CANCELADO", "CANCELASTE LA SOLICITUD", "error");
         }
         });
         */
    }
});

function enviarSolicitudVacacion() {
    $("#modal-confirmacion").modal("hide");
    $("#loadingModal").modal("show");
    console.log(url);
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            console.log(data);
            $("#loadingModal").modal("hide");
            if (data.solicitud == true) {
                swal("Solicitud enviada correctamente");
                setTimeout(function () {
                    parent.location.href = "srvUsuario?accion=pendientes"
                }, 1000);
            } else {
                swal(data.mensaje);
            }
        },
    }).done(function (data) {
        $("#loadingModal").modal("hide");
        console.log('data echo');
    }
    ).fail(function (data) {
        $("#loadingModal").modal("hide");
        console.log('data fail' + data.mensaje);
    });
}