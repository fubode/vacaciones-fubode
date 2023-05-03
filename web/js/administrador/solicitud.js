function formSolicitud() {
    $("#enviarSolicitud").modal("show");
}
function enviarSolicitud() {
    const e_sai = document.getElementById("e_sai").value;
    const e_fecha_salida = document.getElementById("e_fecha_salida").value;
    const e_turno_salida = document.getElementById("e_turno_salida").value;
    const e_fecha_retorno = document.getElementById("e_fecha_retorno").value;
    const e_turno_retorno = document.getElementById("e_turno_retorno").value;
    const e_dias = document.getElementById("e_dias").value;
    const radio = $("input[type=radio][name=tipo]").filter(":checked")[0].value;
    const e_detalle = document.getElementById("e_detalle").value;

    var url = "srvUsuario?accion=enviarSolicitudRRHH&"
            + "e_sai=" + e_sai
            + "&fecha_salida=" + e_fecha_salida
            + "&turno_salida=" + e_turno_salida
            + "&fecha_retorno=" + e_fecha_retorno
            + "&turno_retorno=" + e_turno_retorno
            + "&dias=" + e_dias
            + "&tipo=" + radio
            + "&detalle=" + e_detalle;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            console.log(data);
            if (data.solicitud == true) {
                swal("Solicitud enviada correctamente");
                $("#enviarSolicitud").modal("hide");
                setTimeout(function () {
                    parent.location.href = "srvAdministrador?accion=solicitudes";
                }, 1800);
            } else {
                swal("ERROR!!!", data.mensaje, "error");
            }
        },
    }).done(function (data) {
        console.log(data.mensaje);
    }
    ).fail(function (data) {
        console.log('data fail' + data.mensaje);
        swal("ERROR!!!", data.mensaje, "error");
    });
}
