$(document).ready(function () {
    document.getElementById('fecha_salida').onchange = calcularDias;
    document.getElementById('fecha_retorno').onchange = calcularDias;
    document.getElementById('turno_salida').onchange = calcularDias;
    document.getElementById('turno_retorno').onchange = calcularDias;

    function p() {
        console.log("cambia")
    }
    function calcularDias() {
        diferciaTotal = 0;
        var fecha_salida = document.getElementById('fecha_salida').value;
        var fecha_retorno = document.getElementById('fecha_retorno').value;
        console.log("fecha_retorno " + fecha_retorno)
        var turno_retorno = document.getElementById('turno_retorno').value;
        var turno_salida = document.getElementById('turno_salida').value;
        var fecha_actual = new Date().toDateString();
        if (fecha_salida != '' && fecha_retorno != '') {
            var actual = new Date(fecha_actual).getTime();
            var salida = new Date(fecha_salida).getTime();
            var retorno = new Date(fecha_retorno).getTime();
            var diferencia = retorno - salida;
            diferciaTotal = retorno - salida;
            diferencia = diferencia / (1000 * 60 * 60 * 24);
            if (diferciaTotal >= 0) {
                if ((Math.trunc(salida / (1000 * 60 * 60 * 24))) >= (Math.trunc(actual / (1000 * 60 * 60 * 24)))) {
                    parent.location.href = "svrSupervisor?accion=nuevas_supervisor" +
                            "&fecha_salida=" + fecha_salida +
                            "&fecha_retorno=" + fecha_retorno +
                            "&turno_salida=" + turno_salida +
                            "&turno_retorno=" + turno_retorno +
                            "&diferencia=" + diferencia;
                } else {
                    swal('LA FECHA DE SALIDA DEBE SER SUPERIOR A LA FECHA ACTUAL');
                    document.getElementById('dias').value = 0
                    diferciaTotal = 0;
                    document.querySelector('#fecha_salida').value = '';
                }
            } else {
                swal('LA FECHA DE RETORNO DEBE SER SUPERIOR O IGUAL A LA FECHA DE SALIDA ');
                document.getElementById('dias').value = 0;
                diferciaTotal = 0;
                document.querySelector('#fecha_retorno').value = '';
            }
        } else {

        }
    }
});

window.onload = function () {
    var dias = document.getElementById('dias').value;
    console.log(dias)
    if (dias == "") {
        swal('NO PUEDE SELECCIONAR UN FERIADO COMO FECHA SALIDA O RETORNO');
        document.getElementById('dias').value = 0
        diferciaTotal = 0;
        document.querySelector('#fecha_salida').value = '';
        document.querySelector('#fecha_retorno').value = '';
        console.log(dias)
    }
}
