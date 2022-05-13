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
                            <li class="t"><a href="srvAdministrador?accion=solicitudes" class="nav-link">SOLICITUDES DE VACACIONES</a></li>                  
                            <li class="t"><a href="srvAdministrador?accion=funcionarios" class="nav-link">FUNCIONARIOS</a></li>
                            <li class="t"><a href="srvAdministrador?accion=entidades" class="nav-link">ENTIDADES</a></li>
                            <li class="sombra"><a href="srvAdministrador?accion=cargos" class="nav-link">CARGOS</a></li>
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



    <h1 class="text-center">CARGOS</h1>
    <a href="#"  data-toggle="modal" data-target="#cargoNuevo">
        <button type="button" class="btn fubode-azul" data-toggle="tooltip"  title="Programar fecha" data-original-title="Agregar Cargo">
            AGREGAR NUEVO CARGO
        </button>
    </a>
    <table id="tablaFubode" class="table container table-hover table-striped table-bordered ">
        <thead>
            <tr>
                <th>CODIGO CARGO</th>
                <th>NOMBRE CARGO</th>
                <th>ACCIONES</th>
            </tr>
        </thead>
        <tbody>

            <c:forEach var="dato" items="${lista}">
                <tr>
                    <td>${dato.codigo_cargo}</td>
                    <td>${dato.nombre_cargo}</td>
                    <td>
                        <div class="d-flex justify-content-center">
                            <div class="btn-group" role="group" aria-label="Basic example">
                                <button id="${dato.codigo_cargo}" type="button" class="btn btn-warning" onclick="editarCargo(this)">
                                    <span  class="fa fa-pencil-square">
                                </button>  
                                    <button id="${dato.codigo_cargo}" type="button" class="btn btn-danger" onclick="eliminarCargo(this)">
                                    <span  class="fa fa-trash">
                                </button> 
                            </div>
                        </div>  
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Modal cargo nuevo -->
    <div class="modal fade" id="cargoNuevo" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header fubode-azul">
                    <h5 class="modal-title" id="exampleModalLongTitle">AGREGAR NUEVO CARGO</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="container card-body">
                    <div class="card">
                        <div class="container">
                            <label class="m-2"><strong>CODIGO CARGO: </strong></label>
                            <input type="number" name="codigo" id="codigoCargo" class="form-control">                    
                        </div>
                        <div class="container mb-4">
                            <label class="m-2"><strong>NOMBRE DEL CARGO: </strong></label>
                            <input type="text" name="nombre" id="nombre" class="form-control">                    
                        </div>
                    </div>        
                </div>   
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary m-2" data-dismiss="modal">Cerrar</button>
                    <button type="button" id="agregarCargo" class="btn btn-success m-2" onclick="registrarCargo()">AGREGAR</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal cargo editar -->
    <div class="modal fade" id="editar_cargo" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header fubode-naranja">
                    <h5 class="modal-title" id="exampleModalLongTitle">EDITAR CARGO</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="container card-body">
                    <div class="card">
                        <div class="container">
                            <label class="m-2"><strong>CODIGO CARGO: </strong></label>
                            <input type="number" name="codigo" id="e_codigoCargo" class="form-control" disabled>                    
                        </div>
                        <div class="container mb-4">
                            <label class="m-2"><strong>NOMBRE DEL CARGO: </strong></label>
                            <input type="text" name="nombre" id="e_nombreCargo" class="form-control">                    
                        </div>
                    </div>        
                </div>   
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary m-2" data-dismiss="modal">Cerrar</button>
                    <button type="button" id="editar" class="btn fubode-naranja m-2" onclick="editar()" >EDITAR</button>
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
    <script src="js/administrador/cargos.js" type="text/javascript"></script>
</body>
</html>