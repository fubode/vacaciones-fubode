/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Helper.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class Usuario {

    private String usuario;
    private String password;
    private int codigo_say;
    private boolean isSupervisor;
    private List<String> roles;
    private Conexion conexion;
    private Date fecha_ingreso;
    private int supervisor;
    private String nombre;
    private String apellido;
    private String correo;
    private String cargo;
    private String entidad;
    private String nombreCompleto;
    private String estado;

    public boolean isIsSupervisor() {
        return isSupervisor;
    }

    public void setIsSupervisor(boolean isSupervisor) {
        this.isSupervisor = isSupervisor;
    }

    public Conexion getConexion() {
        return conexion;
    }

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public int getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(int supervisor) {
        this.supervisor = supervisor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public void setSupervisor(boolean supervisor) {
        this.isSupervisor = supervisor;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void addRol(String rol) {
        this.roles.add(rol);
    }

    public Usuario(String usuario, String password, int codigo_say) {
        this.usuario = usuario;
        this.password = password;
        this.codigo_say = codigo_say;
        this.isSupervisor = false;
        this.roles = new ArrayList<String>();
        this.conexion = new Conexion();
        this.estado = "DESBLOQUEADO";
    }

    public Usuario(int codigoSay) {
        this.codigo_say = codigoSay;
        this.conexion = new Conexion();
        List<Map<String, Object>> usuario = null;
        String sql = "SELECT codigo_sai, apellido, f.nombre, fecha_ingreso,correo,ca.nombre_cargo, e.nombre_entidad,f.supervisor,usuario,pass "
                + "FROM funcionario f, cargo ca, entidad e, cuenta cu "
                + "where cu.codigo_funcionario=f.codigo_sai and  f.cargo=ca.codigo_cargo and f.entidad=e.codigo_entidad and f.codigo_sai=" + codigo_say;
        usuario = conexion.jdbcTemplate.queryForList(sql);
        this.nombreCompleto = usuario.get(0).get("nombre") + " " + usuario.get(0).get("apellido");

        this.fecha_ingreso = new Date(usuario.get(0).get("fecha_ingreso").toString());
        this.cargo = usuario.get(0).get("nombre_cargo").toString();
        this.entidad = usuario.get(0).get("nombre_entidad").toString();
        this.correo = usuario.get(0).get("correo").toString();
        this.usuario = usuario.get(0).get("usuario").toString();
        this.password = usuario.get(0).get("pass").toString();
        this.supervisor = Integer.parseInt(usuario.get(0).get("supervisor").toString());
        this.estado = "DESBLOQUEADO";
    }

    public int getCodigo_say() {
        return codigo_say;
    }

    public void setCodigo_say(int codigo_say) {
        this.codigo_say = codigo_say;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int antiguedad() {
        return fecha_ingreso.antiguedad();
    }

    public double vacacionesCumplidas() {
        double vacacionesCumplidas = 0;
        List<Map<String, String>> gestiones = gestiones();
        for (Map<String, String> gestion : gestiones) {
            vacacionesCumplidas = vacacionesCumplidas + Double.parseDouble(gestion.get("saldo"));
        }
        return vacacionesCumplidas;
    }

    public double vacacionesTomadas() {
        double vacacionesTomadas = 0;
        List<Map<String, Object>> funcionarioList;
        String funcionario = "select sum(dias) from solicitud_vacaciones s, funcionario f "
                + "where s.codigo_funcionario=f.codigo_sai and "
                + "s.estado = 'ACEPTADO' and "
                + "(s.tipo = 'DUODESIMA' OR  s.tipo = 'VACACION') and "
                + "s.codigo_funcionario= " + codigo_say;
        try {
            funcionarioList = conexion.jdbcTemplate.queryForList(funcionario);
            double dias = Double.parseDouble(funcionarioList.get(0).get("sum").toString());
            vacacionesTomadas = dias;
        } catch (Exception e) {
        }
        return vacacionesTomadas;
    }

    public List<Map<String, String>> gestiones() {
        List<Map<String, String>> funcionarioList = new LinkedList<>();
        int antiguedad = fecha_ingreso.antiguedad();
        int gestion = fecha_ingreso.getGestion();
        int mes = fecha_ingreso.getMes();
        for (int i = 1; i <= antiguedad; i++) {
            Map<String, String> map = new HashMap<>();
            if (i <= 5 && i > 0) {
                map.put("gestion", ((gestion + i) + "/" + mes));
                map.put("saldo", String.valueOf(15));
                funcionarioList.add(map);
            } else {
                if (i <= 10 && i > 5) {
                    map.put("gestion", ((gestion + i) + "/" + mes));
                    map.put("saldo", String.valueOf(20));
                    funcionarioList.add(map);
                } else {
                    if (i > 10) {
                        map.put("gestion", ((gestion + i) + "/" + mes));
                        map.put("saldo", String.valueOf(30));
                        funcionarioList.add(map);
                    }
                }
            }
        }
        return funcionarioList;
    }

    public double saldoVacaciones() {
        return vacacionesCumplidas() - vacacionesTomadas();
    }

    public double vacacionesSinGoce() {
        double sinGoce = 0;
        String sql = "select sum(dias) "
                + "from solicitud_vacaciones s, funcionario f "
                + "where s.codigo_funcionario=f.codigo_sai and s.codigo_funcionario = " + codigo_say + " and tipo = 'LICENCIA' and s.estado='ACEPTADO'";
        List<Map<String, Object>> lista = conexion.jdbcTemplate.queryForList(sql);
        try {
            sinGoce = Double.parseDouble(lista.get(0).get("sum").toString());
        } catch (Exception e) {

        }
        return sinGoce;
    }

    public double vacacionesCompensaciones() {
        double compensaciones = 0;
        String sql = "select sum(dias) "
                + "from solicitud_vacaciones s, funcionario f "
                + "where s.codigo_funcionario=f.codigo_sai and s.codigo_funcionario = " + codigo_say + " and tipo = 'COMPENSACION' and s.estado='ACEPTADO'";
        List<Map<String, Object>> lista = conexion.jdbcTemplate.queryForList(sql);
        try {
            compensaciones = Double.parseDouble(lista.get(0).get("sum").toString());
        } catch (Exception e) {

        }
        return compensaciones;
    }

    public String nombreSupervisor() {
        String nombreUsuario = "";
        try {
            String sql = "select nombre, apellido from funcionario where codigo_sai=" + supervisor;
            List<Map<String, Object>> usuario;
            usuario = conexion.jdbcTemplate.queryForList(sql);
            nombreUsuario = usuario.get(0).get("apellido").toString() + ", " + usuario.get(0).get("nombre").toString();

        } catch (Exception e) {
            nombreUsuario = "NINGUNO";
        }
        return nombreUsuario;
    }

    public boolean hayExcedentes() {
        int antiguedad = antiguedad();
        double saldoTotalCumulado = saldoVacaciones();
        int maximo = 0;
        boolean hayExcedentes = false;
        if (antiguedad > 0 && antiguedad <= 4) {
            maximo = 30;
        } else {
            if (antiguedad >= 5 && antiguedad <= 9) {
                maximo = 40;
            } else {
                if (antiguedad >= 10) {
                    maximo = 60;
                }
            }
        }
        if(saldoTotalCumulado >= maximo){
            hayExcedentes = true;
        }else{
            if (saldoTotalCumulado==0) {
                hayExcedentes = false;
            }
        }        
        return hayExcedentes;
    }

    void setEstado(String bloqueado) {
        this.estado = bloqueado;
    }
    
    public String getEstado(){
        return estado;
    }
}
