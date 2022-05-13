package Controlador;

import Helper.Date;
import Modelo.Conexion;
import Modelo.DAOSeguridad;
import Modelo.DAOUSUARIO;
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
import org.json.JSONObject;

@WebServlet(name = "srvSeguridad", urlPatterns = {"/srvSeguridad"})
public class srvSeguridad extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            HttpSession sesion = null;
            Usuario usuario = null;
            DAOSeguridad dao = null;
            sesion = request.getSession();
            usuario = (Usuario) sesion.getAttribute("usuario");
            dao = new DAOSeguridad(usuario);
            usuario = (Usuario) sesion.getAttribute("usuario");
            request.setAttribute("esSupervisor", usuario.isSupervisor());
            request.setAttribute("roles", dao.roles(usuario.getCodigo_say()));
            cargarDatosFuncionario(request, response, dao);
            if (accion != null) {
                switch (accion) {
                    case "cuentas":
                        cuentas(request, response, dao);
                        break;
                    case "roles":
                        roles(request, response, dao);
                        break;
                    case "bloquear":
                        bloquear(request, response, dao);
                        break;
                    case "desbloquear":
                        desbloquear(request, response, dao);
                        break;
                    case "asignar":
                        asignar(request, response, dao);
                        break;
                    case "noAsignar":
                        noAsignar(request, response, dao);
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

    private void cuentas(HttpServletRequest request, HttpServletResponse response, DAOSeguridad dao) {
        try {
            List<Map<String, Object>> listaCuentas = null;
            listaCuentas = dao.listaCuentas();
            request.setAttribute("lista", listaCuentas);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/seguridad/cuentas.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    private void bloquear(HttpServletRequest request, HttpServletResponse response, DAOSeguridad dao) {
        try {
            String codigo = request.getParameter("codigo");
            dao.bloquear(codigo);
            response.sendRedirect("srvSeguridad?accion=cuentas");
        } catch (Exception e) {
        }
    }
    
    private void desbloquear(HttpServletRequest request, HttpServletResponse response, DAOSeguridad dao) {
        try {
            String codigo = request.getParameter("codigo");
            dao.desbloquear(codigo);
            response.sendRedirect("srvSeguridad?accion=cuentas");
        } catch (Exception e) {
        }
    }

    private void cargarDatosFuncionario(HttpServletRequest request, HttpServletResponse response, DAOSeguridad dao) {
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

    private void roles(HttpServletRequest request, HttpServletResponse response, DAOSeguridad dao) {
         try {
            List<Map<String, Object>> listaRoles = null;
            listaRoles = dao.listaRoles();
            request.setAttribute("lista", listaRoles);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/seguridad/roles.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    private void noAsignar(HttpServletRequest request, HttpServletResponse response, DAOSeguridad dao) {
        try {
            String codigo = request.getParameter("codigo");
            String rol = request.getParameter("rol");
            dao.noAsignar(codigo,rol);
            response.sendRedirect("srvSeguridad?accion=roles");
        } catch (Exception e) {
        }
    }

private void asignar(HttpServletRequest request, HttpServletResponse response, DAOSeguridad dao) {
        try {
            String codigo = request.getParameter("codigo");
            String rol = request.getParameter("rol");
            dao.asignar(codigo,rol);
            response.sendRedirect("srvSeguridad?accion=roles");
        } catch (Exception e) {
        }
    }
}
