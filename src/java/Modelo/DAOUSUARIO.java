package Modelo;

import Helper.Calendario;
import Helper.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DAOUSUARIO extends Conexion {

    public DAOUSUARIO(Usuario usuario) {
        super(usuario);
    }

    public DAOUSUARIO() {
    }

    public List<Map<String, Object>> listaSolicitudesPendientes() {
        String sql = "select s.codigo_solicitud,s.fecha_solicitud, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias,s.tipo,s.detalle_compensacion "
                + "from solicitud_vacaciones s, funcionario f "
                + "where s.codigo_funcionario=f.codigo_sai and s.codigo_funcionario = " + codigo_say + " and s.estado='PENDIENTE'";
        return this.ejecutarConsulta(sql);
    }

    public void anularSolicitud(String codigo) {
        String sql = "UPDATE public.solicitud_vacaciones "
                + "SET estado='ANULADO', fecha_estado=(select current_date) "
                + "WHERE codigo_solicitud='" + codigo + "'";
        this.actualizarConsulta(sql);
    }

    public JSONObject cargarDatos() throws JSONException {
        return this.datosFuncionario();
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

    public List<Map<String, String>> gestiones() throws JSONException {
        List<Map<String, String>> funcionarioList = new LinkedList<>();
        JSONObject usuario = cargarDatos();
        Date ingreso = new Date(usuario.get("fecha_ingreso").toString());
        int antiguedad = ingreso.antiguedad();
        int gestion = ingreso.getGestion();
        int mes = ingreso.getMes();
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

    public Date fechaIngreso() {
        List<Map<String, Object>> list;
        String funcionario = "select fecha_ingreso "
                + "FROM public.funcionario where codigo_sai = " + codigo_say;
        list = this.jdbcTemplate.queryForList(funcionario);
        Date fecha = new Date(list.get(0).get("fecha_ingreso").toString());
        return fecha;
    }

    public List<List<Map<String, String>>> calendarioMes(int gestion, int mes) {
        if (gestion == 0 || mes == 0) {
            Date date = new Date();
            gestion = date.fechaActual().getGestion();
            mes = date.fechaActual().getMes();
        }
        Calendario calendario = new Calendario(gestion, mes);

        List<List<Map<String, String>>> listas = calendario.dias_calendario();
        return listas;
    }

    public List<List<Map<String, String>>> listaActividades(int gestion, int mes) {
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

    public List<Map<String, Object>> entidades() {
        String sql = "SELECT codigo_entidad, nombre_entidad, tipo_entidad, entidad_supervisor FROM public.entidad";
        List<Map<String, Object>> entidades;
        entidades = this.jdbcTemplate.queryForList(sql);
        return entidades;
    }

    public void registrarFecha(String fecha, String entidad, String turno, String detalle) {
        String sql = "INSERT INTO public.fechas("
                + "fecha, descripcion_estado, tipo, entidad) "
                + "VALUES (?, ?, ?, ?)";
        this.jdbcTemplate.update(sql,
                new Date(fecha),
                detalle,
                turno,
                Integer.parseInt(entidad)
        );
    }

    public double diasNoLaborables(String fecha_salida, String fecha_retorno, String diferencia, String turno_salida, String turno_retorno) {
        double diasNoLaborables = 0;
        try {
            Date salida = new Date(fecha_salida);
            Date retorno = new Date(fecha_retorno);
            String listaEntidades = "select fecha,tipo,entidad from fechas where entidad = 0 and fecha>= '" + fecha_salida + "'  and fecha<= '" + fecha_retorno + "' order by fecha";
            String listaEntidad = "select fecha,tipo,e.nombre_entidad from fechas f, entidad e, funcionario fun where "
                    + "fecha>= '" + fecha_salida + "'  and fecha<= '" + fecha_retorno + "' "
                    + "and  fun.entidad=e.codigo_entidad "
                    + "and e.codigo_entidad=f.entidad "
                    + "and fun.codigo_sai=" + codigo_say + " "
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

    public List<List<Map<String, String>>> listaActividadesEntidad(int gestion, int mes) {
        Calendario calendario = new Calendario(gestion, mes);
        List<List<Map<String, String>>> listas = calendario.dias_calendario();
        List<Map<String, Object>> list;
        String fecha_inicial = gestion + "-" + mes + "-" + "1";
        String fecha_final = gestion + "-" + mes + "-" + String.valueOf(calendario.diasMes(mes));

        String listaEntidades = "select fecha,descripcion_estado,tipo,entidad from fechas where entidad = 0 and fecha>= '" + fecha_inicial + "'  and fecha<= '" + fecha_final + "' order by fecha";
        String listaEntidad = "select fecha,tipo,descripcion_estado,e.nombre_entidad from fechas f, entidad e, funcionario fun where "
                + "fecha>= '" + fecha_inicial + "'  and fecha<= '" + fecha_final + "' "
                + "and  fun.entidad=e.codigo_entidad "
                + "and e.codigo_entidad=f.entidad "
                + "and fun.codigo_sai=" + codigo_say + " "
                + "order by fecha";
        List<Map<String, Object>> entidades = this.jdbcTemplate.queryForList(listaEntidades);
        List<Map<String, Object>> entidad = this.jdbcTemplate.queryForList(listaEntidad);

        list = entidades;
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
                    }
                }
            }
        }
        list = entidad;
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
                    }
                }
            }
        }
        return listas;
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

    public double sinGoce() {
        double sinGoce = 0;
        String sql = "select sum(dias) "
                + "from solicitud_vacaciones s, funcionario f "
                + "where s.codigo_funcionario=f.codigo_sai and s.codigo_funcionario = " + codigo_say + " and tipo = 'LICENCIA' and s.estado='ACEPTADO'";
        List<Map<String, Object>> lista = this.jdbcTemplate.queryForList(sql);
        try {
            sinGoce = Double.parseDouble(lista.get(0).get("sum").toString());
        } catch (Exception e) {

        }
        return sinGoce;
    }

    public double compensaciones() {
        double compensaciones = 0;
        String sql = "select sum(dias) "
                + "from solicitud_vacaciones s, funcionario f "
                + "where s.codigo_funcionario=f.codigo_sai and s.codigo_funcionario = " + codigo_say + " and tipo = 'COMPENSACION' and s.estado='ACEPTADO'";
        List<Map<String, Object>> lista = this.jdbcTemplate.queryForList(sql);
        try {
            compensaciones = Double.parseDouble(lista.get(0).get("sum").toString());
        } catch (Exception e) {

        }
        return compensaciones;
    }

    public List<Map<String, Object>> listaSolicitudesAceptadas() {
        String sql = "select s.codigo_solicitud,s.fecha_solicitud, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias, "
                + "f.apellido,f.nombre,ca.nombre_cargo,s.tipo,s.detalle_compensacion,s.supervisor "
                + "from solicitud_vacaciones s, funcionario f, cargo ca "
                + "where s.codigo_funcionario=f.codigo_sai and "
                + "f.codigo_sai = " + codigo_say + " and "
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
        String sql = "select s.codigo_solicitud,s.fecha_solicitud, s.fecha_salida, s.turno_salida,s.fecha_retorno,s.turno_retorno, s.fecha_estado,s.dias, "
                + "f.apellido,f.nombre,ca.nombre_cargo,s.tipo,s.detalle_compensacion,s.supervisor "
                + "from solicitud_vacaciones s, funcionario f, cargo ca "
                + "where s.codigo_funcionario=f.codigo_sai and "
                + "f.codigo_sai = " + codigo_say + " and "
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

    public String nombreUsuario(String codigo) {
        String nombreUsuario = "";
        try {
            String sql = "select nombre, apellido from funcionario where codigo_sai=" + codigo;
            List<Map<String, Object>> usuario;
            usuario = this.jdbcTemplate.queryForList(sql);
            nombreUsuario = usuario.get(0).get("apellido").toString() + ", " + usuario.get(0).get("nombre").toString();

        } catch (Exception e) {
            nombreUsuario = "0";
        }
        return nombreUsuario;
    }

    public JSONObject enviarSolicitud(String fecha_salida, String turno_salida, String fecha_retorno, String turno_retorno, String tipo, String detalle, String dias, String duodesimas, String aceptar) throws JSONException {
        JSONObject json = new JSONObject();
        double diasSolicitados = dias.equals("") ? 0 : Double.parseDouble(dias);
        boolean solicitud = false;
        boolean tieneDuodesimas = false;
        double vacaciones = vacaciones();
        int saldoDuodesimas = duodesimas();
        try {
            String mensaje = "";
            if (!fecha_retorno.equals("")) {
                if (!fecha_salida.equals("")) {
                    if (!turno_salida.equals("")) {
                        if (!turno_retorno.equals("")) {
                            if (!tipo.equals("")) {
                                if (vacaciones == 0 && tipo.equals("VACACION")) {
                                    if (saldoDuodesimas != 0) {
                                        if (saldoDuodesimas >= diasSolicitados) {
                                            if (duodesimas.equals("SI") && aceptar.equals("ACEPTAR")) {
                                                solicitud = true;
                                                insertarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, "DUODESIMA", detalle, dias);
                                                mensaje = "la solicitud no fue insertada";
                                            } else {
                                                tieneDuodesimas = true;
                                                mensaje = "Usted solo cuenta con un maximo de " + saldoDuodesimas + " dias de vacaciones por duodesimas";
                                            }
                                        } else {
                                            mensaje = "Usted puede solicitar un maximo de " + saldoDuodesimas + " dias de vacaciones por duodesimas";
                                        }
                                    } else {
                                        solicitud = false;
                                        mensaje = "Usted no cuenta con duodesimas";
                                    }
                                } else {
                                    if (diasSolicitados <= vacaciones()) {
                                        solicitud = true;
                                        if (aceptar.equals("ACEPTAR")) {
                                            insertarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, tipo, detalle, dias);
                                        } else {
                                            mensaje = "la solicitud no fue insertada";
                                        }
                                    } else {
                                        switch (tipo) {
                                            case "COMPENSACION":
                                                solicitud = true;
                                                if (aceptar.equals("ACEPTAR")) {
                                                    insertarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, tipo, detalle, dias);
                                                } else {
                                                    mensaje = "la solicitud no fue insertada";
                                                }
                                                break;
                                            case "ASUETO":
                                                solicitud = true;
                                                if (aceptar.equals("ACEPTAR")) {
                                                    insertarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, tipo, detalle, dias);
                                                } else {
                                                    mensaje = "la solicitud no fue insertada";
                                                }
                                                break;
                                            case "LICENCIA":
                                                solicitud = true;
                                                if (aceptar.equals("ACEPTAR")) {
                                                    insertarSolicitud(fecha_salida, turno_salida, fecha_retorno, turno_retorno, tipo, detalle, dias);
                                                } else {
                                                    mensaje = "la solicitud no fue insertada";
                                                }
                                                break;
                                            default:
                                                solicitud = false;
                                                mensaje = "Usted no puede exeder la cantidad de dias que tiene de vacacion";
                                                break;
                                        }
                                    }
                                }
                            } else {
                                solicitud = false;
                                mensaje = "Debe seleccionar un tipo de solicitud";
                            }
                        } else {
                            solicitud = false;
                            mensaje = "Debe seleccionar un turno de retorno";
                        }
                    } else {
                        solicitud = false;
                        mensaje = "Debe seleccionar un turno de salida";
                    }
                } else {
                    solicitud = false;
                    mensaje = "Debe introducir una fecha de salida";
                }
            } else {
                solicitud = false;
                mensaje = "Debe introducir una fecha de retorno";
            }
            json.put("solicitud", solicitud);
            json.put("mensaje", mensaje);
            json.put("tieneDuodesimas", tieneDuodesimas);

        } catch (Exception e) {
            json.put("solicitud", solicitud);
            json.put("mensaje", e.getMessage());
            json.put("tieneDuodesimas", tieneDuodesimas);
        }

        return json;
    }

    public double vacaciones() throws JSONException {
        double totalVacaciones = totalVacaciones();
        double vacacionesTomadas = vacacionesTomadas();

        return totalVacaciones - vacacionesTomadas;
    }

    public double totalVacaciones() throws JSONException {
        List<Map<String, String>> gestiones = gestiones();
        double gestioTotal = 0;
        for (Map<String, String> g : gestiones) {
            gestioTotal += Double.parseDouble(g.get("saldo").toString());
        }
        return gestioTotal;
    }

    public double vacacionesTomadas() {
        List<Map<String, Object>> funcionarioList;
        String funcionario = "select sum(dias) from solicitud_vacaciones s, funcionario f "
                + "where s.codigo_funcionario=f.codigo_sai and "
                + "s.estado = 'ACEPTADO' and "
                + "(s.tipo = 'DUODESIMA' OR  s.tipo = 'VACACION') and "
                + "s.codigo_funcionario= " + codigo_say;
        try {
            funcionarioList = this.jdbcTemplate.queryForList(funcionario);
            double dias = Double.parseDouble(funcionarioList.get(0).get("sum").toString());
            return dias;
        } catch (Exception e) {
            return 0;
        }
    }

    private int duodesimas() {
        Date actual = new Date();
        int mesAcutal = actual.getMes();
        Date ingreso = new Date(fechaIngreso().toString());
        int mesIngreso = ingreso.getMes();
        int duodesima = 0;
        int diferencia = (mesAcutal - mesIngreso);
        int antiguedad = antiguedad();
        int beneficio = 0;
        if (antiguedad > 0 && antiguedad <= 5) {
            beneficio = 15;
        } else {
            if (antiguedad >= 6 && antiguedad <= 10) {
                beneficio = 20;
            } else {
                if (antiguedad >= 11) {
                    beneficio = 30;
                }
            }
        }
        if (diferencia > 0) {
            duodesima = (int) ((beneficio / 12) * (diferencia));
        } else {
            int total = diferencia + 12;
            duodesima = (int) ((beneficio / 12) * (total));
        }
        return duodesima;
    }

    private void insertarSolicitud(String fecha_salida, String turno_salida, String fecha_retorno, String turno_retorno, String tipo, String detalle, String dias) {
        String codigo = codigo(tipo);
        String sql = "INSERT INTO public.solicitud_vacaciones("
                + "codigo_solicitud,"
                + "supervisor,"
                + " fecha_solicitud,"
                + " fecha_salida,"
                + " turno_salida,"
                + " fecha_retorno,"
                + " turno_retorno,"
                + " dias,"
                + " tipo,"
                + " detalle_compensacion,"
                + " codigo_funcionario) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Date fecha = new Date().fechaActual();
        String a = fecha.toString();
        Date fecha_s = new Date(fecha_salida);
        this.jdbcTemplate.update(sql,
                codigo,
                0,
                fecha,
                fecha_s,
                turno_salida,
                new Date(fecha_retorno),
                turno_retorno,
                Double.parseDouble(dias),
                tipo,
                detalle,
                codigo_say
        );
    }

    public void actualizar(String usuario, String pass) {
        try {
            String sql = "UPDATE public.cuenta "
                    + "SET usuario='" + usuario + "', pass='" + pass + "' "
                    + "WHERE codigo_funcionario=" + codigo_say;
            this.actualizarConsulta(sql);
        } catch (Exception e) {
            String m = e.getMessage();
            System.err.println(m);
        }
    }

}
