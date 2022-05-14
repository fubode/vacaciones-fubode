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

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    Error 500
                </h1>
                <ol class="breadcrumb">
                    <li><a href="srvUsuario?accion=verificar"><i class="fa fa-dashboard"></i> Inicio</a></li>
                    <li class="active"><a href="#">Error 500</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">

                <div class="error-page">
                    <h2 class="headline text-red">500</h2>

                    <div class="error-content">
                        <h3><i class="fa fa-warning text-red"></i>Oops! Algo salió mal</h3>

                        <p>
                            Trabajaremos para solucionarlo de inmediato.
                            Mientras tanto, puedes solucionarlo <a href="#"> -- </a>
                        </p>

                        <form class="search-form">
                            <div class="input-group">
                                <input type="text" name="search" class="form-control" placeholder="Search">

                                <div class="input-group-btn">
                                    <button type="submit" name="submit" class="btn btn-danger btn-flat"><i class="fa fa-search"></i>
                                    </button>
                                </div>
                            </div>
                            <!-- /.input-group -->
                        </form>
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
