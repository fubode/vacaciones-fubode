var fechaModificar = "";
var id_fechas = "";
function mostrarCalendario() {
    var gestion = document.getElementById('gestion').value;
    var mes = document.getElementById('mes').value;
    parent.location.href = "srvAdministrador?accion=calendario&gestion=" + gestion + "&mes=" + mes;
}

function mostrarFechas(btn) {
    fecha = btn.id;
    parent.location.href = "srvAdministrador?accion=mostrarFecha&fecha=" + fecha;
}

function mostrarDetalle(detalle, fecha, entidad, tipo, nombre_entidad, id) {
    fechaModificar = fecha;
    id_fechas = id;

    if (tipo == 'NO_LABORAL' && entidad == '0') {
        $(".detalle_des").css("background-color", "#dc3545");
        $(".detalle_des").css("color", "white");
    }
    if (tipo == 'NO_LABORAL' && entidad != '0') {
        $(".detalle_des").css("background-color", "#FE0000");
        $(".detalle_des").css("color", "white");
    }

    if (tipo == 'MANANA' && entidad == '0') {
        $(".detalle_des").css("background-color", "#ffc107");
        $(".detalle_des").css("color", "white");
    }
    if (tipo == 'MANANA' && entidad != '0') {
        $(".detalle_des").css("background-color", "#FEFA00");
        $(".detalle_des").css("color", "white");
    }

    if (tipo == 'TARDE' && entidad == '0') {
        $(".detalle_des").css("background-color", "#17a2b8");
        $(".detalle_des").css("color", "white");
    }
    if (tipo == 'TARDE' && entidad != '0') {
        $(".detalle_des").css("background-color", "#00D7FE");
        $(".detalle_des").css("color", "white");
    }

    if (tipo == 'SABADO' && entidad == '0') {
        $(".detalle_des").css("background-color", "#007bff");
        $(".detalle_des").css("color", "white");
    }
    if (tipo == 'SABADO' && entidad != '0') {
        $(".detalle_des").css("background-color", "#003677");
        $(".detalle_des").css("color", "white");
    }
    console.log(new Date(fecha));
    document.getElementById('fecha_modificar').value = new Date(fecha).toISOString().split('T')[0];
    document.getElementById('entidad_modificar').value = nombre_entidad;
    document.getElementById('detalle_modificar').value = detalle;
    $("#modalDetalle").modal("show");

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
                    console.log("envio");
                    var url = "srvAdministrador?accion=registrarFecha&"
                            + "fecha=" + fecha
                            + "&entidad=" + entidad
                            + "&turno=" + turno
                            + "&detalle=" + detalle;
                    $.ajax({
                        type: 'POST',
                        url: url,
                        async: true,
                        success: function (r) {
                            if (r.mensaje == 'EXITO') {
                                swal('Se registro correctamente la fecha');
                                setTimeout(function () {
                                    parent.location.href = "srvAdministrador?accion=calendario";
                                 }, 1000);
                            } else {
                                swal(r.mensaje);
                            }
                        }
                    });
                    swal("ENVIADO!", "EL REGISTRO FUE PROGRAMADAO", "success");
                } else {
                    swal("CANCELADO", "CANCELASTE EL REGISTRO", "error");
                }
            });
        }

        function eliminarFecha() {
            swal({
                title: "ESTAS SEGURO DE ELIMINAR ESTA FECHA?",
                text: "",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: "btn-danger",
                confirmButtonText: "SI, ELIMINAR!",
                cancelButtonText: "NO, CANCELAR!",
                closeOnConfirm: false,
                closeOnCancel: false
            },
                    function (isConfirm) {
                        if (isConfirm) {
                            console.log("envio");
                            var url = "srvAdministrador?accion=eliminarFecha&"
                                    + "fecha=" + id_fechas;

                            $.ajax({
                                type: 'POST',
                                url: url,
                                async: true,
                                success: function (r) {
                                    swal('Se elimino la fecha correctamente');
                                    $("#modalDetalle").modal("hide");
                                    parent.location.href = "srvAdministrador?accion=calendario";
                                }
                            });
                            swal("ELIMINADO!", "EL REGISTRO FUE ELIMINADO", "success");
                        } else {
                            swal("CANCELADO", "CANCELASTE LA ACCION", "error");
                        }
                    });
        }

        function editarFecha() {
            var detalle = document.getElementById("detalle_modificar").value;
            swal({
                title: "ESTAS SEGURO DE MODIFICAR ESTA FECHA?",
                text: "",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: "btn-danger",
                confirmButtonText: "SI, MODIFICAR!",
                cancelButtonText: "NO, CANCELAR!",
                closeOnConfirm: false,
                closeOnCancel: false
            },
                    function (isConfirm) {
                        if (isConfirm) {
                            var url = "srvAdministrador?accion=editarFecha&"
                                    + "fecha=" + id_fechas
                                    + "&detalle=" + detalle;

                            $.ajax({
                                type: 'POST',
                                url: url,
                                async: true,
                                success: function (r) {
                                    swal('Se modifico la fecha correctamente');
                                    $("#modalDetalle").modal("hide");
                                    parent.location.href = "srvAdministrador?accion=calendario";
                                }
                            });
                            swal("ELIMINADO!", "EL REGISTRO FUE MODIFICADO", "success");
                        } else {
                            swal("CANCELADO", "CANCELASTE LA ACCION", "error");
                        }
                    });
        }

