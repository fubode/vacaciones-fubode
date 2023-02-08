$(document).ready(function () {
    document.getElementById('buscarFuncionario').onclick = buscarFuncionario;
    document.getElementById('codigoSay').onkeyup = selecionarCombo;
    document.getElementById('funcionarios').onchange = cambiarSay;
    document.getElementById('desde').onchange = validacionFechas;
    document.getElementById('hasta').onchange = validacionFechas;
});

function buscarFuncionario() {
    var codigoSay = document.getElementById('codigoSay').value;
    var funcionario = document.getElementById('funcionarios').value;
    var tipo = document.getElementById('tipo').value;
    var estoFuncionario = document.getElementById('estoFuncionario').value;
    var estado = document.getElementById('estado').value;
    var desde = document.getElementById('desde').value;
    var hasta = document.getElementById('hasta').value;
    var say = parseInt(funcionario);


    if(codigoSay==0){
        var url = "srvReportes?accion=reporteTodos" +
                "&codigoSay=" + codigoSay +
                "&funcionario=" + funcionario +
                "&tipo=" + tipo +
                "&estoFuncionario=" + estoFuncionario +
                "&estado=" + estado +
                "&desde=" + desde +
                "&hasta=" + hasta;
        window.open(url, '_blank');
        
    }else{
        var url = "srvReportes?accion=reporteFuncionario" +
                "&codigoSay=" + codigoSay +
                "&funcionario=" + funcionario +
                "&tipo=" + tipo +
                "&estoFuncionario=" + estoFuncionario +
                "&estado=" + estado +
                "&desde=" + desde +
                "&hasta=" + hasta;
        window.open(url, '_blank');
    }
    /*
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            window.open('srvReportes?accion=reporteFun1&data='+data, '_blank');
            limpiarHoja();
            var newDiv = document.createElement("div");
            newDiv.className += "div_datos ";

            var titulo = document.createElement('h3');
            titulo.innerHTML = 'REPORTE FUNCIONARIO';
            titulo.className += "text-center ";

            var intervalo = document.createElement('h4');
            let date = new Date();
            intervalo.innerHTML = '(Practicado del ' + data.intervalo + ')';
            intervalo.className += "text-center ";

            newDiv.appendChild(titulo);
            newDiv.appendChild(intervalo);

            var divNivel1 = document.createElement("div");
            divNivel1.className += "container-fluid m-4 ";

            newDiv.appendChild(divNivel1);

            var divNivel2 = document.createElement("div");
            divNivel2.className += "row ";

            divNivel1.appendChild(divNivel2);

            var divDatos = document.createElement("div");
            divDatos.className += "col-6 datos ";


            var divInformacion = document.createElement("div");
            divInformacion.className += "col-6 informacion ";

            divNivel2.appendChild(divDatos);
            divNivel2.appendChild(divInformacion);

            document.getElementById('reporte').appendChild(newDiv);

            if (say == 0) {
                agregarLinea(divDatos, 'Tipo', data.tipo);
                agregarLinea(divDatos, 'Estado', data.estado);
                agregarLinea(divDatos, 'Emitido', date.toLocaleDateString());
                agregarLinea(divDatos, 'Expedido por', data.funcionario);
                agregarTablaFuncionarios(data.solicitudes);
            } else {
                agregarLinea(divDatos, 'Nombre', data.nombreCompleto);
                agregarLinea(divDatos, 'Cargo', data.cargo);
                agregarLinea(divDatos, 'Entidad', data.entidad);
                agregarLinea(divDatos, 'Correo', data.correo);
                agregarLinea(divDatos, 'Tipo', data.tipo);
                agregarLinea(divDatos, 'Estado', data.estado);
                agregarLinea(divDatos, 'Emitido', date.toLocaleDateString());
                agregarLinea(divDatos, 'Expedido por', data.funcionario);
                agregarLinea(divInformacion, 'Fecha de Ingreso', data.fechaIngreso);
                agregarLinea(divInformacion, 'Antiguedad', data.antiguedad);
                agregarLinea(divInformacion, 'Vacaciones Cumplidas', data.vacacionesCumplidas);
                agregarLinea(divInformacion, 'Vacaciones Tomadas', data.vacacionesTomadas);
                agregarLinea(divInformacion, 'Saldo de Vacaciones', data.vacacionesSaldo);
                agregarLinea(divInformacion, 'Vacaciones sin Goce de Haber', data.vacacionesSinGoce);
                agregarLinea(divInformacion, 'Vacaciones por Compensacion', data.vacacionesCompensaciones);
                agregarTabla(data.solicitudes);
            }

        },
    }).done(function (data) {
        console.log('done');
    }
    ).fail(function (data) {
        console.log('fail');
        swal("ERROR", "NO SE RECUPERO NINGUN REGISTRO", "error");
    });*/
}

function agregarLinea(div, detalle, texto) {
    var labelNombre = document.createElement('label');
    var linea = document.createElement('br');
    labelNombre.innerHTML = detalle + ": " + texto;
    div.appendChild(labelNombre);
    div.appendChild(linea);
}

