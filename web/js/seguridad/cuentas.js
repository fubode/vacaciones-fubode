function bloquear(codigo){
        swal({
            title: "ESTA SEGURO DE BLOQUEAR LA CUENTA?",
            text: "",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-danger",
            confirmButtonText: "SI, BLOQUEAR!",
            cancelButtonText: "NO, CANCELAR!",
            closeOnConfirm: false,
            closeOnCancel: false
        },
                function (isConfirm) {
                    if (isConfirm) {
                        swal("BLOQUEAR!", "LA CUENA FUE BLOQUEADA", "success");
                        setTimeout(function () {
                            parent.location.href = "srvSeguridad?accion=bloquear&codigo=" + codigo;
                        }, 0);
                    } else {
                        swal("CANCELADO", "CANCELASTE LA ACCION", "error");
                    }
                });
    
}
function desbloquear(codigo){
    swal({
            title: "ESTA SEGURO DE DESBLOQUEAR LA CUENTA?",
            text: "",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-success",
            confirmButtonText: "SI, DESBLOQUEAR!",
            cancelButtonText: "NO, CANCELAR!",
            closeOnConfirm: false,
            closeOnCancel: false
        },
                function (isConfirm) {
                    if (isConfirm) {
                        swal("BLOQUEAR!", "LA CUENA FUE DESBLOQUEADA", "success");
                        setTimeout(function () {
                            parent.location.href = "srvSeguridad?accion=desbloquear&codigo=" + codigo;
                        }, 0);
                    } else {
                        swal("CANCELADO", "CANCELASTE LA ACCION", "error");
                    }
                });
}