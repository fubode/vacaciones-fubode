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
        <title>SGV-FUBODE</title>
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
                            <li class="sombra"><a href="srvAdministrador?accion=solicitudes" class="nav-link">SOLICITUDES DE VACACIONES</a></li>                  
                            <li class="t"><a href="srvAdministrador?accion=funcionarios" class="nav-link">FUNCIONARIOS</a></li>
                            <li class="t"><a href="srvAdministrador?accion=entidades" class="nav-link">ENTIDADES</a></li>
                            <li class="t"><a href="srvAdministrador?accion=cargos" class="nav-link">CARGOS</a></li>
                            <li class="t"><a href="srvAdministrador?accion=reportesFuncionario" class="nav-link">REPORTES FUNCIONARIO</a></li>
                            <li class="t"><a href="srvAdministrador?accion=reportesCargos" class="nav-link">REPORTES CARGOS</a></li>
                            <li class="t"><a href="srvAdministrador?accion=reportesEntidades" class="nav-link">REPORTES ENTIDADES</a></li>                            
                            <li class="t"><a href="srvAdministrador?accion=calendario" class="nav-link">CALENDARIO</a></li>
                            <li class="nav-item dropdown t user">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    JP
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <a class="t dropdown-item" href="srvUsuario?accion=inicio">FUNCIONARIO</a>
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


    <h1 class="text-center">SOLICITUDES DE VACACIONES DE FUNCIONARIOS</h1>
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
                <th>FECHA APROBACION</th>
                <th>TIPO DE SOLICITUD</th>
                <th>ESTADO</th>
                <th>FUNCIONARIO</th>
                <th>CARGO</th>
                <th>ENTIDAD</th>
                <th>ACCION</th>
            </tr>
        </thead>
        <tbody>

            <c:forEach var="dato" items="${lista}">
                <tr>
                    <td>${dato.codigo_solicitud}</td>
                    <td>${dato.fecha_solicitud}</td>
                    <td>${dato.fecha_salida}</td>
                    <td>${dato.turno_salida}</td>
                    <td>${dato.fecha_retorno}</td>
                    <td>${dato.turno_retorno}</td>
                    <td>${dato.dias}</td>
                    <td>
                        <c:choose>
                            <c:when test="${dato.fecha_estado=='1500-01-01'}">
                                NINGUNO
                            </c:when>
                            <c:otherwise>
                                ${dato.fecha_estado}    
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="text-center align-items-center">
                        <c:choose>
                            <c:when  test="${dato.tipo=='COMPENSACION'}">
                                <a href="#" class="nav-link"  data-toggle="modal" data-target="#${dato.codigo_solicitud}">
                                    <button type="button" class="btn fubode-azul btn-sm" data-toggle="tooltip"  title="Detalle" data-original-title="Detalle">
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
                                    <button type="button" class="btn fubode-azul btn-sm" data-toggle="tooltip"  title="Detalle" data-original-title="Detalle">
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
                        <c:choose>
                            <c:when test="${dato.estado=='ACEPTADO'}">
                                <a href="#" class="nav-link"  data-toggle="modal" data-target="#${dato.codigo_solicitud}${dato.tipo}">
                                    <button type="button" class="btn btn-success btn-sm" data-toggle="tooltip"  title="Detalle" data-original-title="Detalle">
                                        ${dato.estado}</button></a>
                                <!-- Modal Rechazado -->
                                <div class="modal fade" id="${dato.codigo_solicitud}${dato.tipo}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-success">
                                                <h5 class="modal-title text-white" id="exampleModalLongTitle">SOLICITUD ${dato.codigo_solicitud}</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <label><strong>ESTA SOLICITUD FUE ACEPTADA POR:</strong></label>
                                                <p>${dato.supervisor}</p>                                                
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${dato.estado=='ANULADO'}">
                                <a href="#" class="nav-link"  data-toggle="modal" data-target="#${dato.codigo_solicitud}${dato.tipo}">
                                    <button type="button" class="btn btn-danger btn-sm" data-toggle="tooltip"  title="Detalle" data-original-title="Detalle">
                                        ${dato.estado}</button></a>
                                <!-- Modal Rechazado -->
                                <div class="modal fade" id="${dato.codigo_solicitud}${dato.tipo}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-danger">
                                                <h5 class="modal-title text-white" id="exampleModalLongTitle">SOLICITUD ${dato.codigo_solicitud}</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <label><strong>ESTA SOLICITUD FUE ANULADA POR:</strong></label>
                                                <p>${dato.supervisor}</p>                                                
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${dato.estado=='PENDIENTE'}">
                                <span class="badge fubode-azul m-2 p-2">${dato.estado}</span>
                            </c:when>
                            <c:when test="${dato.estado=='RECHAZADO'}">
                                <a href="#" class="nav-link"  data-toggle="modal" data-target="#${dato.codigo_solicitud}${dato.tipo}">
                                    <button type="button" class="btn btn-danger btn-sm" data-toggle="tooltip"  title="Detalle" data-original-title="Detalle">
                                        ${dato.estado}</button></a>
                                <!-- Modal Rechazado -->
                                <div class="modal fade" id="${dato.codigo_solicitud}${dato.tipo}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-danger">
                                                <h5 class="modal-title text-white" id="exampleModalLongTitle">SOLICITUD ${dato.codigo_solicitud}</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <label><strong>ESTA SOLICITUD FUE RECHAZADA POR:</strong></label>
                                                <p>${dato.supervisor}</p>
                                                <label><strong>MOTIVO DE RECHAZO</strong></label>
                                                <p>
                                                    ${dato.descripcion_estado}
                                                </p>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>

                        </c:choose>
                    </td>
                    <td>${dato.apellido}, ${dato.nombre}</td>
                    <td>${dato.nombre_cargo}</td>
                    <td>${dato.nombre_entidad}</td>
                    <td>
                        <c:choose>
                            <c:when test="${dato.estado=='PENDIENTE'}">
                                <div class="d-flex justify-content-center">
                                    <div class="btn-group" role="group" aria-label="Basic example">
                                        <button id="${dato.codigo_solicitud}" type="button" class="btn btn-success" onclick="aceptarSolicitud(this)">
                                            <span  class="fa fa-check">
                                        </button>
                                        <button id="${dato.codigo_solicitud}" type="button" class="btn btn-danger " onclick="rechazarSolicitud(this)">
                                            <span  class="fa fa-trash">
                                        </button>
                                    </div>
                                </div>                               
                            </c:when>
                            <c:otherwise>
                                <div class="d-flex justify-content-center">
                                    <div class="btn-group" role="group" aria-label="Basic example">
                                        <button id="${dato.codigo_solicitud}" type="button" class="btn btn-warning" onclick="editarSolicitud(this)">
                                            <span  class="fa fa-pencil-square">
                                        </button>                                       
                                    </div>
                                </div>  
                            </c:otherwise>
                        </c:choose>                        
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

