function editarFuncionario(btn) {
    var codigoSai = btn.id;
    var url = "srvAdministrador?accion=obtenerFuncionario&cod=" + codigoSai;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            document.getElementById('codigoSai_e').value = data.codigo_sai;
            document.getElementById('apellidos_e').value = data.apellido;
            document.getElementById('nombre_e').value = data.nombre;
            document.getElementById('ingreso_e').value = data.fecha_ingreso;
            document.getElementById('ci_e').value = data.ci;
            
            var e_expedido = document.getElementById("e_expedido");
            var expedido = data.expedido;
            for (var i = 0; i < e_expedido.length; i++) {
                var opcion = e_expedido[i];
                console.log(opcion.value, expedido);
                if (opcion.value == expedido) {
                    $('#e_expedido > option[value="' + data.expedido + '"]').attr('selected', 'selected');
                }
            }
            
            document.getElementById('correo_e').value = data.correo;

            var entidades = document.getElementById("entidades");
            var entidad = data.entidad;
            for (var i = 0; i < entidades.length; i++) {
                var opcion = entidades[i];
                if (opcion.value == entidad) {
                    $('#entidades > option[value="' + data.entidad + '"]').attr('selected', 'selected');
                }
            }

            var cargo = document.getElementById("cargo_e");
            var entidad = data.entidad;
            for (var i = 0; i < cargo.length; i++) {
                var opcion = cargo[i];
                if (opcion.value == data.cargo) {
                    $('#cargo_e > option[value="' + data.cargo + '"]').attr('selected', 'selected');
                }
            }

            var supervisores = document.getElementById("supervisor_e");
            var supervisor = data.supervisor;
            for (var i = 0; i < supervisores.length; i++) {
                var opcion = supervisores[i];
                if (opcion.value == data.supervisor) {
                    $('#supervisor_e > option[value="' + data.supervisor + '"]').attr('selected', 'selected');
                }
            }
            $("#editarFuncionario").modal("show");
        },
    }).done(function (data) {
    }
    ).fail(function (data) {
    });
}

function darBajaFuncionario(btn) {
    var codigo = btn.id;
    swal({
        title: "ESTA SEGURO DE DAR DE BAJA AL FUNCIONARIO?",
        text: "",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "SI, DAR DE BAJA!",
        cancelButtonText: "NO, CANCELAR!",
        closeOnConfirm: false,
        closeOnCancel: false
    },
            function (isConfirm) {
                if (isConfirm) {
                    anularSolicitud(codigo);
                    swal("DAR DE BAJA!", "EL FUNCIONARIO FUE DADO DE BAJA", "success");
                    setTimeout(function () {
                        parent.location.href = "srvAdministrador?accion=funcionarios"
                    }, 1800);
                } else {
                    swal("CANCELADO", "CANCELASTE LA ACCION", "error");
                }
            });
}

function anularSolicitud(codigoSai) {
    var url = "srvAdministrador?accion=eliminarFuncionario&codigoSai=" + codigoSai;
    $.ajax({
        type: 'POST',
        url: url,
        async: true,
        success: function (r) {

        }
    });
}

function editar() {
    var codigoSai = document.getElementById('codigoSai_e').value;
    var apellidos = document.getElementById('apellidos_e').value;
    var nombre = document.getElementById('nombre_e').value;
    var ingreso = document.getElementById('ingreso_e').value;
    var ci = document.getElementById('ci_e').value;
    var correo = document.getElementById('correo_e').value;
    var entidad = document.getElementById('entidades').value;
    var cargo = document.getElementById('cargo_e').value;
    var supervisor = document.getElementById('supervisor_e').value;
    var e_expedido = document.getElementById('e_expedido').value;
    crudFuncionario('editar', codigoSai, apellidos, nombre, ingreso, ci, correo, entidad, cargo, supervisor, '', '',e_expedido);
}

function agregar() {
    var codigoSai = document.getElementById('codigoSai').value;
    var apellidos = document.getElementById('apellidos').value;
    var nombre = document.getElementById('nombre').value;
    var ingreso = document.getElementById('ingreso').value;
    var ci = document.getElementById('ci').value;
    var expedido = document.getElementById('expedido').value;
    var correo = document.getElementById('correo').value;
    var entidad = document.getElementById('entidad').value;
    var cargo = document.getElementById('cargo').value;
    var supervisor = document.getElementById('supervisor').value;
    crudFuncionario('registrar', codigoSai, apellidos, nombre, ingreso, ci, correo, entidad, cargo, supervisor, '', '',expedido);
}

function crudFuncionario(tipo, codigoSai, apellidos, nombre, ingreso, ci, correo, entidad, cargo, supervisor, solicutud, estado,expedido) {
    var mensajeAlerta = '';
    var mensajeRespuesta = '';
    switch (tipo) {
        case 'registrar':
            mensajeAlerta = 'ESTA SEGURO DE INSERTAR ESTE NUEVO FUNCIONARIO?';
            mensajeRespuesta = 'EL REGISTRO FUE INSERTADO CORRECTAMENTE';
            break;
        case 'editar':
            mensajeRespuesta = 'EL REGISTRO FUE MODIFICADO CORRECTAMENTE';
            mensajeAlerta = 'ESTA SEGURO DE MODIFICAR LOS DATOS DE ESTE FUNCIONARIO?';
            break;
    }
    var url = "srvAdministrador?accion=crudFuncionario&codigoSai="
            + codigoSai + "&apellidos="
            + apellidos + "&nombre="
            + nombre + "&ingreso="
            + ingreso + "&ci="
            + ci + "&correo="
            + correo + "&entidad="
            + entidad + "&cargo="
            + cargo + "&supervisor="
            + supervisor + "&tipo="
            + tipo + "&solicutud="
            + solicutud + "&estado="
            + estado + "&expedido="
            + expedido;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            console.log(data);
            console.log(data.solicutud);
            if (data.solicutud) {
                swal({
                    title: mensajeAlerta,
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonClass: "btn-danger",
                    confirmButtonText: "SI!",
                    cancelButtonText: "NO, CANCELAR!",
                    closeOnConfirm: false,
                    closeOnCancel: false
                },
                        function (isConfirm) {
                            if (isConfirm) {
                                crudFuncionario(tipo, codigoSai, apellidos, nombre, ingreso, ci, correo, entidad, cargo, supervisor, 'NO', 'ACEPTAR',expedido);
                                swal("REGISTRO EXITOSO!", mensajeRespuesta, "success");
                                setTimeout(function () {
                                    parent.location.href = "srvAdministrador?accion=funcionarios";
                                }, 1800)
                            } else {
                                swal("CANCELADO", "CANCELASTE LA ACCION", "error");
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
