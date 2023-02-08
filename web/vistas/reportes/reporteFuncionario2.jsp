<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="recursos/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="recursos/css/estilos.css" rel="stylesheet" type="text/css"/>
        <!-- style para table -->
        <link href="recursos/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <link href="recursos/css/dataTables.bootstrap4.min.css" rel="stylesheet" type="text/css"/>
        <link href="swetalert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <title>SGV-FUBODE | REPORTE FUNCIONARIOS</title>
    </head>
    <body>
        <div class="site-mobile-menu site-navbar-target">
            <div class="site-mobile-menu-header">
                <div class="site-mobile-menu-close mt-3">
                    <span class="icon-close2 js-menu-toggle"></span>
                </div>
            </div>
            <div class="site-mobile-menu-body"></div>
        </div>    


        <div class="linea"></div>
    <nav class="navbar navbar-expand-lg navbar-light bg-white" id="nav">
        <div class="container-fluid">
            <div class="row align-items-center">
                <div class="col-4 align-self-start">
                    <a class="navbar-brand" href="#">
                        <a href="index.htm" class="text-black">
                            <img src="${pageContext.request.contextPath}/recursos/images/logo.png" alt=""/>
                        </a>
                    </a>      
                </div>
            </div>
        </div>
    </nav>
    <div class="linea2"></div>
    <hr>

    <div id="areaImpresion">
        <div id="reporte">
            <h3 class="text-center ">REPORTE FUNCIONARIO</h3>
            <h4 class="text-center ">(${intervalo})</h4>
        </div>
        <div class="container">
            <div class="row">
                <div class="col">
                    <label><strong>Funcionario: </strong>${nombreCompleto}</label><br>
                    <label><strong>Codigo Sai: </strong>${codigoSai}</label><br>
                    <label><strong>Cargo: </strong>${cargo}</label><br>
                    <label><strong>Entidad: </strong>${entidad}</label><br>
                    <label><strong>Correo: </strong>${correo}</label><br>
                    <label><strong>Fecha de Ingreso: </strong>${fechaIngreso}</label><br>
                    <label><strong>Antiguedad: </strong>${antiguedad}</label><br>
                    <label><strong>Vacaciones Cumplidas: </strong>${vacacionesCumplidas}</label><br>
                    <label><strong>Vacaciones Tomadas: </strong>${vacacionesTomadas}</label><br>
                    <label><strong>Vacaciones Sin Goce de haber: </strong>${vacacionesSinGoce}</label><br>
                    <label><strong>Vacaciones por compensacion: </strong>${vacacionesCompensaciones}</label><br>
                    <label><strong>Saldo Vacacion: </strong>${vacacionesSaldo}</label><br>
                </div>
                <div class="col">
                    <label><strong>Tipo: </strong>${tipo}</label><br>
                    <label><strong>Estado: </strong>${estado}</label><br>
                    <label><strong>Emitido </strong>${emitido}</label><br>
                    <label><strong>Expedido por: </strong>${funcionario}</label><br>
                </div>
            </div>
        </div>
        <div id="tablaSolicitudes" class="tablaSolicitudes">
            <table id="tabla_reporte" class="table container-fluid table-hover table-striped table-bordered">
                <thead>
                    <tr>
                        <th>CODIGO SOLICITUD</th>
                        <th>F. SOLICITUD</th>
                        <th>F.SALIDA</th>
                        <th>TURNO</th>
                        <th>F.RETORNO</th>
                        <th>F.TURNO</th>
                        <th>F.ESTADO</th>
                        <th>DIAS</th>
                        <th>RESPONSABLE</th>
                        <th>TIPO</th>
                        <th>ESTADO</th>
                    </tr>
                </thead>
                <tbody>

                    <c:forEach var="solicitud" items="${solicitudes}">
                        <tr>
                            <td>${solicitud.codigo_solicitud}</td>
                            <td>${solicitud.fecha_solicitud}</td>
                            <td>${solicitud.fecha_salida}</td>
                            <td>${solicitud.turno_salida}</td>
                            <td>${solicitud.fecha_retorno}</td>
                            <td>${solicitud.turno_retorno}</td>
                            <td>${solicitud.fecha_estado}</td>
                            <td>${solicitud.dias}</td>
                            <td>${solicitud.supervisor}</td>
                            <td>${solicitud.tipo}</td>
                            <td>${solicitud.estado}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script src="recursos/js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <script src="recursos/js/popper.min.js" type="text/javascript"></script>
    <script src="recursos/js/bootstrap.min.js" type="text/javascript"></script>  
    <!-- scripts para table -->
    <script src="swetalert/sweetalert.js" type="text/javascript"></script>
    <script src="recursos/js/dataTable.js" type="text/javascript"></script>
    <script src="recursos/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="js/administrador/reporteFuncioario.js" type="text/javascript"></script>
    <script src="html2pdf/html2pdf.bundle.min.js" type="text/javascript"></script>
    <script src="js/administrador/imprimir.js" type="text/javascript"></script>
</body>
</html>