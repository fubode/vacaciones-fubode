$(document).ready(function () {
    document.getElementById('buscarCargo').onclick = buscarCargo;
    document.getElementById('codigoCargo').onkeypress = selecionarCombo;
    document.getElementById('cargos').onchange = cambiarCodigo;
});

function buscarCargo() {
    var cargos = document.getElementById('cargos').value;
    var tipo = document.getElementById('tipo').value;
    var estado = document.getElementById('estado').value;
    var desde = document.getElementById('desde').value;
    var hasta = document.getElementById('hasta').value;
    var codigo = parseInt(cargos);
    console.log(cargos, tipo, estado, desde, hasta);
    
    if(cargos==0){
        var url = "srvReportes?accion=reporteCargosTodos" +
            "&cargos=" + cargos +
            "&tipo=" + tipo +
            "&estado=" + estado +
            "&desde=" + desde +
            "&hasta=" + hasta;
        window.open(url, '_blank');
        
    }else{
        var url = "srvReportes?accion=reporteCargo" +
            "&cargos=" + cargos +
            "&tipo=" + tipo +
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
            limpiarHoja();
            var newDiv = document.createElement("div");
            newDiv.className += "div_datos ";

            var titulo = document.createElement('h3');
            titulo.innerHTML = 'REPORTE DE VACACIONES SEGUN CARGOS';
            titulo.className += "text-center ";

            var intervalo = document.createElement('h4');
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

            if (codigo == 0) {
                agregarLinea(divDatos, 'Tipo', data.tipo);
                agregarLinea(divDatos, 'Estado', data.estado);
                agregarTablaCargos(data.solicitudes);
            } else {
                agregarLinea(divDatos, 'Codigo Cargo', data.codigo_cargo);
                agregarLinea(divDatos, 'Nombre Cargo', data.nombre_cargo);
                agregarLinea(divDatos, 'Tipo', data.tipo);
                agregarLinea(divDatos, 'Estado', data.estado);
                agregarTabla(data.solicitudes);
            }

        },
    }).done(function (data) {
        console.log('done');
    }
    ).fail(function (data) {
        console.log('fail');
    });
    */
}

function agregarLinea(div, detalle, texto) {
    var labelNombre = document.createElement('label');
    var linea = document.createElement('br');
    labelNombre.innerHTML = detalle + ": " + texto;
    div.appendChild(labelNombre);
    div.appendChild(linea);
}

function agregarTabla(solicitudes) {
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
    codigo_solicitud.innerHTML = "Codigo Solicitud";

    let codigo_sai = document.createElement('th');
    codigo_sai.innerHTML = "Codigo Say";

    let nombre = document.createElement('th');
    nombre.innerHTML = "Funcionario";

    let fecha_solicitud = document.createElement('th');
    fecha_solicitud.innerHTML = "Fecha de Solicitud";

    let fecha_salida = document.createElement('th');
    fecha_salida.innerHTML = "Fecha de Salida";

    let turno_salida = document.createElement('th');
    turno_salida.innerHTML = "Turno de Salida";
    fecha_salida.innerHTML = "Fecha de Salida";

    let fecha_retorno = document.createElement('th');
    fecha_retorno.innerHTML = "Fecha de Retorno";

    let turno_retorno = document.createElement('th');
    turno_retorno.innerHTML = "Turno de Retorno";

    let dias = document.createElement('th');
    dias.innerHTML = "Dias";

    let tipo = document.createElement('th');
    tipo.innerHTML = "Tipo";

    let estado = document.createElement('th');
    estado.innerHTML = "Estado";

    let supervisor = document.createElement('th');
    supervisor.innerHTML = "Supervisor";

    row_1.appendChild(codigo_solicitud);
    row_1.appendChild(codigo_sai);
    row_1.appendChild(nombre);
    row_1.appendChild(fecha_solicitud);
    row_1.appendChild(fecha_salida);
    row_1.appendChild(turno_salida);
    row_1.appendChild(fecha_retorno);
    row_1.appendChild(turno_retorno);
    row_1.appendChild(dias);
    row_1.appendChild(tipo);
    row_1.appendChild(estado);
    row_1.appendChild(supervisor);
    thead.appendChild(row_1);

    for (var i = 0; i < solicitudes.length; i++) {
        row = document.createElement('tr');

        codigo_solicitud = document.createElement('td');
        codigo_solicitud.innerHTML = solicitudes[i].codigo_solicitud;

        codigo_sai = document.createElement('td');
        codigo_sai.innerHTML = solicitudes[i].codigo_funcionario;

        nombre = document.createElement('td');
        nombre.innerHTML = solicitudes[i].nombre + ", " + solicitudes[i].apellido;

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

        dias = document.createElement('td');
        dias.innerHTML = solicitudes[i].dias;

        tipo = document.createElement('td');
        tipo.innerHTML = solicitudes[i].tipo;

        estado = document.createElement('td');
        estado.innerHTML = solicitudes[i].estado;

        supervisor = document.createElement('td');
        supervisor.innerHTML = solicitudes[i].supervisor;



        row.appendChild(codigo_solicitud);
        row.appendChild(codigo_sai);
        row.appendChild(nombre);
        row.appendChild(fecha_solicitud);
        row.appendChild(fecha_salida);
        row.appendChild(turno_salida);
        row.appendChild(fecha_retorno);
        row.appendChild(turno_retorno);
        row.appendChild(dias);
        row.appendChild(tipo);
        row.appendChild(estado);
        row.appendChild(supervisor);
        tbody.appendChild(row);
    }
    contenedor.appendChild(table);
}

