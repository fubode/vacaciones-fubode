package Controlador;

import Helper.Calendario;
import Helper.Date;
import Modelo.Conexion;
import Modelo.DAOAdministrador;
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
import org.json.JSONObject;

@WebServlet(name = "srvSesion", urlPatterns = {"/srvSesion"})
public class srvSesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "inicio":
                        iniciar(request, response);
                        break;
                    case "verificar":
                        verificar(request, response);
                        break;
                    case "cerrar":
                        cerrar(request, response);
                        break;
                    case "SEGURIDAD":
                        SEGURIDAD(request, response);
                    case "RRHH":
                        RRHH(request, response);
                    case "cambiar":
                        cambiar(request, response);
                        break;
                    default:
                        response.sendRedirect("identificar.jsp");
                }
            } else if (request.getParameter("cambiar") != null) {
            } else {
                response.sendRedirect("identificar.jsp");
            }
        } catch (Exception e) {
            System.out.println("Error Calendareio " + e.getMessage());
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

    private void iniciar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/supervisor/supervisor.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
        }
    }

    private void cambiar(HttpServletRequest request, HttpServletResponse response) {

    }

    private void cerrar(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sesion;
        Usuario usuario = null;
        try {
            sesion = request.getSession();
            sesion.setAttribute("usuario", null);
            response.sendRedirect("identificar.jsp");
        } catch (Exception e) {
            try {
                this.getServletConfig().getServletContext().getRequestDispatcher("/mensaje.jsp").forward(request, response);

            } catch (Exception ex) {
                System.out.println("Error" + e.getMessage());
            }
        }
    }

    private void SEGURIDAD(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("srvSeguridad?accion=cuentas");
        } catch (Exception e) {

        }
    }

    private void RRHH(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("srvAdministrador?accion=solicitudes");
        } catch (Exception e) {

        }
    }

    private void verificar(HttpServletRequest request, HttpServletResponse response) {
        Conexion dao = new Conexion();
        HttpSession sesion;
        Usuario usuario = null;
        PrintWriter out = null;
        JSONObject json = new JSONObject();
        try {
            out = response.getWriter();
            String user = request.getParameter("usuario");
            String password = request.getParameter("password");
            usuario = dao.identificar(user, password);
            if (usuario != null) {
                if (!usuario.getEstado().equals("BLOQUEADO")) {
                    sesion = request.getSession();
                    sesion.setAttribute("usuario", usuario);
                    json.put("verficacion", true);
                } else {
                    json.put("verficacion", false);
                    json.put("estado", usuario.getEstado());
                }
            } else {
                json.put("verficacion", false);
                json.put("estado", "incorrecto");
            }

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
        } finally {
            out.print(json);
            out.close();
        }
    }
}
