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
    var estado = document.getElementById('estado').value;
    var desde = document.getElementById('desde').value;
    var hasta = document.getElementById('hasta').value;
    var say = parseInt(funcionario);
    console.log(codigoSay, funcionario, tipo, estado, desde, hasta);


    var url = "srvAdministrador?accion=buscarFuncionario" +
            "&codigoSay=" + codigoSay +
            "&funcionario=" + funcionario +
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
            titulo.innerHTML = 'REPORTE FUNCIONARIO';
            var intervalo = document.createElement('h4');
            let date = new Date();
            intervalo.innerHTML = '(Practicado del ' + data.intervalo + ')';
            document.getElementById('reporte').appendChild(titulo);
            document.getElementById('reporte').appendChild(intervalo);
                
            
            if (say == 0) {
                agregarLinea('datos', 'Tipo', data.tipo);
                agregarLinea('datos', 'Estado', data.estado);
                agregarLinea('datos', 'Emitido', date.toLocaleDateString());
                agregarLinea('datos', 'Expedido por', data.funcionario);
                    agregarTablaFuncionarios(data.solicitudes);
            } else {
                agregarLinea('datos', 'Nombre', data.nombreCompleto);
                agregarLinea('datos', 'Cargo', data.cargo);
                agregarLinea('datos', 'Entidad', data.entidad);
                agregarLinea('datos', 'Correo', data.correo);
                agregarLinea('datos', 'Tipo', data.tipo);
                agregarLinea('datos', 'Estado', data.estado);
                agregarLinea('datos', 'Emitido', date.toLocaleDateString());
                agregarLinea('datos', 'Expedido por', data.funcionario);
                agregarLinea('informacion', 'Fecha de Ingreso', data.fechaIngreso);
                agregarLinea('informacion', 'Antiguedad', data.antiguedad);
                agregarLinea('informacion', 'Vacaciones Cumplidas', data.vacacionesCumplidas);
                agregarLinea('informacion', 'Vacaciones Tomadas', data.vacacionesTomadas);
                agregarLinea('informacion', 'Saldo de Vacaciones', data.vacacionesSaldo);
                agregarLinea('informacion', 'Vacaciones sin Goce de Haber', data.vacacionesSinGoce);
                agregarLinea('informacion', 'Vacaciones por Compensacion', data.vacacionesCompensaciones);
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
    console.log(solicitudes);
    var contenedor = document.getElementById('tablaSolicitudes');
    var table = document.getElementById('tabla_reporte');
    var thead = document.createElement('thead');
    var tbody = document.createElement('tbody');

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
    console.log(desde, hasta, actual);
}

function limpiar() {
    var contenedor = document.getElementById('reporte');
    contenedor.removeChild();
}

function imprimir() {
    const $elementoParaConvertir = document.getElementById('areaImpresion'); // <-- Aquí puedes elegir cualquier elemento del DOM
    html2pdf()
            .set({
                margin: 1,
                filename: 'documento.pdf',
                image: {
                    type: 'jpeg',
                    quality: 0.98
                },
                html2canvas: {
                    scale: 3, // A mayor escala, mejores gráficos, pero más peso
                    letterRendering: true,
                },
                jsPDF: {
                    unit: "in",
                    format: "a3",
                    orientation: 'landscape' // landscape o portrait
                }
            })
            .from($elementoParaConvertir)
            .save()
            .catch(err => console.log(err));
}