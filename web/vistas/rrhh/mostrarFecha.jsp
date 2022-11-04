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
        <title>SGV-FUBODE | FECHA</title>
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
                        <li class="t"><a href="srvAdministrador?accion=solicitudes" class="nav-link">SOLICITUDES DE VACACIONES</a></li>                  
                        <li class="t"><a href="srvAdministrador?accion=funcionarios" class="nav-link">FUNCIONARIOS</a></li>
                        <li class="t"><a href="srvAdministrador?accion=entidades" class="nav-link">ENTIDADES</a></li>
                        <li class="T"><a href="srvAdministrador?accion=cargos" class="nav-link">CARGOS</a></li>
                        <li class="t"><a href="srvAdministrador?accion=reportesFuncionario" class="nav-link">REPORTES FUNCIONARIO</a></li>
                        <li class="t"><a href="srvAdministrador?accion=reportesCargos" class="nav-link">REPORTES CARGOS</a></li>
                        <li class="t"><a href="srvAdministrador?accion=reportesEntidades" class="nav-link">REPORTES ENTIDADES</a></li>                            
                        <li class="sombra"><a href="srvAdministrador?accion=calendario" class="nav-link">CALENDARIO</a></li>
                        <li class="nav-item dropdown t user">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <label><strong>${nombre_corto}</strong></label>
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
                </div>
            </div>
        </div>
    </nav>
    <div class="linea2"></div>

    <h1 class="text-center">FECHA </h1>

    <table id="tablaFubode" class="table container table-hover table-striped table-bordered ">
        <thead>
            <tr>
                <th>TIPO</th>
                <th>REFERENCIA</th>
                <th>DESCRIPCION</th>
                <th>NOMBRE ENTIDAD</th>
                <th>ACCION</th>
            </tr>
        </thead>
        <tbody>

            <c:forEach var="dato" items="${fechas}">
                <tr>
                    <td>${dato.tipo}</td>
                    <c:choose>
                        <c:when  test="${dato.tipo=='VARIOS'}">
                            <td class="bg-success text-white">
                            </td>
                        </c:when>
                        <c:when  test="${dato.tipo=='NO_LABORAL' && dato.entidad=='0'}">
                            <td class="bg-danger text-white">
                            </td>
                        </c:when>
                        <c:when  test="${dato.tipo=='NO_LABORAL' && dato.entidad!='0'}">
                            <td class="fubode-rojo text-white">
                            </td>
                        </c:when>

                        <c:when  test="${dato.tipo=='MANANA' && dato.entidad=='0'}">
                            <td class="bg-warning text-white">
                            </td>
                        </c:when>
                        <c:when  test="${dato.tipo=='MANANA' && dato.entidad!='0'}">
                            <td class="fubode-amarillo text-white">
                            </td>
                        </c:when>

                        <c:when  test="${dato.tipo=='TARDE' && dato.entidad=='0'}">
                            <td class="bg-info text-white">
                            </td>
                        </c:when>
                        <c:when  test="${dato.tipo=='TARDE' && dato.entidad!='0'}">
                            <td class="fubode-celeste text-white">
                            </td>
                        </c:when>

                        <c:when  test="${dato.tipo=='SABADO' && dato.entidad=='0'}">
                            <td class="bg-primary text-white">
                            </td>
                        </c:when>
                        <c:when  test="${dato.tipo=='SABADO' && dato.entidad!='0'}">
                            <td class="fubode-azul text-white">
                            </td>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose> 
                    <td>${dato.descripcion_estado}</td>
                    <td>${dato.nombre_entidad}</td>
                    <td>
                        <div class="d-flex justify-content-center">
                            <div class="btn-group" role="group" aria-label="Basic example">
                                <button id="" type="button" class="btn btn-warning" onclick="mostrarDetalle('${dato.descripcion_estado}', '${dato.fecha}', '${dato.entidad}', '${dato.tipo}', '${dato.nombre_entidad}', '${dato.id_fechas}')">
                                    <span  class="fa fa-pencil-square">
                                </button>  
                                <button id="${dato.codigo_cargo}" type="button" class="btn btn-danger" onclick="eliminarFecha('${dato.id_fechas}')">
                                    <span  class="fa fa-trash">
                                </button> 
                            </div>
                        </div>  
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- modal detalle de fecha -->          
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
                            <label ><strong>FECHA </strong></label>
                            <input type="date" name = "fecha"  id="fecha_modificar" class="form-control" disabled/>
                            <label ><strong>ENTIDAD </strong></label>
                            <input type="text" name = "fecha"  id="entidad_modificar" class="form-control" disabled/>
                            <label ><strong>DETALLE </strong></label>
                            <textarea id ="detalle_modificar" class="container text-uppercase mb-3" row = "14" cols="10"></textarea>
                        </div>                                        
                    </div>        
                </div>   
                <div class="modal-footer">
                    <button type="button" id ="registrar"class="btn btn-warning"  onclick="editarFecha()">EDITAR</button>
                    <button type="button" id ="registrar"class="btn btn-danger"  onclick="eliminarFecha()">ELIMINAR</button>
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
    <script src="js/administrador/mostrarFechas.js" type="text/javascript"></script>
</body>
</html>