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
        <script src="html2pdf/html2pdf.bundle.min.js" type="text/javascript"></script>
        <title>SGV-FUBODE</title>
    </head>
    <body>
         <div class="linea"></div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03"
                aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand" href="#">
            <img src="recursos/images/logo.png" alt="" />
        </a>

        <div class="collapse navbar-collapse d-flex jus" id="navbarTogglerDemo03">

            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="t"><a href="srvAdministrador?accion=solicitudes" class="nav-link">SOLICITUDES DE
                        VACACIONES</a></li>
                <li class="t"><a href="srvAdministrador?accion=funcionarios" class="nav-link">FUNCIONARIOS</a>
                </li>
                <li class="t"><a href="srvAdministrador?accion=entidades" class="nav-link">ENTIDADES</a></li>
                <li class="t"><a href="srvAdministrador?accion=cargos" class="nav-link">CARGOS</a></li>
                <li class="t"><a href="srvAdministrador?accion=reportesFuncionario" class="nav-link">REPORTES
                        FUNCIONARIO</a></li>
                <li class="sombra"><a href="srvAdministrador?accion=reportesCargos" class="nav-link">REPORTES
                        CARGOS</a></li>
                <li class="t"><a href="srvAdministrador?accion=reportesEntidades" class="nav-link">REPORTES
                        ENTIDADES</a></li>
                <li class="t"><a href="srvAdministrador?accion=calendario" class="nav-link">CALENDARIO</a></li>
                <li class="nav-item dropdown t user">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <label><strong>${nombre_corto}</strong></label>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="t dropdown-item" href="srvUsuario?accion=inicio">FUNCIONARIO</a>
                        <c:if test="${esSupervisor==true}">
                            <a class="t dropdown-item" href="svrSupervisor?accion=pendientes">SUPERVISOR</a>
                        </c:if>
                        <c:forEach var="data" items="${roles}">
                            <a class="t dropdown-item"
                               href="srvSesion?accion=${data.nombre_rol}">${data.nombre_rol}</a>
                        </c:forEach>
                        <div class="dropdown-divider"></div>
                        <a href="#" class="nav-link dropdown-item" data-toggle="modal"
                           data-target="#exampleModalCenter">
                            ${nombreFuncionario}
                        </a>
                        <div class="dropdown-divider"></div>
                        <a href="srvSesion?accion=cerrar" class="nav-link">cerrar sesion</a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
    <div class="linea2"></div>

    <h1 class="text-center">REPORTE CARGOS</h1>

    <div class="container-fluid">
        <div class="container-fluid row">
            <div class="col-2">
                <p><strong>Codigo</strong></p>
                <input id="codigoCargo" type="text" placeholder="Codigo Cargo">
            </div>
            <div class="col-3">
                <p><strong>Seleecione un cargo</strong></p>
                <select name="cargos" id="cargos" class="form-control">
                    <option value="0" selected>TODOS</option>
                    <c:forEach var="dato" items="${lista}">
                        <option value="${dato.codigo_cargo}">${dato.nombre_cargo}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-1">
                <p><strong>Tipo</strong></p>
                <select name="tipo" id="tipo" class="form-control">
                    <option value="TODOS">TODOS</option>
                    <option value="VACACION" >VACACION</option>
                    <option value="COMPENSACION" >COMPENSACION</option>
                    <option value="LICENCIA" >SIN GOCE DE HABER</option>
                    <option value="ASUETO" >ASUETO</option>
                    <option value="DUODESIMA" >DUODESIMA</option>
                </select>
            </div>
            <div class="col-2">
                <p><strong>Estado</strong></p>
                <select name="estado" id="estado" class="form-control">
                    <option value="TODOS" >TODOS</option>
                    <option value="PENDIENTE" >PENDIENTE</option>
                    <option value="ACEPTADO" >ACEPTADO</option>
                    <option value="RECHAZADO" >RECHAZADO</option>
                    <option value="ANULADO" >ANULADO</option>
                </select>
            </div>
            <div class="col-1">
                <p><strong>Desde</strong></p>
                <input id="desde" type="date">
            </div>
            <div class="col-1">
                <p><strong>Hasta</strong></p>
                <input id="hasta" type="date">
            </div>
            <div class="col-2">
                <button class="btn btn-success" id="buscarCargo" >BUSCAR</button>
            </div>
        </div>
    </div>
    <hr>
    <div id="areaImpresion">
        <div id="reporte">

        </div>
        <div id="tablaSolicitudes" class="tablaSolicitudes m-4 letras">
            <table id="tabla_reporte" class="table container-fluid table-hover table-striped table-bordered">
            </table>
        </div>
    </div>

    <!-- Modal datos usuario -->
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header bg-warning">
                    <h5 class="modal-title" id="exampleModalLongTitle">DATOS DEL FUNCIONARIO</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <h4 class="text-center">FUNCIONARIO</h4>
                    <label><strong>NOMBRE: </strong> ${nombreFuncionario}</label><br>
                    <label><strong>CARGO: </strong> ${nombre_cargo}</label><br>
                    <label><strong>ENTIDAD: </strong> ${nombre_entidad}</label><br>
                    <label><strong>CORREO: </strong> ${correo}</label><br>
                    <label><strong>FECHA DE INGRESO: </strong> ${fecha_ingreso}</label><br>
                    <c:if test="${supervisor!=0}">
                        <h4 class="text-center">SUPERVISOR</h4>
                        <label><strong>NOMBRE: </strong> ${supervisor_nombre}</label><br>
                        <label><strong>CARGO: </strong> ${supervisor_cargo}</label><br>
                        <label><strong>ENTIDAD: </strong> ${supervisor_entidad}</label><br>
                        <label><strong>CORREO: </strong> ${supervisor_correo}</label><br>
                    </c:if>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    
    <script src="recursos/js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <script src="recursos/js/popper.min.js" type="text/javascript"></script>
    <script src="recursos/js/bootstrap.min.js" type="text/javascript"></script>  
    <!-- scripts para table -->
    <script src="swetalert/sweetalert.js" type="text/javascript"></script>
    <script src="recursos/js/dataTable.js" type="text/javascript"></script>
    <script src="recursos/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="js/administrador/reporteCargos.js" type="text/javascript"></script>
    <script src="js/administrador/imprimirCargo.js" type="text/javascript"></script>
</body>
</html>