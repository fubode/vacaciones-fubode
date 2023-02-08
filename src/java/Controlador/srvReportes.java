package Controlador;

import Helper.Calendario;
import Helper.Date;
import Modelo.DAOAdministrador;
import Modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(name = "srvReportes", urlPatterns = {"/srvReportes"})
public class srvReportes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            HttpSession sesion = null;
            Usuario usuario = null;
            DAOAdministrador dao = null;
            sesion = request.getSession();
            usuario = (Usuario) sesion.getAttribute("usuario");
            dao = new DAOAdministrador(usuario);
            request.setAttribute("roles", dao.roles(usuario.getCodigo_say()));
            if (accion != null) {
                request.setAttribute("esSupervisor", usuario.isSupervisor());
                switch (accion) {
                    case "reporteTodos":
                        reporteFuncionarioTodos(request, response, dao);
                        break;
                    case "reporteFuncionario":
                        reporteFuncionario(request, response, dao);
                        break;
                    case "reporteFun1":
                        reporteFun1(request, response, dao);
                        break;
                    case "reporteCargosTodos":
                        reporteCargosTodos(request, response, dao);
                        break;
                    case "reporteCargo":
                        reporteCargo(request, response, dao);
                        break;
                    case "reporteEntidadTodos":
                        reporteEntidadTodos(request, response, dao);
                        break;
                    case "reporteEntidad":
                        reporteEntidad(request, response, dao);
                        break;
                }
            } else if (request.getParameter("cambiar") != null) {
            } else {
                response.sendRedirect("identificar.jsp");
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
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

    private void habilitarfuncionario(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) {
        JSONObject json = new JSONObject();
        PrintWriter out = null;

        try {
            String cod = request.getParameter("cod");
            out = response.getWriter();
            json = dao.habilitarFuncionario(cod);

        } catch (Exception e) {
            System.out.println("-------" + e.getMessage());
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void reporteFuncionarioTodos(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        try {
            int codigoSay = 0;
            try {
                codigoSay = Integer.parseInt(request.getParameter("codigoSay"));
            } catch (Exception e) {
            }
            int funcionario = Integer.parseInt(request.getParameter("funcionario"));
            String tipo = request.getParameter("tipo");
            String estoFuncionario = request.getParameter("estoFuncionario");
            String estado = request.getParameter("estado");
            String fechaDesde = request.getParameter("desde");
            String fechaHasta = request.getParameter("hasta");
            Date desde = new Date(fechaDesde);
            Date hasta = new Date(fechaHasta);
            String nombreFuncionario = dao.funcionario();
            List<Map<String, Object>> solicitudes = null;
            solicitudes = dao.listaSolicitudes(funcionario, tipo, estado, desde, hasta, estoFuncionario);
            request.setAttribute("intervalo", desde.fechaImpresion() + " hasta el " + hasta.fechaImpresion());
            request.setAttribute("tipo", tipo);
            request.setAttribute("estado", estado);
            request.setAttribute("funcionario", nombreFuncionario);
            request.setAttribute("solicitudes", solicitudes);
            request.setAttribute("emitido", new Date().fechaActual().fechaImpresion());
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteFuncionario1.jsp").forward(request, response);
        } catch (Exception e) {
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteFuncionario1.jsp").forward(request, response);
        } finally {

        }
    }

    private void reporteFun1(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {

    }

    private void reporteFuncionario(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {

        try {

            int funcionario = Integer.parseInt(request.getParameter("funcionario"));
            String tipo = request.getParameter("tipo");
            String estoFuncionario = request.getParameter("estoFuncionario");
            String estado = request.getParameter("estado");
            String fechaDesde = request.getParameter("desde");
            String fechaHasta = request.getParameter("hasta");
            Date desde = new Date(fechaDesde);
            Date hasta = new Date(fechaHasta);
            String nombreFuncionario = dao.funcionario();

            Usuario usuario = new Usuario(funcionario);
            request.setAttribute("nombreCompleto", usuario.getNombreCompleto());
            request.setAttribute("codigoSai", funcionario);
            request.setAttribute("cargo", usuario.getCargo());
            request.setAttribute("entidad", usuario.getEntidad());
            request.setAttribute("correo", usuario.getCorreo());
            request.setAttribute("fechaIngreso", usuario.getFecha_ingreso().fechaImpresion());
            request.setAttribute("antiguedad", usuario.antiguedad());
            request.setAttribute("vacacionesCumplidas", usuario.vacacionesCumplidas());
            request.setAttribute("vacacionesTomadas", usuario.vacacionesTomadas());
            request.setAttribute("vacacionesSaldo", usuario.saldoVacaciones());
            request.setAttribute("vacacionesSinGoce", usuario.vacacionesSinGoce());
            request.setAttribute("vacacionesCompensaciones", usuario.vacacionesCompensaciones());
            request.setAttribute("intervalo", desde.fechaImpresion() + " hasta el " + hasta.fechaImpresion());
            request.setAttribute("tipo", tipo);
            request.setAttribute("estado", estado);
            request.setAttribute("funcionario", nombreFuncionario);
            request.setAttribute("emitido", new Date().fechaActual().fechaImpresion());
            request.setAttribute("usuario", usuario);

            List<Map<String, Object>> solicitudes = null;
            solicitudes = dao.listaSolicitudes(funcionario, tipo, estado, desde, hasta, estoFuncionario);
            request.setAttribute("solicitudes", solicitudes);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteFuncionario2.jsp").forward(request, response);
        } catch (Exception e) {
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/reportesFuncionario.jsp").forward(request, response);
        } finally {
        }
    }

    private void reporteCargosTodos(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        try {
            int cargos = Integer.parseInt(request.getParameter("cargos"));
            String tipo = request.getParameter("tipo");
            String estado = request.getParameter("estado");
            Date desde = new Date(request.getParameter("desde"));
            Date hasta = new Date(request.getParameter("hasta"));
            List<Map<String, Object>> solicitudes = null;
            solicitudes = dao.listaSolicitudesPorCargo(cargos, tipo, estado, desde, hasta);
            request.setAttribute("intervalo", desde.toString() + " hasta el " + hasta.toString());
            request.setAttribute("tipo", tipo);
            request.setAttribute("estado", estado);
            request.setAttribute("funcionario", dao.funcionario());
            request.setAttribute("emitido", new Date().fechaActual().fechaImpresion());
            request.setAttribute("solicitudes", solicitudes);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteCargo1.jsp").forward(request, response);
        } catch (Exception e) {
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteCargo1.jsp").forward(request, response);
        } finally {
        }
    }

    private void reporteCargo(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        try {
            int cargos = Integer.parseInt(request.getParameter("cargos"));
            String tipo = request.getParameter("tipo");
            String estado = request.getParameter("estado");
            Date desde = new Date(request.getParameter("desde"));
            Date hasta = new Date(request.getParameter("hasta"));
            JSONObject datosCargo = null;
            datosCargo = dao.obtenerCargo(cargos);
            request.setAttribute("codigo_cargo", datosCargo.get("codigo_cargo"));
            request.setAttribute("nombre_cargo", datosCargo.get("nombre_cargo"));
            request.setAttribute("intervalo", desde.toString() + " hasta el " + hasta.toString());
            request.setAttribute("tipo", tipo);
            request.setAttribute("estado", estado);
            request.setAttribute("funcionario", dao.funcionario());
            request.setAttribute("emitido", new Date().fechaActual().fechaImpresion());

            List<Map<String, Object>> solicitudes = null;
            solicitudes = dao.listaSolicitudesPorCargo(cargos, tipo, estado, desde, hasta);
            request.setAttribute("solicitudes", solicitudes);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteCargo2.jsp").forward(request, response);
        } catch (Exception e) {
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteCargo2.jsp").forward(request, response);
        } finally {
        }
    }

    private void reporteEntidadTodos(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        try {
            int cargos = Integer.parseInt(request.getParameter("cargos"));
            int entidad = Integer.parseInt(request.getParameter("entidad"));
            String tipo = request.getParameter("tipo");
            String estado = request.getParameter("estado");
            Date desde = new Date(request.getParameter("desde"));
            Date hasta = new Date(request.getParameter("hasta"));
            List<Map<String, Object>> solicitudes = null;
            solicitudes = dao.listaSolicitudesPorEntidad(entidad, cargos, tipo, estado, desde, hasta);
            request.setAttribute("intervalo", desde.toString() + " hasta el " + hasta.toString());
            request.setAttribute("tipo", tipo);

            JSONObject entidadJSON = dao.obtenerEntidad(entidad);
            request.setAttribute("codigo_entidad", entidadJSON.get("codigo_entidad"));
            request.setAttribute("nombre_entidad", entidadJSON.get("nombre_entidad"));

            JSONObject cargoJSON = dao.obtenerCargo(cargos);
            request.setAttribute("codigo_cargo", cargoJSON.get("codigo_cargo"));
            request.setAttribute("nombre_cargo", cargoJSON.get("nombre_cargo"));

            request.setAttribute("estado", estado);
            request.setAttribute("solicitudes", solicitudes);
            request.setAttribute("funcionario", dao.funcionario());
            request.setAttribute("emitido", new Date().fechaActual().fechaImpresion());
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteEntidad1.jsp").forward(request, response);
        } catch (Exception e) {
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteEntidad1.jsp").forward(request, response);
        } finally {
        }
    }

    private void reporteEntidad(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        JSONObject json = new JSONObject();

        try {
            int cargos = Integer.parseInt(request.getParameter("cargos"));
            int entidad = Integer.parseInt(request.getParameter("entidad"));
            String tipo = request.getParameter("tipo");
            String estado = request.getParameter("estado");
            Date desde = new Date(request.getParameter("desde"));
            Date hasta = new Date(request.getParameter("hasta"));
            request.setAttribute("intervalo", desde.toString() + " hasta el " + hasta.toString());
            request.setAttribute("tipo", tipo);

            JSONObject entidadJSON = dao.obtenerEntidad(entidad);
            request.setAttribute("codigo_entidad", entidadJSON.get("codigo_entidad"));
            request.setAttribute("nombre_entidad", entidadJSON.get("nombre_entidad"));

            JSONObject cargoJSON = dao.obtenerCargo(cargos);
            request.setAttribute("codigo_cargo", cargoJSON.get("codigo_cargo"));
            request.setAttribute("nombre_cargo", cargoJSON.get("nombre_cargo"));

            request.setAttribute("estado", estado);
            request.setAttribute("funcionario", dao.funcionario());
            request.setAttribute("emitido", new Date().fechaActual().fechaImpresion());

            List<Map<String, Object>> solicitudes = null;
            solicitudes = dao.listaSolicitudesPorEntidad(entidad, cargos, tipo, estado, desde, hasta);
            request.setAttribute("solicitudes", solicitudes);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteEntidad1.jsp").forward(request, response);
        } catch (Exception e) {
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/reportes/reporteEntidad1.jsp").forward(request, response);
        } finally {
        }
    }
}