<!-- Modal datos rechazar -->
<div class="modal fade" id="modalRechazar" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header bg-danger">
                <h5 class="modal-title modalRechazar text-white text-center" id="exampleModalLongTitle">DETALLE RECHAZO DE SOLICITUD</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <textarea id = "r_detalle"class="form-control" row = "14" cols="10"></textarea>
            </div>
            <div class="modal-footer">
                <button id = "rechazarSolicitud" type="button" class="btn btn-danger" onclick="rechazar_solicitud()">RECHAZAR SOLICITUD</button>
            </div>
        </div>
    </div>
</div>


<!-- Modal Editar Solicitud-->
<div class="modal fade" id="editarSolicitud" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header fubode-naranja">
                <h5 class="modal-title" id="exampleModalLongTitle"> EDITAR SOLICITUD</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="container card-body">
                <div class="card">
                    <div class="container mt-3">
                        <label ><strong>FECHA DE SOLICITUD </strong></label>
                        <input type="date" name = "fecha_solicitud"  id="fecha_solicitud" class="form-control" value='${fecha_solicitud}'/>
                    </div>
                    <div class="container mt-3">
                        <label ><strong>FECHA DE SALIDA </strong></label>
                        <input type="date" name = "fecha_salida"  id="fecha_salida" class="form-control" value='${fecha_salida}'/>
                        <label ><strong>TURNO DE SALIDA </strong>
                            <select name="turno_salida" id="turno_salida" class="form-control">
                                <option value="MAÑANA">MAÑANA</option>
                                <option value="TARDE" selected>TARDE</option>
                            </select>
                    </div>
                    <div class="container-fluid">
                        <label ><strong>FECHA DE RETORNO </strong></label>
                        <input type="date" name = "fecha_retorno" id="fecha_retorno" class="form-control" value='${fecha_retorno}'/>
                        <label ><strong>TURNO DE RETORNO </strong>
                            <select name="turno_retorno" id="turno_retorno" class="form-control">
                                <option value="MAÑANA">MAÑANA</option>
                                <option value="TARDE" selected>TARDE</option>
                            </select>
                    </div>
                    <div class="container">
                        <label class="m-2"><strong>DIAS DE VACACION: </strong></label>
                        <input type="number" name="dias" id="dias" class="form-control" disabled value='${diasNoLaborables}'>                    
                    </div>
                    <div class="container">
                        <p><strong>TIPO DE SOLICITUD</strong></p>
                        <input type="radio" id="VACACION" name="tipo" value="VACACION">
                        <label >VACACION</label><br>
                        <input type="radio" id="LICENCIA" name="tipo" value="LICENCIA" >
                        <label>LICENCIA SIN GOCE DE HABER</label><br>
                        <input type="radio" id="COMPENSACION" name="tipo" value="COMPENSACION">
                        <label>COMPENSACION</label><br>
                        <input type="radio" id="ASUELTO" name="tipo" value="ASUELTO">
                        <label>ASUELTO</label><br>
                    </div>
                    <div class="container crear mb-2" id="crear">
                        <label ><strong>ESTADO DE SOLICITUD</strong></label><br>
                        <button type="button" id="compesacion_m" class="btn fubode-azul m-2" >COMPENZACION</button>
                        <button type="button" id="rechazado_m" class="btn btn-danger m-2" >RECHAZADO</button>
                        <button type="button" id="anulado_m" class="btn btn-danger m-2" >ANULADO</button>
                        <div class="container-fluid">
                            <select name="aceptado_m" id="aceptado_m" class="form-control">
                                <option value="ACEPTADO">ACEPTADO</option>
                                <option value="ANULADO">ANULADO</option>
                            </select>
                        </div>
                    </div>
                </div>        
            </div>   
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary m-2" data-dismiss="modal">CERRAR</button>
                <button type="button" id="editar_solicitud" class="btn btn-warning m-2" onclick="editar()">MODIFICAR</button>
            </div>
        </div>
    </div>
</div>
<div>
</div>

<!--Modal compensacion -->
<div class="modal fade" id="compensacion_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header fubode-azul">
                <h5 class="modal-title text-white text-center titulo" id="exampleModalLongTitle">DETALLE DE COMPENSACION</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <textarea id = "r_compensacion"class="form-control" row = "14" cols="10"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn fubode-azul m-2" data-dismiss="modal">ACEPTAR</button>
            </div>                
        </div>
    </div>
</div>

<!--Modal rechazo -->
<div class="modal fade" id="rechazo_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header bg-danger">
                <h5 class="modal-title text-white text-center titulo" id="exampleModalLongTitle">DETALLE DE COMPENSACION</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <textarea id = "detalleRechazo"class="form-control" row = "14" cols="10"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger m-2" data-dismiss="modal">ACEPTAR</button>
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
<script src="js/administrador/rrhhSolicitudes.js" type="text/javascript"></script>
<script src="js/administrador/fechas_rrhh.js" type="text/javascript"></script>
</body>
</html>