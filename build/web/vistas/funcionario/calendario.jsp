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
        <title>SGV-FUBODE | CALENDARIO</title>
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
                <li class="t"><a href="srvUsuario?accion=nueva" class="nav-link">SOLICITUDES NUEVAS</a></li>
                <li class="t"><a href="srvUsuario?accion=pendientes" class="nav-link">SOLICITUDES PENDIENTES</a>
                </li>
                <li class="t"><a href="srvUsuario?accion=aceptadas" class="nav-link">SOLICITUDES ACEPTADAS</a>
                </li>
                <li class="t"><a href="srvUsuario?accion=rechazadas" class="nav-link">SOLICITUDES RECHAZADAS</a>
                </li>
                <li class="sombra"><a href="srvUsuario?accion=actividades" class="nav-link">CALENDARIO</a></li>
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

    <h1 class="text-center">Calendario de actividades</h1>

    <div class="container">
        <div class="container">
            <label ><strong>SELECIONE UN AÑO </strong></label>
            <select name="gestion" id="gestion" class="">
                <c:forEach var="dato" items="${gestiones}">
                    <c:choose>
                        <c:when  test="${gestion==dato}">
                            <option value="${dato}" selected>${dato}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${dato}">${dato}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>                    
            </select>
            <label ><strong>   SELECCIONE UN MES </strong></label>
            <select name="mes" id="mes" class="">
                <c:forEach var="dato" items="${meses}">
                    <c:choose>
                        <c:when  test="${mes==dato.valor}">
                            <option value="${dato.valor}" selected>${dato.mes}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${dato.valor}">${dato.mes}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>                         
            </select>
        </div>
    </div>
    <table class="table">
        <thead>
            <tr>
                <th>LUNES</th>
                <th>MARTES</th>
                <th>MIERCOLES</th>
                <th>JUEVES</th>
                <th>VIERNES</th>
                <th>SABADO</th>
                <th>DOMINGO</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="lista" items="${listas}">
                <tr>
                    <c:forEach var="dato" items="${lista}">
                        <c:choose>
                            <c:when  test="${dato.tipo=='NO_LABORAL'}">
                                <td class="bg-danger" data-toggle="modal" data-target="#${dato.diaNumeral}">
                                    ${dato.diaNumeral}  
                                    <button id="${dato.diaNumeral}"  class="btn btn-danger" onclick="mostrarDetalle('${dato.descripcion}')">
                                        <span  class="fa fa-eye">
                                    </button>
                                </td>
                            </c:when>
                            <c:when  test="${dato.tipo=='MANANA'}">
                                <td class="bg-warning text-white">
                                    ${dato.diaNumeral}                                    
                                    <button id="${dato.diaNumeral}"  class="btn btn-warning" onclick="mostrarDetalle('${dato.descripcion}')">
                                        <span  class="fa fa-eye">
                                    </button>
                                </td>
                            </c:when>
                            <c:when  test="${dato.tipo=='TARDE'}">
                                <td class="bg-info text-white">
                                    ${dato.diaNumeral}                                    
                                    <button id="${dato.diaNumeral}"  class="btn btn-info" onclick="mostrarDetalle('${dato.descripcion}')">
                                        <span  class="fa fa-eye">
                                    </button>
                                </td>
                            </c:when>
                            <c:when  test="${dato.tipo=='SABADO'}">
                                <td class="bg-primary text-white">
                                    ${dato.diaNumeral}                                    
                                    <button id="${dato.diaNumeral}"  class="btn btn-primary" onclick="mostrarDetalle('${dato.descripcion}')">
                                        <span  class="fa fa-eye">
                                    </button>
                                </td>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="" data-toggle="modal" data-target="#exampleModalCenter">${dato.diaNumeral}</td>
                            </c:otherwise>
                        </c:choose> 
                    </c:forEach>    
                </tr>
            </c:forEach>
        </tbody>
    </table>



    <div class="linea2 mt-4"></div>
    <div class="row">
        <div class="col-lg-2">
            <div class="text-center">
                LIBRE TODO EL DIA
            </div>
            <div class="container align-items-center text-center bg-danger circulo-color ">
            </div>
        </div>
        <div class="col-lg-2">
            <div class="text-center">
                LIBRE TODA LA MAÑANA
            </div>
            <div class="container align-items-center text-center bg-warning circulo-color ">
            </div>
        </div>
        <div class="col-lg-2">
            <div class="text-center">
                LIBRE TODA LA TARDE
            </div>
            <div class="container align-items-center text-center bg-info circulo-color ">
            </div>
        </div>
        <div class="col-lg-2">
            <div class="text-center">
                PRIMEROS DOS SABADOS
            </div>
            <div class="container align-items-center text-center bg-primary circulo-color ">
            </div>
        </div>
        <div class="col-lg-2">
            <div class="text-center">
                DIA LABORAL
            </div>
            <div class="container align-items-center text-center bg-white circulo-laboral">
            </div>
        </div>
    </div>                        

    <div class="modal fade" id="modalDetalle" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header fubode-naranja detalle_des">
                    <h5 class="modal-title text-white " id="exampleModalLongTitle">DETALLE DE FECHA</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    </button>
                </div>
                <div class="container card-body">
                    <div class="card">
                        <div class="container mt-3">
                            <label ><strong>DETALLE </strong></label>
                            <input  type="text" id ="detalle_modificar" class="container text-uppercase mb-3" disabled></input>
                        </div>                                        
                    </div>        
                </div>   
                <div class="modal-footer">
                    <button type="button" id ="registrar"class="btn btn-secondary"  data-dismiss="modal">CERRAR</button>
                </div>
            </div>
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
    <script src="js/funcionario/calendario.js" type="text/javascript"></script>
    <script src="js/temporizador.js" type="text/javascript"></script>
</body>
</html>