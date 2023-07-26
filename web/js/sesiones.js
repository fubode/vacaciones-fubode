$(document).ready(function () {
    document.getElementById('sesion').onclick = inciarSesion;
});

function inciarSesion() {
    var usuario = document.getElementById('usuario').value;
    var password = document.getElementById('password').value;
    var captcha = document.getElementById('captchaInput').value;

    const userInput = document.getElementById('captchaInput').value;
    if (userInput === window.captchaText) {
        var url = "srvSesion?accion=verificar&" +
                "usuario=" + usuario +
                "&password=" + password;

        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'JSON',
            success: function (data) {
                console.log(data);
                if (data.verficacion) {
                    parent.location.href = "srvUsuario?accion=inicio";
                } else {
                    if (data.estado == 'BLOQUEADO') {
                        alert('Su cuenta esta bloqueada, comuniquese con el administrador');
                    } else {
                        alert('Contraseña incorrecta');
                    }
                }
            },

        }).done(function (data) {
        }
        ).fail(function (data) {
            console.log("fallo");
        });
    } else {
        alert('Captcha incorrecto. Inténtalo de nuevo.');
        drawCaptcha();
    }
}


