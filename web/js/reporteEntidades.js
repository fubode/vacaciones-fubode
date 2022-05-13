$(document).ready(function () {
    document.getElementById('buscarEntidad').onclick = buscarEntidad;
    document.getElementById('entidad').onchange = cambiarCodigo;
    document.getElementById('desde').onchange = validacionFechas;
    document.getElementById('hasta').onchange = validacionFechas;
});

function buscarEntidad() {
    var cargos = document.getElementById('cargos').value;
    var entidad = document.getElementById('entidad').value;
    var tipo = document.getElementById('tipo').value;
    var estado = document.getElementById('estado').value;
    var desde = document.getElementById('desde').value;
    var hasta = document.getElementById('hasta').value;
    var codigo = parseInt(entidad);


    var url = "srvAdministrador?accion=buscarEntidad" +
            "&cargos=" + cargos +
            "&entidad=" + entidad +
            "&tipo=" + tipo +
            "&estado=" + estado +
            "&desde=" + desde +
            "&hasta=" + hasta;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var titulo = document.createElement('h3');
            titulo.innerHTML = 'REPORTE DE VACACIONES SEGUN CARGOS';
            var intervalo = document.createElement('h4');
            intervalo.innerHTML = '(Practicado del ' + data.intervalo + ')';
            document.getElementById('reporte').appendChild(titulo);
            document.getElementById('reporte').appendChild(intervalo);

            if (parseInt(entidad) == 0) {
                agregarLinea('datos', 'Entidades', 'TODOS');
            } else {
                agregarLinea('datos', 'Codigo Entidad', data.codigo_entidad);
                agregarLinea('datos', 'Nombre Entidad', data.nombre_entidad);
            }
            if (parseInt(cargos) == 0) {
                agregarLinea('datos', 'Cargos', 'TODOS');
            } else {
                agregarLinea('datos', 'Codigo Cargo', data.codigo_cargo);
                agregarLinea('datos', 'Nombre Cargo', data.nombre_cargo);
            }
            agregarLinea('datos', 'Tipo', data.tipo);
            agregarLinea('datos', 'Estado', data.estado);
            
            if (codigo == 0) {
                agregarTablaEntidades(data.solicitudes);
            } else {
                agregarTabla(data.solicitudes);
            }

        },
    }).done(function (data) {
        console.log('done');
    }
    ).fail(function (data) {
        console.log('fail');
    });
}

function agregarLinea(div, detalle, texto) {
    var contenedor = document.getElementById(div);
    var labelNombre = document.createElement('label');
    var linea = document.createElement('br');
    labelNombre.innerHTML = detalle + ": " + texto;
    contenedor.appendChild(labelNombre);
    contenedor.appendChild(linea);
}

function agregarTabla(solicitudes) {
    var contenedor = document.getElementById('tablaSolicitudes');
    contenedor.className += "table container-fluid table-hover table-striped table-bordered";
    let table = document.createElement('table');
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

    let fecha_solicitud = document.createElement('th');
    fecha_solicitud.innerHTML = "Fecha de Solicitud";

    let fecha_salida = document.createElement('th');
    fecha_salida.innerHTML = "Fecha de Salida";

    let turno_salida = document.createElement('th');
    turno_salida.innerHTML = "Turno de Salida";

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

    let nombre_cargo = document.createElement('th');
    nombre_cargo.innerHTML = "Nombre cargo";

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
    row_1.appendChild(nombre_cargo);
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

        nombre_cargo = document.createElement('td');
        nombre_cargo.innerHTML = solicitudes[i].nombre_cargo;



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
        row.appendChild(nombre_cargo);

        tbody.appendChild(row);
    }
    contenedor.appendChild(table);
}

function agregarTablaEntidades(solicitudes) {

    var contenedor = document.getElementById('tablaSolicitudes');
    contenedor.className += "table container table-hover table-striped table-bordered";
    let table = document.createElement('table');
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

    let fecha_solicitud = document.createElement('th');
    fecha_solicitud.innerHTML = "Fecha de Solicitud";

    let fecha_salida = document.createElement('th');
    fecha_salida.innerHTML = "Fecha de Salida";

    let turno_salida = document.createElement('th');
    turno_salida.innerHTML = "Turno de Salida";

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

    let nombre_cargo = document.createElement('th');
    nombre_cargo.innerHTML = "Nombre cargo";

    let nombre_entidad = document.createElement('th');
    nombre_entidad.innerHTML = "Nombre Entidad";

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
    row_1.appendChild(nombre_cargo);
    row_1.appendChild(nombre_entidad);
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

        nombre_cargo = document.createElement('td');
        nombre_cargo.innerHTML = solicitudes[i].nombre_cargo;

        nombre_entidad = document.createElement('td');
        nombre_entidad.innerHTML = solicitudes[i].nombre_entidad;



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
        row.appendChild(nombre_cargo);
        row.appendChild(nombre_entidad);

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
function validacionFechas() {
    var desde = document.getElementById('desde').value;
    var hasta = document.getElementById('hasta').value;
    var actual = new Date().toISOString().split('T')[0];
    console.log(desde, hasta, actual);
}