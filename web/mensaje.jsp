<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>SGV-FUBODE | Error</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.7 -->
        <link href="recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <!-- Font Awesome -->
        <link href="recursos/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <!-- Ionicons -->
        <link href="recursos/Ionicons/css/ionicons.min.css" rel="stylesheet" type="text/css"/>
        <!-- Theme style -->
        <link href="recursos/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css"/>
        <!-- iCheck -->
        <link href="recursos/iCheck/square/blue.css" rel="stylesheet" type="text/css"/>

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
    </head>
    <body class="sidebar-closed sidebar-collapse" style="height: auto;">

        <div class="linea"></div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03"
                aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand" href="#">
            <img src="recursos/images/logo.png" alt="" />
        </a>
    </nav>
    <div class="linea2"></div>
    
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <!-- Main content -->
            <section class="content">

                <div class="error-page">

                    <div class="error-content">
                        <h3><i class="fa fa-warning text-red"></i>Oops! Estubo inactivo por 15 min</h3>

                        <p>
                          Porfavor vuelva a iniciar  <a href="srvSesion?accion=cerrar"> sesion </a>
                        </p>               
                    </div>
                </div>
                <!-- /.error-page -->

            </section>
            <!-- /.content -->
        </div>
        <!-- jQuery -->
        <script src="recursos/js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <!-- Bootstrap 3.3.7 -->
        <script src="recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- iCheck -->
        <script src="recursos/iCheck/icheck.min.js" type="text/javascript"></script>

    </body>
</html>
