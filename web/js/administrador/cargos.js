var cod = '';
function editarCargo(btn) {
    cod = btn.id;
    console.log(cod);
    var url = "srvAdministrador?accion=obtenerCargo&cod=" + cod;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            $("#editar_cargo").modal("show");
            document.getElementById('e_codigoCargo').value = data.codigo_cargo;
            document.getElementById('e_nombreCargo').value = data.nombre_cargo;
        },

    }).done(function (data) {
    }
    ).fail(function (data) {
        swal("ERROR", "OCURRIO UN PROBLEMA", "error");
    });
}

function editar() {
    var codigo_cargo = document.getElementById('e_codigoCargo').value;
    var nombre_cargo = document.getElementById('e_nombreCargo').value;
    swal({
        title: "ESTA SEGURO DE EDITAR ESTE CARGO?",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "SI, EDITAR!",
        cancelButtonText: "NO, CANCELAR!",
        closeOnConfirm: false,
        closeOnCancel: false
    },
            function (isConfirm) {
                if (isConfirm) {
                    registrar(codigo_cargo, nombre_cargo, 'editar');
                    swal("AGREGADO!", "EL REGISTRO FUE AGREGADO", "success");
                    setTimeout(function () {
                        parent.location.href = "srvAdministrador?accion=cargos"
                    }, 1800);
                } else {
                    swal("CANCELADO", "EL REGISTRO NO FUE AGREGADO", "error");
                }
            });
}


function eliminarCargo(btn) {
    cod = btn.id;
    swal({
        title: "ESTA SEGURO DE ANULAR LA SOLICITUD?",
        text: "UNA VEZ ANULADO NO PODRA RECUPERARLO ",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "SI, ANULAR!",
        cancelButtonText: "NO, CANCELAR!",
        closeOnConfirm: false,
        closeOnCancel: false
    },
            function (isConfirm) {
                if (isConfirm) {
                    var url = "srvAdministrador?accion=elimarCargo&cod=" + cod;
                    $.ajax({
                        type: 'POST',
                        url: url,
                        async: true,
                        success: function (r) {

                        }
                    });
                    swal("ELIMINAR!", "EL CARGO FUE ELIMINADO", "success");
                    setTimeout(function () {
                        parent.location.href = "srvAdministrador?accion=cargos"
                    }, 1000);
                } else {
                    swal("CANCELADO", "CANCELASTE LA ACCION", "error");
                }
            });
}


function registrarCargo() {

    var codigo = document.getElementById('codigoCargo').value;
    var nombre = document.getElementById('nombre').value;
    $("#cargoNuevo").modal("hide");
    if (codigo != '' && nombre != '') {
        swal({
            title: "ESTA SEGURO DE AGREGAR ESTE CARGO?",
            text: "UNA VEZ ANULADO NO PODRA RECUPERARLO ",
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
                        registrar(codigo, nombre, 'registrar');
                        swal("AGREGADO!", "EL REGISTRO FUE AGREGADO", "success");
                            parent.location.href = "srvAdministrador?accion=cargos"
                    } else {
                        swal("CANCELADO", "EL REGISTRO NO FUE AGREGADO", "error");
                    }
                });
    } else {
        swal("Los campos codigo y noombre deben ser llenados correctamente");
    }
}
function registrar(codigo, nombre, tipo) {
    var url = "srvAdministrador?accion=registrarCargo&nombre=" + nombre + "&codigo=" + codigo + "&tipo=" + tipo;
    $.ajax({
        type: 'POST',
        url: url,
        async: true,
        success: function (r) {

        }
    });
}