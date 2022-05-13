package Modelo;

import Helper.Date;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Conexion {
    
    private final String DRIVEMANAGER = "org.postgresql.Drive";
    private final String URL = "jdbc:postgresql://localhost:5432/vacaciones";
    private final String USER = "postgres";
    private final String PAS = "root";
    protected JdbcTemplate jdbcTemplate;
    private Connection conexion;
    private boolean transaccionIniciada;
    protected int codigo_say;
    protected Usuario usuario;
    //* javir arispe*/ protected int codigo_say = 3036;
    //* david luis */ protected int codigo_say = 3967;
    //* dayne */ protected int codigo_say = 3834;
    //* omar */ protected int codigo_say = 3042;
    //* IRIS */ protected int codigo_say = 3768;

    public Conexion() {
        DriverManagerDataSource dataSource = new DriveManager();           
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Conexion(Usuario usuario) {
        DriverManagerDataSource dataSource = new DriveManager();   
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.codigo_say = usuario.getCodigo_say();
        this.usuario = usuario;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    public Connection getConexion() {
        return conexion;
    }

    protected List<Map<String, Object>> ejecutarConsulta(String sql) {
        List<Map<String, Object>> lista = this.jdbcTemplate.queryForList(sql);
        return lista;
    }

    protected void actualizarConsulta(String sql) {
        this.jdbcTemplate.update(sql);
    }

    protected void cerrar(boolean wEstado) throws Exception {
        if (this.conexion != null) {
            if (this.transaccionIniciada == true) {
                try {
                    if (wEstado == true) {
                        this.conexion.commit();
                    } else {
                        this.conexion.rollback();
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
            try {
                this.conexion.close();
            } catch (Exception e) {
            }
        }
        this.conexion = null;
    }

    protected void ejecutarOrden(String wSQL) throws Exception {
        Statement st;

        if (this.conexion != null) {
            st = this.conexion.createStatement();
            st.executeUpdate(wSQL);
        }
    }

    protected ResultSet ejecutarOrdenDatos(String wSQL) throws Exception {
        Statement st;
        ResultSet rs = null;

        if (this.conexion != null) {
            st = this.conexion.createStatement();
            rs = st.executeQuery(wSQL);
        }

        return rs;
    }

    protected String codigo(String s) {
        String codigo = "";
        String sql = "select * from solicitud_vacaciones s where s.codigo_funcionario=" + codigo_say + " ORDER by id_vacaciones asc";
        List<Map<String, Object>> datos = this.jdbcTemplate.queryForList(sql);
        String caso = s;
        String tipo = "";
        Date fecha = new Date().fechaActual();
        switch (caso) {
            case "VACACION":
                tipo = "SV";
                break;
            case "COMPENSACION":
                tipo = "SC";
                break;
            case "LICENCIA":
                tipo = "SL";
                break;
            case "ASUETO":
                tipo = "ST";
            case "DUODESIMA":
                tipo = "SD";
                break;
        }
        codigo = tipo + "-" + codigo_say + "-" + fecha.fechaParaCodigo();
        if (datos == null || datos.size() == 0) {
            codigo = codigo + "-1";
        } else {
            String cod = datos.get(datos.size() - 1).get("id_vacaciones").toString();
            int serial = Integer.parseInt(cod);
            codigo = codigo + "-" + (serial + 1);
        }
        return codigo;
    }

    protected boolean esSupervisor() {
        boolean esSupervisor = false;

        return esSupervisor;
    }

    public String nombreUsuario(String codigo) {
        String nombreUsuario = "";
        try {
            String sql = "select nombre, apellido from funcionario where codigo_sai=" + codigo;
            List<Map<String, Object>> usuario;
            usuario = this.jdbcTemplate.queryForList(sql);
            nombreUsuario = usuario.get(0).get("apellido").toString() + ", " + usuario.get(0).get("nombre").toString();

        } catch (Exception e) {
            nombreUsuario = "NINGUNO";
        }
        return nombreUsuario;
    }

    public JSONObject datosFuncionario() throws JSONException {
        JSONObject json = new JSONObject();
        List<Map<String, Object>> usuario = funcionario(codigo_say);

        json.put("nombreFuncionario", usuario.get(0).get("nombre") + " " + usuario.get(0).get("apellido"));

        Date ingreso = new Date(usuario.get(0).get("fecha_ingreso").toString());
        json.put("nombre_cargo", usuario.get(0).get("nombre_cargo"));
        json.put("nombre_entidad", usuario.get(0).get("nombre_entidad"));
        json.put("fecha_ingreso", usuario.get(0).get("fecha_ingreso"));
        json.put("antiguedad", ingreso.antiguedad());
        json.put("correo", usuario.get(0).get("correo"));
        json.put("usuarios", usuario);

        int supervisor = Integer.parseInt(usuario.get(0).get("supervisor").toString());
        json.put("supervisor", supervisor);
        if (supervisor != 0) {
            List<Map<String, Object>> funcionario = funcionario(supervisor);
            json.put("supervisor_nombre", funcionario.get(0).get("nombre") + " " + funcionario.get(0).get("apellido"));
            json.put("supervisor_cargo", funcionario.get(0).get("nombre_cargo"));
            json.put("supervisor_entidad", funcionario.get(0).get("nombre_entidad"));
            json.put("supervisor_correo", funcionario.get(0).get("correo"));
        }
        return json;
    }

    public List<Map<String, Object>> funcionario(int codigo) {
        List<Map<String, Object>> funcionario = null;
        try {
            String sql = "SELECT codigo_sai, apellido, f.nombre, fecha_ingreso,correo,ca.nombre_cargo, e.nombre_entidad,f.supervisor "
                    + "FROM funcionario f, cargo ca, entidad e "
                    + "where f.cargo=ca.codigo_cargo and f.entidad=e.codigo_entidad and f.codigo_sai=" + codigo;
            funcionario = this.jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
        }
        return funcionario;
    }

    public int antiguedad() {
        List<Map<String, Object>> funcionario = null;
        try {
            String sql = "SELECT codigo_sai, fecha_ingreso "
                    + "FROM funcionario f "
                    + "where f.codigo_sai=" + codigo_say;
            funcionario = this.jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
        }
        Date ingreso = new Date(funcionario.get(0).get("fecha_ingreso").toString());
        return ingreso.antiguedad();
    }

    public Usuario identificar(String user, String password) {
        Usuario usuario = null;
        try {
            String sqlUsuario = "SELECT id_cuenta, usuario, pass, estado, codigo_funcionario "
                    + "FROM public.cuenta "
                    + "where usuario= '" + user + "'";
            List<Map<String, Object>> users = this.jdbcTemplate.queryForList(sqlUsuario);
            if (users.size() != 0) {
                int intentos = 0;
                String sqlIntentos = "SELECT  intentos "
                        + "FROM public.cuenta "
                        + "where usuario= '" + user + "'";
                List<Map<String, Object>> cantIntentos = this.jdbcTemplate.queryForList(sqlIntentos);
                if (cantIntentos.size() != 0) {
                    intentos = Integer.parseInt(cantIntentos.get(0).get("intentos").toString());
                }
                if (intentos != 0) {
                    if (password.equals(users.get(0).get("pass").toString())) {
                        usuario = new Usuario(users.get(0).get("usuario").toString(),
                                users.get(0).get("pass").toString(),
                                Integer.parseInt(users.get(0).get("codigo_funcionario").toString()));
                        String sqlSupervisor = "select * from funcionario where supervisor =" + usuario.getCodigo_say();
                        List<Map<String, Object>> supervisores = this.jdbcTemplate.queryForList(sqlSupervisor);
                        if (supervisores.size() != 0) {
                            usuario.setSupervisor(true);
                        }
                        String sqlRoles = "select r.nombre_rol from rol r, funcionario f where r.cod_sai=f.codigo_sai and f.codigo_sai=" + usuario.getCodigo_say();
                        List<Map<String, Object>> roles = this.jdbcTemplate.queryForList(sqlRoles);
                        for (Map<String, Object> rol : roles) {
                            usuario.addRol(rol.toString());
                        }
                    } else {
                        sqlUsuario = "SELECT id_cuenta, usuario, pass, estado, codigo_funcionario "
                                + "FROM public.cuenta "
                                + "where usuario= '" + user + "'";
                        users = this.jdbcTemplate.queryForList(sqlUsuario);
                        int codigo_say = Integer.parseInt(users.get(0).get("codigo_funcionario").toString());
                        usuario = new Usuario(codigo_say);
                        String sql = "UPDATE cuenta "
                                + "SET  intentos=intentos-1 "
                                + "WHERE codigo_funcionario = " + codigo_say;
                        this.actualizarConsulta(sql);
                        usuario = null;
                    }
                } else {
                    usuario = new Usuario(users.get(0).get("usuario").toString(),
                            users.get(0).get("pass").toString(),
                            Integer.parseInt(users.get(0).get("codigo_funcionario").toString()));
                    usuario.setEstado("BLOQUEADO");
                    String sql = "UPDATE public.cuenta "
                            + "SET  estado='BLOQUEADO' "
                            + "WHERE codigo_funcionario = " + usuario.getCodigo_say();
                    this.actualizarConsulta(sql);
                }
            }
        } catch (Exception e) {
        }
        return usuario;
    }

    public List<Map<String, Object>> roles(int codigo) {
        String sqlRoles = "select r.nombre_rol from rol r, funcionario f where r.cod_sai=f.codigo_sai and f.codigo_sai=" + codigo;
        List<Map<String, Object>> roles = this.jdbcTemplate.queryForList(sqlRoles);
        return roles;
    }
}
