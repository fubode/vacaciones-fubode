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
        <title>SGV-FUBODE | SOLICITUD ACEPTADAS</title>
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
    <nav class="navbar navbar-expand-lg navbar-light bg-white">
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
            <div class="row align-items-end">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

            </div>

            <div class="row align-items-end">
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                    <ul class="navbar-nav">
                        <li class=""><a href="srvUsuario?accion=inicio" class="nav-link">INICIO</a></li>
                        <li class="t"><a href="srvUsuario?accion=nueva" class="nav-link">SOLICITUDES NUEVAS</a></li>                  
                        <li class="t"><a href="srvUsuario?accion=pendientes" class="nav-link">SOLICITUDES PENDIENTES</a></li>
                        <li class="sombra"><a href="srvUsuario?accion=aceptadas" class="nav-link">SOLICITUDES ACEPTADAS</a></li>
                        <li class="t"><a href="srvUsuario?accion=rechazadas" class="nav-link">SOLICITUDES RECHAZADAS</a></li>
                        <li class="t"><a href="srvUsuario?accion=actividades" class="nav-link">CALENDARIO</a></li>
                        <li class="nav-item dropdown t user">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <label><strong>${nombre_corto}</strong></label>
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <c:if test="${esSupervisor==true}">
                                    <a class="t dropdown-item" href="svrSupervisor?accion=pendientes">SUPERVISOR</a>                                        
                                </c:if>          
                                <c:forEach var="data" items="${roles}">
                                    <a class="t dropdown-item" href="srvSesion?accion=${data.nombre_rol}">${data.nombre_rol}</a>  
                                </c:forEach>
                                <div class="dropdown-divider"></div>
                                <a href="#" class="nav-link dropdown-item"  data-toggle="modal" data-target="#exampleModalCenter">
                                    ${nombreFuncionario}
                                </a>
                                <a href="srvUsuario?accion=configuraciones">
                                    <span class="fa fa-gear">
                                        Configuraciones
                                    </span>
                                </a>
                                <div class="dropdown-divider"></div>
                                <a href="srvSesion?accion=cerrar" class="nav-link">cerrar sesion</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
    <div class="linea2"></div>

    <h1 class="text-center">SOLICITUDES ACEPTADAS</h1>
    <table id="tablaFubode" class="table container table-hover table-striped table-bordered">
        <thead>
            <tr>
                <th>CODIGO SOLICITUD    </th>
                <th>FECHA SOLICITUD</th>
                <th>FECHA SALIDA</th>
                <th>TURNO SALIDA</th>
                <th>FECHA RETORNO</th>
                <th>TURNO RETORNO</th>
                <th>FECHA DE APROBACION</th>
                <th>RESPONSABLE</th>
            </tr>
        </thead>
        <tbody>

            <c:forEach var="dato" items="${lista_aceptados}">
                <tr>
                    <td>${dato.codigo_solicitud}</td>
                    <td>${dato.fecha_solicitud}</td>
                    <td>${dato.fecha_salida}</td>
                    <td>${dato.turno_salida}</td>
                    <td>${dato.fecha_retorno}</td>
                    <td>${dato.turno_retorno}</td>
                    <td>${dato.fecha_estado}</td>
                    <td>${dato.supervisor}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
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
<script src="recursos/js/dataTable.js" type="text/javascript"></script>
<script src="recursos/js/jquery.dataTables.min.js" type="text/javascript"></script>
</body>
</html>
