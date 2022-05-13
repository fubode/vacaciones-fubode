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

@WebServlet(name = "svrSupervisor", urlPatterns = {"/svrSupervisor"})
public class svrSupervisor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            HttpSession sesion = null;
            Usuario usuario = null;
            DAOSupervisor dao = null;
            sesion = request.getSession();
            usuario = (Usuario) sesion.getAttribute("usuario");
            dao = new DAOSupervisor(usuario);
            request.setAttribute("roles", dao.roles(usuario.getCodigo_say()));
            if(usuario.isSupervisor()){
                request.setAttribute("esSupervisor", usuario.isSupervisor());
                cargarDatosFuncionario(request, response, dao);
                if (accion != null) {
                    switch (accion) {                        
                        case "modificar":
                            modificarSolicitud(request, response,dao);  
                            break;
                        case "pendientes":
                            solicitudesPendientes(request, response, dao);
                            break;
                        case "aceptadas":
                            solicitudesAceptadas(request, response,dao);
                            break;
                        case "rechazadas":
                            solicitudesRechazadas(request, response,dao);     
                            break;
                        case "funcionarios":
                            funcionarios(request, response,dao);   
                            break;
                        default:
                            response.sendRedirect("identificar.jsp");
                            break;
                    }
                } else if (request.getParameter("cambiar") != null) {
                } else {
                    response.sendRedirect("identificar.jsp");
                }                
            }else{
                this.getServletConfig().getServletContext().getRequestDispatcher("/mensaje.jsp").forward(request, response);
            }
        } catch (Exception e) {
            String m = e.getMessage();
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
   
    private void cargarDatosFuncionario(HttpServletRequest request, HttpServletResponse response, DAOSupervisor dao) throws ServletException, IOException, JSONException {
        JSONObject usuario = dao.datosFuncionario();
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
  
    //solicitudes pendientes de los funcionarios que supervisa
    private void solicitudesPendientes(HttpServletRequest request, HttpServletResponse response, DAOSupervisor dao) throws ServletException, IOException {
        List<Map<String, Object>> usus = null;
        try {
            usus = dao.listaSolicitudesPendientes();
            request.setAttribute("pendientes", usus);

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistas/supervisor/pendientes_funcionario.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
        }
    }
    //solicitudes aceptadas de los funcionarios que supervisa
    private void solicitudesAceptadas(HttpServletRequest request, HttpServletResponse response,DAOSupervisor dao) throws ServletException, IOException {
        List<Map<String, Object>> solicitudes = null;
        try {
            solicitudes = dao.listaSolicitudesAceptadas();            
            request.setAttribute("lista_aceptados", solicitudes);
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistas/supervisor/aceptadas_funcionario.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
          } finally {
            dao = null;
        }
    }
    //solicitudes pendientes de los funcionarios que supervisa
    private void solicitudesRechazadas(HttpServletRequest request, HttpServletResponse response, DAOSupervisor dao) throws ServletException, IOException {
        List<Map<String, Object>> solicitudes = null;        
        try {
            solicitudes = dao.listaSolicitudesRechazadas();
            request.setAttribute("lista_rechazados", solicitudes);
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistas/supervisor/rechazadas_funcionario.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
        } finally {
            dao = null;
        }
    }

    private void modificarSolicitud(HttpServletRequest request, HttpServletResponse response, DAOSupervisor supervisor) {        
        if (request.getParameter("cod") != null) {
            String codigo = request.getParameter("cod");
            String estado = request.getParameter("estado");
            String detalle = request.getParameter("detalle");
            try {
                supervisor.modificarSolicitud(codigo, estado, detalle);
                response.sendRedirect("svrSupervisor?accion=pendientes");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se encontro el usuario");
        }

    }    

    private void funcionarios(HttpServletRequest request, HttpServletResponse response,DAOSupervisor dao) {
        List<Map<String, Object>> lista = null;
        try {
            lista = dao.listaFuncionaios();
            request.setAttribute("lista", lista);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/supervisor/funcionarios.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
    }
}