function agregarTabla(solicitudes) {
    var contenedor = document.getElementById('tablaSolicitudes');
    var table = document.getElementById('tabla_reporte');
    var thead = document.createElement('thead');
    var tbody = document.createElement('tbody');

    thead.className += "head_tabla";
    tbody.className += "body_tabla";

    table.appendChild(thead);
    table.appendChild(tbody);

// Creacion de tabla
    let row_1 = document.createElement('tr');
    let codigo_solicitud = document.createElement('th');
    codigo_solicitud.innerHTML = "Codigo";
    let f_solicitud = document.createElement('th');
    f_solicitud.innerHTML = "F. Solicitud";
    let f_salida = document.createElement('th');
    f_salida.innerHTML = "F. Salida";
    let turno_salida = document.createElement('th');
    turno_salida.innerHTML = "Turno";
    let f_retorno = document.createElement('th');
    f_retorno.innerHTML = "F. Retorno";
    let turno_retorno = document.createElement('th');
    turno_retorno.innerHTML = "Turno";
    let f_estado = document.createElement('th');
    f_estado.innerHTML = "F. Estado";
    let dias = document.createElement('th');
    dias.innerHTML = "Dias";
    let responsable = document.createElement('th');
    responsable.innerHTML = "Responsable";
    let tipo = document.createElement('th');
    tipo.innerHTML = "Tipo";
    let estado = document.createElement('th');
    estado.innerHTML = "Estado";

    row_1.appendChild(codigo_solicitud);
    row_1.appendChild(f_solicitud);
    row_1.appendChild(f_salida);
    row_1.appendChild(turno_salida);
    row_1.appendChild(f_retorno);
    row_1.appendChild(turno_retorno);
    row_1.appendChild(f_estado);
    row_1.appendChild(dias);
    row_1.appendChild(responsable);
    row_1.appendChild(tipo);
    row_1.appendChild(estado);
    thead.appendChild(row_1);

    for (var i = 0; i < solicitudes.length; i++) {
        row = document.createElement('tr');

        codigo_solicitud = document.createElement('td');
        codigo_solicitud.innerHTML = solicitudes[i].codigo_solicitud;

        fecha_solicitud = document.createElement('td');
        fecha_solicitud.innerHTML = solicitudes[i].fecha_solicitud;

        fecha_salida = document.createElement('td');
        fecha_salida.innerHTML = solicitudes[i].fecha_salida;

        turno_salida = document.createElement('td');
        turno_salida.innerHTML = solicitudes[i].turno_salida;

        fecha_retorno = document.createElement('td');
        fecha_retorno.innerHTML = solicitudes[i].fecha_retorno;

        turno_retorno = document.createElement('td');
        turno_retorno.innerHTML = solicitudes[i].turno_retorno;


        fecha_estado = document.createElement('td');
        fecha_estado.innerHTML = solicitudes[i].fecha_estado;

        dias = document.createElement('td');
        dias.innerHTML = solicitudes[i].dias;

        supervisor = document.createElement('td');
        supervisor.innerHTML = solicitudes[i].supervisor;

        tipo = document.createElement('td');
        tipo.innerHTML = solicitudes[i].tipo;

        estado = document.createElement('td');
        estado.innerHTML = solicitudes[i].estado;


        row.appendChild(codigo_solicitud);
        row.appendChild(fecha_solicitud);
        row.appendChild(fecha_salida);
        row.appendChild(turno_salida);
        row.appendChild(fecha_retorno);
        row.appendChild(turno_retorno);
        row.appendChild(fecha_estado);
        row.appendChild(dias);
        row.appendChild(supervisor);
        row.appendChild(tipo);
        row.appendChild(estado);
        tbody.appendChild(row);
    }
    contenedor.appendChild(table);
}

