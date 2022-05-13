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

@WebServlet(name = "srvAdministrador", urlPatterns = {"/srvAdministrador"})
public class srvAdministrador extends HttpServlet {

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
                cargarDatosFuncionario(request, response, dao);
                switch (accion) {
                    case "funcionarios":
                        funcionarios(request, response,dao);
                        break;
                    case "crudFuncionario":
                        crudFuncionario(request, response, dao);
                        break;
                    case "entidadCrud":
                        entidadCrud(request, response, dao);
                        break;
                    case "entidades":
                        entidades(request, response,dao);
                        break;
                    case "solicitudes":
                        solicitudesVacacioes(request, response,dao);
                        break;
                    case "cargos":
                        cargos(request, response,dao);
                        break;                    
                    case "registrarCargo":
                        registrarCargo(request, response,dao);
                        break;
                    case "registrarEntidad":
                        registrarEntidad(request, response,dao);
                        break;
                    case "registrarFuncionario":
                        registrarFuncionario(request, response,dao);
                        break;
                    case "elimarCargo":
                        elimarCargo(request, response,dao);
                        break;
                    case "eliminarEntidad":
                        eliminarEntidad(request, response,dao);
                        break;
                    case "eliminarFuncionario":
                        eliminarFuncionario(request, response,dao);
                        break;
                    case "modificacionSolicitud":
                        modificacionSolicitud(request, response,dao);
                        break;
                    case "editaSolicitud":
                        editaSolicitud(request, response,dao);
                        break;
                    case "fechas":
                        nroDias(request, response,dao);
                        break;
                    case "calendario":
                        calendario(request, response,dao);
                        break;
                    case "obtenerCargo":
                        obtenerCargo(request, response,dao);
                        break;
                    case "obtenerEntidad":
                        obtenerEntidad(request, response,dao);
                        break;
                    case "obtenerFuncionario":
                        obtenerFuncionario(request, response);
                        break;
                    case "modificarSolicitud":
                        modificarSolicitud(request, response,dao);
                        break;
                    case "funcionarioRepetido":
                        funcionarioRepetido(request, response,dao);
                        break;
                    case "reportesFuncionario":
                        reportesFuncionario(request, response, dao);
                        break;
                    case "buscarFuncionario":
                        buscarFuncionario(request, response, dao);
                        break;
                    case "llenarCombo":
                        llenarCombo(request, response, dao);
                        break;
                    case "reportesCargos":
                        reportesCargos(request, response, dao);
                        break;
                    case "buscarCargo":
                        buscarCargo(request, response, dao);
                        break;
                    case "reportesEntidades":
                        reportesEntidades(request, response, dao);
                        break;
                    case "buscarEntidad":
                        buscarEntidad(request, response, dao);
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

    private void cargarDatosFuncionario(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException, JSONException {
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

    private void solicitudesVacacioes(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {
        
        List<Map<String, Object>> usus = null;
        try {
            usus = dao.listaSolicitudes();
            
            request.setAttribute("lista", usus);

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/solicitudes.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
        }
    }

    private void funcionarios(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {        
        List<Map<String, Object>> lista = null;
        try {
            lista = dao.listaFuncionaios();
            request.setAttribute("lista", lista);
            request.setAttribute("listaEntidad", dao.listaEntidades());
            request.setAttribute("listaCargo", dao.listaCargos());
            request.setAttribute("listaFuncionario", dao.listaFuncionario());
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/funcionarios.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
    }

    private void entidades(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {        
        List<Map<String, Object>> entidades = null;
        try {
            entidades = dao.listaEntidades();
            for (int i = 0; i < entidades.size(); i++) {
                Map<String, Object> entidad = entidades.get(i);
                int entidadSupervisor = Integer.parseInt(entidad.get("entidad_supervisor").toString());
                if (entidadSupervisor != 0) {
                    String nombre = dao.nombreEntidad(entidadSupervisor);
                    entidad.replace("entidad_supervisor", nombre);
                    entidades.set(i, entidad);
                } else {
                    entidad.replace("entidad_supervisor", "NINGUNO");
                    entidades.set(i, entidad);
                }
            }
            request.setAttribute("lista", entidades);

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/entidades.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
        }
    }

    private void cargos(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) {        
        List<Map<String, Object>> cargos = null;
        try {
            cargos = dao.listaCargos();
            request.setAttribute("lista", cargos);

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/cargos.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
        }
    }
    private void registrarCargo(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {        
        try {
            String codigo = request.getParameter("codigo");
            String nombre = request.getParameter("nombre");
            String tipo = request.getParameter("tipo");
            dao.registrarCargo(codigo, nombre, tipo);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/cargos.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
    }

    private void elimarCargo(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {        
        try {
            String codigo = request.getParameter("cod");
            dao.elimarCargo(codigo);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/cargos.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
    }

    private void registrarEntidad(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {
        
        try {
            String codigo = request.getParameter("codigo");
            String nombre = request.getParameter("nombre");
            String entidad = request.getParameter("entidad");
            String tipo = request.getParameter("tipo");
            String tipo_accion = request.getParameter("tipo_accion");
            dao.registrarEntidad(codigo, nombre, entidad, tipo, tipo_accion);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/entidades.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
    }

    private void eliminarEntidad(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {        
        try {
            String codigo = request.getParameter("cod");
            dao.eliminarEntidad(codigo);
            response.sendRedirect("srvAdministrador?accion=entidades");
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private void registrarFuncionario(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {        
        try {
            int codigoSai = Integer.parseInt(request.getParameter("codigoSai"));
            String apellidos = request.getParameter("apellidos");
            String nombre = request.getParameter("nombre");
            String f_ingreso = request.getParameter("ingreso").toString();
            Date ingreso = new Date(f_ingreso);
            String ci = request.getParameter("ci");
            String correo = request.getParameter("correo");
            String tipo = request.getParameter("tipo");
            int entidad = Integer.parseInt(request.getParameter("entidad"));
            int cargo = Integer.parseInt(request.getParameter("cargo"));
            int supervisor = Integer.parseInt(request.getParameter("supervisor"));
            dao.registrarFuncionario(tipo, codigoSai, apellidos, nombre, ingreso, ci, correo, entidad, cargo, supervisor);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/funcionarios.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            dao = null;
        }
    }

    private void eliminarFuncionario(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) {      
        if (request.getParameter("codigoSai") != null) {
            String codigoSai = request.getParameter("codigoSai");
            try {
                dao.anularSolicitud(codigoSai);
                this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/funcionarios.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se encontro el usuario");
        }
    }

    private void modificacionSolicitud(HttpServletRequest request, HttpServletResponse response,DAOAdministrador supervisor) {
        if (request.getParameter("cod") != null) {
            String codigo = request.getParameter("cod");
            String estado = request.getParameter("estado");
            String detalle = request.getParameter("detalle");
            try {
                supervisor.modificacionSolicitud(codigo, estado, detalle);
                response.sendRedirect("srvAdministrador?accion=solicitudes");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se encontro el usuario");
        }
    }

    private void calendario(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) throws ServletException, IOException {
        int gestion = 0;
        int mes = 0;
        try {
            gestion = Integer.parseInt(request.getParameter("gestion"));
            mes = Integer.parseInt(request.getParameter("mes"));
        } catch (Exception e) {

        }
        try {
            if (gestion == 0 || mes == 0) {
                Date date = new Date();
                gestion = date.fechaActual().getGestion();
                mes = date.fechaActual().getMes();
            }
            List<List<Map<String, String>>> listaActividades = dao.listaActividadesEntidad(gestion, mes);
            Calendario calendario = new Calendario(gestion, mes);
            List<Map<String, Object>> entidades = dao.listaEntidades();
            List<List<Map<String, String>>> listas = calendario.dias_calendario();
            request.setAttribute("listas", listaActividades);
            request.setAttribute("meses", calendario.meses());
            request.setAttribute("gestiones", calendario.gestiones());
            request.setAttribute("mes", mes);
            request.setAttribute("gestion", gestion);
            request.setAttribute("entidades", entidades);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/calendario.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
        }
    }

    private void editaSolicitud(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) throws ServletException, IOException {
        PrintWriter out = null;
        JSONObject json = null;
        try {
            
            out = response.getWriter();
            String codigo = request.getParameter("cod");
            json = dao.solicitud(codigo);
            //response.getWriter().write("hola mundoo "+codigo);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void nroDias(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {        
        String fecha_salida = request.getParameter("fecha_salida");
        String fecha_retorno = request.getParameter("fecha_retorno");
        String diferencia = request.getParameter("diferencia");
        String turno_salida = request.getParameter("turno_salida");
        String turno_retorno = request.getParameter("turno_retorno");
        String codigo = request.getParameter("cod");
        double diasNoLaborables = dao.diasNoLaborables(fecha_salida, fecha_retorno, diferencia, turno_salida, turno_retorno, codigo);
        PrintWriter out = null;
        JSONObject json = new JSONObject();
        try {
            out = response.getWriter();
            json.put("dias", diasNoLaborables);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void obtenerCargo(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {        
        JSONObject json = new JSONObject();
        PrintWriter out = null;
        int codigo = Integer.parseInt(request.getParameter("cod"));
        try {
            out = response.getWriter();
            json = dao.obtenerCargo(codigo);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void obtenerEntidad(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {        
        JSONObject json = new JSONObject();
        PrintWriter out = null;
        int codigo = Integer.parseInt(request.getParameter("cod"));
        try {
            out = response.getWriter();
            json = dao.obtenerEntidad(codigo);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void obtenerFuncionario(HttpServletRequest request, HttpServletResponse response) {
        DAOAdministrador dao = new DAOAdministrador();
        JSONObject json = new JSONObject();
        PrintWriter out = null;
        int codigo = Integer.parseInt(request.getParameter("cod"));
        try {
            out = response.getWriter();
            json = dao.obtenerFuncionario(codigo);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void modificarSolicitud(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {
        
        if (request.getParameter("cod") != null) {

            String codigo = request.getParameter("cod");
            String fecha_solicitud = request.getParameter("fecha_solicitud");
            String fecha_salida = request.getParameter("fecha_salida");
            String turno_salida = request.getParameter("turno_salida");
            String fecha_retorno = request.getParameter("fecha_retorno");
            String turno_retorno = request.getParameter("turno_retorno");
            String dias = request.getParameter("dias");
            String tipo = request.getParameter("tipo");
            String detalle_compensacion = request.getParameter("detalle_compensacion");
            String detalle_estado = request.getParameter("detalle_estado");
            String estado = request.getParameter("estado");
            try {
                dao.modificarSolicitud(codigo, fecha_solicitud, fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle_compensacion, detalle_estado, estado);
                response.sendRedirect("srvAdministrador?accion=solicitudes");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se encontro el usuario");
        }
    }

    private void funcionarioRepetido(HttpServletRequest request, HttpServletResponse response,DAOAdministrador dao) {
        
        JSONObject json = new JSONObject();
        PrintWriter out = null;
        int codigo = Integer.parseInt(request.getParameter("cod"));
        String ci = request.getParameter("ci");
        String correo = request.getParameter("correo");
        try {
            out = response.getWriter();
            json = dao.funcionarioRepetido(codigo, correo, ci);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void crudFuncionario(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) {
        JSONObject json = null;
        PrintWriter out = null;
        try {
            String tipo = request.getParameter("tipo");
            String codigoSai = request.getParameter("codigoSai");
            String apellidos = request.getParameter("apellidos");
            String nombre = request.getParameter("nombre");
            String ingreso = request.getParameter("ingreso");
            String ci = request.getParameter("ci");
            String correo = request.getParameter("correo");
            String entidad = request.getParameter("entidad");
            String cargo = request.getParameter("cargo");
            String superviso = request.getParameter("supervisor");
            String solicutud = request.getParameter("solicutud");
            String estado = request.getParameter("estado");
            String expedido = request.getParameter("expedido");

            out = null;
            json = dao.crudFuncionario(tipo, codigoSai, apellidos, nombre, ingreso, ci, correo, entidad, cargo, superviso, solicutud, estado,expedido);
            out = response.getWriter();
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void entidadCrud(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) {
        JSONObject json = null;
        PrintWriter out = null;
        try {
            String nombre = request.getParameter("nombre");
            String codigo = request.getParameter("codigo");
            String entidadSupervisora = request.getParameter("entidad");
            String tipoEntidad = request.getParameter("tipo");
            String tipo_accion = request.getParameter("tipo_accion");
            String estado = request.getParameter("estado");

            out = null;
            json = dao.entidadCrud(nombre, codigo, entidadSupervisora, tipoEntidad, tipo_accion, estado);
            out = response.getWriter();
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            System.out.println(e);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void reportesFuncionario(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        try {
            List<Map<String, Object>> listaFuncionarios = dao.listaFuncionaios();
            request.setAttribute("lista", listaFuncionarios);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/reportesFuncionario.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
        }
    }

    private void reportesCargos(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) {
        try {
            List<Map<String, Object>> listaCargo = dao.listaCargos();
            request.setAttribute("lista", listaCargo);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/reportesCargos.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
        }
    }

    private void reportesEntidades(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) {
        try {
            List<Map<String, Object>> listaEntidades = dao.listaEntidades();
            request.setAttribute("lista", listaEntidades);
            List<Map<String, Object>> listaCargo = dao.listaCargos();
            request.setAttribute("listaCargo", listaCargo);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/reportesEntidades.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
        }
    }

    private void buscarFuncionario(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        PrintWriter out = null;

        try {
            out = response.getWriter();
            int codigoSay = 0;
            try {
                codigoSay = Integer.parseInt(request.getParameter("codigoSay"));
            } catch (Exception e) {
            }
            int funcionario = Integer.parseInt(request.getParameter("funcionario"));
            String tipo = request.getParameter("tipo");
            String estado = request.getParameter("estado");
            Date desde = new Date(request.getParameter("desde"));
            Date hasta = new Date(request.getParameter("hasta"));
            String nombreFuncionario = dao.funcionario();
            if (funcionario == 0) {
                List<Map<String, Object>> solicitudes = null;
                solicitudes = dao.listaSolicitudes(funcionario, tipo, estado, desde, hasta);
                json.put("intervalo", desde.toString() + " hasta el " + hasta.toString());
                json.put("tipo", tipo);
                json.put("estado", estado);
                json.put("funcionario", nombreFuncionario);
                json.put("solicitudes", solicitudes);
            } else {
                Usuario usuario = new Usuario(funcionario);
                json.put("nombreCompleto", usuario.getNombreCompleto());
                json.put("nombreCompleto", usuario.getNombreCompleto());
                json.put("cargo", usuario.getCargo());
                json.put("entidad", usuario.getEntidad());
                json.put("correo", usuario.getCorreo());
                json.put("fechaIngreso", usuario.getFecha_ingreso());
                json.put("antiguedad", usuario.antiguedad());
                json.put("vacacionesCumplidas", usuario.vacacionesCumplidas());
                json.put("vacacionesTomadas", usuario.vacacionesTomadas());
                json.put("vacacionesSaldo", usuario.saldoVacaciones());
                json.put("vacacionesSinGoce", usuario.vacacionesSinGoce());
                json.put("vacacionesCompensaciones", usuario.vacacionesCompensaciones());
                json.put("intervalo", desde.fechaImpresion() + " hasta el " + hasta.fechaImpresion());
                json.put("tipo", tipo);
                json.put("estado", estado);
                json.put("funcionario", nombreFuncionario);
                json.put("usuario", usuario);

                List<Map<String, Object>> solicitudes = null;
                solicitudes = dao.listaSolicitudes(funcionario, tipo, estado, desde, hasta);
                json.put("solicitudes", solicitudes);
            }
        } catch (Exception e) {
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/reportesFuncionario.jsp").forward(request, response);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void buscarCargo(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        PrintWriter out = null;

        try {
            out = response.getWriter();
            int cargos = Integer.parseInt(request.getParameter("cargos"));
            String tipo = request.getParameter("tipo");
            String estado = request.getParameter("estado");
            Date desde = new Date(request.getParameter("desde"));
            Date hasta = new Date(request.getParameter("hasta"));
            if (cargos == 0) {
                List<Map<String, Object>> solicitudes = null;
                solicitudes = dao.listaSolicitudesPorCargo(cargos, tipo, estado, desde, hasta);
                json.put("intervalo", desde.toString() + " hasta el " + hasta.toString());
                json.put("tipo", tipo);
                json.put("estado", estado);
                json.put("solicitudes", solicitudes);
            } else {
                JSONObject datosCargo = null;
                datosCargo = dao.obtenerCargo(cargos);
                json.put("codigo_cargo", datosCargo.get("codigo_cargo"));
                json.put("nombre_cargo", datosCargo.get("nombre_cargo"));
                json.put("intervalo", desde.toString() + " hasta el " + hasta.toString());
                json.put("tipo", tipo);
                json.put("estado", estado);

                List<Map<String, Object>> solicitudes = null;
                solicitudes = dao.listaSolicitudesPorCargo(cargos, tipo, estado, desde, hasta);
                json.put("solicitudes", solicitudes);
            }
        } catch (Exception e) {
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/reportesFuncionario.jsp").forward(request, response);
        } finally {
            out.print(json);
            out.close();
        }
    }

    private void llenarCombo(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        PrintWriter out = null;

        try {
            out = response.getWriter();
            String codigoSay = request.getParameter("codigoSay");
            int codigo = Integer.parseInt(codigoSay);
            List<Map<String, Object>> funcionario = dao.funcionario(codigo);
            if(funcionario.size()!=0 &&funcionario!=null){
                json.put("codigo_sai",funcionario.get(0).get("codigo_sai").toString());
                json.put("estado",true);
            }else{
                json.put("codigo_sai","0");
                json.put("estado",false);
            }
                
        } catch (Exception e) {
            json.put("codigo_sai","0");
            json.put("estado",false);
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/reportesFuncionario.jsp").forward(request, response);
        } finally {
            out.print(json);
            out.close();
        }
    }
    private void buscarEntidad(HttpServletRequest request, HttpServletResponse response, DAOAdministrador dao) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        PrintWriter out = null;

        try {
            out = response.getWriter();
            int cargos = Integer.parseInt(request.getParameter("cargos"));
            int entidad = Integer.parseInt(request.getParameter("entidad"));
            String tipo = request.getParameter("tipo");
            String estado = request.getParameter("estado");
            Date desde = new Date(request.getParameter("desde"));
            Date hasta = new Date(request.getParameter("hasta"));
            if (entidad == 0) {
                List<Map<String, Object>> solicitudes = null;
                solicitudes = dao.listaSolicitudesPorEntidad(entidad, cargos, tipo, estado, desde, hasta);
                json.put("intervalo", desde.toString() + " hasta el " + hasta.toString());
                json.put("tipo", tipo);
                json.put("estado", estado);
                json.put("solicitudes", solicitudes);
            } else {
                JSONObject datosCargo = null;
                if (cargos != 0) {
                    datosCargo = dao.obtenerCargo(cargos);
                    json.put("codigo_cargo", datosCargo.get("codigo_cargo"));
                    json.put("nombre_cargo", datosCargo.get("nombre_cargo"));
                }
                JSONObject entidadJSON = null;
                entidadJSON = dao.obtenerEntidad(entidad);
                json.put("codigo_entidad", entidadJSON.get("codigo_entidad"));
                json.put("nombre_entidad", entidadJSON.get("nombre_entidad"));
                json.put("intervalo", desde.toString() + " hasta el " + hasta.toString());
                json.put("tipo", tipo);
                json.put("estado", estado);

                List<Map<String, Object>> solicitudes = null;
                solicitudes = dao.listaSolicitudesPorEntidad(entidad, cargos, tipo, estado, desde, hasta);
                json.put("solicitudes", solicitudes);
            }
        } catch (Exception e) {
            String enasaje = e.getMessage();
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/rrhh/reportesFuncionario.jsp").forward(request, response);
        } finally {
            out.print(json);
            out.close();
        }
    }
}
