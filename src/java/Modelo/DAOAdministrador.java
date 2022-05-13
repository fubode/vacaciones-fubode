/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Helper.Calendario;
import Helper.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class DAOAdministrador extends Conexion {

    public DAOAdministrador() {

    }

    public DAOAdministrador(Usuario usuario) {
        super(usuario);
    }

    public List<Map<String, Object>> listaSolicitudes() {
        String sql;
        List<Map<String, Object>> listaSolicitudes = null;
        sql = "select s.codigo_solicitud,s.fecha_solicitud, s.descripcion_estado, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias, "
                + "f.apellido,f.nombre,ca.nombre_cargo,s.tipo,s.estado,s.detalle_compensacion, s.supervisor, e.nombre_entidad "
                + "from solicitud_vacaciones s, funcionario f, cargo ca , entidad e "
                + "where s.codigo_funcionario=f.codigo_sai and ca.codigo_cargo=f.cargo and e.codigo_entidad=f.entidad";
        listaSolicitudes = this.ejecutarConsulta(sql);

        for (int i = 0; i < listaSolicitudes.size(); i++) {
            Map<String, Object> usu = listaSolicitudes.get(i);
            String codigo = usu.get("supervisor").toString();
            if (!codigo.equals("0")) {
                String nombre = nombreUsuario(codigo);
                usu.replace("supervisor", nombre);
                listaSolicitudes.set(i, usu);
            }
        }
        return listaSolicitudes;
    }

    public List<Map<String, Object>> listaCargos() {
        String sql;

        sql = "SELECT codigo_cargo, nombre_cargo "
                + "FROM public.cargo";
        return this.ejecutarConsulta(sql);
    }

    public void registrarCargo(String codigo, String nombre, String tipo) {
        switch (tipo) {
            case "registrar":
                String sql = "INSERT INTO public.cargo("
                        + "codigo_cargo, nombre_cargo) "
                        + "VALUES (?, ?)";
                this.jdbcTemplate.update(sql,
                        Integer.parseInt(codigo),
                        nombre);
                break;
            case "editar":
                String sql1 = "UPDATE public.cargo "
                        + "SET nombre_cargo='" + nombre + "' "
                        + "WHERE codigo_cargo=" + Integer.parseInt(codigo);
                this.actualizarConsulta(sql1);
                break;
        }
    }

    public void elimarCargo(String codigo) throws Exception {
        String sql = "DELETE FROM public.cargo"
                + " WHERE codigo_cargo = " + codigo;
        try {
            this.jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Map<String, Object>> listaEntidades() {
        String sql;

        sql = "SELECT codigo_entidad, nombre_entidad, tipo_entidad, entidad_supervisor "
                + "FROM public.entidad";
        return this.ejecutarConsulta(sql);
    }

    public String nombreEntidad(int entidadSupervisor) {
        String nombreEntidad = "";
        try {
            String sql = "SELECT codigo_entidad, nombre_entidad, tipo_entidad, entidad_supervisor "
                    + "FROM public.entidad WHERE codigo_entidad=" + entidadSupervisor;
            List<Map<String, Object>> entidad;
            entidad = this.jdbcTemplate.queryForList(sql);
            nombreEntidad = entidad.get(0).get("nombre_entidad").toString();

        } catch (Exception e) {
            nombreEntidad = "NINGUNO";
        }
        return nombreEntidad;
    }

    public void registrarEntidad(String codigo, String nombre, String entidad, String tipo, String tipo_accion) {
        switch (tipo_accion) {
            case "registrar":
                String sql = "INSERT INTO public.entidad( "
                        + "codigo_entidad, nombre_entidad, tipo_entidad, entidad_supervisor) "
                        + "VALUES (?, ?, ?, ?)";
                this.jdbcTemplate.update(sql,
                        Integer.parseInt(codigo),
                        nombre,
                        tipo,
                        Integer.parseInt(entidad)
                );
                break;
            case "editar":
                String sql12 = "UPDATE public.entidad "
                        + "SET  nombre_entidad='" + nombre + "'"
                        + ", tipo_entidad='" + tipo + "'"
                        + ", entidad_supervisor=" + Integer.parseInt(entidad)
                        + " WHERE codigo_entidad=" + Integer.parseInt(codigo);
                this.actualizarConsulta(sql12);
                break;
        }
    }

    public void eliminarEntidad(String codigo) {
        String sql = "DELETE FROM public.entidad"
                + " WHERE codigo_entidad = " + codigo;
        try {
            this.jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Map<String, Object>> listaFuncionaios() {
        String sql;

        sql = "SELECT codigo_sai, apellido, nombre, fecha_ingreso, ci, correo, fecha_salida, supervisor, ca.nombre_cargo, e.nombre_entidad, f.estado "
                + "FROM funcionario f, entidad e, cargo ca "
                + "where f.entidad=e.codigo_entidad and f.cargo=ca.codigo_cargo";
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

    public Object listaFuncionario() {
        String sql;

        sql = "select codigo_sai,apellido, nombre from funcionario";
        return this.ejecutarConsulta(sql);
    }

    public void registrarFuncionario(String tipo, int codigoSai, String apellidos, String nombre, Date ingreso, String ci, String correo, int entidad, int cargo, int supervisor) {
        try {
            switch (tipo) {
                case "editar":
                    String sql12 = "UPDATE public.funcionario "
                            + "SET"
                            + " apellido='" + apellidos + "'"
                            + " , nombre='" + nombre + "'"
                            + ", fecha_ingreso='" + ingreso + "'"
                            + ", ci='" + ci + "'"
                            + ", correo='" + correo + "'"
                            + ", supervisor=" + supervisor
                            + ", entidad=" + entidad
                            + ", cargo=" + cargo
                            + "WHERE codigo_sai=" + codigoSai;
                    this.actualizarConsulta(sql12);
                    break;
                case "registrar":
                    String estado = "ACTIVO";
                    String sql = "INSERT INTO public.funcionario("
                            + "codigo_sai, apellido, nombre, fecha_ingreso, ci, correo, fecha_salida, supervisor, entidad, cargo, estado) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    this.jdbcTemplate.update(sql,
                            codigoSai,
                            apellidos,
                            nombre,
                            ingreso,
                            ci,
                            correo,
                            new Date("1750-01-01"),
                            supervisor,
                            entidad,
                            cargo,
                            estado
                    );
                    

                    break;
            }

        } catch (Exception e) {
            String mensaje = e.getMessage();
            System.err.println(mensaje);
        }
    }

    public void anularSolicitud(String codigoSai) {
        String sql = "UPDATE public.funcionario "
                + "SET estado='INACTIVO'"
                + ", fecha_salida=(select current_date) "
                + "WHERE codigo_sai=" + codigoSai;
        this.actualizarConsulta(sql);
    }

    public void modificacionSolicitud(String codigo, String estado, String detalle) {
        String sql = "UPDATE public.solicitud_vacaciones "
                + "SET descripcion_estado='" + detalle + "', estado='" + estado + "', fecha_estado=(select current_date),supervisor=" + codigo_say + " "
                + "WHERE codigo_solicitud='" + codigo + "'";
        this.actualizarConsulta(sql);
    }

    public List<List<Map<String, String>>> listaActividadesEntidad(int gestion, int mes) {
        String d = "sas";
        Calendario calendario = new Calendario(gestion, mes);
        List<List<Map<String, String>>> listas = calendario.dias_calendario();
        List<Map<String, Object>> list;
        String fecha_inicial = gestion + "-" + mes + "-" + "1";
        String fecha_final = gestion + "-" + mes + "-" + String.valueOf(calendario.diasMes(mes));

        String sql = "select fecha,descripcion_estado,tipo,entidad FROM fechas WHERE fecha>= '" + fecha_inicial + "' and fecha<='" + fecha_final + "'";
        list = this.jdbcTemplate.queryForList(sql);
        for (int i = 0; i < list.size(); i++) {
            String fecha = list.get(i).get("fecha").toString();
            String[] f = fecha.split("-");
            Date date = new Date(Integer.parseInt(f[0]),
                    Integer.parseInt(f[1]),
                    Integer.parseInt(f[2]));
            fecha = date.toString();
            for (int j = 0; j < listas.size(); j++) {
                List<Map<String, String>> semana = listas.get(j);
                for (int k = 0; k < semana.size(); k++) {
                    String fechaComparacion = semana.get(k).get("fecha");

                    if (fecha.equals(fechaComparacion)) {
                        listas.get(j).get(k).replace("descripcion", list.get(i).get("descripcion_estado").toString());
                        listas.get(j).get(k).replace("tipo", list.get(i).get("tipo").toString());
                        int actividades = actividades(fechaComparacion, list);
                        listas.get(j).get(k).replace("actividades", String.valueOf(actividades));
                    }
                }
            }
        }
        return listas;
    }

    private int actividades(String fechaComparacion, List<Map<String, Object>> list) {
        int actividades = -1;
        for (Map<String, Object> e : list) {
            String fecha = e.get("fecha").toString();
            if (fechaComparacion.equals(fecha)) {
                actividades++;
            }
        }

        return (actividades > 0) ? 1 : 0;
    }

    public JSONObject solicitud(String codigo) {
        JSONObject json = new JSONObject();
        List<Map<String, Object>> list = null;
        try {
            String sql = "SELECT codigo_solicitud, fecha_solicitud, fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle_compensacion, estado, fecha_estado, descripcion_estado, codigo_funcionario, id_vacaciones, supervisor "
                    + "	FROM public.solicitud_vacaciones "
                    + "	where codigo_solicitud='" + codigo + "'";
            list = this.jdbcTemplate.queryForList(sql);
            for (Map<String, Object> solicitud : list) {
                json.put("codigo_solicitud", solicitud.get("codigo_solicitud"));
                json.put("fecha_solicitud", solicitud.get("fecha_solicitud"));
                json.put("fecha_salida", solicitud.get("fecha_salida"));
                json.put("turno_salida", solicitud.get("turno_salida"));
                json.put("fecha_retorno", solicitud.get("fecha_retorno"));
                json.put("turno_retorno", solicitud.get("turno_retorno"));
                json.put("dias", solicitud.get("dias"));
                json.put("tipo", solicitud.get("tipo"));
                json.put("detalle_compensacion", solicitud.get("detalle_compensacion"));
                json.put("estado", solicitud.get("estado"));
                json.put("fecha_estado", solicitud.get("fecha_estado"));
                json.put("descripcion_estado", solicitud.get("descripcion_estado"));
                json.put("codigo_funcionario", solicitud.get("codigo_funcionario"));
                json.put("supervisor", solicitud.get("supervisor"));
            }
        } catch (Exception e) {
            String mesaje = e.getMessage();
        }
        return json;
    }

    public double diasNoLaborables(String fecha_salida, String fecha_retorno, String diferencia, String turno_salida, String turno_retorno, String codigo) {
        double diasNoLaborables = 0;
        try {
            Date salida = new Date(fecha_salida);
            Date retorno = new Date(fecha_retorno);
            String sqlSay = "SELECT codigo_funcionario "
                    + "FROM public.solicitud_vacaciones "
                    + "WHERE codigo_solicitud='" + codigo + "'";
            int say = Integer.parseInt(this.jdbcTemplate.queryForList(sqlSay).get(0).get("codigo_funcionario").toString());
            String listaEntidades = "select fecha,tipo,entidad from fechas where entidad = 0 and fecha>= '" + fecha_salida + "'  and fecha<= '" + fecha_retorno + "' order by fecha";
            String listaEntidad = "select fecha,tipo,e.nombre_entidad from fechas f, entidad e, funcionario fun where "
                    + "fecha>= '" + fecha_salida + "'  and fecha<= '" + fecha_retorno + "' "
                    + "and  fun.entidad=e.codigo_entidad "
                    + "and e.codigo_entidad=f.entidad "
                    + "and fun.codigo_sai=" + say + " "
                    + "order by fecha";
            List<Map<String, Object>> entidades = this.jdbcTemplate.queryForList(listaEntidades);
            List<Map<String, Object>> entidad = this.jdbcTemplate.queryForList(listaEntidad);
            if (esFeriado(fecha_salida, fecha_retorno, entidad, entidades)) {
                diasNoLaborables = 10000;
            } else {
                for (Map<String, Object> map : entidad) {
                    String tipo = map.get("tipo").toString();
                    switch (tipo) {
                        case "SABADO":
                            diasNoLaborables += 0.5;
                            break;
                        case "NO_LABORAL":
                            diasNoLaborables += 1;
                            break;
                        case "TARDE":
                            diasNoLaborables += 0.5;
                            break;
                        case "MANANA":
                            diasNoLaborables += 0.5;
                            break;
                    }
                }
                for (Map<String, Object> e : entidades) {
                    String tipo = e.get("tipo").toString();
                    switch (tipo) {
                        case "SABADO":
                            diasNoLaborables += 0.5;
                            break;
                        case "NO_LABORAL":
                            diasNoLaborables += 1;
                            break;
                        case "TARDE":
                            diasNoLaborables += 0.5;
                            break;
                        case "MANANA":
                            diasNoLaborables += 0.5;
                            break;
                    }
                }
                double descuentoTurno = 0;
                if (turno_salida.equals("MAÑANA") && turno_retorno.equals("TARDE")) {
                    descuentoTurno += 0.5;
                } else {
                    if (turno_salida.equals("TARDE") && turno_retorno.equals("MAÑANA")) {
                        descuentoTurno -= 0.5;
                    }
                }
                diasNoLaborables = Double.parseDouble(diferencia) - diasNoLaborables + descuentoTurno;
            }
        } catch (Exception e) {

        }
        if (diasNoLaborables < 0) {
            diasNoLaborables = 0;
        }
        return diasNoLaborables;
    }

    private boolean esFeriado(String fecha_salida, String fecha_retorno, List<Map<String, Object>> entidad, List<Map<String, Object>> entidades) {
        boolean esFeriado = false;
        for (Map<String, Object> e : entidades) {
            if (e.get("fecha").toString().equals(fecha_retorno) || e.get("fecha").toString().equals(fecha_salida)) {
                esFeriado = true;
                break;
            }
        }
        for (Map<String, Object> e : entidad) {
            if (e.get("fecha").toString().equals(fecha_retorno) || e.get("fecha").toString().equals(fecha_salida)) {
                esFeriado = true;
                break;
            }
        }
        return esFeriado;
    }

    public JSONObject obtenerCargo(int codigo) throws JSONException {
        JSONObject json = new JSONObject();
        String sql = "SELECT codigo_cargo, nombre_cargo "
                + "FROM public.cargo "
                + "where codigo_cargo = " + codigo;
        List<Map<String, Object>> cargo = this.jdbcTemplate.queryForList(sql);
        json.put("codigo_cargo", cargo.get(0).get("codigo_cargo").toString());
        json.put("nombre_cargo", cargo.get(0).get("nombre_cargo").toString());
        return json;
    }

    public JSONObject obtenerEntidad(int codigo) throws JSONException {
        JSONObject json = new JSONObject();
        String sql = "SELECT codigo_entidad, nombre_entidad, tipo_entidad, entidad_supervisor "
                + "FROM public.entidad "
                + "WHERE codigo_entidad=" + codigo;
        List<Map<String, Object>> cargo = this.jdbcTemplate.queryForList(sql);
        json.put("codigo_entidad", cargo.get(0).get("codigo_entidad").toString());
        json.put("nombre_entidad", cargo.get(0).get("nombre_entidad").toString());
        json.put("tipo_entidad", cargo.get(0).get("tipo_entidad").toString());
        json.put("entidad_supervisor", cargo.get(0).get("entidad_supervisor").toString());

        return json;
    }

    public JSONObject obtenerFuncionario(int codigo) throws JSONException {
        JSONObject json = new JSONObject();
        String sql = "SELECT codigo_sai, apellido, nombre, fecha_ingreso, ci, correo, fecha_salida, supervisor, entidad, cargo, estado,expedido "
                + "FROM public.funcionario "
                + "WHERE codigo_sai=" + codigo;
        List<Map<String, Object>> cargo = this.jdbcTemplate.queryForList(sql);
        json.put("codigo_sai", cargo.get(0).get("codigo_sai").toString());
        json.put("apellido", cargo.get(0).get("apellido").toString());
        json.put("nombre", cargo.get(0).get("nombre").toString());
        json.put("fecha_ingreso", cargo.get(0).get("fecha_ingreso").toString());
        json.put("ci", cargo.get(0).get("ci").toString());
        json.put("correo", cargo.get(0).get("correo").toString());
        json.put("supervisor", cargo.get(0).get("supervisor").toString());
        json.put("entidad", cargo.get(0).get("entidad").toString());
        json.put("cargo", cargo.get(0).get("cargo").toString());
        json.put("expedido", cargo.get(0).get("expedido").toString());
        return json;
    }

    public void modificarSolicitud(String codigo, String fecha_solicitud, String fecha_salida, String turno_salida, String fecha_retorno, String turno_retorno, String dias, String tipo, String detalle_compensacion, String detalle_estado, String estado) {
        String sql = "SELECT codigo_solicitud, fecha_solicitud, fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle_compensacion, estado, fecha_estado, descripcion_estado, codigo_funcionario, id_vacaciones, supervisor "
                + "FROM public.solicitud_vacaciones "
                + "WHERE codigo_solicitud='" + codigo + "'";

        List<Map<String, Object>> solicitud = this.jdbcTemplate.queryForList(sql);
        String sqlInsertar = "INSERT INTO public.solicitudes_modificadas( "
                + "codigo_solicitud, fecha_solicitud, fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle_compensacion, estado, fecha_estado, descripcion_estado, codigo_funcionario, supervisor, modificador) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sqlInsertar,
                solicitud.get(0).get("codigo_solicitud").toString(),
                new Date(solicitud.get(0).get("fecha_solicitud").toString()),
                new Date(solicitud.get(0).get("fecha_salida").toString()),
                solicitud.get(0).get("turno_salida").toString(),
                new Date(solicitud.get(0).get("fecha_retorno").toString()),
                solicitud.get(0).get("turno_retorno").toString(),
                Double.parseDouble(solicitud.get(0).get("dias").toString()),
                solicitud.get(0).get("tipo").toString(),
                solicitud.get(0).get("detalle_compensacion").toString(),
                solicitud.get(0).get("estado").toString(),
                new Date(solicitud.get(0).get("fecha_estado").toString()),
                solicitud.get(0).get("descripcion_estado").toString(),
                Integer.parseInt(solicitud.get(0).get("codigo_funcionario").toString()),
                Integer.parseInt(solicitud.get(0).get("supervisor").toString()),
                codigo_say
        );

        String sqlModificar = "UPDATE public.solicitud_vacaciones "
                + " SET fecha_solicitud='" + new Date(fecha_solicitud) + "'"
                + ", fecha_salida='" + new Date(fecha_salida) + "'"
                + ", turno_salida='" + turno_salida + "'"
                + ", fecha_retorno='" + new Date(fecha_retorno) + "'"
                + ", turno_retorno='" + turno_retorno + "'"
                + ", dias=" + Double.parseDouble(dias)
                + ", tipo='" + tipo + "'"
                + ", estado='" + estado + "'";

        String compensacion = "";
        String estadoS = "";
        if (tipo.equals("COMPENSACION") || tipo.equals("ASUELTO")) {
            compensacion = ", detalle_compensacion='" + detalle_compensacion + "'";
        }
        if (estado.equals("RECHAZADO")) {
            estadoS = ", descripcion_estado='" + detalle_estado + "'";
        }
        String s = sqlModificar
                + compensacion
                + estadoS
                + " WHERE codigo_solicitud='" + codigo + "'";
        this.actualizarConsulta(s);
    }

    public JSONObject funcionarioRepetido(int codigo, String correo, String ci) throws JSONException {
        JSONObject json = new JSONObject();
        String sql = "SELECT codigo_sai, ci, correo "
                + "FROM public.funcionario "
                + " where codigo_sai=" + codigo
                + " or ci = '" + ci + "'"
                + " or correo = '" + correo + "'";
        List<Map<String, Object>> funcionarioRepetido = this.jdbcTemplate.queryForList(sql);
        if (funcionarioRepetido.size() == 0) {
            json.put("estado", "correcto");
        } else {
            String r_ci = funcionarioRepetido.get(0).get("ci").toString();
            String r_correo = funcionarioRepetido.get(0).get("correo").toString();
            int r_codigo = Integer.parseInt(funcionarioRepetido.get(0).get("codigo_sai").toString());
            if (codigo == r_codigo) {
                json.put("estado", "codigo");
            } else {
                if (r_ci.equals(ci)) {
                    json.put("estado", "ci");
                } else {
                    if (r_correo.equals(correo)) {
                        json.put("estado", "correo");
                    }
                }
            }
        }
        return json;
    }

    public JSONObject crudFuncionario(String tipo, String codigoSai, String apellidos, String nombre, String ingreso, String ci, String correo, String entidad, String cargo, String superviso, String solicutud, String estado, String expedido) throws JSONException {
        JSONObject json = new JSONObject();
        String mensaje = "";
        switch (tipo) {
            case "registrar":
                json = registrar(codigoSai, apellidos, nombre, ingreso, ci, correo, entidad, cargo, superviso, solicutud, estado, expedido);
                break;
            case "editar":
                json = editar(codigoSai, apellidos, nombre, ingreso, ci, correo, entidad, cargo, superviso, solicutud, estado, expedido);
                break;
        }
        return json;

    }

    private JSONObject registrar(String codigoSai, String apellidos, String nombre, String ingreso, String ci, String correo, String entidad, String cargo, String superviso, String solicutud, String estado, String expedido) throws JSONException {
        JSONObject json = new JSONObject();
        String mensaje = "";
        boolean solicitud = false;
        try {
            int sai = Integer.parseInt(codigoSai);
            Date f_ingreso = new Date(ingreso);
            int supervisor = Integer.parseInt(superviso);
            int carnet = Integer.parseInt(ci);
            String validacionSai = validacionSai(sai, ci, correo);
            if (validacionSai.equals("correcto")) {
                if (estado.equals("ACEPTAR")) {
                    String sql = "INSERT INTO public.funcionario("
                            + "codigo_sai, apellido, nombre, fecha_ingreso, ci, correo, fecha_salida, supervisor, entidad, cargo,estado,expedido) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
                    this.jdbcTemplate.update(sql,
                            sai,
                            apellidos,
                            nombre,
                            f_ingreso,
                            carnet,
                            correo,
                            new Date("1750-01-01"),
                            supervisor,
                            Integer.parseInt(entidad),
                            Integer.parseInt(cargo),
                            "ACTIVO",
                            expedido
                    );
                    String sqlCargo = "INSERT INTO public.cuenta("
                            + "usuario, pass, codigo_funcionario) "
                            + "VALUES (?, ?, ?)";
                    this.jdbcTemplate.update(sqlCargo,
                            correo,
                            String.valueOf(ci),
                            sai
                    );
                    mensaje = "registro insertado correctamente";
                } else {
                    solicitud = true;
                }
            } else {
                mensaje = validacionSai;
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        json.put("solicutud", solicitud);
        json.put("mensaje", mensaje);
        return json;
    }

    private String validacionSai(int sai, String ci, String correo) throws JSONException {
        String validacionSai = "";
        try {
            String validadorCorreo = "^[A-Za-z0-9+_.-]+@fubode.org";
            Pattern pattern = null;
            Matcher matcher = null;
            if (sai < 1000) {
                validacionSai = "El codigo say debe contener mas de 4 numeros";
            } else {
                pattern = Pattern.compile(validadorCorreo);
                matcher = pattern.matcher(correo);
                if (!matcher.matches()) {
                    validacionSai = "El correo electronico debe contener la extencion '@fubode.org'";
                } else {
                    JSONObject funcionario = funcionarioRepetido(sai, correo, ci);
                    String stado = funcionario.get("estado").toString();
                    if (stado.equals("correcto")) {
                        validacionSai = "correcto";
                    } else {
                        switch (stado) {
                            case "ci":
                                validacionSai = "El ci ya fue registrado";
                                break;
                            case "codigo":
                                validacionSai = "El codigo say ya fue registrado";
                                break;
                            case "correo":
                                validacionSai = "El correo ya fue registrado";
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            validacionSai = e.getMessage();
        }
        return validacionSai;
    }

    private JSONObject editar(String codigoSai, String apellidos, String nombre, String ingreso, String ci, String correo, String entidad, String cargo, String superviso, String solicutud, String estado, String expedido) throws JSONException {
        String validacionSai = "";
        JSONObject json = new JSONObject();
        boolean solicitud = false;
        try {
            int supervisor = Integer.parseInt(superviso);
            int say = Integer.parseInt(codigoSai);
            Date f_ingreso = new Date(ingreso);
            String validadorCorreo = "^[A-Za-z0-9+_.-]+@fubode.org";
            Pattern pattern = null;
            Matcher matcher = null;
            if (apellidos.length() == 0) {
                validacionSai = "El debe ingresar un apellido valido";
            } else {
                if (nombre.length() == 0) {
                    validacionSai = "El debe ingresar un nombre valido";
                } else {
                    if (ingreso.length() == 0) {
                        validacionSai = "El debe ingresar una fecha valida";
                    } else {
                        if (ci.length() == 0) {
                            validacionSai = "El debe ingresar un ci valido";
                        } else {
                            pattern = Pattern.compile(validadorCorreo);
                            matcher = pattern.matcher(correo);
                            if (!matcher.matches()) {
                                validacionSai = "El correo electronico debe contener la extencion '@fubode.org'";
                            } else {
                                if (estado.equals("ACEPTAR")) {
                                    String sql12 = "UPDATE public.funcionario "
                                            + "SET"
                                            + " apellido='" + apellidos + "'"
                                            + " , nombre='" + nombre + "'"
                                            + ", fecha_ingreso='" + f_ingreso + "'"
                                            + ", ci='" + ci + "'"
                                            + ", correo='" + correo + "'"
                                            + ", supervisor=" + supervisor
                                            + ", entidad=" + Integer.parseInt(entidad)
                                            + ", cargo=" + Integer.parseInt(cargo)
                                            + ", expedido='" + expedido + "'"
                                            + "WHERE codigo_sai=" + say;
                                    this.actualizarConsulta(sql12);
                                } else {
                                    solicitud = true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            validacionSai = e.getMessage();
        }
        json.put("solicutud", solicitud);
        json.put("mensaje", validacionSai);
        return json;
    }

    public JSONObject entidadCrud(String nombre, String codigo, String entidadSupervisora, String tipoEntidad, String tipo_accion, String estado) {
        JSONObject json = new JSONObject();
        int codigoEntidad = 0;
        try {
            try {
                codigoEntidad = Integer.parseInt(codigo);
            } catch (Exception e) {

            }
            int codigoSupervisor = Integer.parseInt(entidadSupervisora);
            switch (tipo_accion) {
                case "registrar":
                    json = registrarEntidad(nombre, codigoEntidad, codigoSupervisor, tipoEntidad, tipo_accion, estado);
                    break;
                case "editar":
                    json = editarEntidad(nombre, codigoEntidad, codigoSupervisor, tipoEntidad, tipo_accion, estado);
                    break;
            }

        } catch (Exception e) {

        }
        return json;
    }

    private JSONObject registrarEntidad(String nombre, int codigoEntidad, int codigoSupervisor, String tipo, String tipo_accion, String estado) throws JSONException {
        JSONObject json = new JSONObject();
        String mensaje = "";
        boolean solicitud = false;
        try {
            if (nombre.length() == 0) {
                mensaje = "Debe ingresar un nombre para la entidad";
            } else {
                String mensajeCodigo = codigoEntidad(codigoEntidad);
                if (!mensajeCodigo.equals("correcto")) {
                    mensaje = mensajeCodigo;
                } else {
                    if (estado.equals("ACEPTAR")) {
                        String sql = "INSERT INTO public.entidad( "
                                + "codigo_entidad, nombre_entidad, tipo_entidad, entidad_supervisor) "
                                + "VALUES (?, ?, ?, ?)";
                        this.jdbcTemplate.update(sql,
                                codigoEntidad,
                                nombre,
                                tipo,
                                codigoSupervisor
                        );
                        mensaje = "La entidad fue registrada correctamente";
                    } else {
                        solicitud = true;
                    }
                }
            }

        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        json.put("solicitud", solicitud);
        json.put("mensaje", mensaje);
        return json;
    }

    private JSONObject editarEntidad(String nombre, int codigoEntidad, int codigoSupervisor, String tipo, String tipo_accion, String estado) throws JSONException {
        JSONObject json = new JSONObject();
        String mensaje = "";
        boolean solicitud = false;
        try {
            if (nombre.length() == 0) {
                mensaje = "Debe ingresar un nombre para la entidad";
            } else {
                if (estado.equals("ACEPTAR")) {
                    String sql12 = "UPDATE public.entidad "
                            + "SET  nombre_entidad='" + nombre + "'"
                            + ", tipo_entidad='" + tipo + "'"
                            + ", entidad_supervisor=" + codigoSupervisor
                            + " WHERE codigo_entidad=" + codigoEntidad;
                    this.actualizarConsulta(sql12);
                    mensaje = "La entidad fue modificada correctamente correctamente";
                } else {
                    solicitud = true;
                }
            }

        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        json.put("solicitud", solicitud);
        json.put("mensaje", mensaje);
        return json;
    }

    private String codigoEntidad(int codigoEntidad) {
        String codigo = "";
        if (codigoEntidad == 0) {
            codigo = "Debe ingresar un codigo para esta entidad";
        } else {
            String sql = "SELECT codigo_entidad, nombre_entidad, tipo_entidad, entidad_supervisor "
                    + "FROM public.entidad where codigo_entidad=" + codigoEntidad;
            List<Map<String, Object>> entidades = this.jdbcTemplate.queryForList(sql);
            if (entidades.size() != 0) {
                codigo = "El codigo que ingreso ya fue registrado anteriormente";
            } else {
                codigo = "correcto";
            }
        }

        return codigo;
    }

    public JSONObject reporteFuncionario(int codigoSay, String tipo, String estado, Date inicio, Date fin) {
        JSONObject json = new JSONObject();
        String sql = "SELECT codigo_solicitud, fecha_solicitud, fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle_compensacion, estado, fecha_estado, descripcion_estado, codigo_funcionario, nombre_supervisor "
                + "FROM public.solicitud_vacaciones "
                + "where codigo_funcionario = " + codigoSay;
        String condiciones = "";
        String condicionesFechas = " and fecha_solicitud BETWEEN '" + inicio + "' and '" + fin + "'";
        if (codigoSay != 0) {

        } else {
            if (!tipo.equals("TODOS")) {
                condiciones = condiciones + " and tipo = '" + tipo + "'";
            }
            if (!estado.equals("TODOS")) {
                condiciones = condiciones + " and tipo = '" + estado + "'";
            }
            sql = sql + condiciones + condicionesFechas;
        }
        Map<String, Object> funcionario = null;
        List<Map<String, Object>> listaFuncionaios = listaFuncionaios();
        for (int i = 0; i < listaFuncionaios.size(); i++) {
            Map<String, Object> f = listaFuncionaios.get(i);
            int codigo = Integer.parseInt(f.get("codigo_sai").toString());
            if (codigo == codigoSay) {
                funcionario = f;
                break;
            }
        }
        return json;
    }

    public List<Map<String, Object>> listaSolicitudes(int funcionario, String tipo, String estado, Date desde, Date hasta) {
        List<Map<String, Object>> listaSolicitudes = null;
        String sql = "SELECT codigo_solicitud, fecha_solicitud, fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, detalle_compensacion, estado, fecha_estado, descripcion_estado, codigo_funcionario, supervisor "
                + "FROM public.solicitud_vacaciones "
                + "where codigo_funcionario = " + funcionario;
        String condiciones = "";
        String condicionesFechas = " and fecha_solicitud BETWEEN '" + desde + "' and '" + hasta + "'";

        if (funcionario == 0) {
            if (!tipo.equals("TODOS")) {
                condiciones = condiciones + " and tipo = '" + tipo + "'";
            }
            if (!estado.equals("TODOS")) {
                condiciones = condiciones + " and s.estado = '" + estado + "'";
            }
            sql = "SELECT f.codigo_sai, f.nombre,f.apellido,codigo_solicitud, fecha_solicitud, s.fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, tipo, s.estado, fecha_estado, s.supervisor "
                    + "FROM public.solicitud_vacaciones s, public.funcionario f "
                    + "where s.codigo_funcionario=f.codigo_sai ";
            sql = sql + condiciones + condicionesFechas;

            listaSolicitudes = this.jdbcTemplate.queryForList(sql);
            for (int i = 0; i < listaSolicitudes.size(); i++) {
                Map<String, Object> usu = listaSolicitudes.get(i);
                String codigo = usu.get("supervisor").toString();
                if (!codigo.equals("0")) {
                    String nombre = nombreUsuario(codigo);
                    usu.replace("supervisor", nombre);
                    listaSolicitudes.set(i, usu);
                } else {
                    String ninguno = "NINGUNO";
                    usu.replace("supervisor", ninguno);
                    listaSolicitudes.set(i, usu);
                }
                Date fecha_solicitud = new Date(usu.get("fecha_solicitud").toString());
                usu.replace("fecha_solicitud", fecha_solicitud.fechaImpresion());
                listaSolicitudes.set(i, usu);
                
                Date fecha_salida = new Date(usu.get("fecha_salida").toString());
                usu.replace("fecha_salida", fecha_salida.fechaImpresion());
                listaSolicitudes.set(i, usu);
                
                Date fecha_retorno = new Date(usu.get("fecha_retorno").toString());
                usu.replace("fecha_retorno", fecha_retorno.fechaImpresion());
                listaSolicitudes.set(i, usu);
                
                Date fecha_estado = new Date(usu.get("fecha_estado").toString());
                usu.replace("fecha_estado", fecha_estado.fechaImpresion());
                listaSolicitudes.set(i, usu);
            }
        } else {
            if (!tipo.equals("TODOS")) {
                condiciones = condiciones + " and tipo = '" + tipo + "'";
            }
            if (!estado.equals("TODOS")) {
                condiciones = condiciones + " and estado = '" + estado + "'";
            }
            sql = sql + condiciones + condicionesFechas;
            listaSolicitudes = this.jdbcTemplate.queryForList(sql);
            for (int i = 0; i < listaSolicitudes.size(); i++) {
                Map<String, Object> usu = listaSolicitudes.get(i);
                String codigo = usu.get("supervisor").toString();
                if (!codigo.equals("0")) {
                    String nombre = nombreUsuario(codigo);
                    usu.replace("supervisor", nombre);
                    listaSolicitudes.set(i, usu);
                } else {
                    String ninguno = "NINGUNO";
                    usu.replace("supervisor", ninguno);
                    listaSolicitudes.set(i, usu);
                }
                Date fecha_solicitud = new Date(usu.get("fecha_solicitud").toString());
                usu.replace("fecha_solicitud", fecha_solicitud.fechaImpresion());
                listaSolicitudes.set(i, usu);
                
                Date fecha_salida = new Date(usu.get("fecha_salida").toString());
                usu.replace("fecha_salida", fecha_salida.fechaImpresion());
                listaSolicitudes.set(i, usu);
                
                Date fecha_retorno = new Date(usu.get("fecha_retorno").toString());
                usu.replace("fecha_retorno", fecha_retorno.fechaImpresion());
                listaSolicitudes.set(i, usu);
                
                Date fecha_estado = new Date(usu.get("fecha_estado").toString());
                usu.replace("fecha_estado", fecha_estado.fechaImpresion());
                listaSolicitudes.set(i, usu);
                
            }
        }

        return listaSolicitudes;
    }

    public List<Map<String, Object>> listaSolicitudesPorCargo(int cargos, String tipo, String estado, Date desde, Date hasta) {
        List<Map<String, Object>> listaSolicitudes = null;
        String sql = "SELECT f.nombre,f.apellido,ca.nombre_cargo,ca.codigo_cargo ,codigo_solicitud, fecha_solicitud, s.fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, s.tipo, s.estado, s.codigo_funcionario, s.supervisor "
                + "FROM public.solicitud_vacaciones s, public.funcionario f, public.cargo ca "
                + "where f.codigo_sai=s.codigo_funcionario and f.cargo=ca.codigo_cargo ";
        String condiciones = "";
        String condicionesFechas = " and fecha_solicitud BETWEEN '" + desde + "' and '" + hasta + "'";
        if (!tipo.equals("TODOS")) {
            condiciones = condiciones + " and s.tipo = '" + tipo + "'";
        }
        if (!estado.equals("TODOS")) {
            condiciones = condiciones + " and s.estado = '" + estado + "'";
        }

        if (cargos == 0) {
            sql = sql + condiciones + condicionesFechas;

            listaSolicitudes = this.jdbcTemplate.queryForList(sql);
        } else {
            String condicionCargo = " and ca.codigo_cargo=" + cargos;
            sql = sql + condicionCargo + condiciones + condicionesFechas;
            listaSolicitudes = this.jdbcTemplate.queryForList(sql);
        }
        for (int i = 0; i < listaSolicitudes.size(); i++) {
            Map<String, Object> usu = listaSolicitudes.get(i);
            String codigo = usu.get("supervisor").toString();
            if (!codigo.equals("0")) {
                String nombre = nombreUsuario(codigo);
                usu.replace("supervisor", nombre);
                listaSolicitudes.set(i, usu);
            } else {
                String ninguno = "NINGUNO";
                usu.replace("supervisor", ninguno);
                listaSolicitudes.set(i, usu);
            }
        }

        return listaSolicitudes;
    }

    public List<Map<String, Object>> listaSolicitudesPorEntidad(int entidad, int cargos, String tipo, String estado, Date desde, Date hasta) {
        List<Map<String, Object>> listaSolicitudes = null;
        String sql = "SELECT e.nombre_entidad, f.nombre,f.apellido,ca.nombre_cargo,ca.codigo_cargo ,codigo_solicitud, fecha_solicitud, s.fecha_salida, turno_salida, fecha_retorno, turno_retorno, dias, s.tipo, s.estado, s.codigo_funcionario, s.supervisor "
                + "FROM public.solicitud_vacaciones s, public.funcionario f, public.cargo ca, public.entidad e "
                + "where f.codigo_sai=s.codigo_funcionario and f.cargo=ca.codigo_cargo and e.codigo_entidad=f.entidad ";
        String condiciones = "";
        String condicionesFechas = " and fecha_solicitud BETWEEN '" + desde + "' and '" + hasta + "'";
        if (!tipo.equals("TODOS")) {
            condiciones = condiciones + " and s.tipo = '" + tipo + "'";
        }
        if (!estado.equals("TODOS")) {
            condiciones = condiciones + " and s.estado = '" + estado + "'";
        }

        if (cargos != 0) {
            condiciones = condiciones + " and ca.codigo_cargo=" + cargos;
        }

        if (entidad != 0) {
            condiciones = condiciones + " and e.codigo_entidad=" + entidad;
        }

        sql = sql + condiciones + condicionesFechas;
        listaSolicitudes = this.jdbcTemplate.queryForList(sql);

        for (int i = 0; i < listaSolicitudes.size(); i++) {
            Map<String, Object> usu = listaSolicitudes.get(i);
            String codigo = usu.get("supervisor").toString();
            if (!codigo.equals("0")) {
                String nombre = nombreUsuario(codigo);
                usu.replace("supervisor", nombre);
                listaSolicitudes.set(i, usu);
            } else {
                String ninguno = "NINGUNO";
                usu.replace("supervisor", ninguno);
                listaSolicitudes.set(i, usu);
            }
        }

        return listaSolicitudes;
    }

    public String funcionario() {
        String funcionario = "";
        Usuario usuario = new Usuario(codigo_say);
        funcionario = usuario.getNombreCompleto();
        return funcionario;
    }
}