function agregarTablaFuncionarios(solicitudes) {
    console.log(solicitudes);
    var contenedor = document.getElementById('tablaSolicitudes');
    var table = document.getElementById('tabla_reporte');
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    thead.className += "head_tabla";
    tbody.className += "body_tabla";

    table.appendChild(thead);
    table.appendChild(tbody);

// Creacion de tabla
    let row_1 = document.createElement('tr');
    let codigo_solicitud = document.createElement('th');
    codigo_solicitud.innerHTML = "Codigo";
    let codigo_funcionario = document.createElement('th');
    codigo_funcionario.innerHTML = "Say";
    let f_solicitud = document.createElement('th');
    f_solicitud.innerHTML = "F. Solicitud";
    let nombre = document.createElement('th');
    nombre.innerHTML = "F. Solicitud";
    let f_salida = document.createElement('th');
    f_salida.innerHTML = "F. Salida";
    let turno_salida = document.createElement('th');
    turno_salida.innerHTML = "Turno";
    let f_retorno = document.createElement('th');
    f_retorno.innerHTML = "F. Retorno";
    let turno_retorno = document.createElement('th');
    turno_retorno.innerHTML = "Turno";
    let f_estado = document.createElement('th');
    f_estado.innerHTML = "F. Estado";
    let dias = document.createElement('th');
    dias.innerHTML = "Dias";
    let responsable = document.createElement('th');
    responsable.innerHTML = "Responsable";
    let tipo = document.createElement('th');
    tipo.innerHTML = "Tipo";
    let estado = document.createElement('th');
    estado.innerHTML = "Estado";

    row_1.appendChild(codigo_solicitud);
    row_1.appendChild(codigo_funcionario);
    row_1.appendChild(nombre);
    row_1.appendChild(f_solicitud);
    row_1.appendChild(f_salida);
    row_1.appendChild(turno_salida);
    row_1.appendChild(f_retorno);
    row_1.appendChild(turno_retorno);
    row_1.appendChild(f_estado);
    row_1.appendChild(dias);
    row_1.appendChild(responsable);
    row_1.appendChild(tipo);
    row_1.appendChild(estado);
    thead.appendChild(row_1);

    for (var i = 0; i < solicitudes.length; i++) {
        row = document.createElement('tr');

        codigo_solicitud = document.createElement('td');
        codigo_solicitud.innerHTML = solicitudes[i].codigo_solicitud;

        codigo_funcionario = document.createElement('td');
        codigo_funcionario.innerHTML = solicitudes[i].codigo_sai;

        nombre = document.createElement('td');
        nombre.innerHTML = solicitudes[i].apellido + ', ' + solicitudes[i].nombre;

        fecha_solicitud = document.createElement('td');
        fecha_solicitud.innerHTML = solicitudes[i].fecha_solicitud;

        fecha_salida = document.createElement('td');
        fecha_salida.innerHTML = solicitudes[i].fecha_salida;

        turno_salida = document.createElement('td');
        turno_salida.innerHTML = solicitudes[i].turno_salida;

        fecha_retorno = document.createElement('td');
        fecha_retorno.innerHTML = solicitudes[i].fecha_retorno;

        turno_retorno = document.createElement('td');
        turno_retorno.innerHTML = solicitudes[i].turno_retorno;


        fecha_estado = document.createElement('td');
        fecha_estado.innerHTML = solicitudes[i].fecha_estado;

        dias = document.createElement('td');
        dias.innerHTML = solicitudes[i].dias;

        supervisor = document.createElement('td');
        supervisor.innerHTML = solicitudes[i].supervisor;

        tipo = document.createElement('td');
        tipo.innerHTML = solicitudes[i].tipo;

        estado = document.createElement('td');
        estado.innerHTML = solicitudes[i].estado;


        row.appendChild(codigo_solicitud);
        row.appendChild(codigo_funcionario);
        row.appendChild(nombre);
        row.appendChild(fecha_solicitud);
        row.appendChild(fecha_salida);
        row.appendChild(turno_salida);
        row.appendChild(fecha_retorno);
        row.appendChild(turno_retorno);
        row.appendChild(fecha_estado);
        row.appendChild(dias);
        row.appendChild(supervisor);
        row.appendChild(tipo);
        row.appendChild(estado);
        tbody.appendChild(row);
    }
    contenedor.appendChild(table);
}

function selecionarCombo() {
    var codigo = document.getElementById('codigoSay').value;
    var url = "srvAdministrador?accion=llenarCombo" +
            "&codigoSay=" + codigo;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            console.log(data);
            if (data.estado) {
                var funcionariosList = document.getElementById("funcionarios");
                var codigo_sai = data.codigo_sai;
                for (var i = 0; i < funcionariosList.length; i++) {
                    var opcion = funcionariosList[i];

                    if (opcion.value == codigo_sai) {
                        $('#funcionarios > option[value="' + data.codigo_sai + '"]').attr('selected', 'selected');
                    }
                }
            } else {
                $('#funcionarios > option[value="' + data.codigo_sai + '"]').attr('selected', 'selected');
            }
        },
    }).done(function (data) {
        console.log('done');
    }
    ).fail(function (data) {
        console.log(data);
                 swal("ERROR", "NO SE RECUPERO NINGUN REGISTRO", "error");
        $('#funcionarios > option[value="' + data.codigo_sai + '"]').attr('selected', 'selected');
    });
}
function cambiarSay() {
    console.log('say');
    var codigo = document.getElementById('funcionarios').value;
    console.log(codigo);
    document.getElementById('codigoSay').value = codigo;
}
function validacionFechas() {
    var desde = document.getElementById('desde').value;
    var hasta = document.getElementById('hasta').value;
    var actual = new Date().toISOString().split('T')[0];

    var desdeTime = new Date(desde).getTime();
    var hastaTime = new Date(hasta).getTime();
    var actualTime = new Date(actual).getTime();
   
}

function limpiarHoja() {
    try {
        $(".head_tabla").remove();
        $(".body_tabla").remove();
        $(".div_datos").remove();
    } catch (e) {
        console.log(e);
    }


}

