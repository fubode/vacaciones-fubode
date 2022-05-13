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
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <title>Welcome to Spring Web MVC project</title>
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
        <!--Navbar-->
    <header class="container-fluid site-navbar js-sticky-header site-navbar-target" role="banner">
        <div class="container-fluid">
            <div class="linea"></div>
            <div class="row align-items-center position-relative">
                <div class="site-logo">
                    <a href="index.htm" class="text-black">
                        <img src="${pageContext.request.contextPath}/recursos/images/logo.png" alt=""/>
                    </a>
                </div>

                <div class="col-12">
                    <nav class="site-navigation text-right ml-auto " role="navigation">
                        <ul class="site-menu main-menu js-clone-nav ml-auto d-none d-lg-block">
                            <li class="t"><a href="svrSupervisor?accion=funcionarios" class="nav-link">FUNCIONARIOS</a></li>
                            <li class="sombra"><a href="svrSupervisor?accion=pendientes" class="nav-link">SOLICITUDES PENDIENTES</a></li>
                            <li class="t"><a href="svrSupervisor?accion=aceptadas" class="nav-link">SOLICITUDES ACEPTADAS</a></li>
                            <li class="t"><a href="svrSupervisor?accion=rechazadas" class="nav-link">SOLICITUDES RECHAZADAS</a></li>
                            <li class="nav-item dropdown t user">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    JP
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <a class="t dropdown-item" href="srvUsuario?accion=inicio">FUNCIONARIO</a>
                                    <c:forEach var="data" items="${roles}">
                                        <a class="t dropdown-item" href="srvSesion?accion=${data.nombre_rol}">${data.nombre_rol}</a>  
                                    </c:forEach>
                                    <div class="dropdown-divider"></div>
                                    <a href="#" class="nav-link dropdown-item"  data-toggle="modal" data-target="#exampleModalCenter">
                                        ${nombreFuncionario}
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <a href="srvSesion?accion=cerrar" class="nav-link">cerrar sesion</a>
                                </div>
                            </li>
                        </ul>
                    </nav>
                </div>
                <div class="toggle-button d-inline-block d-lg-none"><a href="#" class="site-menu-toggle py-5 js-menu-toggle text-black"><span class="icon-menu h3"></span></a></div>
            </div>
        </div>
        <div class="linea2"></div>
    </header>

    <h1 class="text-center">SOLICITUDES PENDIENTES</h1>
    <table id="tablaFubode" class="table container table-hover table-striped table-bordered">
        <thead>
            <tr>
                <th>CODIGO SOLICITUD</th>
                <th>FECHA SOLICITUD</th>
                <th>FECHA SALIDA</th>
                <th>TURNO SALIDA</th>
                <th>FECHA RETORNO</th>
                <th>TURNO RETORNO</th>
                <th>DIAS</th>
                <th>FUNCIONARIO</th>
                <th>CARGO</th>
                <th>TIPO DE SOLICITUD</th>
                <th>ACCION</th>
            </tr>
        </thead>
        <tbody>

            <c:forEach var="dato" items="${pendientes}">
                <tr>
                    <td>${dato.codigo_solicitud}</td>
                    <td>${dato.fecha_solicitud}</td>
                    <td>${dato.fecha_salida}</td>
                    <td>${dato.turno_salida}</td>
                    <td>${dato.fecha_retorno}</td>
                    <td>${dato.turno_retorno}</td>
                    <td>${dato.dias}</td>
                    <td>${dato.apellido}, ${dato.nombre}</td>
                    <td>${dato.nombre_cargo}</td>
                    <td class="text-center align-items-center">
                        <c:choose>
                            <c:when  test="${dato.tipo=='COMPENSACION'}">
                                <a href="#" class="nav-link"  data-toggle="modal" data-target="#${dato.codigo_solicitud}">
                                    <button type="button" class="btn fubode-azul" data-toggle="tooltip"  title="Detalle" data-original-title="Detalle">
                                        COMPENSACION</button></a>
                                <!-- Modal compensacion -->
                                <div class="modal fade" id="${dato.codigo_solicitud}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header fubode-azul">
                                                <h5 class="modal-title text-white" id="exampleModalLongTitle">SOLICITUD ${dato.codigo_solicitud}</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <p>
                                                    ${dato.detalle_compensacion}
                                                </p>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:when  test="${dato.tipo=='ASUETO'}">
                                <a href="#" class="nav-link"  data-toggle="modal" data-target="#${dato.codigo_solicitud}">
                                    <button type="button" class="btn fubode-azul" data-toggle="tooltip"  title="Detalle" data-original-title="Detalle">
                                        ASUELTO</button></a>
                                <!-- Modal compensacion -->
                                <div class="modal fade" id="${dato.codigo_solicitud}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header fubode-azul">
                                                <h5 class="modal-title text-white" id="exampleModalLongTitle">SOLICITUD ${dato.codigo_solicitud}</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <p>
                                                    ${dato.detalle_compensacion}
                                                </p>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-success m-2 p-2">${dato.tipo}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="btn-group" role="group" aria-label="Basic example">
                                    <button id="${dato.codigo_solicitud}" type="button" class="btn btn-success" onclick="aceptarSolicitud(this)">
                                        <span  class="fa fa-check">
                                    </button>
                                    <button id="${dato.codigo_solicitud}" type="button" class="btn btn-danger " onclick="rechazarSolicitud(this)">
                                        <span  class="fa fa-trash">
                                    </button>
                                </div>
                            </div>
                        </div> 
                    </td>
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


<div class="modal fade" id="modalRechazar" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header fubode-naranja">
                <h5 class="modal-title text-white text-center" id="exampleModalLongTitle">DETALLE RECHAZO DE SOLICITUD</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <textarea id = "detalle"class="form-control" row = "14" cols="10"></textarea>
            </div>
            <div class="modal-footer">
                <button id = "rechazarSolicitud" type="button" class="btn fubode-azul" data-dismiss="modal">RECHAZAR SOLICITUD</button>
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
<script src="js/supervisor/supervisorFunciones.js" type="text/javascript"></script>
</body>
</html>
