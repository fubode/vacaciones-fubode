var codigo = '';
function editarEntidad(btn) {
    codigo = btn.id;
    var url = "srvAdministrador?accion=obtenerEntidad&cod=" + codigo;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            console.log(data);
            document.getElementById('codigoEntidad_e').value = data.codigo_entidad;
            document.getElementById('nombre_entidad_e').value = data.nombre_entidad;
            var entidades = document.getElementById("tipoEntidad_e");
            var tipo_entidad = data.tipo_entidad;
            for (var i = 0; i < entidades.length; i++) {
                var opcion = entidades[i];

                if (opcion.value == tipo_entidad) {
                    $('#tipoEntidad_e > option[value="' + data.tipo_entidad + '"]').attr('selected', 'selected');
                }
            }

            var supervisora = document.getElementById("entidad_e");
            var entidad_supervisor = data.entidad_supervisor;
            for (var i = 0; i < supervisora.length; i++) {
                var opcion = supervisora[i];

                if (opcion.value == data.entidad_supervisor) {
                    $('#entidad_e > option[value="' + data.entidad_supervisor + '"]').attr('selected', 'selected');
                }
            }


            $("#editarEntidadModal").modal("show");
        },
    }).done(function (data) {
    }
    ).fail(function (data) {
    });
}




function editar() {
    var codigoEntidad_e = document.getElementById('codigoEntidad_e').value;
    var nombre_entidad_e = document.getElementById('nombre_entidad_e').value;
    var tipoEntidad_e = document.getElementById('tipoEntidad_e').value;
    var entidad_e = document.getElementById('entidad_e').value;
    entidadCrud(codigoEntidad_e, nombre_entidad_e, entidad_e, tipoEntidad_e, 'editar', '');
}

function agregar() {
    var codigo = document.getElementById('codigoCargo').value;
    var nombre = document.getElementById('nombre').value;
    var entidadSupervisora = document.getElementById('entidad').value;
    var tipoEntidad = document.getElementById('tipoEntidad').value;
    entidadCrud(codigo, nombre, entidadSupervisora, tipoEntidad, 'registrar', '');
}
function entidadCrud(codigo, nombre, entidadSupervisora, tipoEntidad, accion, estado) {
    var url = "srvAdministrador?accion=entidadCrud&nombre="
            + nombre + "&codigo="
            + codigo + "&entidad="
            + entidadSupervisora + "&tipo="
            + tipoEntidad + "&tipo_accion="
            + accion + "&estado="
            + estado;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            if (data.solicitud) {

                swal({
                    title: "ESTA SEGURO DE AGREGAR ESTA ENTIDAD?",
                    text: "!!!!!!!",
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
                                entidadCrud(codigo, nombre, entidadSupervisora, tipoEntidad, accion, 'ACEPTAR');
                                swal("AGREGADO!", "EL REGISTRO FUE AGREGADO", "success");
                                setTimeout(function () {
                                    parent.location.href = "srvAdministrador?accion=entidades"
                                }, 1800);
                            } else {
                                swal("CANCELADO", "EL REGISTRO NO FUE AGREGADO", "error");
                            }
                        });
            } else {
                swal(data.mensaje);
            }
        },
    }).done(function (data) {
    }
    ).fail(function (data) {
    });
}
function eliminarEntidad(btn) {
    codigo = btn.id;
    var url = "srvAdministrador?accion=eliminarEntidad&cod=" + codigo;
    swal({
        title: "ESTA SEGURO DE ELIMINAR ESTA ENTIDAD?",
        text: "!!!!!!!",
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
                    swal("ELIMINADO!", "EL REGISTRO FUE ELIMINADO", "success");
                    setTimeout(function () {
                        parent.location.href = url;
                    }, 0);
                } else {
                    swal("CANCELADO", "EL REGISTRO NO FUE ELIMINADO", "error");
                }
            });
}