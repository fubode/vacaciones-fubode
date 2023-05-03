var aux_salida = '';
var aux_retorno = '';

$(document).ready(function () {
    document.getElementById('fecha_salida').onchange = calcularDias;
    document.getElementById('fecha_retorno').onchange = calcularDias;
    document.getElementById('turno_salida').onchange = calcularDias;
    document.getElementById('turno_retorno').onchange = calcularDias;

    document.getElementById('fecha_salida').onclick = capturarFechas;
    document.getElementById('fecha_retorno').onclick = capturarFechas;

    function capturarFechas() {
        aux_salida = document.getElementById('fecha_salida').value;
        aux_retorno = document.getElementById('fecha_retorno').value;
    }
    function calcularDias() {
        diferciaTotal = 0;
        var fecha_salida = document.getElementById('fecha_salida').value;
        var fecha_retorno = document.getElementById('fecha_retorno').value;
        var turno_retorno = document.getElementById('turno_retorno').value;
        var turno_salida = document.getElementById('turno_salida').value;
        var dif = 86400000;
        var fecha_actual = new Date().toISOString().split('T')[0];
        var actual = new Date(fecha_actual).getTime()-dif;
        var salida = new Date(fecha_salida).getTime();
        var retorno = new Date(fecha_retorno).getTime();
        
        if (salida>=actual) {
            if (fecha_salida != '' && fecha_retorno != '') {
                var diferencia = retorno - salida;
                diferciaTotal = retorno - salida;
                diferencia = diferencia / (1000 * 60 * 60 * 24);
                if (diferciaTotal >= 0) {
                    var url = "";
                    try {
                        url = "srvGeneral?accion=fechas" +
                                "&fecha_salida=" + fecha_salida +
                                "&fecha_retorno=" + fecha_retorno +
                                "&turno_salida=" + turno_salida +
                                "&turno_retorno=" + turno_retorno +
                                "&diferencia=" + diferencia +
                                "&cod=" + null;
                    } catch (e) {
                        console.log(e);
                        url = "srvGeneral?accion=fechas" +
                                "&fecha_salida=" + fecha_salida +
                                "&fecha_retorno=" + fecha_retorno +
                                "&turno_salida=" + turno_salida +
                                "&turno_retorno=" + turno_retorno +
                                "&diferencia=" + diferencia +
                                "&cod=" + null;
                    }

                    $.ajax({
                        url: url,
                        type: 'GET',
                        dataType: 'JSON',
                        success: function (data) {
                            if (data.mensaje != "EXITO") {
                                swal(data.mensaje);
                                document.querySelector('#fecha_retorno').value = aux_retorno;
                            } else {
                                //swal(data.mensaje);
                                document.getElementById('dias').value = data.dias;
                            }
                        },

                    }).done(function (data) {
                    }
                    ).fail(function (data) {
                    });

                } else {
                    swal('LA FECHA DE RETORNO DEBE SER SUPERIOR O IGUAL A LA FECHA DE SALIDA ');
                    document.getElementById('dias').value = 0;
                    diferciaTotal = 0;
                    document.querySelector('#fecha_retorno').value = aux_retorno;
                }
            } else {
            }
        } else {
            document.querySelector('#fecha_salida').value = aux_salida;
            swal('La fecha de salida debe ser mayor a la fecha actual');
        }
    }
});
