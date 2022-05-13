function actualizar(){
    var usuario =document.getElementById('usuario').value;
    var actual =document.getElementById('actual').value;
    var repetir =document.getElementById('repetir').value;
    console.log(usuario,actual,repetir);
    if(usuario==''){
        alert('Debe ingresar un usario');
    }else{
        if(actual==repetir){
             swal({
            title: "ESTA SEGURO DE ACTUALIZAR LA CONTRASEÑA?",
            text: "",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-danger",
            confirmButtonText: "SI, ACTUALIZAR!",
            cancelButtonText: "NO, CANCELAR!",
            closeOnConfirm: false,
            closeOnCancel: false
        },
                function (isConfirm) {
                    if (isConfirm) {
                        swal("ACTUALIZADO!", "LA CONTRASEÑA FUE ACTUALIZADA", "success");
                            parent.location.href = "srvUsuario?accion=actualizar&usuario="+usuario+"&pass="+actual;   
                    } else {
                        swal("CANCELADO", "CANCELASTE LA ACTUALIZACION", "error");
                    }
                });
        }else{
            alert('Las contraseñas no son iguales');
        }
    }
}