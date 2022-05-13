package Controlador;

import Helper.Calendario;
import Helper.Date;
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
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "srvGeneral", urlPatterns = {"/srvGeneral"})
public class srvGeneral extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            HttpSession sesion = request.getSession();
            Usuario usuario = (Usuario) sesion.getAttribute("usuario");
            DAOGeneral dao = new DAOGeneral(usuario);
            if (accion != null) {
                switch (accion) {
                    case "fechas":
                        calcular(request, response, dao);                    
                    case "vacaciones":
                        vacaciones(request, response, dao);                    
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

    private void calcular(HttpServletRequest request, HttpServletResponse response, DAOGeneral dao) throws ServletException, IOException, JSONException {
        String fecha_salida = request.getParameter("fecha_salida");
        String fecha_retorno = request.getParameter("fecha_retorno");
        String diferencia = request.getParameter("diferencia");
        String turno_salida = request.getParameter("turno_salida");
        String turno_retorno = request.getParameter("turno_retorno");
        String codigo = request.getParameter("cod");
        double diasNoLaborables = dao.diasNoLaborables(fecha_salida, fecha_retorno, diferencia, turno_salida, turno_retorno,codigo);
        PrintWriter out = null;
        JSONObject json = new JSONObject();
        try {
            out = response.getWriter();
            json.put("dias",diasNoLaborables);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        }finally{
            out.print(json);
            out.close();
        }
    }

    private void vacaciones(HttpServletRequest request, HttpServletResponse response, DAOGeneral dao) throws JSONException{
        double vacaciones = 0;
        PrintWriter out = null;
        JSONObject json = new JSONObject();
        try {
            vacaciones = dao.vacaciones();
            out = response.getWriter();
            json.put("vacaciones",vacaciones);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        }finally{
            out.print(json);
            out.close();
        }
    }
}
