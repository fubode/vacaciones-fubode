/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Helper.Date;
import java.util.LinkedList;
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
        List<Map<String, Object>> listaFuncionaios = listaFuncionaios();
        List<Map<String, Object>> listaSolicitudes = new LinkedList<>();
        String sql;
        try {
            for (int i = 0; i < listaFuncionaios.size(); i++) {
                int codigo_say = Integer.parseInt(listaFuncionaios.get(i).get("codigo_sai").toString());
                sql = "select f.codigo_sai, s.codigo_solicitud,s.fecha_solicitud, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias, "
                        + "f.apellido,f.nombre,ca.nombre_cargo,s.tipo,s.detalle_compensacion "
                        + "from solicitud_vacaciones s, funcionario f, cargo ca "
                        + "where s.codigo_funcionario=f.codigo_sai and "
                        + "f.codigo_sai = " + codigo_say + " and "
                        + "ca.codigo_cargo = f.cargo and "
                        + "s.estado='PENDIENTE' ";
                List<Map<String, Object>> solicitudes = this.ejecutarConsulta(sql);
                for (int j = 0; j < solicitudes.size(); j++) {
                    Map<String, Object> solicitud = solicitudes.get(j);
                    solicitud.replace("fecha_salida", new Date(solicitud.get("fecha_salida").toString()).fechaImpresion());
                    solicitud.replace("fecha_retorno", new Date(solicitud.get("fecha_retorno").toString()).fechaImpresion());
                    solicitud.replace("fecha_estado", new Date(solicitud.get("fecha_estado").toString()).fechaImpresion());
                    listaSolicitudes.add(solicitud);
                }
            }
        } catch (Exception e) {
        }

        return listaSolicitudes;
    }

    public List<Map<String, Object>> listaSolicitudesAceptadas() {
        List<Map<String, Object>> listaFuncionaios = listaFuncionaios();
        List<Map<String, Object>> listaSolicitudes = new LinkedList<>();
        String sql = "";
        try {
            for (int i = 0; i < listaFuncionaios.size(); i++) {
                int codigo_say = Integer.parseInt(listaFuncionaios.get(i).get("codigo_sai").toString());
                sql = "select s.codigo_solicitud,s.fecha_solicitud, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias, "
                        + "f.apellido,f.nombre,ca.nombre_cargo,s.tipo,s.detalle_compensacion,s.supervisor "
                        + "from solicitud_vacaciones s, funcionario f, cargo ca "
                        + "where s.codigo_funcionario=f.codigo_sai and "
                        + "f.codigo_sai = " + codigo_say + " and "
                        + "ca.codigo_cargo = f.cargo and "
                        + "s.estado='ACEPTADO' ";
                List<Map<String, Object>> solicitudes = this.ejecutarConsulta(sql);
                for (int j = 0; j < solicitudes.size(); j++) {
                    Map<String, Object> solicitud = solicitudes.get(j);
                    solicitud.replace("fecha_salida", new Date(solicitud.get("fecha_salida").toString()).fechaImpresion());
                    solicitud.replace("fecha_retorno", new Date(solicitud.get("fecha_retorno").toString()).fechaImpresion());
                    solicitud.replace("fecha_estado", new Date(solicitud.get("fecha_estado").toString()).fechaImpresion());
                    String codigo = solicitud.get("supervisor").toString();
                    if (!codigo.equals("0")) {
                        String nombre = nombreUsuario(codigo);
                        solicitud.replace("supervisor", nombre);
                        solicitudes.set(j, solicitud);
                    }
                    listaSolicitudes.add(solicitud);
                }
            }
        } catch (Exception e) {
        }
        return listaSolicitudes;
    }

    public List<Map<String, Object>> listaSolicitudesRechazadas() {
        List<Map<String, Object>> listaFuncionaios = listaFuncionaios();
        List<Map<String, Object>> listaSolicitudes = new LinkedList<>();
        try {
            for (int i = 0; i < listaFuncionaios.size(); i++) {
                int codigo_say = Integer.parseInt(listaFuncionaios.get(i).get("codigo_sai").toString());
                String sql = "select s.codigo_solicitud,s.fecha_solicitud, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias, "
                        + "f.apellido,f.nombre,ca.nombre_cargo,s.tipo,s.descripcion_estado,s.supervisor "
                        + "from solicitud_vacaciones s, funcionario f, cargo ca "
                        + "where s.codigo_funcionario=f.codigo_sai and "
                        + "f.codigo_sai = " + codigo_say + " and "
                        + "ca.codigo_cargo = f.cargo and "
                        + "s.estado='RECHAZADO' ";
                List<Map<String, Object>> solicitudes = this.ejecutarConsulta(sql);
                for (int j = 0; j < solicitudes.size(); j++) {
                    Map<String, Object> solicitud = solicitudes.get(j);
                    solicitud.replace("fecha_salida", new Date(solicitud.get("fecha_salida").toString()).fechaImpresion());
                    solicitud.replace("fecha_retorno", new Date(solicitud.get("fecha_retorno").toString()).fechaImpresion());
                    solicitud.replace("fecha_estado", new Date(solicitud.get("fecha_estado").toString()).fechaImpresion());
                    String codigo = solicitud.get("supervisor").toString();
                    if (!codigo.equals("0")) {
                        String nombre = nombreUsuario(codigo);
                        solicitud.replace("supervisor", nombre);
                        solicitudes.set(j, solicitud);
                    }
                    listaSolicitudes.add(solicitud);
                }
            }
        } catch (Exception e) {
        }
        return listaSolicitudes;
    }

    public void modificarSolicitud(String codigo, String estado, String detalle) {
        String sql = "UPDATE public.solicitud_vacaciones "
                + "SET descripcion_estado='" + detalle + "', estado='" + estado + "', fecha_estado=(select current_date),supervisor=" + codigo_say + " "
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
                + "where f.entidad=e.codigo_entidad and f.cargo=ca.codigo_cargo and f.supervisor=" + codigo_say;
        List<Map<String, Object>> listaFuncionaios = new LinkedList<>();
        List<Map<String, Object>> funcionarios = new LinkedList<>();
        try {
            funcionarios = this.ejecutarConsulta(sql);
            for (int i = 0; i < funcionarios.size(); i++) {
                int codigo_say = Integer.parseInt(funcionarios.get(i).get("codigo_sai").toString());
                Usuario usuario = new Usuario(codigo_say);
                Map<String, Object> funcionario = funcionarios.get(i);
                funcionario.put("antiguedad", usuario.antiguedad());
                funcionario.put("supervisor", usuario.nombreSupervisor());
                funcionario.put("tomadas", usuario.vacacionesTomadas());
                funcionario.put("cumplidas", usuario.vacacionesCumplidas());
                funcionario.put("hayExcedentes", usuario.hayExcedentes());
                funcionario.put("saldo", usuario.saldoVacaciones());
                listaFuncionaios.add(funcionario);
                listaFuncionaios = listaFuncionaios(listaFuncionaios, codigo_say);
            }

        } catch (Exception e) {
            String mensaje = e.getMessage();
            System.err.println(mensaje);
        }
        return listaFuncionaios;
    }

    private List<Map<String, Object>> listaFuncionaios(List<Map<String, Object>> listaFuncionaios, int codigo_say) {

        String sql = "SELECT codigo_sai, apellido, nombre, fecha_ingreso, ci, correo, fecha_salida, supervisor, ca.nombre_cargo, e.nombre_entidad, f.estado "
                + "FROM funcionario f, entidad e, cargo ca "
                + "where f.entidad=e.codigo_entidad and f.cargo=ca.codigo_cargo and f.supervisor=" + codigo_say;
        List<Map<String, Object>> funcionarios = new LinkedList<>();
        try {
            funcionarios = this.ejecutarConsulta(sql);
            for (int i = 0; i < funcionarios.size(); i++) {
                int say = Integer.parseInt(funcionarios.get(i).get("codigo_sai").toString());
                Usuario usuario = new Usuario(say);
                Map<String, Object> funcionario = funcionarios.get(i);
                funcionario.put("antiguedad", usuario.antiguedad());
                funcionario.put("supervisor", usuario.nombreSupervisor());
                funcionario.put("tomadas", usuario.vacacionesTomadas());
                funcionario.put("cumplidas", usuario.vacacionesCumplidas());
                funcionario.put("hayExcedentes", usuario.hayExcedentes());
                funcionario.put("saldo", usuario.saldoVacaciones());
                listaFuncionaios.add(funcionario);
                listaFuncionaios = listaFuncionaios(listaFuncionaios, say);
            }

        } catch (Exception e) {
        }
        return listaFuncionaios;
    }
}
