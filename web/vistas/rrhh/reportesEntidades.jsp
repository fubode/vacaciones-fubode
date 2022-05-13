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
                            <li class="sombra"><a href="srvAdministrador?accion=reportesEntidades" class="nav-link">REPORTES ENTIDADES</a></li> 
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


    <h1 class="text-center">REPORTE ENTIDADES</h1>

    <div class="container-fluid">
        <div class="container-fluid row">            
            <div class="col-2">
                <p class="ml-5"><strong>Tipo Entidad</strong></p>
                <select name="entidad" id="entidad" class="form-control ml-5">
                    <option value="0" selected>TODOS</option>
                    <c:forEach var="dato" items="${lista}">
                        <option value="${dato.codigo_entidad}">${dato.nombre_entidad}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-3">
                <p><strong>Seleecione un cargo</strong></p>
                <select name="cargos" id="cargos" class="form-control ml-4">
                    <option value="0" selected>TODOS</option>
                    <c:forEach var="dato" items="${listaCargo}">
                        <option value="${dato.codigo_cargo}">${dato.nombre_cargo}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-1">
                <p><strong>Tipo</strong></p>
                <select name="tipo" id="tipo" class="form-control">
                    <option value="TODOS" >TODOS</option>
                    <option value="VACACION" >VACACION</option>
                    <option value="COMPENSACION" >COMPENSACION</option>
                    <option value="LICENCIA" >SIN GOCE DE HABER</option>
                    <option value="ASUETO" >ASUETO</option>
                    <option value="DUODESIMA" >DUODESIMA</option>
                </select>
            </div>
            <div class="col-1">
                <p><strong>Estado</strong></p>
                <select name="estado" id="estado" class="form-control">
                    <option value="TODOS" selected>TODOS</option>
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
                <button class="btn btn-success" id="buscarEntidad" >BUSCAR</button>
                <button class="btn btn-secondary">LIMPIAR</button>
                <button class="btn fubode-azul">IMPRIMIR</button>
            </div>
        </div>
    </div>

    <hr>
    <div id="reporte" class="text-center">

    </div>
    <div  class="container-fluid">
        <div class="row">
            <div id ="datos" class="col-6 datos">

            </div>
            <div id="informacion" class="col-6 informacion">

            </div>
        </div>
    </div>

    <div id="tablaSolicitudes" class="tablaSolicitudes">

    </div>

    <script src="recursos/js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <script src="recursos/js/popper.min.js" type="text/javascript"></script>
    <script src="recursos/js/bootstrap.min.js" type="text/javascript"></script>  
    <!-- scripts para table -->
    <script src="swetalert/sweetalert.js" type="text/javascript"></script>
    <script src="recursos/js/dataTable.js" type="text/javascript"></script>
    <script src="recursos/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="js/reporteEntidades.js" type="text/javascript"></script>
</body>
</html>