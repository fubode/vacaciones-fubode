package Modelo;

import Helper.Date;
import Helper.EncriptadorAES;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Conexion {

    protected JdbcTemplate jdbcTemplate;
    private Connection conexion;
    private boolean transaccionIniciada;
    protected int codigo_say;
    protected Usuario usuario;
    private final String EMISOR = "fubode.vacaciones@gmail.com";
    private final String CONTRASENA = "fpooxdsoatymykzn";
    private final String ENDPOINTCORREO = "http://181.115.207.107:8096/correo";
    

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

    public int getCodigo_say() {
        return codigo_say;
    }

    public Usuario getUsuario() {
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

    protected String codigo(String s, int sai) {
        String codigo = "";
        String sql = "select * from solicitud_vacaciones s where s.codigo_funcionario=" + sai + " ORDER by id_vacaciones asc";
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
                break;
            case "DUODESIMA":
                tipo = "SD";
                break;
            case "VFI":
                tipo = "SVFI";
                break;
        }
        codigo = tipo + "-" + sai + "-" + fecha.fechaParaCodigo();
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

    public JSONObject datosFuncionario(int sai) throws JSONException {
        JSONObject json = new JSONObject();
        List<Map<String, Object>> usuario = funcionario(sai);

        json.put("nombreFuncionario", usuario.get(0).get("nombre") + " " + usuario.get(0).get("apellido"));

        Date ingreso = new Date(usuario.get(0).get("fecha_ingreso").toString());
        json.put("nombre_cargo", usuario.get(0).get("nombre_cargo"));
        json.put("nombre_entidad", usuario.get(0).get("nombre_entidad"));
        json.put("fecha_ingreso", usuario.get(0).get("fecha_ingreso").toString());
        json.put("antiguedad", ingreso.antiguedad());
        json.put("correo", usuario.get(0).get("correo"));
        json.put("usuarios", usuario);
        json.put("nombre_corto", usuario.get(0).get("nombre").toString().substring(0, 1));

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
        EncriptadorAES encriptadorAES = new EncriptadorAES();
        Usuario usuario = null;
        try {
            String sqlUsuario = "SELECT id_cuenta, usuario, pass, cu.estado, codigo_funcionario, correo, supervisor, nombre, apellido  "
                    + "FROM public.cuenta cu, funcionario f "
                    + "where codigo_funcionario=codigo_sai and cu.usuario = '" + user + "'";
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
                    final String claveEncriptacion = "secreto!";
                    String pass = users.get(0).get("pass").toString();
                    String desEncriptado = encriptadorAES.desencriptar(pass, claveEncriptacion);
                    if (password.equals(desEncriptado)) {
                        int supervisor = Integer.parseInt(users.get(0).get("supervisor").toString());
                        List<Map<String, Object>> funcionario = funcionario(supervisor);
                        usuario = new Usuario(users.get(0).get("usuario").toString(),
                                users.get(0).get("pass").toString(),
                                Integer.parseInt(users.get(0).get("codigo_funcionario").toString()),
                                users.get(0).get("correo").toString(),
                                supervisor, funcionario.get(0).get("correo").toString(),
                                users.get(0).get("apellido") + " " + users.get(0).get("nombre")
                        );
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
                    int supervisor = Integer.parseInt(users.get(0).get("supervisor").toString());
                    List<Map<String, Object>> funcionario = funcionario(supervisor);
                    usuario = new Usuario(users.get(0).get("usuario").toString(),
                            users.get(0).get("pass").toString(),
                            Integer.parseInt(users.get(0).get("codigo_funcionario").toString()),
                            users.get(0).get("correo").toString(),
                            supervisor, funcionario.get(0).get("correo").toString(),
                            users.get(0).get("apellido") + " " + users.get(0).get("nombre")
                    );
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

    public String enviarCorreo(String remitente, String asunto, String detalle) throws IOException {

        String json = "{\"emisor\": \"" + EMISOR + "\","
                + " \"contrasenaEmisor\": \"" + CONTRASENA + "\","
                + " \"remitente\": \"" + remitente + "\","
                + " \"asunto\": \"" + asunto + "\","
                + " \"detalle\": \"" + detalle + "\"}";
        String mensaje = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(ENDPOINTCORREO);

        // Configura la entidad del JSON
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        //StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);

        // Envía la solicitud HTTP POST
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {

            // Procesa la respuesta
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);
            // Haz algo con la respuesta del servidor
            mensaje = "Correo enviado";
        } catch (Exception e) {
            mensaje = "Correo no enviado, </br>" + e.getMessage();
        } finally {
            // Cierra la respuesta y el cliente HTTP
            response.close();
            httpClient.close();
        }
        return mensaje;
    }
}
