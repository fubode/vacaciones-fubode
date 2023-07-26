 // Función para mostrar el alert cuando haya inactividad
    function mostrarAlert() {
        url = "srvSesion?accion=cerrar";
        $.ajax({
                        url: url,
                        type: 'GET',
                        dataType: 'JSON',
                        success: function (data) {
                           console.log(data);
                        },

                    }).done(function (data) {
                    }
                    ).fail(function (data) {
                    });
            alert('¡Su sesion fue cancelada por tiempo de inactividad, porfavor vuelva a iniciar sesion!');
        
    }

    // Variable que determina el tiempo de inactividad en milisegundos (por ejemplo, 5 minutos)
    const tiempoInactividad = 10 * 60 * 1000; // 5 minutos en milisegundos

    let tiempoInactivo;

    // Función para reiniciar el temporizador cuando haya actividad del usuario
    function reiniciarTemporizador() {
        clearTimeout(tiempoInactivo);
        tiempoInactivo = setTimeout(mostrarAlert, tiempoInactividad);
    }

    // Agregar escuchadores de eventos para la actividad del usuario
    document.addEventListener('mousemove', reiniciarTemporizador);
    document.addEventListener('keypress', reiniciarTemporizador);

    // Iniciar el temporizador al cargar la página
    reiniciarTemporizador();