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
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
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
                            <li class="sombra"><a href="srvUsuario?accion=inicio" class="nav-link">INICIO</a></li>
                            <li class="t"><a href="srvUsuario?accion=nueva" class="nav-link">SOLICITUDES NUEVAS</a></li>                  
                            <li class="t"><a href="srvUsuario?accion=pendientes" class="nav-link">SOLICITUDES PENDIENTES</a></li>
                            <li class="t"><a href="srvUsuario?accion=aceptadas" class="nav-link">SOLICITUDES ACEPTADAS</a></li>
                            <li class="t"><a href="srvUsuario?accion=rechazadas" class="nav-link">SOLICITUDES RECHAZADAS</a></li>
                            <li class="t"><a href="srvUsuario?accion=actividades" class="nav-link">CALENDARIO</a></li>
                            <li class="nav-item dropdown t user">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    JP
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


    <h1 class="text-center">CONFIGURACIONES</h1>
    <div class="container-fluid d-flex justify-content-center">
        <div class="card" style="width: 100rem;">
            <div class="card-body">
                <div class="input-group input-group-lg m-4">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroup-sizing-lg">USUARIO</span>
                    </div>
                    <input type="text" id="usuario" value="${user}" class="form-control" aria-label="Large" aria-describedby="inputGroup-sizing-sm">
                </div>

                <div class="input-group input-group-lg m-4">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroup-sizing-lg">CONTRASEÑA ACTUAL</span>
                    </div>
                    <input type="password" id="actual" class="form-control" value="" aria-label="Large" aria-describedby="inputGroup-sizing-sm">
                </div>

                <div class="input-group input-group-lg m-4">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroup-sizing-lg">REPIA CONTRASEÑA</span>
                    </div>
                    <input type="password" id="repetir" class="form-control" value="" aria-label="Large" aria-describedby="inputGroup-sizing-sm">
                </div>
                <div class="container-fluid d-flex justify-content-center">
                    <button class="btn btn-success" onclick="actualizar()">ACTUALIZAR CONTRASEÑA</button>                
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
    <script src="swetalert/sweetalert.js" type="text/javascript"></script>
    <script src="js/funcionario/actualizar.js" type="text/javascript"></script>
</body>
</html>



