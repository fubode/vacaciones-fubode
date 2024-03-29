<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
        <link href="recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="recursos/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="recursos/css/estilos.css" rel="stylesheet" type="text/css"/>
        <!-- style para table -->
        <link href="recursos/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <link href="recursos/css/dataTables.bootstrap4.min.css" rel="stylesheet" type="text/css"/>
        <link href="swetalert/sweetalert.css" rel="stylesheet" type="text/css"/>
        <title>SGV-FUBODE | SOLICITUD NUEVA</title>
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
                <li class="t"><a href="srvUsuario?accion=inicio" class="nav-link text-black">INICIO</a>
                </li>
                <li class="sombra"><a href="srvUsuario?accion=nueva" class="nav-link">SOLICITUDES NUEVAS</a></li>
                <li class="t"><a href="srvUsuario?accion=pendientes" class="nav-link">SOLICITUDES PENDIENTES</a>
                </li>
                <li class="t"><a href="srvUsuario?accion=aceptadas" class="nav-link">SOLICITUDES ACEPTADAS</a>
                </li>
                <li class="t"><a href="srvUsuario?accion=rechazadas" class="nav-link">SOLICITUDES RECHAZADAS</a>
                </li>
                <li class="t"><a href="srvUsuario?accion=actividades" class="nav-link">CALENDARIO</a></li>
                <li class="nav-item dropdown t user">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <label><strong>${nombre_corto}</strong></label>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
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
    </nav>
    <div class="linea2"></div>

    <h1 class="text-center">SOLICITUD NUEVA</h1>
    <div>
        <input type="hidden" name = "vacaciones" id="vacaciones" value="${acumulado}"/>        
        <input type="hidden" name = "fecha" id="fecha" value="${fecha}"/>        
        <div class="container card-body">
            <div class="card">
                <div class="container mt-3">
                    <label ><strong>FECHA DE SALIDA </strong></label>
                    <input type="date" name = "fecha_salida"  id="fecha_salida" class="form-control" value='${fecha_salida}'/>
                    <label ><strong>TURNO DE SALIDA </strong>
                        <select name="turno_salida" id="turno_salida" class="form-control">
                            <option value="MAÑANA">MAÑANA</option>
                            <option value="TARDE">TARDE</option>
                        </select>
                </div>
                <div class="container-fluid">
                    <label ><strong>FECHA DE RETORNO </strong></label>
                    <input type="date" name = "fecha_retorno" id="fecha_retorno" class="form-control" value='${fecha_retorno}'/>
                    <label ><strong>TURNO DE RETORNO </strong>
                        <select name="turno_retorno" id="turno_retorno" class="form-control">
                            <option value="MAÑANA">MAÑANA</option>
                            <option value="TARDE">TARDE</option>
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
                    <label>ASUETO</label><br>
                    <input type="radio" id="VFI" name="tipo" value="VFI">
                    <label>DIA DEL BIENESTAR</label><br>
                </div>
            </div>        
        </div>   
        <div class="container text-center">
            <a id="enviarSolicitud" href="<c:url value="srvUsuario">
               </c:url>"><button type="button" class="btn btn-success" data-toggle="tooltip"  title="Enviar Solicitud" data-original-title="Enviar solicitud">
                    ENVIAR SOLICITUD</button></a>
        </div>
    </div>

    <!-- Modal datos usuario -->
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header fubode-naranja">
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

    <!--Modal compensacion -->
    <div class="modal fade" id="modalCompensacion" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header fubode-azul">
                    <h5 class="modal-title text-white text-center" id="exampleModalLongTitle">DETALLE DE COMPENSACION</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <textarea id = "compensacion"class="form-control" row = "14" cols="10"></textarea>
                </div>
                <div class="modal-footer">
                    <button id = "enviarCompensacion" type="button" class="btn fubode-azul" data-dismiss="modal">ENVIAR SOLICITUD</button>
                </div>
            </div>
        </div>
    </div>

    <!--Modal compensacion -->
    <div class="modal fade" id="modalAsuelto" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header fubode-azul">
                    <h5 class="modal-title text-white text-center" id="exampleModalLongTitle">DETALLE DE ASUELTO O COMPENSACION</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <textarea id = "textAsuelto"class="form-control" row = "14" cols="10"></textarea>
                </div>
                <div class="modal-footer">
                    <button id = "enviarAsuelto" type="button" class="btn fubode-azul" data-dismiss="modal">ENVIAR SOLICITUD</button>
                </div>
            </div>
        </div>
    </div>

    <!--Modal confirmacion-->
    <div class="modal" id="modal-confirmacion"tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Confirmacion</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>ESTA SEGURO DE ENVIAR ESTA SOLICITUD </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">CANCELAR</button>
                    <button type="button" class="btn btn-primary" onclick="enviarSolicitudVacacion()">SI, ENVIAR</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal de Loading -->
    <div class="modal" id="loadingModal" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Cargando...</h5>
                </div>
                <div class="modal-body">
                    <div class="text-center">
                        <div class="spinner-border" role="status">
                            <span class="sr-only">Enviando Solicitud...</span>
                        </div>
                    </div>
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
    <script src="js/funcionesUsuario.js" type="text/javascript"></script>
    <script src="js/funcionario/fechas.js" type="text/javascript"></script>
    <script src="js/temporizador.js" type="text/javascript"></script>
</body>
</html>