function agregarTablaCargos(solicitudes) {
    console.log(solicitudes);
    var contenedor = document.getElementById('tablaSolicitudes');
    //contenedor.className += "table container-fluid table-hover table-striped table-bordered";
    let table = document.getElementById('tabla_reporte');
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    table.appendChild(thead);
    table.appendChild(tbody);

// Creacion de tabla
    let row_1 = document.createElement('tr');

    let codigo_solicitud = document.createElement('th');
    codigo_solicitud.innerHTML = "Codigo Solicitud";

    let codigo_sai = document.createElement('th');
    codigo_sai.innerHTML = "Codigo Say";

    let nombre = document.createElement('th');
    nombre.innerHTML = "Funcionario";

    let nombre_cargo = document.createElement('th');
    nombre_cargo.innerHTML = "Nombre del Cargo";

    let fecha_solicitud = document.createElement('th');
    fecha_solicitud.innerHTML = "Fecha de Solicitud";

    let fecha_salida = document.createElement('th');
    fecha_salida.innerHTML = "Fecha de Salida";

    let turno_salida = document.createElement('th');
    turno_salida.innerHTML = "Turno de Salida";
    fecha_salida.innerHTML = "Fecha de Salida";

    let fecha_retorno = document.createElement('th');
    fecha_retorno.innerHTML = "Fecha de Retorno";

    let turno_retorno = document.createElement('th');
    turno_retorno.innerHTML = "Turno de Retorno";

    let dias = document.createElement('th');
    dias.innerHTML = "Dias";

    let tipo = document.createElement('th');
    tipo.innerHTML = "Tipo";

    let estado = document.createElement('th');
    estado.innerHTML = "Estado";

    let supervisor = document.createElement('th');
    supervisor.innerHTML = "Supervisor";




    row_1.appendChild(codigo_solicitud);
    row_1.appendChild(codigo_sai);
    row_1.appendChild(nombre);
    row_1.appendChild(nombre_cargo);
    row_1.appendChild(fecha_solicitud);
    row_1.appendChild(fecha_salida);
    row_1.appendChild(turno_salida);
    row_1.appendChild(fecha_retorno);
    row_1.appendChild(turno_retorno);
    row_1.appendChild(dias);
    row_1.appendChild(tipo);
    row_1.appendChild(estado);
    row_1.appendChild(supervisor);
    thead.appendChild(row_1);


    for (var i = 0; i < solicitudes.length; i++) {
        row = document.createElement('tr');

        codigo_solicitud = document.createElement('td');
        codigo_solicitud.innerHTML = solicitudes[i].codigo_solicitud;

        codigo_sai = document.createElement('td');
        codigo_sai.innerHTML = solicitudes[i].codigo_funcionario;

        nombre = document.createElement('td');
        nombre.innerHTML = solicitudes[i].nombre + ", " + solicitudes[i].apellido;

        fecha_solicitud = document.createElement('td');
        fecha_solicitud.innerHTML = solicitudes[i].fecha_solicitud;

        nombre_cargo = document.createElement('td');
        nombre_cargo.innerHTML = solicitudes[i].nombre_cargo;

        fecha_salida = document.createElement('td');
        fecha_salida.innerHTML = solicitudes[i].fecha_salida;

        turno_salida = document.createElement('td');
        turno_salida.innerHTML = solicitudes[i].turno_salida;

        fecha_retorno = document.createElement('td');
        fecha_retorno.innerHTML = solicitudes[i].fecha_retorno;

        turno_retorno = document.createElement('td');
        turno_retorno.innerHTML = solicitudes[i].turno_retorno;

        dias = document.createElement('td');
        dias.innerHTML = solicitudes[i].dias;

        tipo = document.createElement('td');
        tipo.innerHTML = solicitudes[i].tipo;

        estado = document.createElement('td');
        estado.innerHTML = solicitudes[i].estado;

        supervisor = document.createElement('td');
        supervisor.innerHTML = solicitudes[i].supervisor;



        row.appendChild(codigo_solicitud);
        row.appendChild(codigo_sai);
        row.appendChild(nombre);
        row.appendChild(nombre_cargo);
        row.appendChild(fecha_solicitud);
        row.appendChild(fecha_salida);
        row.appendChild(turno_salida);
        row.appendChild(fecha_retorno);
        row.appendChild(turno_retorno);
        row.appendChild(dias);
        row.appendChild(tipo);
        row.appendChild(estado);
        row.appendChild(supervisor);

        tbody.appendChild(row);
    }
    contenedor.appendChild(table);
}


function selecionarCombo() {
    console.log('combo');
}
function cambiarCodigo() {
    console.log('say');
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