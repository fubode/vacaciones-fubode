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
                            <li class="t"><a href="srvAdministrador?accion=solicitudes" class="nav-link">SOLICITUDES DE VACACIONES</a></li>                  
                            <li class="t"><a href="srvAdministrador?accion=funcionarios" class="nav-link">FUNCIONARIOS</a></li>
                            <li class="t"><a href="srvAdministrador?accion=entidades" class="nav-link">ENTIDADES</a></li>
                            <li class="t"><a href="srvAdministrador?accion=cargos" class="nav-link">CARGOS</a></li>
                            <li class="t"><a href="srvAdministrador?accion=reportesFuncionario" class="nav-link">REPORTES FUNCIONARIO</a></li>
                            <li class="t"><a href="srvAdministrador?accion=reportesCargos" class="nav-link">REPORTES CARGOS</a></li>
                            <li class="t"><a href="srvAdministrador?accion=reportesEntidades" class="nav-link">REPORTES ENTIDADES</a></li>                            
                            <li class="sombra"><a href="srvAdministrador?accion=calendario" class="nav-link">CALENDARIO</a></li>
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


    <h1 class="text-center">Calendario de actividades</h1>
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
        <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
    </svg>
    <div class="container">
        <div class="container">
            <a href="#" data-toggle="modal" data-target="#modalFecha">
                <button type="button" class="btn fubode-azul" data-toggle="tooltip"  title="Programar fecha" data-original-title="Programar fecha">
                    PROGRAMAR FECHA</button>
            </a>
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
                                <td class="bg-danger text-white">${dato.diaNumeral}</td>
                            </c:when>
                            <c:when  test="${dato.tipo=='MANANA'}">
                                <td class="bg-warning text-white">${dato.diaNumeral}</td>
                            </c:when>
                            <c:when  test="${dato.tipo=='TARDE'}">
                                <td class="bg-info text-white">${dato.diaNumeral}</td>
                            </c:when>
                            <c:when  test="${dato.tipo=='SABADO'}">
                                <td class="bg-primary text-white">${dato.diaNumeral}</td>
                            </c:when>
                            <c:otherwise>
                                <td class="">${dato.diaNumeral}</td>

                            </c:otherwise>
                        </c:choose> 
                    </c:forEach>    
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="linea2"></div>
    <label>PARA TODAS LAS ENTIDADES</label>
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
        <div class="col-lg-2">
            <div class="text-center">
                VARIAS ACTIVIDADES
            </div>
            <div class="container align-items-center text-center bg-success circulo-color">
            </div>
        </div>
    </div>
    <div class="linea2 mt-4"></div>
    <label>SOLO EN UNA ENTIDAD</label>
    <div class="row">
        <div class="col-lg-2">
            <div class="text-center">
                LIBRE TODO EL DIA
            </div>
            <div class="container align-items-center text-center fubode-rojo circulo-color ">
            </div>
        </div>
        <div class="col-lg-2">
            <div class="text-center">
                LIBRE TODA LA MAÑANA
            </div>
            <div class="container align-items-center text-center fubode-amarillo circulo-color ">
            </div>
        </div>
        <div class="col-lg-2">
            <div class="text-center">
                LIBRE TODA LA TARDE
            </div>
            <div class="container align-items-center text-center fubode-celeste circulo-color ">
            </div>
        </div>
        <div class="col-lg-2">
            <div class="text-center">
                PRIMEROS DOS SABADOS
            </div>
            <div class="container align-items-center text-center fubode-azul circulo-color ">
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
    <div class="modal fade" id="modalFecha" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header fubode-naranja">
                    <h5 class="modal-title text-white " id="exampleModalLongTitle">SOLICITUD ${dato.codigo_solicitud}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    </button>
                </div>
                <div class="container card-body">
                    <div class="card">
                        <div class="container mt-3">
                            <label ><strong>FECHA </strong></label>
                            <input type="date" name = "fecha"  id="fecha" class="form-control"/>
                            <label ><strong>TURNO </strong></label>
                            <select name="turno" id="turno" class="form-control">
                                <option value="NO_LABORAL">TODO EL DIA</option>
                                <option value="MANANA">MAÑANA</option>
                                <option value="TARDE">TARDE</option>
                            </select>
                            <label ><strong>ENTIDAD</strong></label>
                            <select name="entidad" id="entidad" class="form-control">
                                <option value="0">TODAS</option>
                                <c:forEach var="entidad" items="${entidades}">
                                    <option value="${entidad.codigo_entidad}">${entidad.nombre_entidad}</option>                                                        
                                </c:forEach>
                            </select>
                            <label ><strong>DETALLE </strong></label>
                            <textarea id = "detalle"class="container" row = "14" cols="10"></textarea>
                        </div>                                        
                    </div>        
                </div>   
                <div class="modal-footer">
                    <button type="button" id ="registrar"class="btn fubode-azul" data-dismiss="modal">REGISTRAR</button>
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
    <script src="js/calendario_rrhh.js" type="text/javascript"></script>
</body>
</html>