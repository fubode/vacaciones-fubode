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
                            <li class="sombra"><a href="svrSupervisor?accion=funcionarios" class="nav-link">FUNCIONARIOS</a></li>
                            <li class="t"><a href="svrSupervisor?accion=pendientes" class="nav-link">SOLICITUDES PENDIENTES</a></li>
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


    <h1 class="text-center">FUNCIONARIOS</h1>    
    
    <table id="tablaFubode" class="table container table-hover table-striped table-bordered">
        <thead>
            <tr>
                <th>CODIGO SAI</th>
                <th>APELLIDOS</th>
                <th>NOMBRE</th>
                <th>FECHA INGRESO</th>
                <th>FECHA RETIRO</th>
                <th>ANTIGUEDAD</th>
                <th>ACUMULADOS</th>
                <th>TOMADAS</th>
                <th>SALDO</th>
                <th>CARGOS</th>
                <th>SUPERVISOR</th>
                <th>ESTADO</th>
                <th>ENTIDAD</th>                
            </tr>
        </thead>
        <tbody>

            <c:forEach var="dato" items="${lista}">
                <tr>
                    <td>${dato.codigo_sai}</td>
                    <td>${dato.apellido}</td>
                    <td>${dato.nombre}</td>
                    <td>${dato.fecha_ingreso}</td>
                    <td>
                        <c:choose>
                            <c:when  test="${dato.fecha_salida=='1750-01-01'}">
                                NINGUNO
                            </c:when>                            
                            <c:otherwise>
                                ${dato.fecha_salida}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="text-center">${dato.antiguedad}</td>
                    <td class="text-center">${dato.cumplidas}</td>
                    <td class="text-center">${dato.tomadas}</td>
                    <c:choose>
                        <c:when  test="${!dato.hayExcedentes}">
                            <td class="m-4 text-success">${dato.saldo}</td>
                        </c:when>
                        <c:otherwise>
                            <td class="m-4 text-danger">${dato.saldo}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${dato.nombre_cargo}</td>
                    <td>${dato.supervisor}</td>
                    <td>
                        <c:choose>
                            <c:when  test="${dato.estado=='ACTIVO'}">
                                <span class="badge badge-success m-2 p-2">${dato.estado}</span>                               
                            </c:when>                            
                            <c:otherwise>
                                <span class="badge badge-danger m-2 p-2">${dato.estado}</span>                               
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${dato.nombre_entidad}</td>                    
                </tr>
            </c:forEach>
        </tbody>
    </table>


    <!-- Modal funcionario nuevo -->
    <div class="modal fade" id="cargoNuevo" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header fubode-azul">
                    <h5 class="modal-title" id="exampleModalLongTitle">AGREGAR FUNCIONARIO</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="container card-body">
                    <div class="card">
                        <div class="container">
                            <label class=""><strong>CODIGO SAI</strong></label>
                            <input type="number" name="codigoSai" id="codigoSai" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>APELLIDOS</strong></label>
                            <input type="text" name="apellidos" id="apellidos" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>NOMBRE(S)</strong></label>
                            <input type="text" name="nombre" id="nombre" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>FECHA DE INGRESO</strong></label>
                            <input type="date" name="ingreso" id="ingreso" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>CI</strong></label>
                            <input type="text" name="ci" id="ci" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>CORREO ELECTRONICO</strong></label>
                            <input type="text" name="correo" id="correo" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>ASIGNAR ENTIDAD </strong></label>
                            <select name="entidad" id="entidad" class="form-control">
                                <option value="0">SELECCIONE ENTIDAD</option>
                                <c:forEach var="dato" items="${listaEntidad}">
                                    <option value="${dato.codigo_entidad}">${dato.nombre_entidad}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>ASIGNAR CARGO </strong></label>
                            <select name="cargo" id="cargo" class="form-control">
                                <c:forEach var="dato" items="${listaCargo}">
                                    <option value="${dato.codigo_cargo}">${dato.nombre_cargo}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>ASIGNAR SUPERVISOR </strong></label>
                            <select name="supervisor" id="supervisor" class="form-control">
                                <c:forEach var="dato" items="${listaFuncionario}">
                                    <option value="${dato.codigo_sai}">${dato.apellido}, ${dato.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>        
                </div>   
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary m-2" data-dismiss="modal">Cerrar</button>
                    <button type="button" id="agregarCargo" class="btn btn-success m-2" >AGREGAR</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal editar funcionario -->
    <div class="modal fade" id="editarFuncionario" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header fubode-naranja">
                    <h5 class="modal-title" id="exampleModalLongTitle">EDITAR FUNCIONARIO</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="container card-body">
                    <div class="card">
                        <div class="container">
                            <label class=""><strong>CODIGO SAI</strong></label>
                            <input type="number" name="codigoSai" id="codigoSai_e" class="form-control" disabled>                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>APELLIDOS</strong></label>
                            <input type="text" name="apellidos" id="apellidos_e" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>NOMBRE(S)</strong></label>
                            <input type="text" name="nombre" id="nombre_e" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>FECHA DE INGRESO</strong></label>
                            <input type="date" name="ingreso" id="ingreso_e" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>CI</strong></label>
                            <input type="text" name="ci" id="ci_e" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>CORREO ELECTRONICO</strong></label>
                            <input type="text" name="correo_e" id="correo_e" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>ASIGNAR ENTIDAD </strong></label>
                            <select name="entidad" id="entidades" class="form-control">
                                <option value="0">SELECCIONE ENTIDAD</option>
                                <c:forEach var="dato" items="${listaEntidad}">
                                    <option value="${dato.codigo_entidad}">${dato.nombre_entidad}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>ASIGNAR CARGO </strong></label>
                            <select name="cargo" id="cargo_e" class="form-control">
                                <c:forEach var="dato" items="${listaCargo}">
                                    <option value="${dato.codigo_cargo}">${dato.nombre_cargo}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="container mb-4">
                            <label class=""><strong>ASIGNAR SUPERVISOR </strong></label>
                            <select name="supervisor" id="supervisor_e" class="form-control">
                                <option value="0">NINGUNO</option>
                                <c:forEach var="dato" items="${listaFuncionario}">
                                    <option value="${dato.codigo_sai}">${dato.apellido}, ${dato.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>        
                </div>   
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary m-2" data-dismiss="modal">Cerrar</button>
                    <button type="button" id="editar_f" class="btn fubode-naranja m-2" >EDITAR</button>
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
</body>
</html>