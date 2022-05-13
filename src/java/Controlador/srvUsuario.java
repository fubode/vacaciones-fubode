package Controlador;

import Helper.Calendario;
import Helper.Date;
import Modelo.DAOSupervisor;
import Modelo.DAOUSUARIO;
import Modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "srvUsuario", urlPatterns = {"/srvUsuario"})
public class srvUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");

        try {
            HttpSession sesion = null;
            Usuario usuario = null;
            DAOUSUARIO dao = null;
            sesion = request.getSession();
            usuario = (Usuario) sesion.getAttribute("usuario");
            dao = new DAOUSUARIO(usuario);
            request.setAttribute("esSupervisor", usuario.isSupervisor());
            request.setAttribute("roles", dao.roles(usuario.getCodigo_say()));
            cargarDatosFuncionario(request, response, dao);
            if (accion != null) {
                switch (accion) {
                    case "enviarSolicitud":
                        enviarSolicitud(request, response, dao);
                        break;
                    case "nueva":
                        solicitudNueva(request, response, dao);
                        break;
                    case "pendientes":
                        solicitudesPendientes(request, response, dao);
                        break;
                    case "aceptadas":
                        solicitudesAceptadas(request, response, dao);
                        break;
                    case "rechazadas":
                        solicitudesRechazadas(request, response, dao);
                        break;
                    case "inicio":
                        inicio(request, response, dao);
                        break;
                    case "actividades":
                        calendario(request, response, dao);
                        break;
                    case "anular":
                        anular(request, response, dao);
                        break;
                    case "registrarFecha":
                        registrarFecha(request, response, dao);
                        break;
                    case "rrhh":
                        rrhh(request, response, dao);
                        break;
                    case "configuraciones":
                        configuraciones(request, response, dao);
                        break;
                    case "actualizar":
                        actualizar(request, response, dao);
                        break;
                    default:
                        response.sendRedirect("identificar.jsp");
                        break;
                }
            } else if (request.getParameter("cambiar") != null) {
            } else {
                response.sendRedirect("identificar.jsp");
            }
        } catch (Exception e) {
            try {
                this.getServletConfig().getServletContext().getRequestDispatcher("/mensaje.jsp").forward(request, response);

            } catch (Exception ex) {
                System.out.println("Error" + e.getMessage());
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>   

    private void solicitudNueva(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException {
        this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/funcionario/nuevas.jsp").forward(request, response);
    }

    private void solicitudesPendientes(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException {
        List<Map<String, Object>> solicitudes = null;
        try {
            solicitudes = dao.listaSolicitudesPendientes();
            request.setAttribute("pendientes", solicitudes);
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistas/funcionario/pendientes.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
        } finally {
            dao = null;
        }
    }

    private void solicitudesAceptadas(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException {
        List<Map<String, Object>> solicitudes = null;
        try {
            solicitudes = dao.listaSolicitudesAceptadas();
            request.setAttribute("lista_aceptados", solicitudes);
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistas/funcionario/aceptadas.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
        } finally {
            dao = null;
        }
    }

    private void solicitudesRechazadas(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException {
        List<Map<String, Object>> solicitudes = null;
        try {
            solicitudes = dao.listaSolicitudesRechazadas();
            request.setAttribute("lista_rechazados", solicitudes);
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistas/funcionario/rechazadas.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
        } finally {
            dao = null;
        }
    }

    private void inicio(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException {
        try {
            cargarDatosEspecificosFuncionario(request, response, dao);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/funcionario/funcionario.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
        }
    }

    private void anular(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException {
        if (request.getParameter("cod") != null) {
            String codigo = request.getParameter("cod");
            try {
                dao.anularSolicitud(codigo);
                response.sendRedirect("srvUsuario?accion=pendientes");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se encontro el usuario");
        }
    }

    private void enviarSolicitud(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException, JSONException {
        JSONObject json = null;
        PrintWriter out = null;
        try {
            String fecha_salida = request.getParameter("fecha_salida");
            String turno_salida = request.getParameter("turno_salida");
            String fecha_retorno = request.getParameter("fecha_retorno");
            String turno_retorno = request.getParameter("turno_retorno");
            String tipo = request.getParameter("tipo");
            String detalle = request.getParameter("detalle");
            String dias = request.getParameter("dias");
            String duodesimas = request.getParameter("duodesimas");
            String acpetar = request.getParameter("ACEPTAR");

            out = null;
            json = dao.enviarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, tipo, detalle, dias, duodesimas, acpetar);
            out = response.getWriter();
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void cargarDatosFuncionario(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException, JSONException {
        JSONObject usuario = dao.cargarDatos();
        request.setAttribute("nombreFuncionario", usuario.get("nombreFuncionario"));
        request.setAttribute("nombre_cargo", usuario.get("nombre_cargo"));
        request.setAttribute("nombre_entidad", usuario.get("nombre_entidad"));
        request.setAttribute("fecha_ingreso", usuario.get("fecha_ingreso"));
        Date ingreso = new Date(usuario.get("fecha_ingreso").toString());
        request.setAttribute("antiguedad", ingreso.antiguedad());
        request.setAttribute("correo", usuario.get("correo"));
        request.setAttribute("usuarios", usuario);
        int supervisor = usuario.getInt("supervisor");
        request.setAttribute("supervisor", supervisor);
        if (supervisor != 0) {
            request.setAttribute("supervisor_nombre", usuario.get("supervisor_nombre"));
            request.setAttribute("supervisor_cargo", usuario.get("nombre_cargo"));
            request.setAttribute("supervisor_entidad", usuario.get("nombre_entidad"));
            request.setAttribute("supervisor_correo", usuario.get("correo"));
        }
    }

    private void cargarDatosEspecificosFuncionario(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException, JSONException {
        //List<Map<String, Object>> datos = dao.vacacionesTomadas();
        double vacaciones = dao.vacacionesTomadas();
        double vacacionesTomadas = dao.vacacionesTomadas();
        request.setAttribute("vacacionesTomadas", vacacionesTomadas);
        double vacacionesTotal = dao.vacacionesTomadas();
        double sinGoce = dao.sinGoce();
        double compensacion = dao.compensaciones();
        List<Map<String, String>> gestiones = dao.gestiones();

        double gestioTotal = 0;
        for (Map<String, String> g : gestiones) {
            gestioTotal += Double.parseDouble(g.get("saldo").toString());
        }
        request.setAttribute("gestioTotal", gestioTotal);
        List<Map<String, String>> saldo = new LinkedList<>();
        double diferencia = 0;
        boolean flag = true;
        double saldoAcumulado = 0;
        for (int i = 0; i < gestiones.size(); i++) {
            saldoAcumulado += Double.parseDouble(gestiones.get(i).get("saldo").toString());
            if (vacaciones > 0) {
                double saldoGestion = Double.parseDouble(gestiones.get(i).get("saldo").toString());
                diferencia = saldoGestion - vacaciones;
                if (diferencia > 0 && flag) {
                    Map<String, String> nuevoSaldo = new HashMap<>();
                    nuevoSaldo.put("saldo", String.valueOf(diferencia));
                    nuevoSaldo.put("gestion", gestiones.get(i).get("gestion").toString());
                    gestiones.set(i, nuevoSaldo);
                    saldo.add(gestiones.get(i));
                    flag = false;
                }
                vacaciones = vacaciones - saldoGestion;
            } else {
                if (diferencia > 0 && !flag) {
                    saldo.add(gestiones.get(i));
                } else {
                    if (diferencia == 0) {
                        saldo.add(gestiones.get(i));
                    }
                }
            }
        }
        boolean hayExcedentes = false;
        double saldoTotalCumulado = saldoAcumulado - vacacionesTotal;
        Date fecha_ingreso = dao.fechaIngreso();
        int antiguedad = fecha_ingreso.antiguedad();
        hayExcedentes = hayExcedentes(antiguedad, saldoTotalCumulado);

        request.setAttribute("hayExcedentes", hayExcedentes);
        request.setAttribute("saldo", saldo);
        request.setAttribute("sinGoce", sinGoce);
        request.setAttribute("acumulado", saldoTotalCumulado);
        request.setAttribute("compensacion", compensacion);
        request.setAttribute("vacaciones", dao.vacacionesTomadas());
    }

    private boolean hayExcedentes(int antiguedad, double saldoTotalCumulado) {
        int beneficio = 0;
        int maximo = 0;
        boolean hayExcedentes = false;

        if (saldoTotalCumulado >= 0) {
            if (antiguedad > 0 && antiguedad <= 5) {
                beneficio = 15 * antiguedad;
                maximo = 30;
            } else {
                if (antiguedad >= 6 && antiguedad <= 10) {
                    int sobrante = antiguedad - 5;
                    beneficio = (5 * 15) + (sobrante * 20);
                    maximo = 40;
                } else {
                    if (antiguedad >= 11) {
                        int sobrante = antiguedad - 10;
                        beneficio = (5 * 15) + (5 * 20) + (sobrante * 30);
                        maximo = 60;
                    }
                }
            }
            if (saldoTotalCumulado >= maximo) {
                hayExcedentes = true;
            }
        } else {
            hayExcedentes = true;
        }
        return hayExcedentes;
    }

    private void calendario(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException {
        int gestion = 0;
        int mes = 0;
        try {
            gestion = Integer.parseInt(request.getParameter("gestion"));
            mes = Integer.parseInt(request.getParameter("mes"));
        } catch (Exception e) {

        }
        if (gestion == 0 || mes == 0) {
            Date date = new Date();
            gestion = date.fechaActual().getGestion();
            mes = date.fechaActual().getMes();
        }
        List<List<Map<String, String>>> listaActividades = dao.listaActividadesEntidad(gestion, mes);
        Calendario calendario = new Calendario(gestion, mes);
        List<Map<String, Object>> entidades = dao.entidades();
        List<List<Map<String, String>>> listas = calendario.dias_calendario();
        request.setAttribute("listas", listaActividades);
        request.setAttribute("meses", calendario.meses());
        request.setAttribute("gestiones", calendario.gestiones());
        request.setAttribute("mes", mes);
        request.setAttribute("gestion", gestion);
        request.setAttribute("entidades", entidades);
        this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/funcionario/calendario.jsp").forward(request, response);
    }

    private void registrarFecha(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException {
        try {
            String fecha = request.getParameter("fecha");
            String entidad = request.getParameter("entidad");
            String turno = request.getParameter("turno");
            String detalle = request.getParameter("detalle");
            dao.registrarFecha(fecha, entidad, turno, detalle);
            response.sendRedirect("srvUsuario?accion=calendario");
        } catch (Exception e) {
            System.out.println("-------" + e.getMessage());
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
        }
    }

    private void rrhh(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) throws ServletException, IOException {
        int gestion = 0;
        int mes = 0;
        try {
            gestion = Integer.parseInt(request.getParameter("gestion"));
            mes = Integer.parseInt(request.getParameter("mes"));
        } catch (Exception e) {

        }
        if (gestion == 0 || mes == 0) {
            Date date = new Date();
            gestion = date.fechaActual().getGestion();
            mes = date.fechaActual().getMes();
        }
        List<List<Map<String, String>>> listaActividades = dao.listaActividades(gestion, mes);
        Calendario calendario = new Calendario(gestion, mes);
        List<Map<String, Object>> entidades = dao.entidades();
        List<List<Map<String, String>>> listas = calendario.dias_calendario();
        request.setAttribute("listas", listaActividades);
        request.setAttribute("meses", calendario.meses());
        request.setAttribute("gestiones", calendario.gestiones());
        request.setAttribute("mes", mes);
        request.setAttribute("gestion", gestion);
        request.setAttribute("entidades", entidades);
        this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/calendario.jsp").forward(request, response);
    }

    private void configuraciones(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) {
        try {
            request.setAttribute("user", dao.getUsuario().getUsuario());
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/funcionario/configuraciones.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response, DAOUSUARIO dao) {
        try {
            String usuario = request.getParameter("usuario");
            String pass = request.getParameter("pass");
            dao.actualizar(usuario,pass);
            response.sendRedirect("srvUsuario?accion=inicio");
        } catch (Exception e) {
            
        }
    }
}
