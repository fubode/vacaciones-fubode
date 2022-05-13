/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.List;
import java.util.Map;

/**
 *
 * @author USUARIO
 */
public class DAOSupervisor extends Conexion {

    public DAOSupervisor(Usuario usuario) {
        super(usuario);
    }

    public DAOSupervisor() {
    }

    public List<Map<String, Object>> listaSolicitudesPendientes() {
        String sql;
            sql = "select s.codigo_solicitud,s.fecha_solicitud, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias, "
                    + "f.apellido,f.nombre,ca.nombre_cargo,s.tipo,s.detalle_compensacion "
                    + "from solicitud_vacaciones s, funcionario f, cargo ca "
                    + "where s.codigo_funcionario=f.codigo_sai and "
                    + "f.supervisor = " + codigo_say + " and "
                    + "ca.codigo_cargo = f.cargo and "
                    + "s.estado='PENDIENTE' ";
        
        return this.ejecutarConsulta(sql);
    }

    public List<Map<String, Object>> listaSolicitudesAceptadas() {
        String sql = "";
        sql = "select s.codigo_solicitud,s.fecha_solicitud, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias, "
                + "f.apellido,f.nombre,ca.nombre_cargo,s.tipo,s.detalle_compensacion,s.supervisor "
                + "from solicitud_vacaciones s, funcionario f, cargo ca "
                + "where s.codigo_funcionario=f.codigo_sai and "
                + "f.supervisor = " + codigo_say + " and "
                + "ca.codigo_cargo = f.cargo and "
                + "s.estado='ACEPTADO' ";
        List<Map<String, Object>> solicitudes = this.ejecutarConsulta(sql);
        for (int i = 0; i < solicitudes.size(); i++) {
            Map<String, Object> usu = solicitudes.get(i);
            String codigo = usu.get("supervisor").toString();
            if (!codigo.equals("0")) {
                String nombre = nombreUsuario(codigo);
                usu.replace("supervisor", nombre);
                solicitudes.set(i, usu);
            }
        }
        return solicitudes;
    }

    public List<Map<String, Object>> listaSolicitudesRechazadas() {
        String sql;
        sql = "select s.codigo_solicitud,s.fecha_solicitud, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias, "
                + "f.apellido,f.nombre,ca.nombre_cargo,s.tipo,s.detalle_compensacion,s.supervisor "
                + "from solicitud_vacaciones s, funcionario f, cargo ca "
                + "where s.codigo_funcionario=f.codigo_sai and "
                + "f.supervisor = " + codigo_say + " and "
                + "ca.codigo_cargo = f.cargo and "
                + "s.estado='RECHAZADO' ";
        List<Map<String, Object>> solicitudes = this.ejecutarConsulta(sql);
        for (int i = 0; i < solicitudes.size(); i++) {
            Map<String, Object> usu = solicitudes.get(i);
            String codigo = usu.get("supervisor").toString();
            if (!codigo.equals("0")) {
                String nombre = nombreUsuario(codigo);
                usu.replace("supervisor", nombre);
                solicitudes.set(i, usu);
            }
        }
        return solicitudes;
    }

    public void modificarSolicitud(String codigo, String estado, String detalle) {
        String sql = "UPDATE public.solicitud_vacaciones "
                + "SET detalle_compensacion='" + detalle + "', estado='" + estado + "', fecha_estado=(select current_date),supervisor=" + codigo_say + " "
                + "WHERE codigo_solicitud='" + codigo + "'";
        this.actualizarConsulta(sql);
    }

    public List<Map<String, Object>> cargarDatos() {
        List<Map<String, Object>> funcionario = cargarDatosUsuario();
        int supervisor = Integer.parseInt(funcionario.get(0).get("supervisor").toString());
        if (supervisor != 0) {
            funcionario.add(cargarDatosSupervisor(supervisor));
        }

        return funcionario;
    }

    private List<Map<String, Object>> cargarDatosUsuario() {
        List<Map<String, Object>> usuario;
        //datos del funcionario
        String funcionario = "SELECT codigo_sai, apellido, f.nombre, fecha_ingreso,correo,ca.nombre_cargo, e.nombre_entidad,f.supervisor "
                + "FROM funcionario f, cargo ca, entidad e "
                + "where f.cargo=ca.codigo_cargo and f.entidad=e.codigo_entidad and f.codigo_sai=" + codigo_say;
        usuario = this.jdbcTemplate.queryForList(funcionario);
        return usuario;
    }

    private Map<String, Object> cargarDatosSupervisor(int supervisor) {
        List<Map<String, Object>> funcionarioList;
        //datos del funcionario
        String funcionario = "SELECT codigo_sai, apellido, f.nombre, fecha_ingreso,correo,ca.nombre_cargo, e.nombre_entidad,f.supervisor "
                + "FROM funcionario f, cargo ca, entidad e "
                + "where f.cargo=ca.codigo_cargo and f.entidad=e.codigo_entidad and f.codigo_sai=" + supervisor;
        funcionarioList = this.jdbcTemplate.queryForList(funcionario);
        return funcionarioList.get(0);
    }

    public List<Map<String, Object>> listaFuncionaios() {
        String sql;

        sql = "SELECT codigo_sai, apellido, nombre, fecha_ingreso, ci, correo, fecha_salida, supervisor, ca.nombre_cargo, e.nombre_entidad, f.estado "
                + "FROM funcionario f, entidad e, cargo ca "
                + "where f.entidad=e.codigo_entidad and f.cargo=ca.codigo_cargo and f.supervisor="+codigo_say;
        List<Map<String, Object>> listaFuncionaios = null;
        try {
            listaFuncionaios = this.ejecutarConsulta(sql);
            for (int i = 0; i < listaFuncionaios.size(); i++) {
                int codigo_say = Integer.parseInt(listaFuncionaios.get(i).get("codigo_sai").toString());
                Usuario usuario = new Usuario(codigo_say);
                Map<String, Object> funcionario = listaFuncionaios.get(i);
                listaFuncionaios.get(i).put("antiguedad", usuario.antiguedad());
                listaFuncionaios.get(i).put("supervisor", usuario.nombreSupervisor());
                listaFuncionaios.get(i).put("tomadas", usuario.vacacionesTomadas());
                listaFuncionaios.get(i).put("cumplidas", usuario.vacacionesCumplidas());
                listaFuncionaios.get(i).put("hayExcedentes", usuario.hayExcedentes());
                listaFuncionaios.get(i).put("saldo", usuario.saldoVacaciones());
            }

        } catch (Exception e) {
            String mensaje = e.getMessage();
            System.err.println(mensaje);
        }
        return listaFuncionaios;
    }
}
