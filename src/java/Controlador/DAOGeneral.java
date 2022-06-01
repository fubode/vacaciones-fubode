package Controlador;

import Helper.Calendario;
import Helper.Date;
import Modelo.Conexion;
import Modelo.Usuario;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DAOGeneral extends Conexion {

    public DAOGeneral(Usuario usuario) {
        super(usuario);
    }

    public DAOGeneral() {
    }

    public JSONObject diasNoLaborables(String fecha_salida, String fecha_retorno, String diferencia, String turno_salida, String turno_retorno, String codigo) {
        double diasNoLaborables = 0;
        
        int say = 0;
        try {
            say = Integer.parseInt(codigo);
        } catch (Exception e) {
            say = codigo_say;
        }
        String mensaje = "";
        JSONObject json = new JSONObject();
        String a = "asd";
        try {
            Date salida = new Date(fecha_salida);
            Date retorno = new Date(fecha_retorno);
            if (esFeriado(salida, codigo) || esFeriado(retorno, codigo)) {
                json.put("mensaje", "NO PUEDE SELECCIONAR UN DIA FERIADO");
                json.put("dias", "");
            } else {
                double descuentoDias = descuentoDias(salida, retorno, say);
                json.put("mensaje", "EXITO");
            }
        } catch (Exception e) {

        }

        /*
            String sqlSay = null;
            int say = 0;
            if (codigo.equals("null")) {
                say = codigo_say;
            } else {
                sqlSay = "SELECT codigo_funcionario "
                        + "FROM public.solicitud_vacaciones "
                        + "WHERE codigo_solicitud='" + codigo + "'";
                say = Integer.parseInt(this.jdbcTemplate.queryForList(sqlSay).get(0).get("codigo_funcionario").toString());
            }
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
            System.out.println(e.getMessage());
        }
        if (diasNoLaborables < 0) {
            diasNoLaborables = 0;
        }*/
        return json;
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
        String funcionario = "select sum(dias)from solicitud_vacaciones s, funcionario f "
                + "where s.codigo_funcionario=f.codigo_sai and "
                + "s.estado = 'ACEPTADO' and "
                + "s.codigo_funcionario=" + codigo_say;
        try {
            funcionarioList = this.jdbcTemplate.queryForList(funcionario);
            return Integer.parseInt(funcionarioList.get(0).get("sum").toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Map<String, String>> gestiones() throws JSONException {
        List<Map<String, String>> funcionarioList = new LinkedList<>();
        JSONObject usuario = this.datosFuncionario();
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

    private boolean esFeriado(Date fecha, String codigo) {
        boolean esFeriado = false;
        Usuario usuario = new Usuario(codigo_say);
        int entididad = usuario.getCodigoEntidad();

        List<Map<String, Object>> fechas;
        String sql = "SELECT id_fechas, fecha, descripcion_estado, tipo, entidad "
                + "FROM public.fechas "
                + "WHERE fecha='" + fecha.toString() + "'";
        fechas = this.jdbcTemplate.queryForList(sql);
        String detalle = "";
        if (fechas.size() == 1) {
            detalle = fechas.get(0).get("tipo").toString();
            if (detalle.equals("NO_LABORAL")) {
                esFeriado = true;
            }
        } else {
            for (int i = 0; i < fechas.size(); i++) {
                detalle = fechas.get(i).get("tipo").toString();
                if (detalle.equals("NO_LABORAL")) {
                    esFeriado = true;
                    break;
                }
            }
        }
        return esFeriado;
    }

    private double descuentoDias(Date salida, Date retorno, int codigo) throws ParseException {
        double descuentoDias = 0;
        String listaEntidades = "select fecha,tipo,entidad from fechas where entidad = 0 and fecha>= '" + salida.toString() + "'  and fecha<= '" + retorno.toString() + "' order by fecha";
        String listaEntidad = "select fecha,tipo,e.nombre_entidad from fechas f, entidad e, funcionario fun where "
                + "fecha>= '" + salida.toString() + "'  and fecha<= '" + retorno.toString() + "' "
                + "and  fun.entidad=e.codigo_entidad "
                + "and e.codigo_entidad=f.entidad "
                + "and fun.codigo_sai=" + codigo + " "
                + "order by fecha";
        List<Map<String, Object>> entidades = this.jdbcTemplate.queryForList(listaEntidades);
        List<Map<String, Object>> entidad = this.jdbcTemplate.queryForList(listaEntidad);
        Calendario calendario = new Calendario();
        List<Map<String, Object>> dias = calendario.dias(salida.toString(),retorno.toString());
        return descuentoDias;
    }
}
