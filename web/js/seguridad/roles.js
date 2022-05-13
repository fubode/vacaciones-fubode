function noAsignar(codigo,rol){
    console.log("no asignar");
    swal({
            title: "ESTA SEGURO DE ASIGNAR ESTE ROL?",
            text: "",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-success",
            confirmButtonText: "SI, ASIGNAR!",
            cancelButtonText: "NO, CANCELAR!",
            closeOnConfirm: false,
            closeOnCancel: false
        },
                function (isConfirm) {
                    if (isConfirm) {
                        swal("ASIGNADO!", "LA EL ROL FUE ASIGNADO", "success");
                        setTimeout(function () {
                            parent.location.href ="srvSeguridad?accion=noAsignar&codigo=" + codigo+"&rol=" + rol;
                        }, 1000);
                    } else {
                        swal("CANCELADO", "CANCELASTE LA ACCION", "error");
                    }
                });
    
}
function asignar(codigo,rol){
    swal({
            title: "ESTA SEGURO DE ASIGNAR ESTE ROL?",
            text: "",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-success",
            confirmButtonText: "SI, ASIGNAR!",
            cancelButtonText: "NO, CANCELAR!",
            closeOnConfirm: false,
            closeOnCancel: false
        },
                function (isConfirm) {
                    if (isConfirm) {
                        swal("ASIGNAR!", "EL ROL FUE ASIGNADO", "success");
                        setTimeout(function () {
                            parent.location.href = "srvSeguridad?accion=asignar&codigo=" + codigo+"&rol=" + rol;
                        }, 1000);
                    } else {
                        swal("CANCELADO", "CANCELASTE LA ACCION", "error");
                    }
                });
}