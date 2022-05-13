document.querySelectorAll(".anularSolicitud").forEach(el => {
    el.addEventListener("click", e => {
        const id = e.target.getAttribute("id");
        console.log(id);
        var cod = id;
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
                        anularSolicitud(cod);
                        swal("ANULADO!", "EL REGISTRO FUE ANULADO", "success");
                        setTimeout(function () {
                            parent.location.href = "srvUsuario?accion=pendientes"
                        }, 1800);
                    } else {
                        swal("CANCELADO", "CANCELASTE LA ANULACION", "error");
                    }
                });
    });
});

function anularSolicitud(cod) {
    var url = "srvUsuario?accion=anular&cod=" + cod;
    console.log("eliminado");
    $.ajax({
        type: 'POST',
        url: url,
        async: true,
        success: function (r) {

        }
    });
}