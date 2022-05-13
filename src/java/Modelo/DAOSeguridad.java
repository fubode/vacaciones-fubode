package Modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DAOSeguridad extends Conexion {

    public DAOSeguridad(Usuario usuario) {
        super(usuario);
    }

    public DAOSeguridad() {
    }

    public List<Map<String, Object>> listaCuentas() {
        String sql = "select usuario, cu.codigo_funcionario,cu.estado, nombre,apellido "
                + "from cuenta cu, funcionario fun "
                + "where cu.codigo_funcionario=fun.codigo_sai";
        List<Map<String, Object>> listaCuentas = this.ejecutarConsulta(sql);

        return listaCuentas;
    }

    public void bloquear(String codigo) {
        int codigo_say = Integer.parseInt(codigo);
        String sql = "UPDATE public.cuenta "
                + "SET  estado='BLOQUEADO' "
                + "WHERE codigo_funcionario = " + codigo_say;
        this.actualizarConsulta(sql);
        String sql1 = "UPDATE cuenta "
                + "SET  intentos=0 "
                + "WHERE codigo_funcionario = " + codigo_say;
        this.actualizarConsulta(sql1);
    }

    public void desbloquear(String codigo) {
        int codigo_say = Integer.parseInt(codigo);
        String sql = "UPDATE public.cuenta "
                + "SET  estado='DESBLOQUEADO' "
                + "WHERE codigo_funcionario = " + codigo_say;
        this.actualizarConsulta(sql);
        String sql1 = "UPDATE cuenta "
                + "SET  intentos=3 "
                + "WHERE codigo_funcionario = " + codigo_say;
        this.actualizarConsulta(sql1);
    }

    public JSONObject cargarDatos() throws JSONException {
        return this.datosFuncionario();
    }

    public List<Map<String, Object>> listaRoles() {
        String sql = "select usuario, cu.codigo_funcionario,cu.estado, nombre,apellido "
                + "from cuenta cu, funcionario fun "
                + "where cu.codigo_funcionario=fun.codigo_sai";
        List<Map<String, Object>> listaRoles = this.ejecutarConsulta(sql);

        for (int i = 0; i < listaRoles.size(); i++) {
            Map<String, Object> user = listaRoles.get(i);
            int codigo = Integer.parseInt(user.get("codigo_funcionario").toString());
            String rol = "SELECT usuario, pass, codigo_funcionario, r.nombre_rol "
                    + "FROM public.cuenta cu, rol r "
                    + "where cu.codigo_funcionario = r.cod_sai and r.cod_sai=" + codigo;
            List<Map<String, Object>> roles = this.ejecutarConsulta(rol);
            switch (roles.size()) {
                case 0:
                    listaRoles.get(i).put("rol1", "");
                    listaRoles.get(i).put("rol2", "");
                    break;
                case 1:
                    String nombre_rol = roles.get(0).get("nombre_rol").toString();
                    if (nombre_rol.equals("RRHH")) {
                        listaRoles.get(i).put("rol1", nombre_rol);
                        listaRoles.get(i).put("rol2", "");
                    } else {
                        if (nombre_rol.equals("SEGURIDAD")) {
                            listaRoles.get(i).put("rol1", "");
                            listaRoles.get(i).put("rol2", nombre_rol);
                        }
                    }
                    break;
                case 2:
                    String nombre_rol2 = roles.get(0).get("nombre_rol").toString();
                    if (nombre_rol2.equals("RRHH")) {
                        listaRoles.get(i).put("rol1", nombre_rol2);
                    } else {
                        if (nombre_rol2.equals("SEGURIDAD")) {
                            listaRoles.get(i).put("rol2", nombre_rol2);
                        }
                    }
                    nombre_rol2 = roles.get(1).get("nombre_rol").toString();
                    if (nombre_rol2.equals("RRHH")) {
                        listaRoles.get(i).put("rol1", nombre_rol2);
                    } else {
                        if (nombre_rol2.equals("SEGURIDAD")) {
                            listaRoles.get(i).put("rol2", nombre_rol2);
                        }
                    }
                    break;
            }
        }
        return listaRoles;
    }

    public void asignar(String codigo, String rol) {
        try {
            String sql = "INSERT INTO public.rol(nombre_rol, cod_sai) "
                    + "VALUES (?,?)";
            this.jdbcTemplate.update(sql,
                    rol,
                    Integer.parseInt(codigo)
            );
        } catch (Exception e) {
            throw e;
        }

    }

    public void noAsignar(String codigo, String rol) {
        try {
            int say = Integer.parseInt(codigo);
            String sql = "DELETE FROM public.rol "
                    + "WHERE cod_sai=" + say
                    + " and nombre_rol ='" + rol + "'";
            this.jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw e;
        }
    }

}
