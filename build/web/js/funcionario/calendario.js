$(document).ready(function () {
    document.getElementById('gestion').onchange = mostrarCalendario;
    document.getElementById('mes').onchange = mostrarCalendario;
    document.getElementById('registrar').onclick = registrarFecha;

    function mostrarCalendario() {
        var gestion = document.getElementById('gestion').value;
        var mes = document.getElementById('mes').value;
        parent.location.href = "srvUsuario?accion=actividades&gestion=" + gestion + "&mes=" + mes;
    }
    function  registrarFecha() {
        var fecha = document.getElementById('fecha').value;
        var turno = document.getElementById('turno').value;
        var entidad = document.getElementById('entidad').value;
        var detalle = document.getElementById('detalle').value;

        swal({
            title: "ESTAS SEGURO DE PROGRAMAR ESTA FECHA?",
            text: "ESTA FECHA SERA AGENDADA PARA EL CALCULO DE DIAS DE VACACION",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-danger",
            confirmButtonText: "SI, REGISTRAR!",
            cancelButtonText: "NO, CANCELAR!",
            closeOnConfirm: false,
            closeOnCancel: false
        },
                function (isConfirm) {
                    if (isConfirm) {
                        console.log("envio")
                        var url = "srvUsuario?accion=registrarFecha&"
                                + "fecha=" + fecha
                                + "&entidad=" + entidad
                                + "&turno=" + turno
                                + "&detalle=" + detalle;
                        $.ajax({
                            type: 'POST',
                            url: url,
                            async: true,
                            success: function (r) {
                                swal('Se registro correctamente la fecha');
                                parent.location.href = "srvUsuario?accion=actividades"
                            }
                        });
                        swal("ENVIADO!", "EL REGISTRO FUE PROGRAMADAO", "success");
                    } else {
                        swal("CANCELADO", "CANCELASTE EL REGISTRO", "error");
                    }
                });
    }
});

